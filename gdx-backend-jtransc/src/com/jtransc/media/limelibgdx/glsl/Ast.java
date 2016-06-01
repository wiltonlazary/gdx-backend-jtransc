package com.jtransc.media.limelibgdx.glsl;

import java.util.ArrayList;
import java.util.List;

public interface Ast {
	class Program implements Ast {
		List<Decl> decls = new ArrayList<Decl>();

		public Program(List<Decl> decls) {
			this.decls = decls;
		}
	}

	interface Decl extends Ast {
		class Precision implements Decl {
			public final String prec;
			public final Type type;

			public Precision(String prec, Type type) {
				this.prec = prec;
				this.type = type;
			}
		}

		class Varying implements Decl {
			public final List<String> mods;
			public final Type type;
			public final String name;

			public Varying(List<String> mods, Type type, String name) {

				this.mods = mods;
				this.type = type;
				this.name = name;
			}
		}

		class Function implements Decl {
			public final Type type;
			public final String name;
			public final ArrayList<Argument> arguments;
			public final Stm body;

			public Function(Type type, String name, ArrayList<Argument> arguments, Stm body) {
				this.type = type;
				this.name = name;
				this.arguments = arguments;
				this.body = body;
			}
		}
	}

	interface Type extends Ast {
		class Prim implements Type {
			private final String name;

			public Prim(String name) {
				this.name = name;
			}
		}
	}

	interface Stm extends Ast {
		class Stms implements Stm {
			public ArrayList<Stm> stms;

			public Stms(ArrayList<Stm> stms) {

				this.stms = stms;
			}
		}

		class ExprStm implements Stm {
			public Expr expr;

			public ExprStm(Expr expr) {
				this.expr = expr;
			}
		}
	}

	interface Expr extends Ast {
	}

	class Argument {
		public final Type type;
		public final String name;

		public Argument(Type type, String name) {
			this.type = type;
			this.name = name;
		}
	}
}
