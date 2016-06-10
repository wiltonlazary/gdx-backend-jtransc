package com.jtransc.media.limelibgdx.glsl.ir;

import java.util.Arrays;

public class GlAsm {
	static public final int REG_UNIFORM = 0;
	static public final int REG_ATTRIBUTE = 1;
	static public final int REG_VARYING = 2;
	static public final int REG_TARGET = 3;

	static public final int OP_COPY = 0;
	static public final int OP_MUL = 1;

	static public final int TYPE_INT = 0;
	static public final int TYPE_FLOAT = 1;

	public int op;
	public int type;

	public Operand target;
	public Operand src;
	public Operand dst;

	public GlAsm(int op, int type, Operand target, Operand src, Operand dst) {
		this.op = op;
		this.type = type;
		this.target = target;
		this.src = src;
		this.dst = dst;
	}

	static public class Operand {
		public int reg;
		public int index;
		public int size;
		public int[] lanes = {0, 1, 2, 3};

		public Operand(int reg, int index, int size, int[] swizzle) {
			this.reg = reg;
			this.index = index;
			this.size = size;
			this.lanes = swizzle;
		}

		@Override
		public String toString() {
			return "Operand{" +
				"reg=" + reg +
				", index=" + index +
				", size=" + size +
				", swizzle=" + Arrays.toString(lanes) +
				'}';
		}
	}

	@Override
	public String toString() {
		return "GlAsm{" +
			"op=" + op +
			", type=" + type +
			", target=" + target +
			", src=" + src +
			", dst=" + dst +
			'}';
	}
}
