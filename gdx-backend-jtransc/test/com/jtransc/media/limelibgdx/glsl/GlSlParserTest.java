package com.jtransc.media.limelibgdx.glsl;

import com.jtransc.media.limelibgdx.util.Tokenizer;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class GlSlParserTest {
	private String fragmentShader = ("" +
		"#ifdef GL_ES\n" +
		"#define LOWP lowp\n" +
		"precision mediump float;\n" +
		"#else\n" +
		"#define LOWP\n" +
		"#endif\n" +
		"varying LOWP vec4 v_color;\n" +
		"varying vec2 v_texCoords;\n" +
		"uniform sampler2D u_texture;\n" +
		"void main()\n" +
		"{\n" +
		"	gl_FragColor = v_color * texture2D(u_texture, v_texCoords);\n" +
		"}\n"
	);

	@Test
	public void testPreprocessor() throws Exception {
		Assert.assertEquals(
			String.join("\n", new String[]{
				"precision mediump float;",
				"varying lowp vec4 v_color;",
				"varying vec2 v_texCoords;",
				"uniform sampler2D u_texture;",
				"void main()",
				"{",
				"	gl_FragColor = v_color * texture2D(u_texture, v_texCoords);",
				"}"
			})
			,
			GlSlParser.preprocess(fragmentShader, new HashMap<String, String>() {{
				put("GL_ES", "1");
			}})
		);

		Assert.assertEquals(
			String.join("\n", new String[]{
				"varying  vec4 v_color;",
				"varying vec2 v_texCoords;",
				"uniform sampler2D u_texture;",
				"void main()",
				"{",
				"	gl_FragColor = v_color * texture2D(u_texture, v_texCoords);",
				"}"
			})
			,
			GlSlParser.preprocess(fragmentShader, new HashMap<String, String>() {{
			}})
		);
	}

	@Test
	public void tokenize() throws Exception {
		String[] tokens = Tokenizer.tokenizeStr("hello world, test + 12 + 3").toArray(new String[0]);
		Assert.assertEquals(
			"hello:world:,:test:+:12:+:3",
			String.join(":", tokens)
		);
	}

	//@Test
	//public void parse() throws Exception {
	//	Ast.Program program = GlSlParser.parse(fragmentShader, new HashMap<String, String>() {{
	//		put("GL_ES", "1");
	//	}});
	//	System.out.println(program);
	//}
}