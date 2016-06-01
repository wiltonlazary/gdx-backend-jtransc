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
		public class Precision implements Decl {
			public final String prec;
			public final Type type;

			public Precision(String prec, Type type) {this.prec = prec;
				this.type = type;
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
	}
	interface Expr extends Ast {
	}
}
