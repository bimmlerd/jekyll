package cd.frontend.parser;

import java.util.ArrayList;
import java.util.List;

import cd.ir.Ast;
import cd.ir.Ast.ClassDecl;
import cd.util.Pair;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;

public final class JavaliAstVisitor extends JavaliBaseVisitor<List<Ast>> {
	
	public List<ClassDecl> classDecls = new ArrayList<>();

	static final String OBJECT = "Object";
	static final String VOID = "void";
	static final String THIS = "this";

	public List<Ast> visitChildren(ParserRuleContext ctx) {
		List<Ast> result = new ArrayList<>();

        // Check that ctx.children is not null
        if (ctx.getChildCount() != 0) {
            for (ParseTree child : ctx.children) {
                result.addAll(visit(child));
            }
        }

		return result;
	}


// types

    @Override
    public List<Ast> visitTypePrimitive(@NotNull JavaliParser.TypePrimitiveContext ctx) {
        return visit(ctx.primitiveType());
    }

    @Override
    public List<Ast> visitTypeReference(@NotNull JavaliParser.TypeReferenceContext ctx) {
        return visit(ctx.referenceType());
    }

    @Override
    public List<Ast> visitMethodTypeType(@NotNull JavaliParser.MethodTypeTypeContext ctx) {
        List<Ast> result = new ArrayList<>();

        assert ctx.getChildCount() == 1;

        String returnType = ((Ast.VarDecl) visit(ctx.type()).get(0)).type;

        result.add(new Ast.MethodDecl(returnType, null, null, null, null, null));
        return result;
    }

    @Override
    public List<Ast> visitMethodTypeVoid(@NotNull JavaliParser.MethodTypeVoidContext ctx) {
        List<Ast> result = new ArrayList<>();

        assert ctx.getChildCount() == 1;

        result.add(new Ast.MethodDecl(VOID, null, null, null, null, null));
        return result;
    }

    @Override
    public List<Ast> visitPrimitiveType(@NotNull JavaliParser.PrimitiveTypeContext ctx) {
        List<Ast> result = new ArrayList<>();

        String type = ctx.getText();

        result.add(new Ast.VarDecl(type, null)); // We don't know the name of the variable yet
        return result;
    }

    @Override
    public List<Ast> visitReferenceTypeId(@NotNull JavaliParser.ReferenceTypeIdContext ctx) {
        List<Ast> result = new ArrayList<>();

        String type = ctx.Identifier().toString();

        result.add(new Ast.VarDecl(type, null)); // We don't know the name of the variable yet
        return result;
    }

    @Override
    public List<Ast> visitReferenceTypeAr(@NotNull JavaliParser.ReferenceTypeArContext ctx) {
        return visit(ctx.arrayType());
    }

    @Override
    public List<Ast> visitArrayTypeId(@NotNull JavaliParser.ArrayTypeIdContext ctx) {
        List<Ast> result = new ArrayList<>();

        String type = String.format("%s[]", ctx.Identifier().toString());

        result.add(new Ast.VarDecl(type, null)); // We don't know the name of the variable yet
        return result;
    }

    @Override
    public List<Ast> visitArrayTypePr(@NotNull JavaliParser.ArrayTypePrContext ctx) {
        List<Ast> result = new ArrayList<>();

        Ast.VarDecl typedVarDecl = (Ast.VarDecl) visit(ctx.primitiveType()).get(0);
        typedVarDecl.type = String.format("%s[]", typedVarDecl.type);

        result.add(typedVarDecl);
        return result;
    }


// program structure

    @Override
    public List<Ast> visitUnit(@NotNull JavaliParser.UnitContext ctx) {
        return super.visitUnit(ctx);
    }

	@Override
	public List<Ast> visitClassDecl(JavaliParser.ClassDeclContext ctx) {
        String superClass;
        List<Ast> members = new ArrayList<>();

        if (ctx.getChildCount() <= 5) {
            // ClassDecl without an Extends
            superClass = OBJECT;
        } else {
            // ClassDecl with an Extends
            superClass = ctx.Identifier(1).toString();
        }

        if (ctx.memberList().getChildCount() != 0) { // check that MemberList is non-empty before call
            members = visit(ctx.memberList());
        }

        classDecls.add(new ClassDecl(ctx.Identifier(0).toString(), superClass, members));
		return null;
	}

    @Override
    public List<Ast> visitMemberList(@NotNull JavaliParser.MemberListContext ctx) {
        return visitChildren(ctx);
    }

