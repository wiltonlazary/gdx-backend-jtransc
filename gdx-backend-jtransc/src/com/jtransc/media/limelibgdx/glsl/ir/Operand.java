package com.jtransc.media.limelibgdx.glsl.ir;

import com.jtransc.media.limelibgdx.glsl.ast.Type;

public class Operand {
	public enum Kind {
		Uniform, Sampler, Attribute, Varying, Temp, Special, Constant
	}

	public final Kind kind;
	public final Type type;
	public final int id;
	public final double constant;
	public final String name;
	public final String swizzle;

	public Operand(Kind kind, Type type, int id, double constant, String name, String swizzle) {
		this.kind = kind;
		this.type = type;
		this.id = id;
		this.constant = constant;
		this.name = name;
		this.swizzle = swizzle;
	}

	public Operand withSwizzle(String swizzle) {
		return new Operand(kind, type, id, Double.NaN, name, swizzle);
	}

	public Operand withLanesCount(int count) {
		return withSwizzle("xyzw".substring(0, count));
	}

	static public Operand temp(int id, Type type) {
		return new Operand(Kind.Temp, type, id, Double.NaN, "temp_" + type + "_" + id, null);
	}

	static public Operand special(String name, Type type) {
		return new Operand(Kind.Special, type, -1, Double.NaN, name, null);
	}

	public static Operand attribute(String name, Type type) {
		return new Operand(Kind.Attribute, type, -1, Double.NaN, name, null);
	}

	public static Operand varying(String name, Type type) {
		return new Operand(Kind.Varying, type, -1, Double.NaN, name, null);
	}

	public static Operand uniform(String name, Type type) {
		return new Operand(Kind.Uniform, type, -1, Double.NaN, name, null);
	}

	public static Operand constant(double v) {
		return new Operand(Kind.Constant, Type.FLOAT, -1, v, "#CST#" + v, null);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Operand operand = (Operand) o;

		if (id != operand.id) return false;
		if (kind != operand.kind) return false;
		if (name != null ? !name.equals(operand.name) : operand.name != null) return false;
		return swizzle != null ? swizzle.equals(operand.swizzle) : operand.swizzle == null;

	}

	@Override
	public int hashCode() {
		int result = kind != null ? kind.hashCode() : 0;
		result = 31 * result + id;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (swizzle != null ? swizzle.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Operand{" +
			"type=" + kind +
			", id=" + id +
			", name='" + name + '\'' +
			", swizzle='" + swizzle + '\'' +
			'}';
	}
}