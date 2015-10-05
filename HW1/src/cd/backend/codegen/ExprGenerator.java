package cd.backend.codegen;

import cd.Config;
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
 * String which indicates the register where the result can be found.
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
		{
			Register reg_left, reg_right;
			CounterVisitor cv = new CounterVisitor();

			Expr l = (Expr) ast.rwChildren.get(0);
			Expr r = (Expr) ast.rwChildren.get(1);

			if (l.registerCount < 0) {
				cv.visit(l, null);
			}
			if (r.registerCount < 0) {
				cv.visit(r, null);
			}

			if (l.registerCount >= r.registerCount) {
				reg_left = visit((Expr) ast.rwChildren.get(0), null);
				reg_right = visit((Expr) ast.rwChildren.get(1), null);
			} else {
				reg_right = visit((Expr) ast.rwChildren.get(1), null);
				reg_left = visit((Expr) ast.rwChildren.get(0), null);
			}

			switch (ast.operator) {
				case B_PLUS:
					cg.emit.emit("addl", reg_left, reg_right);
					break;
				case B_MINUS:
					cg.emit.emit("subl", reg_right, reg_left);
					Register tmp = reg_right;
					reg_right = reg_left; // Switch because the result is now in l
					reg_left = tmp;
					break;
				case B_TIMES:
					cg.emit.emit("imul", reg_left, reg_right); // this truncates the result to 32bit
					break;
				case B_DIV:
					if (cg.rm.isInUse(Register.EAX)) {
						cg.emit.emitComment("Save eax prior to div");
						cg.emit.emitStore(Register.EAX, -4, RegisterManager.STACK_REG);
					}
					if (cg.rm.isInUse(Register.EDX)) {
						cg.emit.emitComment("Save edx prior to div");
						cg.emit.emitStore(Register.EDX, -8, RegisterManager.STACK_REG);
					}
					cg.emit.emitMove(AssemblyEmitter.constant(0), Register.EDX);
					cg.emit.emitMove(reg_left, Register.EAX);
					cg.emit.emit("idiv", reg_right);
					cg.emit.emitMove(Register.EAX, reg_right);
					if (cg.rm.isInUse(Register.EAX)) {
						cg.emit.emitComment("Restore eax post div");
						cg.emit.emitMove(AssemblyEmitter.registerOffset(-4, RegisterManager.STACK_REG), Register.EAX);
					}
					if (cg.rm.isInUse(Register.EDX)) {
						cg.emit.emitComment("Restore edx post div");
						cg.emit.emitMove(AssemblyEmitter.registerOffset(-8, RegisterManager.STACK_REG), Register.EDX);
					}
					break;
				default:
					throw new ToDoException();
			}
/*			if (cg.vm.has(r)) {
				cg.vm.remove(r);
			}*/
			cg.rm.releaseRegister(reg_left);
			/*if (cg.vm.has(l)) {
				cg.vm.remove(l);
			}*/
			return reg_right;
		}
	}

	@Override
	public Register booleanConst(BooleanConst ast, Void arg) {
		{
			throw new RuntimeException("Not required");
		}
	}

	@Override
	public Register builtInRead(BuiltInRead ast, Void arg) {
		{
			// TODO how can we do this cleaner? atm we write the var onto the stack in the assign statement!
			cg.emit.emitStore(AssemblyEmitter.labelAddress(StmtGenerator.WRITE_STRING_LABEL), 0, RegisterManager.STACK_REG);
			cg.emit.emitCall(Config.SCANF);
			return null;
		}
	}

	@Override
	public Register cast(Cast ast, Void arg) {
		{
			throw new RuntimeException("Not required");
		}
	}

	@Override
	public Register index(Index ast, Void arg) {
		{
			throw new RuntimeException("Not required");
		}
	}

	@Override
	public Register intConst(IntConst ast, Void arg) {
		{
			Register r = cg.rm.getRegister();
			cg.emit.emitMove(AssemblyEmitter.constant(ast.value), r);
			return r;
		}
	}

	@Override
	public Register field(Field ast, Void arg) {
		{
			throw new RuntimeException("Not required");
		}
	}

	@Override
	public Register newArray(NewArray ast, Void arg) {
		{
			throw new RuntimeException("Not required");
		}
	}

	@Override
	public Register newObject(NewObject ast, Void arg) {
		{
			throw new RuntimeException("Not required");
		}
	}

	@Override
	public Register nullConst(NullConst ast, Void arg) {
		{
			throw new RuntimeException("Not required");
		}
	}

	@Override
	public Register thisRef(ThisRef ast, Void arg) {
		{
			throw new RuntimeException("Not required");
		}
	}

	@Override
	public Register unaryOp(UnaryOp ast, Void arg) {
		{
			Register r = visit((Expr) ast.rwChildren.get(0), null);
			switch (ast.operator) {
				case U_MINUS:
					cg.emit.emit("neg", r);
					break;
				case U_PLUS:
					break;
				default:
					throw new ToDoException();
			}
			/*if (cg.vm.has(r)) {
				cg.vm.remove(r);
			}*/
			return r;
		}
	}
	
	@Override
	public Register var(Var ast, Void arg) {
		{
			/*Register r;
			if (! cg.vm.has(ast)) {
				r = cg.rm.getRegister();
				cg.emit.emitMove(AssemblyEmitter.labelAddress(ast.name), r);
				cg.emit.emitLoad(0,r,r);
				cg.vm.add(ast, r);
			} else {
				r = cg.vm.get(ast);
			}*/
			Register r;
			r = cg.rm.getRegister();
			cg.emit.emitMove(String.format("(%s)", ast.name), r);
			return r;

		}
	}

}
