package cd.backend.codegen;

import cd.Config;
import cd.ToDoException;
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
    public final static String ARRAY_VTABLE_PREFIX = "ARRAY_VTABLE";

    public VTableBuilder(AstCodeGenerator cg) {
        this.cg = cg;
    }

    public void emitVTables(List<Ast.ClassDecl> unorderedClassDecls) {
        cg.emit.emitCommentSection("VTables");
        cg.emit.emitRaw(Config.DATA_INT_SECTION);

        // we can only build VTables of classes whose superclass' vtable already exists
        Set<Symbol.ClassSymbol> handledClasses = new HashSet<>();

        Symbol.ClassSymbol.objectType.vTable = VTable.makeObjectVTable();
        emitVTableAndArrVTable(Symbol.TypeSymbol.ClassSymbol.objectType);
        Symbol.ClassSymbol.objectType.oTable = new ObjectTable(Symbol.ClassSymbol.objectType);
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
                emitVTableAndArrVTable(current);
                current.oTable = new ObjectTable(current);
                handledClasses.add(current);
            }
        }

        // emit array vtables for primitive types
        emitArrVTable(Symbol.PrimitiveTypeSymbol.booleanType);
        emitArrVTable(Symbol.PrimitiveTypeSymbol.intType);
    }


    /**
     * Builds a vtable for @param classSymbol, assuming the vtable of the superclasses are in place.
     */
    private void buildVTable(Symbol.ClassSymbol classSymbol) {
        classSymbol.vTable = new VTable(classSymbol.superClass.vTable, classSymbol);
        classSymbol.methods.forEach((unqualifiedName, methodSym) ->
                classSymbol.vTable.add(unqualifiedName, VTableBuilder.getMethodLabel(methodSym, classSymbol)));

    }

    /**
     * Emits a vtable for classSymbol and its array type
     * @param classSymbol
     */
    private void emitVTableAndArrVTable(Symbol.ClassSymbol classSymbol) {
        cg.emit.emitLabel(getVTableLabel(classSymbol));
        classSymbol.vTable.getSortedList().forEach(cg.emit::emitConstantData);
        emitArrVTable(classSymbol);
    }

    private void emitArrVTable(Symbol.TypeSymbol typeSymbol) {
        cg.emit.emitLabel(getArrayVTableLabel(typeSymbol));
        cg.emit.emitConstantData(getVTableLabel(Symbol.ClassSymbol.objectType));
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

        public String getMethodLabel(String unqualifiedMethodName) {
            return VTableBuilder.getMethodLabel(unqualifiedMethodName, classSymbol);
        }

        public List<String> getSortedList() {
            String[] res = new String[table.values().size()];
            table.values().forEach(p -> res[p.getValue()] = p.getKey());
            return Arrays.asList(res);
        }
    }

    // Utility methods to have naming logic at one place only
    public static String getVTableLabel(String name) {
        return getVTableLabel(name, VTABLE_PREFIX);
    }

    public static String getVTableLabel(String name, String prefix) {
        return String.format("%s$%s", prefix, name);
    }

    public static String getVTableLabel(Ast.ClassDecl classDecl) {
        return getVTableLabel(classDecl.name);
    }

    public static String getArrayVTableLabel(Symbol.TypeSymbol typeSymbol) {
        if (typeSymbol instanceof Symbol.ArrayTypeSymbol) {
            throw new ToDoException("Not what this is intended for!");
        }
        return getVTableLabel(typeSymbol.name, ARRAY_VTABLE_PREFIX);
    }

    public static String getArrayVTableLabelFromArray(Symbol.TypeSymbol typeSymbol) {
        if (!(typeSymbol instanceof Symbol.ArrayTypeSymbol)) {
            throw new ToDoException("You sure?");
        }

        return getArrayVTableLabel(((Symbol.ArrayTypeSymbol) typeSymbol).elementType);
    }

    public static String getVTableLabel(Symbol.ClassSymbol classSymbol) {
        return getVTableLabel(classSymbol.name);
    }

    public static String getMethodLabel(String unqualifiedMethodName, String className) {
        return String.format("%s$%s", className, unqualifiedMethodName);
    }

    public static String getMethodLabel(String unqualifiedMethodName, Ast.ClassDecl classDecl) {
        return getMethodLabel(unqualifiedMethodName, classDecl.name);
    }

    public static String getMethodLabel(String unqualifiedMethodName, Symbol.ClassSymbol classSymbol) {
        return getMethodLabel(unqualifiedMethodName, classSymbol.name);
    }

    public static String getMethodLabel(Ast.MethodDecl methodDecl, Ast.ClassDecl classDecl) {
        return getMethodLabel(methodDecl.name, classDecl.name);
    }

    public static String getMethodLabel(Symbol.MethodSymbol methodSymbol, Ast.ClassDecl classDecl) {
        return getMethodLabel(methodSymbol.name, classDecl.name);
    }

    public static String getMethodLabel(Ast.MethodDecl methodDecl, Symbol.ClassSymbol classSymbol) {
        return getMethodLabel(methodDecl.name, classSymbol.name);
    }

    public static String getMethodLabel(Symbol.MethodSymbol methodSymbol, Symbol.ClassSymbol classSymbol) {
        return getMethodLabel(methodSymbol.name, classSymbol.name);
    }
}