	@Override
	public List<Ast> visitVarDecl(@NotNull JavaliParser.VarDeclContext ctx) {
		List<Ast> result = new ArrayList<>();

		for (int i = 0; i < ctx.Identifier().size(); i++) {
			Ast.VarDecl typedVarDecl = (Ast.VarDecl) visit(ctx.type()).get(0);

			assert typedVarDecl.name == null;

			typedVarDecl.name = ctx.Identifier(i).toString(); // fill in name of variable
			result.add(typedVarDecl);
		}

		return result;
	}

	@Override
	public List<Ast> visitMethodDecl(@NotNull JavaliParser.MethodDeclContext ctx) {
        List<Ast> result = new ArrayList<>();

		Ast.MethodDecl typedMethodDecl = (Ast.MethodDecl) visit(ctx.methodType()).get(0);

		typedMethodDecl.name = ctx.Identifier().toString();

		List<String> argumentTypes = new ArrayList<>();
		List<String> argumentNames = new ArrayList<>();

        if (ctx.formalParamList() != null) {
            for (Ast ast : visit(ctx.formalParamList())) {
                Ast.VarDecl varDecl = (Ast.VarDecl) ast;
                argumentTypes.add(varDecl.type);
                argumentNames.add(varDecl.name);
            }
        }

        typedMethodDecl.argumentTypes = argumentTypes;
		typedMethodDecl.argumentNames = argumentNames;

		List<Ast> decls = new ArrayList<>();
		List<Ast> body = new ArrayList<>();

        for (JavaliParser.VarDeclContext varDecl : ctx.varDecl()) {
            decls.addAll(visit(varDecl));
        }
        for (JavaliParser.StatementContext statement : ctx.statement()) {
            body.addAll(visit(statement));
        }

		typedMethodDecl.setDecls(new Ast.Seq(decls));
		typedMethodDecl.setBody(new Ast.Seq(body));

		result.add(typedMethodDecl);
		return result;
	}

    @Override
    public List<Ast> visitFormalParamList(@NotNull JavaliParser.FormalParamListContext ctx) {
        List<Ast> result = new ArrayList<>();

        assert ctx.type().size() == ctx.Identifier().size();

        for (int i = 0; i < ctx.type().size(); i++) {
            Ast.VarDecl typedVarDecl = (Ast.VarDecl) visit(ctx.type(i)).get(0);

            assert typedVarDecl.name == null;

            typedVarDecl.name = ctx.Identifier(i).toString(); // fill in name of parameter
            result.add(typedVarDecl);
        }

        return result;
    }


// statements

    @Override
    public List<Ast> visitStmtAssignment(@NotNull JavaliParser.StmtAssignmentContext ctx) {
        return visit(ctx.assignmentStatement());
    }

    @Override
    public List<Ast> visitStmtMethodCall(@NotNull JavaliParser.StmtMethodCallContext ctx) {
        return visit(ctx.methodCallStatement());
    }

    @Override
    public List<Ast> visitStmtIf(@NotNull JavaliParser.StmtIfContext ctx) {
        return visit(ctx.ifStatement());
    }

    @Override
    public List<Ast> visitStmtWhile(@NotNull JavaliParser.StmtWhileContext ctx) {
        return visit(ctx.whileStatement());
    }

    @Override
    public List<Ast> visitStmtReturn(@NotNull JavaliParser.StmtReturnContext ctx) {
        return visit(ctx.returnStatement());
    }

    @Override
    public List<Ast> visitStmtWrite(@NotNull JavaliParser.StmtWriteContext ctx) {
        return visit(ctx.writeStatement());
    }

    @Override
    public List<Ast> visitStatementBlock(@NotNull JavaliParser.StatementBlockContext ctx) {
        List<Ast> result = new ArrayList<>();

        for (JavaliParser.StatementContext stmt : ctx.statement()) {
            result.addAll(visit(stmt));
        }

        return result;
    }

    @Override
    public List<Ast> visitAssignmentStmtExpr(@NotNull JavaliParser.AssignmentStmtExprContext ctx) {
        List<Ast> result = new ArrayList<>();

        Ast.Var left = (Ast.Var) visit(ctx.identAccess()).get(0);
        Ast.Expr right = (Ast.Expr) visit(ctx.expression()).get(0);

        result.add(new Ast.Assign(left, right));
        return result;
    }

    @Override
    public List<Ast> visitAssignmentStmtNew(@NotNull JavaliParser.AssignmentStmtNewContext ctx) {
        List<Ast> result = new ArrayList<>();

        Ast.Var left = (Ast.Var) visit(ctx.identAccess()).get(0);
        Ast.Expr right = (Ast.Expr) visit(ctx.newExpression()).get(0);

        result.add(new Ast.Assign(left, right));
        return result;
    }

