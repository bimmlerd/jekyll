package cd.analyses;

import cd.ir.Symbol.VariableSymbol;

/**
 * Finds uses of (potentially) uninitialized variables (-uninit) and generate a semantic error (i.e., POSSIBLY UNINITIALIZED)
 * whenever an uninitialized local variable is used as an operand.
 */
public class UninitDataFlow extends FwdOrDataFlow<VariableSymbol> {
}
