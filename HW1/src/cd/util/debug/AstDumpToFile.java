package cd.util.debug;

import cd.ir.Ast;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class AstDumpToFile {

    FileWriter w;

    public AstDumpToFile(FileWriter writer) {
        w = writer;
    }

    public void toFile(String astDumpOut) throws IOException {
        w.write(astDumpOut);
        w.close();
    }

    public void toFile(List<? extends Ast> astRoots) throws IOException {
        for (Ast a : astRoots) {
            w.append(AstDump.toString(a, true));
        }
        w.close();
    }

    public void toFile(Ast ast) throws IOException {
        w.write(AstDump.toString(ast, true));
        w.close();
    }

}
