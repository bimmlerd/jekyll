package cd.backend.codegen;

import cd.Config;
import cd.ToDoException;
import cd.backend.ExitCode;
import cd.backend.codegen.RegisterManager.Register;
import cd.ir.Ast.*;
import cd.ir.ExprVisitor;
import cd.ir.Symbol;
import cd.util.debug.AstOneLine;

import java.util.ArrayList;
import java.util.List;

import static cd.Config.SCANF;
import static cd.backend.codegen.AssemblyEmitter.*;
import static cd.backend.codegen.RegisterManager.*;

/**
 * Generates code to evaluate expressions. After emitting the code, returns a
 * Register where the result can be found.
 */
class ExprGenerator extends ExprVisitor<Register, Context> {
	protected final AstCodeGenerator cg;

	ExprGenerator(AstCodeGenerator astCodeGenerator) {
		cg = astCodeGenerator;
	}

    @Override
	public Register visit(Expr ast, Context ctx) {
		try {
			cg.emit.increaseIndent(String.format("Emitting %s", AstOneLine.toString(ast)));
			return super.visit(ast, ctx);
		} finally {
			cg.emit.decreaseIndent();
		}

	}

	@Override
	public Register binaryOp(BinaryOp ast, Context ctx) {
		// Simplistic HW1 implementation that does
		// not care if it runs out of registers, and
		// supports only a limited range of operations:

		int leftRN = cg.rnv.calc(ast.left());
		int rightRN = cg.rnv.calc(ast.right());

		Register leftReg, rightReg;
		if (leftRN > rightRN) {
            leftReg = visit(ast.left(), ctx);
            rightReg = visit(ast.right(), ctx);
		} else {
            rightReg = visit(ast.right(), ctx);
            leftReg = visit(ast.left(), ctx);
		}

		cg.debug("Binary Op: %s (%s,%s)", ast, leftReg, rightReg);

        // store opcode of jump to be performed after comparison of registers
        String jmpOp = "";

		boolean isModulo = false;
		switch (ast.operator) {
		case B_TIMES:
			cg.emit.emit("imul", rightReg, leftReg);
			break;

		case B_MOD:
			isModulo = true;
		case B_DIV:
			String labelOK = cg.emit.uniqueLabel();
			cg.emit.emit("cmp", AssemblyEmitter.constant(0), rightReg);
			cg.emit.emit("jne", labelOK);

			// exit with error code ExitCode.DIVISION_BY_ZERO
			List<String> arguments = new ArrayList<>();
			arguments.add(AssemblyEmitter.constant(ExitCode.DIVISION_BY_ZERO.value));
			cg.beforeFunctionCall(arguments, ctx.stackOffset);
			cg.emit.emit("call", Config.EXIT);

			cg.emit.emitLabel(labelOK);

			if (cg.rm.isInUse(Register.EAX) && Register.EAX != leftReg) {
				cg.emit.emitComment("Save eax prior to div");
				cg.emit.emitStore(Register.EAX, -4, RegisterManager.STACK_REG);
			}
			if (cg.rm.isInUse(Register.EDX)) {
				cg.emit.emitComment("Save edx prior to div");
				cg.emit.emitStore(Register.EDX, -8, RegisterManager.STACK_REG);
			}
			cg.emit.emitMove(leftReg, Register.EAX);
			cg.emit.emitRaw("cdq");
			if (rightReg == Register.EAX) {
				cg.emit.emitComment("Divide by %EAX, which is saved on the stack");
				cg.emit.emit("idivl", AssemblyEmitter.registerOffset(-4, RegisterManager.STACK_REG));
			} else if (rightReg == Register.EDX) {
				cg.emit.emitComment("Divide by %EDX, which is saved on the stack");
				cg.emit.emit("idivl", AssemblyEmitter.registerOffset(-8, RegisterManager.STACK_REG));
			} else {
				cg.emit.emit("idivl", rightReg);
			}
			if (isModulo) {
				cg.emit.emitMove(Register.EDX, rightReg);
			} else {
				cg.emit.emitMove(Register.EAX, rightReg);
			}
			if (cg.rm.isInUse(Register.EAX) && Register.EAX != leftReg) {
				cg.emit.emitComment("Restore eax post div");
				cg.emit.emitMove(AssemblyEmitter.registerOffset(-4, RegisterManager.STACK_REG), Register.EAX);
			}
			if (cg.rm.isInUse(Register.EDX)) {
				cg.emit.emitComment("Restore edx post div");
				cg.emit.emitMove(AssemblyEmitter.registerOffset(-8, RegisterManager.STACK_REG), Register.EDX);
			}
			break;
		case B_PLUS:
			cg.emit.emit("add", rightReg, leftReg);
			break;

		case B_MINUS:
			cg.emit.emit("sub", rightReg, leftReg);
			break;

		case B_AND:
			cg.emit.emit("and", rightReg, leftReg);
            break;

		case B_OR:
            cg.emit.emit("or", rightReg, leftReg);
            break;

		case B_EQUAL:
            jmpOp = "je";
        case B_NOT_EQUAL:
            jmpOp = (jmpOp.isEmpty()) ? "jne" : jmpOp;
        case B_LESS_THAN:
            jmpOp = (jmpOp.isEmpty()) ? "jl" : jmpOp;
        case B_LESS_OR_EQUAL:
            jmpOp = (jmpOp.isEmpty()) ? "jle" : jmpOp;
        case B_GREATER_THAN:
            jmpOp = (jmpOp.isEmpty()) ? "jg" : jmpOp;
        case B_GREATER_OR_EQUAL:
            jmpOp = (jmpOp.isEmpty()) ? "jge" : jmpOp;

            // compare values and use a conditional jump to store correct boolean value in register
            String labelTrue = cg.emit.uniqueLabel();
            String labelFalse = cg.emit.uniqueLabel();
            cg.emit.emit("cmp", rightReg, leftReg);
            cg.emit.emit(jmpOp, labelTrue);
            cg.emit.emitMove(constant(0), leftReg);
            cg.emit.emit("jmp", labelFalse);
            cg.emit.emitLabel(labelTrue);
            cg.emit.emitMove(constant(1), leftReg);
            cg.emit.emitLabel(labelFalse);
            break;
		}

		cg.rm.releaseRegister(rightReg);

		return leftReg;
	}

