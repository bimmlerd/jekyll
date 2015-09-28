package cd.util.debug;

import java.util.List;

import cd.ir.Ast;
import cd.ir.AstVisitor;

public final class AstToDot {

    public static String toDot(final String fileName, final List<? extends Ast> astRoots) {
        final StringBuilder sb = new StringBuilder();
        sb.append("graph" + " ").append(fileName).append("{\n");
        for (final Ast clazz : astRoots) {
            sb.append( toString( clazz ) );
        }
        sb.append("}\n" );
        return sb.toString();
    }

    final StringBuilder sb = new StringBuilder();

    public static String toString(Ast ast) {
        AstToDot adot = new AstToDot();
        adot.dump(ast);
        return adot.sb.toString();
    }

    protected void dump(final Ast ast) {

        final String nodeLabel = new AstVisitor<String, Void>() {
            @Override
            protected String dflt(final Ast ast, final Void arg) {
                return ast.getClass().getSimpleName();
            }
        }.visit(ast, null);

        final String nodeColor = new AstVisitor<String, Void>() {

            @Override
            public String classDecl(cd.ir.Ast.ClassDecl ast, Void arg) {
                return "green";
            }

            @Override
            public String methodDecl(cd.ir.Ast.MethodDecl ast, Void arg) {
                return "blue";
            }

            @Override
            public String intConst(cd.ir.Ast.IntConst ast, Void arg) {
                return "yellow";
            }

            @Override
            protected String dflt(Ast ast, Void arg) {
                return "white";
            }

        }.visit(ast, null);

        final String nodeInfo = new AstVisitor<String, Void>() {

            protected String dflt(Ast ast, Void arg) {
                return null;
            }

            @Override
            public String classDecl(cd.ir.Ast.ClassDecl ast, Void arg) {
                return ast.name + " : " + ast.superClass;
            }

            @Override
            public String methodDecl(cd.ir.Ast.MethodDecl ast, Void arg) {
                String info = ast.name;
                if (ast.argumentNames.size() > 0) {
                    info += "->";
                    for (int i = 0; i < ast.argumentNames.size(); i++) {
                        final String argName = ast.argumentNames.get(i);
                        if (i == ast.argumentNames.size() - 1) {
                            info += argName;
                        } else {
                            info += argName + ":";
                        }
                    }
                }
                return info;
            }

        }.visit(ast, null);

        final int astId = System.identityHashCode(ast);
        if (nodeInfo != null) {
            sb.append("node").append(astId).append("_info").append(" [label=\"").append(nodeInfo).append("\", color=\"").append(nodeColor).append("\", shape=box];\n");
            sb.append("node").append(astId).append(" -- ").append("node").append(astId).append("_info").append(";\n");
        }

        // sb.append("node" + astId + " [label=\"" + nodeInfo + "\"];");
        sb.append("node").append(astId).append(" [label=\"").append(nodeLabel).append("\", style=filled, fillcolor=\"").append(nodeColor).append("\"];\n");
        for (Ast child : ast.children()) {
            final int childId = System.identityHashCode(child);
            dump(child);
            sb.append("node").append(astId).append(" -- ").append("node").append(childId).append(";\n");
        }
    }


}