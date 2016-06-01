package com.jtransc.media.limelibgdx.glsl.ir;

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
}
