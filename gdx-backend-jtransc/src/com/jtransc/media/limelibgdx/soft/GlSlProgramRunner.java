package com.jtransc.media.limelibgdx.soft;

import com.jtransc.FastMemory;
import com.jtransc.media.limelibgdx.StateGL20;
import com.jtransc.media.limelibgdx.glsl.ir.GlAsm;

// @TODO: Use jtransc-eval for fastest performance!
public class GlSlProgramRunner {
	public GlAsm[] vertexProgram;
	public GlAsm[] fragmentProgram;

	public FastMemory uniforms;
	public FastMemory arrays;
	public StateGL20.AttribInfo[] attributes;
	public AttributeData[] attributesData = {
		new AttributeData(0), new AttributeData(1), new AttributeData(2), new AttributeData(3),
		new AttributeData(4), new AttributeData(5), new AttributeData(6), new AttributeData(7)
	};
	public FastMemory varyings = FastMemory.alloc(1024 * 4 * 4);
	public FastMemory temps = FastMemory.alloc(1024 * 4 * 4);
	public FastMemory result = FastMemory.alloc(1024 * 4 * 4);

	public int index;

	float[] f1 = new float[16];
	float[] f2 = new float[16];
	float[] f3 = new float[16];

	public void evalVertex(int index) {
		this.index = index;

		for (int n = 0; n < attributes.length; n++) {
			if (attributes[n].enabled) attributesData[n].read(arrays, attributes[n], index);
		}

		GlAsm[] program = this.vertexProgram;

		//System.out.println("Program:");
		for (GlAsm i : program) {
			//System.out.println(i);
			switch (i.op) {
				case GlAsm.OP_COPY:
					read(f1, i.src);
					write(f1, i.target);
					break;
				default:
					throw new RuntimeException("Unsupported operation");
			}
		}
	}

	private FastMemory getReg(GlAsm.Operand op) {
		switch (op.reg) {
			case GlAsm.REG_ATTRIBUTE:
				return attributesData[op.index].data;
			case GlAsm.REG_UNIFORM:
				return uniforms; // @TODO: Fix this!
			case GlAsm.REG_TARGET:
				return result; // @TODO: Fix this!
		}
		throw new RuntimeException("Unsupported register");
	}

	private int getRegOffset(GlAsm.Operand op) {
		switch (op.reg) {
			case GlAsm.REG_ATTRIBUTE:
				return 0;
			case GlAsm.REG_UNIFORM:
				return 0;
			case GlAsm.REG_TARGET:
				return this.index * 4;
		}
		throw new RuntimeException("Unsupported register");
	}

	private void read(float[] out, GlAsm.Operand op) {
		FastMemory mem = getReg(op);
		int offset = getRegOffset(op);
		for (int n = 0; n < op.size; n++) out[n] = mem.getAlignedFloat32(offset + op.lanes[n]);
	}

	private void write(float[] out, GlAsm.Operand op) {
		FastMemory mem = getReg(op);
		int offset = getRegOffset(op);
		for (int n = 0; n < op.size; n++) {
			mem.setAlignedFloat32(offset + op.lanes[n], out[n]);
		}
	}

	public void evalFragment(int x, int y) {
	}

	static private class AttributeData {
		public int atrib;
		public FastMemory data = FastMemory.alloc(16 * 4);

		public AttributeData(int index) {
			this.atrib = index;
		}

		public void read(FastMemory arrays, StateGL20.AttribInfo info, int index) {
			//System.out.println("Reading vertex: " + this.atrib + " : " + index);
			FastMemory.copy(arrays, info.ptr + info.stride * index, data, 0, info.bytesSize);
			// @TODO: Conversions!
		}
	}
}
