package cd.frontend.semantic;

import cd.Main;
import cd.ir.Ast.ClassDecl;
import cd.ir.Symbol;

import java.util.List;

public class SemanticAnalyzer {

    public final Main main;
    private SymbolManager symbolManager = new SymbolManager();

    public SemanticAnalyzer(Main main) {
        this.main = main;
    }

    public void check(List<ClassDecl> classDecls) throws SemanticFailure {
        // fill symbol manager with all available info
        (new SymbolCollector(symbolManager)).fillSymbolManager(classDecls);

        (new StartPointChecker()).check();

        (new InheritanceChecker(symbolManager)).check();

        (new TypeChecker(symbolManager)).check(classDecls);

    }

    protected class StartPointChecker {
        /**
         * Throws an exception if no valid starting point is present
         */
        public void check() {
            // Test for a valid start point.
            if (!symbolManager.contains("Main")) {
                // No class Main is defined.
                throw new SemanticFailure(SemanticFailure.Cause.INVALID_START_POINT,
                        "There is no class Main defined.");
            }
            Symbol.ClassSymbol mainClass = ((Symbol.ClassSymbol) symbolManager.get("Main"));
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
