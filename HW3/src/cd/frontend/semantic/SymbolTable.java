package cd.frontend.semantic;

import cd.ToDoException;
import cd.ir.Symbol;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable<S extends Symbol> {

    public SymbolTable(SymbolTable<S> outerScope) {
        this.outerScope = outerScope;
    }

    protected Map<String, S> symbolMap = new HashMap<>();
    private SymbolTable<S> outerScope;

    public SymbolTable() {}

    public void setOuterScope(SymbolTable outerScope) {
        this.outerScope = outerScope;
    }

    public void put(S symbol) {
        if (containsInLocalScope(symbol)) {
            throw new ToDoException();
        }
        symbolMap.put(symbol.name, symbol);
    }

    public void putIfAbsent(S symbol) {
        if (!contains(symbol)) {
            symbolMap.put(symbol.name, symbol);
        }
    }

    public S get(String key) {
        S res = symbolMap.get(key);
        if (res == null && outerScope != null) {
            return outerScope.get(key);
        }
        return res;
    }

    public boolean contains(String key) {
        if (outerScope != null) {
            return containsInLocalScope(key) || outerScope.contains(key);
        } else {
            return containsInLocalScope(key);
        }
    }

    public boolean contains(S symbol) { return contains(symbol.name); }

    public boolean containsInLocalScope(String key) {
        return symbolMap.containsKey(key);
    }

    public boolean containsInLocalScope(S symbol) { return containsInLocalScope(symbol.name); }

}
