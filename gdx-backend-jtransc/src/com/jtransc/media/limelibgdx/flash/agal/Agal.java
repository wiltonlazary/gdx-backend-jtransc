package com.jtransc.media.limelibgdx.flash.agal;

import com.jtransc.media.limelibgdx.glsl.Lane;
import com.jtransc.media.limelibgdx.glsl.ShaderType;
import com.jtransc.media.limelibgdx.glsl.ast.Type;
import com.jtransc.media.limelibgdx.glsl.ir.Operand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Agal {
	public static final int OP_SCALAR = 0x1;
	public static final int OP_SPECIAL_TEX = 0x8;
	public static final int OP_SPECIAL_MATRIX = 0x10;
	public static final int OP_FRAG_ONLY = 0x20;
	public static final int OP_VERT_ONLY = 0x40;
	public static final int OP_NO_DEST = 0x80;
	public static final int OP_VERSION2 = 0x100;
	public static final int OP_INCNEST = 0x200;
	public static final int OP_DECNEST = 0x400;

	//
//	// masks and shifts
//	public static int SAMPLER_TYPE_SHIFT = 8;
//	public static int SAMPLER_DIM_SHIFT = 12;
//	public static int SAMPLER_SPECIAL_SHIFT = 16;
//	public static int SAMPLER_REPEAT_SHIFT = 20;
//	public static int SAMPLER_MIPMAP_SHIFT = 24;
//	public static int SAMPLER_FILTER_SHIFT = 28;
//
//	// regmap flags
//	public static final int REG_WRITE = 0x1;
//	public static final int REG_READ = 0x2;
//	public static final int REG_FRAG = 0x20;
//	public static final int REG_VERT = 0x40;
//
//
//	static public enum Register {
//		VA(new String[]{"va"}, "vertex attribute", 0x0, REG_VERT | REG_READ, 7, 7, 15),
//		VC(new String[]{"vc"}, "vertex constant", 0x1, REG_VERT | REG_READ, 127, 249, 249),
//		VT(new String[]{"vt"}, "vertex temporary", 0x2, REG_VERT | REG_WRITE | REG_READ, 7, 25, 25),
//		VO(new String[]{"vo", "op"}, "vertex output", 0x3, REG_VERT | REG_WRITE, 0, 0, 0),
//		VI(new String[]{"vi", "i", "v", "fi"}, "varying", 0x4, REG_VERT | REG_FRAG | REG_READ | REG_WRITE, 7, 9, 9),
//		FC(new String[]{"fc"}, "fragment constant", 0x1, REG_FRAG | REG_READ, 27, 63, 199),
//		FT(new String[]{"ft"}, "fragment temporary", 0x2, REG_FRAG | REG_WRITE | REG_READ, 7, 25, 25),
//		FS(new String[]{"fs"}, "texture sampler", 0x5, REG_FRAG | REG_READ, 7, 7, 7),
//		FO(new String[]{"fo", "oc"}, "fragment output", 0x3, REG_FRAG | REG_WRITE, 0, 3, 3),
//		FD(new String[]{"fd", "fi", "od"}, "fragment depth output", 0x6, REG_FRAG | REG_WRITE, -1, 0, 0),
//		;
//
//		public final String[] aliases;
//		public final String desc;
//		public final int emitCode;
//		public final int flags;
//		public final int limitV1;
//		public final int limitV2;
//		public final int limitV3;
//
//		static public Register Constant = VC;
//		static public Register Attribute = VA;
//		static public Register Temporary = VT;
//		static public Register Varying = VI;
//		static public Register Output = VO;
//
//		Register(String[] aliases, String desc, int emitCode, int flags, int limitV1, int limitV2, int limitV3) {
//			this.aliases = aliases;
//			this.desc = desc;
//			this.emitCode = emitCode;
//			this.flags = flags;
//			this.limitV1 = limitV1;
//			this.limitV2 = limitV2;
//			this.limitV3 = limitV3;
//		}
//	}
//
//	enum Sampler2 {
//		RGBA(SAMPLER_TYPE_SHIFT, 0),
//		DXT1(SAMPLER_TYPE_SHIFT, 1),
//		DXT5(SAMPLER_TYPE_SHIFT, 2),
//		VIDEO(SAMPLER_TYPE_SHIFT, 3),
//		D2(SAMPLER_DIM_SHIFT, 0),
//		D3(SAMPLER_DIM_SHIFT, 2),
//		CUBE(SAMPLER_DIM_SHIFT, 1),
//		MIPNEAREST(SAMPLER_MIPMAP_SHIFT, 1),
//		MIPLINEAR(SAMPLER_MIPMAP_SHIFT, 2),
//		MIPNONE(SAMPLER_MIPMAP_SHIFT, 0),
//		NOMIP(SAMPLER_MIPMAP_SHIFT, 0),
//		NEAREST(SAMPLER_FILTER_SHIFT, 0),
//		LINEAR(SAMPLER_FILTER_SHIFT, 1),
//		ANISOTROPIC2X(SAMPLER_FILTER_SHIFT, 2),
//		ANISOTROPIC4X(SAMPLER_FILTER_SHIFT, 3),
//		ANISOTROPIC8X(SAMPLER_FILTER_SHIFT, 4),
//		ANISOTROPIC16X(SAMPLER_FILTER_SHIFT, 5),
//		CENTROID(SAMPLER_SPECIAL_SHIFT, 1 << 0),
//		SINGLE(SAMPLER_SPECIAL_SHIFT, 1 << 1),
//		IGNORESAMPLER(SAMPLER_SPECIAL_SHIFT, 1 << 2),
//		REPEAT(SAMPLER_REPEAT_SHIFT, 1),
//		WRAP(SAMPLER_REPEAT_SHIFT, 1),
//		CLAMP(SAMPLER_REPEAT_SHIFT, 0),
//		CLAMP_U_REPEAT_V(SAMPLER_REPEAT_SHIFT, 2),
//		REPEAT_U_CLAMP_V(SAMPLER_REPEAT_SHIFT, 3),;
//
//		public final int flag;
//		public final int mask;
//
//		Sampler2(int flag, int mask) {
//			this.flag = flag;
//			this.mask = mask;
//		}
//	}
//
	enum Opcode {
		MOV(2, 0x00, 0),
		ADD(3, 0x01, 0),
		SUB(3, 0x02, 0),
		MUL(3, 0x03, 0),
		DIV(3, 0x04, 0),
		RCP(2, 0x05, 0),
		MIN(3, 0x06, 0),
		MAX(3, 0x07, 0),
		FRC(2, 0x08, 0),
		SQT(2, 0x09, 0),
		RSQ(2, 0x0a, 0),
		POW(3, 0x0b, 0),
		LOG(2, 0x0c, 0),
		EXP(2, 0x0d, 0),
		NRM(2, 0x0e, 0),
		SIN(2, 0x0f, 0),
		COS(2, 0x10, 0),
		CRS(3, 0x11, 0),
		DP3(3, 0x12, 0),
		DP4(3, 0x13, 0),
		ABS(2, 0x14, 0),
		NEG(2, 0x15, 0),
		SAT(2, 0x16, 0),
		M33(3, 0x17, OP_SPECIAL_MATRIX),
		M44(3, 0x18, OP_SPECIAL_MATRIX),
		M34(3, 0x19, OP_SPECIAL_MATRIX),
		DDX(2, 0x1a, OP_VERSION2 | OP_FRAG_ONLY),
		DDY(2, 0x1b, OP_VERSION2 | OP_FRAG_ONLY),
		IFE(2, 0x1c, OP_NO_DEST | OP_VERSION2 | OP_INCNEST | OP_SCALAR),
		INE(2, 0x1d, OP_NO_DEST | OP_VERSION2 | OP_INCNEST | OP_SCALAR),
		IFG(2, 0x1e, OP_NO_DEST | OP_VERSION2 | OP_INCNEST | OP_SCALAR),
		IFL(2, 0x1f, OP_NO_DEST | OP_VERSION2 | OP_INCNEST | OP_SCALAR),
		ELS(0, 0x20, OP_NO_DEST | OP_VERSION2 | OP_INCNEST | OP_DECNEST | OP_SCALAR),
		EIF(0, 0x21, OP_NO_DEST | OP_VERSION2 | OP_DECNEST | OP_SCALAR),
		// space
		//TED(3, 0x26, OP_FRAG_ONLY | OP_SPECIAL_TEX | OP_VERSION2),	//ted is not available in AGAL2
		KIL(1, 0x27, OP_NO_DEST | OP_FRAG_ONLY),
		TEX(3, 0x28, OP_FRAG_ONLY | OP_SPECIAL_TEX),
		SGE(3, 0x29, 0),
		SLT(3, 0x2a, 0),
		SGN(2, 0x2b, 0),
		SEQ(3, 0x2c, 0),
		SNE(3, 0x2d, 0),;

		public final int numRegister;
		public final int emitCode;
		public final int flags;

		Opcode(int numRegister, int emitCode, int flags) {
			this.numRegister = numRegister;
			this.emitCode = emitCode;
			this.flags = flags;
		}

		public String getName2() {
			return this.name().toLowerCase();
		}
	}

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
		enum Kind {
			Attribute(0, "va", "fa"),
			Constant(1, "vc", "fc"),
			Temporary(2, "vt", "ft"),
			Output(3, "op", "oc"),
			//Varying(4, "vi", "fi"),
			Varying(4, "v", "v"),
			Sampler(5, "fs", "fs"),
			//Fragment(6, "f", "f"),
			;

			public int index;
			public String prefixVertex;
			public String prefixFragment;

			Kind(int index, String prefixVertex, String prefixFragment) {
				this.index = index;
				this.prefixVertex = prefixVertex;
				this.prefixFragment = prefixFragment;
			}

			public String getPrefix(ShaderType type, AllocatedLanes index) {
				if (index.type == Type.SAMPLER2D) {
					return "fs";
				} else {
					return (type == ShaderType.Vertex) ? prefixVertex : prefixFragment;
				}
			}

			public String getName(ShaderType type, AllocatedLanes lanes) {
				if (this == Output) {
					return getPrefix(type, lanes);
				} else {
					return getPrefix(type, lanes) + lanes.register;
				}
			}
		}

		public ShaderType shaderType;
		public Kind type;
		public AllocatedLanes register;
		public String swizzle;

		public Register(ShaderType shaderType, Kind type, AllocatedLanes index, String swizzle) {
			this.shaderType = shaderType;
			this.type = type;
			this.register = index;
			this.swizzle = swizzle;
		}

		@Override
		public String toString() {
			return type.getName(shaderType, register) + register.getSuffix(swizzle);
		}
	}

