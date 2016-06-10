package com.jtransc.media.limelibgdx.soft;

import com.badlogic.gdx.graphics.GL20;
import com.jtransc.media.limelibgdx.StateGL20;
import org.junit.Assert;
import org.junit.Test;

import java.nio.FloatBuffer;

public class SoftGlTest {
	final int width = 16;
	final int height = 16;
	StateGL20<SoftGL20.SoftImpl> gl = SoftGL20.create(width, height);
	SoftGL20.SoftImpl impl = gl.getImpl();

	@Test
	public void testClear() throws Exception {
		gl.glClearColor(1f, 0f, 0.5f, 1f);
		gl.glClear(0); // NO CLEAR COLOR

		Assert.assertEquals("RGBA(0,0, 0,0)RGBA(0,0, 0,0)RGBA(0,0, 0,0)RGBA(0,0, 0,0)", impl.backbuffer.sliceCopy(0, 0, 2, 2).dump());

		gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // CLEAR COLOR

		Assert.assertEquals("RGBA(255,0, 127,255)RGBA(255,0, 127,255)RGBA(255,0, 127,255)RGBA(255,0, 127,255)", impl.backbuffer.sliceCopy(0, 0, 2, 2).dump());
	}

	private int createShader(int type, String code) {
		int shader = gl.glCreateShader(type);
		gl.glShaderSource(shader, code);
		gl.glCompileShader(shader);
		return shader;
	}

	private int createProgram(String vertexCode, String fragmentCode) {
		int program = gl.glCreateProgram();
		gl.glAttachShader(program, createShader(GL20.GL_VERTEX_SHADER, vertexCode));
		gl.glAttachShader(program, createShader(GL20.GL_FRAGMENT_SHADER, fragmentCode));
		gl.glLinkProgram(program);
		gl.glUseProgram(program);
		return program;
	}

	private int createSolidColorProgram() {
		return createProgram(
			"attribute vec4 aVertexPosition; void main() { gl_Position = aVertexPosition; }",
			"uniform vec4 u_color; void main() { gl_FragColor = u_color; }"
		);
	}

	private int createTriangle() {
		int vertexBuffer = gl.glGenBuffer();
		gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vertexBuffer);

		FloatBuffer vertices = FloatBuffer.allocate(9);
		vertices.put(new float[]{
			0, 1, 0,
			-1, -1, 0,
			1, -1, 0
		});
		gl.glBufferData(gl.GL_ARRAY_BUFFER, 9, vertices, gl.GL_STATIC_DRAW);
		return vertexBuffer;
	}

	@Test
	public void testShaders() throws Exception {
		int program = createSolidColorProgram();

		int a_position = gl.glGetAttribLocation(program, "aVertexPosition");
		int u_color = gl.glGetUniformLocation(program, "u_color");

		Assert.assertEquals(0, a_position);
		Assert.assertEquals(0, u_color);
	}

	@Test
	public void testDraw() throws Exception {
		//int program = createProgram(
		//	"attribute vec3 aVertexPosition; void main(void) { gl_Position = vec4(aVertexPosition, 1.0); }",
		//	"void main(void) { gl_FragColor = vec4(1.0, 1.0, 1.0, 1.0); }"
		//);
		int program = createSolidColorProgram();
		int vertexBuffer = createTriangle();

		gl.glEnable(GL20.GL_BLEND);
		gl.glViewport(0, 0, 1280, 720);

		gl.glClearColor(1, 0, 1, 1);
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

		gl.glEnableVertexAttribArray(pos);
		gl.glBindBuffer(GL20.GL_ARRAY_BUFFER, vertexBuffer);

		gl.glVertexAttribPointer(
			pos,               // attribute 0. No particular reason for 0, but must match the layout in the shader.
			3,                 // size
			GL20.GL_FLOAT,     // opcode
			false,             // normalized?
			0,                 // stride
			0                  // array buffer offset
		);
		gl.glUseProgram(program);
		gl.glDrawArrays(GL20.GL_TRIANGLES, 0, 3); // Starting from vertex 0; 3 vertices total -> 1 triangle
		gl.glDisableVertexAttribArray(pos);
	}
}
