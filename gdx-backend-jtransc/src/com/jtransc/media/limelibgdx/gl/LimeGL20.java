package com.jtransc.media.limelibgdx.gl;

import com.badlogic.gdx.graphics.GL20;
import com.jtransc.JTranscSystem;
import com.jtransc.annotation.haxe.HaxeMethodBodyPre;
import com.jtransc.media.limelibgdx.dummy.DummyGL20;
import com.jtransc.annotation.haxe.HaxeAddMembers;
import com.jtransc.annotation.haxe.HaxeImports;
import com.jtransc.annotation.haxe.HaxeMethodBody;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

// https://github.com/openfl/lime/blob/develop/lime/graphics/opengl/gl.hx
@HaxeImports({
	"import lime.graphics.opengl.GL;",
	"import lime.graphics.opengl.GLTexture;",
	"import lime.graphics.opengl.GLProgram;",
	"import lime.graphics.opengl.GLShader;",
	"import lime.graphics.opengl.GLBuffer;",
	"import lime.graphics.opengl.GLFramebuffer;",
	"import lime.graphics.opengl.GLRenderbuffer;",
	"import lime.graphics.opengl.GLUniformLocation;",
})
@HaxeAddMembers({
	"var gl: lime.graphics.GLRenderContext;",
	"static private function _buffer(a, size:Int = -1) { return HaxeLimeGdxApplication.convertBuffer(a, size); }",
	"static private function _floatBuffer(a, size:Int = -1) { return HaxeLimeGdxApplication.convertFloatBuffer(a, size); }",
	"static private function _intBuffer(a, size:Int = -1) { return HaxeLimeGdxApplication.convertIntBuffer(a, size); }",
	"static private function _intArray(a, offset:Int, size:Int) { return HaxeLimeGdxApplication.convertIntArray(a, offset, size); }",
	"static private function _floatArray(a, offset:Int, size:Int) { return HaxeLimeGdxApplication.convertFloatArray(a, offset, size); }",
	// GLTexture
	"static public var lastId = 1000;",
	"static public var textures = new Map<Int, GLTexture>();",
	"static public var programs = new Map<Int, GLProgram>();",
	"static public var shaders = new Map<Int, GLShader>();",
	"static public var buffers = new Map<Int, GLBuffer>();",
	"static public var frameBuffers = new Map<Int, GLFramebuffer>();",
	"static public var renderBuffers = new Map<Int, GLRenderbuffer>();",
	"static public var uniformLocations = new Map<Int, GLUniformLocation>();",
})
public class LimeGL20 extends DummyGL20 implements GL20 {
	public LimeGL20() {
		initGL();
	}

	@HaxeMethodBody("this.gl = HaxeLimeGdxApplication.gl;")
	native private void initGL();

	@HaxeMethodBody("gl.activeTexture(p0);")
	native public void glActiveTexture(int texture);

	static public int bindedTextureId = 0;

	@HaxeMethodBody("{% FIELD com.jtransc.media.limelibgdx.gl.LimeGL20:bindedTextureId %} = p1; gl.bindTexture(p0, textures.get(p1));")
	native public void glBindTexture(int target, int texture);

	@HaxeMethodBody("gl.blendFunc(p0, p1);")
	native public void glBlendFunc(int sfactor, int dfactor);

	@HaxeMethodBody("gl.clear(p0);")
	native private void _glClear(int mask);

	public void glClear(int mask) {
		//glClearColor(1f, 0f, 0f, 1f);
		_glClear(mask);
	}

	@HaxeMethodBody("gl.clearColor(p0, p1, p2, p3);")
	native public void glClearColor(float red, float green, float blue, float alpha);

	@HaxeMethodBody("gl.clearDepth(p0);")
	native public void glClearDepthf(float depth);

	@HaxeMethodBody("gl.clearStencil(p0);")
	native public void glClearStencil(int s);

	@HaxeMethodBody("gl.colorMask(p0, p1, p2, p3);")
	native public void glColorMask(boolean red, boolean green, boolean blue, boolean alpha);

	@HaxeMethodBody("gl.compressedTexImage2D(p0, p1, p2, p3, p4, p5, _buffer(p7, p6));")
	native public void glCompressedTexImage2D(int target, int level, int internalformat, int width, int height, int border, int imageSize, Buffer data);