//	// Destination field format
//	// The [destination] field is 32 bits in size:
//	// 31.............................0
//	// ----TTTT----MMMMNNNNNNNNNNNNNNNN
//	// N = Register number (16 bits)
//	// M = Write mask (4 bits)
//	// T = Register type (4 bits)
//	// - = undefined, must be 0
//	static public class Destination {
//		public final int N;
//		public final int M;
//		public final int T;
//
//		public Destination(int n, int m, int t) {
//			N = n;
//			M = m;
//			T = t;
//		}
//
//		public int encode() {
//			int out = 0;
//			out |= (N & 0xFFFF) << 0;
//			out |= (M & 0xF) << 16;
//			out |= (T & 0xF) << 24;
//			return out;
//		}
//	}
//
//	// Source field format
//	// The [source] field is 64 bits in size:
//	// 63.............................................................0
//	// D-------------QQ----IIII----TTTTSSSSSSSSOOOOOOOONNNNNNNNNNNNNNNN
//	// N = Register number (16 bits)
//	// O = Indirect offset (8 bits)
//	// S = Swizzle (8 bits, 2 bits per component)
//	// T = Register type (4 bits)
//	// I = Index register type (4 bits)
//	// Q = Index register component select (2 bits)
//	// D = Direct=0/Indirect=1 for direct Q and I are ignored, 1bit
//	// - = undefined, must be 0
//	// Sampler field format
//	static public class Source {
//		public final int N;
//		public final int O;
//		public final int S;
//		public final int T;
//		public final int I;
//		public final int Q;
//		public final int D;
//
//		public Source(int n, int o, int s, int t, int i, int q, int d) {
//			N = n;
//			O = o;
//			S = s;
//			T = t;
//			I = i;
//			Q = q;
//			D = d;
//		}
//
//		public long encode() {
//			long out = 0;
//			out |= (long) (N & 0xFFFF) << 0;
//			out |= (long) (O & 0xFF) << 16;
//			out |= (long) (S & 0xFF) << 24;
//			out |= (long) (T & 0xF) << 32;
//			out |= (long) (I & 0xF) << 40;
//			out |= (long) (Q & 0x7) << 48;
//			out |= (long) (D & 0x1) << 63;
//			return out;
//		}
//	}
//
//	// Sampler field format
//	// The second source field for the tex opcode must be in [sampler] format, which is 64 bits in size:
//	// 63.............................................................0
//	// FFFFMMMMWWWWSSSSDDDD--------TTTT--------BBBBBBBBNNNNNNNNNNNNNNNN
//	// N = Sampler register number (16 bits)
//	// B = Texture level-of-detail (LOD) bias, signed integer, scale by 8. The floating point value used is b/8.0 (8 bits)
//	// T = Register type, must be 5, Sampler (4 bits)
//	// D = Dimension (0=2D, 1=Cube)
//	// S = Special flag bits (must be 0)
//	// W = Wrapping (0=clamp,1=repeat)
//	// M = Mipmap (0=disable,1=nearest, 2=linear)
//	// F = Filter (0=nearest,1=linear) (4 bits)
//	static public class Sampler {
//		public final int N;
//		public final int B;
//		public final int T;
//		public final int D;
//		public final int S;
//		public final int W;
//		public final int M;
//		public final int F;
//
//		public Sampler(int n, int b, int t, int d, int s, int w, int m, int f) {
//			N = n;
//			B = b;
//			T = t;
//			D = d;
//			S = s;
//			W = w;
//			M = m;
//			F = f;
//		}
//
//		public long encode() {
//			long out = 0;
//			out |= (long) (N & 0xFFFF) << 0;
//			out |= (long) (B & 0xFF) << 16;
//			out |= (long) (T & 0xF) << 32;
//			out |= (long) (D & 0xF) << 44;
//			out |= (long) (S & 0xF) << 48;
//			out |= (long) (W & 0xF) << 52;
//			out |= (long) (M & 0xF) << 56;
//			out |= (long) (F & 0xF) << 60;
//			return out;
//		}
//	}

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

		public Subnames get(Register.Kind type) {
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

			for (Map.Entry<String, AllocatedLanes> entry : names.get(Register.Kind.Constant).names.entrySet()) {
				if (!entry.getKey().startsWith("#CST#")) {
					uniforms.put(entry.getKey(), entry.getValue());
				}
			}

			for (Map.Entry<String, AllocatedLanes> entry : names.get(Register.Kind.Attribute).names.entrySet()) {
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

	// http://help.adobe.com/en_US/as3/dev/WSd6a006f2eb1dc31e-310b95831324724ec56-8000.html
	static public class Assembler {
		public ArrayList<String> sourceCode = new ArrayList<String>();
		public ShaderType shaderType;
		private Names names;

		public Assembler(ShaderType type, Names names) {
			this.shaderType = type;
			this.names = names;
		}

		public Result generateResult() {
			return new Result(sourceCode, new AGALMiniAssembler(false).assemble(shaderType, String.join("\n", sourceCode)));
		}

		private Register operandToRegister(Operand operand) {
			Register.Kind kind;
			int size = 4;
			switch (operand.kind) {
				case Uniform:
					//if (operand.type == Type.SAMPLER2D) {
					//	kind = Register.Type.Sampler;
					//} else {
					//	kind = Register.Type.Constant;
					//}
					kind = Register.Kind.Constant;
					size = 4;
					break;
				case Attribute:
					kind = Register.Kind.Attribute;
					size = 4;
					break;
				case Varying:
					kind = Register.Kind.Varying;
					size = 4;
					break;
				case Temp:
					kind = Register.Kind.Temporary;
					size = 4;
					break;
				case Special:
					// @TODO: Or sampler!!
					kind = Register.Kind.Output;
					size = 4;
					break;
				case Constant:
					size = 1;
					kind = Register.Kind.Constant;
					Agal.AllocatedLanes id = names.get(kind).alloc(operand.name, operand.type, size);
					names.addFixedConstant(id, operand.constant);
					break;
				default:
					throw new RuntimeException("Unhandled " + operand);
			}
			Agal.AllocatedLanes id = names.get(kind).alloc(operand.name, operand.type, size);
			return new Agal.Register(shaderType, kind, id, operand.swizzle);
		}

		private String sop(Operand op) {
			return operandToRegister(op).toString();
		}

		protected void out(Opcode opcode, Operand dst, Operand src1, Operand src2) {
			String str = "";
			str += opcode.getName2() + " " + sop(dst) + ", " + sop(src1) + ", " + sop(src2);
			if (opcode == Opcode.TEX) {
				str += " <2d,linear,repeat,mipnearest>";
			}
			sourceCode.add(str);
		}

		protected void out(Opcode opcode, Operand dst, Operand src) {
			sourceCode.add(opcode.getName2() + " " + sop(dst) + ", " + sop(src));
		}

		/*
		public void mov(Destination dst, Source l) {
			out(Opcode.MOV, dst, l);
		}

		public void add(Destination dst, Source l, Source r) {
			out(Opcode.ADD, dst, l, r);
		}

		public void sub(Destination dst, Source l, Source r) {
			out(Opcode.SUB, dst, l, r);
		}

		public void mul(Destination dst, Source l, Source r) {
			out(Opcode.MUL, dst, l, r);
		}

		public void div(Destination dst, Source l, Source r) {
			out(Opcode.DIV, dst, l, r);
		}

		public void rcp(Destination dst, Source l, Source r) {
			out(Opcode.RCP, dst, l, r);
		}

		public void min(Destination dst, Source l, Source r) {
			out(Opcode.MIN, dst, l, r);
		}

		public void max(Destination dst, Source l, Source r) {
			out(Opcode.MAX, dst, l, r);
		}

		// fractional:
		public void frc(Destination dst, Source l) {
			out(Opcode.FRC, dst, l);
		}

		public void sqt(Destination dst, Source l) {
			out(Opcode.SQT, dst, l);
		}

		public void tex(Destination dst, Source l, Sampler r) {
			out(Opcode.TEX, dst, l, r);
		}
		*/
	}
}