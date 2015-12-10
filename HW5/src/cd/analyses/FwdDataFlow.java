package cd.analyses;

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
    Set<T> context(BasicBlock b) {
        return b.inSet;
    }

    @Override
    Set<T> solution(BasicBlock b) {
        return b.outSet;
    }
}