	@HaxeMethodBody("gl.compressedTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, _buffer(p8, p7));")
	native public void glCompressedTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int imageSize, Buffer data);

	@HaxeMethodBody("gl.copyTexImage2D(p0, p1, p2, p3, p4, p5, p6, p7);")
	native public void glCopyTexImage2D(int target, int level, int internalformat, int x, int y, int width, int height, int border);

	@HaxeMethodBody("gl.copyTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7);")
	native public void glCopyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y, int width, int height);

	@HaxeMethodBody("gl.cullFace(p0);")
	native public void glCullFace(int mode);

	public void glDeleteTextures(int n, IntBuffer textures) {
		for (int i = 0; i < n; i++) glDeleteTexture(textures.get(i));
	}

	@HaxeMethodBody("gl.deleteTexture(textures.get(p0)); textures.remove(p0);")
	native public void glDeleteTexture(int texture);

	@HaxeMethodBody("gl.depthFunc(p0);")
	native public void glDepthFunc(int func);

	@HaxeMethodBody("gl.depthMask(p0);")
	native public void glDepthMask(boolean flag);

	@HaxeMethodBody("gl.depthRange(p0, p1);")
	native public void glDepthRangef(float zNear, float zFar);

	@HaxeMethodBody("gl.disable(p0);")
	native public void glDisable(int cap);

	@HaxeMethodBody("gl.drawArrays(p0, p1, p2);")
	native public void glDrawArrays(int mode, int first, int count);

	//private int tempIndicesBuffer = -1;
	public void glDrawElements(int mode, int count, int type, Buffer indices) {
		/*
		if (tempIndicesBuffer < 0) {
			tempIndicesBuffer = glGenBuffer();
		}
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, tempIndicesBuffer);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices.limit(), indices, GL_STATIC_DRAW);
		*/
		_glDrawElements(mode, count, type, indices.position());
	}

	@HaxeMethodBody("gl.drawElements(p0, p1, p2, p3);")
	native private void _glDrawElements(int mode, int count, int type, int offset);

	@HaxeMethodBody("gl.enable(p0);")
	native public void glEnable(int cap);

	@HaxeMethodBody("gl.finish();")
	native public void glFinish();

	@HaxeMethodBody("gl.flush();")
	native public void glFlush();

	@HaxeMethodBody("gl.frontFace(p0);")
	native public void glFrontFace(int mode);

	public void glGenTextures(int n, IntBuffer textures) {
		for (int i = 0; i < n; i++) textures.put(i, glGenTexture());
	}

	@HaxeMethodBody("var id = lastId++; var tex = gl.createTexture(); textures.set(id, tex); return id;")
	native public int glGenTexture();

	@HaxeMethodBody("return gl.getError();")
	native public int glGetError();

	@HaxeMethodBody("return gl.getParameter(p0);")
	native private int glGetInteger(int pname);

	private int glGetInteger2(int pname) {
		switch (pname) {
			case GL20.GL_TEXTURE_BINDING_2D:
				return bindedTextureId;
		}
		return glGetInteger(pname);
	}

	@HaxeMethodBody("return gl.getParameter(p0);")
	native private boolean glGetBoolean(int pname);

	@HaxeMethodBody("return gl.getParameter(p0);")
	native private float glGetFloat(int pname);

	public void glGetIntegerv(int pname, IntBuffer params) {
		params.put(0, glGetInteger2(pname));
	}

	@HaxeMethodBody("return HaxeNatives.str(cast(gl.getParameter(p0), String));")
	native public String glGetString(int name);

	@HaxeMethodBody("gl.hint(p0, p1);")
	native public void glHint(int target, int mode);

	@HaxeMethodBody("gl.lineWidth(p0);")
	native public void glLineWidth(float width);

	@HaxeMethodBody("gl.pixelStorei(p0, p1);")
	native public void glPixelStorei(int pname, int param);

	@HaxeMethodBody("gl.polygonOffset(p0, p1);")
	native public void glPolygonOffset(float factor, float units);

	@HaxeMethodBody("gl.readPixels(p0, p1, p2, p3, p4, p5, _buffer(p6));")
	native public void glReadPixels(int x, int y, int width, int height, int format, int type, Buffer pixels);

	@HaxeMethodBody("gl.scissor(p0, p1, p2, p3);")
	native public void glScissor(int x, int y, int width, int height);

	@HaxeMethodBody("gl.stencilFunc(p0, p1, p2);")
	native public void glStencilFunc(int func, int ref, int mask);

	@HaxeMethodBody("gl.stencilMask(p0);")
	native public void glStencilMask(int mask);

	@HaxeMethodBody("gl.stencilOp(p0, p1, p2);")
	native public void glStencilOp(int fail, int zfail, int zpass);

	@HaxeMethodBody("gl.texImage2D(p0, p1, p2, p3, p4, p5, p6, p7, _buffer(p8));")
	native private void _glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, Buffer pixels);

	public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, Buffer pixels) {
		System.out.println("glTexImage2D:target=" + target + ",level=" + level + ",internalformat=" + internalformat + ",width=" + width + ",height=" + height + ",border=" + border + ",format=" + format + ",type=" + type + ",pixels=" + pixels.limit());
		_glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
		//_glTexImage2D(target, level, internalformat, 1, 1, border, format, type, ByteBuffer.wrap(new byte[] { -1, 0, 0, -1 }));
	}

	@HaxeMethodBody("gl.texParameterf(p0, p1, p2);")
	native private void _glTexParameterf(int target, int pname, float param);

	public void glTexParameterf(int target, int pname, float param) {
		switch (pname) {
			case GL_TEXTURE_MIN_FILTER:
			case GL_TEXTURE_MAG_FILTER:
			case GL_TEXTURE_WRAP_S:
			case GL_TEXTURE_WRAP_T:
				glTexParameteri(target, pname, (int) param);
				break;
			default:
				_glTexParameterf(target, pname, param);
				break;
		}
	}

	@HaxeMethodBody("gl.texSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, _buffer(p8));")
	native public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, Buffer pixels);

	@HaxeMethodBody("gl.viewport(p0, p1, p2, p3);")
	native private void _glViewport(int x, int y, int width, int height);

	public void glViewport(int x, int y, int width, int height) {
		//System.out.printf("glViewport(%d, %d, %d, %d)\n", x, y, width, height);
		_glViewport(x, y, width, height);
	}

	@HaxeMethodBody("gl.attachShader(programs.get(p0), shaders.get(p1));")
	native public void glAttachShader(int program, int shader);

	@HaxeMethodBody("gl.bindAttribLocation(programs.get(p0), p1, p2._str);")
	native public void glBindAttribLocation(int program, int index, String name);

	@HaxeMethodBody("gl.bindBuffer(p0, buffers.get(p1));")
	native public void glBindBuffer(int target, int buffer);


	@HaxeMethodBody("gl.bindRenderbuffer(p0, renderBuffers.get(p1));")
	native public void glBindRenderbuffer(int target, int renderbuffer);

	@HaxeMethodBody("gl.blendColor(p0, p1, p2, p3);")
	native public void glBlendColor(float red, float green, float blue, float alpha);

	@HaxeMethodBody("gl.blendEquation(p0);")
	native public void glBlendEquation(int mode);

	@HaxeMethodBody("gl.blendEquationSeparate(p0, p1);")
	native public void glBlendEquationSeparate(int modeRGB, int modeAlpha);

	@HaxeMethodBody("gl.blendFuncSeparate(p0, p1, p2, p3);")
	native public void glBlendFuncSeparate(int srcRGB, int dstRGB, int srcAlpha, int dstAlpha);

	@HaxeMethodBody("gl.bufferData(p0, _buffer(p2, p1), p3);")
	native public void glBufferData(int target, int size, Buffer data, int usage);

	@HaxeMethodBody("gl.bufferSubData(p0, p1, _buffer(p3, p2));")
	native public void glBufferSubData(int target, int offset, int size, Buffer data);

	@HaxeMethodBody("gl.compileShader(shaders.get(p0));")
	native public void glCompileShader(int shader);

	@HaxeMethodBody("var id = lastId++; var program = gl.createProgram(); programs.set(id, program); return id;")
	native public int glCreateProgram();

	@HaxeMethodBody("var id = lastId++; var shader = gl.createShader(p0); shaders.set(id, shader); return id;")
	native public int glCreateShader(int type);

	@HaxeMethodBody("gl.deleteBuffer(buffers.get(p0));")
	native public void glDeleteBuffer(int buffer);

	public void glDeleteBuffers(int n, IntBuffer buffers) {
		for (int i = 0; i < n; i++) glDeleteBuffer(buffers.get(i));
	}

	@HaxeMethodBody("gl.deleteProgram(programs.get(p0)); programs.remove(p0);")
	native public void glDeleteProgram(int program);

	@HaxeMethodBody("gl.deleteRenderbuffer(renderBuffers.get(p0)); renderBuffers.remove(p0);")
	native public void glDeleteRenderbuffer(int renderbuffer);

	public void glDeleteRenderbuffers(int n, IntBuffer renderbuffers) {
		for (int i = 0; i < n; i++) glDeleteRenderbuffer(renderbuffers.get(i));
	}

	@HaxeMethodBody("gl.deleteShader(shaders.get(p0)); shaders.remove(p0);")
	native public void glDeleteShader(int shader);

	@HaxeMethodBody("gl.detachShader(programs.get(p0), shaders.get(p1));")
	native public void glDetachShader(int program, int shader);

	@HaxeMethodBody("gl.disableVertexAttribArray(p0);")
	native public void glDisableVertexAttribArray(int index);

	@HaxeMethodBody("gl.drawElements(p0, p1, p2, p3);")
	native public void glDrawElements(int mode, int count, int type, int indices);

	@HaxeMethodBody("gl.enableVertexAttribArray(p0);")
	native public void glEnableVertexAttribArray(int index);

	///////////////////////////////////////////////////////
	// FrameBuffer
	///////////////////////////////////////////////////////
	@HaxeMethodBody("var id = lastId++; var frameBuffer = gl.createFramebuffer(); frameBuffers.set(id, frameBuffer); return id;")
	native public int glGenFramebuffer();

	@HaxeMethodBody("return gl.isFramebuffer(frameBuffers.get(p0));")
	native public boolean glIsFramebuffer(int framebuffer);


	public void glGenFramebuffers(int n, IntBuffer framebuffers) {
		for (int i = 0; i < n; i++) framebuffers.put(i, glGenFramebuffer());
	}

	@HaxeMethodBody("gl.bindFramebuffer(p0, frameBuffers.get(p1));")
	native public void glBindFramebuffer(int target, int framebuffer);

	@HaxeMethodBody("return gl.checkFramebufferStatus(p0);")
	native public int glCheckFramebufferStatus(int target);

	@HaxeMethodBody("gl.deleteFramebuffer(frameBuffers.get(p0)); frameBuffers.remove(p0);")
	native public void glDeleteFramebuffer(int framebuffer);

	public void glDeleteFramebuffers(int n, IntBuffer framebuffers) {
		for (int i = 0; i < n; i++) glDeleteFramebuffer(framebuffers.get(i));
	}

	@HaxeMethodBody("gl.framebufferRenderbuffer(p0, p1, p2, renderBuffers.get(p3));")
	native public void glFramebufferRenderbuffer(int target, int attachment, int renderbuffertarget, int renderbuffer);

	@HaxeMethodBody("gl.framebufferTexture2D(p0, p1, p2, textures.get(p3), p4);")
	native public void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level);

	@HaxeMethodBody("return gl.getFramebufferAttachmentParameter(p0, p1, p2);")
	native private int glGetFramebufferAttachmentParameter(int target, int attachment, int pname);

	public void glGetFramebufferAttachmentParameteriv(int target, int attachment, int pname, IntBuffer params) {
		params.put(0, glGetFramebufferAttachmentParameter(target, attachment, pname));
	}

	@HaxeMethodBody("var id = lastId++; var buffer = gl.createBuffer(); buffers.set(id, buffer); return id;")
	native public int glGenBuffer();

	public void glGenBuffers(int n, IntBuffer buffers) {
		for (int i = 0; i < n; i++) buffers.put(i, glGenBuffer());
	}

	@HaxeMethodBody("gl.generateMipmap(p0);")
	native public void glGenerateMipmap(int target);

	@HaxeMethodBody("var id = lastId++; var renderBuffer = gl.createRenderbuffer(); renderBuffers.set(id, renderBuffer); return id;")
	native public int glGenRenderbuffer();

	public void glGenRenderbuffers(int n, IntBuffer renderbuffers) {
		for (int i = 0; i < n; i++) renderbuffers.put(i, glGenRenderbuffer());
	}

	@HaxeMethodBody("return HaxeNatives.str(gl.getActiveAttrib(programs.get(p0), p1).name);")
	native private String glGetActiveAttribName(int program, int index);

	@HaxeMethodBody("return gl.getActiveAttrib(programs.get(p0), p1).size;")
	native private int glGetActiveAttribSize(int program, int index);

	@HaxeMethodBody("return gl.getActiveAttrib(programs.get(p0), p1).type;")
	native private int glGetActiveAttribType(int program, int index);

	@HaxeMethodBody("return HaxeNatives.str(gl.getActiveUniform(programs.get(p0), p1).name);")
	native private String glGetActiveUniformName(int program, int index);

	@HaxeMethodBody("return gl.getActiveUniform(programs.get(p0), p1).size;")
	native private int glGetActiveUniformSize(int program, int index);

	@HaxeMethodBody("return gl.getActiveUniform(programs.get(p0), p1).type;")
	native private int glGetActiveUniformType(int program, int index);

	public String glGetActiveAttrib(int program, int index, IntBuffer size, Buffer type) {
		size.put(glGetActiveAttribSize(program, index));
		if (type instanceof IntBuffer) ((IntBuffer)type).put(glGetActiveAttribType(program, index));
		return glGetActiveAttribName(program, index);
	}

	public String glGetActiveUniform(int program, int index, IntBuffer size, Buffer type) {
		size.put(glGetActiveUniformSize(program, index));
		if (type instanceof IntBuffer) ((IntBuffer)type).put(glGetActiveUniformType(program, index));
		return glGetActiveUniformName(program, index);
	}

	//@HaxeMethodBody("gl.getAttachedShaders(programs.get(p0), p1, _buffer(p2), p3);")
	native public void glGetAttachedShaders(int program, int maxcount, Buffer count, IntBuffer shaders);

	@HaxeMethodBody("return gl.getAttribLocation(programs.get(p0), p1._str);")
	native private int _glGetAttribLocation(int program, String name);

	public int glGetAttribLocation(int program, String name) {
		//JTranscSystem.debugger();
		return _glGetAttribLocation(program, name);
	}

	public void glGetBooleanv(int pname, Buffer params) {
		((ByteBuffer) params).putInt(0, glGetBoolean(pname) ? 1 : 0);
	}

	// 	public static inline function getBufferParameter (target:Int, pname:Int):Int /*Dynamic*/ {

	@HaxeMethodBody("return gl.getBufferParameter(p0, p1);")
	native private int glGetBufferParameter(int target, int pname);

	public void glGetBufferParameteriv(int target, int pname, IntBuffer params) {
		params.put(0, glGetBufferParameter(target, pname));
	}

	public void glGetFloatv(int pname, FloatBuffer params) {
		params.put(0, glGetFloat(pname));
	}

	@HaxeMethodBody("return gl.getProgramParameter(programs.get(p0), p1);")
	native private int glGetProgrami(int program, int pname);

	public void glGetProgramiv(int program, int pname, IntBuffer params) {
		params.put(0, glGetProgrami(program, pname));
	}

	@HaxeMethodBody("return HaxeNatives.str(gl.getProgramInfoLog(programs.get(p0)));")
	native public String glGetProgramInfoLog(int program);

	@HaxeMethodBody("return gl.getRenderbufferParameter(p0, p1);")
	native private int glGetRenderbufferParameter(int target, int pname);

	public void glGetRenderbufferParameteriv(int target, int pname, IntBuffer params) {
		params.put(0, glGetRenderbufferParameter(target, pname));
	}

	@HaxeMethodBody("return gl.getShaderParameter(shaders.get(p0), p1);")
	native private int glGetShader(int shader, int pname);

	public void glGetShaderiv(int shader, int pname, IntBuffer params) {
		params.put(0, glGetShader(shader, pname));
	}

	@HaxeMethodBody("return HaxeNatives.str(gl.getShaderInfoLog(shaders.get(p0)));")
	native public String glGetShaderInfoLog(int shader);

	@HaxeMethodBody("return gl.getShaderPrecisionFormat(p0, p1).rangeMin;")
	native public int glGetShaderPrecisionFormatMin(int shadertype, int precisiontype);

	@HaxeMethodBody("return gl.getShaderPrecisionFormat(p0, p1).rangeMax;")
	native public int glGetShaderPrecisionFormatMax(int shadertype, int precisiontype);

	@HaxeMethodBody("return gl.getShaderPrecisionFormat(p0, p1).precision;")
	native public int glGetShaderPrecisionFormatPrecision(int shadertype, int precisiontype);

	public void glGetShaderPrecisionFormat(int shadertype, int precisiontype, IntBuffer range, IntBuffer precision) {
		range.put(0, glGetShaderPrecisionFormatMin(shadertype, precisiontype));
		range.put(1, glGetShaderPrecisionFormatMax(shadertype, precisiontype));
		precision.put(0, glGetShaderPrecisionFormatPrecision(shadertype, precisiontype));
	}

	//@HaxeMethodBody("gl.getTexParameter(p0, p1, p2);")
	native public void glGetTexParameterfv(int target, int pname, FloatBuffer params);

	//@HaxeMethodBody("gl.getTexParameter(p0, p1, p2);")
	native public void glGetTexParameteriv(int target, int pname, IntBuffer params);

	//@HaxeMethodBody("gl.getUniformfv(p0, p1, p2);")
	native public void glGetUniformfv(int program, int location, FloatBuffer params);

	//@HaxeMethodBody("gl.getUniformiv(programs.get(p0), p1, p2);")
	native public void glGetUniformiv(int program, int location, IntBuffer params);

	@HaxeMethodBody("var id = lastId++; var location = gl.getUniformLocation(programs.get(p0), p1._str); uniformLocations.set(id, location); return id;")
	native public int glGetUniformLocation(int program, String name);

	@HaxeMethodBody("return gl.getVertexAttrib(p0, p1);")
	native private int glGetVertexAttrib(int index, int pname);

	public void glGetVertexAttribfv(int index, int pname, FloatBuffer params) {
		params.put(0, glGetVertexAttrib(index, pname));
	}

	public void glGetVertexAttribiv(int index, int pname, IntBuffer params) {
		params.put(0, glGetVertexAttrib(index, pname));
	}

	//@HaxeMethodBody("gl.getVertexAttrib(p0, p1, p2);")
	native public void glGetVertexAttribPointerv(int index, int pname, Buffer pointer);

	@HaxeMethodBody("return gl.isBuffer(buffers.get(p0));")
	native public boolean glIsBuffer(int buffer);

	@HaxeMethodBody("return gl.isEnabled(p0);")
	native public boolean glIsEnabled(int cap);

	@HaxeMethodBody("return gl.isProgram(programs.get(p0));")
	native public boolean glIsProgram(int program);

	@HaxeMethodBody("return gl.isRenderbuffer(renderBuffers.get(p0));")
	native public boolean glIsRenderbuffer(int renderbuffer);

	@HaxeMethodBody("return gl.isShader(shaders.get(p0));")
	native public boolean glIsShader(int shader);

	@HaxeMethodBody("return gl.isTexture(textures.get(p0));")
	native public boolean glIsTexture(int texture);

	@HaxeMethodBody("gl.linkProgram(programs.get(p0));")
	native public void glLinkProgram(int program);

	public void glReleaseShaderCompiler() {
	}

	@HaxeMethodBody("gl.renderbufferStorage(p0, p1, p2, p3);")
	native public void glRenderbufferStorage(int target, int internalformat, int width, int height);

	@HaxeMethodBody("gl.sampleCoverage(p0, p1);")
	native public void glSampleCoverage(float value, boolean invert);

	//@HaxeMethodBody("gl.shaderBinary(p0, p1, p2, p3, p4);")
	public void glShaderBinary(int n, IntBuffer shaders, int binaryformat, Buffer binary, int length) {
		throw new RuntimeException("Not supported glShaderBinary");
	}

	@HaxeMethodBody("gl.shaderSource(shaders.get(p0), p1._str);")
	native public void glShaderSource(int shader, String string);

	@HaxeMethodBody("gl.stencilFuncSeparate(p0, p1, p2, p3);")
	native public void glStencilFuncSeparate(int face, int func, int ref, int mask);

	@HaxeMethodBody("gl.stencilMaskSeparate(p0, p1);")
	native public void glStencilMaskSeparate(int face, int mask);

	@HaxeMethodBody("gl.stencilOpSeparate(p0, p1, p2, p3);")
	native public void glStencilOpSeparate(int face, int fail, int zfail, int zpass);

	public void glTexParameterfv(int target, int pname, FloatBuffer params) {
		glTexParameterf(target, pname, params.get(0));
	}

	@HaxeMethodBody("gl.texParameteri(p0, p1, p2);")
	native public void glTexParameteri(int target, int pname, int param);

	public void glTexParameteriv(int target, int pname, IntBuffer params) {
		glTexParameteri(target, pname, params.get(0));
	}

	@HaxeMethodBody("gl.useProgram(programs.get(p0));")
	native public void glUseProgram(int program);

	@HaxeMethodBody("gl.validateProgram(programs.get(p0));")
	native public void glValidateProgram(int program);

	@HaxeMethodBody("gl.uniform1f(uniformLocations.get(p0), p1);")
	native public void glUniform1f(int location, float x);

	@HaxeMethodBody("gl.uniform1fv(uniformLocations.get(p0), _floatBuffer(p2, p1));")
	native public void glUniform1fv(int location, int count, FloatBuffer v);

	@HaxeMethodBody("gl.uniform1fv(uniformLocations.get(p0), _floatArray(p2, p3, p1));")
	native public void glUniform1fv(int location, int count, float[] v, int offset);

	@HaxeMethodBody("gl.uniform1i(uniformLocations.get(p0), p1);")
	native public void glUniform1i(int location, int x);

	@HaxeMethodBody("gl.uniform1iv(uniformLocations.get(p0), _intBuffer(p2, p1));")
	native public void glUniform1iv(int location, int count, IntBuffer v);

	@HaxeMethodBody("gl.uniform1iv(uniformLocations.get(p0), _intArray(p2, p3, p1));")
	native public void glUniform1iv(int location, int count, int[] v, int offset);

	@HaxeMethodBody("gl.uniform2f(uniformLocations.get(p0), p1, p2);")
	native public void glUniform2f(int location, float x, float y);

	@HaxeMethodBody("gl.uniform2fv(uniformLocations.get(p0), _floatBuffer(p2, p1));")
	native public void glUniform2fv(int location, int count, FloatBuffer v);

	@HaxeMethodBody("gl.uniform2fv(uniformLocations.get(p0), _floatArray(p2, p3, p1));")
	native public void glUniform2fv(int location, int count, float[] v, int offset);

	@HaxeMethodBody("gl.uniform2i(uniformLocations.get(p0), p1, p2);")
	native public void glUniform2i(int location, int x, int y);

	@HaxeMethodBody("gl.uniform2iv(uniformLocations.get(p0), _intBuffer(p2, p1));")
	native public void glUniform2iv(int location, int count, IntBuffer v);

	@HaxeMethodBody("gl.uniform2iv(uniformLocations.get(p0), _intArray(p2, p3, p1));")
	native public void glUniform2iv(int location, int count, int[] v, int offset);

	@HaxeMethodBody("gl.uniform3f(uniformLocations.get(p0), p1, p2, p3);")
	native public void glUniform3f(int location, float x, float y, float z);

	@HaxeMethodBody("gl.uniform3fv(uniformLocations.get(p0), _floatBuffer(p2, p1));")
	native public void glUniform3fv(int location, int count, FloatBuffer v);

	@HaxeMethodBody("gl.uniform3fv(uniformLocations.get(p0), _floatArray(p2, p3, p1));")
	native public void glUniform3fv(int location, int count, float[] v, int offset);

	@HaxeMethodBody("gl.uniform3i(uniformLocations.get(p0), p1, p2, p3);")
	native public void glUniform3i(int location, int x, int y, int z);

	@HaxeMethodBody("gl.uniform3iv(uniformLocations.get(p0), _intBuffer(p2, p1));")
	native public void glUniform3iv(int location, int count, IntBuffer v);

	@HaxeMethodBody("gl.uniform3iv(uniformLocations.get(p0), _intArray(p2, p3, p1));")
	native public void glUniform3iv(int location, int count, int[] v, int offset);

	@HaxeMethodBody("gl.uniform4f(uniformLocations.get(p0), p1, p2, p3, p4);")
	native public void glUniform4f(int location, float x, float y, float z, float w);

	@HaxeMethodBody("gl.uniform4fv(uniformLocations.get(p0), _floatBuffer(p2, p1));")
	native public void glUniform4fv(int location, int count, FloatBuffer v);

	public void glUniform4fv(int location, int count, float[] v, int offset) {
		glUniform4fv(location, count, FloatBuffer.wrap(v, offset, count));
	}

	@HaxeMethodBody("gl.uniform4i(uniformLocations.get(p0), p1, p2, p3, p4);")
	native public void glUniform4i(int location, int x, int y, int z, int w);

	@HaxeMethodBody("gl.uniform4iv(uniformLocations.get(p0), _intBuffer(p2, p1));")
	native public void glUniform4iv(int location, int count, IntBuffer v);

	public void glUniform4iv(int location, int count, int[] v, int offset) {
		glUniform4iv(location, count, IntBuffer.wrap(v, offset, count));
	}

	@HaxeMethodBody("gl.uniformMatrix2fv(uniformLocations.get(p0), p2, _floatBuffer(p3, p1));")
	native public void glUniformMatrix2fv(int location, int count, boolean transpose, FloatBuffer value);

	public void glUniformMatrix2fv(int location, int count, boolean transpose, float[] value, int offset) {
		glUniformMatrix2fv(location, count, transpose, FloatBuffer.wrap(value, offset, value.length - offset));
	}

	@HaxeMethodBody("gl.uniformMatrix3fv(uniformLocations.get(p0), p2, _floatBuffer(p3, p1));")
	native public void glUniformMatrix3fv(int location, int count, boolean transpose, FloatBuffer value);

	@HaxeMethodBody("gl.uniformMatrix3fv(uniformLocations.get(p0), p2, _floatArray(p3, p4, p1));")
	native public void glUniformMatrix3fv(int location, int count, boolean transpose, float[] value, int offset);

	@HaxeMethodBody("gl.uniformMatrix4fv(uniformLocations.get(p0), p2, _floatBuffer(p3, p1));")
	native public void glUniformMatrix4fv(int location, int count, boolean transpose, FloatBuffer value);

	@HaxeMethodBody("gl.uniformMatrix4fv(uniformLocations.get(p0), p2, _floatArray(p3, p4, p1));")
	native public void glUniformMatrix4fv(int location, int count, boolean transpose, float[] value, int offset);

	@HaxeMethodBody("gl.vertexAttrib1f(p0, p1);")
	native public void glVertexAttrib1f(int indx, float x);

	@HaxeMethodBody("gl.vertexAttrib1fv(p0, _floatBuffer(p1));")
	native public void glVertexAttrib1fv(int indx, FloatBuffer values);

	@HaxeMethodBody("gl.vertexAttrib2f(p0, p1, p2);")
	native public void glVertexAttrib2f(int indx, float x, float y);

	@HaxeMethodBody("gl.vertexAttrib2fv(p0, _floatBuffer(p1));")
	native public void glVertexAttrib2fv(int indx, FloatBuffer values);

	@HaxeMethodBody("gl.vertexAttrib3f(p0, p1, p2, p3);")
	native public void glVertexAttrib3f(int indx, float x, float y, float z);

	@HaxeMethodBody("gl.vertexAttrib3fv(p0, _floatBuffer(p1));")
	native public void glVertexAttrib3fv(int indx, FloatBuffer values);

	@HaxeMethodBody("gl.vertexAttrib4f(p0, p1, p2, p3, p4);")
	native public void glVertexAttrib4f(int indx, float x, float y, float z, float w);

	@HaxeMethodBody("gl.vertexAttrib4fv(p0, _floatBuffer(p1));")
	native public void glVertexAttrib4fv(int indx, FloatBuffer values);

	//@HaxeMethodBody("gl.vertexAttribPointer(p0, p1, p2, p3, p4, _buffer(p5));")
	native public void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, Buffer ptr);

	// 	public static inline function vertexAttribPointer (indx:Int, size:Int, type:Int, normalized:Bool, stride:Int, offset:Int):Void {
	@HaxeMethodBody("gl.vertexAttribPointer(p0, p1, p2, p3, p4, p5);")
	native public void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, int ptr);
}
