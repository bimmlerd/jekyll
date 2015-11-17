package cd.backend.codegen;

import static cd.Config.SCANF;
import static cd.backend.codegen.AssemblyEmitter.arrayAddress;
import static cd.backend.codegen.AssemblyEmitter.constant;
import static cd.backend.codegen.RegisterManager.STACK_REG;
import cd.ToDoException;
import cd.backend.codegen.RegisterManager.Register;
import cd.ir.Ast.BinaryOp;
import cd.ir.Ast.BooleanConst;
import cd.ir.Ast.BuiltInRead;
import cd.ir.Ast.Cast;
import cd.ir.Ast.Expr;
import cd.ir.Ast.Field;
import cd.ir.Ast.Index;
import cd.ir.Ast.IntConst;
import cd.ir.Ast.MethodCallExpr;
import cd.ir.Ast.NewArray;
import cd.ir.Ast.NewObject;
import cd.ir.Ast.NullConst;
import cd.ir.Ast.ThisRef;
import cd.ir.Ast.UnaryOp;
import cd.ir.Ast.Var;
import cd.ir.ExprVisitor;
import cd.util.debug.AstOneLine;

/**
 * Generates code to evaluate expressions. After emitting the code, returns a
 * Register where the result can be found.
 */
class ExprGenerator extends ExprVisitor<Register, Void> {
	protected final AstCodeGenerator cg;

	ExprGenerator(AstCodeGenerator astCodeGenerator) {
		cg = astCodeGenerator;
	}

	public Register gen(Expr ast) {
		return visit(ast, null);
	}

	@Override
	public Register visit(Expr ast, Void arg) {
		try {
			cg.emit.increaseIndent("Emitting " + AstOneLine.toString(ast));
			return super.visit(ast, null);
		} finally {
			cg.emit.decreaseIndent();
		}

	}

	@Override
	public Register binaryOp(BinaryOp ast, Void arg) {
		// Simplistic HW1 implementation that does
		// not care if it runs out of registers, and
		// supports only a limited range of operations:

		int leftRN = cg.rnv.calc(ast.left());
		int rightRN = cg.rnv.calc(ast.right());

		Register leftReg, rightReg;
		if (leftRN > rightRN) {
			leftReg = gen(ast.left());
			rightReg = gen(ast.right());
		} else {
			rightReg = gen(ast.right());
			leftReg = gen(ast.left());
		}

		cg.debug("Binary Op: %s (%s,%s)", ast, leftReg, rightReg);

        // store opcode of jump to be performed after comparison of registers
        String jmpOp = "";

		switch (ast.operator) {
		case B_TIMES:
			cg.emit.emit("imul", rightReg, leftReg);
			break;

		case B_DIV:
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
	public Register booleanConst(BooleanConst ast, Void arg) {
        Register reg = cg.rm.getRegister();
        cg.emit.emit("movl", ast.value ? constant(1) : constant(0), reg);
        return reg;
	}

	@Override
	public Register builtInRead(BuiltInRead ast, Void arg) {
		Register reg = cg.rm.getRegister();
		cg.emit.emit("sub", constant(16), STACK_REG);
		cg.emit.emit("leal", AssemblyEmitter.registerOffset(8, STACK_REG), reg);
		cg.emit.emitStore(reg, 4, STACK_REG);
		cg.emit.emitStore("$STR_D", 0, STACK_REG);
		cg.emit.emit("call", SCANF);
		cg.emit.emitLoad(8, STACK_REG, reg);
		cg.emit.emit("add", constant(16), STACK_REG);
		return reg;
	}

	@Override
	public Register cast(Cast ast, Void arg) {
		{
			throw new ToDoException();
		}
	}

	@Override
	public Register index(Index ast, Void arg) {

        int leftRN = cg.rnv.calc(ast.left());
        int rightRN = cg.rnv.calc(ast.right());

        Register baseReg, offsetReg;
        if (leftRN > rightRN) {
            // generate code to get the base address of the array
            baseReg = gen(ast.left());
            // generate code to get the offset in the array
            offsetReg = gen(ast.right());
        } else {
            offsetReg = gen(ast.right());
            baseReg = gen(ast.left());
        }

        // access array element
        cg.emit.emitMove(arrayAddress(baseReg, offsetReg), baseReg);

        cg.rm.releaseRegister(offsetReg);
        return baseReg;
	}

	@Override
	public Register intConst(IntConst ast, Void arg) {
        Register reg = cg.rm.getRegister();
        cg.emit.emit("movl", constant(ast.value), reg);
        return reg;
	}

	@Override
	public Register field(Field ast, Void arg) {
		{
			throw new ToDoException();
		}
	}

	@Override
	public Register newArray(NewArray ast, Void arg) {
		{
			throw new ToDoException();
		}
	}

	@Override
	public Register newObject(NewObject ast, Void arg) {
		{
			throw new ToDoException();
		}
	}

	@Override
	public Register nullConst(NullConst ast, Void arg) {
		Register reg = cg.rm.getRegister();
		cg.emit.emit("movl", constant(0), reg);
		return reg;
	}

	@Override
	public Register thisRef(ThisRef ast, Void arg) {
		{
			throw new ToDoException();
		}
	}

	@Override
	public Register methodCall(MethodCallExpr ast, Void arg) {
		// function call; method(0x5, 0x10);
		// subl $0x8, %esp      # Reserve space for the arguments (4 bytes for each arg).
		// movl $0x10, 4(%esp)  # Put the second argument at the memory address %esp + 4.
		// movl $0x5, (%esp)    # Put the first argument at the memory address %esp

		// push %eax            # Save %eax before a function call.
		// push %ecx            # Save %ecx before a function call.
		// push %edx            # Save %edx before a function call.

		// call method

		// save return value (eax) somewhere

		// pop %edx				# Restore %edx after a function call.
		// pop %ecx				# Restore %ecx after a function call.
		// pop %eax				# Restore %eax after a function call.

		// addl $0x8, %esp      # Reclaim stack space reserved for arguments.
		throw new ToDoException();
	}

	@Override
	public Register unaryOp(UnaryOp ast, Void arg) {
        Register argReg = gen(ast.arg());
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
	public Register var(Var ast, Void arg) {
		Register reg = cg.rm.getRegister();
        // TODO: locals are no longer in the data section
		cg.emit.emit("movl", AstCodeGenerator.VAR_PREFIX + ast.name, reg);
		return reg;
	}
}
