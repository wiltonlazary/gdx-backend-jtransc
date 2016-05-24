import com.badlogic.cubocy.screens.CubocScreen;
import com.badlogic.cubocy.screens.MainMenu;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jtransc.annotation.JTranscNativeClass;
import com.jtransc.media.limelibgdx.gl.GL;
import com.jtransc.media.limelibgdx.gl.Wrapped;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class DebugGlMenu extends CubocScreen {
	TextureRegion title;
	SpriteBatch batch;
	float time = 0;

	public DebugGlMenu(Game game) {
		super(game);
	}

	@Override
	public void show () {
		init();
	}

	//DebugProgram debugProgram = new DebugProgramNative();
	//DebugProgram debugProgram = new DebugProgramGL();
	DebugProgram debugProgram = new DebugProgramLibgdx();

	private void init() {
		debugProgram.init();
	}

	@JTranscNativeClass("HaxeLimeGdxApplication")
	static private class HaxeLimeGdxApplication {
		static native public void testInit();
		static native public void testFrame();
	}

	@Override
	public void render (float delta) {
		debugProgram.frame();

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

interface DebugProgram {
	void init();
	void frame();
}

class DebugProgramNative implements DebugProgram {
	@Override
	public void init() {
		GL.HaxeLimeGdxApplication.testInit();
	}

	@Override
	public void frame() {
		GL.HaxeLimeGdxApplication.testFrame();
	}
}

class DebugProgramLibgdx implements DebugProgram {
	int vertexBuffer;
	int program;
	GL20 gl = Gdx.gl;

	@Override
	public void init() {
		initBuffer();
		initShader();
	}

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

	@Override
	public void frame() {
		gl.glClearColor(1f, 0f, 1f, 1f);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		int pos = gl.glGetAttribLocation(program, "aVertexPosition");

		//System.out.println(pos);

		gl.glEnableVertexAttribArray(pos);
		gl.glBindBuffer(GL20.GL_ARRAY_BUFFER, vertexBuffer);

		gl.glVertexAttribPointer(pos, 3, GL20.GL_FLOAT, false, 0, 0);
		gl.glUseProgram(program);
		gl.glDrawArrays(GL20.GL_TRIANGLES, 0, 3); // Starting from vertex 0; 3 vertices total -> 1 triangle
		gl.glDisableVertexAttribArray(pos);
	}
}

class DebugProgramGL implements DebugProgram {
	Wrapped<GL.GLBuffer> vertexBuffer;
	Wrapped<GL.GLProgram> program;
	GL gl = GL.HaxeLimeGdxApplication.gl;

	@Override
	public void init() {
		initBuffer();
		initShader();
	}

	private void initBuffer() {
		vertexBuffer = gl.createBuffer();
		gl.bindBuffer(GL20.GL_ARRAY_BUFFER, vertexBuffer);

		// @TODO: check size! since it is in bytes!
		gl.bufferData(GL20.GL_ARRAY_BUFFER, createVertices(), GL20.GL_STATIC_DRAW);
	}

	private GL.Float32Array createVertices() {
		return GL.Float32Array.Utils.create(new float[]{
			0f, 1f, 0f,
			-1f, -1f, 0f,
			1f, -1f, 0f
		});
	}

	private void initShader() {
		program = compileProgram();
	}

	private Wrapped<GL.GLProgram> compileProgram() {
		Wrapped<GL.GLProgram> program = gl.createProgram();
		gl.attachShader(program, compileShader(GL20.GL_VERTEX_SHADER, "attribute vec3 aVertexPosition; void main(void) { gl_Position = vec4(aVertexPosition, 1.0); }"));
		gl.attachShader(program, compileShader(GL20.GL_FRAGMENT_SHADER, "void main(void) { gl_FragColor = vec4(1.0, 1.0, 1.0, 1.0);}"));

		gl.linkProgram(program);
		System.out.println(gl.getProgramInfoLog(program));
		return program;
	}

	private Wrapped<GL.GLShader> compileShader(int type, String code) {
		Wrapped<GL.GLShader> shader = gl.createShader(type);
		gl.shaderSource(shader, code);
		gl.compileShader(shader);
		return shader;
	}

	@Override
	public void frame() {
		gl.clearColor(1f, 0f, 1f, 1f);
		gl.clear(GL20.GL_COLOR_BUFFER_BIT);

		int pos = gl.getAttribLocation(program, "aVertexPosition");

		//System.out.println(pos);

		gl.enableVertexAttribArray(pos);
		gl.bindBuffer(GL20.GL_ARRAY_BUFFER, vertexBuffer);

		gl.vertexAttribPointer(pos, 3, GL20.GL_FLOAT, false, 0, 0);
		gl.useProgram(program);
		gl.drawArrays(GL20.GL_TRIANGLES, 0, 3); // Starting from vertex 0; 3 vertices total -> 1 triangle
		gl.disableVertexAttribArray(pos);
	}
}
