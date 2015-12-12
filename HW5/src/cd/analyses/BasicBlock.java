package cd.analyses;

import cd.ir.Ast;

import java.util.LinkedList;
import java.util.List;

/**
 * Stores all information necessary for a general basic block.
 */
public class BasicBlock {
    List<Ast> statements = new LinkedList<>(); // statements that make up the basic block

    List<BasicBlock> predecessors = new LinkedList<>(); // keep track of neighboring basic blocks in CFG
    List<BasicBlock> successors = new LinkedList<>();
}
