package com.jtransc.media.limelibgdx.flash.agal;

import com.jtransc.media.limelibgdx.StateGL20;
import com.jtransc.media.limelibgdx.glsl.Lane;
import com.jtransc.media.limelibgdx.glsl.ShaderType;
import com.jtransc.media.limelibgdx.glsl.ast.Type;
import com.jtransc.media.limelibgdx.util.BinWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Agal {
	static public class AllocatedLanes {
		public final String name;
		public final Type type;
		public final int register;
		public final int laneStart;
		public final int laneEnd;
		public final int size;

		public AllocatedLanes(String name, Type type, int register, int laneStart, int laneEnd) {
			this.name = name;
			this.type = type;
			this.register = register;
			this.laneStart = laneStart;
			this.laneEnd = laneEnd;
			this.size = laneEnd - laneStart;
		}

		static private char getSwizzle(int index) {
			switch (index) {
				case 0:
					return 'x';
				case 1:
					return 'y';
				case 2:
					return 'z';
				case 3:
					return 'w';
				default:
					return '?';
			}
		}

		public String getSuffix(String swizzle) {
			String baseSwizzle = "xyzw".substring(0, this.size);
			if (swizzle == null) swizzle = baseSwizzle;
			if (Objects.equals(swizzle, baseSwizzle) && swizzle.length() == 4) {
				return "";
			} else {
				String out = ".";
				for (char c : swizzle.toCharArray()) {
					out += getSwizzle(laneStart + Lane.getLaneIndex(c));
				}
				return out;
			}
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			AllocatedLanes that = (AllocatedLanes) o;

			if (register != that.register) return false;
			if (laneStart != that.laneStart) return false;
			if (laneEnd != that.laneEnd) return false;
			if (size != that.size) return false;
			if (name != null ? !name.equals(that.name) : that.name != null) return false;
			return type != null ? type.equals(that.type) : that.type == null;

		}

		@Override
		public int hashCode() {
			int result = name != null ? name.hashCode() : 0;
			result = 31 * result + (type != null ? type.hashCode() : 0);
			result = 31 * result + register;
			result = 31 * result + laneStart;
			result = 31 * result + laneEnd;
			result = 31 * result + size;
			return result;
		}

		@Override
		public String toString() {
			return "AllocatedLanes(" + name + "," + type + "," + register + "," + laneStart + "-" + laneEnd + ")";
		}

		//public String getSwizzle() {
		//	String out = "";
		//	for (int lane : lanes) {
		//		out += getSwizzle(lane);
		//	}
		//
		//	return out;
		//}
	}

	static public class Register {
		enum Type {
			Attribute(0, "va"),
			Constant(1, "vc"),
			Temporary(2, "t"),
			Output(3, "op"),
			Varying(4, "v"),
			Sampler(5, "s"),
			Fragment(6, "f"),;

			public int index;
			public String prefix;

			Type(int index, String prefix) {
				this.index = index;
				this.prefix = prefix;
			}

			public String getName(AllocatedLanes index) {
				if (this == Output) {
					return this.prefix;
				} else {
					return this.prefix + index.register;
				}
			}
		}

		public Type type;
		public AllocatedLanes register;
		public String swizzle;

		public Register(Type type, AllocatedLanes index, String swizzle) {
			this.type = type;
			this.register = index;
			this.swizzle = swizzle;
		}

		@Override
		public String toString() {
			return type.getName(register) + register.getSuffix(swizzle);
		}

		public int getValue() {
			return 0;
		}
	}

	static public class Subnames {
		int lastId = 0;
		int laneId = 0;
		public HashMap<String, AllocatedLanes> names = new HashMap<>();
		public HashMap<String, Integer> sizes = new HashMap<>();

		private int availableLanes() {
			return 4 - laneId;
		}

		public AllocatedLanes allocSize(String name, Type type, int size) {
			if (size < 1 || size > 4) throw new RuntimeException("Invalid size for lanes " + size);
			if (size > availableLanes()) {
				lastId++;
				laneId = 0;
			}
			//System.out.println("lastId:" + lastId + ",laneId:" + laneId + ",size:" + size);
			try {
				return new AllocatedLanes(name, type, lastId, laneId, laneId + size);
			} finally {
				laneId += size;
			}
		}

		public AllocatedLanes alloc(String name, Type type, int size) {
			if (!names.containsKey(name)) {
				names.put(name, allocSize(name, type, size));
				sizes.put(name, size);
			}
			AllocatedLanes out = names.get(name);
			if (out.size != size) {
				throw new RuntimeException("Repeated allocation " + name + " with different sizes : " + size + " != " + out.size);
			}
			if (!Objects.equals(out.type, type)) {
				throw new RuntimeException("Repeated allocation " + name + " with different type : " + type + " != " + out.type);
			}
			return out;
		}
	}

	static public class Names {
		private Subnames[] names = new Subnames[]{
				new Subnames(), new Subnames(), new Subnames(), new Subnames(), new Subnames(), new Subnames(),
				new Subnames(), new Subnames()
		};

		public Subnames get(Register.Type type) {
			return names[type.index];
		}

		public HashMap<Agal.AllocatedLanes, Double> constants = new HashMap<>();

		public void addFixedConstant(Agal.AllocatedLanes id, double constant) {
			constants.put(id, constant);
			//System.out.println("Constant" + id + "=" + constant);
		}
	}

	static public class Program {
		public final Result vertex;
		public final Result fragment;
		private final HashMap<String, AllocatedLanes> uniforms = new HashMap<>();
		private final HashMap<String, AllocatedLanes> attributes = new HashMap<>();
		private final HashMap<Agal.AllocatedLanes, Double> constants = new HashMap<>();

		public Program(Result vertex, Result fragment, Names names) {
			this.vertex = vertex;
			this.fragment = fragment;

			for (Map.Entry<String, AllocatedLanes> entry : names.get(Register.Type.Constant).names.entrySet()) {
				if (!entry.getKey().startsWith("#CST#")) {
					uniforms.put(entry.getKey(), entry.getValue());
				}
			}

			for (Map.Entry<String, AllocatedLanes> entry : names.get(Register.Type.Attribute).names.entrySet()) {
				attributes.put(entry.getKey(), entry.getValue());
			}

			for (Map.Entry<Agal.AllocatedLanes, Double> entry : names.constants.entrySet()) {
				constants.put(entry.getKey(), entry.getValue());
			}
		}

		public HashMap<String, AllocatedLanes> getUniforms() {
			return uniforms;
		}

		public HashMap<String, AllocatedLanes> getAttributes() {
			return attributes;
		}

		public HashMap<Agal.AllocatedLanes, Double> getConstants() {
			return constants;
		}
	}

	enum Opcode {
		mov(0x00), add(0x01), sub(0x02), mul(0x03),
		div(0x04), rcp(0x05), min(0x06), max(0x07),
		frc(0x08), sqt(0x09), rsq(0x0a), pow(0x0b),
		log(0x0c), exp(0x0d), nrm(0x0e), sin(0x0f),
		cos(0x10), crs(0x11), dp3(0x12), dp4(0x13),
		abs(0x14), neg(0x15), sat(0x16), m33(0x17),
		m44(0x18), m34(0x19), kil(0x27), tex(0x28),
		sge(0x29), slt(0x2a), seq(0x2c), sne(0x2d),
		ddx(0x1a), ddy(0x1b), ife(0x1c), ine(0x1d),
		ifg(0x1e), ifl(0x1f), els(0x20), eif(0x21);

		public final int opcode;

		Opcode(int opcode) {
			this.opcode = opcode;
		}
	}

	static public class Result {
		public ArrayList<String> sourceCode;
		public String[] sourceCodeArray;
		public byte[] binary;

		public Result(ArrayList<String> sourceCode, byte[] binary) {
			this.sourceCode = sourceCode;
			this.sourceCodeArray = sourceCode.toArray(new String[sourceCode.size()]);
			this.binary = binary;
		}
	}

	static public class Assembler {
		public ArrayList<String> sourceCode = new ArrayList<String>();
		public BinWriter binWriter = new BinWriter();

		public Assembler(ShaderType type) {
			binWriter.i8(0xa0);
			binWriter.i32(1);
			binWriter.i8(0xa1);
			binWriter.i8(type.id);
		}

		public Result generateResult() {
			return new Result(sourceCode, binWriter.toByteArray());
		}

		protected void _out(Opcode opcode, Register dst, Register source1, Register source2) {
			binWriter.i32(opcode.opcode);
			binWriter.i32(dst.getValue());
			binWriter.i32(source1.getValue());
			binWriter.i32(source2.getValue());
		}

		protected void out(Opcode opcode, Register dst, Register l) {
			sourceCode.add(opcode.name() + " " + dst + ", " + l);
			_out(opcode, dst, l, l);
		}

		protected void out(Opcode opcode, Register dst, Register l, Register r) {
			sourceCode.add(opcode.name() + " " + dst + ", " + l + ", " + r);
			_out(opcode, dst, l, r);
		}

		public void mov(Register dst, Register l) {
			out(Opcode.mov, dst, l);
		}

		public void add(Register dst, Register l, Register r) {
			out(Opcode.add, dst, l, r);
		}

		public void sub(Register dst, Register l, Register r) {
			out(Opcode.sub, dst, l, r);
		}

		public void mul(Register dst, Register l, Register r) {
			out(Opcode.mul, dst, l, r);
		}

		public void div(Register dst, Register l, Register r) {
			out(Opcode.div, dst, l, r);
		}

		public void rcp(Register dst, Register l, Register r) {
			out(Opcode.rcp, dst, l, r);
		}

		public void min(Register dst, Register l, Register r) {
			out(Opcode.min, dst, l, r);
		}

		public void max(Register dst, Register l, Register r) {
			out(Opcode.max, dst, l, r);
		}

		// fractional:
		public void frc(Register dst, Register l) {
			out(Opcode.frc, dst, l);
		}

		public void sqt(Register dst, Register l) {
			out(Opcode.sqt, dst, l);
		}

		public void tex(Register dst, Register l, Register r) {
			out(Opcode.tex, dst, l, r);
		}
	}
}