package com.jtransc.media.limelibgdx.glsl.ast;

import java.util.ArrayList;

public interface Expr {
	class Id implements Expr {
		public String id;

		public Id(String id) {
			this.id = id;
		}
	}

	class NumberLiteral implements Expr {
		public double value;

		public NumberLiteral(double value) {
			this.value = value;
		}
	}

	class Access implements Expr {
		public final Expr expr;
		public final String field;

		public Access(Expr expr, String read) {
			this.expr = expr;
			this.field = read;
		}
	}

	class ArrayAccess implements Expr {
		public final Expr array;
		public final Expr index;

		public ArrayAccess(Expr expr, Expr expr1) {
			this.array = expr;
			this.index = expr1;
		}
	}

	class Call implements Expr {
		public final String name;
		public final ArrayList<Expr> args;

		public Call(String name, ArrayList<Expr> args) {
			this.name = name;
			this.args = args;
		}
	}

	class UnopPost implements Expr {
		public final Expr expr;
		public final String op;

		public UnopPost(Expr expr, String s) {
			this.expr = expr;
			this.op = s;
		}
	}

	class Unop implements Expr {
		public final String op;
		public final Expr expr;

		public Unop(String s, Expr expr) {
			this.op = s;
			this.expr = expr;
		}
	}

	class Binop implements Expr {
		public final Expr left;
		public final String op;
		public final Expr right;

		public Binop(Expr left, String op, Expr right) {
			this.left = left;
			this.op = op;
			this.right = right;
		}
	}
}