package cd.analysis;

import cd.ir.Ast;
import cd.ir.Ast.ClassDecl;
import cd.ir.Ast.MethodDecl;
import cd.ir.Ast.Stmt;
import cd.ir.AstVisitor;

import java.util.*;

/**
 * Abstract data flow problem
 */
public abstract class DataFlow<T> {

    final List<ControlFlowGraph> graphs = new ArrayList<>();

    /**
     * Stores all information necessary for a basic block of a typed data flow problem.
     */
    protected class BasicBlock {
        final List<Ast> statements = new LinkedList<>(); // statements that make up the basic block
        Stmt condition; // enclosing conditional statement of a basic block if there's any

        final List<BasicBlock> predecessors = new LinkedList<>(); // keep track of neighboring basic blocks in CFG
        final List<BasicBlock> successors = new LinkedList<>();

        Set<T> inSet;
        Set<T> outSet;

        final Set<T> localNew = new HashSet<>(); // locally generated elements
        final Set<T> localCut = new HashSet<>(); // locally destroyed elements
    }

    /**
     * Stores all information necessary to build a CFG and allows operations such as to add basic blocks.
     */
    protected class ControlFlowGraph {
        BasicBlock entry;
        BasicBlock exit;

        final Set<BasicBlock> blockSet = new HashSet<>(); // does neither contain the entry nor the exit block

        ClassDecl classDecl;
        MethodDecl methodDecl; // reference to the method declaration the cfg belongs to

        private BasicBlock addBlockWithPredecessor(BasicBlock b) {
            BasicBlock newBlock = addBlock();
            connectBlocks(b, newBlock);
            return newBlock;
        }

        private BasicBlock addBlock() {
            BasicBlock newBlock = new BasicBlock();
            addBlock(newBlock);
            return newBlock;
        }

        private void addBlock(BasicBlock b) {
            blockSet.add(b);
        }

        private void connectBlocks(BasicBlock predecessor, BasicBlock successor) {
            predecessor.successors.add(successor);
            successor.predecessors.add(predecessor);
        }
    }

    // direction
    abstract List<BasicBlock> ancestors(BasicBlock b);
    abstract List<BasicBlock> descendant(BasicBlock b);
    abstract BasicBlock startPoint(ControlFlowGraph cfg);

    // meet operation: function that takes a list of solution sets and computes a new context set
    abstract Set<T> meet(List<BasicBlock> ancestors);

    // solution and context sets
    abstract Set<T> context(BasicBlock b);
    abstract Set<T> solution(BasicBlock b);
    abstract void setContext(BasicBlock b, Set<T> context);
    abstract void setSolution(BasicBlock b, Set<T> solution);

    // depending on the actual implementation
    abstract void initSets(ControlFlowGraph cfg); // compute all local sets such as the initial solution, localNew and localCut
    abstract void evaluateDataFlow(ControlFlowGraph cfg); // after running the data flow algorithm, we may want to print some results

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

    public void run(List<ClassDecl> astRoots) {
        buildBasicBlocks(astRoots); // build uninitialized basic blocks
        for (ControlFlowGraph cfg : graphs) {
            initSets(cfg); // initialize solution sets and compute localCut and localNew sets for all basic blocks

            Queue<BasicBlock> blocks = new LinkedList<>();
            blocks.addAll(descendant(startPoint(cfg))); // start at the start point of the cfg to evaluate context and solution sets

            while (!blocks.isEmpty()) {
                BasicBlock b = blocks.poll();
                // compute context and solution set in each iteration
                setContext(b, computeContext(b));
                Set<T> computedSolution = computeSolution(b);
                if (!computedSolution.equals(solution(b))) {
                    setSolution(b, computedSolution);
                    blocks.addAll(descendant(b)); // add the descendants of the current basic block to the queue to be evaluated next
                }
            }

            evaluateDataFlow(cfg);
        }
    }

    private void buildBasicBlocks(List<ClassDecl> astRoots) {
        for (ClassDecl classDecl : astRoots) {
            for (MethodDecl methodDecl : classDecl.methods()) {
                // We visit the body of each method declaration to build its global (intra-procedural) control flow graph.
                ControlFlowGraph cfg = new ControlFlowGraph();
                cfg.entry = new BasicBlock();
                cfg.exit = new BasicBlock();

                BasicBlockBuilder builder = new BasicBlockBuilder(cfg);
                BasicBlock last = builder.visit(methodDecl.body(), cfg.addBlockWithPredecessor(cfg.entry));
                if (last != null) {
                    cfg.connectBlocks(last, cfg.exit);
                }
                cfg.classDecl = classDecl;
                cfg.methodDecl = methodDecl;
                graphs.add(cfg);
            }
        }
    }

    protected class BasicBlockBuilder extends AstVisitor<BasicBlock, BasicBlock> {
        private final ControlFlowGraph cfg;

        BasicBlockBuilder(ControlFlowGraph cfg) {
            this.cfg = cfg;
        }

        @Override
        protected BasicBlock dfltStmt(Ast.Stmt ast, BasicBlock arg) {
            arg.statements.add(ast); // add the statement to the current basic block
            return arg;
        }

        @Override
        public BasicBlock ifElse(Ast.IfElse ast, BasicBlock arg) {
            arg.statements.add(ast.condition()); // add the condition expression to the current basic block
            arg.condition = ast;

            // Create new basic blocks for the then and else part, respectively. Additionally connect them to the predecessor block.
            BasicBlock then = cfg.addBlockWithPredecessor(arg);
            BasicBlock otherwise = cfg.addBlockWithPredecessor(arg);

            // Visit the then and else part, respectively.
            then = visit(ast.then(), then);
            otherwise = visit(ast.otherwise(), otherwise);

            if (then == null && otherwise == null) {
                return null;
            } else {
                // Create a new block for after the if/else statement and connect it to its predecessors.
                BasicBlock next = cfg.addBlock();
                if (then != null) {
                    cfg.connectBlocks(then, next);
                }
                if (otherwise != null) {
                    cfg.connectBlocks(otherwise, next);
                }
                return next;
            }
        }

        @Override
        public BasicBlock returnStmt(Ast.ReturnStmt ast, BasicBlock arg) {
            arg.statements.add(ast);
            cfg.connectBlocks(arg, cfg.exit); // connect the block that contains a return statement to the exit block in the CFG
            return null;
        }

        @Override
        public BasicBlock seq(Ast.Seq ast, BasicBlock arg) {
            for (Ast statement : ast.children()) {
                arg = visit(statement, arg);
                if (arg == null) {
                    // Stop visiting children, if the current basic block is null.
                    // This is only the case, when we visited a return statement last.
                    return null;
                }
            }
            return arg;
        }

        @Override
        public BasicBlock whileLoop(Ast.WhileLoop ast, BasicBlock arg) {
            BasicBlock cond = cfg.addBlockWithPredecessor(arg);
            cond.statements.add(ast.condition()); // add the condition expression to the current basic block
            cond.condition = ast;

            // Create new basic blocks for the loop body. Additionally connect it to the predecessor block.
            BasicBlock body = cfg.addBlockWithPredecessor(cond);

            // Visit the loop body and connect it to the condition block.
            body = visit(ast.body(), body);
            if (body != null) {
                cfg.connectBlocks(body, cond);
            }

            // Create a new block for after the while statement and connect it to its predecessor.
            return cfg.addBlockWithPredecessor(cond);
        }
    }
}
