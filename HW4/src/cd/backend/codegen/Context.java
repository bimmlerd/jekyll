package cd.backend.codegen;

import cd.ir.Symbol;

import java.util.Map;
import java.util.Set;

/**
 * Context information for our visitor.
 */
public class Context {

    protected Symbol.ClassSymbol currentClass;

    // by default, we store the calculated value in the returned register and not its address
    protected boolean calculateValue = true;

    protected int stackOffset = 0; // documents the offset of a 16 byte aligned stack

    protected Map<String, Integer> offsetTable; // parameters and locals all have a unique offset to the base pointer on the stack

    Set<RegisterManager.Register> reservedRegisters; // Registers that we cannot spill, as they are needed locally
    Set<RegisterManager.Register> spilledRegisters; // Spilled Registers, those which need to be unspilled when released

    public Context(Symbol.ClassSymbol currentClass) {
        this.currentClass = currentClass;
    }

    public Context(Symbol.ClassSymbol currentClass, boolean calculateValue) {
        this.currentClass = currentClass;
        this.calculateValue = calculateValue;
    }
}
