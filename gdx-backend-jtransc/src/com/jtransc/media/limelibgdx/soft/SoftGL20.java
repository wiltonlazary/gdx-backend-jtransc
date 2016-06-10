package com.jtransc.media.limelibgdx.soft;

import com.badlogic.gdx.graphics.GL20;
import com.jtransc.FastMemory;
import com.jtransc.media.limelibgdx.StateGL20;
import com.jtransc.media.limelibgdx.glsl.ir.GlAsm;
import com.jtransc.media.limelibgdx.glsl.ir.Ir3;

import java.nio.Buffer;

public class SoftGL20 {
	static public StateGL20<SoftImpl> create(int width, int height) {
		return new StateGL20<>(new SoftImpl(width, height));
	}

	static public class SoftImpl extends StateGL20.Impl {
		public BitmapData32 backbuffer;
		public BitmapData32 stencilbuffer;
		public BitmapDataFloat depthbuffer;

		public SoftImpl(int width, int height) {
			this.stencilbuffer = new BitmapData32(width, height);
			this.backbuffer = new BitmapData32(width, height);
			this.depthbuffer = new BitmapDataFloat(width, height);
		}

		@Override
		public void clear(boolean color, boolean depth, boolean stencil) {
			int color32 = state.clearColor.toColor32();
			if (depth) depthbuffer.fill(state.clearDepth);
			if (color) backbuffer.fill(color32);
			if (stencil) stencilbuffer.fill(state.clearStencil);
		}

		@Override
		public StateGL20.Program createProgram() {
			return new Program();
		}

		@Override
		public StateGL20.GLBuffer createBuffer() {
			return new GLBuffer();
		}

		public GlSlProgramRunner runner = new GlSlProgramRunner();
		Rasterizer rasterizer = new Rasterizer(runner);

		@Override
		// https://www.opengl.org/sdk/docs/man/html/glDrawArrays.xhtml
		public void drawArrays(int mode, int first, int count) {
			final Program program = (Program) state.program;
			final GLBuffer buffer = (GLBuffer) state.arrayBuffer;
			super.drawArrays(mode, first, count);
			if (mode != GL20.GL_TRIANGLES) throw new RuntimeException("Just rendering triangles");

			runner.attributes = state.attribs;
			runner.arrays = buffer.data;
			runner.vertexProgram = program.agal.vertex.asmList.toArray(new GlAsm[0]);
			runner.fragmentProgram = program.agal.fragment.asmList.toArray(new GlAsm[0]);

			//System.out.println(buffer.data.getLength());
			for (int n = 0; n < count; n++) {
				runner.evalVertex(n);
			}

			for (int n = 0; n < count; n += 3) {
				rasterizer.renderTriangle(state, backbuffer, n + 0, n + 1, n + 2);
			}
		}
	}

	static private class Rasterizer {
		public GlSlProgramRunner runner;
		private StateGL20.State state;
		private BitmapData32 backbuffer;

		public Rasterizer(GlSlProgramRunner runner) {
			this.runner = runner;
		}

		public void renderTriangle(StateGL20.State state, BitmapData32 backbuffer, int v0, int v1, int v2) {
			this.state = state;
			this.backbuffer = backbuffer;

			int x0 = getScreenX(v0);
			int x1 = getScreenX(v1);
			int x2 = getScreenX(v2);

			int y0 = getScreenY(v0);
			int y1 = getScreenY(v1);
			int y2 = getScreenY(v2);

			int ymin = Math.min(Math.min(y0, y1), y2);
			int ymax = Math.max(Math.max(y0, y1), y2);

			for (int row = ymin; row <= ymax; row++) {

			}

			System.out.println(x0);
			System.out.println(x1);
			System.out.println(x2);

			System.out.println(y0);
			System.out.println(y1);
			System.out.println(y2);
		}

		private int getVertexOffset(int v) {
			return v * 4;
		}

		static private float transform(float value, float min1, float max1, float min2, float max2) {
			//System.out.println("Rasterizer.transform(" + "value = [" + value + "], min1 = [" + min1 + "], max1 = [" + max1 + "], min2 = [" + min2 + "], max2 = [" + max2 + "]" + ")");
			float v0 = (value - min1) / (max1 - min1);
			//System.out.println("->" + v0);
			return (v0 * (max2 - min2)) + min2;
		}

		// @TODO: Use viewport + coordinates
		private int getScreenX(int v) {
			return (int)transform(getX(v), -1, +1, 0, backbuffer.width - 1);
		}

		private int getScreenY(int v) {
			return (int)transform(getY(v), -1, +1, 0, backbuffer.height - 1);
		}

		private float getX(int v) {
			return runner.result.getAlignedFloat32(getVertexOffset(v) + 0);
		}

		private float getY(int v) {
			return runner.result.getAlignedFloat32(getVertexOffset(v) + 4);
		}
	}

	static public class Program extends StateGL20.Program {
		@Override
		public void link() {
			//System.out.println(fragment.data.attributes);
			//System.out.println(fragment.data.uniforms);
			super.link();
		}
	}

	static public class GLBuffer extends StateGL20.GLBuffer {
		public FastMemory data;

		@Override
		public void data(int size, Buffer data, int usage) {
			this.data = FastMemoryUtils.copy(data);
		}
	}
}
