package com.jtransc.media.limelibgdx.glsl.ir;

import com.jtransc.media.limelibgdx.glsl.ast.Type;

import java.util.Objects;

public class Operator {
	public final String str;

	public Operator(String str) {
		this.str = str;
	}

	static public Operator fromString(String str) {
		return new Operator(str);
	}

	@Override
	public String toString() {
		return str;
	}

	static public Type getResultType(Type left, Operator op, Type right) {
		switch (op.str) {
			case "texture2D":
				if (!Objects.equals(left, Type.SAMPLER2D)) {
					throw new RuntimeException("texture2D requires sampler2D type for first argument");
				}
				if (!Objects.equals(right, Type.VEC2)) {
					throw new RuntimeException("texture2D requires vec2 type for second argument");
				}
				return Type.VEC4;
			case "*":
			case "/":
			case "%":
				if (Objects.equals(left, right)) return right;
				if (Objects.equals(left, Type.FLOAT)) return right;
				if (Objects.equals(right, Type.FLOAT)) return left;
				if (left.isMatrix() && right.isVector()) return right; // @TODO: Check matrix size
				throw new RuntimeException("Unsupported operation " + left + " " + op + " " + right);
			default: throw new RuntimeException("Unknown binary operation " + op + " for " + left + " and " + right);
		}
	}

	static public Type getResultType(Operator op, Type right) {
		switch (op.str) {
			default: throw new RuntimeException("Unknown unary operation " + op + " for " + right);
		}
	}

	public Type getResultType(Type left, Type right) {
		return Operator.getResultType(left, this, right);
	}

	public Type getResultType(Type right) {
		return Operator.getResultType(this, right);
	}
}
