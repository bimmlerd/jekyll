package cd.frontend.semantic;

import cd.ToDoException;
import cd.ir.Ast;
import cd.ir.ExprVisitor;
import cd.ir.Symbol;

/**
 * Checks for the following type violations:
 *
 * TODO:
 *  - erroneous method invocations
 *  - missing returns?
 *  - invalid binary operations
 *  - invalid unary operations
 *  - ...
 */
public class TypeChecker {

    private final SymbolManager sm;

    public TypeChecker(SymbolManager symbolManager) {
        this.sm = symbolManager;
    }

    public void check() {

        //
        for (Symbol.ClassSymbol classSymbol : sm.getClassSymbols()) {

        }

    }

    /**
     * The expression typer visitor returns the inferred type symbol of the expression it visits
     */
    protected class ExpressionTypingVisitor extends ExprVisitor<Symbol.TypeSymbol, Void> {

        private void assertTypeEquality(Symbol.TypeSymbol a, Symbol.TypeSymbol b) {
            if (!a.equals(b)) {
                throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "%s type expected, got %s", b, a);
            }
        }

        private void assertSubtype(Symbol.TypeSymbol type, Symbol.TypeSymbol subType) {
            if (!subType.isSubtypeOf(type)) {
                throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "%s is not a subtype of %s", subType, type);
            }
        }

        private void assertBoolean(Symbol.TypeSymbol type) { assertTypeEquality(type, Symbol.PrimitiveTypeSymbol.booleanType); }

        private void assertInteger(Symbol.TypeSymbol type) { assertTypeEquality(type, Symbol.PrimitiveTypeSymbol.intType); }

        @Override
        public Symbol.TypeSymbol unaryOp(Ast.UnaryOp ast, Void arg) {
            switch (ast.operator) {
                // int -> int
                case U_MINUS:
                case U_PLUS:
                    assertInteger(visit(ast.arg(), null));
                    return Symbol.PrimitiveTypeSymbol.intType;

                // bool -> bool
                case U_BOOL_NOT:
                    assertBoolean(visit(ast.arg(), null));
                    return Symbol.PrimitiveTypeSymbol.booleanType;

                default:
                    throw new ToDoException();
            }
        }

        @Override
        public Symbol.TypeSymbol binaryOp(Ast.BinaryOp ast, Void arg) {
            switch (ast.operator) {
                // int op int -> int
                case B_PLUS:
                case B_MINUS:
                case B_DIV:
                case B_TIMES:
                case B_MOD:
                    assertInteger(visit(ast.left(), null));
                    assertInteger(visit(ast.right(), null));
                    return Symbol.PrimitiveTypeSymbol.intType;

                // int op int -> bool
                case B_GREATER_OR_EQUAL:
                case B_GREATER_THAN:
                case B_LESS_THAN:
                case B_LESS_OR_EQUAL:
                    assertInteger(visit(ast.left(), null));
                    assertInteger(visit(ast.right(), null));
                    return Symbol.PrimitiveTypeSymbol.booleanType;

                // bool op bool -> bool
                case B_OR:
                case B_AND:
                    assertBoolean(visit(ast.left(), null));
                    assertBoolean(visit(ast.right(), null));
                    return Symbol.PrimitiveTypeSymbol.booleanType;

                // type op type -> bool
                case B_EQUAL:
                case B_NOT_EQUAL:
                    assertTypeEquality(visit(ast.left(), null), visit(ast.right(), null));
                    return Symbol.PrimitiveTypeSymbol.booleanType;

                default:
                    throw new ToDoException();
            }
        }

        @Override
        public Symbol.TypeSymbol booleanConst(Ast.BooleanConst ast, Void arg) { return Symbol.PrimitiveTypeSymbol.booleanType; }

        @Override
        public Symbol.TypeSymbol intConst(Ast.IntConst ast, Void arg) { return Symbol.PrimitiveTypeSymbol.intType; }

        @Override
        public Symbol.TypeSymbol builtInRead(Ast.BuiltInRead ast, Void arg) { return Symbol.PrimitiveTypeSymbol.intType; }

        @Override
        public Symbol.TypeSymbol cast(Ast.Cast ast, Void arg) {
            assertSubtype(visit(ast, null), sm.get(ast.typeName));
            return super.cast(ast, arg);
        }

        @Override
        public Symbol.TypeSymbol field(Ast.Field ast, Void arg) {
            return super.field(ast, arg);
        }

        @Override
        public Symbol.TypeSymbol index(Ast.Index ast, Void arg) {
            return super.index(ast, arg);
        }

        @Override
        public Symbol.TypeSymbol methodCall(Ast.MethodCallExpr ast, Void arg) {
            return super.methodCall(ast, arg);
        }

        @Override
        public Symbol.TypeSymbol newObject(Ast.NewObject ast, Void arg) {
            return super.newObject(ast, arg);
        }

        @Override
        public Symbol.TypeSymbol newArray(Ast.NewArray ast, Void arg) {
            return super.newArray(ast, arg);
        }

        @Override
        public Symbol.TypeSymbol nullConst(Ast.NullConst ast, Void arg) {
            return super.nullConst(ast, arg);
        }

        @Override
        public Symbol.TypeSymbol thisRef(Ast.ThisRef ast, Void arg) {
            return super.thisRef(ast, arg);
        }

        @Override
        public Symbol.TypeSymbol var(Ast.Var ast, Void arg) {
            return super.var(ast, arg);
        }
    }
}
