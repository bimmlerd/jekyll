package cd.frontend.semantic;

import cd.ir.Symbol;

import java.util.HashSet;
import java.util.Set;

/**
 * Checks all inheritance related semantic aspects
 */
public class InheritanceChecker {

    private final SymbolManager sm;

    public InheritanceChecker(SymbolManager sm) {
        this.sm = sm;
    }

    public void check() {
        for (Symbol.ClassSymbol classSym : sm.getClassSymbols()) {

            // check for circular inheritance
            Set<String> alreadyChecked = new HashSet<>();
            Symbol.ClassSymbol current = classSym;
            while (current.superClass != null) {
                if (alreadyChecked.contains(current.name)) {
                    throw new SemanticFailure(SemanticFailure.Cause.CIRCULAR_INHERITANCE,
                            "Circular inheritance detected, involving Class %s", current.name);
                }

                alreadyChecked.add(current.name);
                current = current.superClass;
            }

            // check for correct method overrides.
            for (Symbol.MethodSymbol method : classSym.methods.values()) {
                Symbol.MethodSymbol superMethod = classSym.superClass.getMethod(method.name);
                if (superMethod != null
                        && !(method.returnType.equals(superMethod.returnType)
                        && method.parameters.equals(superMethod.parameters))) {
                    // The overriding method does not have the same signature as the method in the super class.
                    throw new SemanticFailure(SemanticFailure.Cause.INVALID_OVERRIDE,
                            "Invalid signature: Method %s in class %s", method.name, classSym.name);
                }
            }
        }
    }
}
