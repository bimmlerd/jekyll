package cd.backend.codegen;

import cd.Config;
import cd.ToDoException;
import cd.backend.ExitCode;
import cd.backend.codegen.RegisterManager.*;
import cd.ir.Ast.*;
import cd.ir.ExprVisitor;
import cd.ir.Symbol;
import cd.util.Pair;
import cd.util.debug.AstOneLine;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static cd.Config.SCANF;
import static cd.Config.SIZEOF_PTR;
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
        // visit ast in the optimal order to save registers
		Pair<Register> pair = visitMoreExpensiveSideFirst(ast.left(), ast.right(), ctx);
		Register leftReg = pair.a;
		Register rightReg = pair.b;
		assert rightReg != leftReg;
		assert cg.rm.isInUse(rightReg);
		assert cg.rm.isInUse(leftReg);

		cg.debug("Binary Op: %s (%s,%s)", ast, leftReg, rightReg);

        String jmpOp = ""; // store opcode of jump to be performed after comparison of registers
		boolean isModulo = false;

		switch (ast.operator) {
		case B_TIMES:
			cg.emit.emit("imul", leftReg, rightReg);
			break;

		case B_MOD:
			isModulo = true;
		case B_DIV:
			String labelOK = cg.emit.uniqueLabel();
			cg.emit.emit("cmp", AssemblyEmitter.constant(0), rightReg);
			cg.emit.emit("jne", labelOK);

			emitExit(ExitCode.DIVISION_BY_ZERO, ctx);

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
			if (cg.rm.isInUse(Register.EDX) && Register.EDX != rightReg) {
				cg.emit.emitComment("Restore edx post div");
				cg.emit.emitMove(AssemblyEmitter.registerOffset(-8, RegisterManager.STACK_REG), Register.EDX);
			}
			break;
        case B_PLUS:
			cg.emit.emit("add", leftReg, rightReg);
			break;

		case B_MINUS:
            cg.emit.emit("sub", rightReg, leftReg);
            Register tmp = rightReg;
            rightReg = leftReg; // Switch because the result is now in l
            leftReg = tmp;
            break;

		case B_AND:
			cg.emit.emit("and", leftReg, rightReg);
            break;

		case B_OR:
            cg.emit.emit("or", leftReg, rightReg);
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
            cg.emit.emitMove(constant(0), rightReg);
            cg.emit.emit("jmp", labelFalse);
            cg.emit.emitLabel(labelTrue);
            cg.emit.emitMove(constant(1), rightReg);
            cg.emit.emitLabel(labelFalse);
            break;
		}

		assert rightReg != ctx.reservedRegister;

		cg.rm.releaseRegister(leftReg, ctx);

		return rightReg;
	}

	@Override
	public Register booleanConst(BooleanConst ast, Context ctx) {
        Register reg = cg.rm.getRegister(ctx);
        cg.emit.emitMove(ast.value ? constant(1) : constant(0), reg);
        return reg;
	}

	@Override
	public Register builtInRead(BuiltInRead ast, Context ctx) {
		Register reg = cg.rm.getRegister(ctx);

        List<String> arguments = new ArrayList<>();
        arguments.add(labelAddress("STR_D"));
        arguments.add(reg.repr);

        int allocSpace = cg.calculateAllocSpace(arguments.size(), ctx.stackOffset);

        // make space for the arguments including alignment
        cg.emit.emitComment("Space for arguments and place arguments:");
        cg.emit.emit("subl", constant(allocSpace), RegisterManager.STACK_REG);

        cg.emit.emit("leal", AssemblyEmitter.registerOffset(8, STACK_REG), reg);
        cg.emit.emitStore(reg, 4, STACK_REG);
        cg.emit.emitStore("$STR_D", 0, STACK_REG);

        cg.emit.emit("call", SCANF);

        // store value
        cg.emit.emitLoad(8, STACK_REG, reg);
        cg.afterFunctionCall(arguments, ctx.stackOffset);

		return reg;
	}

	@Override
	public Register cast(Cast ast, Context ctx) {
		Register right = visit(ast.arg(), ctx); // get address of object

		String labelErr = cg.emit.uniqueLabel();
		String labelOk = cg.emit.uniqueLabel();
		String labelLoop = cg.emit.uniqueLabel();

        Register castVTable = cg.rm.getRegister(ctx);
        Register objVTable = cg.rm.getRegister(ctx);

        // check if null
        cg.emit.emit("cmp", constant(0), right);
        cg.emit.emit("je", labelOk);

        // load cast-vtable
		String vTableLabel;
		Symbol.TypeSymbol typeSymbol = ast.type;
		if (typeSymbol instanceof Symbol.ArrayTypeSymbol) {
			vTableLabel = VTableBuilder.getArrayVTableLabelFromArray(typeSymbol);
		} else {
			vTableLabel = VTableBuilder.getVTableLabel(ast.typeName);
		}
        cg.emit.emit("leal", vTableLabel, castVTable);

        // load vtable ptr
		cg.emit.emitLoad(0, right, objVTable);

		cg.emit.emitLabel(labelLoop);

		// check equality
		cg.emit.emit("cmp", objVTable, castVTable);
		cg.emit.emit("je", labelOk);

		// fail, if no parent
		cg.emit.emit("cmpl", constant(0), registerOffset(0, objVTable));
		cg.emit.emit("je", labelErr);

		// load parent vtable
		cg.emit.emitLoad(0, objVTable, objVTable);
		cg.emit.emit("jmp", labelLoop);

		cg.emit.emitLabel(labelErr);
		emitExit(ExitCode.INVALID_DOWNCAST, ctx);

		cg.emit.emitLabel(labelOk);

        // release all registers we acquired for intermediate computations
        cg.rm.releaseRegister(objVTable, ctx);
        cg.rm.releaseRegister(castVTable, ctx);
		return right;
	}

	// array structure on heap:
	//  --------------------  ptr +
	// | vtable ptr			| 0 - 4
	// | length of array	| 4 - 8
	// | ...				|
	// | data[length - 1]	|
	//  --------------------
	@Override
	public Register index(Index ast, Context ctx) {
        boolean calculateValue = ctx.calculateValue;
        ctx.calculateValue = true;

        Pair<Register> pair = visitMoreExpensiveSideFirst(ast.left(), ast.right(), ctx);
		Register baseReg = pair.a; // base address of the array
		Register offsetReg = pair.b; // offset in the array
		assert baseReg != offsetReg;

        String labelOk = cg.emit.uniqueLabel();
		String labelNullErr = cg.emit.uniqueLabel();
        String labelInvalidBoundErr = cg.emit.uniqueLabel();

		// check if null pointer access
		cg.emit.emit("cmp", constant(0), baseReg);
		cg.emit.emit("je", labelNullErr);

        Register allowedSize = cg.rm.getRegister(ctx);

        cg.emit.emit("cmp", constant(0), offsetReg);
        cg.emit.emit("jl", labelInvalidBoundErr);

		cg.emit.emitLoad(SIZEOF_PTR, baseReg, allowedSize);
		cg.emit.emit("cmp", offsetReg, allowedSize);
		cg.emit.emit("jle", labelInvalidBoundErr);
		cg.emit.emit("jmp", labelOk);

		cg.emit.emitLabel(labelNullErr);
		emitExit(ExitCode.NULL_POINTER, ctx);

        cg.emit.emitLabel(labelInvalidBoundErr);
        emitExit(ExitCode.INVALID_ARRAY_BOUNDS, ctx);

        cg.emit.emitLabel(labelOk);

        if (calculateValue) {
            cg.emit.emitMove(arrayAddress(baseReg, offsetReg), baseReg); // access array element
        } else {
            cg.emit.emit("leal", arrayAddress(baseReg, offsetReg), baseReg); // store address of array element
        }

		if (baseReg != ctx.reservedRegister) {
			cg.rm.releaseRegister(offsetReg, ctx);
			cg.rm.releaseRegister(allowedSize, ctx);
			return baseReg;
		} else {
			cg.emit.emitMove(baseReg, offsetReg);
			cg.rm.releaseRegister(baseReg, ctx);
			cg.rm.releaseRegister(allowedSize, ctx);
			return offsetReg;
		}
	}

	@Override
	public Register intConst(IntConst ast, Context ctx) {
        Register reg = cg.rm.getRegister(ctx);
        cg.emit.emitMove(constant(ast.value), reg);
        return reg;
	}

	@Override
	public Register field(Field ast, Context ctx) {
		boolean calculateValue = ctx.calculateValue;
		Symbol.ClassSymbol classSymbol = (Symbol.ClassSymbol) ast.arg().type;
		int offset = -1;
		Symbol.ClassSymbol current = classSymbol;
		while (offset < 0 && current != Symbol.ClassSymbol.objectType) {
			offset = classSymbol.oTable.getOffset(String.format("%s.%s", current.name, ast.fieldName));
			current = current.superClass;
		}

		ctx.calculateValue = true;
		Register recv = visit(ast.arg(), ctx);

		String labelOk = cg.emit.uniqueLabel();

		cg.emit.emit("cmp", constant(0), recv);
		cg.emit.emit("jne", labelOk);

		emitExit(ExitCode.NULL_POINTER, ctx);

		cg.emit.emitLabel(labelOk);

        if (calculateValue) {
            cg.emit.emitMove(registerOffset(offset, recv), recv); // access field
        } else {
            cg.emit.emit("leal", registerOffset(offset, recv), recv); // store address of field
        }
		return recv;
    }


	// array structure on heap:
	//  --------------------  ptr +
	// | vtable ptr			| 0 - 4
	// | length of array	| 4 - 8
	// | data[0]			| 8 - 12
	// | ...				|
	// | data[length - 1]	|
	//  --------------------
	@Override
	public Register newArray(NewArray ast, Context ctx) {

		String labelOK = cg.emit.uniqueLabel();

        // calculate size
		Register size = visit(ast.arg(), ctx);

		cg.emit.emit("cmp", constant(0), size);
		cg.emit.emit("jge", labelOK);
		emitExit(ExitCode.INVALID_ARRAY_SIZE, ctx);

		cg.emit.emitLabel(labelOK);

		// add two to size for vtable and length of array
		cg.emit.emit("add", constant(2), size);

		List<String> arguments = new ArrayList<>();
		arguments.add(size.repr);
		arguments.add(AssemblyEmitter.constant(Config.SIZEOF_PTR));

		Stack<Register> registerStack = storeUsedCallerSavedRegisters(ctx);
		cg.beforeFunctionCall(arguments, ctx.stackOffset);

		cg.emit.emit("call", Config.CALLOC);

		// store address
		Register address = cg.rm.getRegister(ctx);
		cg.emit.emitMove(Register.EAX, address);

		// store vtable ptr
		cg.emit.emit("leal", VTableBuilder.getArrayVTableLabelFromArray(ast.type), Register.EAX);
		cg.emit.emitStore(Register.EAX, 0, address);

		cg.afterFunctionCall(arguments, ctx.stackOffset);
		restoreUsedCallerSavedRegisters(registerStack, ctx);

		// store size
		cg.emit.emit("sub", constant(2), size);
		cg.emit.emitStore(size, SIZEOF_PTR, address);

		cg.rm.releaseRegister(size, ctx);

		return address;
	}

	// object structure on heap:
	//  --------------------  ptr +
	// | vtable ptr			| 0 - 4
	// | data 				| 4 - ...
	//  --------------------
	@Override
	public Register newObject(NewObject ast, Context ctx) {
		if (!(ast.type instanceof Symbol.ClassSymbol)) {
			throw new ToDoException();
		}
		Symbol.ClassSymbol classSymbol = (Symbol.ClassSymbol) ast.type;

		List<String> arguments = new ArrayList<>();
		arguments.add(AssemblyEmitter.constant(classSymbol.oTable.getCount()));
		arguments.add(AssemblyEmitter.constant(Config.SIZEOF_PTR));

		Stack<Register> registerStack = storeUsedCallerSavedRegisters(ctx);
		cg.beforeFunctionCall(arguments, ctx.stackOffset);

		cg.emit.emit("call", Config.CALLOC);

		// store address in a new register
		Register reg = cg.rm.getRegister(ctx);
		cg.emit.emitMove(Register.EAX, reg);
		// store vtable ptr
		cg.emit.emit("leal", classSymbol.vTable.getVTableLabel(), Register.EAX);
		cg.emit.emitStore(Register.EAX, 0, reg);

		cg.afterFunctionCall(arguments, ctx.stackOffset);
		restoreUsedCallerSavedRegisters(registerStack, ctx);

		return reg;
	}

	private Stack<Register> storeUsedCallerSavedRegisters(Context ctx) {
		cg.emit.emitComment("Saving Caller Saved Registers:");
		Stack<Register> toPop = new Stack<>();
		for (Register reg : CALLER_SAVE) {
			if (cg.rm.isInUse(reg)) {
				cg.emit.emit("push", reg);
				ctx.stackOffset -= Config.SIZEOF_PTR;
				toPop.push(reg);
			}
		}
		return toPop;
	}

	private void restoreUsedCallerSavedRegisters(Stack<Register> toPop, Context ctx) {
		cg.emit.emitComment("Restoring Caller Saved Registers:");
		while (! toPop.empty()) {
			cg.emit.emit("pop", toPop.pop());
			ctx.stackOffset += Config.SIZEOF_PTR;
		}
	}

	@Override
	public Register nullConst(NullConst ast, Context ctx) {
		Register reg = cg.rm.getRegister(ctx);
		cg.emit.emitMove(constant(0), reg);
		return reg;
	}

	@Override
	public Register thisRef(ThisRef ast, Context ctx) {
		int offset = ctx.offsetTable.get("this");
		Register thisReg = cg.rm.getRegister(ctx);
		cg.emit.emitMove(registerOffset(offset, BASE_REG), thisReg);
		return thisReg;
	}

	@Override
	public Register methodCall(MethodCallExpr ast, Context ctx) {
        List<Expr> arguments = ast.argumentsWithoutReceiver();

        Stack<Register> registerStack = storeUsedCallerSavedRegisters(ctx);

		int allocSpace = cg.calculateAllocSpace(ast.allArguments().size(), ctx.stackOffset);

		// make space for the arguments including alignment
		cg.emit.emitComment("Space for arguments and place arguments:");
		cg.emit.emit("subl", constant(allocSpace), STACK_REG);
		ctx.stackOffset -= allocSpace;

		Register reg = visit(ast.receiver(), ctx);
		cg.emit.emitStore(reg, 0, STACK_REG);
		cg.rm.releaseRegister(reg, ctx);

		String labelOk = cg.emit.uniqueLabel();

		cg.emit.emit("cmp", constant(0), reg);
		cg.emit.emit("jne", labelOk);

		emitExit(ExitCode.NULL_POINTER, ctx);

		cg.emit.emitLabel(labelOk);
        for (int i = 0; i < arguments.size(); i++) {
			// evaluate this argument
            reg = visit(arguments.get(i), ctx);
			// and store it on the stack
			cg.emit.emitStore(reg, (i + 1) * Config.SIZEOF_PTR, STACK_REG);
			cg.rm.releaseRegister(reg, ctx);
        }

		// call method
		reg = cg.rm.getRegister(ctx);
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
        ctx.stackOffset += allocSpace;

        restoreUsedCallerSavedRegisters(registerStack, ctx);

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
		Register reg = cg.rm.getRegister(ctx);
		int offset = ctx.offsetTable.get(ast.name);

        if (ctx.calculateValue) {
            cg.emit.emitMove(registerOffset(offset, BASE_REG), reg); // access local variable
        } else {
            cg.emit.emit("leal", registerOffset(offset, BASE_REG), reg); // store address of local variable
        }

        return reg;
    }

	void emitExit(ExitCode exitCode, Context ctx) {
		List<String> arguments = new ArrayList<>();
		arguments.add(AssemblyEmitter.constant(exitCode.value));
		cg.beforeFunctionCall(arguments, ctx.stackOffset);
		cg.emit.emit("call", Config.EXIT);
	}

	private Pair<Register> visitMoreExpensiveSideFirst(Expr left, Expr right, Context ctx) {
		int leftRN = cg.rnv.calc(left);
		int rightRN = cg.rnv.calc(right);

		Expr first, second;
		if (leftRN > rightRN) {
			first = left;
			second = right;
		} else {
			second = left;
			first = right;
		}

		Register firstReg, lastReg;
		firstReg = visit(first, ctx);
		assert cg.rm.isInUse(firstReg);

		Register oldReserved = ctx.reservedRegister;
		ctx.reservedRegister = firstReg;

		Stack<Register> spilledRegs = new Stack<>();
		int regCount = cg.rm.availableRegisters();

		while (regCount < 2) {
			spilledRegs.push(cg.rm.spillRegister(ctx));
			regCount++;
		}

		//ctx.spilledRegisters.clear();
		assert cg.rm.isInUse(firstReg);
		lastReg = visit(second, ctx);
		if (!cg.rm.isInUse(firstReg)) {
			assert ctx.spilledRegisters.contains(firstReg);
			cg.rm.unspillRegister(firstReg, ctx);
		}
		assert cg.rm.isInUse(lastReg);
		assert lastReg != ctx.reservedRegister;

		// restore old reserved
		ctx.reservedRegister = oldReserved;

		ctx.spilledRegisters = spilledRegs;

		if (leftRN > rightRN) {
			return new Pair<>(firstReg, lastReg);
		} else {
			return new Pair<>(lastReg, firstReg);
		}
	}
}
