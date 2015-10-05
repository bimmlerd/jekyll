package cd.backend.codegen;

import cd.Main;
import cd.ir.Ast.ClassDecl;

import java.io.Writer;
import java.util.List;

public class AstCodeGenerator {

	protected ExprGenerator eg;
	protected StmtGenerator sg;
	
	protected final Main main;
	
	protected final AssemblyEmitter emit;
	protected final RegisterManager rm = new RegisterManager();
	protected VariableManager vm = new VariableManager();

	AstCodeGenerator(Main main, Writer out) {
		this.emit = new AssemblyEmitter(out);
		this.main = main;
		this.eg = new ExprGenerator(this);
		this.sg = new StmtGenerator(this);
	}

	protected void debug(String format, Object... args) {
		this.main.debug(format, args);
	}

	public static AstCodeGenerator createCodeGenerator(Main main, Writer out) {
		return new AstCodeGenerator(main, out);
	}
	
	
	/**
	 * Main method. Causes us to emit x86 assembly corresponding to {@code ast}
	 * into {@code file}. Throws a {@link RuntimeException} should any I/O error
	 * occur
	 */
	public void go(List<? extends ClassDecl> astRoots) {
		this.rm.initRegisters();
		for (ClassDecl ast : astRoots) {
			sg.gen(ast);
		}
	}

}