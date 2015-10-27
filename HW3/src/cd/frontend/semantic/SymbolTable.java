package cd.frontend.semantic;

import cd.ir.Symbol;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable<S extends Symbol> {

    private Map<String, S> symbolMap = new HashMap<>();
    private SymbolTable outerScope;

    public SymbolTable(SymbolTable outerScope) {
        this.outerScope = outerScope;
    }

    public void put(S symbol) {
        // TODO only check local, otherwise method overrides are broken, right?
        if (containsInLocalScope(symbol)) {
            throw new SemanticFailure(SemanticFailure.Cause.DOUBLE_DECLARATION,
                    "Symbol %s already declared", symbol.name);
        }
        symbolMap.put(symbol.name, symbol);
    }

    public S get(String key) {
        return symbolMap.get(key);
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
