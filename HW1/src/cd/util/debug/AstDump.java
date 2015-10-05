package cd.util.debug;

import cd.ir.Ast;
import cd.ir.AstVisitor;
import cd.util.Pair;

import java.lang.reflect.Field;
import java.util.*;

public class AstDump {
	
	public static String toString(Ast ast) {
		return toString(ast, "");
	}

	public static String toString(Ast ast, boolean includeCustom) {
		return toString(ast, "", includeCustom);
	}

	
	public static String toString(List<? extends Ast> astRoots) {
		StringBuilder sb = new StringBuilder();
		for (Ast a : astRoots) {
			sb.append(toString(a));
		}
		return sb.toString();
	}

	public static String toString(Ast ast, String indent) {
		return toString(ast, indent, false);
	}

	public static String toString(Ast ast, String indent, boolean includeCustom) {
		AstDump ad = new AstDump();
		ad.dump(ast, indent, includeCustom);
		return ad.sb.toString();
	}
	
	public static String toStringFlat(Ast ast) {
		AstDump ad = new AstDump();
		ad.dumpFlat(ast);
		return ad.sb.toString();
	}
	
	private StringBuilder sb = new StringBuilder();
	private Visitor vis = new Visitor();

	protected void dump(Ast ast, String indent) {
		dump(ast, indent, false);
	}

	protected void dump(Ast ast, String indent, boolean includeCustom) {
		// print out the overall class structure
		sb.append(indent);
		String nodeName = ast.getClass().getSimpleName();
		List<Pair<?>> flds = vis.visit(ast, includeCustom);
		sb.append(String.format("%s (%s)\n", 
				nodeName,
				Pair.join(flds, ": ", ", ")));

		// print out any children
		String newIndent = indent + "| ";
		for (Ast child : ast.children()) {
			dump(child, newIndent, includeCustom);
		}
	}
	
	protected void dumpFlat(Ast ast) {
		String nodeName = ast.getClass().getSimpleName();
		List<Pair<?>> flds = vis.visit(ast, null);
		sb.append(String.format("%s(%s)[", 
				nodeName,
				Pair.join(flds, ":", ",")));

		// print out any children
		for (int child = 0; child < ast.children().size(); child++) {
			dumpFlat(ast.children().get(child));
			if (child < ast.children().size() - 1)
				sb.append(",");
		}

		sb.append("]");
	}

	protected class Visitor extends AstVisitor<List<Pair<?>>, Boolean> {
		
		@Override
		protected List<Pair<?>> dflt(Ast ast, Boolean arg) {
			ArrayList<Pair<?>> res = new ArrayList<Pair<?>>();
			
			// Get the list of fields and sort them by name:
			java.lang.Class<? extends Ast> rclass = ast.getClass();
			List<java.lang.reflect.Field> rflds = 
				Arrays.asList(rclass.getFields());
			Collections.sort(rflds, new Comparator<java.lang.reflect.Field> () {
				public int compare(Field o1, Field o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
			
			// Create pairs for each one that is not of type Ast:
			for (java.lang.reflect.Field rfld : rflds) {
				rfld.setAccessible(true);
				
				// ignore various weird fields that show up from
				// time to time:
				if (rfld.getName().startsWith("$"))
					continue;
				
				// ignore fields of AST type, and rwChildren (they should be
				// uncovered using the normal tree walk)
				if (rfld.getType().isAssignableFrom(Ast.class))
					continue;
				if (rfld.getName().equals("rwChildren") || rfld.getName().equals("registerCount"))
					continue;

				if (arg != null && !arg) {
					// skip custom fields to be able to use the testing framework
					if (rfld.getName().equals("registersUsed"))
						continue;
				}
				
				// ignore NULL fields, but add others to our list of pairs
				try {
					Object value = rfld.get(ast);
					if (value != null)
						res.add(new Pair<Object>(rfld.getName(), value));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
			
			return res;
		}
		
	}

}
