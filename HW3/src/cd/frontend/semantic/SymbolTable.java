package cd.frontend.semantic;

import cd.ir.Ast;
import cd.ir.Symbol;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable<S extends Symbol> {

    private Map<String, S> symbolMap = new HashMap<>();

    public void put(S symbol) {
        symbolMap.put(symbol.name, symbol);
    }

    public S get(String key) {
        return symbolMap.get(key);
    }

    public boolean contains(String key) {
        return symbolMap.containsKey(key);
    }

    public boolean contains(S symbol) { return contains(symbol.name); }

}
