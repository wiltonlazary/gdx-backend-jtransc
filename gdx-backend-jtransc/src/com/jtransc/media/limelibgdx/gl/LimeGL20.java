package com.jtransc.media.limelibgdx.gl;

import com.badlogic.gdx.graphics.GL20;
import com.jtransc.media.limelibgdx.dummy.DummyGL20;
import jtransc.annotation.haxe.HaxeAddMembers;
import jtransc.annotation.haxe.HaxeImports;
import jtransc.annotation.haxe.HaxeMethodBody;

import java.nio.Buffer;
import java.nio.IntBuffer;

@HaxeImports({
	"import lime.graphics.opengl.GL;"
})
@HaxeAddMembers({
	"static private function _convertBuffer(a) { return a; }",
	// GLTexture
	"static private function _texId(a) { return -1; }",
	"static private function _texIdAlloc(a) { return -1; }",
	"static private function _texIdFree(a) { return -1; }"
})
public class LimeGL20 extends DummyGL20 implements GL20 {
	@HaxeMethodBody("GL.activeTexture(p0);")
	native public void glActiveTexture(int texture);

	@HaxeMethodBody("GL.bindTexture(p0, p1);")
	native public void glBindTexture(int target, int texture);

	@HaxeMethodBody("GL.blendFunc(p0, p1);")
	native public void glBlendFunc(int sfactor, int dfactor);

	@HaxeMethodBody("GL.clear(p0);")
	native public void glClear(int mask);

	@HaxeMethodBody("GL.clearColor(p0, p1, p2, p3);")
	native public void glClearColor(float red, float green, float blue, float alpha);

	@HaxeMethodBody("GL.clearDepth(p0);")
	native public void glClearDepthf(float depth);

	@HaxeMethodBody("GL.clearStencil(p0);")
	native public void glClearStencil(int s);

	@HaxeMethodBody("GL.colorMask(p0, p1, p2, p3);")
	native public void glColorMask(boolean red, boolean green, boolean blue, boolean alpha);

	@HaxeMethodBody("GL.compressedTexImage2D(p0, p1, p2, p3, p4, p5, _convertBuffer(p6));")
	native public void glCompressedTexImage2D(int target, int level, int internalformat, int width, int height, int border, int imageSize, Buffer data);

