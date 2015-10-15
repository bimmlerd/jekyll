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
		for (ParseTree child: ctx.children) {
			result.addAll(visit(child));
		}
		return result;
	}


// types

    @Override
    public List<Ast> visitType(@NotNull JavaliParser.TypeContext ctx) {
        return visit(ctx.getChild(0));
    }

    @Override
    public List<Ast> visitMethodType(@NotNull JavaliParser.MethodTypeContext ctx) {
        assert ctx.children.size() == 1;

        List<Ast> result = new ArrayList<>();
        String type;
        if (ctx.getChild(0).toString().equals(VOID)) {
            type = VOID;
        } else {
            // get type from typed VarDecl
            Ast.VarDecl typedVarDecl = (Ast.VarDecl) visit(ctx.getChild(0)).get(0);
            type = typedVarDecl.type;
        }
        result.add(new Ast.MethodDecl(type, null, null, null, null, null));
        return result;
    }

    @Override
    public List<Ast> visitPrimitiveType(@NotNull JavaliParser.PrimitiveTypeContext ctx) {
        List<Ast> result = new ArrayList<>();
        result.add(new Ast.VarDecl(ctx.getChild(0).toString(), null)); // We don't know the name of the variable yet
        return result;

    }

    @Override
    public List<Ast> visitReferenceType(@NotNull JavaliParser.ReferenceTypeContext ctx) {
        return super.visitReferenceType(ctx);
    }

    @Override
    public List<Ast> visitArrayType(@NotNull JavaliParser.ArrayTypeContext ctx) {
        return super.visitArrayType(ctx);
    }


// program structure

    @Override
    public List<Ast> visitUnit(@NotNull JavaliParser.UnitContext ctx) {
    return super.visitUnit(ctx);
}

	@Override
	public List<Ast> visitClassDecl(JavaliParser.ClassDeclContext ctx) {
		{
			List<Ast> memberList = new ArrayList<>();
			memberList.addAll(visit(ctx.getChild(3)));
			ClassDecl classDecl = new ClassDecl(ctx.getChild(1).toString(), OBJECT, memberList);
			classDecls.add(classDecl);
		}
		return null;
	}

    @Override
    public List<Ast> visitMemberList(@NotNull JavaliParser.MemberListContext ctx) {
        return visitChildren(ctx);
    }

	@Override
	public List<Ast> visitVarDecl(@NotNull JavaliParser.VarDeclContext ctx) {
		List<Ast> result = new ArrayList<>();

		for (int i = 1; i < ctx.children.size(); i += 2) {
			Ast.VarDecl typedVarDecl = (Ast.VarDecl) visit(ctx.getChild(0)).get(0);
			assert typedVarDecl.name == null;

			typedVarDecl.name = ctx.getChild(i).toString(); // fill in name of variable
			result.add(typedVarDecl);
		}
		return result;
	}

	@Override
	public List<Ast> visitMethodDecl(@NotNull JavaliParser.MethodDeclContext ctx) {
		Ast.MethodDecl typedMethodDecl = (Ast.MethodDecl) visit(ctx.getChild(0)).get(0);

		typedMethodDecl.name = ctx.getChild(1).toString();

		List<String> argumentTypes = new ArrayList<>();
		List<String> argumentNames = new ArrayList<>();

		ParseTree formalParamsOrNothing = ctx.getChild(3);
		if (! formalParamsOrNothing.toString().equals(")")) {
			for (Ast ast : visit(ctx.getChild(3))) {
				Ast.VarDecl varDecl = (Ast.VarDecl) ast;
				argumentTypes.add(varDecl.type);
				argumentNames.add(varDecl.name);
			}
		}

		typedMethodDecl.argumentTypes = argumentTypes;
		typedMethodDecl.argumentNames = argumentNames;

		List<Ast> decls = new ArrayList<>();
		List<Ast> body = new ArrayList<>();

		int i = ctx.getChild(5).toString().equals("{") ? 6 : 5;
		for (; i < ctx.children.size() - 1; i++) {
			if (ctx.getChild(i) instanceof JavaliParser.VarDeclContext) {
				decls.addAll(visit(ctx.getChild(i)));
			} else {
				body.addAll(visit(ctx.getChild(i)));
			}
		}

		typedMethodDecl.setDecls(new Ast.Seq(decls));
		typedMethodDecl.setBody(new Ast.Seq(body)); // TODO

		List<Ast> result = new ArrayList<>();
		result.add(typedMethodDecl);
		return result;
	}

    @Override
    public List<Ast> visitFormalParamList(@NotNull JavaliParser.FormalParamListContext ctx) {
        List<Ast> result = new ArrayList<>();
        for (int i = 0; i < ctx.children.size(); i += 3) {
            Ast.VarDecl typedVarDecl = (Ast.VarDecl) visit(ctx.getChild(i)).get(0);

            assert typedVarDecl.name == null;

            typedVarDecl.name = ctx.getChild(i + 1).toString(); // fill in name of parameter
            result.add(typedVarDecl);
        }
        return result;
    }


