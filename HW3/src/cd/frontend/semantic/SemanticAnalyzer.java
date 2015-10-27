package cd.frontend.semantic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cd.Main;
import cd.ir.Ast.ClassDecl;
import cd.ir.Symbol;

public class SemanticAnalyzer {
	
	public final Main main;

    Map<String, ClassDecl> classDeclMap = new HashMap<>();
    SymbolTable<Symbol.ClassSymbol> classSymbolTable = new SymbolTable<>();

	public SemanticAnalyzer(Main main) {
		this.main = main;
	}
	
	public void check(List<ClassDecl> classDecls) throws SemanticFailure {
		{
            classSymbolTable.put(Symbol.ClassSymbol.objectType);
			for (ClassDecl classDecl : classDecls) {
                if (classDeclMap.containsKey(classDecl.name)) {
                    // TODO make better error msg
                    throw new SemanticFailure(SemanticFailure.Cause.DOUBLE_DECLARATION);
                } else {
                    classDeclMap.put(classDecl.name, classDecl);
                }
            }

            for (ClassDecl classDecl : classDecls) {
                handleInheritance(classDecl, new ArrayList<String>());
            }
		}
	}

    private void handleInheritance(ClassDecl classDecl, List<String> alreadyChecked) {
        if (alreadyChecked.contains(classDecl.name)) {
            throw new SemanticFailure(SemanticFailure.Cause.CIRCULAR_INHERITANCE);
        }

        if (classSymbolTable.contains(classDecl.superClass)) {
            // the class symbol of the parent already exists, we're good.
            Symbol.ClassSymbol classSymbol = new Symbol.ClassSymbol(classDecl);
            classSymbol.superClass = classSymbolTable.get(classDecl.superClass);
            classSymbolTable.put(classSymbol);
            classDeclMap.remove(classDecl.name);
        } else {
            alreadyChecked.add(classDecl.name);
            handleInheritance(classDeclMap.get(classDecl.superClass), alreadyChecked);
        }
    }

}
