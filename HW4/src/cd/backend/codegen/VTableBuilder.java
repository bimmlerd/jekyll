package cd.backend.codegen;

import cd.Config;
import cd.ir.Ast;
import cd.ir.Symbol;
import javafx.util.Pair;

import java.util.*;

/**
 * Creates the necessary vtables and emits them into the data section
 */
public class VTableBuilder {

    private final AstCodeGenerator cg;

    public final static String VTABLE_PREFIX = "VTABLE";

    public VTableBuilder(AstCodeGenerator cg) {
        this.cg = cg;
    }

    public void emitVTables(List<Ast.ClassDecl> unorderedClassDecls) {
        cg.emit.emitCommentSection("VTables");
        cg.emit.emitRaw(Config.DATA_INT_SECTION);

        // we can only build VTables of classes whose superclass' vtable already exists
        Set<Symbol.ClassSymbol> handledClasses = new HashSet<>();

        Symbol.ClassSymbol.objectType.vTable = VTable.makeObjectVTable();
        emitVTable(Symbol.TypeSymbol.ClassSymbol.objectType);
        handledClasses.add(Symbol.ClassSymbol.objectType);

        Symbol.ClassSymbol current;
        for (Ast.ClassDecl classDecl : unorderedClassDecls) {
            if (handledClasses.contains(classDecl.sym)) {
                continue;
            }

            Stack<Symbol.ClassSymbol> deferredClasses = new Stack<>();
            current = classDecl.sym;

            // go up the hierarchy until we are at a handled class, pushing deferred classes onto a stack
            while (!handledClasses.contains(current)) {
                deferredClasses.push(current);
                current = current.superClass;
            }

            // work our way down again, handling the classes in the right order
            while (!deferredClasses.empty()) {
                current = deferredClasses.pop();
                buildVTable(current);
                emitVTable(current);
                handledClasses.add(current);
            }
        }
    }


    /**
     * Builds a vtable for @param classSymbol, assuming the vtable of the superclasses are in place.
     */
    private void buildVTable(Symbol.ClassSymbol classSymbol) {
        classSymbol.vTable = new VTable(classSymbol.superClass.vTable, classSymbol);
        classSymbol.methods.forEach((unqualifiedName, methodSym) ->
                classSymbol.vTable.add(unqualifiedName, VTableBuilder.getMethodLabel(methodSym, classSymbol)));

    }

    private void emitVTable(Symbol.ClassSymbol classSymbol) {
        cg.emit.emitLabel(getVTableLabel(classSymbol));
        classSymbol.vTable.getSortedList().forEach(cg.emit::emitConstantData);
    }

    public static class VTable {

        private final Map<String, Pair<String, Integer>> table = new HashMap<>();

        private final Symbol.ClassSymbol classSymbol;
        private final static String PARENT_KEY = "$parent";

        public static VTable makeObjectVTable() {
            return new VTable();
        }

        private VTable() {
            // should only be used for object vtable
            classSymbol = Symbol.ClassSymbol.objectType;
            table.put(PARENT_KEY, new Pair<>("0", 0));
        }

        public VTable(VTable parentVTable, Symbol.ClassSymbol classSymbol) {
            table.putAll(parentVTable.table);
            this.classSymbol = classSymbol;
            table.put(PARENT_KEY, new Pair<>(parentVTable.getVTableLabel(), 0));
        }

        public void add(String unqualifiedName, String label) {
            if (!table.containsKey(unqualifiedName)) {
                table.put(unqualifiedName, new Pair<>(label, table.size()));
            } else {
                Integer index = table.get(unqualifiedName).getValue();
                table.put(unqualifiedName, new Pair<>(label, index));
            }
        }

        public int getMethodOffset(String methodName) {
            return Config.SIZEOF_PTR * table.get(methodName).getValue();
        }

        public String getVTableLabel() {
            return VTableBuilder.getVTableLabel(classSymbol);
        }

        public List<String> getSortedList() {
            String[] res = new String[table.values().size()];
            table.values().forEach(p -> res[p.getValue()] = p.getKey());
            return Arrays.asList(res);
        }
    }

    // Utility methods to have naming logic at one place only
    public static String getVTableLabel(String name) {
        return String.format("%s$%s", VTABLE_PREFIX, name);
    }
    public static String getVTableLabel(Ast.ClassDecl classDecl) {
        return getVTableLabel(classDecl.name);
    }

    public static String getVTableLabel(Symbol.ClassSymbol classSymbol) {
        return getVTableLabel(classSymbol.name);
    }

    public static String getMethodLabel(Ast.MethodDecl methodDecl, Ast.ClassDecl classDecl) {
        return String.format("%s$%s", classDecl.name, methodDecl.name);
    }

    public static String getMethodLabel(Symbol.MethodSymbol methodSymbol, Ast.ClassDecl classDecl) {
        return getMethodLabel(methodSymbol.ast, classDecl);
    }

    public static String getMethodLabel(Ast.MethodDecl methodDecl, Symbol.ClassSymbol classSymbol) {
        return getMethodLabel(methodDecl, classSymbol.ast);
    }

    public static String getMethodLabel(Symbol.MethodSymbol methodSymbol, Symbol.ClassSymbol classSymbol) {
        return getMethodLabel(methodSymbol.ast, classSymbol.ast);
    }
}
