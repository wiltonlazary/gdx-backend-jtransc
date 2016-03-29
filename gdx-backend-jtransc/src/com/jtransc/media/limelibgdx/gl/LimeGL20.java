package com.jtransc.media.limelibgdx.gl;

import com.badlogic.gdx.graphics.GL20;
import com.jtransc.media.limelibgdx.dummy.DummyGL20;
import jtransc.annotation.haxe.HaxeAddMembers;
import jtransc.annotation.haxe.HaxeImports;
import jtransc.annotation.haxe.HaxeMethodBody;

import java.nio.Buffer;
import java.nio.FloatBuffer;
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

	@HaxeMethodBody("GL.blendColor(p0, p1, p2, p3);")
	native public void glBlendColor(float red, float green, float blue, float alpha);

	@HaxeMethodBody("GL.blendEquation(p0);")
	native public void glBlendEquation(int mode);

	@HaxeMethodBody("GL.blendEquationSeparate(p0, p1);")
	native public void glBlendEquationSeparate(int modeRGB, int modeAlpha);

	@HaxeMethodBody("GL.blendFuncSeparate(p0, p1, p2, p3);")
	native public void glBlendFuncSeparate(int srcRGB, int dstRGB, int srcAlpha, int dstAlpha);

	@HaxeMethodBody("GL.bufferData(p0, p1, _convertBuffer(p2), p3);")
	native public void glBufferData(int target, int size, Buffer data, int usage);

	@HaxeMethodBody("GL.bufferSubData(p0, p1, p2, _convertBuffer(p3));")
	native public void glBufferSubData(int target, int offset, int size, Buffer data);

	@HaxeMethodBody("return GL.checkFramebufferStatus(p0);")
	native public int glCheckFramebufferStatus(int target);

	@HaxeMethodBody("GL.compileShader(p0);")
	native public void glCompileShader(int shader);

	@HaxeMethodBody("return GL.createProgram();")
	native public int glCreateProgram();

	@HaxeMethodBody("return GL.createShader(p0);")
	native public int glCreateShader(int type);

	@HaxeMethodBody("GL.deleteBuffer(p0);")
	native public void glDeleteBuffer(int buffer);

	public void glDeleteBuffers(int n, IntBuffer buffers) {
		for (int i = 0; i < n; i++) glDeleteBuffer(buffers.get(i));
	}

	@HaxeMethodBody("GL.deleteFramebuffer(p0);")
	native public void glDeleteFramebuffer(int framebuffer);

	public void glDeleteFramebuffers(int n, IntBuffer framebuffers) {
		for (int i = 0; i < n; i++) glDeleteFramebuffer(framebuffers.get(i));
	}

	@HaxeMethodBody("GL.deleteProgram(p0);")
	native public void glDeleteProgram(int program);

	@HaxeMethodBody("GL.deleteRenderbuffer(p0);")
	native public void glDeleteRenderbuffer(int renderbuffer);

	public void glDeleteRenderbuffers(int n, IntBuffer renderbuffers) {
		for (int i = 0; i < n; i++) glDeleteRenderbuffer(renderbuffers.get(i));
	}

	@HaxeMethodBody("GL.deleteShader(p0);")
	native public void glDeleteShader(int shader);

	@HaxeMethodBody("GL.detachShader(p0, p1);")
	native public void glDetachShader(int program, int shader);

	@HaxeMethodBody("GL.disableVertexAttribArray(p0);")
	native public void glDisableVertexAttribArray(int index);

	@HaxeMethodBody("GL.drawElements(p0, p1, p2, p3);")
	native public void glDrawElements(int mode, int count, int type, int indices);

	@HaxeMethodBody("GL.enableVertexAttribArray(p0);")
	native public void glEnableVertexAttribArray(int index);

	@HaxeMethodBody("GL.framebufferRenderbuffer(p0, p1, p2, p3);")
	native public void glFramebufferRenderbuffer(int target, int attachment, int renderbuffertarget, int renderbuffer);

	@HaxeMethodBody("GL.framebufferTexture2D(p0, p1, p2, p3, p4);")
	native public void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level);

	@HaxeMethodBody("return GL.createBuffer();")
	native public int glGenBuffer();

	public void glGenBuffers(int n, IntBuffer buffers) {
		for (int i = 0; i < n; i++) buffers.put(i, glGenBuffer());
	}

	@HaxeMethodBody("GL.generateMipmap(p0);")
	native public void glGenerateMipmap(int target);

	@HaxeMethodBody("return GL.createFramebuffer();")
	native public int glGenFramebuffer();

	public void glGenFramebuffers(int n, IntBuffer framebuffers) {
		for (int i = 0; i < n; i++) framebuffers.put(i, glGenFramebuffer());
	}

	@HaxeMethodBody("return GL.createRenderbuffer();")
	native public int glGenRenderbuffer();

	public void glGenRenderbuffers(int n, IntBuffer renderbuffers) {
		for (int i = 0; i < n; i++) renderbuffers.put(i, glGenRenderbuffer());
	}

	@HaxeMethodBody("return HaxeNatives.str(GL.getActiveAttrib(p0, p1, p2, _convertBuffer(p3)));")
	native public String glGetActiveAttrib(int program, int index, IntBuffer size, Buffer type);

	@HaxeMethodBody("return HaxeNatives.str(GL.getActiveUniform(p0, p1, p2, p3));")
	native public String glGetActiveUniform(int program, int index, IntBuffer size, Buffer type);

	@HaxeMethodBody("GL.getAttachedShaders(p0, p1, p2, p3);")
	native public void glGetAttachedShaders(int program, int maxcount, Buffer count, IntBuffer shaders);

	@HaxeMethodBody("return GL.getAttribLocation(p0, p1._str);")
	native public int glGetAttribLocation(int program, String name);

	@HaxeMethodBody("GL.getBooleanv(p0, p1);")
	native public void glGetBooleanv(int pname, Buffer params);

	@HaxeMethodBody("GL.getBufferParameteriv(p0, p1, p2);")
	native public void glGetBufferParameteriv(int target, int pname, IntBuffer params);

	@HaxeMethodBody("GL.getFloatv(p0, p1);")
	native public void glGetFloatv(int pname, FloatBuffer params);

	@HaxeMethodBody("GL.getFramebufferAttachmentParameter(p0, p1, p2, p3);")
	native public void glGetFramebufferAttachmentParameteriv(int target, int attachment, int pname, IntBuffer params);

	@HaxeMethodBody("GL.getProgramiv(p0, p1, p2);")
	native public void glGetProgramiv(int program, int pname, IntBuffer params);

	@HaxeMethodBody("return GL.getProgramInfoLog(p0);")
	native public String glGetProgramInfoLog(int program);

	@HaxeMethodBody("GL.getRenderbufferParameteriv(p0, p1, p2);")
	native public void glGetRenderbufferParameteriv(int target, int pname, IntBuffer params);

	@HaxeMethodBody("GL.getShaderiv(p0, p1, p2);")
	native public void glGetShaderiv(int shader, int pname, IntBuffer params);

	@HaxeMethodBody("return GL.getShaderInfoLog(p0);")
	native public String glGetShaderInfoLog(int shader);

	@HaxeMethodBody("GL.getShaderPrecisionFormat(p0, p1, p2, p3);")
	native public void glGetShaderPrecisionFormat(int shadertype, int precisiontype, IntBuffer range, IntBuffer precision);

	@HaxeMethodBody("GL.getTexParameter(p0, p1, p2);")
	native public void glGetTexParameterfv(int target, int pname, FloatBuffer params);

	@HaxeMethodBody("GL.getTexParameter(p0, p1, p2);")
	native public void glGetTexParameteriv(int target, int pname, IntBuffer params);

	@HaxeMethodBody("GL.glGetUniformfv(p0, p1, p2)")
	native public void glGetUniformfv(int program, int location, FloatBuffer params);

	@HaxeMethodBody("GL.glGetUniformiv(p0, p1, p2);")
	native public void glGetUniformiv(int program, int location, IntBuffer params);

	@HaxeMethodBody("return GL.getUniformLocation(p0, p1);")
	native public int glGetUniformLocation(int program, String name);

	@HaxeMethodBody("GL.getVertexAttrib(p0, p1, p2);")
	native public void glGetVertexAttribfv(int index, int pname, FloatBuffer params);

	@HaxeMethodBody("GL.getVertexAttrib(p0, p1, p2);")
	native public void glGetVertexAttribiv(int index, int pname, IntBuffer params);

	@HaxeMethodBody("GL.getVertexAttrib(p0, p1, p2);")
	native public void glGetVertexAttribPointerv(int index, int pname, Buffer pointer);

	@HaxeMethodBody("return GL.isBuffer(buffer);")
	native public boolean glIsBuffer(int buffer);

	@HaxeMethodBody("return GL.isEnabled(p0);")
	native public boolean glIsEnabled(int cap);

	@HaxeMethodBody("return GL.isFramebuffer(p0);")
	native public boolean glIsFramebuffer(int framebuffer);

	@HaxeMethodBody("return GL.isProgram(p0);")
	native public boolean glIsProgram(int program);

	@HaxeMethodBody("return GL.isRenderbuffer(p0);")
	native public boolean glIsRenderbuffer(int renderbuffer);

	@HaxeMethodBody("return GL.isShader(p0);")
	native public boolean glIsShader(int shader);

	@HaxeMethodBody("return GL.isTexture(p0);")
	native public boolean glIsTexture(int texture);

	@HaxeMethodBody("GL.linkProgram(p0);")
	native public void glLinkProgram(int program);

	@HaxeMethodBody("GL.releaseShaderCompiler();")
	native public void glReleaseShaderCompiler();

	@HaxeMethodBody("GL.renderbufferStorage(p0, p1, p2, p3);")
	native public void glRenderbufferStorage(int target, int internalformat, int width, int height);

	@HaxeMethodBody("GL.sampleCoverage(p0, p1);")
	native public void glSampleCoverage(float value, boolean invert);

	@HaxeMethodBody("GL.shaderBinary(p0, p1, p2, p3, p4);")
	native public void glShaderBinary(int n, IntBuffer shaders, int binaryformat, Buffer binary, int length);

	@HaxeMethodBody("GL.shaderSource(p0, p1);")
	native public void glShaderSource(int shader, String string);

	@HaxeMethodBody("GL.stencilFuncSeparate(p0, p1, p2, p3);")
	native public void glStencilFuncSeparate(int face, int func, int ref, int mask);

	@HaxeMethodBody("GL.stencilMaskSeparate(p0, p1);")
	native public void glStencilMaskSeparate(int face, int mask);

	@HaxeMethodBody("GL.stencilOpSeparate(p0, p1, p2, p3);")
	native public void glStencilOpSeparate(int face, int fail, int zfail, int zpass);

	@HaxeMethodBody("GL.texParameterf(p0, p1, p2);")
	native public void glTexParameterfv(int target, int pname, FloatBuffer params);

	@HaxeMethodBody("GL.texParameteri(p0, p1, p2);")
	native public void glTexParameteri(int target, int pname, int param);

	@HaxeMethodBody("GL.texParameteri(p0, p1, p2);")
	native public void glTexParameteriv(int target, int pname, IntBuffer params);

	@HaxeMethodBody("GL.useProgram(p0);")
	native public void glUseProgram(int program);

	@HaxeMethodBody("GL.validateProgram(p0);")
	native public void glValidateProgram(int program);

	@HaxeMethodBody("GL.uniform1f(p0, p1);")
	native public void glUniform1f(int location, float x);

	@HaxeMethodBody("GL.uniform1fv(p0, p1, p2);")
	native public void glUniform1fv(int location, int count, FloatBuffer v);

	@HaxeMethodBody("GL.uniform1fv(p0, p1, p2, p3);")
	native public void glUniform1fv(int location, int count, float[] v, int offset);

	@HaxeMethodBody("GL.uniform1i(p0, p1);")
	native public void glUniform1i(int location, int x);

	@HaxeMethodBody("GL.uniform1iv(p0, p1, p2);")
	native public void glUniform1iv(int location, int count, IntBuffer v);

	@HaxeMethodBody("GL.uniform1iv(p0, p1, p2, p3);")
	native public void glUniform1iv(int location, int count, int[] v, int offset);

	@HaxeMethodBody("GL.uniform2f(p0, p1, p2);")
	native public void glUniform2f(int location, float x, float y);

	@HaxeMethodBody("GL.uniform2fv(p0, p1, p2);")
	native public void glUniform2fv(int location, int count, FloatBuffer v);

	@HaxeMethodBody("GL.uniform2fv(p0, p1, p2, p3);")
	native public void glUniform2fv(int location, int count, float[] v, int offset);

	@HaxeMethodBody("GL.uniform2i(p0, p1, p2);")
	native public void glUniform2i(int location, int x, int y);

	@HaxeMethodBody("GL.uniform2iv(p0, p1, p2);")
	native public void glUniform2iv(int location, int count, IntBuffer v);

	@HaxeMethodBody("GL.uniform2iv(p0, p1, p2, p3);")
	native public void glUniform2iv(int location, int count, int[] v, int offset);

	@HaxeMethodBody("GL.uniform3f(p0, p1, p2, p3);")
	native public void glUniform3f(int location, float x, float y, float z);

	@HaxeMethodBody("GL.uniform3fv(p0, p1, p2);")
	native public void glUniform3fv(int location, int count, FloatBuffer v);

	@HaxeMethodBody("GL.uniform3fv(p0, p1, p2, p3);")
	native public void glUniform3fv(int location, int count, float[] v, int offset);

	@HaxeMethodBody("GL.uniform3i(p0, p1, p2, p3);")
	native public void glUniform3i(int location, int x, int y, int z);

	@HaxeMethodBody("GL.uniform3iv(p0, p1, p2);")
	native public void glUniform3iv(int location, int count, IntBuffer v);

	@HaxeMethodBody("GL.uniform3iv(p0, p1, p2, p3);")
	native public void glUniform3iv(int location, int count, int[] v, int offset);

	@HaxeMethodBody("GL.uniform4f(p0, p1, p2, p3, p4);")
	native public void glUniform4f(int location, float x, float y, float z, float w);

	@HaxeMethodBody("GL.uniform4fv(p0, p1, p2);")
	native public void glUniform4fv(int location, int count, FloatBuffer v);

	@HaxeMethodBody("GL.uniform4fv(p0, p1, p2, p3);")
	native public void glUniform4fv(int location, int count, float[] v, int offset);

	@HaxeMethodBody("GL.uniform4i(p0, p1, p2, p3, p4);")
	native public void glUniform4i(int location, int x, int y, int z, int w);

	@HaxeMethodBody("GL.uniform4iv(p0, p1, p2);")
	native public void glUniform4iv(int location, int count, IntBuffer v);

	@HaxeMethodBody("GL.uniform4iv(p0, p1, p2, p3);")
	native public void glUniform4iv(int location, int count, int[] v, int offset);

	@HaxeMethodBody("GL.uniformMatrix2fv(p0, p1, p2, p3);")
	native public void glUniformMatrix2fv(int location, int count, boolean transpose, FloatBuffer value);

	@HaxeMethodBody("GL.uniformMatrix2fv(p0, p1, p2, p3, p4);")
	native public void glUniformMatrix2fv(int location, int count, boolean transpose, float[] value, int offset);

	@HaxeMethodBody("GL.uniformMatrix3fv(p0, p1, p2, p3);")
	native public void glUniformMatrix3fv(int location, int count, boolean transpose, FloatBuffer value);

	@HaxeMethodBody("GL.uniformMatrix3fv(p0, p1, p2, p3, p4);")
	native public void glUniformMatrix3fv(int location, int count, boolean transpose, float[] value, int offset);

	@HaxeMethodBody("GL.uniformMatrix4fv(p0, p1, p2, p3);")
	native public void glUniformMatrix4fv(int location, int count, boolean transpose, FloatBuffer value);

	@HaxeMethodBody("GL.uniformMatrix4fv(p0, p1, p2, p3, p4);")
	native public void glUniformMatrix4fv(int location, int count, boolean transpose, float[] value, int offset);

	@HaxeMethodBody("GL.vertexAttrib1f(p0, p1);")
	native public void glVertexAttrib1f(int indx, float x);

	@HaxeMethodBody("GL.vertexAttrib1fv(p0, p1);")
	native public void glVertexAttrib1fv(int indx, FloatBuffer values);

	@HaxeMethodBody("GL.vertexAttrib2f(p0, p1, p2);")
	native public void glVertexAttrib2f(int indx, float x, float y);

	@HaxeMethodBody("GL.vertexAttrib2fv(p0, p1);")
	native public void glVertexAttrib2fv(int indx, FloatBuffer values);

	@HaxeMethodBody("GL.vertexAttrib3f(p0, p1, p2, p3);")
	native public void glVertexAttrib3f(int indx, float x, float y, float z);

	@HaxeMethodBody("GL.vertexAttrib3fv(p0, p1);")
	native public void glVertexAttrib3fv(int indx, FloatBuffer values);

	@HaxeMethodBody("GL.vertexAttrib4f(p0, p1, p2, p3, p4);")
	native public void glVertexAttrib4f(int indx, float x, float y, float z, float w);

	@HaxeMethodBody("GL.vertexAttrib4fv(p0, p1);")
	native public void glVertexAttrib4fv(int indx, FloatBuffer values);

	@HaxeMethodBody("GL.vertexAttribPointer(p0, p1, p2, p3, p4, p5);")
	native public void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, Buffer ptr);

	@HaxeMethodBody("GL.vertexAttribPointer(p0, p1, p2, p3, p4, p5);")
	native public void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, int ptr);
}
