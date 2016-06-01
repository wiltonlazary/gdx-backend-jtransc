package com.jtransc.media.limelibgdx.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Operators {
	static public HashMap<String, Integer> BINOPS = constructOperators(new String[][]{
		new String[] { "=" },
		new String[] { "*", "/", "%" },
		new String[] { "+", "-" },
		new String[] { "<<", ">>", ">>>" },
		new String[] { "<", ">", "<=", ">=" },
		new String[] { "==", "!=" },
		new String[] { "&" },
		new String[] { "^" },
		new String[] { "|" },
		new String[] { "&&" },
		new String[] { "||" },
	});

	static public HashMap<String, Integer> UNOPS = constructOperators(new String[][]{
		new String[] { "!" },
		new String[] { "~" },
		new String[] { "++" },
		new String[] { "--" },
		new String[] { "-" },
		new String[] { "+" },
	});

	static public HashMap<String, Integer> SYMBOLS = constructOperators(new String[][]{
		new String[] { "(", ")" },
		new String[] { "[", "]" },
		new String[] { "{", "}" },
		new String[] { "." },
		new String[] { "," },
		new String[] { ";" },
	});

	static public final Set<String> ALL = new HashSet<String>() {{
		addAll(Operators.SYMBOLS.keySet());
		addAll(Operators.BINOPS.keySet());
		addAll(Operators.UNOPS.keySet());
	}};

	static private HashMap<String, Integer> constructOperators(String[][] opsList) {
		HashMap<String, Integer> out = new HashMap<>();
		int priority = 0;
		for (String[] ops : opsList) {
			for (String op : ops) {
				out.put(op, priority);
			}
			priority++;
		}

		return out;
	}
}
