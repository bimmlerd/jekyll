package cd.frontend.semantic;

import cd.ir.Symbol;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collector;

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

    Collector<Symbol.TypeSymbol, Collection<Symbol.ClassSymbol>, Collection<Symbol.ClassSymbol>> castingCollector =
            Collector.of(
                    HashSet::new,                                   // supplier
                    (c, sym) -> c.add((Symbol.ClassSymbol) sym),    // accumulator
                    (j1, j2) -> {j1.addAll(j2); return j1;},        // combiner
                    (c) -> (c));                                    // finisher

    public Collection<Symbol.ClassSymbol> getClassSymbols() {
        return getSymbols().stream().filter(s -> s instanceof Symbol.ClassSymbol).collect(castingCollector);
    }

}
