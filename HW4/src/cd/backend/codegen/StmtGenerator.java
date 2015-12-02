package cd.backend.codegen;

import cd.Config;
import cd.backend.codegen.RegisterManager.Register;
import cd.ir.Ast;
import cd.ir.Ast.*;
import cd.ir.AstVisitor;
import cd.ir.Symbol;
import cd.util.debug.AstOneLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static cd.backend.codegen.AssemblyEmitter.constant;
import static cd.backend.codegen.AssemblyEmitter.labelAddress;

/**
 * Generates code to process statements and declarations.
 */
class StmtGenerator extends AstVisitor<Register, Context> {
	protected final AstCodeGenerator cg;

	StmtGenerator(AstCodeGenerator astCodeGenerator) {
		cg = astCodeGenerator;
	}

    @Override
	public Register visit(Ast ast, Context ctx) {
		try {
			cg.emit.increaseIndent(String.format("Emitting %s", AstOneLine.toString(ast)));
			return super.visit(ast, ctx);
		} finally {
			cg.emit.decreaseIndent();
		}
	}

	@Override
	public Register methodCall(MethodCall ast, Context ctx) {
        Register reg = cg.eg.visit(ast.getMethodCallExpr(), ctx);
		// return value is not stored
		cg.rm.releaseRegister(reg);
		return null;
	}

	// Emit vtable for arrays of this class:
	@Override
	public Register classDecl(ClassDecl ast, Context ctx) {
		return visitChildren(ast, ctx);
	}

	@Override
	public Register methodDecl(MethodDecl ast, Context ctx) {
		cg.emit.emitLabel(ctx.currentClass.vTable.getMethodLabel(ast.name));

		int localSpace = ast.sym.locals.size() * Config.SIZEOF_PTR;
		cg.emit.emit("enter",
				AssemblyEmitter.constant(localSpace),
				AssemblyEmitter.constant(0));
		ctx.stackOffset = -1 * (localSpace + 8); // localSpace for locals, 4 for enter, 4 for the ret addr

		if (ctx.offsetTable != null) {
			ctx.offsetTable.clear();
		} else {
			ctx.offsetTable = new HashMap<>();
		}

		int basePointerOffset = 2 * Config.SIZEOF_PTR;

		ctx.offsetTable.put("this", basePointerOffset); // fill in this ref as first argument
		for (Symbol.VariableSymbol param: ast.sym.parameters) {
			basePointerOffset += Config.SIZEOF_PTR;
			ctx.offsetTable.put(param.name, basePointerOffset);
		}

		basePointerOffset = 0;
		cg.emit.emitComment("Zero locals on stack");
		for (Symbol.VariableSymbol local : ast.sym.locals.values()) {
			basePointerOffset -= Config.SIZEOF_PTR;
			ctx.offsetTable.put(local.name, basePointerOffset);
			cg.emit.emitStore(AssemblyEmitter.constant(0),
                    basePointerOffset,
                    RegisterManager.BASE_REG);
        }

		ctx.stackOffset += cg.saveRegisters(RegisterManager.CALLEE_SAVE);

		// # Function body.
		visit(ast.body(), ctx);

		// TODO somehow avoid emitting the suffix again if we had a return in the body?
		cg.emitMethodSuffix(ast.sym.returnType.equals(Symbol.PrimitiveTypeSymbol.voidType));
		return null;
	}

	@Override
	public Register ifElse(IfElse ast, Context ctx) {

        String labelAfterThen = cg.emit.uniqueLabel();
        String labelAfterElse = cg.emit.uniqueLabel();

		// generate code to evaluate the condition
        Register conditionReg = cg.eg.visit(ast.condition(), ctx);

		// decide whether then-part is executed
		cg.emit.emit("cmp", constant(0), conditionReg);
        cg.rm.releaseRegister(conditionReg);
        cg.emit.emit("je", labelAfterThen);

		// generate code for then-part
        visit(ast.then(), ctx);

        // skip around else-part
        cg.emit.emit("jmp", labelAfterElse);
        cg.emit.emitLabel(labelAfterThen);

        // generate code for else-part
        visit(ast.otherwise(), ctx);

        cg.emit.emitLabel(labelAfterElse);
		return null;
	}

	@Override
	public Register whileLoop(WhileLoop ast, Context ctx) {

        String labelBeforeTest = cg.emit.uniqueLabel();
        String labelAfterBody = cg.emit.uniqueLabel();

        cg.emit.emitLabel(labelBeforeTest);

        // generate code to evaluate the condition
        Register conditionReg = cg.eg.visit(ast.condition(), ctx);

        // decide whether loop-body is executed
        cg.emit.emit("cmp", constant(0), conditionReg);
        cg.rm.releaseRegister(conditionReg);
        cg.emit.emit("je", labelAfterBody);

        // generate code for loop-body
        visit(ast.body(), ctx);

        cg.emit.emit("jmp", labelBeforeTest);
        cg.emit.emitLabel(labelAfterBody);
		return null;
	}

	@Override
	public Register assign(Assign ast, Context ctx) {
        // we need an address where to store the assigned value
        ctx.calculateValue = false;
        Register lhsReg = cg.eg.visit(ast.left(), ctx);

        // we want to value of the rhs expression
        ctx.calculateValue = true;
        Register rhsReg = cg.eg.visit(ast.right(), ctx);

		cg.emit.emitStore(rhsReg, 0, lhsReg);
		cg.rm.releaseRegister(rhsReg);
		return null;
	}

	@Override
	public Register builtInWrite(BuiltInWrite ast, Context ctx) {
        // evaluate integer expression to print
        Register reg = cg.eg.visit(ast.arg(), ctx);
        List<String> arguments = new ArrayList<>();
        arguments.add(labelAddress("STR_D"));
        arguments.add(reg.repr);

        // put arguments on the stack and save caller saved registers before making the call
		cg.beforeFunctionCall(arguments, ctx.stackOffset);
		cg.emit.emit("call", Config.PRINTF); // return value is not stored
		cg.afterFunctionCall(arguments, ctx.stackOffset);

		cg.rm.releaseRegister(reg);
		return null;
	}

	@Override
	public Register builtInWriteln(BuiltInWriteln ast, Context ctx) {
        List<String> arguments = new ArrayList<>();
        arguments.add(labelAddress("STR_NL"));

        // put arguments on the stack and save caller saved registers before making the call
		cg.beforeFunctionCall(arguments, ctx.stackOffset);
        cg.emit.emit("call", Config.PRINTF); // return value is not stored
		cg.afterFunctionCall(arguments, ctx.stackOffset);

		return null;
	}

	@Override
	public Register returnStmt(ReturnStmt ast, Context ctx) {
        if (ast.arg() == null) {
            // no return value
            cg.emitMethodSuffix(true);
        } else {
            Register reg = cg.eg.visit(ast.arg(), ctx);
            cg.emit.emitMove(reg, Register.EAX);
            cg.emitMethodSuffix(false);
        }

        return null;
	}

}
