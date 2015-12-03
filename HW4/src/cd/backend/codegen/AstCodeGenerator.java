package cd.backend.codegen;

import cd.Config;
import cd.Main;
import cd.backend.ExitCode;
import cd.backend.codegen.RegisterManager.*;
import cd.ir.Ast.ClassDecl;
import cd.ir.Symbol;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import static cd.Config.MAIN;
import static cd.backend.codegen.AssemblyEmitter.constant;
import static cd.backend.codegen.AssemblyEmitter.registerOffset;
import static cd.backend.codegen.RegisterManager.*;

public class AstCodeGenerator {

	protected RegsNeededVisitor rnv;
	
	protected ExprGenerator eg;
	protected StmtGenerator sg;
	
	protected final Main main;
	
	protected final AssemblyEmitter emit;
	protected final RegisterManager rm = new RegisterManager();

	AstCodeGenerator(Main main, Writer out) {
		{
			initMethodData();
		}
		
		this.emit = new AssemblyEmitter(out);
		this.main = main;
		this.rnv = new RegsNeededVisitor();

		this.eg = new ExprGenerator(this);
		this.sg = new StmtGenerator(this);
	}

	protected void debug(String format, Object... args) {
		this.main.debug(format, args);
	}

	public static AstCodeGenerator createCodeGenerator(Main main, Writer out) {
		return new AstCodeGenerator(main, out);
	}
	
	protected static final String VAR_PREFIX = "var_";
	
	/**
	 * Main method. Causes us to emit x86 assembly corresponding to {@code ast}
	 * into {@code file}. Throws a {@link RuntimeException} should any I/O error
	 * occur.
	 * 
	 * <p>
	 * The generated file will be divided into three sections:
	 * <ol>
	 * <li>Prologue: Generated by {@link #emitPrologue()}. This contains any
	 * introductory declarations and the like.
	 * <li>Body: Generated by {@link ExprGenerator}. This contains the main
	 * method definitions.
	 * <li>Epilogue: Generated by {@link #emitEpilogue()}. This contains any
	 * final declarations required.
	 * </ol>
	 */
	public void go(List<ClassDecl> astRoots) {
		new VTableBuilder(this).emitVTables(astRoots);

		for (ClassDecl classDecl : astRoots) {
			if (classDecl.name.equals("Main")) {
				mainSymbol = classDecl.sym;
				break;
			}
		}

		emitPrologue();

		emit.emitCommentSection("Body");

        Context ctx;
		for (ClassDecl ast : astRoots) {
			ctx = new Context(ast.sym, true); // context information for current class
			sg.visit(ast, ctx);
		}

		emit.emitCommentSection("Epilogue");

		emitEpilogue();
	}

	private Symbol.ClassSymbol mainSymbol;


	protected void initMethodData() {
		rm.initRegisters();
	}


	protected void emitPrologue() {
		emit.emitCommentSection("Prologue");

		Context ctx = new Context(null);
		// get all registers we need for the prologue
		Register mainInstance = rm.getRegister(ctx);
		Register vTablePointer = rm.getRegister(ctx);

		emit.emitRaw(Config.TEXT_SECTION);
		emit.emitRaw(String.format(".globl %s", MAIN));
		emit.emitLabel(MAIN);
		emit.emit("enter", constant(8), constant(0));
		emit.emit("and", -16, STACK_REG); // 1111...0000 -> align

		// create new Main object
		List<String> arguments = new ArrayList<>();
		arguments.add(AssemblyEmitter.constant(mainSymbol.oTable.getCount()));
		arguments.add(AssemblyEmitter.constant(Config.SIZEOF_PTR));

		beforeFunctionCall(arguments, 0);

		emit.emit("call", Config.CALLOC); // allocate space for the instance of the Main class

		// store address in a new register
		emit.emitMove(Register.EAX, mainInstance);

		afterFunctionCall(arguments, 0);

		// store vtable ptr
		emit.emit("leal", mainSymbol.vTable.getVTableLabel(), vTablePointer);
		emit.emitStore(vTablePointer, 0, mainInstance);

		// put 'this' on stack
		emit.emitStore(mainInstance, 0, STACK_REG);

		emit.emit("call", mainSymbol.vTable.getMethodLabel("main")); // execute the main() method on the newly create instance

		// TODO: do we need to check the return value of main()?
		// if we return normally from the main() function with the value 0, no error occurred and we can terminate the execution
		emit.emitStore(AssemblyEmitter.constant(ExitCode.OK.value), 0, STACK_REG);
		emit.emit("call", Config.EXIT);

		// release all register we needed for the prologue
		rm.releaseRegister(vTablePointer, ctx);
		rm.releaseRegister(mainInstance, ctx);
	}

