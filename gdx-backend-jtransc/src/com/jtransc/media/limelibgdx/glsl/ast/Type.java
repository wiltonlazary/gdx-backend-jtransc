package com.jtransc.media.limelibgdx.glsl.ast;

public class Type {
	static public class Prim extends Type {
		private final String name;

		public Prim(String name) {
			this.name = name;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			Prim prim = (Prim) o;

			return name != null ? name.equals(prim.name) : prim.name == null;

		}

		@Override
		public int hashCode() {
			return name != null ? name.hashCode() : 0;
		}
	}

	static public Prim VoidType = new Prim("void");
}