// statements

    @Override
    public List<Ast> visitStatement(@NotNull JavaliParser.StatementContext ctx) {
        return visitChildren(ctx); // TODO maybe implement this using labels?
    }

    @Override
    public List<Ast> visitStatementBlock(@NotNull JavaliParser.StatementBlockContext ctx) {
        return super.visitStatementBlock(ctx);
    }

    @Override
    public List<Ast> visitAssignmentStatement(@NotNull JavaliParser.AssignmentStatementContext ctx) {
        Ast.Var left = (Ast.Var) visit(ctx.getChild(0)).get(0);
        Ast.Expr right = (Ast.Expr) visit(ctx.getChild(2)).get(0); // TODO readExpression?
        List<Ast> result = new ArrayList<>();
        result.add(new Ast.Assign(left, right));
        return result;
    }

    @Override
    public List<Ast> visitMethodCallStatement(@NotNull JavaliParser.MethodCallStatementContext ctx) {
        return super.visitMethodCallStatement(ctx);
    }

    @Override
    public List<Ast> visitIfStatement(@NotNull JavaliParser.IfStatementContext ctx) {
        return super.visitIfStatement(ctx);
    }

    @Override
    public List<Ast> visitWhileStatement(@NotNull JavaliParser.WhileStatementContext ctx) {
        return super.visitWhileStatement(ctx);
    }

    @Override
    public List<Ast> visitReturnStatement(@NotNull JavaliParser.ReturnStatementContext ctx) {
        return super.visitReturnStatement(ctx);
    }

	@Override
	public List<Ast> visitWriteStatement(@NotNull JavaliParser.WriteStatementContext ctx) {
		return super.visitWriteStatement(ctx);
	}


// expressions

    @Override
    public List<Ast> visitNewExpression(@NotNull JavaliParser.NewExpressionContext ctx) {
        return super.visitNewExpression(ctx);
    }

    @Override
    public List<Ast> visitReadExpression(@NotNull JavaliParser.ReadExpressionContext ctx) {
        return super.visitReadExpression(ctx);
    }

    @Override
    public List<Ast> visitActualParamList(@NotNull JavaliParser.ActualParamListContext ctx) {
        return super.visitActualParamList(ctx);
    }

    @Override
    public List<Ast> visitIdentAccess(@NotNull JavaliParser.IdentAccessContext ctx) {
        List<Ast> result = new ArrayList<>();
        ParseTree child = ctx.getChild(0);
        if (child.toString().equals(THIS)) {

        }
        result.add(new Ast.Var(child.toString())); // TODO handle all cases, not just Identifier
        return result;
    }

    @Override
    public List<Ast> visitLIT(@NotNull JavaliParser.LITContext ctx) {
        return super.visitLIT(ctx);
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