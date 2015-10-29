package cd.frontend.semantic;

import cd.Main;
import cd.ir.Ast.ClassDecl;
import cd.ir.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class SemanticAnalyzer {

	public final Main main;

    Map<String, ClassDecl> classDeclMap = new HashMap<>();
    SymbolTable<Symbol.ClassSymbol> classSymbolTable = new SymbolTable<>(null); // null for outermost scope

	public SemanticAnalyzer(Main main) {
		this.main = main;
	}

	public void check(List<ClassDecl> classDecls) throws SemanticFailure {
		{

            SymbolTableBuilderVisitor symTableBuilder = new SymbolTableBuilderVisitor(classSymbolTable);

            Map<String, List<ClassDecl>> blah = classDecls
                    .stream()
                    .sorted((d1, d2) -> d1.name.compareTo(d2.name))
                    .collect(Collectors.groupingBy(d -> d.name));

            for (List<ClassDecl> sameNameDecls : blah.values()) {
                if (sameNameDecls.size() != 1) {
                    throw new SemanticFailure(SemanticFailure.Cause.DOUBLE_DECLARATION,
                            "Multiple declarations for class %s", sameNameDecls.get(0).name);
                }
            }

            blah.values().stream().

            for (ClassDecl classDecl : classDecls) {
                symTableBuilder.visit(classDecl, null);
            }

            for (ClassDecl classDecl : classDecls) {
                fillInClassSymbolTable(classDecl);
            }



		}
	}

    private void fillInClassSymbolTable(ClassDecl classDecl, List<String> alreadyChecked) {
        // TODO reimplement as visitor
        if (alreadyChecked.contains(classDecl.name)) {
            throw new SemanticFailure(SemanticFailure.Cause.CIRCULAR_INHERITANCE,
                    "Circular inheritance detected, involving at least %s", classDecl.name);
        }

        if (classSymbolTable.contains(classDecl.superClass)) {
            // the class symbol of the parent already exists, we're good.
            Symbol.ClassSymbol classSymbol = new Symbol.ClassSymbol(classDecl);
            classSymbol.superClass = classSymbolTable.get(classDecl.superClass);
            classSymbolTable.put(classSymbol);
            classDecl.sym = classSymbol;
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
