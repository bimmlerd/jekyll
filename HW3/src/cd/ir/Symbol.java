package cd.ir;

import cd.ToDoException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Symbol {

    public final String name;

    public static abstract class TypeSymbol extends Symbol {

        public TypeSymbol(String name) {
            super(name);
        }

        public abstract boolean isReferenceType();

        public abstract boolean isSubtypeOf(TypeSymbol type);

        public String toString() {
            return name;
        }
    }

    public static class PrimitiveTypeSymbol extends TypeSymbol {

        /** Symbols for the built-in primitive types */
        public static final PrimitiveTypeSymbol intType = new PrimitiveTypeSymbol("int");
        public static final PrimitiveTypeSymbol voidType = new PrimitiveTypeSymbol("void");
        public static final PrimitiveTypeSymbol booleanType = new PrimitiveTypeSymbol("boolean");

        public PrimitiveTypeSymbol(String name) {
            super(name);
        }

        public boolean isReferenceType() {
            return false;
        }

        @Override
        public boolean isSubtypeOf(TypeSymbol type) {
            // TODO this currently returns true when the types are equal.
            return type == this;
        }
    }

    public static class ArrayTypeSymbol extends TypeSymbol {
        public final TypeSymbol elementType;

        @Override
        public boolean isSubtypeOf(TypeSymbol type) {
            throw new UnsupportedOperationException("isSubtypeOf does not make sense for array types");
        }

        public ArrayTypeSymbol(TypeSymbol elementType) {
            super(elementType.name + "[]");
            this.elementType = elementType;
        }

        public boolean isReferenceType() {
            return true;
        }
    }

    public static class ClassSymbol extends TypeSymbol {
        public final Ast.ClassDecl ast;
        public ClassSymbol superClass;
        public final VariableSymbol thisSymbol = new VariableSymbol("this", this);
        public final Map<String, VariableSymbol> fields = new HashMap<>();
        public final Map<String, MethodSymbol> methods = new HashMap<>();

        /** Symbols for the built-in Object and null types */
        public static final ClassSymbol nullType = new ClassSymbol("<null>");
        public static final ClassSymbol objectType = new ClassSymbol("Object");

        public ClassSymbol(Ast.ClassDecl ast) {
            super(ast.name);
            this.ast = ast;
        }

        /** Used to create the default {@code Object}
         *  and {@code <null>} types */
        public ClassSymbol(String name) {
            super(name);
            this.ast = null;
        }

        public boolean isReferenceType() {
            return true;
        }

        @Override
        public boolean isSubtypeOf(TypeSymbol type) {
            // TODO this should return true when the types are equal.
            throw new ToDoException(); //TODO
        }

        public VariableSymbol getField(String name) {
            VariableSymbol fsym = fields.get(name);
            if (fsym == null && superClass != null)
                return superClass.getField(name);
            return fsym;
        }

        public MethodSymbol getMethod(String name) {
            MethodSymbol msym = methods.get(name);
            if (msym == null && superClass != null)
                return superClass.getMethod(name);
            return msym;
        }
    }

    public static class MethodSymbol extends Symbol {

        public final Ast.MethodDecl ast;
        public final Map<String, VariableSymbol> locals = new HashMap<>();
        public final List<VariableSymbol> parameters = new ArrayList<>();

        public TypeSymbol returnType;

        public MethodSymbol(Ast.MethodDecl ast) {
            super(ast.name);
            this.ast = ast;
        }

        public String toString() {
            return name + "(...)";
        }
    }

    public static class VariableSymbol extends Symbol {

        public static enum Kind { PARAM, LOCAL, FIELD };
        public final TypeSymbol type;
        public final Kind kind;

        public VariableSymbol(String name, TypeSymbol type) {
            this(name, type, Kind.PARAM);
        }

        public VariableSymbol(String name, TypeSymbol type, Kind kind) {
            super(name);
            this.type = type;
            this.kind = kind;
        }

        public String toString() {
            return name;
        }
    }

    protected Symbol(String name) {
        this.name = name;
    }

}
