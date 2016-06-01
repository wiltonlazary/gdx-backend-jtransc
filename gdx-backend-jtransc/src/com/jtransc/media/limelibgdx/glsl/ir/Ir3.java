package com.jtransc.media.limelibgdx.glsl.ir;

/**
 * 3-address Intermediate Representation
 */
public class Ir3 {
	static public class Unop extends Ir3 {
		public final Operand target;
		public final Operator op;
		public final Operand l;

		public Unop(Operand target, Operator op, Operand l) {
			this.target = target;
			this.op = op;
			this.l = l;
		}

		@Override
		public String toString() {
			return op + " " + target + ", " + l;
		}
	}

	static public class Binop extends Ir3 {
		public final Operand target;
		public final Operator op;
		public final Operand l;
		public final Operand r;

		public Binop(Operand target, Operator op, Operand l, Operand r) {
			this.target = target;
			this.op = op;
			this.l = l;
			this.r = r;
		}

		@Override
		public String toString() {
			return op + " " + target + ", " + l + ", " + r;
		}
	}
}
