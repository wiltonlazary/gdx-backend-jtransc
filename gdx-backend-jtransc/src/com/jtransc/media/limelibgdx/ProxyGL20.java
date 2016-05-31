package com.jtransc.media.limelibgdx;

import com.badlogic.gdx.graphics.GL20;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class ProxyGL20 implements GL20Ext {
	private GL20 parent;

	public ProxyGL20(GL20 parent) {
		this.parent = parent;
	}

	@Override
	public void glActiveTexture(int texture) {
		parent.glActiveTexture(texture);
	}

	@Override
	public void glBindTexture(int target, int texture) {
		parent.glBindTexture(target, texture);
	}

	@Override
	public void glBlendFunc(int sfactor, int dfactor) {
		parent.glBlendFunc(sfactor, dfactor);
	}

	@Override
	public void glClear(int mask) {
		parent.glClear(mask);
	}

	@Override
	public void glClearColor(float red, float green, float blue, float alpha) {
		parent.glClearColor(red, green, blue, alpha);
	}

	@Override
	public void glClearDepthf(float depth) {
		parent.glClearDepthf(depth);
	}

	@Override
	public void glClearStencil(int s) {
		parent.glClearStencil(s);
	}

	@Override
	public void glColorMask(boolean red, boolean green, boolean blue, boolean alpha) {
		parent.glColorMask(red, green, blue, alpha);
	}

	@Override
	public void glCompressedTexImage2D(int target, int level, int internalformat, int width, int height, int border, int imageSize, Buffer data) {
		parent.glCompressedTexImage2D(target, level, internalformat, width, height, border, imageSize, data);
	}

	@Override
	public void glCompressedTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int imageSize, Buffer data) {
		parent.glCompressedTexSubImage2D(target, level, xoffset, yoffset, width, height, format, imageSize, data);
	}

	@Override
	public void glCopyTexImage2D(int target, int level, int internalformat, int x, int y, int width, int height, int border) {
		parent.glCopyTexImage2D(target, level, internalformat, x, y, width, height, border);
	}

	@Override
	public void glCopyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y, int width, int height) {
		parent.glCopyTexSubImage2D(target, level, xoffset, yoffset, x, y, width, height);
	}

	@Override
	public void glCullFace(int mode) {
		parent.glCullFace(mode);
	}

	@Override
	public void glDeleteTextures(int n, IntBuffer textures) {
		parent.glDeleteTextures(n, textures);
	}

	@Override
	public void glDeleteTexture(int texture) {
		parent.glDeleteTexture(texture);
	}

	@Override
	public void glDepthFunc(int func) {
		parent.glDepthFunc(func);
	}

	@Override
	public void glDepthMask(boolean flag) {
		parent.glDepthMask(flag);
	}

	@Override
	public void glDepthRangef(float zNear, float zFar) {
		parent.glDepthRangef(zNear, zFar);
	}

	@Override
	public void glDisable(int cap) {
		parent.glDisable(cap);
	}

	@Override
	public void glDrawArrays(int mode, int first, int count) {
		parent.glDrawArrays(mode, first, count);
	}

	@Override
	public void glDrawElements(int mode, int count, int type, Buffer indices) {
		parent.glDrawElements(mode, count, type, indices);
	}

	@Override
	public void glEnable(int cap) {
		parent.glEnable(cap);
	}

	@Override
	public void glFinish() {
		parent.glFinish();
	}

	@Override
	public void glFlush() {
		parent.glFlush();
	}

	@Override
	public void glFrontFace(int mode) {
		parent.glFrontFace(mode);
	}

	@Override
	public void glGenTextures(int n, IntBuffer textures) {
		parent.glGenTextures(n, textures);
	}

	@Override
	public int glGenTexture() {
		return parent.glGenTexture();
	}

	@Override
	public int glGetError() {
		return parent.glGetError();
	}

	@Override
	public void glGetIntegerv(int pname, IntBuffer params) {
		parent.glGetIntegerv(pname, params);
	}

	@Override
	public String glGetString(int name) {
		return parent.glGetString(name);
	}

	@Override
	public void glHint(int target, int mode) {
		parent.glHint(target, mode);
	}

	@Override
	public void glLineWidth(float width) {
		parent.glLineWidth(width);
	}

	@Override
	public void glPixelStorei(int pname, int param) {
		parent.glPixelStorei(pname, param);
	}

	@Override
	public void glPolygonOffset(float factor, float units) {
		parent.glPolygonOffset(factor, units);
	}

	@Override
	public void glReadPixels(int x, int y, int width, int height, int format, int type, Buffer pixels) {
		parent.glReadPixels(x, y, width, height, format, type, pixels);
	}

	@Override
	public void glScissor(int x, int y, int width, int height) {
		parent.glScissor(x, y, width, height);
	}

	@Override
	public void glStencilFunc(int func, int ref, int mask) {
		parent.glStencilFunc(func, ref, mask);
	}

	@Override
	public void glStencilMask(int mask) {
		parent.glStencilMask(mask);
	}

	@Override
	public void glStencilOp(int fail, int zfail, int zpass) {
		parent.glStencilOp(fail, zfail, zpass);
	}

	@Override
	public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, Buffer pixels) {
		parent.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
	}

	@Override
	public void glTexParameterf(int target, int pname, float param) {
		parent.glTexParameterf(target, pname, param);
	}

	@Override
	public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, Buffer pixels) {
		parent.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
	}

	@Override
	public void glViewport(int x, int y, int width, int height) {
		parent.glViewport(x, y, width, height);
	}

	@Override
	public void glAttachShader(int program, int shader) {
		parent.glAttachShader(program, shader);
	}

	@Override
	public void glBindAttribLocation(int program, int index, String name) {
		parent.glBindAttribLocation(program, index, name);
	}

	@Override
	public void glBindBuffer(int target, int buffer) {
		parent.glBindBuffer(target, buffer);
	}

	@Override
	public void glBindFramebuffer(int target, int framebuffer) {
		parent.glBindFramebuffer(target, framebuffer);
	}

	@Override
	public void glBindRenderbuffer(int target, int renderbuffer) {
		parent.glBindRenderbuffer(target, renderbuffer);
	}

	@Override
	public void glBlendColor(float red, float green, float blue, float alpha) {
		parent.glBlendColor(red, green, blue, alpha);
	}

	@Override
	public void glBlendEquation(int mode) {
		parent.glBlendEquation(mode);
	}

	@Override
	public void glBlendEquationSeparate(int modeRGB, int modeAlpha) {
		parent.glBlendEquationSeparate(modeRGB, modeAlpha);
	}

	@Override
	public void glBlendFuncSeparate(int srcRGB, int dstRGB, int srcAlpha, int dstAlpha) {
		parent.glBlendFuncSeparate(srcRGB, dstRGB, srcAlpha, dstAlpha);
	}

	@Override
	public void glBufferData(int target, int size, Buffer data, int usage) {
		parent.glBufferData(target, size, data, usage);
	}

	@Override
	public void glBufferSubData(int target, int offset, int size, Buffer data) {
		parent.glBufferSubData(target, offset, size, data);
	}

	@Override
	public int glCheckFramebufferStatus(int target) {
		return parent.glCheckFramebufferStatus(target);
	}

	@Override
	public void glCompileShader(int shader) {
		parent.glCompileShader(shader);
	}

	@Override
	public int glCreateProgram() {
		return parent.glCreateProgram();
	}

	@Override
	public int glCreateShader(int type) {
		return parent.glCreateShader(type);
	}

	@Override
	public void glDeleteBuffer(int buffer) {
		parent.glDeleteBuffer(buffer);
	}

	@Override
	public void glDeleteBuffers(int n, IntBuffer buffers) {
		parent.glDeleteBuffers(n, buffers);
	}

	@Override
	public void glDeleteFramebuffer(int framebuffer) {
		parent.glDeleteFramebuffer(framebuffer);
	}

	@Override
	public void glDeleteFramebuffers(int n, IntBuffer framebuffers) {
		parent.glDeleteFramebuffers(n, framebuffers);
	}

	@Override
	public void glDeleteProgram(int program) {
		parent.glDeleteProgram(program);
	}

	@Override
	public void glDeleteRenderbuffer(int renderbuffer) {
		parent.glDeleteRenderbuffer(renderbuffer);
	}

	@Override
	public void glDeleteRenderbuffers(int n, IntBuffer renderbuffers) {
		parent.glDeleteRenderbuffers(n, renderbuffers);
	}

	@Override
	public void glDeleteShader(int shader) {
		parent.glDeleteShader(shader);
	}

	@Override
	public void glDetachShader(int program, int shader) {
		parent.glDetachShader(program, shader);
	}

	@Override
	public void glDisableVertexAttribArray(int index) {
		parent.glDisableVertexAttribArray(index);
	}

	@Override
	public void glDrawElements(int mode, int count, int type, int indices) {
		parent.glDrawElements(mode, count, type, indices);
	}

	@Override
	public void glEnableVertexAttribArray(int index) {
		parent.glEnableVertexAttribArray(index);
	}

	@Override
	public void glFramebufferRenderbuffer(int target, int attachment, int renderbuffertarget, int renderbuffer) {
		parent.glFramebufferRenderbuffer(target, attachment, renderbuffertarget, renderbuffer);
	}

	@Override
	public void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level) {
		parent.glFramebufferTexture2D(target, attachment, textarget, texture, level);
	}

	@Override
	public int glGenBuffer() {
		return parent.glGenBuffer();
	}

	@Override
	public void glGenBuffers(int n, IntBuffer buffers) {
		parent.glGenBuffers(n, buffers);
	}

	@Override
	public void glGenerateMipmap(int target) {
		parent.glGenerateMipmap(target);
	}

	@Override
	public int glGenFramebuffer() {
		return parent.glGenFramebuffer();
	}

	@Override
	public void glGenFramebuffers(int n, IntBuffer framebuffers) {
		parent.glGenFramebuffers(n, framebuffers);
	}

	@Override
	public int glGenRenderbuffer() {
		return parent.glGenRenderbuffer();
	}

	@Override
	public void glGenRenderbuffers(int n, IntBuffer renderbuffers) {
		parent.glGenRenderbuffers(n, renderbuffers);
	}

	@Override
	public String glGetActiveAttrib(int program, int index, IntBuffer size, Buffer type) {
		return parent.glGetActiveAttrib(program, index, size, type);
	}

	@Override
	public String glGetActiveUniform(int program, int index, IntBuffer size, Buffer type) {
		return parent.glGetActiveUniform(program, index, size, type);
	}

	@Override
	public void glGetAttachedShaders(int program, int maxcount, Buffer count, IntBuffer shaders) {
		parent.glGetAttachedShaders(program, maxcount, count, shaders);
	}

	@Override
	public int glGetAttribLocation(int program, String name) {
		return parent.glGetAttribLocation(program, name);
	}

	@Override
	public void glGetBooleanv(int pname, Buffer params) {
		parent.glGetBooleanv(pname, params);
	}

	@Override
	public void glGetBufferParameteriv(int target, int pname, IntBuffer params) {
		parent.glGetBufferParameteriv(target, pname, params);
	}

	@Override
	public void glGetFloatv(int pname, FloatBuffer params) {
		parent.glGetFloatv(pname, params);
	}

	@Override
	public void glGetFramebufferAttachmentParameteriv(int target, int attachment, int pname, IntBuffer params) {
		parent.glGetFramebufferAttachmentParameteriv(target, attachment, pname, params);
	}

	@Override
	public void glGetProgramiv(int program, int pname, IntBuffer params) {
		parent.glGetProgramiv(program, pname, params);
	}

	@Override
	public String glGetProgramInfoLog(int program) {
		return parent.glGetProgramInfoLog(program);
	}

	@Override
	public void glGetRenderbufferParameteriv(int target, int pname, IntBuffer params) {
		parent.glGetRenderbufferParameteriv(target, pname, params);
	}

	@Override
	public void glGetShaderiv(int shader, int pname, IntBuffer params) {
		parent.glGetShaderiv(shader, pname, params);
	}

	@Override
	public String glGetShaderInfoLog(int shader) {
		return parent.glGetShaderInfoLog(shader);
	}

	@Override
	public void glGetShaderPrecisionFormat(int shadertype, int precisiontype, IntBuffer range, IntBuffer precision) {
		parent.glGetShaderPrecisionFormat(shadertype, precisiontype, range, precision);
	}

	@Override
	public void glGetTexParameterfv(int target, int pname, FloatBuffer params) {
		parent.glGetTexParameterfv(target, pname, params);
	}

	@Override
	public void glGetTexParameteriv(int target, int pname, IntBuffer params) {
		parent.glGetTexParameteriv(target, pname, params);
	}

	@Override
	public void glGetUniformfv(int program, int location, FloatBuffer params) {
		parent.glGetUniformfv(program, location, params);
	}

	@Override
	public void glGetUniformiv(int program, int location, IntBuffer params) {
		parent.glGetUniformiv(program, location, params);
	}

	@Override
	public int glGetUniformLocation(int program, String name) {
		return parent.glGetUniformLocation(program, name);
	}

	@Override
	public void glGetVertexAttribfv(int index, int pname, FloatBuffer params) {
		parent.glGetVertexAttribfv(index, pname, params);
	}

	@Override
	public void glGetVertexAttribiv(int index, int pname, IntBuffer params) {
		parent.glGetVertexAttribiv(index, pname, params);
	}

	@Override
	public void glGetVertexAttribPointerv(int index, int pname, Buffer pointer) {
		parent.glGetVertexAttribPointerv(index, pname, pointer);
	}

	@Override
	public boolean glIsBuffer(int buffer) {
		return parent.glIsBuffer(buffer);
	}

	@Override
	public boolean glIsEnabled(int cap) {
		return parent.glIsEnabled(cap);
	}

	@Override
	public boolean glIsFramebuffer(int framebuffer) {
		return parent.glIsFramebuffer(framebuffer);
	}

	@Override
	public boolean glIsProgram(int program) {
		return parent.glIsProgram(program);
	}

	@Override
	public boolean glIsRenderbuffer(int renderbuffer) {
		return parent.glIsRenderbuffer(renderbuffer);
	}

	@Override
	public boolean glIsShader(int shader) {
		return parent.glIsShader(shader);
	}

	@Override
	public boolean glIsTexture(int texture) {
		return parent.glIsTexture(texture);
	}

	@Override
	public void glLinkProgram(int program) {
		parent.glLinkProgram(program);
	}

	@Override
	public void glReleaseShaderCompiler() {
		parent.glReleaseShaderCompiler();
	}

	@Override
	public void glRenderbufferStorage(int target, int internalformat, int width, int height) {
		parent.glRenderbufferStorage(target, internalformat, width, height);
	}

	@Override
	public void glSampleCoverage(float value, boolean invert) {
		parent.glSampleCoverage(value, invert);
	}

	@Override
	public void glShaderBinary(int n, IntBuffer shaders, int binaryformat, Buffer binary, int length) {
		parent.glShaderBinary(n, shaders, binaryformat, binary, length);
	}

	@Override
	public void glShaderSource(int shader, String string) {
		parent.glShaderSource(shader, string);
	}

	@Override
	public void glStencilFuncSeparate(int face, int func, int ref, int mask) {
		parent.glStencilFuncSeparate(face, func, ref, mask);
	}

	@Override
	public void glStencilMaskSeparate(int face, int mask) {
		parent.glStencilMaskSeparate(face, mask);
	}

	@Override
	public void glStencilOpSeparate(int face, int fail, int zfail, int zpass) {
		parent.glStencilOpSeparate(face, fail, zfail, zpass);
	}

	@Override
	public void glTexParameterfv(int target, int pname, FloatBuffer params) {
		parent.glTexParameterfv(target, pname, params);
	}

	@Override
	public void glTexParameteri(int target, int pname, int param) {
		parent.glTexParameteri(target, pname, param);
	}

	@Override
	public void glTexParameteriv(int target, int pname, IntBuffer params) {
		parent.glTexParameteriv(target, pname, params);
	}

	@Override
	public void glUniform1f(int location, float x) {
		parent.glUniform1f(location, x);
	}

	@Override
	public void glUniform1fv(int location, int count, FloatBuffer v) {
		parent.glUniform1fv(location, count, v);
	}

	@Override
	public void glUniform1fv(int location, int count, float[] v, int offset) {
		parent.glUniform1fv(location, count, v, offset);
	}

	@Override
	public void glUniform1i(int location, int x) {
		parent.glUniform1i(location, x);
	}

	@Override
	public void glUniform1iv(int location, int count, IntBuffer v) {
		parent.glUniform1iv(location, count, v);
	}

	@Override
	public void glUniform1iv(int location, int count, int[] v, int offset) {
		parent.glUniform1iv(location, count, v, offset);
	}

	@Override
	public void glUniform2f(int location, float x, float y) {
		parent.glUniform2f(location, x, y);
	}

	@Override
	public void glUniform2fv(int location, int count, FloatBuffer v) {
		parent.glUniform2fv(location, count, v);
	}

	@Override
	public void glUniform2fv(int location, int count, float[] v, int offset) {
		parent.glUniform2fv(location, count, v, offset);
	}

	@Override
	public void glUniform2i(int location, int x, int y) {
		parent.glUniform2i(location, x, y);
	}

	@Override
	public void glUniform2iv(int location, int count, IntBuffer v) {
		parent.glUniform2iv(location, count, v);
	}

	@Override
	public void glUniform2iv(int location, int count, int[] v, int offset) {
		parent.glUniform2iv(location, count, v, offset);
	}

	@Override
	public void glUniform3f(int location, float x, float y, float z) {
		parent.glUniform3f(location, x, y, z);
	}

	@Override
	public void glUniform3fv(int location, int count, FloatBuffer v) {
		parent.glUniform3fv(location, count, v);
	}

	@Override
	public void glUniform3fv(int location, int count, float[] v, int offset) {
		parent.glUniform3fv(location, count, v, offset);
	}

	@Override
	public void glUniform3i(int location, int x, int y, int z) {
		parent.glUniform3i(location, x, y, z);
	}

	@Override
	public void glUniform3iv(int location, int count, IntBuffer v) {
		parent.glUniform3iv(location, count, v);
	}

	@Override
	public void glUniform3iv(int location, int count, int[] v, int offset) {
		parent.glUniform3iv(location, count, v, offset);
	}

	@Override
	public void glUniform4f(int location, float x, float y, float z, float w) {
		parent.glUniform4f(location, x, y, z, w);
	}

	@Override
	public void glUniform4fv(int location, int count, FloatBuffer v) {
		parent.glUniform4fv(location, count, v);
	}

	@Override
	public void glUniform4fv(int location, int count, float[] v, int offset) {
		parent.glUniform4fv(location, count, v, offset);
	}

	@Override
	public void glUniform4i(int location, int x, int y, int z, int w) {
		parent.glUniform4i(location, x, y, z, w);
	}

	@Override
	public void glUniform4iv(int location, int count, IntBuffer v) {
		parent.glUniform4iv(location, count, v);
	}

	@Override
	public void glUniform4iv(int location, int count, int[] v, int offset) {
		parent.glUniform4iv(location, count, v, offset);
	}

	@Override
	public void glUniformMatrix2fv(int location, int count, boolean transpose, FloatBuffer value) {
		parent.glUniformMatrix2fv(location, count, transpose, value);
	}

	@Override
	public void glUniformMatrix2fv(int location, int count, boolean transpose, float[] value, int offset) {
		parent.glUniformMatrix2fv(location, count, transpose, value, offset);
	}

	@Override
	public void glUniformMatrix3fv(int location, int count, boolean transpose, FloatBuffer value) {
		parent.glUniformMatrix3fv(location, count, transpose, value);
	}

	@Override
	public void glUniformMatrix3fv(int location, int count, boolean transpose, float[] value, int offset) {
		parent.glUniformMatrix3fv(location, count, transpose, value, offset);
	}

	@Override
	public void glUniformMatrix4fv(int location, int count, boolean transpose, FloatBuffer value) {
		parent.glUniformMatrix4fv(location, count, transpose, value);
	}

	@Override
	public void glUniformMatrix4fv(int location, int count, boolean transpose, float[] value, int offset) {
		parent.glUniformMatrix4fv(location, count, transpose, value, offset);
	}

	@Override
	public void glUseProgram(int program) {
		parent.glUseProgram(program);
	}

	@Override
	public void glValidateProgram(int program) {
		parent.glValidateProgram(program);
	}

	@Override
	public void glVertexAttrib1f(int indx, float x) {
		parent.glVertexAttrib1f(indx, x);
	}

	@Override
	public void glVertexAttrib1fv(int indx, FloatBuffer values) {
		parent.glVertexAttrib1fv(indx, values);
	}

	@Override
	public void glVertexAttrib2f(int indx, float x, float y) {
		parent.glVertexAttrib2f(indx, x, y);
	}

	@Override
	public void glVertexAttrib2fv(int indx, FloatBuffer values) {
		parent.glVertexAttrib2fv(indx, values);
	}

	@Override
	public void glVertexAttrib3f(int indx, float x, float y, float z) {
		parent.glVertexAttrib3f(indx, x, y, z);
	}

	@Override
	public void glVertexAttrib3fv(int indx, FloatBuffer values) {
		parent.glVertexAttrib3fv(indx, values);
	}

	@Override
	public void glVertexAttrib4f(int indx, float x, float y, float z, float w) {
		parent.glVertexAttrib4f(indx, x, y, z, w);
	}

	@Override
	public void glVertexAttrib4fv(int indx, FloatBuffer values) {
		parent.glVertexAttrib4fv(indx, values);
	}

	@Override
	public void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, Buffer ptr) {
		parent.glVertexAttribPointer(indx, size, type, normalized, stride, ptr);
	}

	@Override
	public void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, int ptr) {
		parent.glVertexAttribPointer(indx, size, type, normalized, stride, ptr);
	}
}
