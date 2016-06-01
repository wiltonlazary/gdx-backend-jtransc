package com.jtransc.media.limelibgdx.flash;

public class Agal {
	static public class Source {

	}

	static public class Names {

	}

	static public class Program {

	}

	enum Type {
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
		eif(0x21),
		;

		public final int opcode;

		Type(int opcode) {
			this.opcode = opcode;
		}
	}

	static public class Assembler {
		protected void out(Type type, Source dst, Source l) {
			System.out.println(type.name() + " " + dst + ", " + l);
		}

		protected void out(Type type, Source dst, Source l, Source r) {
			System.out.println(type.name() + " " + dst + ", " + l + ", " + r);
		}

		public void mov(Source dst, Source l) {
			out(Type.mov, dst, l);
		}

		public void add(Source dst, Source l, Source r) {
			out(Type.add, dst, l, r);
		}

		public void sub(Source dst, Source l, Source r) {
			out(Type.sub, dst, l, r);
		}

		public void mul(Source dst, Source l, Source r) {
			out(Type.mul, dst, l, r);
		}

		public void div(Source dst, Source l, Source r) {
			out(Type.div, dst, l, r);
		}

		public void rcp(Source dst, Source l, Source r) {
			out(Type.rcp, dst, l, r);
		}

		public void min(Source dst, Source l, Source r) {
			out(Type.min, dst, l, r);
		}

		public void max(Source dst, Source l, Source r) {
			out(Type.max, dst, l, r);
		}

		// fractional:
		public void frc(Source dst, Source l) {
			out(Type.frc, dst, l);
		}

		public void sqt(Source dst, Source l) {
			out(Type.sqt, dst, l);
		}

		public void tex(Source dst, Source l, Source r) {
			out(Type.tex, dst, l, r);
		}
	}
}