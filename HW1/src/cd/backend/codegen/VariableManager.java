package cd.backend.codegen;

import cd.ir.Ast;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import cd.backend.codegen.RegisterManager.Register;

public class VariableManager implements Iterable<String>{
    private Map<String, Register> varToReg = new HashMap<>();
    private Map<Register, String> regToVar = new HashMap<>();

    public void declare(String var) {
        if (varToReg.containsKey(var)) {
            throw new IllegalArgumentException();
        }
        varToReg.put(var, null);
    }

    public void declare(Ast.VarDecl var) {
        declare(var.name);
    }

    /*public void add(String var, Register reg) {
        if (regToVar.containsKey(reg)) {
            throw new IllegalArgumentException();
        }
        varToReg.put(var, reg);
        regToVar.put(reg, var);
    }

    public void add(Ast.VarDecl var, Register reg) {
        add(var.name, reg);
    }

    public void add(Ast.Var var, Register reg) {
        add(var.name, reg);
    }

    public void remove(String var) {
        if (! varToReg.containsKey(var)) {
            throw new IllegalArgumentException();
        }
        regToVar.remove(varToReg.get(var));
        varToReg.replace(var, null);
    }

    public void remove(Ast.VarDecl var) {
        remove(var.name);
    }

    public void remove(Ast.Var var) {
        remove(var.name);
    }

    public void remove(Register reg) {
        if (! regToVar.containsKey(reg)) {
            throw new IllegalArgumentException();
        }
        varToReg.replace(regToVar.get(reg), null);
        regToVar.remove(reg);
    }

    public Register get(String var) {
        if (! varToReg.containsKey(var)) {
            throw new IllegalArgumentException();
        }
        return varToReg.get(var);
    }

    public Register get(Ast.VarDecl var) {
        return get(var.name);
    }

    public Register get(Ast.Var var) {
        return get(var.name);
    }

    public Boolean has(String var) {
        return varToReg.containsKey(var) && (varToReg.get(var) != null);
    }

    public Boolean has(Register reg) {
        return regToVar.containsKey(reg);
    }

    public Boolean has(Ast.VarDecl var) {
        return has(var.name);
    }

    public Boolean has(Ast.Var var) {
        return has(var.name);
    }*/


    @Override
    public Iterator<String> iterator() {
        return varToReg.keySet().iterator();
    }
}