	@Override
	public Register booleanConst(BooleanConst ast, Context ctx) {
        Register reg = cg.rm.getRegister();
        cg.emit.emitMove(ast.value ? constant(1) : constant(0), reg);
        return reg;
	}

	@Override
	public Register builtInRead(BuiltInRead ast, Context ctx) {
		Register reg = cg.rm.getRegister();
		cg.emit.emit("leal", AssemblyEmitter.registerOffset(8, STACK_REG), reg);

        List<String> arguments = new ArrayList<>();
        arguments.add(labelAddress("STR_D"));
        arguments.add(reg.repr);

		cg.beforeFunctionCall(arguments, ctx.stackOffset);

		cg.emit.emit("call", SCANF);

        // store value
        cg.emit.emitLoad(8, STACK_REG, reg);
        cg.afterFunctionCall(arguments, ctx.stackOffset);

		return reg;
	}

	@Override
	public Register cast(Cast ast, Context ctx) {
        // TODO exit with error code ExitCode.INVALID_DOWNCAST if runtime type is not a subtype of cast type
		{
			throw new ToDoException();
		}
	}

	@Override
	public Register index(Index ast, Context ctx) {

        boolean calculateValue = ctx.calculateValue;
        ctx.calculateValue = true;

        int leftRN = cg.rnv.calc(ast.left());
        int rightRN = cg.rnv.calc(ast.right());

        Register baseReg, offsetReg;
        if (leftRN > rightRN) {
            // generate code to get the base address of the array
            baseReg = visit(ast.left(), ctx);
            // generate code to get the offset in the array
            offsetReg = visit(ast.right(), ctx);
        } else {
            offsetReg = visit(ast.right(), ctx);
            baseReg = visit(ast.left(), ctx);
        }

        // TODO exit with error code ExitCode.INVALID_ARRAY_BOUNDS if array index is invalid
		// array structure on heap:
		//  --------------------  ptr +
		// | vtable ptr			| 0 - 4
		// | length of array	| 4 - 8
		// | data[0]			| 8 - 12
		// | ...				|
		// | data[length - 1]	|
		//  --------------------

        if (calculateValue) {
            cg.emit.emitMove(arrayAddress(baseReg, offsetReg), baseReg); // access array element
        } else {
            cg.emit.emit("leal", arrayAddress(baseReg, offsetReg), baseReg); // store address of array element
        }

        cg.rm.releaseRegister(offsetReg);
        return baseReg;
	}

	@Override
	public Register intConst(IntConst ast, Context ctx) {
        Register reg = cg.rm.getRegister();
        cg.emit.emitMove(constant(ast.value), reg);
        return reg;
	}

	@Override
	public Register field(Field ast, Context ctx) {
		boolean calculateValue = ctx.calculateValue;
		int offset = ((Symbol.ClassSymbol) ast.arg().type).oTable.getOffset(ast.fieldName);

		ctx.calculateValue = true;
		Register recv = visit(ast.arg(), ctx);
        if (calculateValue) {
            cg.emit.emitMove(registerOffset(offset, recv), recv); // access field
        } else {
            cg.emit.emit("leal", registerOffset(offset, recv), recv); // store address of field
        }
		return recv;
    }

