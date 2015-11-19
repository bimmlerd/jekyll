package cd.backend.codegen;

import static cd.backend.codegen.AssemblyEmitter.constant;
import static cd.backend.codegen.RegisterManager.BASE_REG;
import static cd.backend.codegen.RegisterManager.STACK_REG;

import cd.Config;
import cd.ToDoException;
import cd.backend.codegen.RegisterManager.Register;
import cd.ir.Ast;
import cd.ir.Ast.Assign;
import cd.ir.Ast.BuiltInWrite;
import cd.ir.Ast.BuiltInWriteln;
import cd.ir.Ast.ClassDecl;
import cd.ir.Ast.IfElse;
import cd.ir.Ast.MethodCall;
import cd.ir.Ast.MethodDecl;
import cd.ir.Ast.ReturnStmt;
import cd.ir.Ast.Var;
import cd.ir.Ast.WhileLoop;
import cd.ir.AstVisitor;
import cd.ir.Symbol;
import cd.util.debug.AstOneLine;

/**
 * Generates code to process statements and declarations.
 */
class StmtGenerator extends AstVisitor<Register, Void> {
	protected final AstCodeGenerator cg;
	private Symbol.ClassSymbol currentClass;

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
			throw new ToDoException();
		}
	}

	// Emit vtable for arrays of this class:
	@Override
	public Register classDecl(ClassDecl ast, Void arg) {
		currentClass = ast.sym;
		return visitChildren(ast, arg);
	}

	@Override
	public Register methodDecl(MethodDecl ast, Void arg) {
		cg.emit.emitLabel(String.format("%s$%s", currentClass.name, ast.name));

		// TODO replace with enter?
		// Preamble: save the old %ebp and point %ebp to the saved %ebp (ie, the new stack frame).
		cg.emit.emit("push", BASE_REG);
		cg.emit.emitMove(STACK_REG, BASE_REG);
		// Reserve space for local variables.
		int space = ast.decls().children().size() * Config.SIZEOF_PTR; // TODO do nicer
		cg.emit.emit("subl", AssemblyEmitter.constant(space), STACK_REG);


		cg.stack.storeCalleeSavedRegs();

		// # Function body.
		visit(ast.body(), arg);

		// TODO somehow avoid emitting the suffix again if we had a return in the body?
		cg.emitMethodSuffix(ast.sym.returnType.equals(Symbol.PrimitiveTypeSymbol.voidType));
		return null;
	}

	@Override
	public Register ifElse(IfElse ast, Void arg) {

        String labelAfterThen = cg.emit.uniqueLabel();
        String labelAfterElse = cg.emit.uniqueLabel();

		// generate code to evaluate the condition
        Register conditionReg = cg.eg.gen(ast.condition());

		// decide whether then-part is executed
		cg.emit.emit("cmp", constant(0), conditionReg);
        cg.rm.releaseRegister(conditionReg);
        cg.emit.emit("je", labelAfterThen);

		// generate code for then-part
        gen(ast.then());

        // skip around else-part
        cg.emit.emit("jmp", labelAfterElse);
        cg.emit.emitLabel(labelAfterThen);

        // generate code for else-part
        gen(ast.otherwise());

        cg.emit.emitLabel(labelAfterElse);
		return null;
	}

	@Override
	public Register whileLoop(WhileLoop ast, Void arg) {

        String labelBeforeTest = cg.emit.uniqueLabel();
        String labelAfterBody = cg.emit.uniqueLabel();

        cg.emit.emitLabel(labelBeforeTest);

        // generate code to evaluate the condition
        Register conditionReg = cg.eg.gen(ast.condition());

        // decide whether loop-body is executed
        cg.emit.emit("cmp", constant(0), conditionReg);
        cg.rm.releaseRegister(conditionReg);
        cg.emit.emit("je", labelAfterBody);

        // generate code for loop-body
        gen(ast.body());

        cg.emit.emit("jmp", labelBeforeTest);
        cg.emit.emitLabel(labelAfterBody);
		return null;
	}

	@Override
	public Register assign(Assign ast, Void arg) {
		if (!(ast.left() instanceof Var))
			throw new RuntimeException("LHS must be var in HW1");
		Var var = (Var) ast.left();
		Register rhsReg = cg.eg.gen(ast.right());
		cg.emit.emit("movl", rhsReg, AstCodeGenerator.VAR_PREFIX + var.name);
		cg.rm.releaseRegister(rhsReg);
		return null;
	}

	@Override
	public Register builtInWrite(BuiltInWrite ast, Void arg) {
		// TODO save caller saved registers
		Register reg = cg.eg.gen(ast.arg());
		cg.emit.emit("sub", constant(16), STACK_REG);
		cg.emit.emitStore(reg, 4, STACK_REG);
		cg.emit.emitStore("$STR_D", 0, STACK_REG);
		cg.emit.emit("call", Config.PRINTF);
		cg.emit.emit("add", constant(16), STACK_REG);
		cg.rm.releaseRegister(reg);
		return null;
	}

	@Override
	public Register builtInWriteln(BuiltInWriteln ast, Void arg) {
		// TODO save caller saved registers
		cg.emit.emit("sub", constant(16), STACK_REG);
		cg.emit.emitStore("$STR_NL", 0, STACK_REG);
		cg.emit.emit("call", Config.PRINTF);
		cg.emit.emit("add", constant(16), STACK_REG);
		return null;
	}

	@Override
	public Register returnStmt(ReturnStmt ast, Void arg) {
		{
			throw new ToDoException();
		}
	}

}
