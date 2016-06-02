package com.jtransc.media.limelibgdx.glsl;

import com.jtransc.media.limelibgdx.flash.agal.Agal;
import com.jtransc.media.limelibgdx.flash.agal.GlSlToAgal;
import com.jtransc.media.limelibgdx.glsl.ast.Shader;
import com.jtransc.media.limelibgdx.glsl.ast.Type;
import com.jtransc.media.limelibgdx.util.Tokenizer;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class GlSlParserTest {
	private String fragmentShader = (""
			+ "#ifdef GL_ES\n"
			+ "#define LOWP lowp\n"
			+ "precision mediump float;\n"
			+ "#else\n"
			+ "#define LOWP\n"
			+ "#endif\n"
			+ "varying LOWP vec4 v_color;\n"
			+ "varying vec2 v_texCoords;\n"
			+ "uniform sampler2D u_texture;\n"
			+ "void main()\n"
			+ "{\n"
			+ "	gl_FragColor = v_color * texture2D(u_texture, v_texCoords);\n"
			+ "}\n"
	);

	String vertexShader = (""
			+ "attribute vec4 a_position;\n" //
			+ "attribute vec4 a_color;\n" //
			+ "attribute vec2 a_texCoord0;\n" //
			+ "uniform mat4 u_projTrans;\n" //
			+ "varying vec4 v_color;\n" //
			+ "varying vec2 v_texCoords;\n" //
			+ "\n" //
			+ "void main()\n" //
			+ "{\n" //
			+ "   v_color = a_color;\n" //
			+ "   v_color.a = v_color.a * (255.0/254.0);\n" //
			+ "   v_texCoords = a_texCoord0;\n" //
			+ "   gl_Position =  u_projTrans * a_position;\n" //
			+ "}\n"
	);

	HashMap<String, String> MACROS_GLES = new HashMap<String, String>() {{
		put("GL_ES", "1");
	}};

	HashMap<String, String> MACROS_EMPTY = new HashMap<String, String>() {{
	}};

	@Test
	public void testPreprocessor() throws Exception {
		Assert.assertEquals(
				String.join("\n",
						"precision mediump float;",
						"varying lowp vec4 v_color;",
						"varying vec2 v_texCoords;",
						"uniform sampler2D u_texture;",
						"void main()",
						"{",
						"	gl_FragColor = v_color * texture2D(u_texture, v_texCoords);",
						"}"
				),
				GlSlParser.preprocess(fragmentShader, MACROS_GLES)
		);

		Assert.assertEquals(
				String.join("\n",
						"varying  vec4 v_color;",
						"varying vec2 v_texCoords;",
						"uniform sampler2D u_texture;",
						"void main()",
						"{",
						"	gl_FragColor = v_color * texture2D(u_texture, v_texCoords);",
						"}"
				),
				GlSlParser.preprocess(fragmentShader, MACROS_EMPTY)
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

	@Test
	public void tokenize2() throws Exception {
		String[] tokens = Tokenizer.tokenizeStr("10.1 + 10.2").toArray(new String[0]);
		Assert.assertEquals(
				"10.1:+:10.2",
				String.join(":", tokens)
		);
	}

	@Test
	public void parseAndAgailOptimized() throws Exception {
		Shader fragment = GlSlParser.parse(ShaderType.Fragment, fragmentShader, MACROS_GLES);
		Shader vertex = GlSlParser.parse(ShaderType.Vertex, vertexShader, MACROS_GLES);
		Agal.Program program = GlSlToAgal.compile(vertex, fragment, true);

		Assert.assertEquals(
				String.join("\n", new CharSequence[]{
					"tex ft0, v0, fs0 <2d,linear,repeat,mipnearest>",
					"mul oc, v1, ft0",
				}),
				String.join("\n", (CharSequence[]) program.fragment.sourceCodeArray)
		);

		Assert.assertEquals(
				String.join("\n", new CharSequence[]{
					"mov v1, va0",
					"mul v1.w, v1.w, vc1.x",
					"mov v0, va1",
					"mul op, vc2, va2",
				}),
				String.join("\n", (CharSequence[]) program.vertex.sourceCodeArray)
		);
		Assert.assertEquals(
				new HashMap<Agal.AllocatedLanes, Double>() {{
					put(new Agal.AllocatedLanes("#CST#1.0039370078740157", Type.FLOAT, 1, 0, 1), 255.0 / 254.0);
				}},
				program.getConstants()
		);
		Assert.assertEquals(
				new HashMap<String, Agal.AllocatedLanes>() {{
					put("a_color", new Agal.AllocatedLanes("a_color", Type.VEC4, 0, 0, 4));
					put("a_texCoord0", new Agal.AllocatedLanes("a_texCoord0", Type.VEC2, 1, 0, 4));
					put("a_position", new Agal.AllocatedLanes("a_position", Type.VEC4, 2, 0, 4));
				}},
				program.getAttributes()
		);
		Assert.assertEquals(
				new HashMap<String, Agal.AllocatedLanes>() {{
					put("u_texture", new Agal.AllocatedLanes("u_texture", Type.SAMPLER2D, 0, 0, 4));
					put("u_projTrans", new Agal.AllocatedLanes("u_projTrans", Type.MAT4, 2, 0, 4));
				}},
				program.getUniforms()
		);
	}

	@Test
	public void parseAndAgailNotOptimized() throws Exception {
		Shader fragment = GlSlParser.parse(ShaderType.Fragment, fragmentShader, MACROS_GLES);
		Shader vertex = GlSlParser.parse(ShaderType.Vertex, vertexShader, MACROS_GLES);
		Agal.Program program = GlSlToAgal.compile(vertex, fragment, false);

		Assert.assertEquals(
				String.join("\n", new CharSequence[]{
					"tex ft0, v0, fs0 <2d,linear,repeat,mipnearest>",
					"mul ft0, v1, ft0",
					"mov oc, ft0",
				}),
				String.join("\n", (CharSequence[]) program.fragment.sourceCodeArray)
		);

		Assert.assertEquals(
				String.join("\n", new CharSequence[]{
					"mov v1, va0",
					"div vt0.x, vc1.x, vc1.y",
					"mul vt0, v1.w, vt0.x",
					"mov v1.w, vt0",
					"mov v0, va1",
					"mul vt0, vc2, va2",
					"mov op, vt0",
				}),
				String.join("\n", (CharSequence[]) program.vertex.sourceCodeArray)
		);
		//Assert.assertEquals(
		//		new HashMap<Agal.AllocatedLanes, Double>() {{
		//			put(new Agal.AllocatedLanes("#CST#255.0", Type.FLOAT, 1, 0, 1), 255.0);
		//			put(new Agal.AllocatedLanes("#CST#254.0", Type.FLOAT, 1, 1, 2), 254.0);
		//		}},
		//		program.getConstants()
		//);
	}
}