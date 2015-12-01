package cd.backend.codegen;

import cd.ir.Symbol;

/**
 * Context information for our visitor.
 */
public class Context {

    protected Symbol.ClassSymbol currentClass;

    // by default, we store the calculated value in the returned register and not its address
    protected boolean calculateValue;

    public Context(Symbol.ClassSymbol currentClass, boolean calculateValue) {
        this.currentClass = currentClass;
        this.calculateValue = calculateValue;
    }
}
