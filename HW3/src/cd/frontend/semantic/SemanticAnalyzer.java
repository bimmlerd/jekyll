package cd.frontend.semantic;

import cd.Main;
import cd.ir.Ast.ClassDecl;
import cd.ir.Symbol;

import java.util.List;

public class SemanticAnalyzer {

    public final Main main;
    private SymbolTable<Symbol.TypeSymbol> globalSymTable = new SymbolTable<>();

    public SemanticAnalyzer(Main main) {
        this.main = main;
    }

    public void check(List<ClassDecl> classDecls) throws SemanticFailure {

        (new SymbolCollector(globalSymTable)).fillTable(classDecls);

        (new StartPointChecker()).check();

        (new InheritanceChecker(globalSymTable)).check();

        (new ReturnChecker(globalSymTable)).check();

        (new TypeChecker(globalSymTable)).check(classDecls);
    }

    /**
     * Checks for a valid starting point to be present
     */
    protected class StartPointChecker {

        public void check() {

            if (!globalSymTable.contains("Main")) {
                // No class Main is defined.
                throw new SemanticFailure(SemanticFailure.Cause.INVALID_START_POINT,
                        "There is no class Main defined.");
            }
            Symbol.ClassSymbol mainClass = ((Symbol.ClassSymbol) globalSymTable.get("Main"));
            Symbol.MethodSymbol mainMethod = mainClass.getMethod("main");
            if (mainMethod == null) {
                // Main has no method main().
                throw new SemanticFailure(SemanticFailure.Cause.INVALID_START_POINT,
                        "There is no method main() defined in Main.");
            }
            if (!mainMethod.returnType.equals(Symbol.PrimitiveTypeSymbol.voidType) || !mainMethod.parameters.isEmpty()) {
                // main() has an invalid signature.
                throw new SemanticFailure(SemanticFailure.Cause.INVALID_START_POINT,
                        "The method main() has an invalid signature.");
            }
        }
    }
}
