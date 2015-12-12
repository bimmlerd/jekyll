package cd.analyses;

import cd.ir.Ast;
import cd.ir.Ast.ClassDecl;
import cd.ir.Ast.MethodDecl;
import cd.ir.AstVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Visits the body of all method declarations in the IR and produces their CFG.
 */
public class BasicBlockBuilder {

    public List<ControlFlowGraph> build(List<ClassDecl> astRoots) {
        List<ControlFlowGraph> globalCFGs = new ArrayList<>();
        for (ClassDecl classDecl : astRoots) {
            for (MethodDecl methodDecl : classDecl.methods()) {
                // We visit the body of each method declaration to build its global (intra-procedural) control flow graph.
                ControlFlowGraph cfg = new ControlFlowGraph();
                cfg.entry = new BasicBlock();
                cfg.exit = new BasicBlock();

                BasicBlockBuilderVisitor builder = new BasicBlockBuilderVisitor(cfg);
                BasicBlock last = builder.visit(methodDecl.body(), cfg.newBlockWithPredecessor(cfg.entry));
                if (last != null) {
                    cfg.connectBlocks(last, cfg.exit);
                }
                globalCFGs.add(cfg);
            }
        }
        return globalCFGs;
    }

    protected class BasicBlockBuilderVisitor extends AstVisitor<BasicBlock, BasicBlock> {
        private ControlFlowGraph cfg;

        public BasicBlockBuilderVisitor(ControlFlowGraph cfg) {
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

            // Create new basic blocks for the then and else part, respectively. Additionally connect them to the predecessor block.
            BasicBlock then = cfg.newBlockWithPredecessor(arg);
            BasicBlock otherwise = cfg.newBlockWithPredecessor(arg);

            // Visit the then and else part, respectively.
            then = visit(ast.then(), then);
            otherwise = visit(ast.otherwise(), otherwise);

            if (then == null && otherwise == null) {
                return null;
            } else {
                // Create a new block for after the if/else statement and connect it to its predecessors.
                BasicBlock next = cfg.newBlock();
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
            BasicBlock cond = cfg.newBlockWithPredecessor(arg);
            cond.statements.add(ast.condition()); // add the condition expression to the current basic block

            // Create new basic blocks for the loop body. Additionally connect it to the predecessor block.
            BasicBlock body = cfg.newBlockWithPredecessor(cond);

            // Visit the loop body and connect it to the condition block.
            body = visit(ast.body(), body);
            if (body != null) {
                cfg.connectBlocks(body, cond);
            }

            // Create a new block for after the while statement and connect it to its predecessor.
            return cfg.newBlockWithPredecessor(cond);
        }
    }
}
