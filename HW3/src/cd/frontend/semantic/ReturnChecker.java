package cd.frontend.semantic;

import cd.ir.Ast;
import cd.ir.AstVisitor;
import cd.ir.Symbol;

import java.util.Optional;

public class ReturnChecker {

    private final SymbolTable<Symbol.TypeSymbol> st;

    public ReturnChecker(SymbolTable<Symbol.TypeSymbol> symbolTable) {
        this.st = symbolTable;
    }

    public void check() {
        ReturnCheckVisitor checker = new ReturnCheckVisitor();
        for (Symbol.ClassSymbol classSymbol : st.getClassSymbols()) {
            for (Symbol.MethodSymbol methodSymbol : classSymbol.methods.values()) {
                if (methodSymbol.returnType != Symbol.PrimitiveTypeSymbol.voidType
                && !checker.visit(methodSymbol.ast.body(), null)) {
                    throw new SemanticFailure(SemanticFailure.Cause.MISSING_RETURN, "No return found in %s", methodSymbol.name);
                }
            }
        }
    }

    protected class ReturnCheckVisitor extends AstVisitor<Boolean, Void> {

        @Override
        protected Boolean dfltStmt(Ast.Stmt ast, Void arg) {
            return Boolean.FALSE;
        }

        @Override
        public Boolean returnStmt(Ast.ReturnStmt ast, Void arg) {
            return Boolean.TRUE;
        }

        @Override
        public Boolean ifElse(Ast.IfElse ast, Void arg) {
            return visit(ast.then(), arg) && visit(ast.otherwise(), arg);
        }

        @Override
        public Boolean seq(Ast.Seq ast, Void arg) {
            if (!ast.children().isEmpty()) {
                Optional<Boolean> res = ast.children().stream().map(a -> visit(a, null)).reduce(Boolean::logicalOr);
                return res.get();
            }
            return false;
        }
    }
}
