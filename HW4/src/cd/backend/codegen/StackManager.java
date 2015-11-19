package cd.backend.codegen;

import cd.Config;

import java.util.List;

import static cd.backend.codegen.AssemblyEmitter.constant;

/**
 * Utility class to prepare the stack for function calls etc
 */
public class StackManager {

    private final AstCodeGenerator cg;

    // TODO maybe this is useful, requires that all stack interaction goes through us though
    private int offsetFromOrigin = 0; // origin is 16 aligned: esp == XX..XXX0000 in binary

    public StackManager(AstCodeGenerator codeGenerator) {
        cg = codeGenerator;
    }

    /**
     * Saves caller saved registers on the stack
     * makes space for the arguments and puts them in the right place
     * aligns the stack on a 16
     */
    public void beforeFunctionCall(List<String> arguments) {
        storeCallerSavedRegs();

        // TODO the space reserved probably needs to be something like ceil(actual_space_needed / 16) * 16

        int argSpace = arguments.size() * Config.SIZEOF_PTR;
        int adjustment = ((offsetFromOrigin + argSpace) % 16 + 1) * 16 - offsetFromOrigin; // TODO untested

        // make space for the arguments including alignment
        cg.emit.emitComment("Space for arguments and place arguments:");
        cg.emit.emit("subl", constant(adjustment), RegisterManager.STACK_REG);
        offsetFromOrigin += adjustment;

        // place arguments
        for (int i = 0; i < arguments.size(); i++) {
            cg.emit.emitStore(arguments.get(i), i * Config.SIZEOF_PTR, RegisterManager.STACK_REG);
        }
    }

    /**
     * Undoes beforeFunctionCall()
     */
    public void afterFunctionCall(List<String> arguments) {

        int argSpace = arguments.size() * Config.SIZEOF_PTR;
        int adjustment = offsetFromOrigin - ((offsetFromOrigin - argSpace) % 16) * 16; // TODO untested

        cg.emit.emitComment("Reclaim space from arguments:");
        cg.emit.emit("addl", constant(adjustment), RegisterManager.STACK_REG);
        offsetFromOrigin -= adjustment;

        restoreCallerSavedRegs();
    }

    public void storeCallerSavedRegs() {
        cg.emit.emitComment("Store Caller Saved Registers:");
        for (RegisterManager.Register reg : RegisterManager.CALLER_SAVE) {
            cg.emit.emit("push", reg);
            offsetFromOrigin += Config.SIZEOF_PTR;
        }
    }

    /**
     * Restores the caller saved registers in reversed order as they are stored.
     */
    public void restoreCallerSavedRegs() {
        cg.emit.emitComment("Restore Caller Saved Registers:");
        for (int i = RegisterManager.CALLER_SAVE.length - 1; i >= 0; i--) {
            cg.emit.emit("pop", RegisterManager.CALLER_SAVE[i]);
            offsetFromOrigin -= Config.SIZEOF_PTR;
        }
    }


    public void storeCalleeSavedRegs() {
        cg.emit.emitComment("Store Callee Saved Registers:");
        for (RegisterManager.Register reg : RegisterManager.CALLEE_SAVE) {
            cg.emit.emit("push", reg);
            offsetFromOrigin += Config.SIZEOF_PTR;
        }
    }

    /**
     * Restores the callee saved registers in reversed order as they are stored.
     */
    public void restoreCalleeSavedRegs() {
        cg.emit.emitComment("Restore Callee Saved Registers:");
        for (int i = RegisterManager.CALLEE_SAVE.length - 1; i >= 0; i--) {
            cg.emit.emit("pop", RegisterManager.CALLEE_SAVE[i]);
            offsetFromOrigin -= Config.SIZEOF_PTR;
        }
    }
}
