package com.jtransc.media.limelibgdx.util;

public class NumberUtils {
	static public boolean isNumber(String str) {
		return str.length() >= 1 && Character.isDigit(str.charAt(0));
	}
}
