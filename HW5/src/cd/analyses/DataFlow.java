package cd.analyses;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Abstract ata flow problem
 */
public abstract class DataFlow<T> {

    // direction
    abstract List<BasicBlock<T>> ancestors(BasicBlock<T> b);

    // meet operation: function that takes a list of solution sets and computes a new context set
    abstract Set<T> meet(List<BasicBlock<T>> ancestors);

    // solution and context sets
    abstract Set<T> context(BasicBlock<T> b);
    abstract Set<T> solution(BasicBlock<T> b);

    Set<T> computeContext(BasicBlock<T> b) {
        return meet(ancestors(b));
    }

    Set<T> computeSolution(BasicBlock<T> b) {
        return null;
    }

    /**
     * Forward Data-Flow Problem: context = IN, solution = OUT
     */
    public abstract class FwdDataFlow extends DataFlow<T> {
        @Override
        List<BasicBlock<T>> ancestors(BasicBlock<T> b) {
            return b.predecessors;
        }

        @Override
        Set<T> context(BasicBlock<T> b) {
            return b.inSet;
        }

        @Override
        Set<T> solution(BasicBlock<T> b) {
            return b.outSet;
        }
    }

    /**
     * Backward Data-Flow Problem: context = OUT, solution = IN
     */
    public abstract class BwdDataFlow extends DataFlow<T> {
        @Override
        List<BasicBlock<T>> ancestors(BasicBlock<T> b) {
            return b.successors;
        }

        @Override
        Set<T> context(BasicBlock<T> b) {
            return b.outSet;
        }

        @Override
        Set<T> solution(BasicBlock<T> b) {
            return b.inSet;
        }
    }

    /**
     * Forward Or Data-Flow Problem: meet operation = union
     */
    public abstract class FwdOrDataFlow extends FwdDataFlow {
        @Override
        Set<T> meet(List<BasicBlock<T>> ancestors) {
            Set<T> union = new HashSet<>();
            for (BasicBlock<T> b : ancestors) {
                union.addAll(solution(b));
            }
            return union;
        }
    }

    /**
     * Backward And Data-Flow Problem: meet operation = intersection
     */
    public abstract class BwdAndDataFlow extends BwdDataFlow {
        @Override
        Set<T> meet(List<BasicBlock<T>> ancestors) {
            Set<T> intersection = new HashSet<>();
            for (BasicBlock<T> b : ancestors) {
                intersection.retainAll(solution(b));
            }
            return intersection;
        }
    }

    public class UninitDataFlow extends FwdOrDataFlow {
    }

    public class BexprDataFlow extends BwdAndDataFlow {
    }
}
