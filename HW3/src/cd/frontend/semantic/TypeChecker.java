package cd.frontend.semantic;

import cd.ToDoException;
import cd.ir.Ast;
import cd.ir.AstVisitor;
import cd.ir.ExprVisitor;
import cd.ir.Symbol;
import cd.util.Pair;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TypeChecker {

    private final SymbolTable<Symbol.TypeSymbol> st;

    public TypeChecker(SymbolTable<Symbol.TypeSymbol> symbolTable) {
        this.st = symbolTable;
    }

    public void check(List<Ast.ClassDecl> classDecls) {

        for (Ast.ClassDecl classDecl : classDecls) {
            StatementTypeCheckerVisitor checker = new StatementTypeCheckerVisitor((Symbol.ClassSymbol) st.get(classDecl.name));
            checker.visit(classDecl, null);
        }

    }

    private void assertTypeEquality(Symbol.TypeSymbol a, Symbol.TypeSymbol b) {
        if (!a.equals(b)) {
            throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "%s type expected, got %s", b, a);
        }
    }

    private void assertSubtype(Symbol.TypeSymbol type, Symbol.TypeSymbol subType) {
        if (type == null || subType == null) {
            throw new SemanticFailure(SemanticFailure.Cause.NO_SUCH_TYPE);
        }

        if (!subType.isSubtypeOf(type)) {
            throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "%s is not a subtype of %s", subType, type);
        }
    }

    private void assertBoolean(Symbol.TypeSymbol type) {
        assertTypeEquality(type, Symbol.PrimitiveTypeSymbol.booleanType);
    }

    private void assertInteger(Symbol.TypeSymbol type) {
        assertTypeEquality(type, Symbol.PrimitiveTypeSymbol.intType);
    }

    private void assertArray(Symbol.TypeSymbol type) {
        if (!type.isArrayType()) {
            throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "%s should be an array, but isn't", type);
        }
    }

    protected class StatementTypeCheckerVisitor extends AstVisitor<Void, Void> {
        private final ExpressionTypingVisitor expressionTyper = new ExpressionTypingVisitor();
        private Map<String, Symbol.MethodSymbol> methods;
        private final SymbolTable<Symbol.VariableSymbol> classScope;
        private SymbolTable<cd.ir.Symbol.VariableSymbol> localScope;
        private Symbol.MethodSymbol currentMethod;

        public StatementTypeCheckerVisitor(Symbol.ClassSymbol classSymbol) {
            this.methods = classSymbol.methods;

            Symbol.ClassSymbol current = classSymbol;
            classScope = new SymbolTable<>();
            do {
                // put if absent to not overwrite hidden fields
                current.fields.values().forEach(classScope::putIfAbsent);

                current = current.superClass;
            } while (current != null);
            classScope.put(classSymbol.thisSymbol);
        }

        @Override
        public Void assign(Ast.Assign ast, Void arg) {
            if (!(ast.left() instanceof Ast.Var || ast.left() instanceof Ast.Index || ast.left() instanceof Ast.Field)) {
                throw new SemanticFailure(SemanticFailure.Cause.NOT_ASSIGNABLE, "%s is not assignable", ast.left());
            }

            Symbol.TypeSymbol left = expressionTyper.visit(ast.left(), localScope);
            Symbol.TypeSymbol right = expressionTyper.visit(ast.right(), localScope);



            // The two sides of an assignment statement must have identical types,
            // or the right-hand side's type must be a subtype of the left-hand side's type.
            if (!left.equals(right)) {
                assertSubtype(left, right);
            }
            return null;
        }

        @Override
        public Void builtInWrite(Ast.BuiltInWrite ast, Void arg) {
            assertInteger(expressionTyper.visit(ast.arg(), localScope));
            return null;
        }

        @Override
        public Void methodDecl(Ast.MethodDecl ast, Void arg) {
            Symbol.MethodSymbol methodSymbol = methods.get(ast.name);
            localScope = new SymbolTable<>(classScope);
            for (Symbol.VariableSymbol param : methodSymbol.parameters) {
                localScope.put(param);
            }

            for (Symbol.VariableSymbol local : methodSymbol.locals.values()) {
                localScope.put(local);
            }

            currentMethod = methodSymbol;

            Void res = visitChildren(ast, null);
            localScope = null;
            currentMethod = null;
            return res;
        }

        @Override
        public Void ifElse(Ast.IfElse ast, Void arg) {
            assertBoolean(expressionTyper.visit(ast.condition(), localScope));
            visit(ast.then(), arg);
            return visit(ast.otherwise(), arg);
        }

        @Override
        public Void returnStmt(Ast.ReturnStmt ast, Void arg) {
            if (!(ast.arg() == null)) {
                ast.arg().type = expressionTyper.visit(ast.arg(), localScope);
                assertSubtype(currentMethod.returnType, ast.arg().type);
            } else {
                assertTypeEquality(currentMethod.returnType, Symbol.PrimitiveTypeSymbol.voidType);
            }
            return null;
        }

        @Override
        public Void methodCall(Ast.MethodCall ast, Void arg) {
            Symbol.TypeSymbol returnType = expressionTyper.visit(ast.getMethodCallExpr(), localScope);
            return null;
        }

        @Override
        public Void whileLoop(Ast.WhileLoop ast, Void arg) {
            assertBoolean(expressionTyper.visit(ast.condition(), localScope));
            return visit(ast.body(), arg);
        }
    }

    /**
     * The expression typer visitor returns the inferred type symbol of the expression it visits
     */
    protected class ExpressionTypingVisitor extends ExprVisitor<Symbol.TypeSymbol,SymbolTable<Symbol.VariableSymbol>> {

        @Override
        public Symbol.TypeSymbol unaryOp(Ast.UnaryOp ast, SymbolTable<Symbol.VariableSymbol> localScope) {
            Symbol.TypeSymbol astType = visit(ast.arg(), localScope);
            switch (ast.operator) {
                // int -> int
                case U_MINUS:
                case U_PLUS:
                    assertInteger(astType);
                    return Symbol.PrimitiveTypeSymbol.intType;

                // bool -> bool
                case U_BOOL_NOT:
                    assertBoolean(astType);
                    return Symbol.PrimitiveTypeSymbol.booleanType;

                default:
                    throw new ToDoException();
            }
        }

        @Override
        public Symbol.TypeSymbol binaryOp(Ast.BinaryOp ast, SymbolTable<Symbol.VariableSymbol> localScope) {
            Symbol.TypeSymbol left = visit(ast.left(), localScope);
            Symbol.TypeSymbol right = visit(ast.right(), localScope);
            switch (ast.operator) {
                // int op int -> int
                case B_PLUS:
                case B_MINUS:
                case B_DIV:
                case B_TIMES:
                case B_MOD:
                    assertInteger(left);
                    assertInteger(right);
                    return Symbol.PrimitiveTypeSymbol.intType;

                // int op int -> bool
                case B_GREATER_OR_EQUAL:
                case B_GREATER_THAN:
                case B_LESS_THAN:
                case B_LESS_OR_EQUAL:
                    assertInteger(left);
                    assertInteger(right);
                    return Symbol.PrimitiveTypeSymbol.booleanType;

                // bool op bool -> bool
                case B_OR:
                case B_AND:
                    assertBoolean(left);
                    assertBoolean(right);
                    return Symbol.PrimitiveTypeSymbol.booleanType;

                // type op type -> bool
                case B_EQUAL:
                case B_NOT_EQUAL:
                    if (!(left.isSubtypeOf(right) || right.isSubtypeOf(left))) {
                        throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "equality check on non related types");
                    }
                    return Symbol.PrimitiveTypeSymbol.booleanType;

                default:
                    throw new ToDoException();
            }
        }

        @Override
        public Symbol.TypeSymbol booleanConst(Ast.BooleanConst ast, SymbolTable<Symbol.VariableSymbol> localScope) {
            return Symbol.PrimitiveTypeSymbol.booleanType;
        }

        @Override
        public Symbol.TypeSymbol intConst(Ast.IntConst ast, SymbolTable<Symbol.VariableSymbol> localScope) {
            return Symbol.PrimitiveTypeSymbol.intType;
        }

        @Override
        public Symbol.TypeSymbol builtInRead(Ast.BuiltInRead ast, SymbolTable<Symbol.VariableSymbol> localScope) {
            return Symbol.PrimitiveTypeSymbol.intType;
        }

        @Override
        public Symbol.TypeSymbol cast(Ast.Cast ast, SymbolTable<Symbol.VariableSymbol> localScope) {
            Symbol.TypeSymbol originalType = visit(ast.arg(), localScope);
            Symbol.TypeSymbol castedType = (Symbol.TypeSymbol) st.get(ast.typeName);
            if (castedType == null) {
                throw new SemanticFailure(SemanticFailure.Cause.NO_SUCH_TYPE, "no type called %s", ast.typeName);
            }
            if (!(originalType.isSubtypeOf(castedType) || castedType.isSubtypeOf(originalType))) {
                throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "Cannot cast from %s to %s", originalType, castedType);
            }
            return castedType;
        }

        @Override
        public Symbol.TypeSymbol field(Ast.Field ast, SymbolTable<Symbol.VariableSymbol> localScope) {
            Symbol.TypeSymbol classType = visit(ast.arg(), localScope);
            if (!(classType instanceof Symbol.ClassSymbol)) {
                throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR);
            }
            Symbol.VariableSymbol symbol = ((Symbol.ClassSymbol) classType).getField(ast.fieldName);
            if (symbol == null) {
                throw new SemanticFailure(SemanticFailure.Cause.NO_SUCH_FIELD,
                        "Can't find field %s anywhere, must've misplaced it. Sorry.", ast.fieldName);
            }
            return symbol.type;
        }

        @Override
        public Symbol.TypeSymbol index(Ast.Index ast, SymbolTable<Symbol.VariableSymbol> localScope) {
            assertInteger(visit(ast.right(), localScope));
            Symbol.TypeSymbol left = visit(ast.left(), localScope);
            assertArray(left);
            return ((Symbol.ArrayTypeSymbol) left).elementType;
        }

        @Override
        public Symbol.TypeSymbol methodCall(Ast.MethodCallExpr ast, SymbolTable<Symbol.VariableSymbol> localScope) {
            Symbol.TypeSymbol calledOn = visit(ast.receiver(), localScope);
            if (! (calledOn instanceof Symbol.ClassSymbol)) {
                throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR);
            }
            Symbol.MethodSymbol method = ((Symbol.ClassSymbol) calledOn).getMethod(ast.methodName);
            if (method == null) {
                throw new SemanticFailure(SemanticFailure.Cause.NO_SUCH_METHOD,
                        "no method %s on type %s", ast.methodName, calledOn);
            }

            List<Symbol.TypeSymbol> argTypes = ast.argumentsWithoutReceiver().stream()
                    .map((a)->(visit(a, localScope)))
                    .collect(Collectors.toList());
            List<Symbol.TypeSymbol> paramTypes = method.parameters.stream()
                    .map((a)->(a.type))
                    .collect(Collectors.toList());

            if (argTypes.size() != paramTypes.size()) {
                throw new SemanticFailure(SemanticFailure.Cause.WRONG_NUMBER_OF_ARGUMENTS,
                        "wrong number of arguments for %s", ast.methodName);
            // verify formal parameter types match actual argument types
            } else if (!Pair.zip(argTypes, paramTypes).stream()
                    .map((p) -> (p.a.isSubtypeOf(p.b)))
                    .reduce(true, (acc, t) -> (acc && t))) {
                throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR,
                        "formal params of %s don't match arguments", ast.methodName);
            }

            return method.returnType;
        }

        @Override
        public Symbol.TypeSymbol newObject(Ast.NewObject ast, SymbolTable<Symbol.VariableSymbol> localScope) {
            Symbol.TypeSymbol classSymbol = (Symbol.TypeSymbol) st.get(ast.typeName);
            if (!(classSymbol instanceof Symbol.ClassSymbol)) {
                throw new SemanticFailure(SemanticFailure.Cause.NO_SUCH_TYPE,
                        "Tried to instantiate %s which doesn't exist", ast.typeName);
            }
            return classSymbol;
        }

        @Override
        public Symbol.TypeSymbol newArray(Ast.NewArray ast, SymbolTable<Symbol.VariableSymbol> localScope) {
            assertInteger(visit(ast.arg(), localScope));
            return (Symbol.TypeSymbol) st.get(ast.typeName);
        }

        @Override
        public Symbol.TypeSymbol nullConst(Ast.NullConst ast, SymbolTable<Symbol.VariableSymbol> localScope) {
            return Symbol.ClassSymbol.nullType;
        }

        @Override
        public Symbol.TypeSymbol thisRef(Ast.ThisRef ast, SymbolTable<Symbol.VariableSymbol> localScope) {
            return ((Symbol.VariableSymbol) localScope.get("this")).type;
        }

        @Override
        public Symbol.TypeSymbol var(Ast.Var ast, SymbolTable<Symbol.VariableSymbol> localScope) {
            Symbol.VariableSymbol symbol = (Symbol.VariableSymbol) localScope.get(ast.name);
            if (symbol == null) {
                throw new SemanticFailure(SemanticFailure.Cause.NO_SUCH_VARIABLE,
                        "Can't find variable %s anywhere, must've misplaced it. Sorry.", ast.name);
            }
            return symbol.type;
        }
    }
}
