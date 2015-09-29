package cd.backend.codegen;

import cd.ToDoException;
import cd.ir.Ast;
import cd.ir.ExprVisitor;

public class CounterVisitor extends ExprVisitor<Integer, Void> {

    @Override
    public Integer visit(Ast.Expr ast, Void arg) {
        return super.visit(ast, arg);
    }

    @Override
    public Integer visitChildren(Ast.Expr ast, Void arg) {
        return super.visitChildren(ast, arg);
    }

    @Override
    protected Integer dfltExpr(Ast.Expr ast, Void arg) {
        throw new ToDoException();
    }

    @Override
    public Integer binaryOp(Ast.BinaryOp ast, Void arg) {
        Ast.Expr l = (Ast.Expr) ast.rwChildren.get(0);
        Ast.Expr r = (Ast.Expr) ast.rwChildren.get(1);
        if (visit(l, null).equals(visit(r, null))) {
            ast.registersUsed = l.registersUsed + 1;
        } else {
            ast.registersUsed = Integer.max(r.registersUsed, l.registersUsed);
        }
        return ast.registersUsed;
    }

    @Override
    public Integer booleanConst(Ast.BooleanConst ast, Void arg) {
        throw new RuntimeException("Not required");
    }

    @Override
    public Integer builtInRead(Ast.BuiltInRead ast, Void arg) {
        ast.registersUsed = 0;
        return ast.registersUsed;
    }

    @Override
    public Integer cast(Ast.Cast ast, Void arg) {
        throw new RuntimeException("Not required");
    }

    @Override
    public Integer field(Ast.Field ast, Void arg) {
        throw new RuntimeException("Not required");
    }

    @Override
    public Integer index(Ast.Index ast, Void arg) {
        throw new RuntimeException("Not required");
    }

    @Override
    public Integer intConst(Ast.IntConst ast, Void arg) {
        ast.registersUsed = 1;
        return ast.registersUsed;
    }

    @Override
    public Integer methodCall(Ast.MethodCallExpr ast, Void arg) {
        throw new RuntimeException("Not required");
    }

    @Override
    public Integer newObject(Ast.NewObject ast, Void arg) {
        throw new RuntimeException("Not required");
    }

    @Override
    public Integer newArray(Ast.NewArray ast, Void arg) {
        throw new RuntimeException("Not required");
    }

    @Override
    public Integer nullConst(Ast.NullConst ast, Void arg) {
        throw new RuntimeException("Not required");
    }

    @Override
    public Integer thisRef(Ast.ThisRef ast, Void arg) {
        throw new RuntimeException("Not required");
    }

    @Override
    public Integer unaryOp(Ast.UnaryOp ast, Void arg) {
        ast.registersUsed = ((Ast.Expr) ast.rwChildren.get(0)).registersUsed;
        return ast.registersUsed;
    }

    @Override
    public Integer var(Ast.Var ast, Void arg) {
        ast.registersUsed = 1;
        return ast.registersUsed;
    }
}
