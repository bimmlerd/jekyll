package cd.frontend.semantic;

import cd.ir.Ast;
import cd.ir.AstVisitor;
import cd.ir.Symbol;

public class SymbolTableBuilderVisitor extends AstVisitor<Void, Void> {

    private SymbolTable<Symbol.ClassSymbol> classSymbolTable;

    public SymbolTableBuilderVisitor(SymbolTable<Symbol.ClassSymbol> classSymbolTable) {
        this.classSymbolTable = classSymbolTable;
    }

    @Override
    public Void classDecl(Ast.ClassDecl ast, Void arg) {
        return super.classDecl(ast, arg);
    }

    @Override
    public Void methodDecl(Ast.MethodDecl ast, Void arg) {
        return super.methodDecl(ast, arg);
    }

    @Override
    public Void varDecl(Ast.VarDecl ast, Void arg) {
        return super.varDecl(ast, arg);
    }
}
