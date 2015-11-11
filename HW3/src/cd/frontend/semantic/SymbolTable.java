package cd.frontend.semantic;

import cd.ir.Symbol;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collector;

public class SymbolTable<S extends Symbol> {

    private SymbolTable<? extends Symbol> outerScope;
    protected Map<String, S> symbolTable = new HashMap<>();

    public SymbolTable(SymbolTable<? extends Symbol> outerScope) {
        this.outerScope = outerScope;
    }

    public SymbolTable() { this.outerScope = null; }

    public void put(S symbol) {
        symbolTable.put(symbol.name, symbol);
    }

    public void putIfAbsent(S symbol) {
        if (!contains(symbol)) {
            symbolTable.put(symbol.name, symbol);
        }
    }

    public Symbol get(String key) {
        S res = symbolTable.get(key);
        if (res == null && outerScope != null) {
            return outerScope.get(key);
        }
        return res;
    }

    public Symbol get(Symbol symbol) {
        return get(symbol.name);
    }

    public boolean contains(String key) {
        if (outerScope != null) {
            return containsInLocalScope(key) || outerScope.contains(key);
        } else {
            return containsInLocalScope(key);
        }
    }

    public boolean contains(Symbol symbol) { return contains(symbol.name); }

    public boolean containsInLocalScope(String key) {
        return symbolTable.containsKey(key);
    }

    public boolean containsInLocalScope(Symbol symbol) { return containsInLocalScope(symbol.name); }

    public Collection<S> getSymbols() {
        return symbolTable.values();
    }

    private Collector<S, Collection<Symbol.ClassSymbol>, Collection<Symbol.ClassSymbol>> castingCollector =
            Collector.of(
                    HashSet::new,                                   // supplier
                    (c, sym) -> c.add((Symbol.ClassSymbol) sym),    // accumulator
                    (j1, j2) -> {j1.addAll(j2); return j1;},        // combiner
                    (c) -> (c));                                    // finisher

    public Collection<Symbol.ClassSymbol> getClassSymbols() {
        return getSymbols().stream().filter(s -> s instanceof Symbol.ClassSymbol).collect(castingCollector);
    }
}
