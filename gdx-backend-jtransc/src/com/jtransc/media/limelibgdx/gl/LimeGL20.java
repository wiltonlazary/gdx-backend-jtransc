package com.jtransc.media.limelibgdx.gl;

import com.badlogic.gdx.graphics.GL20;
import com.jtransc.annotation.haxe.HaxeImports;
import com.jtransc.ds.FastIntMap;
import com.jtransc.media.limelibgdx.dummy.DummyGL20;

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
public class LimeGL20 extends DummyGL20 implements GL20 {
	private GL gl;
	private int lastId = 1000;
	static public int bindedTextureId = 0;

	private GL.BaseIntMap buffers = GL.BaseIntMap.Utils.create();
	private GL.BaseIntMap programs = GL.BaseIntMap.Utils.create();
	private GL.BaseIntMap shaders = GL.BaseIntMap.Utils.create();
	private GL.BaseIntMap textures = GL.BaseIntMap.Utils.create();

	private GL.BaseIntMap frameBuffers = GL.BaseIntMap.Utils.create();
	private GL.BaseIntMap renderBuffers = GL.BaseIntMap.Utils.create();
	private GL.BaseIntMap uniformLocations = GL.BaseIntMap.Utils.create();

	public LimeGL20() {
		this.gl = GL.HaxeLimeGdxApplication.gl;
	}

	static private GL.ArrayBufferView _buffer(Buffer buffer, int size) {
		return GL.HaxeLimeGdxApplication.convertBuffer(buffer, size);
	}

	static private GL.Float32Array _floatBuffer(FloatBuffer buffer, int size) {
		return GL.HaxeLimeGdxApplication.convertFloatBuffer(buffer, size);
	}

	static private GL.Int32Array _intBuffer(IntBuffer buffer, int size) {
		return GL.HaxeLimeGdxApplication.convertIntBuffer(buffer, size);
	}

	static private GL.ArrayBufferView _buffer(Buffer buffer) {
		return _buffer(buffer, -1);
	}

	static private GL.Float32Array _floatBuffer(FloatBuffer buffer) {
		return _floatBuffer(buffer, -1);
	}

	static private GL.Int32Array _intBuffer(IntBuffer buffer) {
		return _intBuffer(buffer, -1);
	}

	static private GL.Float32Array _floatArray(float[] buffer, int offset, int size) {
		return GL.HaxeLimeGdxApplication.convertFloatArray(buffer, offset, size);
	}

	static private GL.Int32Array _intArray(int[] buffer, int offset, int size) {
		return GL.HaxeLimeGdxApplication.convertIntArray(buffer, offset, size);
	}


	public void glActiveTexture(int texture) {
		gl.activeTexture(texture);
	}

	public void glBindTexture(int target, int texture) {
		bindedTextureId = texture;
		gl.bindTexture(target, textures.getTexture(texture));
	}

	public void glBlendFunc(int sfactor, int dfactor) {
		gl.blendFunc(sfactor, dfactor);
	}

	public void glClear(int mask) {
		gl.clear(mask);
	}

	public void glClearColor(float red, float green, float blue, float alpha) {
		gl.clearColor(red, green, blue, alpha);
	}

	public void glClearDepthf(float depth) {
		gl.clearDepth(depth);
	}

	public void glClearStencil(int s) {
		gl.clearStencil(s);
	}

	public void glColorMask(boolean red, boolean green, boolean blue, boolean alpha) {
		gl.colorMask(red, green, blue, alpha);
	}

	public void glCompressedTexImage2D(int target, int level, int internalformat, int width, int height, int border, int imageSize, Buffer data) {
		gl.compressedTexImage2D(target, level, internalformat, width, height, border, _buffer(data, imageSize));
	}

