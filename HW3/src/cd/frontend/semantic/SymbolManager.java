package cd.frontend.semantic;

import cd.ir.Symbol;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public Collection<Symbol.TypeSymbol> getSymbols() {
        return symbolTable.values();
    }

    public List<Symbol.TypeSymbol> getClassSymbols() { // TODO make this return ClassSymbols by implementing a casting collector?
        return getSymbols().stream().filter(s -> s instanceof Symbol.ClassSymbol).collect(Collectors.toList());
    }

}
