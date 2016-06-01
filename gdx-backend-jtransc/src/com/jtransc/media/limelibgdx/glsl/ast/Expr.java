package com.jtransc.media.limelibgdx.glsl.ast;

import java.util.ArrayList;

public interface Expr {
	static public class Id implements Expr {
		public String id;

		public Id(String id) {
			this.id = id;
		}
	}

	static public class Access implements Expr {
		public final Expr expr;
		public final String field;

		public Access(Expr expr, String read) {
			this.expr = expr;
			this.field = read;
		}
	}

	static public class ArrayAccess implements Expr {
		public final Expr expr;
		public final Expr expr1;

		public ArrayAccess(Expr expr, Expr expr1) {
			this.expr = expr;
			this.expr1 = expr1;
		}
	}

	static public class Call implements Expr {
		public final String name;
		public final ArrayList<Expr> args;

		public Call(String name, ArrayList<Expr> args) {
			this.name = name;
			this.args = args;
		}
	}

	static public class UnopPost implements Expr {
		public final Expr expr;
		public final String s;

		public UnopPost(Expr expr, String s) {
			this.expr = expr;
			this.s = s;
		}
	}

	static public class Unop implements Expr {
		public final String s;
		public final Expr expr;

		public Unop(String s, Expr expr) {
			this.s = s;
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