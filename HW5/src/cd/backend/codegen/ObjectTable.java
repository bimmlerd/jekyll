package cd.backend.codegen;

import cd.Config;
import cd.ir.Symbol;

import java.util.ArrayList;
import java.util.List;

/**
 * Table for field offsets
 */
public class ObjectTable {

    protected List<String> table = new ArrayList<>();

    public ObjectTable(Symbol.ClassSymbol classSymbol) {
        if (classSymbol.superClass == null) {
            table.add(VTableBuilder.getVTableLabel(classSymbol));
            return;
        }
        table.addAll(classSymbol.superClass.oTable.table); // this preserves indices.
        table.set(0, VTableBuilder.getVTableLabel(classSymbol));
        classSymbol.fields.keySet().forEach((n) -> table.add(String.format("%s.%s", classSymbol.name, n)));
    }

    public int getOffset(String name) {
        int res = table.indexOf(name);
        return res * Config.SIZEOF_PTR;
    }

    /**
     * @return the number of items for calloc
     */
    public int getCount() {
        return table.size();
    }
}