    @Override
    public List<Ast> visitAssignmentStmtRead(@NotNull JavaliParser.AssignmentStmtReadContext ctx) {
        List<Ast> result = new ArrayList<>();

        Ast.Var left = (Ast.Var) visit(ctx.identAccess()).get(0);
        Ast.Expr right = (Ast.Expr) visit(ctx.readExpression()).get(0);

        result.add(new Ast.Assign(left, right));
        return result;
    }

    @Override
    public List<Ast> visitMethodCallStatement(@NotNull JavaliParser.MethodCallStatementContext ctx) {
        List<Ast> result = new ArrayList<>();

        Ast.Expr rcvr;
        String methodName = ctx.Identifier().toString();
        List<Ast.Expr> arguments = new ArrayList<>();

        if (ctx.identAccess() == null) {
            // implicit function call: add ThisRef as first argument
            rcvr = new Ast.ThisRef();
        } else {
            // explicit function call: add identAccess expression as first argument
            rcvr = (Ast.Expr) visit(ctx.identAccess()).get(0);
        }

        if (ctx.actualParamList() != null) {
            for (Ast ast : visit(ctx.actualParamList())) {
                Ast.Expr expr = (Ast.Expr) ast;
                arguments.add(expr);
            }
        }

        Ast.MethodCallExpr mce = new Ast.MethodCallExpr(rcvr, methodName, arguments);

        result.add(new Ast.MethodCall(mce));
        return result;
    }

    @Override
    public List<Ast> visitIfStatement(@NotNull JavaliParser.IfStatementContext ctx) {
        List<Ast> result = new ArrayList<>();

        Ast.Expr condition = (Ast.Expr) visit(ctx.expression()).get(0);
        Ast.Seq then = new Ast.Seq(visit(ctx.statementBlock(0)));
        Ast otherwise;

        if (ctx.statementBlock().size() == 1) {
            // no else block
            otherwise = new Ast.Nop();
        } else {
            // visit else block
            otherwise = new Ast.Seq(visit(ctx.statementBlock(1)));
        }

        result.add(new Ast.IfElse(condition, then, otherwise));
        return result;
    }

    @Override
    public List<Ast> visitWhileStatement(@NotNull JavaliParser.WhileStatementContext ctx) {
        List<Ast> result = new ArrayList<>();

        Ast.Expr condition = (Ast.Expr) visit(ctx.expression()).get(0);
        Ast body = new Ast.Seq(visit(ctx.statementBlock()));

        result.add(new Ast.WhileLoop(condition, body));
        return result;
    }

    @Override
    public List<Ast> visitReturnStatement(@NotNull JavaliParser.ReturnStatementContext ctx) {
        List<Ast> result = new ArrayList<>();

        if (ctx.expression() != null) {
            Ast.Expr arg = (Ast.Expr) visit(ctx.expression()).get(0);
            result.add(new Ast.ReturnStmt(arg));
        } else {
            result.add(new Ast.ReturnStmt(null));
        }

        return result;
    }

    @Override
    public List<Ast> visitWriteStmt(@NotNull JavaliParser.WriteStmtContext ctx) {
        List<Ast> result = new ArrayList<>();

        Ast.Expr arg = (Ast.Expr) visit(ctx.expression()).get(0);

        result.add(new Ast.BuiltInWrite(arg));
        return result;
    }

    @Override
    public List<Ast> visitWriteLnStmt(@NotNull JavaliParser.WriteLnStmtContext ctx) {
        List<Ast> result = new ArrayList<>();

        result.add(new Ast.BuiltInWriteln());
        return result;
    }


// expressions

    @Override
    public List<Ast> visitNewIdentifier(@NotNull JavaliParser.NewIdentifierContext ctx) {
        List<Ast> result = new ArrayList<>();

        String typeName = ctx.Identifier().toString();

        result.add(new Ast.NewObject(typeName));
        return result;
    }

    @Override
    public List<Ast> visitNewArrayId(@NotNull JavaliParser.NewArrayIdContext ctx) {
        List<Ast> result = new ArrayList<>();

        String typeName = ctx.Identifier().toString();
        Ast.Expr capacity = (Ast.Expr) visit(ctx.expression()).get(0);

        result.add(new Ast.NewArray(String.format("%s[]", typeName), capacity));
        return result;
    }

    @Override
    public List<Ast> visitNewArrayPr(@NotNull JavaliParser.NewArrayPrContext ctx) {
        List<Ast> result = new ArrayList<>();

        String typeName = ((Ast.VarDecl) visit(ctx.primitiveType()).get(0)).type;
        Ast.Expr capacity = (Ast.Expr) visit(ctx.expression()).get(0);

        result.add(new Ast.NewArray(String.format("%s[]", typeName), capacity));
        return result;
    }

    @Override
    public List<Ast> visitReadExpression(@NotNull JavaliParser.ReadExpressionContext ctx) {
        List<Ast> result = new ArrayList<>();

        result.add(new Ast.BuiltInRead());
        return result;
    }

