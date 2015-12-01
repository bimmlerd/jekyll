package cd.backend.codegen;

import cd.Config;
import cd.Main;
import cd.backend.codegen.RegisterManager.Register;
import cd.ir.Ast.ClassDecl;
import cd.ir.Symbol;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import static cd.Config.MAIN;
import static cd.backend.codegen.AssemblyEmitter.constant;
import static cd.backend.codegen.RegisterManager.STACK_REG;

public class AstCodeGenerator {

	protected RegsNeededVisitor rnv;
	
	protected ExprGenerator eg;
	protected StmtGenerator sg;
	
	protected final Main main;
	
	protected final AssemblyEmitter emit;
	protected final StackManager stack;
	protected final RegisterManager rm = new RegisterManager();

	AstCodeGenerator(Main main, Writer out) {
		{
			initMethodData();
		}
		
		this.emit = new AssemblyEmitter(out);
		this.stack = new StackManager(this);
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

		for (ClassDecl ast : astRoots) {
			sg.gen(ast);
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

		emit.emitRaw(Config.TEXT_SECTION);
		emit.emitRaw(String.format(".globl %s", MAIN));
		emit.emitLabel(MAIN);
		emit.emit("enter", constant(8), constant(0));
		emit.emit("and", -16, STACK_REG); // 1111...0000 -> align

		// create new Main object
		List<String> arguments = new ArrayList<>();
		arguments.add(AssemblyEmitter.constant(mainSymbol.oTable.getCount()));
		arguments.add(AssemblyEmitter.constant(Config.SIZEOF_PTR));
		stack.beforeFunctionCall(arguments);

		emit.emit("call", Config.CALLOC);

		// store address in a new register
		Register mainInstance = rm.getRegister();
		emit.emitMove(Register.EAX, mainInstance);

		stack.afterFunctionCall(arguments);

		// store vtable ptr
		// TODO no need to spill here, right?
		Register vTablePointer = rm.getRegister();
		emit.emitMove(mainSymbol.vTable.getVTableLabel(), vTablePointer);
		emit.emitStore(vTablePointer, 0, mainInstance);

		// call Main.main
		emit.emit("call", mainSymbol.vTable.getMethodLabel("main"));

		emitMethodSuffix(true);
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

		stack.restoreCalleeSavedRegs();

		emit.emitRaw("leave");
		emit.emitRaw("ret");
	}
}