package cd.backend.codegen;

import cd.Config;
import cd.ToDoException;
import cd.ir.Ast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cd.backend.codegen.AssemblyEmitter.constant;

/**
 * Utility class to prepare the stack for function calls etc
 */
public class StackManager {

    private final AstCodeGenerator cg;

    private int offsetFromOrigin = 0; // origin is 16 aligned: esp == XX..XXX0000 in binary

    private Map<String, Integer> currentFunctionScope;

    // function name maps to a local scope, scope maps name to offset from ebp
    private Map<String, Map<String, Integer>> localScopes = new HashMap<>();

    public StackManager(AstCodeGenerator codeGenerator) {
        cg = codeGenerator;
    }

    public Map<String, Integer> createFunctionScope(String methodName) {
        if (!localScopes.containsKey(methodName)) {
            Map<String, Integer> res = new HashMap<>();
            localScopes.put(methodName, res);
            return res;
        } else {
            throw new ToDoException();
        }
    }


    public int getOffsetForLocal(String name) {
        if (currentFunctionScope == null) {
            throw new ToDoException();
        }

        return currentFunctionScope.get(name);
    }

    /**
     * allocates stack space for @param locals of a method, and adjusts stack
     * offset
     */
    public void methodPreamble(String methodName, List<Ast> locals) {
        currentFunctionScope =  createFunctionScope(methodName);

        int localSpace = locals.size() * Config.SIZEOF_PTR;
        cg.emit.emit("enter",
                AssemblyEmitter.constant(localSpace),
                AssemblyEmitter.constant(0));
        offsetFromOrigin += localSpace + 4; // localSpace for locals, 4 for enter

        cg.emit.emitComment("Zero locals on stack");
        int basePointerOffset = Config.SIZEOF_PTR; // $ebp + 0 is old ebp
        for (Ast astLocal : locals) {
            Ast.VarDecl local = (Ast.VarDecl) astLocal;
            currentFunctionScope.put(local.name, basePointerOffset);
            cg.emit.emitStore(AssemblyEmitter.constant(0),
                    basePointerOffset,
                    RegisterManager.BASE_REG);
            basePointerOffset += Config.SIZEOF_PTR;
        }
    }

    /**
     * Saves caller saved registers on the stack
     * makes space for the arguments and puts them in the right place
     * aligns the stack on a 16
     */
    public void beforeFunctionCall(List<String> arguments) {
        storeCallerSavedRegs();

        int argSpace = arguments.size() * Config.SIZEOF_PTR;
        int adjustment = 16 - (offsetFromOrigin % 16 + argSpace) % 16;
        int allocSpace = adjustment + argSpace;

        // make space for the arguments including alignment
        cg.emit.emitComment("Space for arguments and place arguments:");
        cg.emit.emit("subl", constant(allocSpace), RegisterManager.STACK_REG);

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
        int adjustment = 16 - (offsetFromOrigin % 16 + argSpace) % 16;
        int allocSpace = adjustment + argSpace;

        cg.emit.emitComment("Reclaim space from arguments:");
        cg.emit.emit("addl", constant(allocSpace), RegisterManager.STACK_REG);

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
