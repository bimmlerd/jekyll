package cd.frontend.semantic;

import cd.ir.Ast;
import cd.ir.AstVisitor;
import cd.ir.Symbol;

import java.util.Map;

public class InformationCollectorVisitor extends AstVisitor<Symbol, Symbol.VariableSymbol.Kind> {

    private final Map<String, Symbol.TypeSymbol> st;

    public InformationCollectorVisitor(Map<String, Symbol.TypeSymbol> globalSymbolTable) {
        this.st = globalSymbolTable;
    }

    @Override
    public Symbol classDecl(Ast.ClassDecl ast, Symbol.VariableSymbol.Kind arg) {

        // TODO: already done in SemanticAnalyzer
        ast.sym = new Symbol.ClassSymbol(ast);

        // Visit all fields in our current class and add them to our ClassSymbol.
        for (Ast.VarDecl decl : ast.fields()) {
            Symbol.VariableSymbol field = (Symbol.VariableSymbol) visit(decl, Symbol.VariableSymbol.Kind.FIELD);
            ast.sym.fields.put(field.name, field);
        }

        // Visit all methods in our current class and add them to our ClassSymbol.
        for (Ast.MethodDecl decl : ast.methods()) {
            Symbol.MethodSymbol method = (Symbol.MethodSymbol) visit(decl, null);
            ast.sym.methods.put(method.name, method);
        }

        return ast.sym;
    }

    @Override
    public Symbol methodDecl(Ast.MethodDecl ast, Symbol.VariableSymbol.Kind arg) {

        // Create symbol for our current methodDecl.
        ast.sym = new Symbol.MethodSymbol(ast);

        // Visit all local variables in our current method add them to our MethodSymbol.
        for (Ast decl : ast.decls().rwChildren()) {
            Symbol.VariableSymbol local = (Symbol.VariableSymbol) visit(decl, Symbol.VariableSymbol.Kind.LOCAL);
            ast.sym.locals.put(local.name, local);
        }

        // TODO: Visit all parameters of our current method.
        // combine ast.argumentNames and ast.argumentTypes

        return ast.sym;
    }

    @Override
    public Symbol varDecl(Ast.VarDecl ast, Symbol.VariableSymbol.Kind arg) {

        // Create symbol for our current varDecl.
        ast.sym = new Symbol.VariableSymbol(ast.name, st.get(ast.type), arg);

        return ast.sym;
    }
}
