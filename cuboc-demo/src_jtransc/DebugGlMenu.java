
package com.badlogic.cubocy.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jtransc.annotation.JTranscNativeClass;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class DebugGlMenu extends CubocScreen {
	TextureRegion title;
	SpriteBatch batch;
	float time = 0;
	GL20 gl = Gdx.gl;

	public DebugGlMenu(Game game) {
		super(game);
	}

	@Override
	public void show () {
		init();
	}

	int vertexBuffer;
	int program;

	private void init() {
		//initBuffer();
		//initShader();
		HaxeLimeGdxApplication.testInit();
	}

	@JTranscNativeClass("HaxeLimeGdxApplication")
	static private class HaxeLimeGdxApplication {
		static native public void testInit();
		static native public void testFrame();
	}

	/*
	private void initBuffer() {
		vertexBuffer = gl.glGenBuffer();
		gl.glBindBuffer(GL20.GL_ARRAY_BUFFER, vertexBuffer);

		ByteBuffer verticesData = ByteBuffer.allocate(9 * 4).order(ByteOrder.LITTLE_ENDIAN);
		FloatBuffer vertices = verticesData.asFloatBuffer();
		vertices.mark();
		vertices.put(new float[]{
			0f, 1f, 0f,
			-1f, -1f, 0f,
			1f, -1f, 0f
		});
		vertices.reset();

		// @TODO: check size! since it is in bytes!
		gl.glBufferData(GL20.GL_ARRAY_BUFFER, 9 * 4, vertices, GL20.GL_STATIC_DRAW);
	}

	private void initShader() {
		program = compileProgram();
	}

	private int compileProgram() {
		int program = gl.glCreateProgram();
		gl.glAttachShader(program, compileShader(GL20.GL_VERTEX_SHADER, "attribute vec3 aVertexPosition; void main(void) { gl_Position = vec4(aVertexPosition, 1.0); }"));
		gl.glAttachShader(program, compileShader(GL20.GL_FRAGMENT_SHADER, "void main(void) { gl_FragColor = vec4(1.0, 1.0, 1.0, 1.0);}"));

		gl.glLinkProgram(program);
		System.out.println(gl.glGetProgramInfoLog(program));
		return program;
	}

	private int compileShader(int type, String code) {
		int shader = gl.glCreateShader(type);
		gl.glShaderSource(shader, code);
		gl.glCompileShader(shader);
		return shader;
	}
	*/

	@Override
	public void render (float delta) {
		HaxeLimeGdxApplication.testFrame();
		/*
		gl.glEnable(GL20.GL_BLEND);
		gl.glViewport(0, 0, 1280, 720);

		gl.glClearColor(1f, 0f, 1f, 1f);
		gl.glClearStencil(0);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		gl.glDisable(GL20.GL_SCISSOR_TEST);
		gl.glDisable(GL20.GL_STENCIL_TEST);
		gl.glDepthMask(false);
		gl.glColorMask(true, true, true, true);
		gl.glStencilOp(GL20.GL_KEEP, GL20.GL_KEEP, GL20.GL_KEEP);
		gl.glStencilFunc(GL20.GL_EQUAL, 0x00, 0x00);
		gl.glStencilMask(0x00);

		int pos = gl.glGetAttribLocation(program, "aVertexPosition");

		//System.out.println(pos);

		gl.glEnableVertexAttribArray(pos);
		gl.glBindBuffer(GL20.GL_ARRAY_BUFFER, vertexBuffer);

		gl.glVertexAttribPointer(
			pos,               // attribute 0. No particular reason for 0, but must match the layout in the shader.
			3,                 // size
			GL20.GL_FLOAT,     // type
			false,             // normalized?
			0,                 // stride
			0                  // array buffer offset
		);
		gl.glUseProgram(program);
		gl.glDrawArrays(GL20.GL_TRIANGLES, 0, 3); // Starting from vertex 0; 3 vertices total -> 1 triangle
		gl.glDisableVertexAttribArray(pos);
		*/

		time += delta;
		if (time > 1) {
			if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) || Gdx.input.justTouched()) {
				game.setScreen(new MainMenu(game));
			}
		}
	}

	@Override
	public void hide () {
		Gdx.app.debug("Cubocy", "dispose main menu");
		batch.dispose();
		title.getTexture().dispose();
	}
}
