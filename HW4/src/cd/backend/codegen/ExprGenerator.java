package cd.backend.codegen;

import cd.Config;
import cd.ToDoException;
import cd.backend.codegen.RegisterManager.Register;
import cd.ir.Ast.*;
import cd.ir.ExprVisitor;
import cd.ir.Symbol;
import cd.util.debug.AstOneLine;

import java.util.ArrayList;
import java.util.List;

import static cd.Config.SCANF;
import static cd.backend.codegen.AssemblyEmitter.*;
import static cd.backend.codegen.RegisterManager.BASE_REG;
import static cd.backend.codegen.RegisterManager.STACK_REG;

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

		switch (ast.operator) {
		case B_TIMES:
			cg.emit.emit("imul", rightReg, leftReg);
			break;

		case B_DIV:
            // TODO exit with error code ExitCode.DIVISION_BY_ZERO if divisor is zero
			throw new ToDoException();
		case B_MOD:
			throw new ToDoException();

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
        cg.stack.beforeFunctionCall(arguments);

		cg.emit.emit("call", SCANF);

        // store value
        cg.emit.emitLoad(8, STACK_REG, reg);
        cg.stack.afterFunctionCall(arguments);

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
		{
			throw new ToDoException();
		}

/* TODO
        if (ctx.calculateValue) {
            cg.emit.emitMove(arrayAddress(baseReg, offsetReg), baseReg); // access array element
        } else {
            cg.emit.emit("leal", arrayAddress(baseReg, offsetReg), baseReg); // store address of array element
        }
*/

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
		cg.stack.beforeFunctionCall(arguments);

		cg.emit.emit("call", Config.CALLOC);

		// store address in a new register
		Register reg = cg.rm.getRegister();
		cg.emit.emitMove(Register.EAX, reg);

		cg.stack.afterFunctionCall(arguments);

		// store vtable ptr
		//classSymbol.vTable.getVTableLabel()

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
		{
			throw new ToDoException();
		}
	}

	@Override
	public Register methodCall(MethodCallExpr ast, Context ctx) {
        List<String> arguments = new ArrayList<>();

        if (!(ast.receiver().type instanceof Symbol.ClassSymbol)) {
            // should be caught by semantic analyzer
            throw new ToDoException();
        }
        Register reg = visit(ast.receiver(), ctx);
        arguments.add(reg.repr);
        // TODO target is determined by the runtime type of the object instance
        // get class name to construct label
        String recv = "$";

        for (Expr argExpr : ast.argumentsWithoutReceiver()) {
            // generate code to evaluate all arguments
            reg = visit(argExpr, ctx);
            arguments.add(reg.repr);
        }

        // function call; method(0x5, 0x10);
        // push %eax            # Save %eax before a function call.
        // push %ecx            # Save %ecx before a function call.
        // push %edx            # Save %edx before a function call.

        // subl $0x8, %esp      # Reserve space for the arguments (4 bytes for each arg).
        // movl $0x10, 4(%esp)  # Put the second argument at the memory address %esp + 4.
        // movl $0x5, (%esp)    # Put the first argument at the memory address %esp

        // put arguments on the stack and save caller saved registers before making the call
        cg.stack.beforeFunctionCall(arguments);

        // call method

		// TODO don't do the name logic yourself, use getMethodLabel
        cg.emit.emit("call", String.format("%s$%s", recv, ast.methodName));

        // movl %eax, ...       # save return value (eax) somewhere

        // store return value in a new register
        reg = cg.rm.getRegister();
        cg.emit.emitMove(Register.EAX, reg);

        // addl $0x8, %esp      # Reclaim stack space reserved for arguments.
        // pop %edx				# Restore %edx after a function call.
        // pop %ecx				# Restore %ecx after a function call.
        // pop %eax				# Restore %eax after a function call.

        cg.stack.afterFunctionCall(arguments);

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
		int offset = cg.stack.getOffsetForLocal(ast.name);

        if (ctx.calculateValue) {
            cg.emit.emitMove(String.format("%d(%s)", offset, BASE_REG), reg); // access local variable
        } else {
            cg.emit.emit("leal", String.format("%d(%s)", offset, BASE_REG), reg); // store address of local variable
        }

        return reg;
    }
}
