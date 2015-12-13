package cd.analyses;

import cd.ir.Ast;
import cd.ir.Ast.Expr;
import cd.ir.AstVisitor;

import java.util.HashSet;
import java.util.Set;

/**
 * Identifies busy expressions (-bexpr) and prints a message in the console that shows useful information about
 * these expressions (e.g., expression line number with the expression string or AST subtree).
 */
public class BexprDataFlow extends BwdAndDataFlow<Expr> {
    @Override
    void initSolutionSets(ControlFlowGraph cfg) {
        // collect all expressions used intra-procedural
        Set<Expr> exprs = new HashSet<>();
        ExpressionCollector collector = new ExpressionCollector();
        for (BasicBlock b : cfg.blockSet) {
            for (Ast ast : b.statements) {
                exprs.addAll(collector.visit(ast, null));
            }
        }

        setSolution(startPoint(cfg), new HashSet<>()); // the solution set of the start point is empty
        for (BasicBlock b : cfg.blockSet) {
            setSolution(b, exprs); // the solution sets for all other blocks initially contain all expressions
        }
    }

    @Override
    void computeLocals(ControlFlowGraph cfg) {

    }

    @Override
    void evaluateDataFlow(ControlFlowGraph cfg) {

    }

    protected class ExpressionCollector extends AstVisitor<Set<Expr>, Void> {
        @Override
        protected Set<Expr> dflt(Ast ast, Void arg) {
            Set<Expr> union = new HashSet<>();
            for (Ast child : ast.children()) {
                union.addAll(visit((Expr) child, arg));
            }
            return union;
        }

        @Override
        public Set<Expr> binaryOp(Ast.BinaryOp ast, Void arg) {
            Set<Expr> singleExpr = new HashSet<>();
            singleExpr.add(ast);
            return singleExpr;
        }

        @Override
        public Set<Expr> unaryOp(Ast.UnaryOp ast, Void arg) {
            Set<Expr> singleExpr = new HashSet<>();
            singleExpr.add(ast);
            return singleExpr;
        }
    }
}
