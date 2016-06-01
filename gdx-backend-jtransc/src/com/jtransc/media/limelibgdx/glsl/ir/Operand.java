package com.jtransc.media.limelibgdx.glsl.ir;

public class Operand {
	enum Type {
		Uniform, Attribute, Varying, Temp, Special
	}

	public final Type type;
	public final int id;
	public final String name;
	public final String swizzle;

	public Operand(Type type, int id, String name, String swizzle) {
		this.type = type;
		this.id = id;
		this.name = name;
		this.swizzle = swizzle;
	}

	public Operand withSwizzle(String swizzle) {
		return new Operand(type, id, name, swizzle);
	}

	static public Operand temp(int id) {
		return new Operand(Type.Temp, id, "temp" + id, null);
	}

	static public Operand special(String name) {
		return new Operand(Type.Special, -1, name, null);
	}

	public static Operand attribute(String name) {
		return new Operand(Type.Attribute, -1, name, null);
	}

	public static Operand varying(String name) {
		return new Operand(Type.Varying, -1, name, null);
	}

	public static Operand uniform(String name) {
		return new Operand(Type.Uniform, -1, name, null);
	}
}