package cd.analyses;

import java.util.List;
import java.util.Set;

/**
 * Backward Data-Flow Problem: context = OUT, solution = IN
 */
public abstract class BwdDataFlow<T> extends DataFlow<T> {
    @Override
    List<BasicBlock> ancestors(BasicBlock b) {
        return b.successors;
    }

    @Override
    Set<T> context(BasicBlock b) {
        return b.outSet;
    }

    @Override
    Set<T> solution(BasicBlock b) {
        return b.inSet;
    }
}
