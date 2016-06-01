package com.jtransc.media.limelibgdx.glsl;

import java.util.Map;

public class GlSlParser {
	public static String preprocess(String shader, Map<String, String> macros) {
		return String.join("\n", CPreprocessor.preprocess(shader.split("\n"), macros));
	}

	public static void parseShader(String shader) {
	}
}