	public void glCompressedTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int imageSize, Buffer data) {
		gl.compressedTexSubImage2D(target, level, xoffset, yoffset, width, height, format, _buffer(data, imageSize));
	}

	public void glCopyTexImage2D(int target, int level, int internalformat, int x, int y, int width, int height, int border) {
		gl.copyTexImage2D(target, level, internalformat, x, y, width, height, border);
	}

	public void glCopyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y, int width, int height) {
		gl.copyTexSubImage2D(target, level, xoffset, yoffset, x, y, width, height);
	}

	public void glCullFace(int mode) {
		gl.cullFace(mode);
	}

	public void glDeleteTextures(int n, IntBuffer textures) {
		for (int i = 0; i < n; i++) glDeleteTexture(textures.get(i));
	}

	public void glDeleteTexture(int texture) {
		gl.deleteTexture(textures.getTexture(texture));
		textures.remove(texture);
	}

	public void glDepthFunc(int func) {
		gl.depthFunc(func);
	}

	public void glDepthMask(boolean flag) {
		gl.depthMask(flag);
	}

	public void glDepthRangef(float zNear, float zFar) {
		gl.depthRange(zNear, zFar);
	}

	public void glDisable(int cap) {
		gl.disable(cap);
	}

	public void glDrawArrays(int mode, int first, int count) {
		gl.drawArrays(mode, first, count);
	}

	public void glDrawElements(int mode, int count, int type, Buffer indices) {
		//gl.drawElements(mode, count, type, _buffer(indices));
		System.out.println("Unchecked LimeGL20.glDrawElements");
		gl.drawElements(mode, count, type, indices.position());
	}

	public void glEnable(int cap) {
		gl.enable(cap);
	}

	public void glFinish() {
		gl.finish();
	}

	public void glFlush() {
		gl.flush();
	}

	public void glFrontFace(int mode) {
		gl.frontFace(mode);
	}

	public void glGenTextures(int n, IntBuffer textures) {
		for (int i = 0; i < n; i++) textures.put(i, glGenTexture());
	}

	public int glGenTexture() {
		int id = lastId++;
		textures.setTexture(id, gl.createTexture());
		return id;
	}

	public int glGetError() {
		return gl.getError();
	}

	private int glGetInteger(int pname) {
		return GL.Dynamic.Utils.toInt(gl.getParameter(pname));
	}

	private int glGetInteger2(int pname) {
		switch (pname) {
			case GL20.GL_TEXTURE_BINDING_2D:
				return bindedTextureId;
		}
		return glGetInteger(pname);
	}

	private boolean glGetBoolean(int pname) {
		return GL.Dynamic.Utils.toBool(gl.getParameter(pname));
	}

	private float glGetFloat(int pname) {
		return GL.Dynamic.Utils.toFloat(gl.getParameter(pname));
	}

	public void glGetIntegerv(int pname, IntBuffer params) {
		params.put(0, glGetInteger2(pname));
	}

	public String glGetString(int name) {
		return GL.Dynamic.Utils.toString(gl.getParameter(name));
	}

	public void glHint(int target, int mode) {
		gl.hint(target, mode);
	}

	public void glLineWidth(float width) {
		gl.lineWidth(width);
	}

	public void glPixelStorei(int pname, int param) {
		gl.pixelStorei(pname, param);
	}

	public void glPolygonOffset(float factor, float units) {
		gl.polygonOffset(factor, units);
	}

	public void glReadPixels(int x, int y, int width, int height, int format, int type, Buffer pixels) {
		gl.readPixels(x, y, width, height, format, type, _buffer(pixels));
	}

	public void glScissor(int x, int y, int width, int height) {
		gl.scissor(x, y, width, height);
	}

	public void glStencilFunc(int func, int ref, int mask) {
		gl.stencilFunc(func, ref, mask);
	}

	public void glStencilMask(int mask) {
		gl.stencilMask(mask);
	}

	public void glStencilOp(int fail, int zfail, int zpass) {
		gl.stencilOp(fail, zfail, zpass);
	}

	public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, Buffer pixels) {
		gl.texImage2D(target, level, internalformat, width, height, border, format, type, _buffer(pixels));
	}

	private void _glTexParameterf(int target, int pname, float param) {
		gl.texParameterf(target, pname, param);
	}

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

	public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, Buffer pixels) {
		gl.texSubImage2D(target, level, xoffset, yoffset, width, height, format, type, _buffer(pixels));
	}

	public void glViewport(int x, int y, int width, int height) {
		gl.viewport(x, y, width, height);
	}

	public void glAttachShader(int program, int shader) {
		gl.attachShader(programs.getProgram(program), shaders.getShader(shader));
	}

	public void glBindAttribLocation(int program, int index, String name) {
		gl.bindAttribLocation(programs.getProgram(program), index, name);
	}

	public void glBindBuffer(int target, int buffer) {
		gl.bindBuffer(target, buffers.getBuffer(buffer));
	}


	public void glBindRenderbuffer(int target, int renderbuffer) {
		gl.bindRenderbuffer(target, renderBuffers.getRenderbuffer(renderbuffer));
	}

	public void glBlendColor(float red, float green, float blue, float alpha) {
		gl.blendColor(red, green, blue, alpha);
	}

	public void glBlendEquation(int mode) {
		gl.blendEquation(mode);
	}

	public void glBlendEquationSeparate(int modeRGB, int modeAlpha) {
		gl.blendEquationSeparate(modeRGB, modeAlpha);
	}

	public void glBlendFuncSeparate(int srcRGB, int dstRGB, int srcAlpha, int dstAlpha) {
		gl.blendFuncSeparate(srcRGB, dstRGB, srcAlpha, dstAlpha);
	}

	public void glBufferData(int target, int size, Buffer data, int usage) {
		gl.bufferData(target, _buffer(data, size), usage);
	}

	public void glBufferSubData(int target, int offset, int size, Buffer data) {
		gl.bufferSubData(target, offset, _buffer(data, size));
	}

	public void glCompileShader(int shader) {
		gl.compileShader(shaders.getShader(shader));
	}

	public int glCreateProgram() {
		int id = lastId++;
		programs.setProgram(id, gl.createProgram());
		return id;
	}

	public int glCreateShader(int type) {
		int id = lastId++;
		shaders.setShader(id, gl.createShader(type));
		return id;
	}

	public void glDeleteBuffer(int buffer) {
		gl.deleteBuffer(buffers.getBuffer(buffer));
		buffers.remove(buffer);
	}

	public void glDeleteBuffers(int n, IntBuffer buffers) {
		for (int i = 0; i < n; i++) glDeleteBuffer(buffers.get(i));
	}

	public void glDeleteProgram(int program) {
		gl.deleteProgram(programs.getProgram(program));
		programs.remove(program);
	}

	public void glDeleteRenderbuffer(int renderbuffer) {
		gl.deleteRenderbuffer(renderBuffers.getRenderbuffer(renderbuffer));
		renderBuffers.remove(renderbuffer);
	}

	public void glDeleteRenderbuffers(int n, IntBuffer renderbuffers) {
		for (int i = 0; i < n; i++) glDeleteRenderbuffer(renderbuffers.get(i));
	}

	public void glDeleteShader(int shader) {
		gl.deleteShader(shaders.getShader(shader));
		shaders.remove(shader);
	}

	public void glDetachShader(int program, int shader) {
		gl.detachShader(programs.getProgram(program), shaders.getShader(shader));
	}

	public void glDisableVertexAttribArray(int index) {
		gl.disableVertexAttribArray(index);
	}

	public void glDrawElements(int mode, int count, int type, int indices) {
		gl.drawElements(mode, count, type, indices);
	}

	public void glEnableVertexAttribArray(int index) {
		gl.enableVertexAttribArray(index);
	}

	///////////////////////////////////////////////////////
	// FrameBuffer
	///////////////////////////////////////////////////////
	public int glGenFramebuffer() {
		int id = lastId++;
		frameBuffers.set(id, gl.createFramebuffer());
		return id;
	}

	public boolean glIsFramebuffer(int framebuffer) {
		return frameBuffers.exists(framebuffer);
	}


	public void glGenFramebuffers(int n, IntBuffer framebuffers) {
		for (int i = 0; i < n; i++) framebuffers.put(i, glGenFramebuffer());
	}

	public void glBindFramebuffer(int target, int framebuffer) {
		gl.bindFramebuffer(target, frameBuffers.getFramebuffer(framebuffer));
	}

	public int glCheckFramebufferStatus(int target) {
		return gl.checkFramebufferStatus(target);
	}

	public void glDeleteFramebuffer(int framebuffer) {
		gl.deleteFramebuffer(frameBuffers.getFramebuffer(framebuffer));
		frameBuffers.remove(framebuffer);
	}

	public void glDeleteFramebuffers(int n, IntBuffer framebuffers) {
		for (int i = 0; i < n; i++) glDeleteFramebuffer(framebuffers.get(i));
	}

	public void glFramebufferRenderbuffer(int target, int attachment, int renderbuffertarget, int renderbuffer) {
		gl.framebufferRenderbuffer(target, attachment, renderbuffertarget, renderBuffers.getRenderbuffer(renderbuffer));
	}

	public void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level) {
		gl.framebufferTexture2D(target, attachment, textarget, textures.getTexture(texture), level);
	}

	private int glGetFramebufferAttachmentParameter(int target, int attachment, int pname) {
		return gl.getFramebufferAttachmentParameter(target, attachment, pname);
	}

	public void glGetFramebufferAttachmentParameteriv(int target, int attachment, int pname, IntBuffer params) {
		params.put(0, glGetFramebufferAttachmentParameter(target, attachment, pname));
	}

	public int glGenBuffer() {
		int id = lastId++;
		buffers.setBuffer(id, gl.createBuffer());
		return id;
	}

	public void glGenBuffers(int n, IntBuffer buffers) {
		for (int i = 0; i < n; i++) buffers.put(i, glGenBuffer());
	}

	public void glGenerateMipmap(int target) {
		gl.generateMipmap(target);
	}

	public int glGenRenderbuffer() {
		int id = lastId++;
		renderBuffers.setRenderbuffer(id, gl.createRenderbuffer());
		return id;
	}

	public void glGenRenderbuffers(int n, IntBuffer renderbuffers) {
		for (int i = 0; i < n; i++) renderbuffers.put(i, glGenRenderbuffer());
	}

	private GL.GLActiveInfo _glGetActiveAttrib(Wrapped<GL.GLProgram> program, int index) {
		return gl.getActiveAttrib(program, index);
	}

	private String glGetActiveAttribName(Wrapped<GL.GLProgram> program, int index) {
		return GL.HaxeString.Utils.toString(_glGetActiveAttrib(program, index).name);
	}

	private int glGetActiveAttribSize(Wrapped<GL.GLProgram> program, int index) {
		return _glGetActiveAttrib(program, index).size;
	}

	private int glGetActiveAttribType(Wrapped<GL.GLProgram> program, int index) {
		return _glGetActiveAttrib(program, index).type;
	}

	private GL.GLActiveInfo _glGetActiveUniform(Wrapped<GL.GLProgram> program, int index) {
		return gl.getActiveUniform(program, index);
	}

	private String glGetActiveUniformName(Wrapped<GL.GLProgram> program, int index) {
		return GL.HaxeString.Utils.toString(_glGetActiveUniform(program, index).name);
	}

	private int glGetActiveUniformSize(Wrapped<GL.GLProgram> program, int index) {
		return _glGetActiveUniform(program, index).size;
	}

	private int glGetActiveUniformType(Wrapped<GL.GLProgram> program, int index) {
		return _glGetActiveUniform(program, index).type;
	}

	public String glGetActiveAttrib(int program, int index, IntBuffer size, Buffer type) {
		Wrapped<GL.GLProgram> _program = programs.getProgram(program);
		size.put(glGetActiveAttribSize(_program, index));
		if (type instanceof IntBuffer) ((IntBuffer) type).put(glGetActiveAttribType(_program, index));
		return glGetActiveAttribName(_program, index);
	}

	public String glGetActiveUniform(int program, int index, IntBuffer size, Buffer type) {
		Wrapped<GL.GLProgram> _program = programs.getProgram(program);
		size.put(glGetActiveUniformSize(_program, index));
		if (type instanceof IntBuffer) ((IntBuffer) type).put(glGetActiveUniformType(_program, index));
		return glGetActiveUniformName(_program, index);
	}

	native public void glGetAttachedShaders(int program, int maxcount, Buffer count, IntBuffer shaders);

	public int glGetAttribLocation(int program, String name) {
		//JTranscSystem.debugger();
		return gl.getAttribLocation(programs.getProgram(program), name);
	}

	public void glGetBooleanv(int pname, Buffer params) {
		((ByteBuffer) params).putInt(0, glGetBoolean(pname) ? 1 : 0);
	}

	// 	public static inline function getBufferParameter (target:Int, pname:Int):Int /*Dynamic*/ {

	private int glGetBufferParameter(int target, int pname) {
		return gl.getBufferParameter(target, pname);
	}

	public void glGetBufferParameteriv(int target, int pname, IntBuffer params) {
		params.put(0, glGetBufferParameter(target, pname));
	}

	public void glGetFloatv(int pname, FloatBuffer params) {
		params.put(0, glGetFloat(pname));
	}

	private int glGetProgrami(int program, int pname) {
		return gl.getProgramParameter(programs.getProgram(program), pname);
	}

	public void glGetProgramiv(int program, int pname, IntBuffer params) {
		params.put(0, glGetProgrami(program, pname));
	}

	public String glGetProgramInfoLog(int program) {
		return gl.getProgramInfoLog(programs.getProgram(program));
	}

	private int glGetRenderbufferParameter(int target, int pname) {
		return gl.getRenderbufferParameter(target, pname);
	}

	public void glGetRenderbufferParameteriv(int target, int pname, IntBuffer params) {
		params.put(0, glGetRenderbufferParameter(target, pname));
	}

	private int glGetShader(int shader, int pname) {
		return gl.getShaderParameter(shaders.getShader(shader), pname);
	}

	public void glGetShaderiv(int shader, int pname, IntBuffer params) {
		params.put(0, glGetShader(shader, pname));
	}

	public String glGetShaderInfoLog(int shader) {
		return gl.getShaderInfoLog(shaders.getShader(shader));
	}

	private GL.GLShaderPrecisionFormat _glGetShaderPrecisionFormat(int shadertype, int precisiontype) {
		return gl.getShaderPrecisionFormat(shadertype, precisiontype);
	}

	public int glGetShaderPrecisionFormatMin(int shadertype, int precisiontype) {
		return _glGetShaderPrecisionFormat(shadertype, precisiontype).rangeMin;
	}

	public int glGetShaderPrecisionFormatMax(int shadertype, int precisiontype) {
		return _glGetShaderPrecisionFormat(shadertype, precisiontype).rangeMax;
	}

	public int glGetShaderPrecisionFormatPrecision(int shadertype, int precisiontype) {
		return _glGetShaderPrecisionFormat(shadertype, precisiontype).precision;
	}

	public void glGetShaderPrecisionFormat(int shadertype, int precisiontype, IntBuffer range, IntBuffer precision) {
		range.put(0, glGetShaderPrecisionFormatMin(shadertype, precisiontype));
		range.put(1, glGetShaderPrecisionFormatMax(shadertype, precisiontype));
		precision.put(0, glGetShaderPrecisionFormatPrecision(shadertype, precisiontype));
	}

	native public void glGetTexParameterfv(int target, int pname, FloatBuffer params);

	native public void glGetTexParameteriv(int target, int pname, IntBuffer params);

	native public void glGetUniformfv(int program, int location, FloatBuffer params);

	native public void glGetUniformiv(int program, int location, IntBuffer params);

	public int glGetUniformLocation(int program, String name) {
		int id = lastId++;
		uniformLocations.setUniformlocation(id, gl.getUniformLocation(programs.getProgram(program), name));
		return id;
	}

	private int glGetVertexAttrib(int index, int pname) {
		return gl.getVertexAttrib(index, pname);
	}

	public void glGetVertexAttribfv(int index, int pname, FloatBuffer params) {
		params.put(0, glGetVertexAttrib(index, pname));
	}

	public void glGetVertexAttribiv(int index, int pname, IntBuffer params) {
		params.put(0, glGetVertexAttrib(index, pname));
	}

	native public void glGetVertexAttribPointerv(int index, int pname, Buffer pointer);

	public boolean glIsBuffer(int buffer) {
		//return gl.isBuffer(buffers.get(buffer));
		return buffers.exists(buffer);
	}

	public boolean glIsEnabled(int cap) {
		return gl.isEnabled(cap);
	}

	public boolean glIsProgram(int program) {
		return gl.isProgram(programs.getProgram(program));
	}

	public boolean glIsRenderbuffer(int renderbuffer) {
		return gl.isRenderbuffer(renderBuffers.getRenderbuffer(renderbuffer));
	}

	public boolean glIsShader(int shader) {
		return gl.isShader(shaders.getShader(shader));
	}

	public boolean glIsTexture(int texture) {
		return gl.isTexture(textures.getTexture(texture));
	}

	public void glLinkProgram(int program) {
		gl.linkProgram(programs.getProgram(program));
	}

	public void glReleaseShaderCompiler() {
	}

	public void glRenderbufferStorage(int target, int internalformat, int width, int height) {
		gl.renderbufferStorage(target, internalformat, width, height);
	}

	public void glSampleCoverage(float value, boolean invert) {
		gl.sampleCoverage(value, invert);
	}

	public void glShaderBinary(int n, IntBuffer shaders, int binaryformat, Buffer binary, int length) {
		throw new RuntimeException("Not supported glShaderBinary");
	}

	public void glShaderSource(int shader, String string) {
		gl.shaderSource(shaders.getShader(shader), string);
	}

	public void glStencilFuncSeparate(int face, int func, int ref, int mask) {
		gl.stencilFuncSeparate(face, func, ref, mask);
	}

	public void glStencilMaskSeparate(int face, int mask) {
		gl.stencilMaskSeparate(face, mask);
	}

	public void glStencilOpSeparate(int face, int fail, int zfail, int zpass) {
		gl.stencilOpSeparate(face, fail, zfail, zpass);
	}

	public void glTexParameterfv(int target, int pname, FloatBuffer params) {
		glTexParameterf(target, pname, params.get(0));
	}

	public void glTexParameteri(int target, int pname, int param) {
		gl.texParameteri(target, pname, param);
	}

	public void glTexParameteriv(int target, int pname, IntBuffer params) {
		glTexParameteri(target, pname, params.get(0));
	}

	public void glUseProgram(int program) {
		gl.useProgram(programs.getProgram(program));
	}

	public void glValidateProgram(int program) {
		gl.validateProgram(programs.getProgram(program));
	}

	public void glUniform1f(int location, float x) {
		gl.uniform1f(uniformLocations.getUniformlocation(location), x);
	}

	public void glUniform1fv(int location, int count, FloatBuffer v) {
		gl.uniform1fv(uniformLocations.getUniformlocation(location), _floatBuffer(v, count));
	}

	public void glUniform1fv(int location, int count, float[] v, int offset) {
		gl.uniform1fv(uniformLocations.getUniformlocation(location), _floatArray(v, offset, count));
	}

	public void glUniform1i(int location, int x) {
		gl.uniform1i(uniformLocations.getUniformlocation(location), x);
	}

	public void glUniform1iv(int location, int count, IntBuffer v) {
		gl.uniform1iv(uniformLocations.getUniformlocation(location), _intBuffer(v, count));
	}

	public void glUniform1iv(int location, int count, int[] v, int offset) {
		gl.uniform1iv(uniformLocations.getUniformlocation(location), _intArray(v, offset, count));
	}

	public void glUniform2f(int location, float x, float y) {
		gl.uniform2f(uniformLocations.getUniformlocation(location), x, y);
	}

	public void glUniform2fv(int location, int count, FloatBuffer v) {
		gl.uniform2fv(uniformLocations.getUniformlocation(location), _floatBuffer(v, count));
	}

	public void glUniform2fv(int location, int count, float[] v, int offset) {
		gl.uniform2fv(uniformLocations.getUniformlocation(location), _floatArray(v, offset, count));
	}

	public void glUniform2i(int location, int x, int y) {
		gl.uniform2i(uniformLocations.getUniformlocation(location), x, y);
	}

	public void glUniform2iv(int location, int count, IntBuffer v) {
		gl.uniform2iv(uniformLocations.getUniformlocation(location), _intBuffer(v, count));
	}

	public void glUniform2iv(int location, int count, int[] v, int offset) {
		gl.uniform2iv(uniformLocations.getUniformlocation(location), _intArray(v, offset, count));
	}

	public void glUniform3f(int location, float x, float y, float z) {
		gl.uniform3f(uniformLocations.getUniformlocation(location), x, y, z);
	}

	public void glUniform3fv(int location, int count, FloatBuffer v) {
		gl.uniform3fv(uniformLocations.getUniformlocation(location), _floatBuffer(v, count));
	}

	public void glUniform3fv(int location, int count, float[] v, int offset) {
		gl.uniform3fv(uniformLocations.getUniformlocation(location), _floatArray(v, offset, count));
	}

	public void glUniform3i(int location, int x, int y, int z) {
		gl.uniform3i(uniformLocations.getUniformlocation(location), x, y, z);
	}

	public void glUniform3iv(int location, int count, IntBuffer v) {
		gl.uniform3iv(uniformLocations.getUniformlocation(location), _intBuffer(v, count));
	}

	public void glUniform3iv(int location, int count, int[] v, int offset) {
		gl.uniform3iv(uniformLocations.getUniformlocation(location), _intArray(v, offset, count));
	}

	public void glUniform4f(int location, float x, float y, float z, float w) {
		gl.uniform4f(uniformLocations.getUniformlocation(location), x, y, z, w);
	}

	public void glUniform4fv(int location, int count, FloatBuffer v) {
		gl.uniform4fv(uniformLocations.getUniformlocation(location), _floatBuffer(v, count));
	}

	public void glUniform4fv(int location, int count, float[] v, int offset) {
		gl.uniform4fv(uniformLocations.getUniformlocation(location), _floatArray(v, offset, count));
	}

	public void glUniform4i(int location, int x, int y, int z, int w) {
		gl.uniform4i(uniformLocations.getUniformlocation(location), x, y, z, w);
	}

	public void glUniform4iv(int location, int count, IntBuffer v) {
		gl.uniform4iv(uniformLocations.getUniformlocation(location), _intBuffer(v, count));
	}

	public void glUniform4iv(int location, int count, int[] v, int offset) {
		gl.uniform4iv(uniformLocations.getUniformlocation(location), _intArray(v, offset, count));
	}

	public void glUniformMatrix2fv(int location, int count, boolean transpose, FloatBuffer value) {
		gl.uniformMatrix2fv(uniformLocations.getUniformlocation(location), transpose, _floatBuffer(value, count));
	}

	public void glUniformMatrix2fv(int location, int count, boolean transpose, float[] value, int offset) {
		glUniformMatrix2fv(location, count, transpose, FloatBuffer.wrap(value, offset, value.length - offset));
	}

	public void glUniformMatrix3fv(int location, int count, boolean transpose, FloatBuffer value) {
		gl.uniformMatrix3fv(uniformLocations.getUniformlocation(location), transpose, _floatBuffer(value, count));
	}

	public void glUniformMatrix3fv(int location, int count, boolean transpose, float[] value, int offset) {
		gl.uniformMatrix3fv(uniformLocations.getUniformlocation(location), transpose, _floatArray(value, offset, count));
	}

	public void glUniformMatrix4fv(int location, int count, boolean transpose, FloatBuffer value) {
		gl.uniformMatrix4fv(uniformLocations.getUniformlocation(location), transpose, _floatBuffer(value, count));
	}

	public void glUniformMatrix4fv(int location, int count, boolean transpose, float[] value, int offset) {
		gl.uniformMatrix4fv(uniformLocations.getUniformlocation(location), transpose, _floatArray(value, offset, count));
	}

	public void glVertexAttrib1f(int indx, float x) {
		gl.vertexAttrib1f(indx, x);
	}

	public void glVertexAttrib1fv(int indx, FloatBuffer values) {
		gl.vertexAttrib1fv(indx, _floatBuffer(values));
	}

	public void glVertexAttrib2f(int indx, float x, float y) {
		gl.vertexAttrib2f(indx, x, y);
	}

	public void glVertexAttrib2fv(int indx, FloatBuffer values) {
		gl.vertexAttrib2fv(indx, _floatBuffer(values));
	}

	public void glVertexAttrib3f(int indx, float x, float y, float z) {
		gl.vertexAttrib3f(indx, x, y, z);
	}

	public void glVertexAttrib3fv(int indx, FloatBuffer values) {
		gl.vertexAttrib3fv(indx, _floatBuffer(values));
	}

	public void glVertexAttrib4f(int indx, float x, float y, float z, float w) {
		gl.vertexAttrib4f(indx, x, y, z, w);
	}

	public void glVertexAttrib4fv(int indx, FloatBuffer values) {
		gl.vertexAttrib4fv(indx, _floatBuffer(values));
	}

	native public void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, Buffer ptr);

	public void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, int ptr) {
		gl.vertexAttribPointer(indx, size, type, normalized, stride, ptr);
	}


}
