package com.jtransc.media.limelibgdx.util;

public class StringUtils {
	//static private String[] hexs = new String[256];
	//
	//static {
	//	for (int n = 0; n < 0x100; n++) {
	//		hexs[n] = toHexString((byte)n);
	//	}
	//}

	static public String toHexString(byte data) {
		String out = "00" + Integer.toHexString(data & 0xFF);
		return out.substring(out.length() - 2);
	}

	static public String toHexString(byte[] data) {
		StringBuilder sb = new StringBuilder(data.length * 2);
		for (int n = 0; n < data.length; n++) sb.append(toHexString(data[n]));
		return sb.toString();
	}
}
