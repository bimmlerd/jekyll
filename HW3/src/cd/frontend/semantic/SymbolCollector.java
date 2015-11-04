package cd.frontend.semantic;

import cd.ir.Ast;
import cd.ir.AstVisitor;
import cd.ir.Symbol;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Fills the given symbol manager with all symbols we can find
 */
public class SymbolCollector {
    public SymbolCollector(SymbolManager symbolManager) {
        this.sm = symbolManager;
    }

    private final SymbolManager sm;
    private Set<String> declaredClassNames = new HashSet<>();

    /**
     * Fills all type symbols into the symbol manager, but doesn't check for valid inheritance.
     *
     * @param classDecls list of class declarations
     */
    public void fillSymbolManager(List<Ast.ClassDecl> classDecls) {

        // Add built-in primitive types.
        sm.put(Symbol.TypeSymbol.PrimitiveTypeSymbol.intType);
        sm.put(new Symbol.ArrayTypeSymbol(Symbol.TypeSymbol.PrimitiveTypeSymbol.intType));
        sm.put(Symbol.TypeSymbol.PrimitiveTypeSymbol.voidType); // no void[]
        sm.put(Symbol.TypeSymbol.PrimitiveTypeSymbol.booleanType);
        sm.put(new Symbol.ArrayTypeSymbol(Symbol.TypeSymbol.PrimitiveTypeSymbol.booleanType));

        // Add basic reference types.
        sm.put(Symbol.TypeSymbol.ClassSymbol.nullType); // TODO null[] ??
        sm.put(Symbol.TypeSymbol.ClassSymbol.objectType);
        sm.put(new Symbol.ArrayTypeSymbol(Symbol.TypeSymbol.ClassSymbol.objectType));

        for (Ast.ClassDecl classDecl : classDecls) {
            if (classDecl.name.equals(Symbol.ClassSymbol.objectType.name)) {
                // A class with the name Object is defined.
                throw new SemanticFailure(SemanticFailure.Cause.OBJECT_CLASS_DEFINED);
            }

            if (declaredClassNames.contains(classDecl.name)) {
                // Two classes with the same name.
                throw new SemanticFailure(SemanticFailure.Cause.DOUBLE_DECLARATION,
                        "Already declared: Class %s", classDecl.name);
            }
            declaredClassNames.add(classDecl.name);

            Symbol.ClassSymbol sym = new Symbol.ClassSymbol(classDecl);
            classDecl.sym = sym;
            sm.put(sym);
            sm.put(new Symbol.ArrayTypeSymbol(sym)); // also put class[] into the symbol manager
        }

        InformationCollectorVisitor visitor = new InformationCollectorVisitor();
        for (Ast.ClassDecl classDecl : classDecls) {
            visitor.visit(classDecl, null);
        }
    }

    protected class InformationCollectorVisitor extends AstVisitor<Symbol, Symbol.VariableSymbol.Kind> {
        /**
         * Adds superclass to class symbols and visits fields and methods.
         *
         * @param ast the ClassDecl
         * @param arg Visitor argument (unused)
         * @return the updated class symbol
         */
        @Override
        public Symbol classDecl(Ast.ClassDecl ast, Symbol.VariableSymbol.Kind arg) {

            // Symbol for our current classDecl already exists.

            // set superclass without checking for circular inheritance, that's the job of the inheritance checker
            ast.sym.superClass = (Symbol.ClassSymbol) getType(ast.superClass);

            // Visit all fields in our current class and add them to our ClassSymbol. Handle duplicate field declarations.
            for (Ast.VarDecl varDecl : ast.fields()) {
                if (ast.sym.fields.containsKey(varDecl.name)) {
                    // Two fields with the same name.
                    throw new SemanticFailure(SemanticFailure.Cause.DOUBLE_DECLARATION,
                            "Already declared: Field %s", varDecl.name);
                }
                Symbol.VariableSymbol field = (Symbol.VariableSymbol) visit(varDecl, Symbol.VariableSymbol.Kind.FIELD);
                ast.sym.fields.put(field.name, field);
            }

            // Visit all methods in our current class and add them to our ClassSymbol. Handle duplicate method declarations.
            for (Ast.MethodDecl methodDecl : ast.methods()) {
                if (ast.sym.methods.containsKey(methodDecl.name)) {
                    // Two methods with the same name.
                    throw new SemanticFailure(SemanticFailure.Cause.DOUBLE_DECLARATION,
                            "Already declared: Method %s", methodDecl.name);
                }
                Symbol.MethodSymbol method = (Symbol.MethodSymbol) visit(methodDecl, null);
                ast.sym.methods.put(method.name, method);
            }

            return ast.sym;
        }

