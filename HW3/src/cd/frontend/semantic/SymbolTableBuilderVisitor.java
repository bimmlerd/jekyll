package cd.frontend.semantic;

import cd.ir.Ast;
import cd.ir.AstVisitor;

public class SymbolTableBuilderVisitor extends AstVisitor<Void, Void> {

    private final SemanticAnalyzer sa;

    public SymbolTableBuilderVisitor(SemanticAnalyzer semanticAnalyzer) {
        this.sa = semanticAnalyzer;
    }

    @Override
    public Void classDecl(Ast.ClassDecl ast, Void arg) {
        // visit method and field decls
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
