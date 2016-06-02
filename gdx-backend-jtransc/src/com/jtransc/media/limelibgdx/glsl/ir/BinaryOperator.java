package com.jtransc.media.limelibgdx.glsl.ir;

import com.jtransc.media.limelibgdx.glsl.ast.Type;

import java.util.HashMap;
import java.util.Objects;

public enum BinaryOperator {
	TEX2D("texture2D"),
	MUL("*"),
	DIV("/"),
	REM("%"),
	//ASSIGN("="),
	;

	static private HashMap<String, BinaryOperator> OPS = new HashMap<String, BinaryOperator>() {{
		for (BinaryOperator o : BinaryOperator.values()) {
			put(o._str, o);
		}
	}};

	private final String _str;

	BinaryOperator(String str) {
		this._str = str;
	}

	@Override
	public String toString() {
		return _str;
	}

	static public BinaryOperator fromString(String str) {
		if (!OPS.containsKey(str)) throw new RuntimeException("Unsupported operator " + str);
		return OPS.get(str);
	}

	static public Type getResultType(Type left, BinaryOperator op, Type right) {
		switch (op) {
			case TEX2D:
				if (!Objects.equals(left, Type.SAMPLER2D)) {
					throw new RuntimeException("texture2D requires sampler2D type for first argument");
				}
				if (!Objects.equals(right, Type.VEC2)) {
					throw new RuntimeException("texture2D requires vec2 type for second argument");
				}
				return Type.VEC4;
			case MUL:
			case DIV:
			case REM:
				if (Objects.equals(left, right)) return right;
				if (Objects.equals(left, Type.FLOAT)) return right;
				if (Objects.equals(right, Type.FLOAT)) return left;
				if (left.isMatrix() && right.isVector()) return right; // @TODO: Check matrix size
				throw new RuntimeException("Unsupported operation " + left + " " + op + " " + right);
			default: throw new RuntimeException("Unknown binary operation " + op + " for " + left + " and " + right);
		}
	}


	public Type getResultType(Type left, Type right) {
		return BinaryOperator.getResultType(left, this, right);
	}

}
