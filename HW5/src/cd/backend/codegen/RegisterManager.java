package cd.backend.codegen;

import cd.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Simple class that manages the set of currently used
 * and unused registers
 */
public class RegisterManager {
	private final AstCodeGenerator cg;

	public RegisterManager(AstCodeGenerator cg) {
		this.cg = cg;
	}

	private List<Register> registers = new ArrayList<Register>();

	// lists of register to save by the callee and the caller
	public static final Register CALLEE_SAVE[] = new Register[]{Register.ESI,
			Register.EDI, Register.EBX};
	public static final Register CALLER_SAVE[] = new Register[]{Register.EAX,
			Register.ECX, Register.EDX};
	
	// list of general purpose registers
	public static final Register GPR[] = new Register[]{Register.EAX, Register.EBX,
		Register.ECX, Register.EDX, Register.ESI, Register.EDI};

	// special purpose registers
	public static final Register BASE_REG = Register.EBP;
	public static final Register STACK_REG = Register.ESP;

	public static final int SIZEOF_REG = 4;

	
	public enum Register {
		EAX("%eax", ByteRegister.EAX), EBX("%ebx", ByteRegister.EBX), ECX(
				"%ecx", ByteRegister.ECX), EDX("%edx", ByteRegister.EDX), ESI(
				"%esi", null), EDI("%edi", null), EBP("%ebp", null), ESP(
				"%esp", null);

		public final String repr;
		private final ByteRegister lowByteVersion;

		private Register(String repr, ByteRegister bv) {
			this.repr = repr;
			this.lowByteVersion = bv;
		}

		@Override
		public String toString() {
			return repr;
		}

		/**
		 * determines if this register has an 8bit version
		 */
		public boolean hasLowByteVersion() {
			return lowByteVersion != null;
		}

		/**
		 * Given a register like {@code %eax} returns {@code %al}, but doesn't
		 * work for {@code %esi} and {@code %edi}!
		 */
		public ByteRegister lowByteVersion() {
			assert hasLowByteVersion();
			return lowByteVersion;
		}
	}

	public enum ByteRegister {
		EAX("%al"), EBX("%bl"), ECX("%cl"), EDX("%dl");

		public final String repr;

		private ByteRegister(String repr) {
			this.repr = repr;
		}

		@Override
		public String toString() {
			return repr;
		}
	}

	/**
	 * Reset all general purpose registers to free
	 */
	public void initRegisters() {
		registers.clear();
		registers.addAll(Arrays.asList(GPR));
	}

	/**
	 * returns a free register and marks it as used
	 * @param ctx the context of the function requesting a register
	 */
	public Register getRegister(Context ctx) {
		int last = registers.size() - 1;
		if (last < 0) {
			// we need to spill a register
			ctx.spilledRegisters.push(spillRegister(ctx));
			return registers.remove(0);
		}

		return registers.remove(last);
	}

	/**
	 * marks a currently used register as free
	 * @param reg the to be released register
	 * @param ctx the context of the function releasing a register
	 */
	public void releaseRegister(Register reg, Context ctx) {
		if (ctx.spilledRegisters.contains(reg)) {
			unspillRegister(reg, ctx);
			return; // don't add it to registers, as it isn't free
		}
		assert !registers.contains(reg);
		registers.add(reg);
	}

	public void releaseRegisterWithoutUnspilling (Register reg) {
		assert !registers.contains(reg);
		registers.add(reg);
	}

	public Register spillRegister(Context ctx) {
		// we need to spill a register
		for (Register reg : RegisterManager.GPR) {
			if (ctx.reservedRegister == reg || registers.contains(reg)) {
				// not safe or not necessary to spill this register
				continue;
			}
			// spill register
			cg.emit.emit("push", reg); // store temporary value on the stack
			ctx.stackOffset -= Config.SIZEOF_PTR;
			registers.add(reg); // make available for use
			return reg;
		}

		// we only get here if every register is reserved
		throw new AssemblyFailedException(
				"Program requires too many registers");
	}

	public void unspillRegister(Register reg, Context ctx) {
		// undo spilling
		cg.emit.emit("pop", reg); // get temporary value back from stack
		ctx.stackOffset += Config.SIZEOF_PTR;
	}

	/**
	 * Returns whether the register is currently non-free
	 */
	public boolean isInUse(Register reg) {
		return !registers.contains(reg);
	}

	/**
	 * returns the number of free registers
	 */
	public int availableRegisters() {
		return registers.size();
	}
}