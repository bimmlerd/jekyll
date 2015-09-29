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
import org.antlr.v4.codegen.CodeGenerator;

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
			Register r = visit((Expr) ast.rwChildren.get(1), null); // TODO right child first atm, implement counterVisitor?
			Register l = visit((Expr) ast.rwChildren.get(0), null);
			switch (ast.operator) {
				case B_PLUS:
					cg.emit.emit("addl", l, r);
					break;
				case B_MINUS:
					cg.emit.emit("subl", r, l);
					Register tmp = r;
					r = l; // Switch because the result is now in l
					l = tmp;
					break;
				case B_TIMES:
					cg.emit.emit("imul", l, r); // TODO this truncates the result to 32bit
					break;
				case B_DIV:
					throw new ToDoException();
				case B_MOD:
					throw new ToDoException();
				default:
					throw new ToDoException();
			}
/*			if (cg.vm.has(r)) {
				cg.vm.remove(r);
			}*/
			if (! cg.rm.isInUse(l)) {
				throw new ToDoException();
			}
			cg.rm.releaseRegister(l);
			/*if (cg.vm.has(l)) {
				cg.vm.remove(l);
			}*/
			return r;
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
