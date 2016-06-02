package com.jtransc.media.limelibgdx.glsl;

public enum ShaderType {
	Vertex(0), Fragment(1);

	public final int id;

	ShaderType(int id) {
		this.id = id;
	}
}
