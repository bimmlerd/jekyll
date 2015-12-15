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
        intersection.addAll(solution(ancestors.get(0)));
        for (BasicBlock b : ancestors.subList(1, ancestors.size())) {
            intersection.retainAll(solution(b));
        }
        return intersection;
    }
}
