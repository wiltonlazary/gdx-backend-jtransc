package com.jtransc.media.limelibgdx.gl;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.jtransc.annotation.JTranscMethodBody;
import com.jtransc.annotation.haxe.HaxeAddMembers;
import com.jtransc.annotation.haxe.HaxeImports;
import com.jtransc.annotation.haxe.HaxeMethodBody;
import com.jtransc.media.limelibgdx.GL20Ext;
import com.jtransc.media.limelibgdx.dummy.DummyGL20;

import java.nio.*;

// https://github.com/openfl/lime/blob/develop/lime/graphics/opengl/GL.hx
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
	"static private function _buffer(a, size:Int = -1) { return HaxeLimeGdxApplication.convertBuffer(a, size); }",
	"static private function _floatBuffer(a, size:Int = -1) { return HaxeLimeGdxApplication.convertFloatBuffer(a, size); }",
	"static private function _intBuffer(a, size:Int = -1) { return HaxeLimeGdxApplication.convertIntBuffer(a, size); }",
	"static private function _intArray(a, offset:Int, size:Int) { return HaxeLimeGdxApplication.convertIntArray(a, offset, size); }",
	"static private function _floatArray(a, offset:Int, size:Int) { return HaxeLimeGdxApplication.convertFloatArray(a, offset, size); }",
	// GLTexture
	"public var lastId = 1000;",
	"public var textures = new Map<Int, GLTexture>();",
	"public var programs = new Map<Int, GLProgram>();",
	"public var shaders = new Map<Int, GLShader>();",
	"public var buffers = new Map<Int, GLBuffer>();",
	"public var frameBuffers = new Map<Int, GLFramebuffer>();",
	"public var renderBuffers = new Map<Int, GLRenderbuffer>();",
	"public var uniformLocations = new Map<Int, GLUniformLocation>();",
})
public class LimeGL20 extends DummyGL20 implements GL20Ext {
	static public int bindedTextureId = 0;

	static public LimeGL20 create() {
		return new LimeGL20();
	}

	public LimeGL20() {
		_init();
	}

	@JTranscMethodBody(target = "js", value = {
		"this.lastId = 1000;",
		"this.textures = new Map();",
		"this.programs = new Map();",
		"this.shaders = new Map();",
		"this.buffers = new Map();",
		"this.frameBuffers = new Map();",
		"this.renderBuffers = new Map();",
		"this.uniformLocations = new Map();",
	})
	private void _init() {
	}

	@HaxeMethodBody("GL.activeTexture(p0);")
	@JTranscMethodBody(target = "js", value = "GL.activeTexture(p0);")
	native public void glActiveTexture(int texture);

	@HaxeMethodBody("{% SFIELD com.jtransc.media.limelibgdx.gl.LimeGL20:bindedTextureId %} = p1; GL.bindTexture(p0, this.textures.get(p1));")
	@JTranscMethodBody(target = "js", value = "{% SFIELD com.jtransc.media.limelibgdx.gl.LimeGL20:bindedTextureId %} = p1; GL.bindTexture(p0, this.textures.get(p1));")
	native public void glBindTexture(int target, int texture);

	@HaxeMethodBody("GL.blendFunc(p0, p1);")
	@JTranscMethodBody(target = "js", value = "GL.blendFunc(p0, p1);")
	native public void glBlendFunc(int sfactor, int dfactor);

	@HaxeMethodBody("GL.clear(p0);")
	@JTranscMethodBody(target = "js", value = "GL.clear(p0);")
	native private void _glClear(int mask);

	public void glClear(int mask) {
		_glClear(mask);
	}

	@HaxeMethodBody("GL.clearColor(p0, p1, p2, p3);")
	@JTranscMethodBody(target = "js", value = "GL.clearColor(p0, p1, p2, p3);")
	native public void glClearColor(float red, float green, float blue, float alpha);

	@HaxeMethodBody("GL.clearDepthf(p0);")
	@JTranscMethodBody(target = "js", value = "GL.clearDepthf(p0);")
	native public void glClearDepthf(float depth);

	@HaxeMethodBody("GL.clearStencil(p0);")
	@JTranscMethodBody(target = "js", value = "GL.clearStencil(p0);")
	native public void glClearStencil(int s);

	@HaxeMethodBody("GL.colorMask(p0, p1, p2, p3);")
	@JTranscMethodBody(target = "js", value = "GL.colorMask(p0, p1, p2, p3);")
	native public void glColorMask(boolean red, boolean green, boolean blue, boolean alpha);

	@HaxeMethodBody("GL.compressedTexImage2D(p0, p1, p2, p3, p4, p5, p6, _buffer(p7, p6));")
	@JTranscMethodBody(target = "js", value = "GL.compressedTexImage2D(p0, p1, p2, p3, p4, p5, p6, _buffer(p7, p6));")
	native public void glCompressedTexImage2D(int target, int level, int internalformat, int width, int height, int border, int imageSize, Buffer data);