        /**
         * Creates a MethodSymbol for the visited MethodDecl and adds it to the AST.
         * Fills in locals and parameters via visiting.
         *
         * @param ast the MethodDecl
         * @param arg Visitor Argument (unused)
         * @return the created methodSymbol
         */
        @Override
        public Symbol methodDecl(Ast.MethodDecl ast, Symbol.VariableSymbol.Kind arg) {

            // Create symbol for our current methodDecl.
            ast.sym = new Symbol.MethodSymbol(ast);

            // Visit all local variables in our current method add them to our MethodSymbol. Handle duplicate local variable declarations.
            for (Ast decl : ast.decls().rwChildren()) {
                // check for nested methods would go somewhere here, but the parser already sorts those out
                Ast.VarDecl varDecl = (Ast.VarDecl) decl;
                if (ast.sym.locals.containsKey(varDecl.name)) {
                    // Two local variables with the same name.
                    throw new SemanticFailure(SemanticFailure.Cause.DOUBLE_DECLARATION,
                            "Already declared: Local Variable %s", varDecl.name);
                } else {
                    Symbol.VariableSymbol local = (Symbol.VariableSymbol) visit(varDecl, Symbol.VariableSymbol.Kind.LOCAL);
                    ast.sym.locals.put(local.name, local);
                }
            }

            // Visit all parameters in our current method and add them to our MethodSymbol. Handle duplicate parameter declarations.
            for (int i = 0; i < ast.argumentNames.size(); i++) {
                String argName = ast.argumentNames.get(i);
                String argType = ast.argumentTypes.get(i);

                if (ast.sym.parameters.stream().anyMatch(p -> argName.equals(p.name))) {
                    // Two parameters with the same name.
                    throw new SemanticFailure(SemanticFailure.Cause.DOUBLE_DECLARATION,
                            "Already declared: Parameter %s", argName);
                }

                if (ast.sym.locals.containsKey(argName)) {
                    // A parameter with the same name as a local variable.
                    throw new SemanticFailure(SemanticFailure.Cause.DOUBLE_DECLARATION,
                            "Already declared as local variable: Parameter %s", argName);
                }

                Symbol.TypeSymbol typeSymbol = getType(argType);
                // Create symbol for our current parameter.
                Symbol.VariableSymbol variableSymbol =
                        new Symbol.VariableSymbol(argName, typeSymbol, Symbol.VariableSymbol.Kind.PARAM);
                ast.sym.parameters.add(variableSymbol);
            }

            // Add returnType to our MethodSymbol.
            ast.sym.returnType = getType(ast.returnType);
            return ast.sym;
        }

        /**
         * Creates a symbol for visited varDecl and adds it to the AST.
         *
         * @param ast The varDecl
         * @param arg Visitor argument
         * @return returns the newly created VariableSymbol
         */
        @Override
        public Symbol varDecl(Ast.VarDecl ast, Symbol.VariableSymbol.Kind arg) {
            Symbol.TypeSymbol typeSymbol = getType(ast.type);
            ast.sym = new Symbol.VariableSymbol(ast.name, typeSymbol, arg);
            return ast.sym;
        }

        /**
         * @implNote Takes care of undefined types.
         * @param type String name of type
         * @return TypeSymbol of requested type.
         */
        private Symbol.TypeSymbol getType(String type) {
            Symbol.TypeSymbol typeSymbol = sm.get(type);
            if (typeSymbol == null) {
                throw new SemanticFailure(SemanticFailure.Cause.NO_SUCH_TYPE,
                        "Type not found: %s", type);
            }
            return typeSymbol;
        }

    }

}
