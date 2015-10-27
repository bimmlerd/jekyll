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

    Map<String, ClassDecl> classDeclMap = new HashMap<>();
    SymbolTable<Symbol.ClassSymbol> classSymbolTable = new SymbolTable<>(null); // null for outermost scope

	public SemanticAnalyzer(Main main) {
		this.main = main;
	}

	public void check(List<ClassDecl> classDecls) throws SemanticFailure {
		{
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
                fillInClassSymbolTable(classDecl);
            }

		}
	}

    private void fillInClassSymbolTable(ClassDecl classDecl, List<String> alreadyChecked) {
        // TODO reimplement as visitor
        if (alreadyChecked.contains(classDecl.name)) {
            throw new SemanticFailure(SemanticFailure.Cause.CIRCULAR_INHERITANCE);
        }

        if (classSymbolTable.contains(classDecl.superClass)) {
            // the class symbol of the parent already exists, we're good.
            Symbol.ClassSymbol classSymbol = new Symbol.ClassSymbol(classDecl);
            classSymbol.superClass = classSymbolTable.get(classDecl.superClass);
            classSymbolTable.put(classSymbol);
            classDecl.sym = classSymbol;
            classDeclMap.remove(classDecl.name);
        } else {
            // we don't have the class symbol of the parent yet, so we 'visit' that first
            alreadyChecked.add(classDecl.name);
            fillInClassSymbolTable(classDeclMap.get(classDecl.superClass), alreadyChecked);
        }
    }

    private void fillInClassSymbolTable(ClassDecl classDecl) {
        fillInClassSymbolTable(classDecl, new ArrayList<>());
    }

}