	@Override
	public Register newArray(NewArray ast, Context ctx) {
        // TODO exit with error code ExitCode.INVALID_ARRAY_SIZE if array size is negative

		// array structure on heap:
		//  --------------------  ptr +
		// | vtable ptr			| 0 - 4
		// | length of array	| 4 - 8
		// | data[0]			| 8 - 12
		// | ...				|
		// | data[length - 1]	|
		//  --------------------

		{
			throw new ToDoException();
		}
	}

	@Override
	public Register newObject(NewObject ast, Context ctx) {
		if (!(ast.type instanceof Symbol.ClassSymbol)) {
			throw new ToDoException();
		}
		Symbol.ClassSymbol classSymbol = (Symbol.ClassSymbol) ast.type;

		// object structure on heap: TODO
		//  --------------------  ptr +
		// | vtable ptr			| 0 - 4
		// | data 				| 4 - ...
		//  --------------------

		List<String> arguments = new ArrayList<>();
		arguments.add(AssemblyEmitter.constant(classSymbol.oTable.getCount()));
		arguments.add(AssemblyEmitter.constant(Config.SIZEOF_PTR));

		cg.beforeFunctionCall(arguments, ctx.stackOffset);

		cg.emit.emit("call", Config.CALLOC);

		// store address in a new register
		Register reg = cg.rm.getRegister();
		cg.emit.emitMove(Register.EAX, reg);
		// store vtable ptr
		cg.emit.emit("leal", classSymbol.vTable.getVTableLabel(), Register.EAX);
		cg.emit.emitStore(Register.EAX, 0, reg);

		cg.afterFunctionCall(arguments, ctx.stackOffset);

		return reg;
	}

	@Override
	public Register nullConst(NullConst ast, Context ctx) {
		Register reg = cg.rm.getRegister();
		cg.emit.emitMove(constant(0), reg);
		return reg;
	}

	@Override
	public Register thisRef(ThisRef ast, Context ctx) {
		int offset = ctx.offsetTable.get("this");
		Register thisReg = cg.rm.getRegister();
		cg.emit.emitMove(String.format("%d(%s)", offset, BASE_REG), thisReg);
		return thisReg;
	}

	@Override
	public Register methodCall(MethodCallExpr ast, Context ctx) {
        List<Expr> arguments = ast.allArguments();

		ctx.stackOffset += cg.saveRegisters(CALLER_SAVE);

		int allocSpace = cg.calculateAllocSpace(arguments.size(), ctx.stackOffset);

		// make space for the arguments including alignment
		cg.emit.emitComment("Space for arguments and place arguments:");
		cg.emit.emit("subl", constant(allocSpace), STACK_REG);

		Register reg;
        for (int i = 0; i < arguments.size(); i++) {
			// evaluate this argument
            reg = visit(arguments.get(i), ctx);
			// and store it on the stack
			cg.emit.emitStore(reg, i * Config.SIZEOF_PTR, STACK_REG);
			cg.rm.releaseRegister(reg);
        }

		// call method
		reg = cg.rm.getRegister();
		cg.emit.emitLoad(0, STACK_REG, reg); // Load object pointer
		cg.emit.emitLoad(0, reg, reg); // Load vtable pointer
		int offset = ((Symbol.ClassSymbol) ast.receiver().type).vTable.getMethodOffset(ast.methodName);
		cg.emit.emit("addl", constant(offset), reg);
		cg.emit.emitLoad(0, reg, reg);
        cg.emit.emit("call", String.format("*%s", reg.repr));

        // store return value in a new register
        cg.emit.emitMove(Register.EAX, reg);

		cg.emit.emitComment("Reclaim space from arguments:");
		cg.emit.emit("addl", constant(allocSpace), STACK_REG);

		ctx.stackOffset += cg.restoreRegisters(CALLER_SAVE);

		return reg;
	}

	@Override
	public Register unaryOp(UnaryOp ast, Context ctx) {
        Register argReg = visit(ast.arg(), ctx);
        switch (ast.operator) {
        case U_PLUS:
            break;

        case U_MINUS:
            cg.emit.emit("negl", argReg);
            break;

        case U_BOOL_NOT:
            cg.emit.emit("negl", argReg);
            cg.emit.emit("incl", argReg);
            break;
        }

        return argReg;
	}
	
	@Override
	public Register var(Var ast, Context ctx) {
		Register reg = cg.rm.getRegister();
		int offset = ctx.offsetTable.get(ast.name);

        if (ctx.calculateValue) {
            cg.emit.emitMove(String.format("%d(%s)", offset, BASE_REG), reg); // access local variable
        } else {
            cg.emit.emit("leal", String.format("%d(%s)", offset, BASE_REG), reg); // store address of local variable
        }

        return reg;
    }
}
