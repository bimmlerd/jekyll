package cd.analyses;

import cd.ir.Ast;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Stores all information necessary for a basic block of a typed data flow problem.
 */
public class BasicBlock<T> {

    List<Ast> statements = new LinkedList<>(); // statements that make up the basic block

    List<BasicBlock<T>> predecessors = new LinkedList<>(); // keep track of neighboring basic blocks in CFG
    List<BasicBlock<T>> successors = new LinkedList<>();

    Set<T> inSet = new HashSet<>();
    Set<T> outSet = new HashSet<>();

    Set<T> localNew = new HashSet<>(); // locally generated elements
    Set<T> localCut = new HashSet<>(); // locally destroyed elements
}
