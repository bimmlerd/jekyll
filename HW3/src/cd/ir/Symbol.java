package cd.ir;

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

        public abstract boolean isArrayType();

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
        public boolean isArrayType() {
            return false;
        }

        @Override
        public boolean isSubtypeOf(TypeSymbol type) {
            // this currently returns true when the types are equal.
            return type == this;
        }
    }

    public static class ArrayTypeSymbol extends TypeSymbol {
        public final TypeSymbol elementType;

        @Override
        public boolean isSubtypeOf(TypeSymbol type) {
            return type == ClassSymbol.objectType;
        }

        public ArrayTypeSymbol(TypeSymbol elementType) {
            super(elementType.name + "[]");
            this.elementType = elementType;
        }

        public boolean isReferenceType() {
            return true;
        }

        @Override
        public boolean isArrayType() {
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
        public boolean isArrayType() {
            return false;
        }

        @Override
        public boolean isSubtypeOf(TypeSymbol type) {
            // this also returns true when the types are equal.
            if (this.equals(ClassSymbol.nullType) || type.equals(ClassSymbol.objectType)) {
                return true;
            }
            ClassSymbol current = this;
            while (current.superClass != null) {
                if (current.equals(type)) {
                    return true;
                }
                current = current.superClass;
            }
            return false;
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
