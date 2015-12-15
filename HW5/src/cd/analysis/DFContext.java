package cd.analysis;

import cd.ir.Ast;
import cd.ir.ExprVisitor;

import java.util.HashSet;
import java.util.Set;

/**
 * Holds localNew and localCut
 */
public class DFContext {
    protected final Set<Ast.Expr> localNew = new HashSet<>();
    protected final Set<Ast.Expr> localCut = new HashSet<>();
    protected final BexprDataFlow.ExpressionCollector expressionCollector;

    protected final Set<Ast.Expr> allExpr;

    public DFContext(BexprDataFlow.ExpressionCollector expressionCollector, Set<Ast.Expr> allExpr) {
        this.expressionCollector = expressionCollector;
        this.allExpr = allExpr;
    }

    void cutExpressionsContaining(Ast.Var var) {
        ContainsVisitor containsVisitor = new ContainsVisitor();
        allExpr.stream().filter((expr -> containsVisitor.visit(expr, var))).forEach((localCut::add));
    }

    void filterCut(Set<Ast.Expr> exprs) {
        exprs.stream().filter(e -> !localCut.contains(e)).forEach(localNew::add);
    }

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
}
