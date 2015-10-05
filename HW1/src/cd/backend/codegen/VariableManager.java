package cd.backend.codegen;

import cd.ir.Ast;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import cd.backend.codegen.RegisterManager.Register;

public class VariableManager implements Iterable<String>{
    private Map<String, Register> varToReg = new HashMap<>();

    public void declare(String var) {
        if (varToReg.containsKey(var)) {
            throw new IllegalArgumentException();
        }
        varToReg.put(var, null);
    }

    public void declare(Ast.VarDecl var) {
        declare(var.name);
    }

    @Override
    public Iterator<String> iterator() {
        return varToReg.keySet().iterator();
    }
}
