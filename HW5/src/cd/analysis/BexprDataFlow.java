package cd.analysis;

import cd.ir.Ast;
import cd.ir.Ast.Expr;
import cd.ir.AstVisitor;
import cd.ir.ExprVisitor;
import cd.util.debug.AstOneLine;

import java.util.HashSet;
import java.util.Set;

/**
 * Identifies busy expressions (-bexpr) and prints a message in the console that shows useful information about
 * these expressions (e.g., expression line number with the expression string or AST subtree).
 */
public class BexprDataFlow extends BwdAndDataFlow<Expr> {
    private final ExpressionCollector exprCollector = new ExpressionCollector();
    private final SetInitializer setInitializer = new SetInitializer();
    private final ContainsVisitor containsVisitor = new ContainsVisitor();

    @Override
    void initSets(ControlFlowGraph cfg) {
        // collect all expressions used intra-procedural
        Set<Expr> allExpr = new HashSet<>();
        for (BasicBlock b : cfg.blockSet) {
            for (Ast ast : b.statements) {
                allExpr.addAll(exprCollector.visit(ast, null));
            }
        }
        setSolution(startPoint(cfg), new HashSet<>()); // the solution set of the start point is empty

        for (BasicBlock b : cfg.blockSet) {
            setSolution(b, allExpr); // the solution sets for all other blocks initially contain all expressions
            LocalSetContext ctx = new LocalSetContext(allExpr);
            for (Ast ast : b.statements) {
                ctx = setInitializer.visit(ast, ctx);
            }
            b.localNew.addAll(ctx.localNew);
            b.localCut.addAll(ctx.localCut);
        }
    }

    @Override
    void evaluateDataFlow(ControlFlowGraph cfg) {
        for (BasicBlock b : cfg.blockSet) {
            // report busy expressions at the start of an if/else or while statement
            if (b.condition != null && !context(b).isEmpty()) {
                System.out.println(String.format("Busy expressions at %s in method %s.%s:",
                        AstOneLine.toString(b.condition), cfg.classDecl.name, cfg.methodDecl.name));
                context(b).stream().forEach(expr -> System.out.println(AstOneLine.toString(expr)));
            }
        }
    }

    /**
     * Visits statements and returns the set of expressions we are interested in.
     */
    // TODO: we don't want expressions that contain arrays or fields
    protected class ExpressionCollector extends AstVisitor<Set<Expr>, Void> {
        @Override
        protected Set<Expr> dfltExpr(Expr ast, Void arg) {
            Set<Expr> singleExpr = new HashSet<>();
            singleExpr.add(ast);
            return singleExpr;
        }

        @Override
        protected Set<Expr> dflt(Ast ast, Void arg) {
            Set<Expr> union = new HashSet<>();
            for (Ast child : ast.children()) {
                union.addAll(visit((Expr) child, arg));
            }
            return union;
        }
    }

    /**
     * Needed to initialize the localNew and localCut for each basic block.
     */
    private class SetInitializer extends AstVisitor<LocalSetContext, LocalSetContext> {
        @Override
        public LocalSetContext assign(Ast.Assign ast, LocalSetContext arg) {
            if (ast.left() instanceof Ast.Var) {
                arg.updateLocalCutWithExprContaining((Ast.Var) ast.left());
            }
            visit(ast.right(), arg);
            return arg;
        }

        @Override
        protected LocalSetContext dflt(Ast ast, LocalSetContext arg) {
            arg.updateLocalNewWithExprNotInCut(exprCollector.visit(ast, null));
            return arg;
        }
    }

    /**
     * Checks whether a given expressions contains a specific variable.
     */
    private class ContainsVisitor extends ExprVisitor<Boolean, Ast.Var> {
        @Override
        public Boolean var(Ast.Var ast, Ast.Var arg) {
            return ast.equals(arg);
        }

        @Override
        public Boolean visitChildren(Ast.Expr ast, Ast.Var arg) {
            Boolean result = Boolean.FALSE;
            for (Ast child : ast.children())
                result |= visit((Ast.Expr)child, arg);
            return result;
        }
    }

    /**
     * Holds intermediate localNew and localCut for the initialization process.
     */
    private class LocalSetContext {
        protected final Set<Ast.Expr> localNew = new HashSet<>();
        protected final Set<Ast.Expr> localCut = new HashSet<>();
        protected final Set<Ast.Expr> allExpr;

        public LocalSetContext(Set<Ast.Expr> allExpr) {
            this.allExpr = allExpr;
        }

        void updateLocalCutWithExprContaining(Ast.Var var) {
            allExpr.stream().filter(expr -> containsVisitor.visit(expr, var)).forEach(localCut::add);
        }

        void updateLocalNewWithExprNotInCut(Set<Ast.Expr> exprs) {
            exprs.stream().filter(expr -> !localCut.contains(expr)).forEach(localNew::add);
        }
    }
}
