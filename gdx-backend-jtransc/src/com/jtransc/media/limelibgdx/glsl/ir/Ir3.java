package com.jtransc.media.limelibgdx.glsl.ir;

/**
 * 3-address Intermediate Representation
 */
abstract public class Ir3 {
	abstract public Ir3 withTarget(Operand operand);

	abstract public Ir3 withReadOperands(Operand[] operands);

	abstract public Operand getTarget();

	abstract public Operand[] getReadOperands();

	static public class Unop extends Ir3 {
		public final Operand target;
		public final UnaryOperator op;
		public final Operand l;

		public Unop(Operand target, UnaryOperator op, Operand l) {
			this.target = target;
			this.op = op;
			this.l = l;
		}

		@Override
		public Ir3 withTarget(Operand target) {
			return new Unop(target, op, l);
		}

		@Override
		public Ir3 withReadOperands(Operand[] operands) {
			return new Unop(target, op, operands[0]);
		}

		@Override
		public Operand getTarget() {
			return target;
		}

		@Override
		public Operand[] getReadOperands() {
			return new Operand[]{l};
		}

		@Override
		public String toString() {
			return op + " " + target + ", " + l;
		}
	}

	static public class Binop extends Ir3 {
		public final Operand target;
		public final BinaryOperator op;
		public final Operand l;
		public final Operand r;

		public Binop(Operand target, BinaryOperator op, Operand l, Operand r) {
			this.target = target;
			this.op = op;
			this.l = l;
			this.r = r;
		}

		@Override
		public Ir3 withTarget(Operand target) {
			return new Binop(target, op, l, r);
		}

		@Override
		public Ir3 withReadOperands(Operand[] operands) {
			return new Binop(target, op, operands[0], operands[1]);
		}

		@Override
		public Operand getTarget() {
			return target;
		}

		@Override
		public Operand[] getReadOperands() {
			return new Operand[]{l, r};
		}

		@Override
		public String toString() {
			return op + " " + target + ", " + l + ", " + r;
		}
	}
}
