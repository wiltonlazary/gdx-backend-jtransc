package com.jtransc.media.limelibgdx.flash;

import com.jtransc.annotation.JTranscNativeClass;
import com.jtransc.annotation.haxe.HaxeAvailableOnTargets;
import com.jtransc.annotation.haxe.HaxeMethodBody;
import com.jtransc.media.limelibgdx.GL20Ext;
import com.jtransc.media.limelibgdx.StateGL20;
import com.jtransc.media.limelibgdx.flash.agal.Agal;
import com.jtransc.media.limelibgdx.flash.agal.GlSlToAgal;
import com.jtransc.media.limelibgdx.glsl.ShaderType;

import java.nio.Buffer;
import java.util.HashMap;
import java.util.Map;

import static com.jtransc.media.limelibgdx.StateGL20.*;

@HaxeAvailableOnTargets({"flash"})
public class FlashStage3DGL20 {
	static public GL20Ext create() {
		return new StateGL20(new FlashImpl());
	}

	@HaxeAvailableOnTargets({"flash"})
	static private class FlashImpl extends StateGL20.Impl {
		@HaxeMethodBody("return HaxeLimeGdxApplication.context3D;")
		native static Context3D getContext3D();

		@Override
		public void clear(StateGL20.State state, boolean color, boolean depth, boolean stencil) {
			int mask = 0;

			if (color) mask |= Context3DClearMask.COLOR;
			if (depth) mask |= Context3DClearMask.DEPTH;
			if (stencil) mask |= Context3DClearMask.STENCIL;

			if (getContext3D() != null) {
				getContext3D().clear(
					state.clearColor.red,
					state.clearColor.green,
					state.clearColor.blue,
					state.clearColor.alpha,
					state.clearDepth,
					state.clearStencil,
					mask
				);
			}
		}

		@Override
		public Texture createTexture() {
			return new FlashTexture(getContext3D());
		}

		@Override
		public Program createProgram() {
			return new FlashProgram(getContext3D());
		}

		@Override
		public Shader createShader(ShaderType type) {
			return new FlashShader(type);
		}

		@Override
		public GLBuffer createBuffer() {
			return new FlashGLBuffer();
		}

		@Override
		public void drawElements(int mode, int count, int type, Buffer indices) {

		}

		@Override
		public void drawElements(int mode, int count, int type, int indices) {

		}

		@Override
		public void drawArrays(int mode, int first, int count) {

		}

		@Override
		public FrameBuffer createFrameBuffer() {
			return null;
		}

		@Override
		public RenderBuffer createRenderBuffer() {
			return null;
		}

		@Override
		public void render(StateGL20.State state) {
			getContext3D().present();
		}
	}

	@HaxeAvailableOnTargets({"flash"})
	static class FlashGLBuffer implements GLBuffer {
		@Override
		public void dispose() {

		}

		@Override
		public void data(int size, Buffer data, int usage) {

		}

		@Override
		public void subdata(int offset, int size, Buffer data) {

		}
	}

	@HaxeAvailableOnTargets({"flash"})
	static class FlashProgram extends StateGL20.Program {
		Agal.Program agal;
		Context3D context;
		Program3D program;

		public FlashProgram(Context3D context) {
			this.context = context;
			this.program = context.createProgram();
		}

		@Override
		public void dispose() {
			program.dispose();
		}

		@Override
		public void link() {
			Map<String, String> defines = new HashMap<String, String>();
			defines.put("GL_ES", "1");
			defines.put("JTRANSC", "1");
			defines.put("FLASH", "1");

			this.agal = GlSlToAgal.compile(vertex.source, fragment.source, true, defines);

			System.out.println("FlashProgram().vertex:");
			for (String s : agal.vertex.sourceCode) System.out.println(" - " + s);
			System.out.println("FlashProgram().fragment:");
			for (String s : agal.fragment.sourceCode) System.out.println(" - " + s);
			System.out.println("FlashProgram().uniforms:");
			for (Agal.AllocatedLanes lane : agal.getUniforms().values())
				System.out.println(" - " + lane.name + " : " + lane);
			System.out.println("FlashProgram().attributes:");
			for (Agal.AllocatedLanes lane : agal.getAttributes().values())
				System.out.println(" - " + lane.name + " : " + lane);
			System.out.println("FlashProgram().constants:");
			for (Map.Entry<Agal.AllocatedLanes, Double> e : agal.getConstants().entrySet())
				System.out.println(" - " + e.getKey().name + " = " + e.getValue() + " : " + e.getKey());


			this.uniforms.clear();
			this.attributes.clear();
			for (Agal.AllocatedLanes e : this.agal.getUniforms().values()) {
				this.uniforms.add(new ProgramUniform(e.name, e.size, e.type));
			}
			for (Agal.AllocatedLanes e : this.agal.getAttributes().values()) {
				this.attributes.add(new ProgramAttribute(e.name, e.size, e.type));
			}

			program.upload(As3Utils.toByteArray(agal.vertex.binary), As3Utils.toByteArray(agal.fragment.binary));
		}

