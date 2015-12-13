package cd.analyses;

import cd.ir.Ast;
import cd.ir.Ast.Assign;
import cd.ir.Ast.Expr;
import cd.ir.Ast.VarDecl;
import cd.ir.ExprVisitor;
import cd.ir.Symbol.VariableSymbol;

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
        VariableCollector visitor = new VariableCollector();
        for (BasicBlock b : cfg.blockSet) {
            for (Ast ast : b.statements) {
                if (ast instanceof Assign) {
                    // add initialized variables to the localCut set
                    b.localCut.addAll(visitor.visit(((Assign) ast).left(), null));
                }
                // the localNew sets are all empty
            }
        }
    }

    @Override
    void evaluateDataFlow(ControlFlowGraph cfg) {
        // TODO: throw SemanticFailure with cause POSSIBLY_UNINITIALIZED, if a Variable in the set of uninitialized variables is used as an operand
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
}
