package com.jtransc.media.limelibgdx.glsl.ir;

import com.jtransc.media.limelibgdx.glsl.ast.Type;

import java.util.HashMap;

public enum UnaryOperator {
	NEG("-"),
	NOT("!"),
	ASSIGN("="),
	;

	static private HashMap<String, UnaryOperator> OPS = new HashMap<String, UnaryOperator>() {{
		for (UnaryOperator o : UnaryOperator.values()) {
			put(o._str, o);
		}
	}};

	private final String _str;

	UnaryOperator(String str) {
		this._str = str;
	}

	@Override
	public String toString() {
		return _str;
	}

	static public UnaryOperator fromString(String str) {
		if (!OPS.containsKey(str)) throw new RuntimeException("Unsupported operator " + str);
		return OPS.get(str);
	}

	static public Type getResultType(UnaryOperator op, Type right) {
		switch (op) {
			default:
				throw new RuntimeException("Unknown unary operation " + op + " for " + right);
		}
	}

	public Type getResultType(Type right) {
		return UnaryOperator.getResultType(this, right);
	}
}
