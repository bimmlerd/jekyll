package cd.backend.codegen;

import cd.Config;
import cd.backend.codegen.RegisterManager.Register;
import cd.ir.Ast;
import cd.ir.Ast.*;
import cd.ir.AstVisitor;
import cd.util.debug.AstOneLine;

/**
 * Generates code to process statements and declarations.
 */
class StmtGenerator extends AstVisitor<Register, Void> {
	protected final AstCodeGenerator cg;

	private boolean usesWrite;
	private boolean usesWriteln;

	protected static final String WRITE_STRING_LABEL = "int_format.str";
	protected static final String WRITELN_STRING_LABEL = "newline.str";

	StmtGenerator(AstCodeGenerator astCodeGenerator) {
		cg = astCodeGenerator;
	}

	public void gen(Ast ast) {
		visit(ast, null);
	}

	@Override
	public Register visit(Ast ast, Void arg) {
		try {
			cg.rm.currentAst = ast;
			cg.emit.increaseIndent("Emitting " + AstOneLine.toString(ast));
			return super.visit(ast, arg);
		} finally {
			cg.emit.decreaseIndent();
		}
	}

	@Override
	public Register methodCall(MethodCall ast, Void dummy) {
		{
			throw new RuntimeException("Not required");
		}
	}

	@Override
	public Register methodDecl(MethodDecl ast, Void arg) {
		{
			// Because we only handle very simple programs in HW1,
			// you can just emit the prologue here (there is only one
			// method: main)!
			cg.emit.emitRaw(Config.TEXT_SECTION);
			cg.emit.emitRaw(".globl " + Config.MAIN); // Needed on OSX
			cg.emit.emitLabel(Config.MAIN);

			cg.emit.emitComment("Stack pad for 16 byte alignment requirement on OSX");
			cg.emit.emit("addl", AssemblyEmitter.constant(4), RegisterManager.STACK_REG);

			visitChildren(ast.rwChildren.get(0), null); // visit declarations
			visitChildren(ast.rwChildren.get(1), null); // visit statements

			cg.emit.emitStore(AssemblyEmitter.constant(0), 0, RegisterManager.STACK_REG); // return 0
			cg.emit.emitCall(Config.EXIT);


			// DATA INT SECTION -------------------------------
			cg.emit.emitRaw(Config.DATA_INT_SECTION);
			for (String var: cg.vm) {
				cg.emit.emitLabel(var);
				cg.emit.emitConstantData(Integer.toString(0));
			}


			// DATA STR SECTION -------------------------------
			cg.emit.emitRaw(Config.DATA_STR_SECTION);
			if (this.usesWrite) {
				cg.emit.emitLabel(StmtGenerator.WRITE_STRING_LABEL);
				cg.emit.emitRaw(Config.DOT_STRING + " \"%d\"");
			}
			if (this.usesWriteln) {
				cg.emit.emitLabel(StmtGenerator.WRITELN_STRING_LABEL);
				cg.emit.emitRaw(Config.DOT_STRING + " \"\\n\"");
			}

			return null;
		}
	}

	@Override
	public Register varDecl(VarDecl ast, Void arg) {
		cg.vm.declare(ast);
		return null;
	}

	@Override
	public Register ifElse(IfElse ast, Void arg) {
		{
			throw new RuntimeException("Not required");
		}
	}

	@Override
	public Register whileLoop(WhileLoop ast, Void arg) {
		{
			throw new RuntimeException("Not required");
		}
	}

	@Override
	public Register assign(Assign ast, Void arg) {
		{
			ExprGenerator eg = new ExprGenerator(cg);

			Var left = (Var) ast.rwChildren.get(0);
			Ast.Expr right = (Ast.Expr) ast.rwChildren.get(1);
			if (right instanceof Ast.Expr.BuiltInRead) {
				// TODO this violates separation, but how do we get the variable name into the eg?
				cg.emit.emitStore(AssemblyEmitter.labelAddress(left.name), 4, RegisterManager.STACK_REG);
			}
			Register reg = eg.visit(right, null);
			if (reg != null) {
				//cg.vm.add(left, reg);
				cg.emit.emitMove(reg, String.format("(%s)", left.name));
				cg.emit.emitComment("Releasing reg: " + reg.repr);
				cg.rm.releaseRegister(reg);
			}

			return null;
		}
	}

	@Override
	public Register builtInWrite(BuiltInWrite ast, Void arg) {
		{
			this.usesWrite = true;

			ExprGenerator eg = new ExprGenerator(cg);


			cg.emit.emitStore(AssemblyEmitter.labelAddress(StmtGenerator.WRITE_STRING_LABEL), 0, RegisterManager.STACK_REG);
			Expr e = (Expr) ast.rwChildren.get(0);
			Register res = eg.visit(e, null);
			cg.emit.emitStore(res, 4, RegisterManager.STACK_REG);
			cg.emit.emitCall(Config.PRINTF);
			cg.emit.emitComment("Releasing reg: " + res.repr);
			cg.rm.releaseRegister(res);

			return null;
		}
	}

	@Override
	public Register builtInWriteln(BuiltInWriteln ast, Void arg) {
		{
			this.usesWriteln = true;

			cg.emit.emitStore(AssemblyEmitter.labelAddress(StmtGenerator.WRITELN_STRING_LABEL), 0, RegisterManager.STACK_REG);
			cg.emit.emitCall(Config.PRINTF);

			return null;
		}
	}

}
