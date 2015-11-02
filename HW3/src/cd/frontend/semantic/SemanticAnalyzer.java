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

    private Map<String, ClassDecl> classDeclMap = new HashMap<>();
    protected SymbolTable<Symbol.ClassSymbol> classSymbolTable = new SymbolTable<>(null); // null for outermost scope

    protected SymbolTable<Symbol.TypeSymbol> typeSymbolTable = new SymbolTable<>(null); // types are in a global scope

	public SemanticAnalyzer(Main main) {
		this.main = main;
	}

	public void check(List<ClassDecl> classDecls) throws SemanticFailure {
		{
            // handle duplicate class declarations
            classSymbolTable.put(Symbol.ClassSymbol.objectType);
			for (ClassDecl classDecl : classDecls) {
                if (classDeclMap.containsKey(classDecl.name)) {
                    throw new SemanticFailure(SemanticFailure.Cause.DOUBLE_DECLARATION,
                            "Already declared: Class %s", classDecl.name);
                } else {
                    classDeclMap.put(classDecl.name, classDecl);
                }
            }

            for (ClassDecl classDecl : classDecls) {
                buildClassSymbolTable(classDecl);
            }

            buildTypeSymbolTable();

            SymbolTableBuilderVisitor symbolTableBuilderVisitor = new SymbolTableBuilderVisitor(this);

            for (ClassDecl classDecl: classDecls) {
                symbolTableBuilderVisitor.visit(classDecl, null);
            }


		}
	}

    private void buildClassSymbolTable(ClassDecl classDecl, List<String> alreadyChecked) {
        if (alreadyChecked.contains(classDecl.name)) {
            throw new SemanticFailure(SemanticFailure.Cause.CIRCULAR_INHERITANCE,
                    "Circular inheritance detected, involving %s", classDecl.name);
        }

        if (classSymbolTable.contains(classDecl.name)) {
            // Already handled this class, and we assume no duplicates
            return;
        }

        if (!classSymbolTable.contains(classDecl.superClass)) {
            // we don't have the class symbol of the parent yet, so we 'visit' that first
            alreadyChecked.add(classDecl.name);
            buildClassSymbolTable(classDeclMap.get(classDecl.superClass), alreadyChecked);
        }
        // the class symbol of the parent now exists, we're good.
        Symbol.ClassSymbol classSymbol = new Symbol.ClassSymbol(classDecl);
        classSymbol.superClass = classSymbolTable.get(classDecl.superClass);
        classSymbolTable.put(classSymbol);
        classDecl.sym = classSymbol;
    }

    private void buildClassSymbolTable(ClassDecl classDecl) {
        buildClassSymbolTable(classDecl, new ArrayList<>());
    }

    private void buildTypeSymbolTable() {
        typeSymbolTable.put(Symbol.TypeSymbol.PrimitiveTypeSymbol.booleanType);
        typeSymbolTable.put(new Symbol.ArrayTypeSymbol(Symbol.TypeSymbol.PrimitiveTypeSymbol.booleanType));
        typeSymbolTable.put(Symbol.TypeSymbol.PrimitiveTypeSymbol.intType);
        typeSymbolTable.put(new Symbol.ArrayTypeSymbol(Symbol.TypeSymbol.PrimitiveTypeSymbol.intType));
        typeSymbolTable.put(Symbol.TypeSymbol.PrimitiveTypeSymbol.voidType); // no void[]
        typeSymbolTable.put(Symbol.TypeSymbol.ClassSymbol.nullType);
        // TODO
        for (Symbol.ClassSymbol classSymbol: classSymbolTable.symbolMap.values()) {
            typeSymbolTable.put(classSymbol);
            typeSymbolTable.put(new Symbol.ArrayTypeSymbol(classSymbol));
        }
    }
}