	protected void emitEpilogue() {
		// Emit some useful string constants:
		emit.emitRaw(Config.DATA_STR_SECTION);
		emit.emitLabel("STR_NL");
		emit.emitRaw(Config.DOT_STRING + " \"\\n\"");
		emit.emitLabel("STR_D");
		emit.emitRaw(Config.DOT_STRING + " \"%d\"");
	}

	protected void emitMethodSuffix(boolean returnNull) {
		if (returnNull) {
			emit.emitMove(constant(0), Register.EAX);
		}

		restoreRegisters(CALLEE_SAVE);

		emit.emitRaw("leave");
		emit.emitRaw("ret");
	}

	/**
	 * Stores registers on the stack.
	 * @param collection array of registers to save
	 * @return the offset it caused (i.e. -12)
	 */
	public int saveRegisters(Register[] collection) {
		int offset = 0;
		emit.emitComment("Saving Registers:");
		for (Register reg : collection) {
			emit.emit("push", reg);
			offset -= Config.SIZEOF_PTR;
		}
		return offset;
	}

	/**
	 * Restores saved registers in reversed order as they are stored.
	 * @param collection array of registers to restore
	 * @return the offset it caused (i.e. +12)
	 */
	public int restoreRegisters(Register[] collection) {
		int offset = 0;
		emit.emitComment("Restoring Saved Registers:");
		for (int i = collection.length - 1; i >= 0; i--) {
			emit.emit("pop", collection[i]);
			offset += Config.SIZEOF_PTR;
		}
		return offset;
	}

	/**
	 * Emits the necessary code for calling a library function
	 * @implNote due to the requirement of having all the arguments in a list, it cannot be
	 * 	used for arbitrary function calls
	 * @param arguments contains the arguments passed to the library function (C-like order)
	 * @param offset stores the current stack offset
	 */
	public void beforeFunctionCall(List<String> arguments, int offset) {

		int allocSpace = calculateAllocSpace(arguments.size(), offset);

		// make space for the arguments including alignment
		emit.emitComment("Space for arguments and place arguments:");
		emit.emit("subl", constant(allocSpace), RegisterManager.STACK_REG);

		// place arguments
		for (int i = 0; i < arguments.size(); i++) {
			emit.emitStore(arguments.get(i), i * Config.SIZEOF_PTR, RegisterManager.STACK_REG);
		}
	}

	/**
	 * Reverses the effects of {@link #beforeFunctionCall(List, int)}
	 * @param arguments the arguments passed to the library function
	 * @param offset the current offset
	 */
	public void afterFunctionCall(List<String> arguments, int offset) {
		int allocSpace = calculateAllocSpace(arguments.size(), offset);

		emit.emitComment("Reclaim space from arguments:");
		emit.emit("addl", constant(allocSpace), RegisterManager.STACK_REG);
	}

	/**
	 * @implNote Assumes that the offset is negative
	 * @param argCount number of 4-byte arguments
	 * @param offset current stack offset (assumed to be negative)
	 * @return adjustment needed to be at a 16-byte aligned value
	 */
	protected int calculateAllocSpace(int argCount, int offset) {
		int argSpace = argCount * Config.SIZEOF_PTR;
		int adjustment = 16 + (offset - argSpace) % 16;
		return adjustment + argSpace;
	}
}