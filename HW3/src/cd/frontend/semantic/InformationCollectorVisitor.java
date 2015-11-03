package cd.frontend.semantic;

import cd.ir.Ast;
import cd.ir.AstVisitor;
import cd.ir.Symbol;

import java.util.List;

public class InformationCollectorVisitor extends AstVisitor<Symbol, Symbol.VariableSymbol.Kind> {

    private final SymbolManager sm;

    public InformationCollectorVisitor(SymbolManager symbolManager) {
        this.sm = symbolManager;
    }

    @Override
    public Symbol classDecl(Ast.ClassDecl ast, Symbol.VariableSymbol.Kind arg) {

        // Symbol for our current classDecl already exists.

        // Add superClass to our ClassSymbol. Handle undefined types.
        Symbol.TypeSymbol typeSymbol = sm.get(ast.superClass);
        if (typeSymbol == null) {
            // The type name does neither refer to a class nor to a predefined type.
            throw new SemanticFailure(SemanticFailure.Cause.NO_SUCH_TYPE,
                    "Type not found: %s", ast.superClass);
        }
        ast.sym.superClass = (Symbol.ClassSymbol) typeSymbol;

        // Visit all fields in our current class and add them to our ClassSymbol. Handle duplicate field declarations.
        for (Ast.VarDecl varDecl : ast.fields()) {
            if (ast.sym.fields.containsKey(varDecl.name)) {
                // Two fields with the same name.
                throw new SemanticFailure(SemanticFailure.Cause.DOUBLE_DECLARATION,
                        "Already declared: Field %s", varDecl.name);
            } else {
                Symbol.VariableSymbol field = (Symbol.VariableSymbol) visit(varDecl, Symbol.VariableSymbol.Kind.FIELD);
                ast.sym.fields.put(field.name, field);
            }
        }

        // Visit all methods in our current class and add them to our ClassSymbol. Handle duplicate method declarations.
        for (Ast.MethodDecl methodDecl : ast.methods()) {
            if (ast.sym.methods.containsKey(methodDecl.name)) {
                // Two methods with the same name.
                throw new SemanticFailure(SemanticFailure.Cause.DOUBLE_DECLARATION,
                        "Already declared: Method %s", methodDecl.name);
            } else {
                Symbol.MethodSymbol method = (Symbol.MethodSymbol) visit(methodDecl, null);
                ast.sym.methods.put(method.name, method);
            }
        }

        return ast.sym;
    }

    @Override
    public Symbol methodDecl(Ast.MethodDecl ast, Symbol.VariableSymbol.Kind arg) {

        // Create symbol for our current methodDecl.
        ast.sym = new Symbol.MethodSymbol(ast);

        // Visit all local variables in our current method add them to our MethodSymbol. Handle duplicate local variable declarations.
        for (Ast decl : ast.decls().rwChildren()) {
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
            if (nameContainedInList(ast.argumentNames.get(i), ast.sym.parameters)) {
                // Two parameters with the same name.
                throw new SemanticFailure(SemanticFailure.Cause.DOUBLE_DECLARATION,
                        "Already declared: Parameter %s", ast.argumentNames.get(i));
            } else if (ast.sym.locals.containsKey(ast.argumentNames.get(i))) {
                // A parameter with the same name as a local variable.
                throw new SemanticFailure(SemanticFailure.Cause.DOUBLE_DECLARATION,
                        "Already declared as local variable: Parameter %s", ast.argumentNames.get(i));
            } else {
                // Handle undefined types.
                Symbol.TypeSymbol typeSymbol = sm.get(ast.argumentTypes.get(i));
                if (typeSymbol == null) {
                    // The type name does neither refer to a class nor to a predefined type.
                    throw new SemanticFailure(SemanticFailure.Cause.NO_SUCH_TYPE,
                            "Type not found: %s", ast.argumentTypes.get(i));
                }
                // Create symbol for our current parameter.
                Symbol.VariableSymbol variableSymbol = new Symbol.VariableSymbol(ast.argumentNames.get(i), typeSymbol, Symbol.VariableSymbol.Kind.PARAM);
                ast.sym.parameters.add(variableSymbol);
            }
        }

        // Add returnType to our MethodSymbol. Handle undefined types.
        Symbol.TypeSymbol typeSymbol = sm.get(ast.returnType);
        if (typeSymbol == null) {
            // The type name does neither refer to a class nor to a predefined type.
            throw new SemanticFailure(SemanticFailure.Cause.NO_SUCH_TYPE,
                    "Type not found: %s", ast.returnType);
        }
        ast.sym.returnType = typeSymbol;

        return ast.sym;
    }

    @Override
    public Symbol varDecl(Ast.VarDecl ast, Symbol.VariableSymbol.Kind arg) {

        // Handle undefined types.
        Symbol.TypeSymbol typeSymbol = sm.get(ast.type);
        if (typeSymbol == null) {
            // The type name does neither refer to a class nor to a predefined type.
            throw new SemanticFailure(SemanticFailure.Cause.NO_SUCH_TYPE,
                    "Type not found: %s", ast.type);
        }
        // Create symbol for our current varDecl.
        ast.sym = new Symbol.VariableSymbol(ast.name, typeSymbol, arg);

        return ast.sym;
    }

    private boolean nameContainedInList(String name, List<Symbol.VariableSymbol> list) {
        for (Symbol.VariableSymbol variableSymbol : list) {
            if (variableSymbol.name.equals(name)) {
                return true;
            }
        }
        return false;
    }

}