    @Override
    public List<Ast> visitActualParamList(@NotNull JavaliParser.ActualParamListContext ctx) {
        List<Ast> result = new ArrayList<>();

        for (int i = 0; i < ctx.expression().size(); i++) {
            result.add(visit(ctx.expression(i)).get(0));
        }

        return result;
    }

    @Override
    public List<Ast> visitIdentAccessId(@NotNull JavaliParser.IdentAccessIdContext ctx) {
        List<Ast> result = new ArrayList<>();

        // TODO Identifier is not necessarily an AST.Var
        String name = ctx.Identifier().toString();

        result.add(new Ast.Var(name));
        return result;
    }

    @Override
    public List<Ast> visitIdentAccessThis(@NotNull JavaliParser.IdentAccessThisContext ctx) {
        return super.visitIdentAccessThis(ctx);
    }

    @Override
    public List<Ast> visitIdentAccessField(@NotNull JavaliParser.IdentAccessFieldContext ctx) {
        return super.visitIdentAccessField(ctx);
    }

    @Override
    public List<Ast> visitIdentAccessArray(@NotNull JavaliParser.IdentAccessArrayContext ctx) {
        return super.visitIdentAccessArray(ctx);
    }

    @Override
    public List<Ast> visitIdentAccessMethod(@NotNull JavaliParser.IdentAccessMethodContext ctx) {
        List<Ast> result = new ArrayList<>();

        Ast.Expr rcvr = new Ast.ThisRef();
        String methodName = ctx.Identifier().toString();
        List<Ast.Expr> arguments = new ArrayList<>();

        if (ctx.actualParamList() != null) {
            for (Ast ast : visit(ctx.actualParamList())) {
                Ast.Expr expr = (Ast.Expr) ast;
                arguments.add(expr);
            }
        }

        result.add(new Ast.MethodCallExpr(rcvr, methodName, arguments));
        return result;
    }

    @Override
    public List<Ast> visitIdentAccessFieldMethod(@NotNull JavaliParser.IdentAccessFieldMethodContext ctx) {
        List<Ast> result = new ArrayList<>();

        Ast.Expr rcvr = (Ast.Expr) visit(ctx.identAccess()).get(0);
        String methodName = ctx.Identifier().toString();
        List<Ast.Expr> arguments = new ArrayList<>();

        if (ctx.actualParamList() != null) {
            for (Ast ast : visit(ctx.actualParamList())) {
                Ast.Expr expr = (Ast.Expr) ast;
                arguments.add(expr);
            }
        }

        result.add(new Ast.MethodCallExpr(rcvr, methodName, arguments));
        return result;
    }

    @Override
    public List<Ast> visitLIT(@NotNull JavaliParser.LITContext ctx) {
        List<Ast> result = new ArrayList<>();

        // TODO parseInt throws NumberFormatException if out of bound (?)
        // should throw ParseFailure according to homework sheet

        // TODO handle different kinds of Literals
        int value = Integer.parseInt(ctx.Literal().toString());
        Ast.IntConst intConst = new Ast.IntConst(value); // works for decimal integers

        result.add(intConst);
        return result;
    }

	@Override
	public List<Ast> visitIDACC(@NotNull JavaliParser.IDACCContext ctx) {
		return super.visitIDACC(ctx);
	}

    @Override
    public List<Ast> visitPARS(@NotNull JavaliParser.PARSContext ctx) {
        return super.visitPARS(ctx);
    }

    @Override
    public List<Ast> visitUNARY(@NotNull JavaliParser.UNARYContext ctx) {
        return super.visitUNARY(ctx);
    }

    @Override
    public List<Ast> visitCAST(@NotNull JavaliParser.CASTContext ctx) {
        return super.visitCAST(ctx);
    }

    @Override
    public List<Ast> visitMULT(@NotNull JavaliParser.MULTContext ctx) {
        return super.visitMULT(ctx);
    }

    @Override
    public List<Ast> visitADD(@NotNull JavaliParser.ADDContext ctx) {
        return super.visitADD(ctx);
    }

    @Override
    public List<Ast> visitCOMP(@NotNull JavaliParser.COMPContext ctx) {
        return super.visitCOMP(ctx);
    }

    @Override
    public List<Ast> visitEQ(@NotNull JavaliParser.EQContext ctx) {
        return super.visitEQ(ctx);
    }

	@Override
	public List<Ast> visitLAND(@NotNull JavaliParser.LANDContext ctx) {
		return super.visitLAND(ctx);
	}

    @Override
    public List<Ast> visitLOR(@NotNull JavaliParser.LORContext ctx) {
        return super.visitLOR(ctx);
    }
}