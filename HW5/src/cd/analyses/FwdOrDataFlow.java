package cd.analyses;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Forward Or Data-Flow Problem: meet operation = union
 */
public abstract class FwdOrDataFlow<T> extends FwdDataFlow<T> {
    @Override
    Set<T> meet(List<BasicBlock> ancestors) {
        Set<T> union = new HashSet<>();
        for (BasicBlock b : ancestors) {
            union.addAll(solution(b));
        }
        return union;
    }
}