		@Override
		public String getInfoLog() {
			return "successful linked";
		}

		@Override
		public boolean linked() {
			return true;
		}

		public ProgramAttribute[] boundAttribs = new ProgramAttribute[6];

		@Override
		public void bindAttribLocation(int index, String name) {
			boundAttribs[index] = attributes.get(getAttribLocation(name));
		}
	}

	@HaxeAvailableOnTargets({"flash"})
	static public class FlashShader extends Shader {
		public String source;

		public FlashShader(ShaderType type) {
			super(type);
		}

		@Override
		public void dispose() {
		}

		@Override
		public void setSource(String source) {
			super.setSource(source);
			System.out.println("FlashShader(" + type + ").setSource('" + source + "')");
		}

		@Override
		public void compile() {
			System.out.println("FlashShader.compile");
		}

		@Override
		public String getInfoLog() {
			return "successful compiled";
		}

		@Override
		public boolean compiled() {
			return true;
		}
	}

	@HaxeAvailableOnTargets({"flash"})
	static class FlashTexture implements Texture {
		final Context3D context;
		//Textures.Texture internal;

		public FlashTexture(Context3D context) {
			this.context = context;
		}

		@Override
		public void uploadData(Buffer data, int width, int height) {
			//context.crea
		}

		@Override
		public void compressedTexImage2D(int level, int internalformat, int width, int height, int border, int imageSize, Buffer data) {

		}

		@Override
		public void compressedTexSubImage2D(int level, int xoffset, int yoffset, int width, int height, int format, int imageSize, Buffer data) {

		}

		@Override
		public void copyTexImage2D(int level, int internalformat, int x, int y, int width, int height, int border) {

		}

		@Override
		public void copyTexSubImage2D(int level, int xoffset, int yoffset, int x, int y, int width, int height) {

		}

		@Override
		public void texImage2D(int level, int internalformat, int width, int height, int border, int format, int type, Buffer pixels) {

		}

		@Override
		public void parameter(int pname, float param) {

		}

		@Override
		public void texSubImage2D(int level, int xoffset, int yoffset, int width, int height, int format, int type, Buffer pixels) {

		}

		@Override
		public void generateMipmap() {

		}

		@Override
		public void dispose() {
		}
	}

	@HaxeAvailableOnTargets({"flash"})
	@JTranscNativeClass("flash.display3D.Context3D")
	static class Context3D {
		native public void clear(double red, double green, double blue, double alpha, double depth, int stencil, int mask);

		//native public void configureBackBuffer(int width, int height, int antiAlias, boolean enableDepthAndStencil, boolean wantsBestResolution, boolean wantsBestResolutionOnBrowserZoom);
		//native public Textures.CubeTexture createCubeTexture(int size, String format, boolean optimizeForRenderToTexture, int streamingLevels);
		//native public IndexBuffer3D createIndexBuffer(int numIndices, String bufferUsage);
		native public Program3D createProgram();

