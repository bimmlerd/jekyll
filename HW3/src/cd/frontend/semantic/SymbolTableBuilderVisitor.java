package cd.frontend.semantic;

import cd.ir.Ast;
import cd.ir.AstVisitor;
import cd.ir.Symbol;

import java.util.List;

public class SymbolTableBuilderVisitor extends AstVisitor<Void, Void> {

    private final SemanticAnalyzer sa;
    private SymbolTable<? extends Symbol> classScope;
    private SymbolTable<? extends Symbol> fieldScope;
    private SymbolTable<? extends Symbol> methodScope;

    public SymbolTableBuilderVisitor(SemanticAnalyzer semanticAnalyzer) {
        this.sa = semanticAnalyzer;
    }

    @Override
    public Void classDecl(Ast.ClassDecl ast, Void arg) {
        // visit method and field decls
        classScope = this.sa.classSymbolTable;
/*
        ast.sym.fields.setOuterScope(classScope);
        fieldScope = ast.sym.fields;
        ast.sym.methods.setOuterScope(classScope);
        methodScope = ast.sym.methods;
*/
        visitChildren(ast, arg);
        classScope = null;
        fieldScope = null;
        methodScope = null;
        return null;
    }

    @Override
    public Void methodDecl(Ast.MethodDecl ast, Void arg) {

        ast.sym = new Symbol.MethodSymbol(ast);
/*
        ast.sym.locals.setOuterScope(methodScope);
*/
        for (Ast decl : ast.decls().rwChildren()) {
            Ast.VarDecl varDecl = (Ast.VarDecl) decl;
            Symbol.VariableSymbol sym = new Symbol.VariableSymbol(varDecl.name, this.sa.typeSymbolTable.get(varDecl.type));
/*
            ast.sym.locals.put(sym);
*/
        }

        // TODO parameters?
        return super.methodDecl(ast, arg);
    }

    @Override
    public Void varDecl(Ast.VarDecl ast, Void arg) {
        return super.varDecl(ast, arg);
    }
}
