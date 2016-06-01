package com.jtransc.media.limelibgdx.glsl.ast;

import java.util.ArrayList;
import java.util.List;

public class Decl {
	public final String name;

	public Decl(String name) {
		this.name = name;
	}

	static public class Precision extends Decl {
		public final String prec;
		public final Type type;

		public Precision(String prec, Type type) {
			super(prec);
			this.prec = prec;
			this.type = type;
		}
	}

	static public class Global extends Decl {
		public final String kind;
		public final List<String> mods;
		public final Type type;
		public final String name;

		public Global(String kind, List<String> mods, Type type, String name) {
			super(name);
			this.kind = kind;
			this.mods = mods;
			this.type = type;
			this.name = name;
		}
	}

	static public class Function extends Decl {
		public final Type type;
		public final String name;
		public final ArrayList<Argument> arguments;
		public final Stm body;

		public Function(Type type, String name, ArrayList<Argument> arguments, Stm body) {
			super(name);
			this.type = type;
			this.name = name;
			this.arguments = arguments;
			this.body = body;
		}
	}
}