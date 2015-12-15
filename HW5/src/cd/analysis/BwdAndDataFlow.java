package cd.analysis;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Backward And Data-Flow Problem: meet operation = intersection
 */
public abstract class BwdAndDataFlow<T> extends BwdDataFlow<T> {
    @Override
    Set<T> meet(List<BasicBlock> ancestors) {
        Set<T> intersection = new HashSet<>();
        for (BasicBlock b : ancestors) {
            intersection.retainAll(solution(b)); // TODO: addAll() for the first basic block to avoid an intersection with the empty set
        }
        return intersection;
    }
}
