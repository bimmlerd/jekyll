package cd.analyses;

import cd.ir.Ast.Expr;

/**
 * Identifies busy expressions (-bexpr) and prints a message in the console that shows useful information about
 * these expressions (e.g., expression line number with the expression string or AST subtree).
 */
public class BexprDataFlow extends BwdAndDataFlow<Expr> {
    @Override
    void initSolutionSets(ControlFlowGraph cfg) {

    }

    @Override
    void computeLocals(ControlFlowGraph cfg) {

    }
}
