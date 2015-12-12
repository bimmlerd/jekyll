package cd.analyses;

import java.util.HashSet;
import java.util.Set;

/**
 * Stores all information necessary to build a CFG and allows operations such as to add basic blocks.
 */
public class ControlFlowGraph {
    BasicBlock entry;
    BasicBlock exit;

    Set<BasicBlock> blockSet = new HashSet<>();

    public void addBlock(BasicBlock b) {
        blockSet.add(b);
    }

    public void connectBlocks(BasicBlock predecessor, BasicBlock successor) {
        predecessor.successors.add(successor);
        successor.predecessors.add(predecessor);
    }

    public BasicBlock newBlock() {
        BasicBlock newBlock = new BasicBlock();
        addBlock(newBlock);
        return newBlock;
    }

    public BasicBlock newBlockWithPredecessor(BasicBlock b) {
        BasicBlock newBlock = new BasicBlock();
        addBlock(newBlock);
        connectBlocks(b, newBlock);
        return newBlock;
    }
}