		//native public RectangleTexture createRectangleTexture(int width, int height, String format, boolean optimizeForRenderToTexture);
		//native public Textures.Texture createTexture(int width, int height, String format, boolean optimizeForRenderToTexture, int streamingLevels);
		//native public VertexBuffer3D createVertexBuffer(int numVertices, int data32PerVertex, String bufferUsage);
		//native public VertexBuffer3D createVertexBufferForInstances(int numVertices, int data32PerVertex, int instancesPerElement, String bufferUsage);
		//native public VideoTexture createVideoTexture();
		//native public void dispose(boolean recreate);
		//native public void drawToBitmapData(BitmapData destination);
		//native public void drawTriangles(IndexBuffer3D indexBuffer, int firstIndex, int numTriangles);
		//native public void drawTrianglesInstanced(IndexBuffer3D indexBuffer, int numInstances, int firstIndex, int numTriangles);
		native public void present();
		//native public void setBlendFactors(String sourceFactor, String destinationFactor);
		//native public void setColorMask(boolean red, boolean green, boolean blue, boolean alpha);
		//native public void setCulling(String triangleFaceToCull);
		//native public void setDepthTest(boolean depthMask, String passCompareMode);
		//native public void setFillMode(String fillMode);
		//native public void setProgram(Program3D program);
		//native public void setProgramConstantsFromByteArray(String programType, int firstRegister, int numRegisters, byte[] data, int byteArrayOffset);
		//native public void setProgramConstantsFromMatrix(String programType, int firstRegister, Matrix3D matrix, boolean transposedMatrix);
		//native public void setProgramConstantsFromVector(String programType, int firstRegister, double[] data, int numRegisters);
		//native public void setRenderToBackBuffer();
		//native public void setRenderToTexture(Textures.TextureBase texture, boolean enableDepthAndStencil, int antiAlias, int surfaceSelector, int colorOutputIndex);
		//native public void setSamplerStateAt(int sampler, String wrap, String filter, String mipfilter);
		//native public void setScissorRectangle(Rectangle rectangle);
		//native public void setStencilActions(String triangleFace, String compareMode, String actionOnBothPass, String actionOnDepthFail, String actionOnDepthPassStencilFail);
		//native public void setStencilReferenceValue(int referenceValue, int readMask, int writeMask);
		//native public void setTextureAt(int sampler, Textures.TextureBase texture);
		//native public void setVertexBufferAt(int index, VertexBuffer3D buffer, int bufferOffset, String format);

		public int backBufferHeight;
		public int backBufferWidth;
		public String driverInfo;
		public boolean enableErrorChecking;
		public int maxBackBufferHeight;
		public int maxBackBufferWidth;
		public String profile;
		public boolean supportsVideoTexture;
		public double totalGPUMemory;
	}

	// http://wonderfl.net/c/qc87
	@HaxeAvailableOnTargets({"flash"})
	@JTranscNativeClass("flash.display3D.Context3DClearMask")
	static class ClearMask {
		static public final int COLOR = 1;
		static public final int DEPTH = 2;
		static public final int STENCIL = 4;
		static public final int ALL = COLOR | DEPTH | STENCIL;
	}

	@HaxeAvailableOnTargets({"flash"})
	static class Textures {
		@JTranscNativeClass("flash.display3D.textures.Texture")
		static class Texture {
			native public void uploadCompressedTextureFromByteArray(byte[] data, int byteArrayOffset, boolean async);

			//native public void uploadFromBitmapData(BitmapData source, int miplevel);
			native public void uploadFromByteArray(byte[] data, int byteArrayOffset, int miplevel);
		}

		@JTranscNativeClass("flash.display3D.textures.CubeTexture")
		static class CubeTexture {

		}
	}

	//@HaxeAddMembers({
	//	"#if flash var context:flash.display3D.Context3D; #end"
	//})
	//static class Context3D {
	//	@HaxeMethodBody(target = "flash", value = "context.clear(p0, p1, p2, p3, p4, p5, p6);")
	//	public void clear(double red, double green, double blue, double alpha, double depth, int stencil, int mask) {
	//	}
	//}

	@HaxeAvailableOnTargets({"flash"})
	static class As3Utils {
		@HaxeMethodBody("return p0.getBytesData();")
		native static public ByteArray toByteArray(byte[] bytes);
	}

	@HaxeAvailableOnTargets({"flash"})
	@JTranscNativeClass("flash.utils.ByteArray")
	static class ByteArray {
	}

	@HaxeAvailableOnTargets({"flash"})
	@JTranscNativeClass("flash.display.BitmapData")
	static class BitmapData {
	}

	@HaxeAvailableOnTargets({"flash"})
	@JTranscNativeClass("flash.display3D.Program3D")
	static class Program3D {
		native public void dispose();

		native public void upload(ByteArray vertexProgram, ByteArray fragmentProgram);
	}

	@HaxeAvailableOnTargets({"flash"})
	static class Context3DClearMask {
		static public final int COLOR = 1;
		static public final int DEPTH = 2;
		static public final int STENCIL = 4;
		static public final int ALL = COLOR | DEPTH | STENCIL;
	}
}
