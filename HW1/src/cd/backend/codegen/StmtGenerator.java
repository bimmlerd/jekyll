package cd.backend.codegen;

import cd.Config;
import cd.ToDoException;
import cd.backend.codegen.RegisterManager.Register;
import cd.ir.Ast;
import cd.ir.Ast.*;
import cd.ir.AstVisitor;
import cd.util.debug.AstOneLine;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates code to process statements and declarations.
 */
class StmtGenerator extends AstVisitor<Register, Void> {
	protected final AstCodeGenerator cg;

	private Boolean usesWrite;
	private Boolean usesWriteln;

	private final String WRITE_STRING_LABEL = "int_format.str";
	private final String WRITELN_STRING_LABEL = "newline.str";

	private List<Ast.VarDecl> symbols = new ArrayList<>();

	StmtGenerator(AstCodeGenerator astCodeGenerator) {
		cg = astCodeGenerator;
	}

	public void gen(Ast ast) {
		visit(ast, null);
	}

	@Override
	public Register visit(Ast ast, Void arg) {
		try {
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
			cg.rm.initRegisters(); // TODO here?

			cg.emit.emitRaw(Config.TEXT_SECTION);
			cg.emit.emitRaw(".globl " + Config.MAIN); // Needed on OSX
			cg.emit.emitLabel(Config.MAIN);

			List<Ast> declarations = ast.rwChildren.get(0).rwChildren;
			for (Ast decl: declarations) {
				visit(decl, null);
			}

			List<Ast> statements = ast.rwChildren.get(1).rwChildren;

			for (Ast stmt: statements) {
				visit(stmt, null);
			}

			cg.emit.emitComment("Stack pad for _exit");
			cg.emit.emit("addl", AssemblyEmitter.constant(4), "%esp");
			cg.emit.emitMoveToAddress(AssemblyEmitter.constant(0), "%esp"); // return 0
			cg.emit.emitCall(Config.EXIT);


			// DATA INT SECTION -------------------------------
			cg.emit.emitRaw(Config.DATA_INT_SECTION);
			for (VarDecl var: this.symbols) {
				cg.emit.emitLabel(var.name);
				cg.emit.emitRaw(String.format("%s %s", Config.DOT_INT, 0));
			}


			// DATA STR SECTION -------------------------------
			cg.emit.emitRaw(Config.DATA_STR_SECTION);
			if (this.usesWrite) {
				cg.emit.emitLabel(this.WRITE_STRING_LABEL);
				cg.emit.emitRaw(Config.DOT_STRING + " \"%d\"");
			}
			if (this.usesWriteln) {
				cg.emit.emitLabel(this.WRITELN_STRING_LABEL);
				cg.emit.emitRaw(Config.DOT_STRING + " \"\\n\"");
			}

			return null; // TODO?
		}
	}

	@Override
	public Register varDecl(VarDecl ast, Void arg) {
		this.symbols.add(ast);
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
			Var left = (Var) ast.rwChildren.get(0);
			Ast.Expr right = (Ast.Expr) ast.rwChildren.get(1);
			ExprGenerator eg = new ExprGenerator(cg);
			return eg.visit(right, null);
		}
	}

	@Override
	public Register builtInWrite(BuiltInWrite ast, Void arg) {
		{
			this.usesWrite = true;

			Register reg = null;
			try {
				reg = cg.rm.getRegister();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}

			String tmpLabel = cg.emit.uniqueLabel();
			String offsetCalculation = String.format("%s-%s(%s)", this.WRITE_STRING_LABEL, tmpLabel, reg.repr);
			cg.emit.emitCall(tmpLabel);
			cg.emit.emitLabel(tmpLabel);
			cg.emit.emit("popl", reg);
			cg.emit.emit("leal", offsetCalculation, reg);
			cg.emit.emitComment("Stack pad for 16 byte alignment requirement on OSX");
			cg.emit.emit("addl", AssemblyEmitter.constant(4), "%esp");
			cg.emit.emitMoveToAddress(reg, "%esp");
			Ast thing = ast.rwChildren.get(0);
			if (thing instanceof Var) {
				// load value of variable into reg
				cg.emit.emitMove(AssemblyEmitter.labelAddress(((Var) thing).name), reg);
				cg.emit.emitLoad(0, reg, reg);
			} else {
				// load constant into reg
				cg.emit.emitMove(AssemblyEmitter.constant(((Ast.IntConst) thing).value), reg);
			}
			cg.emit.emitMoveToAddress(reg, "%esp", 4);
			cg.emit.emitCall(Config.PRINTF);
			cg.emit.emitComment("Realign Stack");
			cg.emit.emit("subl", AssemblyEmitter.constant(4), "%esp");

			cg.rm.releaseRegister(reg);
			return null;
		}
	}

	@Override
	public Register builtInWriteln(BuiltInWriteln ast, Void arg) {
		{
			this.usesWriteln = true;

			Register reg = null;
			try {
				reg = cg.rm.getRegister();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}

			String tmpLabel = cg.emit.uniqueLabel();
			String offsetCalculation = String.format("%s-%s(%s)", this.WRITELN_STRING_LABEL, tmpLabel, reg.repr);
			cg.emit.emitCall(tmpLabel);
			cg.emit.emitLabel(tmpLabel);
			cg.emit.emit("popl", reg);
			cg.emit.emit("leal", offsetCalculation, reg);
			cg.emit.emitComment("Stack pad for 16 byte alignment requirement on OSX");
			cg.emit.emit("addl", AssemblyEmitter.constant(4), "%esp");
			cg.emit.emitMoveToAddress(reg, "%esp");
			cg.emit.emitCall(Config.PRINTF);
			cg.emit.emitComment("Realign Stack");
			cg.emit.emit("subl", AssemblyEmitter.constant(4), "%esp");

			cg.rm.releaseRegister(reg);
			return null;
		}
	}

}
