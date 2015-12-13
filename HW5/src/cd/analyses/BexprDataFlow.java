package cd.analyses;

import cd.ir.Ast.Expr;

import java.util.HashSet;
import java.util.Set;

/**
 * Identifies busy expressions (-bexpr) and prints a message in the console that shows useful information about
 * these expressions (e.g., expression line number with the expression string or AST subtree).
 */
public class BexprDataFlow extends BwdAndDataFlow<Expr> {
    @Override
    void initSolutionSets(ControlFlowGraph cfg) {
        setSolution(startPoint(cfg), new HashSet<>()); // the solution set of the start point is empty
        Set<Expr> exprs = new HashSet<>(); // TODO: collect all expressions used intra-procedural
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
}