	@HaxeMethodBody("GL.compressedTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, _buffer(p8, p7));")
	@JTranscMethodBody(target = "js", value = "GL.compressedTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, _buffer(p8, p7));")
	native public void glCompressedTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int imageSize, Buffer data);

	@HaxeMethodBody("GL.copyTexImage2D(p0, p1, p2, p3, p4, p5, p6, p7);")
	@JTranscMethodBody(target = "js", value = "GL.copyTexImage2D(p0, p1, p2, p3, p4, p5, p6, p7);")
	native public void glCopyTexImage2D(int target, int level, int internalformat, int x, int y, int width, int height, int border);

	@HaxeMethodBody("GL.copyTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7);")
	@JTranscMethodBody(target = "js", value = "GL.copyTexSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7);")
	native public void glCopyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y, int width, int height);

	@HaxeMethodBody("GL.cullFace(p0);")
	@JTranscMethodBody(target = "js", value = "GL.cullFace(p0);")
	native public void glCullFace(int mode);

	public void glDeleteTextures(int n, IntBuffer textures) {
		for (int i = 0; i < n; i++) glDeleteTexture(textures.get(i));
	}

	@HaxeMethodBody("GL.deleteTexture(this.textures.get(p0)); this.textures.remove(p0);")
	@JTranscMethodBody(target = "js", value = "GL.deleteTexture(this.textures.get(p0)); this.textures.remove(p0);")
	native public void glDeleteTexture(int texture);

	@HaxeMethodBody("GL.depthFunc(p0);")
	@JTranscMethodBody(target = "js", value = "GL.depthFunc(p0);")
	native public void glDepthFunc(int func);

	@HaxeMethodBody("GL.depthMask(p0);")
	@JTranscMethodBody(target = "js", value = "GL.depthMask(p0);")
	native public void glDepthMask(boolean flag);

	@HaxeMethodBody("GL.depthRange(p0, p1);")
	@JTranscMethodBody(target = "js", value = "GL.depthRange(p0, p1);")
	native public void glDepthRangef(float zNear, float zFar);

	@HaxeMethodBody("GL.disable(p0);")
	@JTranscMethodBody(target = "js", value = "GL.disable(p0);")
	native public void glDisable(int cap);

	@HaxeMethodBody("GL.drawArrays(p0, p1, p2);")
	@JTranscMethodBody(target = "js", value = "GL.drawArrays(p0, p1, p2);")
	native public void glDrawArrays(int mode, int first, int count);

	//private int tempIndicesBuffer = -1;
	public void glDrawElements(int mode, int count, int type, Buffer indices) {
		int multiply = 1;
		if (indices instanceof ShortBuffer) multiply = 2;
		else if (indices instanceof IntBuffer) multiply = 4;
		_glDrawElements(mode, count, type, indices.position() * multiply);
	}

	@HaxeMethodBody("GL.drawElements(p0, p1, p2, p3);")
	@JTranscMethodBody(target = "js", value = "GL.drawElements(p0, p1, p2, p3);")
	native private void _glDrawElements(int mode, int count, int type, int offset);

	@HaxeMethodBody("GL.enable(p0);")
	@JTranscMethodBody(target = "js", value = "GL.enable(p0);")
	native public void glEnable(int cap);

	@HaxeMethodBody("GL.finish();")
	@JTranscMethodBody(target = "js", value = "GL.finish();")
	native public void glFinish();

	@HaxeMethodBody("GL.flush();")
	@JTranscMethodBody(target = "js", value = "GL.flush();")
	native public void glFlush();

	@HaxeMethodBody("GL.frontFace(p0);")
	@JTranscMethodBody(target = "js", value = "GL.frontFace(p0);")
	native public void glFrontFace(int mode);

	public void glGenTextures(int n, IntBuffer textures) {
		for (int i = 0; i < n; i++) textures.put(i, glGenTexture());
	}

	@HaxeMethodBody("var id = this.lastId++; var tex = GL.createTexture(); this.textures.set(id, tex); return id;")
	@JTranscMethodBody(target = "js", value = "var id = this.lastId++; var tex = GL.createTexture(); this.textures.set(id, tex); return id;")
	native public int glGenTexture();

	@HaxeMethodBody("return GL.getError();")
	@JTranscMethodBody(target = "js", value = "return GL.getError();")
	native public int glGetError();

	@HaxeMethodBody("return GL.getParameter(p0);")
	@JTranscMethodBody(target = "js", value = "return GL.getParameter(p0);")
	native private int glGetInteger(int pname);

	private int glGetInteger2(int pname) {
		switch (pname) {
			case GL20.GL_TEXTURE_BINDING_2D:
				return bindedTextureId;
		}
		return glGetInteger(pname);
	}

	@HaxeMethodBody("return GL.getParameter(p0);")
	@JTranscMethodBody(target = "js", value = "return GL.getParameter(p0);")
	native private boolean glGetBoolean(int pname);

	@HaxeMethodBody("return GL.getParameter(p0);")
	@JTranscMethodBody(target = "js", value = "return GL.getParameter(p0);")
	native private float glGetFloat(int pname);

	public void glGetIntegerv(int pname, IntBuffer params) {
		params.put(0, glGetInteger2(pname));
	}

	@HaxeMethodBody("return N.str(cast(GL.getParameter(p0), String));")
	@JTranscMethodBody(target = "js", value = "return N.str(cast(GL.getParameter(p0), String));")
	native public String glGetString(int name);

	@HaxeMethodBody("GL.hint(p0, p1);")
	@JTranscMethodBody(target = "js", value = "GL.hint(p0, p1);")
	native public void glHint(int target, int mode);

	@HaxeMethodBody("GL.lineWidth(p0);")
	@JTranscMethodBody(target = "js", value = "GL.lineWidth(p0);")
	native public void glLineWidth(float width);

	@HaxeMethodBody("GL.pixelStorei(p0, p1);")
	@JTranscMethodBody(target = "js", value = "GL.pixelStorei(p0, p1);")
	native public void glPixelStorei(int pname, int param);

	@HaxeMethodBody("GL.polygonOffset(p0, p1);")
	@JTranscMethodBody(target = "js", value = "GL.polygonOffset(p0, p1);")
	native public void glPolygonOffset(float factor, float units);

	@HaxeMethodBody("GL.readPixels(p0, p1, p2, p3, p4, p5, _buffer(p6));")
	@JTranscMethodBody(target = "js", value = "GL.readPixels(p0, p1, p2, p3, p4, p5, _buffer(p6));")
	native public void glReadPixels(int x, int y, int width, int height, int format, int type, Buffer pixels);

	@HaxeMethodBody("GL.scissor(p0, p1, p2, p3);")
	@JTranscMethodBody(target = "js", value = "GL.scissor(p0, p1, p2, p3);")
	native public void glScissor(int x, int y, int width, int height);

	@HaxeMethodBody("GL.stencilFunc(p0, p1, p2);")
	@JTranscMethodBody(target = "js", value = "GL.stencilFunc(p0, p1, p2);")
	native public void glStencilFunc(int func, int ref, int mask);

	@HaxeMethodBody("GL.stencilMask(p0);")
	@JTranscMethodBody(target = "js", value = "GL.stencilMask(p0);")
	native public void glStencilMask(int mask);

	@HaxeMethodBody("GL.stencilOp(p0, p1, p2);")
	@JTranscMethodBody(target = "js", value = "GL.stencilOp(p0, p1, p2);")
	native public void glStencilOp(int fail, int zfail, int zpass);

	@HaxeMethodBody("GL.texImage2D(p0, p1, p2, p3, p4, p5, p6, p7, _buffer(p8));")
	@JTranscMethodBody(target = "js", value = "GL.texImage2D(p0, p1, p2, p3, p4, p5, p6, p7, _arrayCopyRev(_buffer(p8)));")
	native private void _glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, Buffer pixels);

	public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, Buffer pixels) {
		System.out.println("glTexImage2D:target=" + target + ",level=" + level + ",internalformat=" + internalformat + ",width=" + width + ",height=" + height + ",border=" + border + ",format=" + format + ",type=" + type + ",pixels=" + pixels.limit());
		_glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
		//_glTexImage2D(target, level, internalformat, 1, 1, border, format, type, ByteBuffer.wrap(new byte[] { -1, 0, 0, -1 }));
	}

	@HaxeMethodBody("GL.texParameterf(p0, p1, p2);")
	@JTranscMethodBody(target = "js", value = "GL.texParameterf(p0, p1, p2);")
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

	@HaxeMethodBody("GL.texSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, _buffer(p8));")
	@JTranscMethodBody(target = "js", value = "GL.texSubImage2D(p0, p1, p2, p3, p4, p5, p6, p7, _buffer(p8));")
	native public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, Buffer pixels);

	@HaxeMethodBody("GL.viewport(p0, p1, p2, p3);")
	@JTranscMethodBody(target = "js", value = "GL.viewport(p0, p1, p2, p3);")
	native private void _glViewport(int x, int y, int width, int height);

	public void glViewport(int x, int y, int width, int height) {
		//System.out.printf("glViewport(%d, %d, %d, %d)\n", x, y, width, height);
		_glViewport(x, y, width, height);
	}

	@HaxeMethodBody("GL.attachShader(this.programs.get(p0), this.shaders.get(p1));")
	@JTranscMethodBody(target = "js", value = "GL.attachShader(this.programs.get(p0), this.shaders.get(p1));")
	native public void glAttachShader(int program, int shader);

	@HaxeMethodBody("GL.bindAttribLocation(this.programs.get(p0), p1, N.istr(p2));")
	@JTranscMethodBody(target = "js", value = "GL.bindAttribLocation(this.programs.get(p0), p1, N.istr(p2));")
	native public void glBindAttribLocation(int program, int index, String name);

	@HaxeMethodBody("GL.bindBuffer(p0, this.buffers.get(p1));")
	@JTranscMethodBody(target = "js", value = "GL.bindBuffer(p0, this.buffers.get(p1));")
	native public void glBindBuffer(int target, int buffer);

	@HaxeMethodBody("GL.bindRenderbuffer(p0, this.renderBuffers.get(p1));")
	@JTranscMethodBody(target = "js", value = "GL.bindRenderbuffer(p0, this.renderBuffers.get(p1));")
	native public void glBindRenderbuffer(int target, int renderbuffer);

	@HaxeMethodBody("GL.blendColor(p0, p1, p2, p3);")
	@JTranscMethodBody(target = "js", value = "GL.blendColor(p0, p1, p2, p3);")
	native public void glBlendColor(float red, float green, float blue, float alpha);

	@HaxeMethodBody("GL.blendEquation(p0);")
	@JTranscMethodBody(target = "js", value = "GL.blendEquation(p0);")
	native public void glBlendEquation(int mode);

	@HaxeMethodBody("GL.blendEquationSeparate(p0, p1);")
	@JTranscMethodBody(target = "js", value = "GL.blendEquationSeparate(p0, p1);")
	native public void glBlendEquationSeparate(int modeRGB, int modeAlpha);

	@HaxeMethodBody("GL.blendFuncSeparate(p0, p1, p2, p3);")
	@JTranscMethodBody(target = "js", value = "GL.blendFuncSeparate(p0, p1, p2, p3);")
	native public void glBlendFuncSeparate(int srcRGB, int dstRGB, int srcAlpha, int dstAlpha);

	@HaxeMethodBody("GL.bufferData(p0, p1, _buffer(p2, p1), p3);")
	@JTranscMethodBody(target = "js", value = "GL.bufferData(p0, p1, _buffer(p2, p1), p3);")
	native public void glBufferData(int target, int size, Buffer data, int usage);

	@HaxeMethodBody("GL.bufferSubData(p0, p1, p2, _buffer(p3, p2));")
	@JTranscMethodBody(target = "js", value = "GL.bufferSubData(p0, p1, p2, _buffer(p3, p2));")
	native public void glBufferSubData(int target, int offset, int size, Buffer data);

	@HaxeMethodBody("GL.compileShader(this.shaders.get(p0));")
	@JTranscMethodBody(target = "js", value = "GL.compileShader(this.shaders.get(p0));")
	native public void glCompileShader(int shader);

	@HaxeMethodBody("var id = this.lastId++; var program = GL.createProgram(); this.programs.set(id, program); return id;")
	@JTranscMethodBody(target = "js", value = "var id = this.lastId++; var program = GL.createProgram(); this.programs.set(id, program); return id;")
	native public int glCreateProgram();

	@HaxeMethodBody("var id = this.lastId++; var shader = GL.createShader(p0); this.shaders.set(id, shader); return id;")
	@JTranscMethodBody(target = "js", value = "var id = this.lastId++; var shader = GL.createShader(p0); this.shaders.set(id, shader); return id;")
	native public int glCreateShader(int type);

	@HaxeMethodBody("GL.deleteBuffer(this.buffers.get(p0));")
	@JTranscMethodBody(target = "js", value = "GL.deleteBuffer(this.buffers.get(p0));")
	native public void glDeleteBuffer(int buffer);

	public void glDeleteBuffers(int n, IntBuffer buffers) {
		for (int i = 0; i < n; i++) glDeleteBuffer(buffers.get(i));
	}

	@HaxeMethodBody("GL.deleteProgram(this.programs.get(p0)); this.programs.remove(p0);")
	@JTranscMethodBody(target = "js", value = "GL.deleteProgram(this.programs.get(p0)); this.programs.remove(p0);")
	native public void glDeleteProgram(int program);

	@HaxeMethodBody("GL.deleteRenderbuffer(this.renderBuffers.get(p0)); this.renderBuffers.remove(p0);")
	@JTranscMethodBody(target = "js", value = "GL.deleteRenderbuffer(this.renderBuffers.get(p0)); this.renderBuffers.remove(p0);")
	native public void glDeleteRenderbuffer(int renderbuffer);

	public void glDeleteRenderbuffers(int n, IntBuffer renderbuffers) {
		for (int i = 0; i < n; i++) glDeleteRenderbuffer(renderbuffers.get(i));
	}

	@HaxeMethodBody("GL.deleteShader(this.shaders.get(p0)); this.shaders.remove(p0);")
	@JTranscMethodBody(target = "js", value = "GL.deleteShader(this.shaders.get(p0)); this.shaders.remove(p0);")
	native public void glDeleteShader(int shader);

	@HaxeMethodBody("GL.detachShader(this.programs.get(p0), this.shaders.get(p1));")
	@JTranscMethodBody(target = "js", value = "GL.detachShader(this.programs.get(p0), this.shaders.get(p1));")
	native public void glDetachShader(int program, int shader);

	@HaxeMethodBody("GL.disableVertexAttribArray(p0);")
	@JTranscMethodBody(target = "js", value = "GL.disableVertexAttribArray(p0);")
	native public void glDisableVertexAttribArray(int index);

	@HaxeMethodBody("GL.drawElements(p0, p1, p2, p3);")
	@JTranscMethodBody(target = "js", value = "GL.drawElements(p0, p1, p2, p3);")
	native public void glDrawElements(int mode, int count, int type, int indices);

	@HaxeMethodBody("GL.enableVertexAttribArray(p0);")
	@JTranscMethodBody(target = "js", value = "GL.enableVertexAttribArray(p0);")
	native public void glEnableVertexAttribArray(int index);

	///////////////////////////////////////////////////////
	// FrameBuffer
	///////////////////////////////////////////////////////
	@HaxeMethodBody("var id = this.lastId++; var frameBuffer = GL.createFramebuffer(); this.frameBuffers.set(id, frameBuffer); return id;")
	@JTranscMethodBody(target = "js", value = "var id = this.lastId++; var frameBuffer = GL.createFramebuffer(); this.frameBuffers.set(id, frameBuffer); return id;")
	native public int glGenFramebuffer();

	@HaxeMethodBody("return GL.isFramebuffer(this.frameBuffers.get(p0));")
	@JTranscMethodBody(target = "js", value = "return GL.isFramebuffer(this.frameBuffers.get(p0));")
	native public boolean glIsFramebuffer(int framebuffer);

	public void glGenFramebuffers(int n, IntBuffer framebuffers) {
		for (int i = 0; i < n; i++) framebuffers.put(i, glGenFramebuffer());
	}

	@HaxeMethodBody("GL.bindFramebuffer(p0, this.frameBuffers.get(p1));")
	@JTranscMethodBody(target = "js", value = "GL.bindFramebuffer(p0, this.frameBuffers.get(p1));")
	native public void glBindFramebuffer(int target, int framebuffer);

	@HaxeMethodBody("return GL.checkFramebufferStatus(p0);")
	@JTranscMethodBody(target = "js", value = "return GL.checkFramebufferStatus(p0);")
	native public int glCheckFramebufferStatus(int target);

	@HaxeMethodBody("GL.deleteFramebuffer(frameBuffers.get(p0)); frameBuffers.remove(p0);")
	@JTranscMethodBody(target = "js", value = "GL.deleteFramebuffer(frameBuffers.get(p0)); frameBuffers.remove(p0);")
	native public void glDeleteFramebuffer(int framebuffer);

	public void glDeleteFramebuffers(int n, IntBuffer framebuffers) {
		for (int i = 0; i < n; i++) glDeleteFramebuffer(framebuffers.get(i));
	}

	@HaxeMethodBody("GL.framebufferRenderbuffer(p0, p1, p2, renderBuffers.get(p3));")
	@JTranscMethodBody(target = "js", value = "GL.framebufferRenderbuffer(p0, p1, p2, renderBuffers.get(p3));")
	native public void glFramebufferRenderbuffer(int target, int attachment, int renderbuffertarget, int renderbuffer);

	@HaxeMethodBody("GL.framebufferTexture2D(p0, p1, p2, this.textures.get(p3), p4);")
	@JTranscMethodBody(target = "js", value = "GL.framebufferTexture2D(p0, p1, p2, this.textures.get(p3), p4);")
	native public void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level);

	@HaxeMethodBody("return GL.getFramebufferAttachmentParameter(p0, p1, p2);")
	@JTranscMethodBody(target = "js", value = "return GL.getFramebufferAttachmentParameter(p0, p1, p2);")
	native private int glGetFramebufferAttachmentParameter(int target, int attachment, int pname);

	public void glGetFramebufferAttachmentParameteriv(int target, int attachment, int pname, IntBuffer params) {
		params.put(0, glGetFramebufferAttachmentParameter(target, attachment, pname));
	}

	@HaxeMethodBody("var id = this.lastId++; var buffer = GL.createBuffer(); this.buffers.set(id, buffer); return id;")
	@JTranscMethodBody(target = "js", value = "var id = this.lastId++; var buffer = GL.createBuffer(); this.buffers.set(id, buffer); return id;")
	native public int glGenBuffer();

	public void glGenBuffers(int n, IntBuffer buffers) {
		for (int i = 0; i < n; i++) buffers.put(i, glGenBuffer());
	}

	@HaxeMethodBody("GL.generateMipmap(p0);")
	@JTranscMethodBody(target = "js", value = "GL.generateMipmap(p0);")
	native public void glGenerateMipmap(int target);

	@HaxeMethodBody("var id = this.lastId++; var renderBuffer = GL.createRenderbuffer(); this.renderBuffers.set(id, renderBuffer); return id;")
	@JTranscMethodBody(target = "js", value = "var id = this.lastId++; var renderBuffer = GL.createRenderbuffer(); this.renderBuffers.set(id, renderBuffer); return id;")
	native public int glGenRenderbuffer();

	public void glGenRenderbuffers(int n, IntBuffer renderbuffers) {
		for (int i = 0; i < n; i++) renderbuffers.put(i, glGenRenderbuffer());
	}

	@HaxeMethodBody("return N.str(GL.getActiveAttrib(this.programs.get(p0), p1).name);")
	@JTranscMethodBody(target = "js", value = "return N.str(GL.getActiveAttrib(this.programs.get(p0), p1).name);")
	native private String glGetActiveAttribName(int program, int index);

	@HaxeMethodBody("return GL.getActiveAttrib(this.programs.get(p0), p1).size;")
	@JTranscMethodBody(target = "js", value = "return GL.getActiveAttrib(this.programs.get(p0), p1).size;")
	native private int glGetActiveAttribSize(int program, int index);

	@HaxeMethodBody("return GL.getActiveAttrib(this.programs.get(p0), p1).type;")
	@JTranscMethodBody(target = "js", value = "return GL.getActiveAttrib(this.programs.get(p0), p1).type;")
	native private int glGetActiveAttribType(int program, int index);

	@HaxeMethodBody("return N.str(GL.getActiveUniform(this.programs.get(p0), p1).name);")
	@JTranscMethodBody(target = "js", value = "return N.str(GL.getActiveUniform(this.programs.get(p0), p1).name);")
	native private String glGetActiveUniformName(int program, int index);

	@HaxeMethodBody("return GL.getActiveUniform(this.programs.get(p0), p1).size;")
	@JTranscMethodBody(target = "js", value = "return GL.getActiveUniform(this.programs.get(p0), p1).size;")
	native private int glGetActiveUniformSize(int program, int index);

	@HaxeMethodBody("return GL.getActiveUniform(this.programs.get(p0), p1).type;")
	@JTranscMethodBody(target = "js", value = "return GL.getActiveUniform(this.programs.get(p0), p1).type;")
	native private int glGetActiveUniformType(int program, int index);

	public String glGetActiveAttrib(int program, int index, IntBuffer size, Buffer type) {
		size.put(glGetActiveAttribSize(program, index));
		if (type instanceof IntBuffer) ((IntBuffer) type).put(glGetActiveAttribType(program, index));
		return glGetActiveAttribName(program, index);
	}

	public String glGetActiveUniform(int program, int index, IntBuffer size, Buffer type) {
		size.put(glGetActiveUniformSize(program, index));
		if (type instanceof IntBuffer) ((IntBuffer) type).put(glGetActiveUniformType(program, index));
		return glGetActiveUniformName(program, index);
	}

	//@HaxeMethodBody("GL.getAttachedShaders(this.programs.get(p0), p1, _buffer(p2), p3);")
	@JTranscMethodBody(target = "js", value = "GL.getAttachedShaders(this.programs.get(p0), p1, _buffer(p2), p3);")
	native public void glGetAttachedShaders(int program, int maxcount, Buffer count, IntBuffer shaders);

	@HaxeMethodBody("return GL.getAttribLocation(this.programs.get(p0), N.istr(p1));")
	@JTranscMethodBody(target = "js", value = "return GL.getAttribLocation(this.programs.get(p0), N.istr(p1));")
	native private int _glGetAttribLocation(int program, String name);

	public int glGetAttribLocation(int program, String name) {
		//JTranscSystem.debugger();
		return _glGetAttribLocation(program, name);
	}

	public void glGetBooleanv(int pname, Buffer params) {
		((ByteBuffer) params).putInt(0, glGetBoolean(pname) ? 1 : 0);
	}

	// 	public static inline function getBufferParameter (target:Int, pname:Int):Int /*Dynamic*/ {

	@HaxeMethodBody("return GL.getBufferParameter(p0, p1);")
	@JTranscMethodBody(target = "js", value = "return GL.getBufferParameter(p0, p1);")
	native private int glGetBufferParameter(int target, int pname);

	public void glGetBufferParameteriv(int target, int pname, IntBuffer params) {
		params.put(0, glGetBufferParameter(target, pname));
	}

	public void glGetFloatv(int pname, FloatBuffer params) {
		params.put(0, glGetFloat(pname));
	}

	@HaxeMethodBody("return GL.getProgramParameter(this.programs.get(p0), p1);")
	@JTranscMethodBody(target = "js", value = "return GL.getProgramParameter(this.programs.get(p0), p1);")
	native private int glGetProgrami(int program, int pname);

	public void glGetProgramiv(int program, int pname, IntBuffer params) {
		params.put(0, glGetProgrami(program, pname));
	}

	@HaxeMethodBody("return N.str(GL.getProgramInfoLog(this.programs.get(p0)));")
	@JTranscMethodBody(target = "js", value = "return N.str(GL.getProgramInfoLog(this.programs.get(p0)));")
	native public String glGetProgramInfoLog(int program);

	@HaxeMethodBody("return GL.getRenderbufferParameter(p0, p1);")
	@JTranscMethodBody(target = "js", value = "return GL.getRenderbufferParameter(p0, p1);")
	native private int glGetRenderbufferParameter(int target, int pname);

	public void glGetRenderbufferParameteriv(int target, int pname, IntBuffer params) {
		params.put(0, glGetRenderbufferParameter(target, pname));
	}

	@HaxeMethodBody("return GL.getShaderParameter(this.shaders.get(p0), p1);")
	@JTranscMethodBody(target = "js", value = "return GL.getShaderParameter(this.shaders.get(p0), p1);")
	native private int glGetShader(int shader, int pname);

	public void glGetShaderiv(int shader, int pname, IntBuffer params) {
		params.put(0, glGetShader(shader, pname));
	}

	@HaxeMethodBody("return N.str(GL.getShaderInfoLog(this.shaders.get(p0)));")
	@JTranscMethodBody(target = "js", value = "return N.str(GL.getShaderInfoLog(this.shaders.get(p0)));")
	native public String glGetShaderInfoLog(int shader);

	@HaxeMethodBody("return GL.getShaderPrecisionFormat(p0, p1).rangeMin;")
	@JTranscMethodBody(target = "js", value = "return GL.getShaderPrecisionFormat(p0, p1).rangeMin;")
	native public int glGetShaderPrecisionFormatMin(int shadertype, int precisiontype);

	@HaxeMethodBody("return GL.getShaderPrecisionFormat(p0, p1).rangeMax;")
	@JTranscMethodBody(target = "js", value = "return GL.getShaderPrecisionFormat(p0, p1).rangeMax;")
	native public int glGetShaderPrecisionFormatMax(int shadertype, int precisiontype);

	@HaxeMethodBody("return GL.getShaderPrecisionFormat(p0, p1).precision;")
	@JTranscMethodBody(target = "js", value = "return GL.getShaderPrecisionFormat(p0, p1).precision;")
	native public int glGetShaderPrecisionFormatPrecision(int shadertype, int precisiontype);

	public void glGetShaderPrecisionFormat(int shadertype, int precisiontype, IntBuffer range, IntBuffer precision) {
		range.put(0, glGetShaderPrecisionFormatMin(shadertype, precisiontype));
		range.put(1, glGetShaderPrecisionFormatMax(shadertype, precisiontype));
		precision.put(0, glGetShaderPrecisionFormatPrecision(shadertype, precisiontype));
	}

	//@HaxeMethodBody("GL.getTexParameter(p0, p1, p2);")
	@JTranscMethodBody(target = "js", value = "GL.getTexParameter(p0, p1, p2);")
	native public void glGetTexParameterfv(int target, int pname, FloatBuffer params);

	//@HaxeMethodBody("GL.getTexParameter(p0, p1, p2);")
	@JTranscMethodBody(target = "js", value = "GL.getTexParameter(p0, p1, p2);")
	native public void glGetTexParameteriv(int target, int pname, IntBuffer params);

	//@HaxeMethodBody("GL.getUniformfv(p0, p1, p2);")
	@JTranscMethodBody(target = "js", value = "GL.getUniformfv(p0, p1, p2);")
	native public void glGetUniformfv(int program, int location, FloatBuffer params);

	//@HaxeMethodBody("GL.getUniformiv(this.programs.get(p0), p1, p2);")
	@JTranscMethodBody(target = "js", value = "GL.getUniformiv(this.programs.get(p0), p1, p2);")
	native public void glGetUniformiv(int program, int location, IntBuffer params);

	@HaxeMethodBody("var id = this.lastId++; var location = GL.getUniformLocation(this.programs.get(p0), N.istr(p1)); this.uniformLocations.set(id, location); return id;")
	@JTranscMethodBody(target = "js", value = "var id = this.lastId++; var location = GL.getUniformLocation(this.programs.get(p0), N.istr(p1)); this.uniformLocations.set(id, location); return id;")
	native public int glGetUniformLocation(int program, String name);

	@HaxeMethodBody("return GL.getVertexAttrib(p0, p1);")
	@JTranscMethodBody(target = "js", value = "return GL.getVertexAttrib(p0, p1);")
	native private int glGetVertexAttrib(int index, int pname);

	public void glGetVertexAttribfv(int index, int pname, FloatBuffer params) {
		params.put(0, glGetVertexAttrib(index, pname));
	}

	public void glGetVertexAttribiv(int index, int pname, IntBuffer params) {
		params.put(0, glGetVertexAttrib(index, pname));
	}

	//@HaxeMethodBody("GL.getVertexAttrib(p0, p1, p2);")
	@JTranscMethodBody(target = "js", value = "GL.getVertexAttrib(p0, p1, p2);")
	native public void glGetVertexAttribPointerv(int index, int pname, Buffer pointer);

	@HaxeMethodBody("return GL.isBuffer(this.buffers.get(p0));")
	@JTranscMethodBody(target = "js", value = "return GL.isBuffer(this.buffers.get(p0));")
	native public boolean glIsBuffer(int buffer);

	@HaxeMethodBody("return GL.isEnabled(p0);")
	@JTranscMethodBody(target = "js", value = "return GL.isEnabled(p0);")
	native public boolean glIsEnabled(int cap);

	@HaxeMethodBody("return GL.isProgram(this.programs.get(p0));")
	@JTranscMethodBody(target = "js", value = "return GL.isProgram(this.programs.get(p0));")
	native public boolean glIsProgram(int program);

	@HaxeMethodBody("return GL.isRenderbuffer(this.renderBuffers.get(p0));")
	@JTranscMethodBody(target = "js", value = "return GL.isRenderbuffer(this.renderBuffers.get(p0));")
	native public boolean glIsRenderbuffer(int renderbuffer);

	@HaxeMethodBody("return GL.isShader(this.shaders.get(p0));")
	@JTranscMethodBody(target = "js", value = "return GL.isShader(this.shaders.get(p0));")
	native public boolean glIsShader(int shader);

	@HaxeMethodBody("return GL.isTexture(this.textures.get(p0));")
	@JTranscMethodBody(target = "js", value = "return GL.isTexture(this.textures.get(p0));")
	native public boolean glIsTexture(int texture);

	@HaxeMethodBody("GL.linkProgram(this.programs.get(p0));")
	@JTranscMethodBody(target = "js", value = "GL.linkProgram(this.programs.get(p0));")
	native public void glLinkProgram(int program);

	public void glReleaseShaderCompiler() {
	}

	@HaxeMethodBody("GL.renderbufferStorage(p0, p1, p2, p3);")
	@JTranscMethodBody(target = "js", value = "GL.renderbufferStorage(p0, p1, p2, p3);")
	native public void glRenderbufferStorage(int target, int internalformat, int width, int height);

	@HaxeMethodBody("GL.sampleCoverage(p0, p1);")
	@JTranscMethodBody(target = "js", value = "GL.sampleCoverage(p0, p1);")
	native public void glSampleCoverage(float value, boolean invert);

	//@HaxeMethodBody("GL.shaderBinary(p0, p1, p2, p3, p4);")
	@JTranscMethodBody(target = "js", value = "GL.shaderBinary(p0, p1, p2, p3, p4);")
	public void glShaderBinary(int n, IntBuffer shaders, int binaryformat, Buffer binary, int length) {
		throw new RuntimeException("Not supported glShaderBinary");
	}

	//@HaxeMethodBody("GL.shaderSource(this.shaders.get(p0), '#define GL_ES 1\\n' + N.istr(p1));")
	@HaxeMethodBody("GL.shaderSource(this.shaders.get(p0), N.istr(p1));")
	@JTranscMethodBody(target = "js", value = "GL.shaderSource(this.shaders.get(p0), N.istr(p1));")
	native public void glShaderSource(int shader, String string);

	@HaxeMethodBody("GL.stencilFuncSeparate(p0, p1, p2, p3);")
	@JTranscMethodBody(target = "js", value = "GL.stencilFuncSeparate(p0, p1, p2, p3);")
	native public void glStencilFuncSeparate(int face, int func, int ref, int mask);

	@HaxeMethodBody("GL.stencilMaskSeparate(p0, p1);")
	@JTranscMethodBody(target = "js", value = "GL.stencilMaskSeparate(p0, p1);")
	native public void glStencilMaskSeparate(int face, int mask);

	@HaxeMethodBody("GL.stencilOpSeparate(p0, p1, p2, p3);")
	@JTranscMethodBody(target = "js", value = "GL.stencilOpSeparate(p0, p1, p2, p3);")
	native public void glStencilOpSeparate(int face, int fail, int zfail, int zpass);

	public void glTexParameterfv(int target, int pname, FloatBuffer params) {
		glTexParameterf(target, pname, params.get(0));
	}

	@HaxeMethodBody("GL.texParameteri(p0, p1, p2);")
	@JTranscMethodBody(target = "js", value = "GL.texParameteri(p0, p1, p2);")
	native public void glTexParameteri(int target, int pname, int param);

	public void glTexParameteriv(int target, int pname, IntBuffer params) {
		glTexParameteri(target, pname, params.get(0));
	}

	@HaxeMethodBody("GL.useProgram(this.programs.get(p0));")
	@JTranscMethodBody(target = "js", value = "GL.useProgram(this.programs.get(p0));")
	native public void glUseProgram(int program);

	@HaxeMethodBody("GL.validateProgram(this.programs.get(p0));")
	@JTranscMethodBody(target = "js", value = "GL.validateProgram(this.programs.get(p0));")
	native public void glValidateProgram(int program);

	@HaxeMethodBody("GL.uniform1f(this.uniformLocations.get(p0), p1);")
	@JTranscMethodBody(target = "js", value = "GL.uniform1f(this.uniformLocations.get(p0), p1);")
	native public void glUniform1f(int location, float x);

	@HaxeMethodBody("GL.uniform1fv(this.uniformLocations.get(p0), p1, _floatBuffer(p2, p1));")
	@JTranscMethodBody(target = "js", value = "GL.uniform1fv(this.uniformLocations.get(p0), p1, _floatBuffer(p2, p1));")
	native public void glUniform1fv(int location, int count, FloatBuffer v);

	@HaxeMethodBody("GL.uniform1fv(this.uniformLocations.get(p0), p1, _floatArray(p2, p3, p1));")
	@JTranscMethodBody(target = "js", value = "GL.uniform1fv(this.uniformLocations.get(p0), p1, _floatArray(p2, p3, p1));")
	native public void glUniform1fv(int location, int count, float[] v, int offset);

	@HaxeMethodBody("GL.uniform1i(this.uniformLocations.get(p0), p1);")
	@JTranscMethodBody(target = "js", value = "GL.uniform1i(this.uniformLocations.get(p0), p1);")
	native public void glUniform1i(int location, int x);

	@HaxeMethodBody("GL.uniform1iv(this.uniformLocations.get(p0), p1, _intBuffer(p2, p1));")
	@JTranscMethodBody(target = "js", value = "GL.uniform1iv(this.uniformLocations.get(p0), p1, _intBuffer(p2, p1));")
	native public void glUniform1iv(int location, int count, IntBuffer v);

	@HaxeMethodBody("GL.uniform1iv(this.uniformLocations.get(p0), p1, _intArray(p2, p3, p1));")
	@JTranscMethodBody(target = "js", value = "GL.uniform1iv(this.uniformLocations.get(p0), p1, _intArray(p2, p3, p1));")
	native public void glUniform1iv(int location, int count, int[] v, int offset);

	@HaxeMethodBody("GL.uniform2f(this.uniformLocations.get(p0), p1, p2);")
	@JTranscMethodBody(target = "js", value = "GL.uniform2f(this.uniformLocations.get(p0), p1, p2);")
	native public void glUniform2f(int location, float x, float y);

	@HaxeMethodBody("GL.uniform2fv(this.uniformLocations.get(p0), p1, _floatBuffer(p2, p1));")
	@JTranscMethodBody(target = "js", value = "GL.uniform2fv(this.uniformLocations.get(p0), p1, _floatBuffer(p2, p1));")
	native public void glUniform2fv(int location, int count, FloatBuffer v);

	@HaxeMethodBody("GL.uniform2fv(this.uniformLocations.get(p0), p1, _floatArray(p2, p3, p1));")
	@JTranscMethodBody(target = "js", value = "GL.uniform2fv(this.uniformLocations.get(p0), p1, _floatArray(p2, p3, p1));")
	native public void glUniform2fv(int location, int count, float[] v, int offset);

	@HaxeMethodBody("GL.uniform2i(this.uniformLocations.get(p0), p1, p2);")
	@JTranscMethodBody(target = "js", value = "GL.uniform2i(this.uniformLocations.get(p0), p1, p2);")
	native public void glUniform2i(int location, int x, int y);

	@HaxeMethodBody("GL.uniform2iv(this.uniformLocations.get(p0), p1, _intBuffer(p2, p1));")
	@JTranscMethodBody(target = "js", value = "GL.uniform2iv(this.uniformLocations.get(p0), p1, _intBuffer(p2, p1));")
	native public void glUniform2iv(int location, int count, IntBuffer v);

	@HaxeMethodBody("GL.uniform2iv(this.uniformLocations.get(p0), p1, _intArray(p2, p3, p1));")
	@JTranscMethodBody(target = "js", value = "GL.uniform2iv(this.uniformLocations.get(p0), p1, _intArray(p2, p3, p1));")
	native public void glUniform2iv(int location, int count, int[] v, int offset);

	@HaxeMethodBody("GL.uniform3f(this.uniformLocations.get(p0), p1, p2, p3);")
	@JTranscMethodBody(target = "js", value = "GL.uniform3f(this.uniformLocations.get(p0), p1, p2, p3);")
	native public void glUniform3f(int location, float x, float y, float z);

	@HaxeMethodBody("GL.uniform3fv(this.uniformLocations.get(p0), p1, _floatBuffer(p2, p1));")
	@JTranscMethodBody(target = "js", value = "GL.uniform3fv(this.uniformLocations.get(p0), p1, _floatBuffer(p2, p1));")
	native public void glUniform3fv(int location, int count, FloatBuffer v);

	@HaxeMethodBody("GL.uniform3fv(this.uniformLocations.get(p0), p1, _floatArray(p2, p3, p1));")
	@JTranscMethodBody(target = "js", value = "GL.uniform3fv(this.uniformLocations.get(p0), p1, _floatArray(p2, p3, p1));")
	native public void glUniform3fv(int location, int count, float[] v, int offset);

	@HaxeMethodBody("GL.uniform3i(this.uniformLocations.get(p0), p1, p2, p3);")
	@JTranscMethodBody(target = "js", value = "GL.uniform3i(this.uniformLocations.get(p0), p1, p2, p3);")
	native public void glUniform3i(int location, int x, int y, int z);

	@HaxeMethodBody("GL.uniform3iv(this.uniformLocations.get(p0), p1, _intBuffer(p2, p1));")
	@JTranscMethodBody(target = "js", value = "GL.uniform3iv(this.uniformLocations.get(p0), p1, _intBuffer(p2, p1));")
	native public void glUniform3iv(int location, int count, IntBuffer v);

	@HaxeMethodBody("GL.uniform3iv(this.uniformLocations.get(p0), p1, _intArray(p2, p3, p1));")
	@JTranscMethodBody(target = "js", value = "GL.uniform3iv(this.uniformLocations.get(p0), p1, _intArray(p2, p3, p1));")
	native public void glUniform3iv(int location, int count, int[] v, int offset);

	@HaxeMethodBody("GL.uniform4f(this.uniformLocations.get(p0), p1, p2, p3, p4);")
	@JTranscMethodBody(target = "js", value = "GL.uniform4f(this.uniformLocations.get(p0), p1, p2, p3, p4);")
	native public void glUniform4f(int location, float x, float y, float z, float w);

	@HaxeMethodBody("GL.uniform4fv(this.uniformLocations.get(p0), p1, _floatBuffer(p2, p1));")
	@JTranscMethodBody(target = "js", value = "GL.uniform4fv(this.uniformLocations.get(p0), p1, _floatBuffer(p2, p1));")
	native public void glUniform4fv(int location, int count, FloatBuffer v);

	public void glUniform4fv(int location, int count, float[] v, int offset) {
		glUniform4fv(location, count, FloatBuffer.wrap(v, offset, count));
	}

	@HaxeMethodBody("GL.uniform4i(this.uniformLocations.get(p0), p1, p2, p3, p4);")
	@JTranscMethodBody(target = "js", value = "GL.uniform4i(this.uniformLocations.get(p0), p1, p2, p3, p4);")
	native public void glUniform4i(int location, int x, int y, int z, int w);

	@HaxeMethodBody("GL.uniform4iv(this.uniformLocations.get(p0), p1, _intBuffer(p2, p1));")
	@JTranscMethodBody(target = "js", value = "GL.uniform4iv(this.uniformLocations.get(p0), p1, _intBuffer(p2, p1));")
	native public void glUniform4iv(int location, int count, IntBuffer v);

	public void glUniform4iv(int location, int count, int[] v, int offset) {
		glUniform4iv(location, count, IntBuffer.wrap(v, offset, count));
	}

	@HaxeMethodBody("GL.uniformMatrix2fv(this.uniformLocations.get(p0), p1, p2, _floatBuffer(p3, p1));")
	@JTranscMethodBody(target = "js", value = "GL.uniformMatrix2fv(this.uniformLocations.get(p0), p1, p2, _floatBuffer(p3, p1));")
	native public void glUniformMatrix2fv(int location, int count, boolean transpose, FloatBuffer value);

	public void glUniformMatrix2fv(int location, int count, boolean transpose, float[] value, int offset) {
		glUniformMatrix2fv(location, count, transpose, FloatBuffer.wrap(value, offset, value.length - offset));
	}

	@HaxeMethodBody("GL.uniformMatrix3fv(this.uniformLocations.get(p0), p1, p2, _floatBuffer(p3, p1));")
	@JTranscMethodBody(target = "js", value = "GL.uniformMatrix3fv(this.uniformLocations.get(p0), p1, p2, _floatBuffer(p3, p1));")
	native public void glUniformMatrix3fv(int location, int count, boolean transpose, FloatBuffer value);

	@HaxeMethodBody("GL.uniformMatrix3fv(this.uniformLocations.get(p0), p1, p2, _floatArray(p3, p4, p1));")
	@JTranscMethodBody(target = "js", value = "GL.uniformMatrix3fv(this.uniformLocations.get(p0), p1, p2, _floatArray(p3, p4, p1));")
	native public void glUniformMatrix3fv(int location, int count, boolean transpose, float[] value, int offset);

	@HaxeMethodBody("GL.uniformMatrix4fv(this.uniformLocations.get(p0), p1, p2, _floatBuffer(p3, p1));")
	@JTranscMethodBody(target = "js", value = "GL.uniformMatrix4fv(this.uniformLocations.get(p0), p1, p2, _floatBuffer(p3, p1));")
	native public void glUniformMatrix4fv(int location, int count, boolean transpose, FloatBuffer value);

	@HaxeMethodBody("GL.uniformMatrix4fv(this.uniformLocations.get(p0), p1, p2, _floatArray(p3, p4, p1));")
	@JTranscMethodBody(target = "js", value = "GL.uniformMatrix4fv(this.uniformLocations.get(p0), p1, p2, _floatArray(p3, p4, p1));")
	native public void glUniformMatrix4fv(int location, int count, boolean transpose, float[] value, int offset);

	@HaxeMethodBody("GL.vertexAttrib1f(p0, p1);")
	@JTranscMethodBody(target = "js", value = "GL.vertexAttrib1f(p0, p1);")
	native public void glVertexAttrib1f(int indx, float x);

	@HaxeMethodBody("GL.vertexAttrib1fv(p0, _floatBuffer(p1));")
	@JTranscMethodBody(target = "js", value = "GL.vertexAttrib1fv(p0, _floatBuffer(p1));")
	native public void glVertexAttrib1fv(int indx, FloatBuffer values);

	@HaxeMethodBody("GL.vertexAttrib2f(p0, p1, p2);")
	@JTranscMethodBody(target = "js", value = "GL.vertexAttrib2f(p0, p1, p2);")
	native public void glVertexAttrib2f(int indx, float x, float y);

	@HaxeMethodBody("GL.vertexAttrib2fv(p0, _floatBuffer(p1));")
	@JTranscMethodBody(target = "js", value = "GL.vertexAttrib2fv(p0, _floatBuffer(p1));")
	native public void glVertexAttrib2fv(int indx, FloatBuffer values);

	@HaxeMethodBody("GL.vertexAttrib3f(p0, p1, p2, p3);")
	@JTranscMethodBody(target = "js", value = "GL.vertexAttrib3f(p0, p1, p2, p3);")
	native public void glVertexAttrib3f(int indx, float x, float y, float z);

	@HaxeMethodBody("GL.vertexAttrib3fv(p0, _floatBuffer(p1));")
	@JTranscMethodBody(target = "js", value = "GL.vertexAttrib3fv(p0, _floatBuffer(p1));")
	native public void glVertexAttrib3fv(int indx, FloatBuffer values);

	@HaxeMethodBody("GL.vertexAttrib4f(p0, p1, p2, p3, p4);")
	@JTranscMethodBody(target = "js", value = "GL.vertexAttrib4f(p0, p1, p2, p3, p4);")
	native public void glVertexAttrib4f(int indx, float x, float y, float z, float w);

	@HaxeMethodBody("GL.vertexAttrib4fv(p0, _floatBuffer(p1));")
	@JTranscMethodBody(target = "js", value = "GL.vertexAttrib4fv(p0, _floatBuffer(p1));")
	native public void glVertexAttrib4fv(int indx, FloatBuffer values);

	//@HaxeMethodBody("GL.vertexAttribPointer(p0, p1, p2, p3, p4, _buffer(p5));")
	@JTranscMethodBody(target = "js", value = "GL.vertexAttribPointer(p0, p1, p2, p3, p4, _buffer(p5));")
	native public void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, Buffer ptr);

	// 	public static inline function vertexAttribPointer (indx:Int, size:Int, type:Int, normalized:Bool, stride:Int, offset:Int):Void {
	@HaxeMethodBody("GL.vertexAttribPointer(p0, p1, p2, p3, p4, p5);")
	@JTranscMethodBody(target = "js", value = "GL.vertexAttribPointer(p0, p1, p2, p3, p4, p5);")
	native public void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, int ptr);

	@Override
	public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, Pixmap pixmap) {
		System.out.println("glTexImage2D:target=" + target + ",level=" + level + ",internalformat=" + internalformat + ",width=" + width + ",height=" + height + ",border=" + border + ",format=" + format + ",type=" + type + ",pixmap=" + pixmap);
		_glTexImage2D(target, level, internalformat, width, height, border, format, type, pixmap);
	}

	@JTranscMethodBody(target = "js", value = {
		"var target = p0, level = p1, internalformat = p2, width = p3, height = p4, border = p5, format = p6, type = p7, pixmap = p8;",

		"if (pixmap.INT_image) {",
		"	GL.texImage2D(target, level, internalformat, width, height, border, format, type, pixmap.INT_image);",
		"} else {",
		"	var jarray = pixmap['{% FIELD com.badlogic.gdx.graphics.Pixmap:data %}'];",
		"	var arrayBufferView = _arrayCopyRev(new Uint8Array(jarray.data.buffer));",
		"	GL.texImage2D(target, level, internalformat, width, height, border, format, type, arrayBufferView);",
		"}",
	})
	private void _glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, Pixmap pixmap) {
		_glTexImage2D(target, level, internalformat, width, height, border, format, type, pixmap.getPixels());
	}
}
