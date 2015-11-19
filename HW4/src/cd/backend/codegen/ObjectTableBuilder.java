package cd.backend.codegen;

import cd.Config;
import cd.ToDoException;
import cd.ir.Ast;
import cd.ir.Symbol;

import java.util.ArrayList;
import java.util.List;

/**
 * Table for field offsets
 */
// TODO remove Builder class and make it just ObjectTable
public class ObjectTableBuilder {

    public static void buildObjectTable(Symbol.ClassSymbol classSymbol) {
        classSymbol.oTable = new ObjectTable(classSymbol);
    }

    public static class ObjectTable {

        private List<String> table = new ArrayList<>();

        public ObjectTable(Symbol.ClassSymbol classSymbol) {
            if (classSymbol.superClass == null) {
                table.add(VTableBuilder.getVTableLabel(classSymbol));
                return;
            }
            table.addAll(classSymbol.superClass.oTable.table); // this preserves indices.
            table.set(0, VTableBuilder.getVTableLabel(classSymbol));
            classSymbol.fields.forEach((n, v) -> table.add(n));
        }

        public int getOffset(String name) {
            int res = table.indexOf(name);
            if (res < 0) {
                // TODO
                throw new ToDoException("Trying to getOffset for a field which doesn't appear in the otable");
            }
            return res * Config.SIZEOF_PTR;
        }

        /**
         * @return the number of items for calloc
         */
        public int getCount() {
            return table.size();
        }
    }
}
