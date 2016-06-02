package com.jtransc.media.limelibgdx;

import com.badlogic.gdx.graphics.GL20;
import com.jtransc.ds.IntPool;
import com.jtransc.media.limelibgdx.glsl.ShaderType;
import com.jtransc.media.limelibgdx.glsl.ast.Type;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Objects;

public class StateGL20 implements GL20Ext {
	static public class Color {
		public float red;
		public float green;
		public float blue;
		public float alpha;
	}

	static public class State {
		public Color clearColor = new Color();
		public float clearDepth;
		public int clearStencil;

		public boolean maskRed;
		public boolean maskGreen;
		public boolean maskBlue;
		public boolean maskAlpha;
		public boolean maskDepth;

		public int activeTexture;

		public boolean cullFaceClockWise;
		public boolean cullFaceEnabled;

		public float depthNear;
		public float depthFar;

		public int scissorX;
		public int scissorY;
		public int scissorWidth;
		public int scissorHeight;

		public float lineWidth;

		public int viewportX;
		public int viewportY;
		public int viewportWidth;
		public int viewportHeight;

		public int stencilMask;
		public int stencilFunc;
		public int stencilRef;
		public int stencilOpFail;
		public int stencilOpZFail;
		public int stencilOpZPass;

		public Color blendColor = new Color();

		public boolean blendEnabled;
		public boolean depthTestEnabled;
		public boolean stencilTestEnabled;
		public boolean scissorTestEnabled;

		public int activeTextureUnit = 0;
		public Texture[] textureUnits = new Texture[8];
		public int blendEquationRGB;
		public int blendEquationAlpha;
		public int blendFuncSrcRGB;
		public int blendFuncDstRGB;
		public int blendFuncSrcAlpha;
		public int blendFuncDstAlpha;
		public int blendFuncSFactor;
		public int blendFuncDFactor;
		public int depthFunc;
		public int frontFace;
		public int generateMipmapHint;
		public boolean[] enabledAttribArrays = new boolean[32];
		public Program program;
		public GLBuffer arrayBuffer;
		public GLBuffer elementArrayBuffer;
		public FrameBuffer frameBuffer;
		public RenderBuffer renderBuffer;

	}

	public interface Disposable {
		void dispose();
	}

	public interface Texture extends Disposable {
		void uploadData(Buffer data, int width, int height);

		void compressedTexImage2D(int level, int internalformat, int width, int height, int border, int imageSize, Buffer data);

		void compressedTexSubImage2D(int level, int xoffset, int yoffset, int width, int height, int format, int imageSize, Buffer data);

		void copyTexImage2D(int level, int internalformat, int x, int y, int width, int height, int border);

		void copyTexSubImage2D(int level, int xoffset, int yoffset, int x, int y, int width, int height);

		void texImage2D(int level, int internalformat, int width, int height, int border, int format, int type, Buffer pixels);

		void parameter(int pname, float param);

		void texSubImage2D(int level, int xoffset, int yoffset, int width, int height, int format, int type, Buffer pixels);

		void generateMipmap();
	}

	abstract static public class Program implements Disposable {
		public Shader fragment;
		public Shader vertex;

		abstract public void link();

		public void attach(Shader shader) {
			switch (shader.type) {
				case Vertex:
					this.vertex = shader;
					break;
				case Fragment:
					this.fragment = shader;
					break;
				default:
					throw new RuntimeException("Unsupported shader type");
			}
		}

		public void detach(Shader shader) {
			switch (shader.type) {
				case Vertex:
					this.vertex = null;
					break;
				case Fragment:
					this.fragment = null;
					break;
				default:
					throw new RuntimeException("Unsupported shader type");
			}
		}

		abstract public String getInfoLog();

		abstract public boolean linked();

		public ArrayList<ProgramUniform> uniforms = new ArrayList<>();
		public ArrayList<ProgramAttribute> attributes = new ArrayList<>();

		public int uniformsCount() {
			return uniforms.size();
		}

		public int attributesCount() {
			return attributes.size();
		}

		public ProgramAttribute getAttrib(int index) {
			return attributes.get(index);
		}

		public ProgramUniform getUniform(int index) {
			return uniforms.get(index);
		}

		abstract public void bindAttribLocation(int index, String name);

		public int getAttribLocation(String name) {
			for (int n = 0; n < attributes.size(); n++) {
				if (Objects.equals(attributes.get(n).name, name)) return n;
			}
			return -1;
		}

		public int getUniformLocation(String name) {
			for (int n = 0; n < uniforms.size(); n++) {
				if (Objects.equals(uniforms.get(n).name, name)) return n;
			}
			return -1;
		}
	}

	static public class ProgramAttributeUniform {
		public String name;
		public int size;
		public Type type;

		public ProgramAttributeUniform(String name, int size, Type type) {
			this.name = name;
			this.size = size;
			this.type = type;
		}

		public int getGlType() {
			if (Objects.equals(type, Type.FLOAT)) return GL20.GL_FLOAT;
			if (Objects.equals(type, Type.VEC2)) return GL20.GL_FLOAT_VEC2;
			if (Objects.equals(type, Type.VEC3)) return GL20.GL_FLOAT_VEC3;
			if (Objects.equals(type, Type.VEC4)) return GL20.GL_FLOAT_VEC4;
			if (Objects.equals(type, Type.MAT2)) return GL20.GL_FLOAT_MAT2;
			if (Objects.equals(type, Type.MAT3)) return GL20.GL_FLOAT_MAT3;
			if (Objects.equals(type, Type.MAT4)) return GL20.GL_FLOAT_MAT4;
			return -1;
		}
	}