	@HaxeMethodBody("GL.compressedTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, _convertBuffer(p7));")
	native public void glCompressedTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int imageSize, Buffer data);

	@HaxeMethodBody("GL.copyTexImage2D(p0, p1, p2, p3, p4, p5, p6, p7);")
	native public void glCopyTexImage2D(int target, int level, int internalformat, int x, int y, int width, int height, int border);

	@HaxeMethodBody("GL.copyTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7);")
	native public void glCopyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y, int width, int height);

	@HaxeMethodBody("GL.cullFace(p0);")
	native public void glCullFace(int mode);

	public void glDeleteTextures(int n, IntBuffer textures) {
		for (int i = 0; i < n; i++) glDeleteTexture(textures.get(i));
	}

	@HaxeMethodBody("GL.deleteTexture(_texId(p0)); _texIdFree(p0);")
	native public void glDeleteTexture(int texture);

	@HaxeMethodBody("GL.depthFunc(p0);")
	native public void glDepthFunc(int func);

	@HaxeMethodBody("GL.depthMask(p0);")
	native public void glDepthMask(boolean flag);

	@HaxeMethodBody("GL.depthRange(p0, p1);")
	native public void glDepthRangef(float zNear, float zFar);

	@HaxeMethodBody("GL.disable(p0);")
	native public void glDisable(int cap);

	@HaxeMethodBody("GL.drawArrays(p0, p1, p2);")
	native public void glDrawArrays(int mode, int first, int count);

	@HaxeMethodBody("GL.drawElements(p0, p1, p2, _convertBuffer(p3));")
	native public void glDrawElements(int mode, int count, int type, Buffer indices);

	@HaxeMethodBody("GL.enable(p0);")
	native public void glEnable(int cap);

	@HaxeMethodBody("GL.finish();")
	native public void glFinish();

	@HaxeMethodBody("GL.flush();")
	native public void glFlush();

	@HaxeMethodBody("GL.frontFace(mode);")
	native public void glFrontFace(int mode);

	public void glGenTextures(int n, IntBuffer textures) {
		for (int i = 0; i < n; i++) textures.put(i, glGenTexture());
	}

	@HaxeMethodBody("return _texIdAlloc(GL.createTexture());")
	native public int glGenTexture();

	@HaxeMethodBody("return GL.getError();")
	native public int glGetError();

	@HaxeMethodBody("GL.getIntegerv(p0, p1);")
	native public void glGetIntegerv(int pname, IntBuffer params);

	@HaxeMethodBody("return HaxeNatives.str(GL.getString(p0));")
	native public String glGetString(int name);

	@HaxeMethodBody("GL.hint(p0, p1);")
	native public void glHint(int target, int mode);

	@HaxeMethodBody("GL.lineWidth(p0);")
	native public void glLineWidth(float width);

	@HaxeMethodBody("GL.pixelStorei(p0, p1);")
	native public void glPixelStorei(int pname, int param);

	@HaxeMethodBody("GL.polygonOffset(p0, p1);")
	native public void glPolygonOffset(float factor, float units);

	@HaxeMethodBody("GL.readPixels(p0, p1, p2, p3, p4, p5, _convertBuffer(p6));")
	native public void glReadPixels(int x, int y, int width, int height, int format, int type, Buffer pixels);

	@HaxeMethodBody("GL.scissor(p0, p1, p2, p3);")
	native public void glScissor(int x, int y, int width, int height);

	@HaxeMethodBody("GL.stencilFunc(p0, p1, p2);")
	native public void glStencilFunc(int func, int ref, int mask);

	@HaxeMethodBody("GL.stencilMask(p0);")
	native public void glStencilMask(int mask);

	@HaxeMethodBody("GL.stencilOp(p0, p1, p2);")
	native public void glStencilOp(int fail, int zfail, int zpass);

	@HaxeMethodBody("GL.texImage2D(p0, p1, p2, p3, p4, p5, p6, p7, _convertBuffer(p8));")
	native public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, Buffer pixels);

	@HaxeMethodBody("GL.texParameterf(p0, p1, p2);")
	native public void glTexParameterf(int target, int pname, float param);

	@HaxeMethodBody("GL.texSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, _convertBuffer(p8));")
	native public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, Buffer pixels);

	@HaxeMethodBody("GL.viewport(p0, p1, p2, p3);")
	native public void glViewport(int x, int y, int width, int height);

	@HaxeMethodBody("GL.attachShader(p0, p1);")
	native public void glAttachShader(int program, int shader);

	@HaxeMethodBody("GL.bindAttribLocation(p0, p1, p2._str);")
	native public void glBindAttribLocation(int program, int index, String name);

	@HaxeMethodBody("GL.bindBuffer(p0, p1);")
	native public void glBindBuffer(int target, int buffer);

	@HaxeMethodBody("GL.bindFramebuffer(p0, p1);")
	native public void glBindFramebuffer(int target, int framebuffer);

	@HaxeMethodBody("GL.bindRenderbuffer(p0, p1);")
	native public void glBindRenderbuffer(int target, int renderbuffer);

	/*
	public void glBlendColor(float red, float green, float blue, float alpha) {
		GL.blendColor(red, green, blue, alpha);
	}

	public void glBlendEquation(int mode) {
		GL.blendEquation(mode);
	}

	public void glBlendEquationSeparate(int modeRGB, int modeAlpha) {
		GL.blendEquationSeparate(modeRGB, modeAlpha);
	}

	public void glBlendFuncSeparate(int srcRGB, int dstRGB, int srcAlpha, int dstAlpha) {
		GL.blendFuncSeparate(srcRGB, dstRGB, srcAlpha, dstAlpha);
	}

	public void glBufferData(int target, int size, Buffer data, int usage) {
		GL.bufferData(target, size, data, usage);
	}

	public void glBufferSubData(int target, int offset, int size, Buffer data) {
		GL.bufferSubData(target, offset, size, data);
	}

	public int glCheckFramebufferStatus(int target) {
		return GL.checkFramebufferStatus(target);
	}

	public void glCompileShader(int shader) {
		GL.compileShader(shader);
	}

	public int glCreateProgram() {
		return GL.createProgram();
	}

	public int glCreateShader(int type) {
		return GL.createShader(type);
	}

	public void glDeleteBuffer(int buffer) {
		GL.deleteBuffer(buffer);
	}

	public void glDeleteBuffers(int n, IntBuffer buffers) {
		for (int i = 0; i < n; i++) glDeleteBuffer(buffers.get(i));
	}

	public void glDeleteFramebuffer(int framebuffer) {
		GL.deleteFramebuffer(framebuffer);
	}

	public void glDeleteFramebuffers(int n, IntBuffer framebuffers) {
		for (int i = 0; i < n; i++) glDeleteFramebuffer(framebuffers.get(i));
	}

	public void glDeleteProgram(int program) {
		GL.deleteProgram(program);
	}

	public void glDeleteRenderbuffer(int renderbuffer) {
		GL.deleteRenderbuffer(renderbuffer);
	}

	public void glDeleteRenderbuffers(int n, IntBuffer renderbuffers) {
		for (int i = 0; i < n; i++) glDeleteRenderbuffer(renderbuffers.get(i));
	}

	public void glDeleteShader(int shader) {
		GL.deleteShader(shader);
	}

	public void glDetachShader(int program, int shader) {
		GL.detachShader(program, shader);
	}

	public void glDisableVertexAttribArray(int index) {
		GL.disableVertexAttribArray(index);
	}

	public void glDrawElements(int mode, int count, int type, int indices) {
		GL.drawElements(mode, count, type, indices);
	}

	public void glEnableVertexAttribArray(int index) {
		GL.enableVertexAttribArray(index);
	}

	public void glFramebufferRenderbuffer(int target, int attachment, int renderbuffertarget, int renderbuffer) {
		GL.framebufferRenderbuffer(target, attachment, renderbuffertarget, renderbuffer);
	}

	public void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level) {
		GL.framebufferTexture2D(target, attachment, textarget, texture, level);
	}

	public int glGenBuffer() {
		return GL.createBuffer();
	}

	public void glGenBuffers(int n, IntBuffer buffers) {
		for (int i = 0; i < n; i++) buffers.put(i, glGenBuffers());
	}

	public void glGenerateMipmap(int target) {
		GL.generateMipmap(target);
	}

	public int glGenFramebuffer() {
		return GL.createFramebuffer();
	}

	public void glGenFramebuffers(int n, IntBuffer framebuffers) {
		for (int i = 0; i < n; i++) framebuffers.put(i, glGenFramebuffer());
	}

	public int glGenRenderbuffer() {
		return GL.createRenderbuffer();
	}

	public void glGenRenderbuffers(int n, IntBuffer renderbuffers) {
		for (int i = 0; i < n; i++) renderbuffers.put(i, glGenRenderbuffer());
	}

	public String glGetActiveAttrib(int program, int index, IntBuffer size, Buffer type) {
		return GL.getActiveAttrib(program, index, size, type);
	}

	public String glGetActiveUniform(int program, int index, IntBuffer size, Buffer type) {
		return GL.getActiveUniform(program, index, size, type);
	}

	public void glGetAttachedShaders(int program, int maxcount, Buffer count, IntBuffer shaders) {
		GL.getAttachedShaders(program, maxcount, buffer, shaders)
	}

	public int glGetAttribLocation(int program, String name) {
		return GL.getAttribLocation(program, name);
	}

	public void glGetBooleanv(int pname, Buffer params) {
		GL.getBooleanv(pname, params);
	}

	public void glGetBufferParameteriv(int target, int pname, IntBuffer params) {
		GL.getBufferParameteriv(target, name, params);
	}

	public void glGetFloatv(int pname, FloatBuffer params) {
		GL.getFloatv(pname, params);
	}

	public void glGetFramebufferAttachmentParameteriv(int target, int attachment, int pname, IntBuffer params) {
		GL.getFramebufferAttachmentParameter(target, attachment, pname, params);
	}

	public void glGetProgramiv(int program, int pname, IntBuffer params) {
		GL.getProgramiv(program, pname, params);
	}

	public String glGetProgramInfoLog(int program) {
		return GL.getProgramInfoLog(program);
	}

	public void glGetRenderbufferParameteriv(int target, int pname, IntBuffer params) {
		GL.getRenderbufferParameteriv(target, pname, params);
	}

	public void glGetShaderiv(int shader, int pname, IntBuffer params) {
		GL.getShaderiv(shader, pname, params);
	}

	public String glGetShaderInfoLog(int shader) {
		return GL.getShaderInfoLog(shader);
	}

	public void glGetShaderPrecisionFormat(int shadertype, int precisiontype, IntBuffer range, IntBuffer precision) {
		GL.getShaderPrecisionFormat(shadertype, precisiontype, range, precision);
	}

	public void glGetTexParameterfv(int target, int pname, FloatBuffer params) {
		GL.getTexParameter(target, pname, params);
	}

	public void glGetTexParameteriv(int target, int pname, IntBuffer params) {
		GL.getTexParameter(target, pname, params);
	}

	public void glGetUniformfv(int program, int location, FloatBuffer params) {
		GL.getUniform(program, location, params);
	}

	public void glGetUniformiv(int program, int location, IntBuffer params) {
		GL.getUniform(program, location, params);
	}

	public int glGetUniformLocation(int program, String name) {
		return GL.getUniformLocation(program, name);
	}

	public void glGetVertexAttribfv(int index, int pname, FloatBuffer params) {
		GL.getVertexAttrib(index, pname, params);
	}

	public void glGetVertexAttribiv(int index, int pname, IntBuffer params) {
		GL.getVertexAttrib(index, pname, params);
	}

	public void glGetVertexAttribPointerv(int index, int pname, Buffer pointer) {
		//GL.getVertexAttrib(index, pname, params);
	}

	public boolean glIsBuffer(int buffer) {
		return GL.isBuffer(buffer);
	}

	public boolean glIsEnabled(int cap) {
		return GL.isEnabled(cap);
	}

	public boolean glIsFramebuffer(int framebuffer) {
		return GL.isFramebuffer(framebuffer);
	}

	public boolean glIsProgram(int program) {
		return GL.isProgram(program);
	}

	public boolean glIsRenderbuffer(int renderbuffer) {
		return GL.isRenderbuffer(renderbuffer);
	}

	public boolean glIsShader(int shader) {
		return GL.isShader(shader);
	}

	public boolean glIsTexture(int texture) {
		return GL.isTexture(texture);
	}

	public void glLinkProgram(int program) {
		GL.linkProgram(program);
	}

	public void glReleaseShaderCompiler() {
		GL.releaseShaderCompiler();
	}

	public void glRenderbufferStorage(int target, int internalformat, int width, int height) {
		GL.renderbufferStorage(target, internalformat, width, height);
	}

	public void glSampleCoverage(float value, boolean invert) {
		GL.sampleCoverage(value, invert);
	}

	public void glShaderBinary(int n, IntBuffer shaders, int binaryformat, Buffer binary, int length) {
		GL.shaderBinary(n, shaders, binaryformat, binary, length);
	}

	public void glShaderSource(int shader, String string) {
		GL.shaderSource(shader, string);
	}

	public void glStencilFuncSeparate(int face, int func, int ref, int mask) {
		GL.stencilFuncSeparate(face, func, ref, mask);
	}

	public void glStencilMaskSeparate(int face, int mask) {
		GL.stencilMaskSeparate(face, mask);
	}

	public void glStencilOpSeparate(int face, int fail, int zfail, int zpass) {
		GL.stencilOpSeparate(face, fail, zfail, zpass);
	}

	public void glTexParameterfv(int target, int pname, FloatBuffer params) {
		GL.texParameterf(target, pname, params);
	}

	public void glTexParameteri(int target, int pname, int param) {
		GL.texParameteri(target, pname, param);
	}

	public void glTexParameteriv(int target, int pname, IntBuffer params) {
		GL.texParameteri(target, pname, params);
	}

	public void glUniform1f(int location, float x) {
		GL.uniform1f(location, x);
	}

	public void glUniform1fv(int location, int count, FloatBuffer v) {
		GL.uniform1fv(location, count, v);
	}

	public void glUniform1fv(int location, int count, float[] v, int offset) {
		GL.uniform1fv(location, count, v, offset);
	}

	public void glUniform1i(int location, int x) {
		GL.uniform1i(location, x);
	}

	public void glUniform1iv(int location, int count, IntBuffer v) {
	}

	public void glUniform1iv(int location, int count, int[] v, int offset) {
	}

	public void glUniform2f(int location, float x, float y) {
	}

	public void glUniform2fv(int location, int count, FloatBuffer v) {
	}

	public void glUniform2fv(int location, int count, float[] v, int offset) {
	}

	public void glUniform2i(int location, int x, int y) {
	}

	public void glUniform2iv(int location, int count, IntBuffer v) {
	}

	public void glUniform2iv(int location, int count, int[] v, int offset) {
	}

	public void glUniform3f(int location, float x, float y, float z) {
	}

	public void glUniform3fv(int location, int count, FloatBuffer v) {
	}

	public void glUniform3fv(int location, int count, float[] v, int offset) {
	}

	public void glUniform3i(int location, int x, int y, int z) {
	}

	public void glUniform3iv(int location, int count, IntBuffer v) {
	}

	public void glUniform3iv(int location, int count, int[] v, int offset) {
	}

	public void glUniform4f(int location, float x, float y, float z, float w) {
	}

	public void glUniform4fv(int location, int count, FloatBuffer v) {
	}

	public void glUniform4fv(int location, int count, float[] v, int offset) {
	}

	public void glUniform4i(int location, int x, int y, int z, int w) {
	}

	public void glUniform4iv(int location, int count, IntBuffer v) {
	}

	public void glUniform4iv(int location, int count, int[] v, int offset) {
	}

	public void glUniformMatrix2fv(int location, int count, boolean transpose, FloatBuffer value) {
	}

	public void glUniformMatrix2fv(int location, int count, boolean transpose, float[] value, int offset) {
	}

	public void glUniformMatrix3fv(int location, int count, boolean transpose, FloatBuffer value) {
	}

	public void glUniformMatrix3fv(int location, int count, boolean transpose, float[] value, int offset) {
	}

	public void glUniformMatrix4fv(int location, int count, boolean transpose, FloatBuffer value) {
	}

	public void glUniformMatrix4fv(int location, int count, boolean transpose, float[] value, int offset) {
	}

	public void glUseProgram(int program) {
	}

	public void glValidateProgram(int program) {
	}

	public void glVertexAttrib1f(int indx, float x) {
	}

	public void glVertexAttrib1fv(int indx, FloatBuffer values) {
	}

	public void glVertexAttrib2f(int indx, float x, float y) {
	}

	public void glVertexAttrib2fv(int indx, FloatBuffer values) {
	}

	public void glVertexAttrib3f(int indx, float x, float y, float z) {
	}

	public void glVertexAttrib3fv(int indx, FloatBuffer values) {
	}

	public void glVertexAttrib4f(int indx, float x, float y, float z, float w) {
	}

	public void glVertexAttrib4fv(int indx, FloatBuffer values) {
	}

	public void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, Buffer ptr) {
	}

	public void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, int ptr) {
	}
	*/
}
