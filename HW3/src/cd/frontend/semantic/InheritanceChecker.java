package cd.frontend.semantic;

import cd.ir.Symbol;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Checks all inheritance related semantic aspects
 */
public class InheritanceChecker {

    private final SymbolTable<Symbol.TypeSymbol> st;

    public InheritanceChecker(SymbolTable<Symbol.TypeSymbol> symbolTable) {
        this.st = symbolTable;
    }

    public void check() {

        for (Symbol.ClassSymbol classSym : st.getClassSymbols()) {

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
                if (superMethod == null) {
                    continue;
                }
                List<Symbol.TypeSymbol> methodParams = method.parameters.stream().map(p -> p.type).collect(Collectors.toList());
                List<Symbol.TypeSymbol> superMethodParams = superMethod.parameters.stream().map(p -> p.type).collect(Collectors.toList());
                if (!(method.returnType.equals(superMethod.returnType) && methodParams.equals(superMethodParams))) {
                    // The overriding method does not have the same signature as the method in the super class.
                    throw new SemanticFailure(SemanticFailure.Cause.INVALID_OVERRIDE,
                            "Invalid signature: Method %s in class %s", method.name, classSym.name);
                }
            }
        }
    }
}
