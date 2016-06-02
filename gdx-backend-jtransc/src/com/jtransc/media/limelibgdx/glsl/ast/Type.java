package com.jtransc.media.limelibgdx.glsl.ast;

abstract public class Type {
	abstract public String getName();
	abstract public int getLaneCount();

	public boolean isVector() {
		return this instanceof VectorType;
	}

	public boolean isMatrix() {
		return this instanceof MatrixType;
	}

	@Override
	public String toString() {
		return getName();
	}

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

		@Override
		public String getName() {
			return name;
		}

		@Override
		public int getLaneCount() {
			return 1;
		}
	}

	static public class VectorType extends Type {
		private final Prim type;
		private final int size;
		private final String name;

		public VectorType(Prim type, int size) {
			this.type = type;
			this.size = size;

			if (size < 2 || size > 4) {
				throw new RuntimeException("Vector type size must be between 2 and 4 but was " + size);
			}

			if (type == FLOAT) {
				this.name = "vec" + size;
			} else if (type == INT) {
				this.name = "ivec" + size;
			} else if (type == UINT) {
				this.name = "uivec" + size;
			} else if (type == BOOL) {
				this.name = "bvec" + size;
			} else if (type == DOUBLE) {
				this.name = "dvec" + size;
			} else {
				throw new RuntimeException("Unknown primitive type " + type);
			}
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public int getLaneCount() {
			return size;
		}
	}

	static public class MatrixType extends Type {
		private final Prim type;
		private final int columns;
		private final int rows;
		private final String name;

		public MatrixType(Prim type, int columns, int rows) {
			this.type = type;
			this.columns = columns;
			this.rows = rows;
			if (columns == rows) {
				name = "mat" + columns;
			} else {
				name = "mat" + columns + "x" + rows;
			}
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public int getLaneCount() {
			return columns * rows;
		}
	}

	//static public class ArrayType extends Type {
	//	private final Type type;
	//
	//	public ArrayType(Type type) {
	//		this.type = type;
	//	}
	//
	//	@Override
	//	public String getName() {
	//		return null;
	//	}
	//
	//	@Override
	//	public int getLaneCount() {
	//		return 0;
	//	}
	//}

	static public final Prim VOID = new Prim("void");
	static public final Prim BOOL = new Prim("bool");
	static public final Prim INT = new Prim("int");
	static public final Prim SAMPLER2D = new Prim("sampler2D"); // INT
	static public final Prim UINT = new Prim("uint");
	static public final Prim FLOAT = new Prim("float");
	static public final Prim DOUBLE = new Prim("double");

	static public final VectorType VEC2 = new VectorType(FLOAT, 2);
	static public final VectorType VEC3 = new VectorType(FLOAT, 3);
	static public final VectorType VEC4 = new VectorType(FLOAT, 4);

	static public final VectorType BVEC2 = new VectorType(BOOL, 2);
	static public final VectorType BVEC3 = new VectorType(BOOL, 3);
	static public final VectorType BVEC4 = new VectorType(BOOL, 4);

	static public final VectorType IVEC2 = new VectorType(INT, 2);
	static public final VectorType IVEC3 = new VectorType(INT, 3);
	static public final VectorType IVEC4 = new VectorType(INT, 4);

	static public final VectorType UVEC2 = new VectorType(UINT, 2);
	static public final VectorType UVEC3 = new VectorType(UINT, 3);
	static public final VectorType UVEC4 = new VectorType(UINT, 4);

	static public final VectorType DVEC2 = new VectorType(DOUBLE, 2);
	static public final VectorType DVEC3 = new VectorType(DOUBLE, 3);
	static public final VectorType DVEC4 = new VectorType(DOUBLE, 4);

	static public final MatrixType MAT2 = new MatrixType(FLOAT, 2, 2);
	static public final MatrixType MAT3 = new MatrixType(FLOAT, 3, 3);
	static public final MatrixType MAT4 = new MatrixType(FLOAT, 4, 4);
}