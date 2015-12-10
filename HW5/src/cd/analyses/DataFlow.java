package cd.analyses;

import cd.ir.Ast;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Abstract data flow problem
 */
public abstract class DataFlow<T> {

    /**
     * Stores all information necessary for a basic block of a typed data flow problem.
     */
    protected class BasicBlock {
        List<Ast> statements = new LinkedList<>(); // statements that make up the basic block

        List<BasicBlock> predecessors = new LinkedList<>(); // keep track of neighboring basic blocks in CFG
        List<BasicBlock> successors = new LinkedList<>();

        Set<T> inSet = new HashSet<>();
        Set<T> outSet = new HashSet<>();

        Set<T> localNew = new HashSet<>(); // locally generated elements
        Set<T> localCut = new HashSet<>(); // locally destroyed elements
    }

    // direction
    abstract List<BasicBlock> ancestors(BasicBlock b);

    // meet operation: function that takes a list of solution sets and computes a new context set
    abstract Set<T> meet(List<BasicBlock> ancestors);

    // solution and context sets
    abstract Set<T> context(BasicBlock b);
    abstract Set<T> solution(BasicBlock b);

    Set<T> computeContext(BasicBlock b) {
        return meet(ancestors(b));
    }

    Set<T> computeSolution(BasicBlock b) {
        Set<T> result = new HashSet<>();
        result.addAll(context(b));
        result.removeAll(b.localCut);
        result.addAll(b.localNew);
        return result;
    }
}
