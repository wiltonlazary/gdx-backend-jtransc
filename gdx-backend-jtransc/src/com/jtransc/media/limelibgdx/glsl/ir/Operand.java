package com.jtransc.media.limelibgdx.glsl.ir;

public class Operand {
	public enum Type {
		Uniform, Attribute, Varying, Temp, Special, Constant
	}

	public final Type type;
	public final int id;
	public final double constant;
	public final String name;
	public final String swizzle;

	public Operand(Type type, int id, double constant, String name, String swizzle) {
		this.type = type;
		this.id = id;
		this.constant = constant;
		this.name = name;
		this.swizzle = swizzle;
	}

	public Operand withSwizzle(String swizzle) {
		return new Operand(type, id, Double.NaN, name, swizzle);
	}

	static public Operand temp(int id) {
		return new Operand(Type.Temp, id, Double.NaN, "temp" + id, null);
	}

	static public Operand special(String name) {
		return new Operand(Type.Special, -1, Double.NaN, name, null);
	}

	public static Operand attribute(String name) {
		return new Operand(Type.Attribute, -1, Double.NaN, name, null);
	}

	public static Operand varying(String name) {
		return new Operand(Type.Varying, -1, Double.NaN, name, null);
	}

	public static Operand uniform(String name) {
		return new Operand(Type.Uniform, -1, Double.NaN, name, null);
	}

	public static Operand constant(double v) {
		return new Operand(Type.Constant, -1, v, "#CST#" + v, null);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Operand operand = (Operand) o;

		if (id != operand.id) return false;
		if (type != operand.type) return false;
		if (name != null ? !name.equals(operand.name) : operand.name != null) return false;
		return swizzle != null ? swizzle.equals(operand.swizzle) : operand.swizzle == null;

	}

	@Override
	public int hashCode() {
		int result = type != null ? type.hashCode() : 0;
		result = 31 * result + id;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (swizzle != null ? swizzle.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Operand{" +
			"type=" + type +
			", id=" + id +
			", name='" + name + '\'' +
			", swizzle='" + swizzle + '\'' +
			'}';
	}
}