package com.jtransc.media.limelibgdx.glsl;

public class Lane {
	static public int getLaneIndex(char c) {
		switch (c) {
			case 'r':
			case 'x':
				return 0;
			case 'g':
			case 'y':
				return 1;
			case 'b':
			case 'z':
				return 2;
			case 'a':
			case 'w':
				return 3;
			default:
				throw new RuntimeException("Invalid lane name '" + c + "'");
		}
	}
}
