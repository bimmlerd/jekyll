package cd.frontend.semantic;

import cd.Main;
import cd.ir.Ast.ClassDecl;
import cd.ir.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SemanticAnalyzer {

	public final Main main;

    private Map<String, Symbol.ClassSymbol> classSymbolMap = new HashMap<>();
    private Map<String, Symbol.TypeSymbol> globalSymbolTable = new HashMap<>();

    private void addToClassSymbolMap(Symbol.ClassSymbol sym) {
        classSymbolMap.put(sym.name, sym);
    }
    private void addToGlobalSymbolTable(Symbol.TypeSymbol sym) {
        globalSymbolTable.put(sym.name, sym);
    }

	public SemanticAnalyzer(Main main) {
		this.main = main;
	}

	public void check(List<ClassDecl> classDecls) throws SemanticFailure {
		{
            // List of all ClassSymbols. Handle duplicate class declarations in classDecls.
			for (ClassDecl classDecl : classDecls) {
                if (classSymbolMap.containsKey(classDecl.name)) {
                    // Two classes with the same name.
                    throw new SemanticFailure(SemanticFailure.Cause.DOUBLE_DECLARATION,
                            "Already declared: Class %s", classDecl.name);
                } else {
                    // Create symbol for our current classDecl.
                    Symbol.ClassSymbol sym = new Symbol.ClassSymbol(classDecl);
                    classDecl.sym = sym; // Store information in the AST.
                    addToClassSymbolMap(sym);
                }
            }

            // Check for correct root of the inheritance hierarchy.
            if (classSymbolMap.containsKey(Symbol.ClassSymbol.objectType.name)) {
                // A class with the name Object is defined.
                throw new SemanticFailure(SemanticFailure.Cause.OBJECT_CLASS_DEFINED);
            }
            addToClassSymbolMap(Symbol.ClassSymbol.objectType);

            // Add all possible types to our globalSymbolTable.
            buildGlobalSymbolTable();

            // Collect information from the AST and store it in the sym field of each ast node.
            InformationCollectorVisitor informationCollectorVisitor = new InformationCollectorVisitor(globalSymbolTable);
            for (ClassDecl classDecl : classDecls) {
                informationCollectorVisitor.visit(classDecl, null);
            }

            // Test for a valid start point.
            if (!globalSymbolTable.containsKey("Main")) {
                // No class Main is defined.
                throw new SemanticFailure(SemanticFailure.Cause.INVALID_START_POINT,
                        "There is no class Main defined.");
            } else if (!((Symbol.ClassSymbol) globalSymbolTable.get("Main")).methods.containsKey("main")) {
                // Main has no method main().
                throw new SemanticFailure(SemanticFailure.Cause.INVALID_START_POINT,
                        "There is no method main() defined in Main.");
            } else if (!((Symbol.ClassSymbol) globalSymbolTable.get("Main")).getMethod("main").returnType.equals(Symbol.PrimitiveTypeSymbol.voidType)) {
                // main() has an invalid signature.
                throw new SemanticFailure(SemanticFailure.Cause.INVALID_START_POINT,
                        "The method main() has an invalid signature.");
            } else if (!((Symbol.ClassSymbol) globalSymbolTable.get("Main")).getMethod("main").parameters.isEmpty()) {
                // main() has an invalid signature.
                throw new SemanticFailure(SemanticFailure.Cause.INVALID_START_POINT,
                        "The method main() has an invalid signature.");
            }

            // Test for circular inheritance.
            for (ClassDecl classDecl : classDecls) {
                checkForCircularInheritance(classDecl);
            }

/*
            SymbolTableBuilderVisitor symbolTableBuilderVisitor = new SymbolTableBuilderVisitor(this);

            for (ClassDecl classDecl: classDecls) {
                symbolTableBuilderVisitor.visit(classDecl, null);
            }
*/
		}
	}

    private void buildGlobalSymbolTable() {
        // Add built-in primitive types.
        addToGlobalSymbolTable(Symbol.TypeSymbol.PrimitiveTypeSymbol.intType);
        addToGlobalSymbolTable(new Symbol.ArrayTypeSymbol(Symbol.TypeSymbol.PrimitiveTypeSymbol.intType));
        addToGlobalSymbolTable(Symbol.TypeSymbol.PrimitiveTypeSymbol.voidType); // no void[]
        addToGlobalSymbolTable(Symbol.TypeSymbol.PrimitiveTypeSymbol.booleanType);
        addToGlobalSymbolTable(new Symbol.ArrayTypeSymbol(Symbol.TypeSymbol.PrimitiveTypeSymbol.booleanType));

        // Add reference types.
        addToGlobalSymbolTable(Symbol.TypeSymbol.ClassSymbol.nullType);
        for (Symbol.ClassSymbol classSymbol: classSymbolMap.values()) {
            addToGlobalSymbolTable(classSymbol);
            addToGlobalSymbolTable(new Symbol.ArrayTypeSymbol(classSymbol)); // TODO: There should probably be no Main[].
        }
    }

    private void checkForCircularInheritance(Symbol.ClassSymbol sym, List<String> alreadyChecked) {
        if (alreadyChecked.contains(sym.name)) {
            // Inheritance relationships between classes contain a cycle.
            throw new SemanticFailure(SemanticFailure.Cause.CIRCULAR_INHERITANCE,
                    "Circular inheritance detected, involving Class %s", sym.name);
        } else if (!sym.superClass.name.equals("Object")) {
            alreadyChecked.add(sym.name);
            checkForCircularInheritance(classSymbolMap.get(sym.superClass.name), alreadyChecked);
        }
    }

    private void checkForCircularInheritance(ClassDecl classDecl) {
        checkForCircularInheritance(classDecl.sym, new ArrayList<>());
    }
}
