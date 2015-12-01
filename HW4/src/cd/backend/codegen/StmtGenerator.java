package cd.backend.codegen;

import cd.Config;
import cd.backend.codegen.RegisterManager.Register;
import cd.ir.Ast;
import cd.ir.Ast.*;
import cd.ir.AstVisitor;
import cd.ir.Symbol;
import cd.util.debug.AstOneLine;

import java.util.ArrayList;
import java.util.List;

import static cd.backend.codegen.AssemblyEmitter.constant;
import static cd.backend.codegen.AssemblyEmitter.labelAddress;

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
			cg.emit.increaseIndent(String.format("Emitting %s", AstOneLine.toString(ast)));
			return super.visit(ast, arg);
		} finally {
			cg.emit.decreaseIndent();
		}
	}

	@Override
	public Register methodCall(MethodCall ast, Void dummy) {
		Register reg = cg.eg.gen(ast.getMethodCallExpr());
		// return value is not stored
		cg.rm.releaseRegister(reg);
		return null;
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

		cg.stack.methodPreamble(ast.name, ast.decls().children());

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
        // TODO visitor for allowed LHS to return addresses and not value stored at the address
		if (!(ast.left() instanceof Var))
			throw new RuntimeException("LHS must be var in HW1");
		Var var = (Var) ast.left();
		Register rhsReg = cg.eg.gen(ast.right());
		cg.emit.emitMove(rhsReg, String.format("%s%s", AstCodeGenerator.VAR_PREFIX, var.name));
		cg.rm.releaseRegister(rhsReg);
		return null;
	}

	@Override
	public Register builtInWrite(BuiltInWrite ast, Void arg) {
        // evaluate integer expression to print
        Register reg = cg.eg.gen(ast.arg());
        List<String> arguments = new ArrayList<>();
        arguments.add(labelAddress("STR_D"));
        arguments.add(reg.repr);

        // put arguments on the stack and save caller saved registers before making the call
        cg.stack.beforeFunctionCall(arguments);
		cg.emit.emit("call", Config.PRINTF); // return value is not stored
		cg.stack.afterFunctionCall(arguments);

		cg.rm.releaseRegister(reg);
		return null;
	}

	@Override
	public Register builtInWriteln(BuiltInWriteln ast, Void arg) {
        List<String> arguments = new ArrayList<>();
        arguments.add(labelAddress("STR_NL"));

        // put arguments on the stack and save caller saved registers before making the call
        cg.stack.beforeFunctionCall(arguments);
        cg.emit.emit("call", Config.PRINTF); // return value is not stored
        cg.stack.afterFunctionCall(arguments);

		return null;
	}

	@Override
	public Register returnStmt(ReturnStmt ast, Void arg) {
        if (ast.arg() == null) {
            // no return value
            cg.emitMethodSuffix(true);
        } else {
            Register reg = cg.eg.gen(ast.arg());
            cg.emit.emitMove(reg, Register.EAX);
            cg.emitMethodSuffix(false);
        }

        return null;
	}

}
