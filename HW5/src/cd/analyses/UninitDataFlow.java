package cd.analyses;

import cd.frontend.semantic.SemanticFailure;
import cd.frontend.semantic.SemanticFailure.Cause;
import cd.ir.Ast;
import cd.ir.Ast.Assign;
import cd.ir.Ast.Expr;
import cd.ir.Ast.VarDecl;
import cd.ir.AstVisitor;
import cd.ir.ExprVisitor;
import cd.ir.Symbol.VariableSymbol;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Finds uses of (potentially) uninitialized variables (-uninit) and generate a semantic error (i.e., POSSIBLY UNINITIALIZED)
 * whenever an uninitialized local variable is used as an operand.
 */
public class UninitDataFlow extends FwdOrDataFlow<VariableSymbol> {
    @Override
    void initSolutionSets(ControlFlowGraph cfg) {
        // collect all variables declared locally
        Set<VariableSymbol> varSymbols = cfg.methodDecl.decls().children().stream().map(child -> ((VarDecl) child).sym).collect(Collectors.toSet());
        setSolution(startPoint(cfg), varSymbols); // initially all local variables are uninitialized
        for (BasicBlock b : cfg.blockSet) {
            setSolution(b, new HashSet<>()); // the solution sets for all other blocks are initially empty
        }
    }

    @Override
    void computeLocals(ControlFlowGraph cfg) {
        VariableCollector collector = new VariableCollector();
        for (BasicBlock b : cfg.blockSet) {
            for (Ast ast : b.statements) {
                if (ast instanceof Assign) {
                    // add initialized variables to the localCut set
                    b.localCut.addAll(collector.visit(((Assign) ast).left(), null));
                }
                // the localNew sets are all empty
            }
        }
    }

    @Override
    void evaluateDataFlow(ControlFlowGraph cfg) {
        UsageChecker checker = new UsageChecker();
        Set<VariableSymbol> uninitVars;
        for (BasicBlock b : cfg.blockSet) {
            uninitVars = context(b);
            for (Ast ast : b.statements) {
                uninitVars = checker.visit(ast, uninitVars);
            }
        }
    }

    protected class VariableCollector extends ExprVisitor<Set<VariableSymbol>, Void> {
        @Override
        protected Set<VariableSymbol> dfltExpr(Ast.Expr ast, Void arg) {
            Set<VariableSymbol> union = new HashSet<>();
            for (Ast child : ast.children()) {
                union.addAll(visit((Expr) child, arg));
            }
            return union;
        }

        @Override
        public Set<VariableSymbol> var(Ast.Var ast, Void arg) {
            Set<VariableSymbol> singleVar = new HashSet<>();
            singleVar.add(ast.sym);
            return singleVar;
        }
    }

    protected class UsageChecker extends AstVisitor<Set<VariableSymbol>, Set<VariableSymbol>> {
        VariableCollector collector = new VariableCollector();

        @Override
        protected Set<VariableSymbol> dflt(Ast ast, Set<VariableSymbol> arg) {
            Set<VariableSymbol> usedVars;
            for (Ast child : ast.children()) {
                usedVars = collector.visit((Expr) child, null);
                if (!Collections.disjoint(usedVars, arg)) {
                    // at least one variable in the set of uninitialized variables is used as an operand
                    Set<VariableSymbol> report = new HashSet<>();
                    report.addAll(usedVars);
                    report.retainAll(arg); // report all uninitialized variables that are used in the expression
                    throw new SemanticFailure(Cause.POSSIBLY_UNINITIALIZED, "Not all used variables are initialized: " + report);
                }
            }
            return arg;
        }

        @Override
        public Set<VariableSymbol> assign(Assign ast, Set<VariableSymbol> arg) {
            // first, visit the right side of the assignment and check that no uninitialized variables are used as operands
            arg = this.visit(ast.right(), arg);
            arg.removeAll(collector.visit(ast.left(), null)); // remove newly initialized variables
            return arg;
        }
    }
}
