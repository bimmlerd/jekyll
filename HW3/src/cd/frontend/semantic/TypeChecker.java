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

/**
 * Checks the type of all statements and expressions
 */
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

    private void assertRelatedType(Symbol.TypeSymbol a, Symbol.TypeSymbol b) {
        if (!(a.isSubtypeOf(b) || b.isSubtypeOf(a))) {
            throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR, "Types %s and %s are not related", a, b);
        }
    }

    /**
     * The statement type checker visitor throws a semantic failure whenever a typing error is detected
     */
    protected class StatementTypeCheckerVisitor extends AstVisitor<Void, Void> {

        private final ExpressionTypingVisitor ev = new ExpressionTypingVisitor();
        private final Map<String, Symbol.MethodSymbol> methods;
        private final SymbolTable<Symbol.VariableSymbol> classScope;
        private SymbolTable<Symbol.VariableSymbol> localScope;
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

            Symbol.TypeSymbol leftType = ev.visit(ast.left(), localScope);
            Symbol.TypeSymbol rightType = ev.visit(ast.right(), localScope);

            assertSubtype(leftType, rightType);

            return arg;
        }

        @Override
        public Void builtInWrite(Ast.BuiltInWrite ast, Void arg) {
            assertInteger(ev.visit(ast.arg(), localScope));
            return arg;
        }

        @Override
        public Void methodDecl(Ast.MethodDecl ast, Void arg) {
            currentMethod = methods.get(ast.name);
            localScope = new SymbolTable<>(classScope);

            currentMethod.parameters.forEach(localScope::put);
            currentMethod.locals.values().forEach(localScope::put);

            Void res = visitChildren(ast, null);

            currentMethod = null;
            localScope = null;
            return res;
        }

        @Override
        public Void ifElse(Ast.IfElse ast, Void arg) {
            assertBoolean(ev.visit(ast.condition(), localScope));
            visit(ast.then(), arg);
            return visit(ast.otherwise(), arg);
        }

        @Override
        public Void returnStmt(Ast.ReturnStmt ast, Void arg) {
            if (!(ast.arg() == null)) {
                ast.arg().type = ev.visit(ast.arg(), localScope);
                assertSubtype(currentMethod.returnType, ast.arg().type);
            } else {
                assertTypeEquality(currentMethod.returnType, Symbol.PrimitiveTypeSymbol.voidType);
            }
            return arg;
        }

        @Override
        public Void methodCall(Ast.MethodCall ast, Void arg) {
            ev.visit(ast.getMethodCallExpr(), localScope);
            return arg;
        }

        @Override
        public Void whileLoop(Ast.WhileLoop ast, Void arg) {
            assertBoolean(ev.visit(ast.condition(), localScope));
            return visit(ast.body(), arg);
        }
    }

    /**
     * The expression typing visitor returns the inferred type symbol of the expression it visits
     */
    protected class ExpressionTypingVisitor extends ExprVisitor<Symbol.TypeSymbol,SymbolTable<Symbol.VariableSymbol>> {

        @Override
        public Symbol.TypeSymbol unaryOp(Ast.UnaryOp ast, SymbolTable<Symbol.VariableSymbol> localScope) {
            ast.type = visit(ast.arg(), localScope);
            switch (ast.operator) {
                // int -> int
                case U_MINUS:
                case U_PLUS:
                    assertInteger(ast.type);
                    break;

                // bool -> bool
                case U_BOOL_NOT:
                    assertBoolean(ast.type);
                    break;

                default:
                    throw new ToDoException();
            }
            return ast.type;
        }

        @Override
        public Symbol.TypeSymbol binaryOp(Ast.BinaryOp ast, SymbolTable<Symbol.VariableSymbol> localScope) {
            Symbol.TypeSymbol leftType = visit(ast.left(), localScope);
            Symbol.TypeSymbol rightType = visit(ast.right(), localScope);
            switch (ast.operator) {
                // int op int -> int
                case B_PLUS:
                case B_MINUS:
                case B_DIV:
                case B_TIMES:
                case B_MOD:
                    assertInteger(leftType);
                    assertInteger(rightType);
                    ast.type = Symbol.PrimitiveTypeSymbol.intType;
                    break;

                // int op int -> bool
                case B_GREATER_OR_EQUAL:
                case B_GREATER_THAN:
                case B_LESS_THAN:
                case B_LESS_OR_EQUAL:
                    assertInteger(leftType);
                    assertInteger(rightType);
                    ast.type = Symbol.PrimitiveTypeSymbol.booleanType;
                    break;

                // bool op bool -> bool
                case B_OR:
                case B_AND:
                    assertBoolean(leftType);
                    assertBoolean(rightType);
                    ast.type = Symbol.PrimitiveTypeSymbol.booleanType;
                    break;

                // type op type -> bool
                case B_EQUAL:
                case B_NOT_EQUAL:
                    assertRelatedType(leftType, rightType);
                    ast.type = Symbol.PrimitiveTypeSymbol.booleanType;
                    break;

                default:
                    throw new ToDoException();
            }
            return ast.type;
        }

        @Override
        public Symbol.TypeSymbol booleanConst(Ast.BooleanConst ast, SymbolTable<Symbol.VariableSymbol> localScope) {
            ast.type = Symbol.PrimitiveTypeSymbol.booleanType;
            return ast.type;
        }

        @Override
        public Symbol.TypeSymbol intConst(Ast.IntConst ast, SymbolTable<Symbol.VariableSymbol> localScope) {
            ast.type = Symbol.PrimitiveTypeSymbol.intType;
            return ast.type;
        }

        @Override
        public Symbol.TypeSymbol builtInRead(Ast.BuiltInRead ast, SymbolTable<Symbol.VariableSymbol> localScope) {
            ast.type = Symbol.PrimitiveTypeSymbol.intType;
            return ast.type;
        }

        @Override
        public Symbol.TypeSymbol cast(Ast.Cast ast, SymbolTable<Symbol.VariableSymbol> localScope) {
            ast.originalType = visit(ast.arg(), localScope);
            ast.type = getType(ast.typeName);
            assertRelatedType(ast.originalType, ast.type);
            return ast.type;
        }

        @Override
        public Symbol.TypeSymbol field(Ast.Field ast, SymbolTable<Symbol.VariableSymbol> localScope) {
            Symbol.TypeSymbol classType = visit(ast.arg(), localScope);
            if (!(classType instanceof Symbol.ClassSymbol)) {
                throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR);
            }
            ast.sym = ((Symbol.ClassSymbol) classType).getField(ast.fieldName);
            if (ast.sym == null) {
                throw new SemanticFailure(SemanticFailure.Cause.NO_SUCH_FIELD,
                        "Can't find field %s anywhere, must've misplaced it. Sorry.", ast.fieldName);
            }
            ast.type = ast.sym.type;
            return ast.type;
        }

        @Override
        public Symbol.TypeSymbol index(Ast.Index ast, SymbolTable<Symbol.VariableSymbol> localScope) {
            assertInteger(visit(ast.right(), localScope));
            Symbol.TypeSymbol leftType = visit(ast.left(), localScope);
            assertArray(leftType);
            ast.type = ((Symbol.ArrayTypeSymbol) leftType).elementType;
            return ast.type;
        }

        @Override
        public Symbol.TypeSymbol methodCall(Ast.MethodCallExpr ast, SymbolTable<Symbol.VariableSymbol> localScope) {
            Symbol.TypeSymbol classType = visit(ast.receiver(), localScope);
            if (! (classType instanceof Symbol.ClassSymbol)) {
                throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR,
                        "Expected a class but got %s", classType.name);
            }

            ast.sym = ((Symbol.ClassSymbol) classType).getMethod(ast.methodName);
            if (ast.sym == null) {
                throw new SemanticFailure(SemanticFailure.Cause.NO_SUCH_METHOD,
                        "No method %s on type %s", ast.methodName, classType);
            }

            List<Symbol.TypeSymbol> argTypes = ast.argumentsWithoutReceiver().stream()
                    .map((a)->(visit(a, localScope)))
                    .collect(Collectors.toList());
            List<Symbol.TypeSymbol> paramTypes = ast.sym.parameters.stream()
                    .map((a)->(a.type))
                    .collect(Collectors.toList());

            if (argTypes.size() != paramTypes.size()) {
                throw new SemanticFailure(SemanticFailure.Cause.WRONG_NUMBER_OF_ARGUMENTS,
                        "Wrong number of arguments for %s", ast.methodName);
            // verify formal parameter types match actual argument types
            } else if (!argTypes.isEmpty() &&
                    !Pair.zip(argTypes, paramTypes).stream()
                    .map((p) -> (p.a.isSubtypeOf(p.b)))
                    .reduce(Boolean::logicalAnd).get()) {
                throw new SemanticFailure(SemanticFailure.Cause.TYPE_ERROR,
                        "Types of formal parameters of %s don't match arguments", ast.methodName);
            }
            ast.type = ast.sym.returnType;
            return ast.type;
        }

        @Override
        public Symbol.TypeSymbol newObject(Ast.NewObject ast, SymbolTable<Symbol.VariableSymbol> localScope) {
            ast.type = getType(ast.typeName);
            return ast.type;
        }

        @Override
        public Symbol.TypeSymbol newArray(Ast.NewArray ast, SymbolTable<Symbol.VariableSymbol> localScope) {
            assertInteger(visit(ast.arg(), localScope));
            ast.type = getType(ast.typeName);
            return ast.type;
        }

        @Override
        public Symbol.TypeSymbol nullConst(Ast.NullConst ast, SymbolTable<Symbol.VariableSymbol> localScope) {
            ast.type = Symbol.ClassSymbol.nullType;
            return ast.type;
        }

        @Override
        public Symbol.TypeSymbol thisRef(Ast.ThisRef ast, SymbolTable<Symbol.VariableSymbol> localScope) {
            ast.type = ((Symbol.VariableSymbol) localScope.get("this")).type;
            return ast.type;
        }

        @Override
        public Symbol.TypeSymbol var(Ast.Var ast, SymbolTable<Symbol.VariableSymbol> localScope) {
            ast.sym = (Symbol.VariableSymbol) localScope.get(ast.name);
            if (ast.sym == null) {
                throw new SemanticFailure(SemanticFailure.Cause.NO_SUCH_VARIABLE,
                        "Can't find variable %s anywhere, must've misplaced it. Sorry.", ast.name);
            }
            ast.type = ast.sym.type;
            return ast.type;
        }

        // Take care of undefined types.
        private Symbol.TypeSymbol getType(String type) {
            Symbol.TypeSymbol typeSymbol = (Symbol.TypeSymbol) st.get(type);
            if (typeSymbol == null) {
                throw new SemanticFailure(SemanticFailure.Cause.NO_SUCH_TYPE,
                        "Type not found: %s", type);
            }
            return typeSymbol;
        }
    }
}
