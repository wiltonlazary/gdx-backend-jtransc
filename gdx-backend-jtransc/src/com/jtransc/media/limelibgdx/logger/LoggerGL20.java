package com.jtransc.media.limelibgdx.logger;

import com.badlogic.gdx.graphics.GL20;
import com.jtransc.media.limelibgdx.ProxyGL20;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class LoggerGL20 extends ProxyGL20 {
	public LoggerGL20(GL20 parent) {
		super(parent);
	}

	private void log(String msg) {
		System.out.println(msg);
	}

	private String toString(float[] fb) {
		String out = "";
		for (int n = 0; n < fb.length; n++) out += fb[n] + ",";
		return out;
	}

	private String toString(FloatBuffer fb) {
		String out = "";
		for (int n = 0; n < fb.limit(); n++) out += fb.get(n) + ",";
		return out;
	}

	private String toString(int[] fb) {
		String out = "";
		for (int n = 0; n < fb.length; n++) out += fb[n] + ",";
		return out;
	}

	private String toString(IntBuffer fb) {
		String out = "";
		for (int n = 0; n < fb.limit(); n++) out += fb.get(n) + ",";
		return out;
	}

	@Override
	public void glActiveTexture(int texture) {
		log("glActiveTexture: texture = [" + texture + "]");
		super.glActiveTexture(texture);
	}

	@Override
	public void glBindTexture(int target, int texture) {
		log("glBindTexture: target = [" + target + "], texture = [" + texture + "]");
		super.glBindTexture(target, texture);
	}

	@Override
	public void glBlendFunc(int sfactor, int dfactor) {
		log("glBlendFunc: sfactor = [" + sfactor + "], dfactor = [" + dfactor + "]");
		super.glBlendFunc(sfactor, dfactor);
	}

	@Override
	public void glClear(int mask) {
		log("glClear: mask = [" + mask + "]");
		super.glClear(mask);
	}

	@Override
	public void glClearColor(float red, float green, float blue, float alpha) {
		log("glClearColor: red = [" + red + "], green = [" + green + "], blue = [" + blue + "], alpha = [" + alpha + "]");
		super.glClearColor(red, green, blue, alpha);
	}

	@Override
	public void glClearDepthf(float depth) {
		log("glClearDepthf: depth = [" + depth + "]");
		super.glClearDepthf(depth);
	}

	@Override
	public void glClearStencil(int s) {
		log("glClearStencil: s = [" + s + "]");
		super.glClearStencil(s);
	}

	@Override
	public void glColorMask(boolean red, boolean green, boolean blue, boolean alpha) {
		log("glColorMask: red = [" + red + "], green = [" + green + "], blue = [" + blue + "], alpha = [" + alpha + "]");
		super.glColorMask(red, green, blue, alpha);
	}

	@Override
	public void glCompressedTexImage2D(int target, int level, int internalformat, int width, int height, int border, int imageSize, Buffer data) {
		log("glCompressedTexImage2D: target = [" + target + "], level = [" + level + "], internalformat = [" + internalformat + "], width = [" + width + "], height = [" + height + "], border = [" + border + "], imageSize = [" + imageSize + "], data = [" + data + "]");
		super.glCompressedTexImage2D(target, level, internalformat, width, height, border, imageSize, data);
	}

	@Override
	public void glCompressedTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int imageSize, Buffer data) {
		log("glCompressedTexSubImage2D: target = [" + target + "], level = [" + level + "], xoffset = [" + xoffset + "], yoffset = [" + yoffset + "], width = [" + width + "], height = [" + height + "], format = [" + format + "], imageSize = [" + imageSize + "], data = [" + data + "]");
		super.glCompressedTexSubImage2D(target, level, xoffset, yoffset, width, height, format, imageSize, data);
	}

	@Override
	public void glCopyTexImage2D(int target, int level, int internalformat, int x, int y, int width, int height, int border) {
		log("glCopyTexImage2D: target = [" + target + "], level = [" + level + "], internalformat = [" + internalformat + "], x = [" + x + "], y = [" + y + "], width = [" + width + "], height = [" + height + "], border = [" + border + "]");
		super.glCopyTexImage2D(target, level, internalformat, x, y, width, height, border);
	}

	@Override
	public void glCopyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y, int width, int height) {
		log("glCopyTexSubImage2D: target = [" + target + "], level = [" + level + "], xoffset = [" + xoffset + "], yoffset = [" + yoffset + "], x = [" + x + "], y = [" + y + "], width = [" + width + "], height = [" + height + "]");
		super.glCopyTexSubImage2D(target, level, xoffset, yoffset, x, y, width, height);
	}

	@Override
	public void glCullFace(int mode) {
		log("glCullFace: mode = [" + mode + "]");
		super.glCullFace(mode);
	}

	@Override
	public void glDeleteTextures(int n, IntBuffer textures) {
		log("glDeleteTextures: n = [" + n + "], textures = [" + textures + "]");
		super.glDeleteTextures(n, textures);
	}

	@Override
	public void glDeleteTexture(int texture) {
		log("glDeleteTexture: texture = [" + texture + "]");
		super.glDeleteTexture(texture);
	}

	@Override
	public void glDepthFunc(int func) {
		log("glDepthFunc: func = [" + func + "]");
		super.glDepthFunc(func);
	}

	@Override
	public void glDepthMask(boolean flag) {
		log("glDepthMask: flag = [" + flag + "]");
		super.glDepthMask(flag);
	}

	@Override
	public void glDepthRangef(float zNear, float zFar) {
		log("glDepthRangef: zNear = [" + zNear + "], zFar = [" + zFar + "]");
		super.glDepthRangef(zNear, zFar);
	}

	@Override
	public void glDisable(int cap) {
		log("glDisable: cap = [" + cap + "]");
		super.glDisable(cap);
	}

	@Override
	public void glDrawArrays(int mode, int first, int count) {
		log("glDrawArrays: mode = [" + mode + "], first = [" + first + "], count = [" + count + "]");
		super.glDrawArrays(mode, first, count);
	}

	@Override
	public void glDrawElements(int mode, int count, int type, Buffer indices) {
		log("glDrawElements: mode = [" + mode + "], count = [" + count + "], type = [" + type + "], indices = [" + indices + "]");
		super.glDrawElements(mode, count, type, indices);
	}

	@Override
	public void glEnable(int cap) {
		log("glEnable: cap = [" + cap + "]");
		super.glEnable(cap);
	}

	@Override
	public void glFinish() {
		log("glFinish: ");
		super.glFinish();
	}

	@Override
	public void glFlush() {
		log("glFlush: ");
		super.glFlush();
	}

	@Override
	public void glFrontFace(int mode) {
		log("glFrontFace: mode = [" + mode + "]");
		super.glFrontFace(mode);
	}

	@Override
	public void glGenTextures(int n, IntBuffer textures) {
		log("glGenTextures: n = [" + n + "], textures = [" + textures + "]");
		super.glGenTextures(n, textures);
	}

	@Override
	public int glGenTexture() {
		log("glGenTexture: ");
		return super.glGenTexture();
	}

	@Override
	public int glGetError() {
		log("glGetError: ");
		return super.glGetError();
	}

	@Override
	public void glGetIntegerv(int pname, IntBuffer params) {
		log("glGetIntegerv: pname = [" + pname + "], params = [" + params + "]");
		super.glGetIntegerv(pname, params);
	}

	@Override
	public String glGetString(int name) {
		log("glGetString: name = [" + name + "]");
		return super.glGetString(name);
	}

	@Override
	public void glHint(int target, int mode) {
		log("glHint: target = [" + target + "], mode = [" + mode + "]");
		super.glHint(target, mode);
	}

	@Override
	public void glLineWidth(float width) {
		log("glLineWidth: width = [" + width + "]");
		super.glLineWidth(width);
	}

	@Override
	public void glPixelStorei(int pname, int param) {
		log("glPixelStorei: pname = [" + pname + "], param = [" + param + "]");
		super.glPixelStorei(pname, param);
	}

	@Override
	public void glPolygonOffset(float factor, float units) {
		log("glPolygonOffset: factor = [" + factor + "], units = [" + units + "]");
		super.glPolygonOffset(factor, units);
	}

	@Override
	public void glReadPixels(int x, int y, int width, int height, int format, int type, Buffer pixels) {
		log("glReadPixels: x = [" + x + "], y = [" + y + "], width = [" + width + "], height = [" + height + "], format = [" + format + "], type = [" + type + "], pixels = [" + pixels + "]");
		super.glReadPixels(x, y, width, height, format, type, pixels);
	}

	@Override
	public void glScissor(int x, int y, int width, int height) {
		log("glScissor: x = [" + x + "], y = [" + y + "], width = [" + width + "], height = [" + height + "]");
		super.glScissor(x, y, width, height);
	}

	@Override
	public void glStencilFunc(int func, int ref, int mask) {
		log("glStencilFunc: func = [" + func + "], ref = [" + ref + "], mask = [" + mask + "]");
		super.glStencilFunc(func, ref, mask);
	}

	@Override
	public void glStencilMask(int mask) {
		log("glStencilMask: mask = [" + mask + "]");
		super.glStencilMask(mask);
	}

	@Override
	public void glStencilOp(int fail, int zfail, int zpass) {
		log("glStencilOp: fail = [" + fail + "], zfail = [" + zfail + "], zpass = [" + zpass + "]");
		super.glStencilOp(fail, zfail, zpass);
	}

	@Override
	public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, Buffer pixels) {
		log("glTexImage2D: target = [" + target + "], level = [" + level + "], internalformat = [" + internalformat + "], width = [" + width + "], height = [" + height + "], border = [" + border + "], format = [" + format + "], type = [" + type + "], pixels = [" + pixels + "]");
		super.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
	}

	@Override
	public void glTexParameterf(int target, int pname, float param) {
		log("glTexParameterf: target = [" + target + "], pname = [" + pname + "], param = [" + param + "]");
		super.glTexParameterf(target, pname, param);
	}

	@Override
	public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, Buffer pixels) {
		log("glTexSubImage2D: target = [" + target + "], level = [" + level + "], xoffset = [" + xoffset + "], yoffset = [" + yoffset + "], width = [" + width + "], height = [" + height + "], format = [" + format + "], type = [" + type + "], pixels = [" + pixels + "]");
		super.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
	}

	@Override
	public void glViewport(int x, int y, int width, int height) {
		log("glViewport: x = [" + x + "], y = [" + y + "], width = [" + width + "], height = [" + height + "]");
		super.glViewport(x, y, width, height);
	}

	@Override
	public void glAttachShader(int program, int shader) {
		log("glAttachShader: program = [" + program + "], shader = [" + shader + "]");
		super.glAttachShader(program, shader);
	}

	@Override
	public void glBindAttribLocation(int program, int index, String name) {
		log("glBindAttribLocation: program = [" + program + "], index = [" + index + "], name = [" + name + "]");
		super.glBindAttribLocation(program, index, name);
	}

	@Override
	public void glBindBuffer(int target, int buffer) {
		log("glBindBuffer: target = [" + target + "], buffer = [" + buffer + "]");
		super.glBindBuffer(target, buffer);
	}

	@Override
	public void glBindFramebuffer(int target, int framebuffer) {
		log("glBindFramebuffer: target = [" + target + "], framebuffer = [" + framebuffer + "]");
		super.glBindFramebuffer(target, framebuffer);
	}

	@Override
	public void glBindRenderbuffer(int target, int renderbuffer) {
		log("glBindRenderbuffer: target = [" + target + "], renderbuffer = [" + renderbuffer + "]");
		super.glBindRenderbuffer(target, renderbuffer);
	}

	@Override
	public void glBlendColor(float red, float green, float blue, float alpha) {
		log("glBlendColor: red = [" + red + "], green = [" + green + "], blue = [" + blue + "], alpha = [" + alpha + "]");
		super.glBlendColor(red, green, blue, alpha);
	}

	@Override
	public void glBlendEquation(int mode) {
		log("glBlendEquation: mode = [" + mode + "]");
		super.glBlendEquation(mode);
	}

	@Override
	public void glBlendEquationSeparate(int modeRGB, int modeAlpha) {
		log("glBlendEquationSeparate: modeRGB = [" + modeRGB + "], modeAlpha = [" + modeAlpha + "]");
		super.glBlendEquationSeparate(modeRGB, modeAlpha);
	}

	@Override
	public void glBlendFuncSeparate(int srcRGB, int dstRGB, int srcAlpha, int dstAlpha) {
		log("glBlendFuncSeparate: srcRGB = [" + srcRGB + "], dstRGB = [" + dstRGB + "], srcAlpha = [" + srcAlpha + "], dstAlpha = [" + dstAlpha + "]");
		super.glBlendFuncSeparate(srcRGB, dstRGB, srcAlpha, dstAlpha);
	}

	@Override
	public void glBufferData(int target, int size, Buffer data, int usage) {
		log("glBufferData: target = [" + target + "], size = [" + size + "], data = [" + data + "], usage = [" + usage + "]");
		super.glBufferData(target, size, data, usage);
	}

	@Override
	public void glBufferSubData(int target, int offset, int size, Buffer data) {
		log("glBufferSubData: target = [" + target + "], offset = [" + offset + "], size = [" + size + "], data = [" + data + "]");
		super.glBufferSubData(target, offset, size, data);
	}

	@Override
	public int glCheckFramebufferStatus(int target) {
		log("glCheckFramebufferStatus: target = [" + target + "]");
		return super.glCheckFramebufferStatus(target);
	}

	@Override
	public void glCompileShader(int shader) {
		log("glCompileShader: shader = [" + shader + "]");
		super.glCompileShader(shader);
	}

	@Override
	public int glCreateProgram() {
		log("glCreateProgram: ");
		return super.glCreateProgram();
	}

	@Override
	public int glCreateShader(int type) {
		log("glCreateShader: type = [" + type + "]");
		return super.glCreateShader(type);
	}

	@Override
	public void glDeleteBuffer(int buffer) {
		log("glDeleteBuffer: buffer = [" + buffer + "]");
		super.glDeleteBuffer(buffer);
	}

	@Override
	public void glDeleteBuffers(int n, IntBuffer buffers) {
		log("glDeleteBuffers: n = [" + n + "], buffers = [" + buffers + "]");
		super.glDeleteBuffers(n, buffers);
	}

	@Override
	public void glDeleteFramebuffer(int framebuffer) {
		log("glDeleteFramebuffer: framebuffer = [" + framebuffer + "]");
		super.glDeleteFramebuffer(framebuffer);
	}

	@Override
	public void glDeleteFramebuffers(int n, IntBuffer framebuffers) {
		log("glDeleteFramebuffers: n = [" + n + "], framebuffers = [" + framebuffers + "]");
		super.glDeleteFramebuffers(n, framebuffers);
	}

	@Override
	public void glDeleteProgram(int program) {
		log("glDeleteProgram: program = [" + program + "]");
		super.glDeleteProgram(program);
	}

	@Override
	public void glDeleteRenderbuffer(int renderbuffer) {
		log("glDeleteRenderbuffer: renderbuffer = [" + renderbuffer + "]");
		super.glDeleteRenderbuffer(renderbuffer);
	}

	@Override
	public void glDeleteRenderbuffers(int n, IntBuffer renderbuffers) {
		log("glDeleteRenderbuffers: n = [" + n + "], renderbuffers = [" + renderbuffers + "]");
		super.glDeleteRenderbuffers(n, renderbuffers);
	}

	@Override
	public void glDeleteShader(int shader) {
		log("glDeleteShader: shader = [" + shader + "]");
		super.glDeleteShader(shader);
	}

	@Override
	public void glDetachShader(int program, int shader) {
		log("glDetachShader: program = [" + program + "], shader = [" + shader + "]");
		super.glDetachShader(program, shader);
	}

	@Override
	public void glDisableVertexAttribArray(int index) {
		log("glDisableVertexAttribArray: index = [" + index + "]");
		super.glDisableVertexAttribArray(index);
	}

	@Override
	public void glDrawElements(int mode, int count, int type, int indices) {
		log("glDrawElements: mode = [" + mode + "], count = [" + count + "], type = [" + type + "], indices = [" + indices + "]");
		super.glDrawElements(mode, count, type, indices);
	}

	@Override
	public void glEnableVertexAttribArray(int index) {
		log("glEnableVertexAttribArray: index = [" + index + "]");
		super.glEnableVertexAttribArray(index);
	}

	@Override
	public void glFramebufferRenderbuffer(int target, int attachment, int renderbuffertarget, int renderbuffer) {
		log("glFramebufferRenderbuffer: target = [" + target + "], attachment = [" + attachment + "], renderbuffertarget = [" + renderbuffertarget + "], renderbuffer = [" + renderbuffer + "]");
		super.glFramebufferRenderbuffer(target, attachment, renderbuffertarget, renderbuffer);
	}

	@Override
	public void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level) {
		log("glFramebufferTexture2D: target = [" + target + "], attachment = [" + attachment + "], textarget = [" + textarget + "], texture = [" + texture + "], level = [" + level + "]");
		super.glFramebufferTexture2D(target, attachment, textarget, texture, level);
	}

	@Override
	public int glGenBuffer() {
		log("glGenBuffer: ");
		return super.glGenBuffer();
	}

	@Override
	public void glGenBuffers(int n, IntBuffer buffers) {
		log("glGenBuffers: n = [" + n + "], buffers = [" + buffers + "]");
		super.glGenBuffers(n, buffers);
	}

	@Override
	public void glGenerateMipmap(int target) {
		log("glGenerateMipmap: target = [" + target + "]");
		super.glGenerateMipmap(target);
	}

	@Override
	public int glGenFramebuffer() {
		int result = super.glGenFramebuffer();
		log("glGenFramebuffer: -> " + result);
		return result;
	}

	@Override
	public void glGenFramebuffers(int n, IntBuffer framebuffers) {
		log("glGenFramebuffers: n = [" + n + "], framebuffers = [" + framebuffers + "]");
		super.glGenFramebuffers(n, framebuffers);
	}

	@Override
	public int glGenRenderbuffer() {
		int result = super.glGenRenderbuffer();
		log("glGenRenderbuffer: -> " + result);
		return result;
	}

	@Override
	public void glGenRenderbuffers(int n, IntBuffer renderbuffers) {
		log("glGenRenderbuffers: n = [" + n + "], renderbuffers = [" + renderbuffers + "]");
		super.glGenRenderbuffers(n, renderbuffers);
	}

	@Override
	public String glGetActiveAttrib(int program, int index, IntBuffer size, Buffer type) {
		String result = super.glGetActiveAttrib(program, index, size, type);
		log("glGetActiveAttrib: program = [" + program + "], index = [" + index + "], size = [" + size + "], type = [" + type + "] -> " + result);
		return result;
	}

	@Override
	public String glGetActiveUniform(int program, int index, IntBuffer size, Buffer type) {
		String result = super.glGetActiveUniform(program, index, size, type);
		log("glGetActiveUniform: program = [" + program + "], index = [" + index + "], size = [" + size + "], type = [" + type + "] -> " + result);
		return result;
	}

	@Override
	public void glGetAttachedShaders(int program, int maxcount, Buffer count, IntBuffer shaders) {
		log("glGetAttachedShaders: program = [" + program + "], maxcount = [" + maxcount + "], count = [" + count + "], shaders = [" + shaders + "]");
		super.glGetAttachedShaders(program, maxcount, count, shaders);
	}

	@Override
	public int glGetAttribLocation(int program, String name) {
		log("glGetAttribLocation: program = [" + program + "], name = [" + name + "]");
		int result = super.glGetAttribLocation(program, name);
		log("   -> " + result);
		return result;
	}

	@Override
	public void glGetBooleanv(int pname, Buffer params) {
		log("glGetBooleanv: pname = [" + pname + "], params = [" + params + "]");
		super.glGetBooleanv(pname, params);
	}

	@Override
	public void glGetBufferParameteriv(int target, int pname, IntBuffer params) {
		log("glGetBufferParameteriv: target = [" + target + "], pname = [" + pname + "], params = [" + params + "]");
		super.glGetBufferParameteriv(target, pname, params);
	}

	@Override
	public void glGetFloatv(int pname, FloatBuffer params) {
		log("glGetFloatv: pname = [" + pname + "], params = [" + params + "]");
		super.glGetFloatv(pname, params);
	}

	@Override
	public void glGetFramebufferAttachmentParameteriv(int target, int attachment, int pname, IntBuffer params) {
		log("glGetFramebufferAttachmentParameteriv: target = [" + target + "], attachment = [" + attachment + "], pname = [" + pname + "], params = [" + params + "]");
		super.glGetFramebufferAttachmentParameteriv(target, attachment, pname, params);
	}

	@Override
	public void glGetProgramiv(int program, int pname, IntBuffer params) {
		log("glGetProgramiv: program = [" + program + "], pname = [" + pname + "], params = [" + params + "]");
		super.glGetProgramiv(program, pname, params);
	}

	@Override
	public String glGetProgramInfoLog(int program) {
		log("glGetProgramInfoLog: program = [" + program + "]");
		return super.glGetProgramInfoLog(program);
	}

	@Override
	public void glGetRenderbufferParameteriv(int target, int pname, IntBuffer params) {
		log("glGetRenderbufferParameteriv: target = [" + target + "], pname = [" + pname + "], params = [" + params + "]");
		super.glGetRenderbufferParameteriv(target, pname, params);
	}

	@Override
	public void glGetShaderiv(int shader, int pname, IntBuffer params) {
		log("glGetShaderiv: shader = [" + shader + "], pname = [" + pname + "], params = [" + params + "]");
		super.glGetShaderiv(shader, pname, params);
	}

	@Override
	public String glGetShaderInfoLog(int shader) {
		log("glGetShaderInfoLog: shader = [" + shader + "]");
		return super.glGetShaderInfoLog(shader);
	}

	@Override
	public void glGetShaderPrecisionFormat(int shadertype, int precisiontype, IntBuffer range, IntBuffer precision) {
		log("glGetShaderPrecisionFormat: shadertype = [" + shadertype + "], precisiontype = [" + precisiontype + "], range = [" + range + "], precision = [" + precision + "]");
		super.glGetShaderPrecisionFormat(shadertype, precisiontype, range, precision);
	}

	@Override
	public void glGetTexParameterfv(int target, int pname, FloatBuffer params) {
		log("glGetTexParameterfv: target = [" + target + "], pname = [" + pname + "], params = [" + params + "]");
		super.glGetTexParameterfv(target, pname, params);
	}

	@Override
	public void glGetTexParameteriv(int target, int pname, IntBuffer params) {
		log("glGetTexParameteriv: target = [" + target + "], pname = [" + pname + "], params = [" + params + "]");
		super.glGetTexParameteriv(target, pname, params);
	}

	@Override
	public void glGetUniformfv(int program, int location, FloatBuffer params) {
		log("glGetUniformfv: program = [" + program + "], location = [" + location + "], params = [" + params + "]");
		super.glGetUniformfv(program, location, params);
	}

	@Override
	public void glGetUniformiv(int program, int location, IntBuffer params) {
		log("glGetUniformiv: program = [" + program + "], location = [" + location + "], params = [" + params + "]");
		super.glGetUniformiv(program, location, params);
	}

	@Override
	public int glGetUniformLocation(int program, String name) {
		log("glGetUniformLocation: program = [" + program + "], name = [" + name + "]");
		return super.glGetUniformLocation(program, name);
	}

	@Override
	public void glGetVertexAttribfv(int index, int pname, FloatBuffer params) {
		log("glGetVertexAttribfv: index = [" + index + "], pname = [" + pname + "], params = [" + params + "]");
		super.glGetVertexAttribfv(index, pname, params);
	}

	@Override
	public void glGetVertexAttribiv(int index, int pname, IntBuffer params) {
		log("glGetVertexAttribiv: index = [" + index + "], pname = [" + pname + "], params = [" + params + "]");
		super.glGetVertexAttribiv(index, pname, params);
	}

	@Override
	public void glGetVertexAttribPointerv(int index, int pname, Buffer pointer) {
		log("glGetVertexAttribPointerv: index = [" + index + "], pname = [" + pname + "], pointer = [" + pointer + "]");
		super.glGetVertexAttribPointerv(index, pname, pointer);
	}

	@Override
	public boolean glIsBuffer(int buffer) {
		log("glIsBuffer: buffer = [" + buffer + "]");
		return super.glIsBuffer(buffer);
	}

	@Override
	public boolean glIsEnabled(int cap) {
		log("glIsEnabled: cap = [" + cap + "]");
		return super.glIsEnabled(cap);
	}

	@Override
	public boolean glIsFramebuffer(int framebuffer) {
		log("glIsFramebuffer: framebuffer = [" + framebuffer + "]");
		return super.glIsFramebuffer(framebuffer);
	}

	@Override
	public boolean glIsProgram(int program) {
		log("glIsProgram: program = [" + program + "]");
		return super.glIsProgram(program);
	}

	@Override
	public boolean glIsRenderbuffer(int renderbuffer) {
		log("glIsRenderbuffer: renderbuffer = [" + renderbuffer + "]");
		return super.glIsRenderbuffer(renderbuffer);
	}

	@Override
	public boolean glIsShader(int shader) {
		log("glIsShader: shader = [" + shader + "]");
		return super.glIsShader(shader);
	}

	@Override
	public boolean glIsTexture(int texture) {
		log("glIsTexture: texture = [" + texture + "]");
		return super.glIsTexture(texture);
	}

	@Override
	public void glLinkProgram(int program) {
		log("glLinkProgram: program = [" + program + "]");
		super.glLinkProgram(program);
	}

	@Override
	public void glReleaseShaderCompiler() {
		log("glReleaseShaderCompiler: ");
		super.glReleaseShaderCompiler();
	}

	@Override
	public void glRenderbufferStorage(int target, int internalformat, int width, int height) {
		log("glRenderbufferStorage: target = [" + target + "], internalformat = [" + internalformat + "], width = [" + width + "], height = [" + height + "]");
		super.glRenderbufferStorage(target, internalformat, width, height);
	}

	@Override
	public void glSampleCoverage(float value, boolean invert) {
		log("glSampleCoverage: value = [" + value + "], invert = [" + invert + "]");
		super.glSampleCoverage(value, invert);
	}

	@Override
	public void glShaderBinary(int n, IntBuffer shaders, int binaryformat, Buffer binary, int length) {
		log("glShaderBinary: n = [" + n + "], shaders = [" + shaders + "], binaryformat = [" + binaryformat + "], binary = [" + binary + "], length = [" + length + "]");
		super.glShaderBinary(n, shaders, binaryformat, binary, length);
	}

	@Override
	public void glShaderSource(int shader, String string) {
		log("glShaderSource: shader = [" + shader + "], string = [" + string + "]");
		super.glShaderSource(shader, string);
	}

	@Override
	public void glStencilFuncSeparate(int face, int func, int ref, int mask) {
		log("glStencilFuncSeparate: face = [" + face + "], func = [" + func + "], ref = [" + ref + "], mask = [" + mask + "]");
		super.glStencilFuncSeparate(face, func, ref, mask);
	}

	@Override
	public void glStencilMaskSeparate(int face, int mask) {
		log("glStencilMaskSeparate: face = [" + face + "], mask = [" + mask + "]");
		super.glStencilMaskSeparate(face, mask);
	}

	@Override
	public void glStencilOpSeparate(int face, int fail, int zfail, int zpass) {
		log("glStencilOpSeparate: face = [" + face + "], fail = [" + fail + "], zfail = [" + zfail + "], zpass = [" + zpass + "]");
		super.glStencilOpSeparate(face, fail, zfail, zpass);
	}

	@Override
	public void glTexParameterfv(int target, int pname, FloatBuffer params) {
		log("glTexParameterfv: target = [" + target + "], pname = [" + pname + "], params = [" + toString(params) + "]");
		super.glTexParameterfv(target, pname, params);
	}

	@Override
	public void glTexParameteri(int target, int pname, int param) {
		log("glTexParameteri: target = [" + target + "], pname = [" + pname + "], param = [" + param + "]");
		super.glTexParameteri(target, pname, param);
	}

	@Override
	public void glTexParameteriv(int target, int pname, IntBuffer params) {
		log("glTexParameteriv: target = [" + target + "], pname = [" + pname + "], params = [" + params + "]");
		super.glTexParameteriv(target, pname, params);
	}

	@Override
	public void glUniform1f(int location, float x) {
		log("glUniform1f: location = [" + location + "], x = [" + x + "]");
		super.glUniform1f(location, x);
	}

	@Override
	public void glUniform1fv(int location, int count, FloatBuffer v) {
		log("glUniform1fv: location = [" + location + "], count = [" + count + "], v = [" + toString(v) + "]");
		super.glUniform1fv(location, count, v);
	}

	@Override
	public void glUniform1fv(int location, int count, float[] v, int offset) {
		log("glUniform1fv: location = [" + location + "], count = [" + count + "], v = [" + toString(v) + "], offset = [" + offset + "]");
		super.glUniform1fv(location, count, v, offset);
	}

	@Override
	public void glUniform1i(int location, int x) {
		log("glUniform1i: location = [" + location + "], x = [" + x + "]");
		super.glUniform1i(location, x);
	}

	@Override
	public void glUniform1iv(int location, int count, IntBuffer v) {
		log("glUniform1iv: location = [" + location + "], count = [" + count + "], v = [" + toString(v) + "]");
		super.glUniform1iv(location, count, v);
	}

	@Override
	public void glUniform1iv(int location, int count, int[] v, int offset) {
		log("glUniform1iv: location = [" + location + "], count = [" + count + "], v = [" + toString(v) + "], offset = [" + offset + "]");
		super.glUniform1iv(location, count, v, offset);
	}

	@Override
	public void glUniform2f(int location, float x, float y) {
		log("glUniform2f: location = [" + location + "], x = [" + x + "], y = [" + y + "]");
		super.glUniform2f(location, x, y);
	}

	@Override
	public void glUniform2fv(int location, int count, FloatBuffer v) {
		log("glUniform2fv: location = [" + location + "], count = [" + count + "], v = [" + toString(v) + "]");
		super.glUniform2fv(location, count, v);
	}

	@Override
	public void glUniform2fv(int location, int count, float[] v, int offset) {
		log("glUniform2fv: location = [" + location + "], count = [" + count + "], v = [" + toString(v) + "], offset = [" + offset + "]");
		super.glUniform2fv(location, count, v, offset);
	}

	@Override
	public void glUniform2i(int location, int x, int y) {
		log("glUniform2i: location = [" + location + "], x = [" + x + "], y = [" + y + "]");
		super.glUniform2i(location, x, y);
	}

	@Override
	public void glUniform2iv(int location, int count, IntBuffer v) {
		log("glUniform2iv: location = [" + location + "], count = [" + count + "], v = [" + toString(v) + "]");
		super.glUniform2iv(location, count, v);
	}

	@Override
	public void glUniform2iv(int location, int count, int[] v, int offset) {
		log("glUniform2iv: location = [" + location + "], count = [" + count + "], v = [" + toString(v) + "], offset = [" + offset + "]");
		super.glUniform2iv(location, count, v, offset);
	}

	@Override
	public void glUniform3f(int location, float x, float y, float z) {
		log("glUniform3f: location = [" + location + "], x = [" + x + "], y = [" + y + "], z = [" + z + "]");
		super.glUniform3f(location, x, y, z);
	}

	@Override
	public void glUniform3fv(int location, int count, FloatBuffer v) {
		log("glUniform3fv: location = [" + location + "], count = [" + count + "], v = [" + toString(v) + "]");
		super.glUniform3fv(location, count, v);
	}

	@Override
	public void glUniform3fv(int location, int count, float[] v, int offset) {
		log("glUniform3fv: location = [" + location + "], count = [" + count + "], v = [" + toString(v) + "], offset = [" + offset + "]");
		super.glUniform3fv(location, count, v, offset);
	}

	@Override
	public void glUniform3i(int location, int x, int y, int z) {
		log("glUniform3i: location = [" + location + "], x = [" + x + "], y = [" + y + "], z = [" + z + "]");
		super.glUniform3i(location, x, y, z);
	}

	@Override
	public void glUniform3iv(int location, int count, IntBuffer v) {
		log("glUniform3iv: location = [" + location + "], count = [" + count + "], v = [" + toString(v) + "]");
		super.glUniform3iv(location, count, v);
	}

	@Override
	public void glUniform3iv(int location, int count, int[] v, int offset) {
		log("glUniform3iv: location = [" + location + "], count = [" + count + "], v = [" + toString(v) + "], offset = [" + offset + "]");
		super.glUniform3iv(location, count, v, offset);
	}

	@Override
	public void glUniform4f(int location, float x, float y, float z, float w) {
		log("glUniform4f: location = [" + location + "], x = [" + x + "], y = [" + y + "], z = [" + z + "], w = [" + w + "]");
		super.glUniform4f(location, x, y, z, w);
	}

	@Override
	public void glUniform4fv(int location, int count, FloatBuffer v) {
		log("glUniform4fv: location = [" + location + "], count = [" + count + "], v = [" + toString(v) + "]");
		super.glUniform4fv(location, count, v);
	}

	@Override
	public void glUniform4fv(int location, int count, float[] v, int offset) {
		log("glUniform4fv: location = [" + location + "], count = [" + count + "], v = [" + toString(v) + "], offset = [" + offset + "]");
		super.glUniform4fv(location, count, v, offset);
	}

	@Override
	public void glUniform4i(int location, int x, int y, int z, int w) {
		log("glUniform4i: location = [" + location + "], x = [" + x + "], y = [" + y + "], z = [" + z + "], w = [" + w + "]");
		super.glUniform4i(location, x, y, z, w);
	}

	@Override
	public void glUniform4iv(int location, int count, IntBuffer v) {
		log("glUniform4iv: location = [" + location + "], count = [" + count + "], v = [" + toString(v) + "]");
		super.glUniform4iv(location, count, v);
	}

	@Override
	public void glUniform4iv(int location, int count, int[] v, int offset) {
		log("glUniform4iv: location = [" + location + "], count = [" + count + "], v = [" + toString(v) + "], offset = [" + offset + "]");
		super.glUniform4iv(location, count, v, offset);
	}

	@Override
	public void glUniformMatrix2fv(int location, int count, boolean transpose, FloatBuffer value) {
		log("glUniformMatrix2fv: location = [" + location + "], count = [" + count + "], transpose = [" + transpose + "], value = [" + toString(value) + "]");
		super.glUniformMatrix2fv(location, count, transpose, value);
	}

	@Override
	public void glUniformMatrix2fv(int location, int count, boolean transpose, float[] value, int offset) {
		log("glUniformMatrix2fv: location = [" + location + "], count = [" + count + "], transpose = [" + transpose + "], value = [" + toString(value) + "], offset = [" + offset + "]");
		super.glUniformMatrix2fv(location, count, transpose, value, offset);
	}

	@Override
	public void glUniformMatrix3fv(int location, int count, boolean transpose, FloatBuffer value) {
		log("glUniformMatrix3fv: location = [" + location + "], count = [" + count + "], transpose = [" + transpose + "], value = [" + toString(value) + "]");
		super.glUniformMatrix3fv(location, count, transpose, value);
	}

	@Override
	public void glUniformMatrix3fv(int location, int count, boolean transpose, float[] value, int offset) {
		log("glUniformMatrix3fv: location = [" + location + "], count = [" + count + "], transpose = [" + transpose + "], value = [" + toString(value) + "], offset = [" + offset + "]");
		super.glUniformMatrix3fv(location, count, transpose, value, offset);
	}

	@Override
	public void glUniformMatrix4fv(int location, int count, boolean transpose, FloatBuffer value) {
		log("glUniformMatrix4fv: location = [" + location + "], count = [" + count + "], transpose = [" + transpose + "], value = [" + toString(value) + "]");
		super.glUniformMatrix4fv(location, count, transpose, value);
	}

	@Override
	public void glUniformMatrix4fv(int location, int count, boolean transpose, float[] value, int offset) {
		log("glUniformMatrix4fv: location = [" + location + "], count = [" + count + "], transpose = [" + transpose + "], value = [" + toString(value) + "], offset = [" + offset + "]");
		super.glUniformMatrix4fv(location, count, transpose, value, offset);
	}

	@Override
	public void glUseProgram(int program) {
		log("glUseProgram: program = [" + program + "]");
		super.glUseProgram(program);
	}

	@Override
	public void glValidateProgram(int program) {
		log("glValidateProgram: program = [" + program + "]");
		super.glValidateProgram(program);
	}

	@Override
	public void glVertexAttrib1f(int indx, float x) {
		log("glVertexAttrib1f: indx = [" + indx + "], x = [" + x + "]");
		super.glVertexAttrib1f(indx, x);
	}

	@Override
	public void glVertexAttrib1fv(int indx, FloatBuffer values) {
		log("glVertexAttrib1fv: indx = [" + indx + "], values = [" + toString(values) + "]");
		super.glVertexAttrib1fv(indx, values);
	}

	@Override
	public void glVertexAttrib2f(int indx, float x, float y) {
		log("glVertexAttrib2f: indx = [" + indx + "], x = [" + x + "], y = [" + y + "]");
		super.glVertexAttrib2f(indx, x, y);
	}

	@Override
	public void glVertexAttrib2fv(int indx, FloatBuffer values) {
		log("glVertexAttrib2fv: indx = [" + indx + "], values = [" + toString(values) + "]");
		super.glVertexAttrib2fv(indx, values);
	}

	@Override
	public void glVertexAttrib3f(int indx, float x, float y, float z) {
		log("glVertexAttrib3f: indx = [" + indx + "], x = [" + x + "], y = [" + y + "], z = [" + z + "]");
		super.glVertexAttrib3f(indx, x, y, z);
	}

	@Override
	public void glVertexAttrib3fv(int indx, FloatBuffer values) {
		log("glVertexAttrib3fv: indx = [" + indx + "], values = [" + toString(values) + "]");
		super.glVertexAttrib3fv(indx, values);
	}

	@Override
	public void glVertexAttrib4f(int indx, float x, float y, float z, float w) {
		log("glVertexAttrib4f: indx = [" + indx + "], x = [" + x + "], y = [" + y + "], z = [" + z + "], w = [" + w + "]");
		super.glVertexAttrib4f(indx, x, y, z, w);
	}

	@Override
	public void glVertexAttrib4fv(int indx, FloatBuffer values) {
		log("glVertexAttrib4fv: indx = [" + indx + "], values = [" + toString(values) + "]");
		super.glVertexAttrib4fv(indx, values);
	}

	@Override
	public void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, Buffer ptr) {
		log("glVertexAttribPointer: indx = [" + indx + "], size = [" + size + "], type = [" + type + "], normalized = [" + normalized + "], stride = [" + stride + "], ptr = [" + ptr + "]");
		super.glVertexAttribPointer(indx, size, type, normalized, stride, ptr);
	}

	@Override
	public void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, int ptr) {
		log("glVertexAttribPointer: indx = [" + indx + "], size = [" + size + "], type = [" + type + "], normalized = [" + normalized + "], stride = [" + stride + "], ptr = [" + ptr + "]");
		super.glVertexAttribPointer(indx, size, type, normalized, stride, ptr);
	}

	@Override
	public void present() {
		System.out.println("LoggerGL20.present(" + "" + ")");
		super.present();
	}
}
