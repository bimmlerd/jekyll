package cd.frontend.semantic;

import cd.ir.Symbol;

import java.util.HashMap;
import java.util.Map;

public class SymbolManager {

    private Map<String, Symbol.TypeSymbol> symbolTable = new HashMap<>();

    public boolean contains(String key) {
        return symbolTable.containsKey(key);
    }

    public boolean contains(Symbol.TypeSymbol symbol) {
        return contains(symbol.name);
    }

    public Symbol.TypeSymbol get(String key) {
        return symbolTable.get(key);
    }

    public void put(Symbol.TypeSymbol symbol) {
        symbolTable.put(symbol.name, symbol);
    }

}