	static public class ProgramAttribute extends ProgramAttributeUniform {
		public ProgramAttribute(String name, int size, Type type) {
			super(name, size, type);
		}
	}

	static public class ProgramUniform extends ProgramAttributeUniform {
		public ProgramUniform(String name, int size, Type type) {
			super(name, size, type);
		}
	}


	public static abstract class Shader implements Disposable {
		public ShaderType type;
		public String source;

		public Shader(ShaderType type) {
			this.type = type;
		}

		public ShaderType getType() {
			return type;
		}

		public void setSource(String source) {
			this.source = source;
		}

		abstract public void compile();

		abstract public String getInfoLog();

		abstract public boolean compiled();
	}

	public interface RenderBuffer extends Disposable {
	}

	public interface FrameBuffer extends Disposable {
		int status();
	}

	public interface GLBuffer extends Disposable {
		void data(int size, Buffer data, int usage);

		void subdata(int offset, int size, Buffer data);
	}

	public static abstract class Impl {
		public abstract void clear(State state, boolean color, boolean depth, boolean stencil);

		public abstract Texture createTexture();

		public abstract Program createProgram();

		public abstract Shader createShader(ShaderType type);

		public abstract void render(StateGL20.State state);

		public abstract GLBuffer createBuffer();

		public abstract void drawElements(int mode, int count, int type, Buffer indices);

		public abstract void drawElements(int mode, int count, int type, int indices);

		public abstract void drawArrays(int mode, int first, int count);

		public void finish() {
		}

		public void flush() {
		}

		public void present() {
		}

		public abstract FrameBuffer createFrameBuffer();

		public abstract RenderBuffer createRenderBuffer();
	}

	private State state = new State();
	private Impl impl;
	private Disposable[] objects = new Disposable[2048];
	private IntPool availableIds = new IntPool();

	public StateGL20(Impl impl) {
		this.impl = impl;
	}

	private int allocateObject(Disposable obj) {
		int id = availableIds.alloc();
		objects[id] = obj;
		return id;
	}

	private void deleteObject(int id) {
		objects[id].dispose();
		availableIds.free(id);
		objects[id] = null;
	}

	@Override
	public void glActiveTexture(int texture) {
		state.activeTexture = texture;
	}

	private Texture getActiveTexture(int target) {
		return state.textureUnits[state.activeTextureUnit];
	}

	private void setActiveTexture(int target, Texture texture) {
		state.textureUnits[state.activeTextureUnit] = texture;
	}

	@Override
	public void glBindTexture(int target, int texture) {
		setActiveTexture(target, (Texture) objects[texture]);
	}

	@Override
	public void glBlendFunc(int sfactor, int dfactor) {
		state.blendFuncSFactor = sfactor;
		state.blendFuncDFactor = dfactor;
	}

	@Override
	public void glClear(int mask) {
		impl.clear(
			state,
			((mask & GL20.GL_COLOR_BUFFER_BIT) != 0),
			((mask & GL20.GL_DEPTH_BUFFER_BIT) != 0),
			((mask & GL20.GL_STENCIL_BUFFER_BIT) != 0)
		);
	}

	@Override
	public void glClearColor(float red, float green, float blue, float alpha) {
		state.clearColor.red = red;
		state.clearColor.green = green;
		state.clearColor.blue = blue;
		state.clearColor.alpha = alpha;
	}

	@Override
	public void glClearDepthf(float depth) {
		state.clearDepth = depth;
	}

	@Override
	public void glClearStencil(int s) {
		state.clearStencil = s;
	}

	@Override
	public void glColorMask(boolean red, boolean green, boolean blue, boolean alpha) {
		state.maskRed = red;
		state.maskGreen = green;
		state.maskBlue = blue;
		state.maskAlpha = alpha;
	}

	@Override
	public void glCompressedTexImage2D(int target, int level, int internalformat, int width, int height, int border, int imageSize, Buffer data) {
		getActiveTexture(target).compressedTexImage2D(level, internalformat, width, height, border, imageSize, data);
	}

