package com.jtransc.media.limelibgdx.glsl.ir;

/**
 * Stack-based Intermediate Representation
 */
public class Sir {
	static public class Get extends Sir {
		public final Operand operand;

		public Get(Operand operand) {
			this.operand = operand;
		}

		@Override
		public String toString() {
			return "push " + operand.toString();
		}
	}

	static public class Unop extends Sir {
		public final UnaryOperator operator;

		public Unop(UnaryOperator operator) {
			this.operator = operator;
		}

		@Override
		public String toString() {
			return operator.toString();
		}
	}

	static public class Binop extends Sir {
		public final BinaryOperator operator;

		public Binop(BinaryOperator operator) {
			this.operator = operator;
		}

		@Override
		public String toString() {
			return operator.toString();
		}
	}

	static public class Set extends Sir {
		public final Operand target;

		public Set(Operand target) {
			this.target = target;
		}

		@Override
		public String toString() {
			return "set " + target.toString();
		}
	}
}
