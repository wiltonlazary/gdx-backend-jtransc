package com.jtransc.media.limelibgdx.gl;

import com.jtransc.annotation.JTranscNativeClass;
import com.jtransc.annotation.haxe.HaxeMethodBody;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;

@JTranscNativeClass("lime.graphics.GLRenderContext")
public class GL {
	native public void vertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, int ptr);

	// BUFFERS
	native public Wrapped<GLBuffer> createBuffer();

	native public boolean isBuffer(Wrapped<GLBuffer> buffer);

	native public void bindBuffer(int target, Wrapped<GLBuffer> buffer);

	native public void deleteBuffer(Wrapped<GLBuffer> buffer);

	native public boolean isEnabled(int cap);

	native public int getError();

	native public void activeTexture(int texture);

	native public void blendFunc(int sfactor, int dfactor);

	native public void clearColor(float red, float green, float blue, float alpha);

	native public void bindTexture(int target, Wrapped<GLTexture> texture);

	native public void clear(int mask);

	native public void clearDepth(float depth);

	native public void clearStencil(int s);

	native public void colorMask(boolean red, boolean green, boolean blue, boolean alpha);

	native public void compressedTexImage2D(int target, int level, int internalformat, int width, int height, int border, ArrayBufferView arrayBufferView);

	native public void compressedTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, ArrayBufferView arrayBufferView);

	native public void copyTexImage2D(int target, int level, int internalformat, int x, int y, int width, int height, int border);

	native public void copyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y, int width, int height);

	native public void cullFace(int mode);

	native public void deleteTexture(Wrapped<GLTexture> glTexture);

	native public void depthFunc(int func);

	native public void depthMask(boolean flag);

	native public void depthRange(float zNear, float zFar);

	native public void disable(int cap);

	native public void drawArrays(int mode, int first, int count);

	native public void drawElements(int mode, int count, int type, ArrayBufferView arrayBufferView);

	native public void drawElements(int mode, int count, int type, int inidces);

	native public void enable(int cap);

	native public void finish();

	native public void flush();

	native public void frontFace(int mode);

	native public Wrapped<GLTexture> createTexture();

	native public Dynamic getParameter(int pname);

	native public void hint(int target, int mode);

	native public Wrapped<GLProgram> createProgram();

	native public Wrapped<GLShader> createShader(int type);

	native public void compileShader(Wrapped<GLShader> glShader);

	native public void lineWidth(float width);

	native public void pixelStorei(int pname, int param);

	native public void polygonOffset(float factor, float units);

	native public void readPixels(int x, int y, int width, int height, int format, int type, ArrayBufferView arrayBufferView);

	native public void scissor(int x, int y, int width, int height);

	native public void stencilFunc(int func, int ref, int mask);

	native public void stencilMask(int mask);

	native public void stencilOp(int fail, int zfail, int zpass);

	native public void texImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, ArrayBufferView arrayBufferView);

	native public void texParameterf(int target, int pname, float param);

	native public void texSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, ArrayBufferView arrayBufferView);

	native public void viewport(int x, int y, int width, int height);

	native public void attachShader(Wrapped<GLProgram> glProgram, Wrapped<GLShader> glShader);

	native public void bindAttribLocation(Wrapped<GLProgram> glProgram, int index, String name);

	native public void bindRenderbuffer(int target, Wrapped<GLRenderbuffer> glRenderbuffer);

	native public void blendColor(float red, float green, float blue, float alpha);

	native public void blendEquation(int mode);

	native public void blendEquationSeparate(int modeRGB, int modeAlpha);

	native public void blendFuncSeparate(int srcRGB, int dstRGB, int srcAlpha, int dstAlpha);

	native public void bufferData(int target, ArrayBufferView arrayBufferView, int usage);

	native public void bufferSubData(int target, int offset, ArrayBufferView arrayBufferView);

	native public void deleteProgram(Wrapped<GLProgram> glProgram);

	native public void deleteRenderbuffer(Wrapped<GLRenderbuffer> glRenderbuffer);

	native public void deleteShader(Wrapped<GLShader> glShader);

	native public void detachShader(Wrapped<GLProgram> glProgram, Wrapped<GLShader> glShader);

	native public void disableVertexAttribArray(int index);

	native public void enableVertexAttribArray(int index);

	native public Wrapped<GLFramebuffer> createFramebuffer();

	native public void bindFramebuffer(int target, Wrapped<GLFramebuffer> glFramebuffer);

	native public int checkFramebufferStatus(int target);

	native public void deleteFramebuffer(Wrapped<GLFramebuffer> glFramebuffer);

	native public void framebufferRenderbuffer(int target, int attachment, int renderbuffertarget, Wrapped<GLRenderbuffer> glRenderbuffer);

	native public void framebufferTexture2D(int target, int attachment, int textarget, Wrapped<GLTexture> glTexture, int level);

	native public int getFramebufferAttachmentParameter(int target, int attachment, int pname);

	native public void generateMipmap(int target);

	native public Wrapped<GLRenderbuffer> createRenderbuffer();

	native public int getAttribLocation(Wrapped<GLProgram> glProgram, String name);

	native public int getBufferParameter(int target, int pname);

	native public int getProgramParameter(Wrapped<GLProgram> glProgram, int pname);

	native public String getProgramInfoLog(Wrapped<GLProgram> glProgram);

	native public int getRenderbufferParameter(int target, int pname);

	native public int getShaderParameter(Wrapped<GLShader> glShader, int pname);

	native public String getShaderInfoLog(Wrapped<GLShader> glShader);

	native public Wrapped<GLUniformLocation> getUniformLocation(Wrapped<GLProgram> glProgram, String name);

	native public int getVertexAttrib(int index, int pname);

	native public boolean isProgram(Wrapped<GLProgram> glProgram);

	native public boolean isRenderbuffer(Wrapped<GLRenderbuffer> glRenderbuffer);

	native public boolean isShader(Wrapped<GLShader> glShader);

	native public boolean isTexture(Wrapped<GLTexture> glTexture);

	native public void linkProgram(Wrapped<GLProgram> glProgram);

	native public void renderbufferStorage(int target, int internalformat, int width, int height);

	native public void sampleCoverage(float value, boolean invert);

	native public void shaderSource(Wrapped<GLShader> glShader, String string);

	native public void stencilFuncSeparate(int face, int func, int ref, int mask);

	native public void stencilMaskSeparate(int face, int mask);

	native public void stencilOpSeparate(int face, int fail, int zfail, int zpass);

	native public void texParameteri(int target, int pname, int param);

	native public void useProgram(Wrapped<GLProgram> glProgram);

	native public void validateProgram(Wrapped<GLProgram> glProgram);

	native public void uniform1f(Wrapped<GLUniformLocation> glUniformLocation, float x);

	native public void uniform1fv(Wrapped<GLUniformLocation> glUniformLocation, Float32Array float32Array);

	native public void uniformMatrix2fv(Wrapped<GLUniformLocation> glUniformLocation, boolean transpose, Float32Array float32Array);

	native public void vertexAttrib4fv(int indx, Float32Array float32Array);

	native public void vertexAttrib4f(int indx, float x, float y, float z, float w);

	native public void vertexAttrib3fv(int indx, Float32Array float32Array);

	native public void uniformMatrix3fv(Wrapped<GLUniformLocation> glUniformLocation, boolean transpose, Float32Array float32Array);

	native public void uniformMatrix4fv(Wrapped<GLUniformLocation> glUniformLocation, boolean transpose, Float32Array float32Array);

	native public void vertexAttrib1f(int indx, float x);

	native public void vertexAttrib1fv(int indx, Float32Array float32Array);

	native public void vertexAttrib2f(int indx, float x, float y);

	native public void vertexAttrib2fv(int indx, Float32Array float32Array);

	native public void vertexAttrib3f(int indx, float x, float y, float z);

	native public GLShaderPrecisionFormat getShaderPrecisionFormat(int shadertype, int precisiontype);

	native public GLActiveInfo getActiveAttrib(Wrapped<GLProgram> program, int index);

	native public GLActiveInfo getActiveUniform(Wrapped<GLProgram> program, int index);

	native public void uniform1i(Wrapped<GLUniformLocation> glUniformLocation, int x);

	native public void uniform1iv(Wrapped<GLUniformLocation> glUniformLocation, Int32Array int32Array);

	native public void uniform2f(Wrapped<GLUniformLocation> glUniformLocation, float x, float y);

	native public void uniform2fv(Wrapped<GLUniformLocation> glUniformLocation, Float32Array float32Array);

	native public void uniform2i(Wrapped<GLUniformLocation> glUniformLocation, int x, int y);

	native public void uniform2iv(Wrapped<GLUniformLocation> glUniformLocation, Int32Array int32Array);

	native public void uniform3f(Wrapped<GLUniformLocation> glUniformLocation, float x, float y, float z);

	native public void uniform3fv(Wrapped<GLUniformLocation> glUniformLocation, Float32Array float32Array);

	native public void uniform3i(Wrapped<GLUniformLocation> glUniformLocation, int x, int y, int z);

	native public void uniform3iv(Wrapped<GLUniformLocation> glUniformLocation, Int32Array int32Array);

	native public void uniform4f(Wrapped<GLUniformLocation> glUniformLocation, float x, float y, float z, float w);

	native public void uniform4fv(Wrapped<GLUniformLocation> glUniformLocation, Float32Array float32Array);

	native public void uniform4i(Wrapped<GLUniformLocation> glUniformLocation, int x, int y, int z, int w);

	native public void uniform4iv(Wrapped<GLUniformLocation> glUniformLocation, Int32Array int32Array);

	@JTranscNativeClass("Dynamic")
	static public class Dynamic {
		static public class Utils {
			@HaxeMethodBody("return cast(p0, Int);")
			native static public int toInt(Dynamic p0);

			@HaxeMethodBody("return cast(p0, Bool);")
			native public static boolean toBool(Dynamic p0);

			@HaxeMethodBody("return cast(p0, Float);")
			native public static float toFloat(Dynamic p0);

			@HaxeMethodBody("return N.str(cast(p0, String));")
			native public static String toString(Dynamic p0);
		}
	}


	@JTranscNativeClass("lime.utils.ArrayBufferView")
	static public class ArrayBufferView extends Dynamic {
	}

	@JTranscNativeClass("lime.utils.Float32Array")
	static public class Float32Array extends ArrayBufferView {
		static public class Utils {
			@HaxeMethodBody("return new lime.utils.Float32Array(p0.toArray());")
			native static public Float32Array create(float[] data);
		}
	}

	@JTranscNativeClass("lime.utils.Int32Array")
	static public class Int32Array extends ArrayBufferView {
	}

	@JTranscNativeClass("lime.graphics.opengl.GLProgram")
	static public class GLProgram extends Dynamic {
	}

	@JTranscNativeClass("lime.graphics.opengl.GLShader")
	static public class GLShader extends Dynamic {
	}

	@JTranscNativeClass("lime.graphics.opengl.GLTexture")
	static public class GLTexture extends Dynamic {
	}

	@JTranscNativeClass("lime.graphics.opengl.GLBuffer")
	static public class GLBuffer extends Dynamic {
	}

	@JTranscNativeClass("lime.graphics.opengl.GLRenderbuffer")
	static public class GLRenderbuffer extends Dynamic {
	}

	@JTranscNativeClass("lime.graphics.opengl.GLFramebuffer")
	static public class GLFramebuffer extends Dynamic {
	}

	@JTranscNativeClass("lime.graphics.opengl.GLUniformLocation")
	static public class GLUniformLocation extends Dynamic {
	}

	@JTranscNativeClass("HaxeLimeGdxApplication")
	static public class HaxeLimeGdxApplication {
		static public GL gl;

		native public static ArrayBufferView convertBuffer(Buffer buffer, int size);

		native public static Float32Array convertFloatBuffer(FloatBuffer buffer, int size);

		native public static Int32Array convertIntBuffer(IntBuffer buffer, int size);

		native public static Float32Array convertFloatArray(float[] buffer, int offset, int size);

		native public static Int32Array convertIntArray(int[] buffer, int offset, int size);

		native public static void testInit();

		native public static void testFrame();
	}

	@JTranscNativeClass("lime.graphics.opengl.GLShaderPrecisionFormat")
	static public class GLShaderPrecisionFormat {
		public int rangeMin;
		public int rangeMax;
		public int precision;
	}

	@JTranscNativeClass("String")
	static public class HaxeString {
		static public class Utils {
			@HaxeMethodBody("return N.str(p0);")
			native static public String toString(HaxeString str);
		}
	}

	@JTranscNativeClass("lime.graphics.opengl.GLActiveInfo")
	static public class GLActiveInfo {
		public int size;
		public int type;
		public HaxeString name;
	}

	static public class BaseIntMap {
		private HashMap<Integer, Object> map = new HashMap<>();

		public Wrapped<GLShader> getShader(int key) {
			return (Wrapped<GLShader>) map.get(key);
		}

		public Wrapped<GLProgram> getProgram(int key) {
			return (Wrapped<GLProgram>) map.get(key);
		}

		public Wrapped<GLBuffer> getBuffer(int buffer) {
			return (Wrapped<GLBuffer>) map.get(buffer);
		}

		public Wrapped<GLRenderbuffer> getRenderbuffer(int renderbuffer) {
			return (Wrapped<GLRenderbuffer>) map.get(renderbuffer);
		}

		public void set(int key, Wrapped<? extends Dynamic> value) {
			this.map.put(key, value);
		}

		public void setUniformlocation(int id, Wrapped<GLUniformLocation> location) {
			this.map.put(id, location);
		}

		public void remove(int key) {
			this.map.remove(key);
		}

		public boolean exists(int key) {
			return this.map.containsKey(key);
		}

		public Wrapped<GLTexture> getTexture(int texture) {
			return (Wrapped<GLTexture>) map.get(texture);
		}

		public Wrapped<GLFramebuffer> getFramebuffer(int framebuffer) {
			return (Wrapped<GLFramebuffer>) map.get(framebuffer);
		}

		public Wrapped<GLUniformLocation> getUniformlocation(int location) {
			return (Wrapped<GLUniformLocation>) map.get(location);
		}

		public void setRenderbuffer(int id, Wrapped<GLRenderbuffer> renderbuffer) {
			this.map.put(id, renderbuffer);
		}

		public void setBuffer(int id, Wrapped<GLBuffer> buffer) {
			this.map.put(id, buffer);
		}

		public void setShader(int id, Wrapped<GLShader> shader) {
			this.map.put(id, shader);
		}

		public void setProgram(int id, Wrapped<GLProgram> program) {
			this.map.put(id, program);
		}

		public void setTexture(int id, Wrapped<GLTexture> texture) {
			this.map.put(id, texture);
		}


		static public class Utils {
			static public BaseIntMap create() {
				return new BaseIntMap();
			}
		}
	}
}