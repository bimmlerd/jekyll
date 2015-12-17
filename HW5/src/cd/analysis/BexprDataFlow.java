package cd.analysis;

import cd.ir.Ast;
import cd.ir.Ast.Expr;
import cd.ir.AstVisitor;
import cd.util.debug.AstOneLine;

import java.util.HashSet;
import java.util.Set;

/**
 * Identifies busy expressions (-bexpr) and prints a message in the console that shows useful information about
 * these expressions (e.g., expression line number with the expression string or AST subtree).
 */
public class BexprDataFlow extends BwdAndDataFlow<Expr> {
    @Override
    void initSets(ControlFlowGraph cfg) {
        ExpressionCollector exprCollector = new ExpressionCollector();

        // collect all expressions used intra-procedural
        Set<Expr> exprs = new HashSet<>();
        for (BasicBlock b : cfg.blockSet) {
            for (Ast ast : b.statements) {
                exprs.addAll(exprCollector.visit(ast, null));
            }
        }
        setSolution(startPoint(cfg), new HashSet<>()); // the solution set of the start point is empty

        for (BasicBlock b : cfg.blockSet) {
            setSolution(b, exprs); // the solution sets for all other blocks initially contain all expressions
            DFContext ctx = new DFContext(exprCollector, exprs);
            DFVisitor dfVisitor = new DFVisitor();
            for (Ast ast : b.statements) {
                ctx = dfVisitor.visit(ast, ctx);
            }
            b.localCut.addAll(ctx.localCut);
            b.localNew.addAll(ctx.localNew);
        }
    }

    @Override
    void evaluateDataFlow(ControlFlowGraph cfg) {
        for (BasicBlock b : cfg.blockSet) {
            if (b.condition != null && !context(b).isEmpty()) {
                System.out.println(String.format("Busy expressions at %s in method %s.%s:",
                        AstOneLine.toString(b.condition), cfg.classDecl.name, cfg.methodDecl.name));
                context(b).stream().forEach(expr -> System.out.println(AstOneLine.toString(expr)));
            }
        }
    }

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

    private class DFVisitor extends AstVisitor<DFContext, DFContext> {
        @Override
        public DFContext assign(Ast.Assign ast, DFContext arg) {
            if (ast.left() instanceof Ast.Var) {
                arg.cutExpressionsContaining((Ast.Var) ast.left());
            }
            arg.filterCut(arg.expressionCollector.visit(ast.right(), null));
            return arg;
        }

        @Override
        protected DFContext dflt(Ast ast, DFContext arg) {
            arg.filterCut(arg.expressionCollector.visit(ast, null));
            return arg;
        }
    }
}