	@Override
	public void glCompressedTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int imageSize, Buffer data) {
		getActiveTexture(target).compressedTexSubImage2D(level, xoffset, yoffset, width, height, format, imageSize, data);
	}

	@Override
	public void glCopyTexImage2D(int target, int level, int internalformat, int x, int y, int width, int height, int border) {
		getActiveTexture(target).copyTexImage2D(level, internalformat, x, y, width, height, border);
	}

	@Override
	public void glCopyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y, int width, int height) {
		getActiveTexture(target).copyTexSubImage2D(level, xoffset, yoffset, x, y, width, height);
	}

	@Override
	public void glCullFace(int mode) {
		state.cullFaceClockWise = (mode == GL_CW);
	}

	@Override
	public void glDeleteTextures(int count, IntBuffer textures) {
		for (int n = 0; n < count; n++) glDeleteTexture(textures.get(n));
	}

	@Override
	public void glDeleteTexture(int texture) {
		deleteObject(texture);
	}

	@Override
	public void glDepthFunc(int func) {
		state.depthFunc = func;
	}

	@Override
	public void glDepthMask(boolean flag) {
		state.maskDepth = flag;
	}

	@Override
	public void glDepthRangef(float zNear, float zFar) {
		state.depthNear = zNear;
		state.depthFar = zNear;
	}

	private void glEnableDisable(int cap, boolean set) {
		switch (cap) {
			case GL20.GL_BLEND:
				state.blendEnabled = set;
				break;
			case GL20.GL_CULL_FACE:
				state.cullFaceEnabled = set;
				break;
			case GL20.GL_DEPTH_TEST:
				state.depthTestEnabled = set;
				break;
			case GL20.GL_STENCIL_TEST:
				state.stencilTestEnabled = set;
				break;
			case GL20.GL_SCISSOR_TEST:
				state.scissorTestEnabled = set;
				break;
		}
	}

	@Override
	public void glEnable(int cap) {
		glEnableDisable(cap, true);
	}

	@Override
	public void glDisable(int cap) {
		glEnableDisable(cap, false);
	}

	@Override
	public void glDrawArrays(int mode, int first, int count) {
		impl.drawArrays(mode, first, count);
	}

	@Override
	public void glDrawElements(int mode, int count, int type, Buffer indices) {
		impl.drawElements(mode, count, type, indices);
	}

	@Override
	public void glFinish() {
		impl.finish();
	}

	@Override
	public void glFlush() {
		impl.flush();
	}

	@Override
	public void glFrontFace(int mode) {
		state.frontFace = mode;
	}

	@Override
	public void glGenTextures(int count, IntBuffer textures) {
		for (int n = 0; n < count; n++) textures.put(n, glGenTexture());
	}

	@Override
	public int glGenTexture() {
		return allocateObject(impl.createTexture());
	}

	@Override
	public int glGetError() {
		return 0;
	}

	// https://www.khronos.org/opengles/sdk/docs/man3/html/glGet.xhtml
	private int glGetIntegeri(int pname) {
		switch (pname) {
			case GL_ACTIVE_TEXTURE:
				return 0;
			case GL_ALPHA_BITS:
				return 8;
			case GL_BLEND:
				return state.blendEnabled ? 1 : 0;
		}
		//return 0;
		throw new RuntimeException("glGetIntegeri: unknown " + pname);
	}

	@Override
	public void glGetIntegerv(int pname, IntBuffer params) {
		params.put(0, glGetIntegeri(pname));
	}

	@Override
	public String glGetString(int name) {
		switch (name) {
			case GL_VENDOR:
				return "jtransc";
			case GL_RENDERER:
				return "lime";
			case GL_VERSION:
				return "OpenGL ES2.0 lime";
			case GL_SHADING_LANGUAGE_VERSION:
				return "OpenGL ES GLSL ES2.0";
			case GL_EXTENSIONS:
				return "";
		}
		throw new RuntimeException("glGetString: unknown " + name);
	}

	@Override
	public void glHint(int target, int mode) {
		switch (target) {
			case GL20.GL_GENERATE_MIPMAP_HINT:
				state.generateMipmapHint = mode;
				break;
			default:
				throw new RuntimeException("glHint: unknown " + target);
		}
	}

	@Override
	public void glLineWidth(float width) {
		state.lineWidth = width;
	}

	@Override
	public void glPixelStorei(int pname, int param) {
		System.out.println("StateGL20.glPixelStorei(" + "pname = [" + pname + "], param = [" + param + "]" + ")");
	}

	@Override
	public void glPolygonOffset(float factor, float units) {
		System.out.println("StateGL20.glPolygonOffset(" + "factor = [" + factor + "], units = [" + units + "]" + ")");
	}

	@Override
	public void glReadPixels(int x, int y, int width, int height, int format, int type, Buffer pixels) {
		System.out.println("StateGL20.glReadPixels(" + "x = [" + x + "], y = [" + y + "], width = [" + width + "], height = [" + height + "], format = [" + format + "], type = [" + type + "], pixels = [" + pixels + "]" + ")");
	}

	@Override
	public void glScissor(int x, int y, int width, int height) {
		state.scissorX = x;
		state.scissorY = y;
		state.scissorWidth = width;
		state.scissorHeight = height;
	}

	@Override
	public void glStencilFunc(int func, int ref, int mask) {
		state.stencilFunc = func;
		state.stencilRef = ref;
		state.stencilMask = mask;
	}

	@Override
	public void glStencilMask(int mask) {
		state.stencilMask = mask;
	}

	@Override
	public void glStencilOp(int fail, int zfail, int zpass) {
		state.stencilOpFail = fail;
		state.stencilOpZFail = zfail;
		state.stencilOpZPass = zpass;
	}

	@Override
	public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, Buffer pixels) {
		getActiveTexture(target).texImage2D(level, internalformat, width, height, border, format, type, pixels);
	}

	@Override
	public void glTexParameterf(int target, int pname, float param) {
		getActiveTexture(target).parameter(pname, param);
	}

	@Override
	public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, Buffer pixels) {
		getActiveTexture(target).texSubImage2D(level, xoffset, yoffset, width, height, format, type, pixels);
	}

	@Override
	public void glViewport(int x, int y, int width, int height) {
		state.viewportX = x;
		state.viewportY = y;
		state.viewportWidth = width;
		state.viewportHeight = height;
	}

	@Override
	public void glAttachShader(int program, int shader) {
		((Program) objects[program]).attach((Shader) objects[shader]);
	}

	@Override
	public void glBindAttribLocation(int program, int index, String name) {
		((Program) objects[program]).bindAttribLocation(index, name);
	}


	@Override
	public void glBindBuffer(int target, int buffer) {
		switch (target) {
			case GL_ARRAY_BUFFER:
				state.arrayBuffer = ((GLBuffer) objects[buffer]);
				break;
			case GL_ELEMENT_ARRAY_BUFFER:
				state.elementArrayBuffer = ((GLBuffer) objects[buffer]);
				break;
			default:
				throw new RuntimeException("glBindBuffer invalid target");
		}
	}


	@Override
	public void glBindFramebuffer(int target, int framebuffer) {
		switch (target) {
			case GL_FRAMEBUFFER:
				state.frameBuffer = ((FrameBuffer) objects[framebuffer]);
				break;
			default:
				throw new RuntimeException("glBindFramebuffer invalid target");
		}
	}


	@Override
	public void glBindRenderbuffer(int target, int renderbuffer) {
		switch (target) {
			case GL_RENDERBUFFER:
				state.renderBuffer = ((RenderBuffer) objects[renderbuffer]);
				break;
			default:
				throw new RuntimeException("glBindRenderbuffer invalid target");
		}
	}

	@Override
	public void glBlendColor(float red, float green, float blue, float alpha) {
		state.blendColor.red = red;
		state.blendColor.green = green;
		state.blendColor.blue = blue;
		state.blendColor.alpha = alpha;
	}

	@Override
	public void glBlendEquation(int mode) {
		state.blendEquationRGB = mode;
		state.blendEquationAlpha = mode;
	}

	@Override
	public void glBlendEquationSeparate(int modeRGB, int modeAlpha) {
		state.blendEquationRGB = modeRGB;
		state.blendEquationAlpha = modeAlpha;
	}

	@Override
	public void glBlendFuncSeparate(int srcRGB, int dstRGB, int srcAlpha, int dstAlpha) {
		state.blendFuncSrcRGB = srcRGB;
		state.blendFuncDstRGB = dstRGB;
		state.blendFuncSrcAlpha = srcAlpha;
		state.blendFuncDstAlpha = dstAlpha;
	}

	@Override
	public void glBufferData(int target, int size, Buffer data, int usage) {
		switch (target) {
			case GL_ARRAY_BUFFER:
				state.arrayBuffer.data(size, data, usage);
				break;
			case GL_ELEMENT_ARRAY_BUFFER:
				state.elementArrayBuffer.data(size, data, usage);
				break;
			default:
				throw new RuntimeException("glBindBuffer invalid target");
		}
	}

	@Override
	public void glBufferSubData(int target, int offset, int size, Buffer data) {
		switch (target) {
			case GL_ARRAY_BUFFER:
				state.arrayBuffer.subdata(offset, size, data);
				break;
			case GL_ELEMENT_ARRAY_BUFFER:
				state.elementArrayBuffer.subdata(offset, size, data);
				break;
			default:
				throw new RuntimeException("glBindBuffer invalid target");
		}
	}

	@Override
	public int glCheckFramebufferStatus(int target) {
		switch (target) {
			case GL_FRAMEBUFFER:
				return state.frameBuffer.status();
			default:
				throw new RuntimeException("glBindFramebuffer invalid target");
		}
	}

	@Override
	public void glCompileShader(int shader) {
		((Shader) objects[shader]).compile();
	}

	@Override
	public int glCreateProgram() {
		return allocateObject(impl.createProgram());
	}

	@Override
	public int glCreateShader(int type) {
		ShaderType shaderType;
		switch (type) {
			case GL20.GL_FRAGMENT_SHADER:
				shaderType = ShaderType.Fragment;
				break;
			case GL20.GL_VERTEX_SHADER:
				shaderType = ShaderType.Vertex;
				break;
			default:
				throw new RuntimeException("Unknown shader type " + type);
		}
		return allocateObject(impl.createShader(shaderType));
	}

	@Override
	public void glDeleteBuffer(int buffer) {
		deleteObject(buffer);
	}

	@Override
	public void glDeleteBuffers(int n, IntBuffer buffers) {
		for (int i = 0; i < n; i++) glDeleteBuffer(buffers.get(i));
	}

	@Override
	public void glDeleteFramebuffer(int framebuffer) {
		deleteObject(framebuffer);
	}

	@Override
	public void glDeleteFramebuffers(int n, IntBuffer framebuffers) {
		for (int i = 0; i < n; i++) glDeleteFramebuffer(framebuffers.get(i));
	}

	@Override
	public void glDeleteProgram(int program) {
		deleteObject(program);
	}

	@Override
	public void glDeleteRenderbuffer(int renderbuffer) {
		deleteObject(renderbuffer);
	}

	@Override
	public void glDeleteRenderbuffers(int n, IntBuffer renderbuffers) {
		for (int i = 0; i < n; i++) glDeleteRenderbuffer(renderbuffers.get(i));
	}

	@Override
	public void glDeleteShader(int shader) {
		deleteObject(shader);
	}

	@Override
	public void glDetachShader(int program, int shader) {
		((Program) objects[program]).detach((Shader) objects[shader]);
	}

	@Override
	public void glDisableVertexAttribArray(int index) {
		state.enabledAttribArrays[index] = false;
	}

	@Override
	public void glDrawElements(int mode, int count, int type, int indices) {
		impl.drawElements(mode, count, type, indices);
	}

	@Override
	public void glEnableVertexAttribArray(int index) {
		state.enabledAttribArrays[index] = true;
	}

	@Override
	public void glFramebufferRenderbuffer(int target, int attachment, int renderbuffertarget, int renderbuffer) {
		System.out.println("StateGL20.glFramebufferRenderbuffer(" + "target = [" + target + "], attachment = [" + attachment + "], renderbuffertarget = [" + renderbuffertarget + "], renderbuffer = [" + renderbuffer + "]" + ")");
	}

	@Override
	public void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level) {
		System.out.println("StateGL20.glFramebufferTexture2D(" + "target = [" + target + "], attachment = [" + attachment + "], textarget = [" + textarget + "], texture = [" + texture + "], level = [" + level + "]" + ")");
	}

	@Override
	public int glGenBuffer() {
		return allocateObject(impl.createBuffer());
	}

	@Override
	public void glGenBuffers(int n, IntBuffer buffers) {
		for (int i = 0; i < n; i++) buffers.put(glGenBuffer());
	}

	@Override
	public void glGenerateMipmap(int target) {
		getActiveTexture(target).generateMipmap();
	}

	@Override
	public int glGenFramebuffer() {
		return allocateObject(impl.createFrameBuffer());
	}

	@Override
	public void glGenFramebuffers(int n, IntBuffer framebuffers) {
		for (int i = 0; i < n; i++) framebuffers.put(glGenFramebuffer());
	}

	@Override
	public int glGenRenderbuffer() {
		return allocateObject(impl.createRenderBuffer());
	}

	@Override
	public void glGenRenderbuffers(int n, IntBuffer renderbuffers) {
		for (int i = 0; i < n; i++) renderbuffers.put(glGenRenderbuffer());
	}

	@Override
	public String glGetActiveAttrib(int program, int index, IntBuffer size, Buffer type) {
		ProgramAttribute a = ((Program) objects[program]).getAttrib(index);
		size.put(a.size);
		((IntBuffer) type).put(a.getGlType());
		return a.name;
	}

	@Override
	public String glGetActiveUniform(int program, int index, IntBuffer size, Buffer type) {
		ProgramUniform a = ((Program) objects[program]).getUniform(index);
		size.put(a.size);
		((IntBuffer) type).put(a.getGlType());
		return a.name;

	}

	@Override
	public void glGetAttachedShaders(int program, int maxcount, Buffer count, IntBuffer shaders) {
		System.out.println("StateGL20.glGetAttachedShaders(" + "program = [" + program + "], maxcount = [" + maxcount + "], count = [" + count + "], shaders = [" + shaders + "]" + ")");
	}

	@Override
	public int glGetAttribLocation(int program, String name) {
		return ((Program) objects[program]).getAttribLocation(name);
	}

	@Override
	public void glGetBooleanv(int pname, Buffer params) {
		System.out.println("StateGL20.glGetBooleanv(" + "pname = [" + pname + "], params = [" + params + "]" + ")");
	}

	@Override
	public void glGetBufferParameteriv(int target, int pname, IntBuffer params) {
		System.out.println("StateGL20.glGetBufferParameteriv(" + "target = [" + target + "], pname = [" + pname + "], params = [" + params + "]" + ")");
	}

	@Override
	public void glGetFloatv(int pname, FloatBuffer params) {
		System.out.println("StateGL20.glGetFloatv(" + "pname = [" + pname + "], params = [" + params + "]" + ")");
	}

	@Override
	public void glGetFramebufferAttachmentParameteriv(int target, int attachment, int pname, IntBuffer params) {
		System.out.println("StateGL20.glGetFramebufferAttachmentParameteriv(" + "target = [" + target + "], attachment = [" + attachment + "], pname = [" + pname + "], params = [" + params + "]" + ")");
	}

	public int glGetProgrami(int program, int pname) {
		Program p = (Program) objects[program];
		switch (pname) {
			case GL20.GL_LINK_STATUS:
				return p.linked() ? 1 : 0;
			case GL20.GL_ACTIVE_UNIFORMS:
				return p.uniformsCount();
			case GL20.GL_ACTIVE_ATTRIBUTES:
				return p.attributesCount();
			default:
				throw new RuntimeException("Special glGetProgrami:" + pname);
		}
	}

	@Override
	public void glGetProgramiv(int program, int pname, IntBuffer params) {
		params.put(glGetProgrami(program, pname));
	}

	@Override
	public String glGetProgramInfoLog(int program) {
		return ((Program) objects[program]).getInfoLog();
	}

	@Override
	public void glGetRenderbufferParameteriv(int target, int pname, IntBuffer params) {
		System.out.println("StateGL20.glGetRenderbufferParameteriv(" + "target = [" + target + "], pname = [" + pname + "], params = [" + params + "]" + ")");
	}

	public int glGetShaderi(int shader, int pname) {
		switch (pname) {
			case GL20.GL_COMPILE_STATUS:
				return ((Shader) objects[shader]).compiled() ? 1 : 0;
			default:
				throw new RuntimeException("Special glGetShaderi:" + pname);
		}
	}

	@Override
	public void glGetShaderiv(int shader, int pname, IntBuffer params) {
		params.put(glGetShaderi(shader, pname));
	}

	@Override
	public String glGetShaderInfoLog(int shader) {
		return ((Shader) objects[shader]).getInfoLog();
	}

	@Override
	public void glGetShaderPrecisionFormat(int shadertype, int precisiontype, IntBuffer range, IntBuffer precision) {
		System.out.println("StateGL20.glGetShaderPrecisionFormat(" + "shadertype = [" + shadertype + "], precisiontype = [" + precisiontype + "], range = [" + range + "], precision = [" + precision + "]" + ")");
	}

	@Override
	public void glGetTexParameterfv(int target, int pname, FloatBuffer params) {
		System.out.println("StateGL20.glGetTexParameterfv(" + "target = [" + target + "], pname = [" + pname + "], params = [" + params + "]" + ")");
	}

	@Override
	public void glGetTexParameteriv(int target, int pname, IntBuffer params) {
		System.out.println("StateGL20.glGetTexParameteriv(" + "target = [" + target + "], pname = [" + pname + "], params = [" + params + "]" + ")");
	}

	@Override
	public void glGetUniformfv(int program, int location, FloatBuffer params) {
		System.out.println("StateGL20.glGetUniformfv(" + "program = [" + program + "], location = [" + location + "], params = [" + params + "]" + ")");
	}

	@Override
	public void glGetUniformiv(int program, int location, IntBuffer params) {
		System.out.println("StateGL20.glGetUniformiv(" + "program = [" + program + "], location = [" + location + "], params = [" + params + "]" + ")");
	}

	@Override
	public int glGetUniformLocation(int program, String name) {
		return ((Program) objects[program]).getUniformLocation(name);
	}

	@Override
	public void glGetVertexAttribfv(int index, int pname, FloatBuffer params) {
		System.out.println("StateGL20.glGetVertexAttribfv(" + "index = [" + index + "], pname = [" + pname + "], params = [" + params + "]" + ")");
	}

	@Override
	public void glGetVertexAttribiv(int index, int pname, IntBuffer params) {
		System.out.println("StateGL20.glGetVertexAttribiv(" + "index = [" + index + "], pname = [" + pname + "], params = [" + params + "]" + ")");
	}

	@Override
	public void glGetVertexAttribPointerv(int index, int pname, Buffer pointer) {
		System.out.println("StateGL20.glGetVertexAttribPointerv(" + "index = [" + index + "], pname = [" + pname + "], pointer = [" + pointer + "]" + ")");
	}

	@Override
	public boolean glIsBuffer(int buffer) {
		return objects[buffer] instanceof GLBuffer;
	}

	@Override
	public boolean glIsEnabled(int cap) {
		switch (cap) {
			case GL20.GL_BLEND:
				return state.blendEnabled;
			case GL20.GL_CULL_FACE:
				return state.cullFaceEnabled;
			case GL20.GL_DEPTH_TEST:
				return state.depthTestEnabled;
			case GL20.GL_STENCIL_TEST:
				return state.stencilTestEnabled;
			case GL20.GL_SCISSOR_TEST:
				return state.scissorTestEnabled;
			default:
				return false;
		}
	}

	@Override
	public boolean glIsFramebuffer(int framebuffer) {
		return objects[framebuffer] instanceof FrameBuffer;
	}

	@Override
	public boolean glIsProgram(int program) {
		return objects[program] instanceof Program;
	}

	@Override
	public boolean glIsRenderbuffer(int renderbuffer) {
		return objects[renderbuffer] instanceof RenderBuffer;
	}

	@Override
	public boolean glIsShader(int shader) {
		return objects[shader] instanceof Shader;
	}

	@Override
	public boolean glIsTexture(int texture) {
		return objects[texture] instanceof Texture;
	}

	@Override
	public void glLinkProgram(int program) {
		((Program) objects[program]).link();
	}

	@Override
	public void glReleaseShaderCompiler() {
		System.out.println("StateGL20.glReleaseShaderCompiler(" + "" + ")");
	}

	@Override
	public void glRenderbufferStorage(int target, int internalformat, int width, int height) {
		System.out.println("StateGL20.glRenderbufferStorage(" + "target = [" + target + "], internalformat = [" + internalformat + "], width = [" + width + "], height = [" + height + "]" + ")");
	}

	@Override
	public void glSampleCoverage(float value, boolean invert) {
		System.out.println("StateGL20.glSampleCoverage(" + "value = [" + value + "], invert = [" + invert + "]" + ")");
	}

	@Override
	public void glShaderBinary(int n, IntBuffer shaders, int binaryformat, Buffer binary, int length) {
		System.out.println("StateGL20.glShaderBinary(" + "n = [" + n + "], shaders = [" + shaders + "], binaryformat = [" + binaryformat + "], binary = [" + binary + "], length = [" + length + "]" + ")");
	}

	@Override
	public void glShaderSource(int shader, String string) {
		((Shader) objects[shader]).setSource(string);
	}

	@Override
	public void glStencilFuncSeparate(int face, int func, int ref, int mask) {
		System.out.println("StateGL20.glStencilFuncSeparate(" + "face = [" + face + "], func = [" + func + "], ref = [" + ref + "], mask = [" + mask + "]" + ")");
	}

	@Override
	public void glStencilMaskSeparate(int face, int mask) {
		System.out.println("StateGL20.glStencilMaskSeparate(" + "face = [" + face + "], mask = [" + mask + "]" + ")");
	}

	@Override
	public void glStencilOpSeparate(int face, int fail, int zfail, int zpass) {
		System.out.println("StateGL20.glStencilOpSeparate(" + "face = [" + face + "], fail = [" + fail + "], zfail = [" + zfail + "], zpass = [" + zpass + "]" + ")");
	}

	@Override
	public void glTexParameterfv(int target, int pname, FloatBuffer params) {
		System.out.println("StateGL20.glTexParameterfv(" + "target = [" + target + "], pname = [" + pname + "], params = [" + params + "]" + ")");
	}

	@Override
	public void glTexParameteri(int target, int pname, int param) {
		System.out.println("StateGL20.glTexParameteri(" + "target = [" + target + "], pname = [" + pname + "], param = [" + param + "]" + ")");
	}

	@Override
	public void glTexParameteriv(int target, int pname, IntBuffer params) {
		System.out.println("StateGL20.glTexParameteriv(" + "target = [" + target + "], pname = [" + pname + "], params = [" + params + "]" + ")");
	}

	@Override
	public void glUniform1f(int location, float x) {
		System.out.println("StateGL20.glUniform1f(" + "location = [" + location + "], x = [" + x + "]" + ")");
	}

	@Override
	public void glUniform1fv(int location, int count, FloatBuffer v) {
		System.out.println("StateGL20.glUniform1fv(" + "location = [" + location + "], count = [" + count + "], v = [" + v + "]" + ")");
	}

	@Override
	public void glUniform1fv(int location, int count, float[] v, int offset) {
		System.out.println("StateGL20.glUniform1fv(" + "location = [" + location + "], count = [" + count + "], v = [" + v + "], offset = [" + offset + "]" + ")");
	}

	@Override
	public void glUniform1i(int location, int x) {
		System.out.println("StateGL20.glUniform1i(" + "location = [" + location + "], x = [" + x + "]" + ")");
	}

	@Override
	public void glUniform1iv(int location, int count, IntBuffer v) {
		System.out.println("StateGL20.glUniform1iv(" + "location = [" + location + "], count = [" + count + "], v = [" + v + "]" + ")");
	}

	@Override
	public void glUniform1iv(int location, int count, int[] v, int offset) {
		System.out.println("StateGL20.glUniform1iv(" + "location = [" + location + "], count = [" + count + "], v = [" + v + "], offset = [" + offset + "]" + ")");
	}

	@Override
	public void glUniform2f(int location, float x, float y) {
		System.out.println("StateGL20.glUniform2f(" + "location = [" + location + "], x = [" + x + "], y = [" + y + "]" + ")");
	}

	@Override
	public void glUniform2fv(int location, int count, FloatBuffer v) {
		System.out.println("StateGL20.glUniform2fv(" + "location = [" + location + "], count = [" + count + "], v = [" + v + "]" + ")");
	}

	@Override
	public void glUniform2fv(int location, int count, float[] v, int offset) {
		System.out.println("StateGL20.glUniform2fv(" + "location = [" + location + "], count = [" + count + "], v = [" + v + "], offset = [" + offset + "]" + ")");
	}

	@Override
	public void glUniform2i(int location, int x, int y) {
		System.out.println("StateGL20.glUniform2i(" + "location = [" + location + "], x = [" + x + "], y = [" + y + "]" + ")");
	}

	@Override
	public void glUniform2iv(int location, int count, IntBuffer v) {
		System.out.println("StateGL20.glUniform2iv(" + "location = [" + location + "], count = [" + count + "], v = [" + v + "]" + ")");
	}

	@Override
	public void glUniform2iv(int location, int count, int[] v, int offset) {
		System.out.println("StateGL20.glUniform2iv(" + "location = [" + location + "], count = [" + count + "], v = [" + v + "], offset = [" + offset + "]" + ")");
	}

	@Override
	public void glUniform3f(int location, float x, float y, float z) {
		System.out.println("StateGL20.glUniform3f(" + "location = [" + location + "], x = [" + x + "], y = [" + y + "], z = [" + z + "]" + ")");
	}

	@Override
	public void glUniform3fv(int location, int count, FloatBuffer v) {
		System.out.println("StateGL20.glUniform3fv(" + "location = [" + location + "], count = [" + count + "], v = [" + v + "]" + ")");
	}

	@Override
	public void glUniform3fv(int location, int count, float[] v, int offset) {
		System.out.println("StateGL20.glUniform3fv(" + "location = [" + location + "], count = [" + count + "], v = [" + v + "], offset = [" + offset + "]" + ")");
	}

	@Override
	public void glUniform3i(int location, int x, int y, int z) {
		System.out.println("StateGL20.glUniform3i(" + "location = [" + location + "], x = [" + x + "], y = [" + y + "], z = [" + z + "]" + ")");
	}

	@Override
	public void glUniform3iv(int location, int count, IntBuffer v) {
		System.out.println("StateGL20.glUniform3iv(" + "location = [" + location + "], count = [" + count + "], v = [" + v + "]" + ")");
	}

	@Override
	public void glUniform3iv(int location, int count, int[] v, int offset) {
		System.out.println("StateGL20.glUniform3iv(" + "location = [" + location + "], count = [" + count + "], v = [" + v + "], offset = [" + offset + "]" + ")");
	}

	@Override
	public void glUniform4f(int location, float x, float y, float z, float w) {
		System.out.println("StateGL20.glUniform4f(" + "location = [" + location + "], x = [" + x + "], y = [" + y + "], z = [" + z + "], w = [" + w + "]" + ")");
	}

	@Override
	public void glUniform4fv(int location, int count, FloatBuffer v) {
		System.out.println("StateGL20.glUniform4fv(" + "location = [" + location + "], count = [" + count + "], v = [" + v + "]" + ")");
	}

	@Override
	public void glUniform4fv(int location, int count, float[] v, int offset) {
		System.out.println("StateGL20.glUniform4fv(" + "location = [" + location + "], count = [" + count + "], v = [" + v + "], offset = [" + offset + "]" + ")");
	}

	@Override
	public void glUniform4i(int location, int x, int y, int z, int w) {
		System.out.println("StateGL20.glUniform4i(" + "location = [" + location + "], x = [" + x + "], y = [" + y + "], z = [" + z + "], w = [" + w + "]" + ")");
	}

	@Override
	public void glUniform4iv(int location, int count, IntBuffer v) {
		System.out.println("StateGL20.glUniform4iv(" + "location = [" + location + "], count = [" + count + "], v = [" + v + "]" + ")");
	}

	@Override
	public void glUniform4iv(int location, int count, int[] v, int offset) {
		System.out.println("StateGL20.glUniform4iv(" + "location = [" + location + "], count = [" + count + "], v = [" + v + "], offset = [" + offset + "]" + ")");
	}

	@Override
	public void glUniformMatrix2fv(int location, int count, boolean transpose, FloatBuffer value) {
		System.out.println("StateGL20.glUniformMatrix2fv(" + "location = [" + location + "], count = [" + count + "], transpose = [" + transpose + "], value = [" + value + "]" + ")");
	}

	@Override
	public void glUniformMatrix2fv(int location, int count, boolean transpose, float[] value, int offset) {
		System.out.println("StateGL20.glUniformMatrix2fv(" + "location = [" + location + "], count = [" + count + "], transpose = [" + transpose + "], value = [" + value + "], offset = [" + offset + "]" + ")");
	}

	@Override
	public void glUniformMatrix3fv(int location, int count, boolean transpose, FloatBuffer value) {
		System.out.println("StateGL20.glUniformMatrix3fv(" + "location = [" + location + "], count = [" + count + "], transpose = [" + transpose + "], value = [" + value + "]" + ")");
	}

	@Override
	public void glUniformMatrix3fv(int location, int count, boolean transpose, float[] value, int offset) {
		System.out.println("StateGL20.glUniformMatrix3fv(" + "location = [" + location + "], count = [" + count + "], transpose = [" + transpose + "], value = [" + value + "], offset = [" + offset + "]" + ")");
	}

	@Override
	public void glUniformMatrix4fv(int location, int count, boolean transpose, FloatBuffer value) {
		System.out.println("StateGL20.glUniformMatrix4fv(" + "location = [" + location + "], count = [" + count + "], transpose = [" + transpose + "], value = [" + value + "]" + ")");
	}

	@Override
	public void glUniformMatrix4fv(int location, int count, boolean transpose, float[] value, int offset) {
		System.out.println("StateGL20.glUniformMatrix4fv(" + "location = [" + location + "], count = [" + count + "], transpose = [" + transpose + "], value = [" + value + "], offset = [" + offset + "]" + ")");
	}

	@Override
	public void glUseProgram(int program) {
		state.program = (Program) objects[program];
	}

	@Override
	public void glValidateProgram(int program) {
		System.out.println("StateGL20.glValidateProgram(" + "program = [" + program + "]" + ")");
	}

	@Override
	public void glVertexAttrib1f(int indx, float x) {
		System.out.println("StateGL20.glVertexAttrib1f(" + "indx = [" + indx + "], x = [" + x + "]" + ")");
	}

	@Override
	public void glVertexAttrib1fv(int indx, FloatBuffer values) {
		System.out.println("StateGL20.glVertexAttrib1fv(" + "indx = [" + indx + "], values = [" + values + "]" + ")");
	}

	@Override
	public void glVertexAttrib2f(int indx, float x, float y) {
		System.out.println("StateGL20.glVertexAttrib2f(" + "indx = [" + indx + "], x = [" + x + "], y = [" + y + "]" + ")");
	}

	@Override
	public void glVertexAttrib2fv(int indx, FloatBuffer values) {
		System.out.println("StateGL20.glVertexAttrib2fv(" + "indx = [" + indx + "], values = [" + values + "]" + ")");
	}

	@Override
	public void glVertexAttrib3f(int indx, float x, float y, float z) {
		System.out.println("StateGL20.glVertexAttrib3f(" + "indx = [" + indx + "], x = [" + x + "], y = [" + y + "], z = [" + z + "]" + ")");
	}

	@Override
	public void glVertexAttrib3fv(int indx, FloatBuffer values) {
		System.out.println("StateGL20.glVertexAttrib3fv(" + "indx = [" + indx + "], values = [" + values + "]" + ")");
	}

	@Override
	public void glVertexAttrib4f(int indx, float x, float y, float z, float w) {
		System.out.println("StateGL20.glVertexAttrib4f(" + "indx = [" + indx + "], x = [" + x + "], y = [" + y + "], z = [" + z + "], w = [" + w + "]" + ")");
	}

	@Override
	public void glVertexAttrib4fv(int indx, FloatBuffer values) {
		System.out.println("StateGL20.glVertexAttrib4fv(" + "indx = [" + indx + "], values = [" + values + "]" + ")");
	}

	@Override
	public void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, Buffer ptr) {
		System.out.println("StateGL20.glVertexAttribPointer(" + "indx = [" + indx + "], size = [" + size + "], type = [" + type + "], normalized = [" + normalized + "], stride = [" + stride + "], ptr = [" + ptr + "]" + ")");
	}

	@Override
	public void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, int ptr) {
		System.out.println("StateGL20.glVertexAttribPointer(" + "indx = [" + indx + "], size = [" + size + "], type = [" + type + "], normalized = [" + normalized + "], stride = [" + stride + "], ptr = [" + ptr + "]" + ")");
	}

	@Override
	public void present() {
		impl.present();
	}
}
