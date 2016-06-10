package com.jtransc.media.limelibgdx.glsl;

public class Lanes {
	static public String stringify(int[] lanes) {
		String out = "";
		for (int lane : lanes) out += stringify(lane);
		return out;
	}

	static public char stringify(int index) {
		switch (index) {
			case 0:
				return 'x';
			case 1:
				return 'y';
			case 2:
				return 'z';
			case 3:
				return 'w';
			default:
				return '?';
		}
	}

	static public int[] parse(String str) {
		return parse(str, str.length());
	}

	static public int[] parse(String str, int size) {
		int[] out = new int[size];
		for (int n = 0; n < out.length; n++) {
			out[n] = ((str != null) && (n < str.length())) ? parse(str.charAt(n)) : n;
		}
		return out;
	}

	static public int parse(char c) {
		switch (c) {
			case 'x':
			case 'r':
				return 0;
			case 'y':
			case 'g':
				return 1;
			case 'z':
			case 'b':
				return 2;
			case 'w':
			case 'a':
				return 3;
		}
		return -1;
	}
}
