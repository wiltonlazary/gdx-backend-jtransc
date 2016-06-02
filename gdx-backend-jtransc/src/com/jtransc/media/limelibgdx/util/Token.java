package com.jtransc.media.limelibgdx.util;

public class Token {
	enum Type {
		OPERATOR, STRING, ID, NUMBER;
	}

	public final Type type;
	public final String content;

	public Token(Type type, String content) {
		this.type = type;
		this.content = content;
	}
}
