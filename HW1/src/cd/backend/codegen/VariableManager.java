package cd.backend.codegen;

import cd.ir.Ast;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import cd.backend.codegen.RegisterManager.Register;
import org.antlr.v4.runtime.misc.NotNull;

public class VariableManager implements Iterable<String>{
    private Map<String, Register> variables = new HashMap<>();

    public void declare(String var) {
        if (variables.containsKey(var)) {
            throw new IllegalArgumentException();
        }
        variables.put(var, null);
    }

    public void declare(Ast.VarDecl var) {
        declare(var.name);
    }

    public void define(String var, Register reg) {
        if (! variables.containsKey(var)) {
            variables.put(var, reg);
        } else {
            variables.replace(var, reg);
        }
    }

    public void define(Ast.VarDecl var, Register reg) {
        define(var.name, reg);
    }

    public void define(Ast.Var var, Register reg) {
        define(var.name, reg);
    }

    public Register get(String var) {
        if (! variables.containsKey(var)) {
            throw new IllegalArgumentException();
        }
        return variables.get(var);
    }

    public Register get(Ast.VarDecl var) {
        return get(var.name);
    }

    public Register get(Ast.Var var) {
        return get(var.name);
    }


    @Override
    public Iterator<String> iterator() {
        return variables.keySet().iterator();
    }
}
