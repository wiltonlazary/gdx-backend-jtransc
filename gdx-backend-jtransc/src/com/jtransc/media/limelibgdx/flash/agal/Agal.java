package com.jtransc.media.limelibgdx.flash.agal;

import java.util.ArrayList;
import java.util.HashMap;

public class Agal {
	static public class Register {
		enum Type {
			Attribute(0, "va"),
			Constant(1, "vc"),
			Temporary(2, "t"),
			Output(3, "op"),
			Varying(4, "v"),
			Sampler(5, "s"),
			Fragment(6, "f"),
			;

			public int index;
			public String prefix;

			Type(int index, String prefix) {
				this.index = index;
				this.prefix = prefix;
			}

			public String getName(int index) {
				if (this == Output) {
					return this.prefix;
				} else {
					return this.prefix + index;
				}
			}
		}

		public Type type;
		public int index;
		public String swizzle;

		public Register(Type type, int index, String swizzle) {
			this.type = type;
			this.index = index;
			this.swizzle = swizzle;
		}

		@Override
		public String toString() {
			if (swizzle == null) {
				return type.getName(index);
			} else {
				return (type.getName(index)) + "." + swizzle;
			}
		}
	}

	static public class Subnames {
		int lastId = 0;
		HashMap<String, Integer> names = new HashMap<>();

		public int alloc(String name) {
			if (!names.containsKey(name)) {
				names.put(name, lastId++);
			}
			return names.get(name);
		}
	}

	static public class Names {
		private Subnames[] names = new Subnames[]{
			new Subnames(), new Subnames(), new Subnames(), new Subnames(), new Subnames(), new Subnames(),
			new Subnames(), new Subnames()
		};

		public Subnames get(Register.Type type) {
			return names[type.index];
		}

		HashMap<Integer, Double> constants = new HashMap<>();

		public void addFixedConstant(int id, double constant) {
			constants.put(id, constant);
			System.out.println("Constant" + id + "=" + constant);
		}
	}

	static public class Program {
		public final Result vertex;
		public final Result fragment;

		public Program(Result vertex, Result fragment) {
			this.vertex = vertex;
			this.fragment = fragment;
		}
	}

	enum Opcode {
		mov(0x00),
		add(0x01),
		sub(0x02),
		mul(0x03),
		div(0x04),
		rcp(0x05),
		min(0x06),
		max(0x07),
		frc(0x08),
		sqt(0x09),
		rsq(0x0a),
		pow(0x0b),
		log(0x0c),
		exp(0x0d),
		nrm(0x0e),
		sin(0x0f),
		cos(0x10),
		crs(0x11),
		dp3(0x12),
		dp4(0x13),
		abs(0x14),
		neg(0x15),
		sat(0x16),
		m33(0x17),
		m44(0x18),
		m34(0x19),
		kil(0x27),
		tex(0x28),
		sge(0x29),
		slt(0x2a),
		seq(0x2c),
		sne(0x2d),
		ddx(0x1a),
		ddy(0x1b),
		ife(0x1c),
		ine(0x1d),
		ifg(0x1e),
		ifl(0x1f),
		els(0x20),
		eif(0x21),;

		public final int opcode;

		Opcode(int opcode) {
			this.opcode = opcode;
		}
	}

	static public class Result {
		public ArrayList<String> sourceCode;
		public String[] sourceCodeArray;
		public byte[] binary;

		public Result(ArrayList<String> sourceCode, byte[] binary) {
			this.sourceCode = sourceCode;
			this.sourceCodeArray = sourceCode.toArray(new String[sourceCode.size()]);
			this.binary = binary;
		}
	}

	static public class Assembler {
		public ArrayList<String> sourceCode = new ArrayList<String>();

		public Result generateResult() {
			return new Result(sourceCode, new byte[0]);
		}

		protected void out(Opcode opcode, Register dst, Register l) {
			sourceCode.add(opcode.name() + " " + dst + ", " + l);
		}

		protected void out(Opcode opcode, Register dst, Register l, Register r) {
			sourceCode.add(opcode.name() + " " + dst + ", " + l + ", " + r);
		}

		public void mov(Register dst, Register l) {
			out(Opcode.mov, dst, l);
		}

		public void add(Register dst, Register l, Register r) {
			out(Opcode.add, dst, l, r);
		}

		public void sub(Register dst, Register l, Register r) {
			out(Opcode.sub, dst, l, r);
		}

		public void mul(Register dst, Register l, Register r) {
			out(Opcode.mul, dst, l, r);
		}

		public void div(Register dst, Register l, Register r) {
			out(Opcode.div, dst, l, r);
		}

		public void rcp(Register dst, Register l, Register r) {
			out(Opcode.rcp, dst, l, r);
		}

		public void min(Register dst, Register l, Register r) {
			out(Opcode.min, dst, l, r);
		}

		public void max(Register dst, Register l, Register r) {
			out(Opcode.max, dst, l, r);
		}

		// fractional:
		public void frc(Register dst, Register l) {
			out(Opcode.frc, dst, l);
		}

		public void sqt(Register dst, Register l) {
			out(Opcode.sqt, dst, l);
		}

		public void tex(Register dst, Register l, Register r) {
			out(Opcode.tex, dst, l, r);
		}
	}
}