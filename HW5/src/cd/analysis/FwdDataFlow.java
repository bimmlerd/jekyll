package cd.analysis;

import java.util.List;
import java.util.Set;

/**
 * Forward Data-Flow Problem: context = IN, solution = OUT
 */
public abstract class FwdDataFlow<T> extends DataFlow<T> {
    @Override
    List<BasicBlock> ancestors(BasicBlock b) {
        return b.predecessors;
    }

    @Override
    List<BasicBlock> descendant(BasicBlock b) {
        return b.successors;
    }

    @Override
    BasicBlock startPoint(ControlFlowGraph cfg) {
        return cfg.entry;
    }

    @Override
    Set<T> context(BasicBlock b) {
        return b.inSet;
    }

    @Override
    Set<T> solution(BasicBlock b) {
        return b.outSet;
    }

    @Override
    void setContext(BasicBlock b, Set<T> context) {
        b.inSet = context;
    }

    @Override
    void setSolution(BasicBlock b, Set<T> solution) {
        b.outSet = solution;
    }
}