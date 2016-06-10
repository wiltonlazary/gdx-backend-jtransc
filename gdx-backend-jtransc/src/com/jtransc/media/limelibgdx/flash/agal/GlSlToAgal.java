package com.jtransc.media.limelibgdx.flash.agal;

import com.jtransc.media.limelibgdx.glsl.GlSlParser;
import com.jtransc.media.limelibgdx.glsl.Lanes;
import com.jtransc.media.limelibgdx.glsl.ShaderType;
import com.jtransc.media.limelibgdx.glsl.ast.Shader;
import com.jtransc.media.limelibgdx.glsl.ast.Type;
import com.jtransc.media.limelibgdx.glsl.ir.GlAsm;
import com.jtransc.media.limelibgdx.glsl.ir.Ir3;
import com.jtransc.media.limelibgdx.glsl.ir.Operand;
import com.jtransc.media.limelibgdx.glsl.transform.AstToIr3;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

// http://www.adobe.com/devnet/flashplayer/articles/what-is-agal.html
// http://help.adobe.com/en_US/as3/dev/WSd6a006f2eb1dc31e-310b95831324724ec56-8000.html
public class GlSlToAgal {
	Shader shader;
	Agal.Names names;

	public GlSlToAgal(Shader shader, Agal.Names names) {
		this.shader = shader;
		this.names = names;
	}

	static public Agal.Program convertTest(Shader shader, boolean optimize) {
		Agal.Names names = new Agal.Names();
		Agal.Assembler shaderAssembler = compile(shader, names, optimize);
		return new Agal.Program(shaderAssembler.generateResult(), shaderAssembler.generateResult(), names);
	}

	static public Agal.Program compile(String vertex, String fragment, boolean optimize, Map<String, String> macros) {
		return compile(
			GlSlParser.parse(ShaderType.Vertex, vertex, macros),
			GlSlParser.parse(ShaderType.Fragment, fragment, macros),
			optimize
		);
	}

	static public Agal.Program compile(Shader vertex, Shader fragment, boolean optimize) {
		Agal.Names names = new Agal.Names();
		Agal.Assembler fragmentAssembler = compile(fragment, names, optimize);
		Agal.Assembler vertexAssembler = compile(vertex, names, optimize);
		return new Agal.Program(vertexAssembler.generateResult(), fragmentAssembler.generateResult(), names);
	}

	static private Agal.Assembler compile(Shader shader, Agal.Names alloc, boolean optimize) {
		GlSlToAgal glSlToAgal = new GlSlToAgal(shader, alloc);
		return glSlToAgal.compile(shader.type, optimize);
	}



	/*
	private Agal.Destination operandToDestination(Operand operand) {
		return new Agal.Destination(operandToRegister(operand));
	}

	private Agal.Source operandToSource(Operand operand) {
		return new Agal.Source(operandToRegister(operand));
	}

	private Agal.Sampler operandToSampler(Operand operand) {
		return new Agal.Sampler(operandToRegister(operand));
	}
	*/

	private void outAsm(Agal.Assembler agal, Agal.Opcode opcode, Operand target, Operand src, Operand dst) {
		agal.out(opcode, target, src, dst);
		agal.asmList.add(new GlAsm(toAsm(opcode), toAsmType(), toAsm(agal, target), toAsm(agal, src), toAsm(agal, dst)));
	}

	private void outAsm(Agal.Assembler agal, Agal.Opcode opcode, Operand target, Operand src) {
		agal.out(opcode, target, src);
		agal.asmList.add(new GlAsm(toAsm(opcode), toAsmType(), toAsm(agal, target), toAsm(agal, src), null));
	}

	private int toAsmType() {
		return GlAsm.TYPE_FLOAT;
	}

	private int toAsm(Agal.Opcode opcode) {
		switch (opcode) {
			case MOV:
				return GlAsm.OP_COPY;
			case MUL:
				return GlAsm.OP_MUL;
			default:
				return GlAsm.OP_COPY;
		}
	}

	private GlAsm.Operand toAsm(Agal.Assembler agal, Operand operand) {
		int reg = GlAsm.REG_UNIFORM;

		switch (operand.kind) {
			case Constant:
				reg = GlAsm.REG_UNIFORM;
				break;
			case Attribute:
				reg = GlAsm.REG_ATTRIBUTE;
				break;
			case Varying:
				reg = GlAsm.REG_VARYING;
				break;
			case Special:
				reg = GlAsm.REG_TARGET;
				break;
		}


		Agal.AllocatedLanes id = agal.allocateLanes(operand);
		int size = operand.type.getLaneCount();
		int lanes[] = operand.getLaneIndices();

		return new GlAsm.Operand(reg, id.register, size, lanes);
	}

	private Agal.Assembler compile(ShaderType type, boolean optimize) {
		ArrayList<Ir3> ir3List = AstToIr3.convert(shader, optimize);
		ArrayList<GlAsm> asmList = new ArrayList<>();

		final Agal.Assembler agal = new Agal.Assembler(type, names, ir3List, asmList);

		for (Ir3 ir3 : ir3List) {
			Ir3.Binop binop = ir3 instanceof Ir3.Binop ? ((Ir3.Binop) ir3) : null;
			Ir3.Unop unop = ir3 instanceof Ir3.Unop ? ((Ir3.Unop) ir3) : null;
			if (unop != null) {
				Agal.Opcode opcode;

				switch (unop.op) {
					case ASSIGN:
						opcode = Agal.Opcode.MOV;

						break;
					default:
						throw new RuntimeException("Unhandled unop " + unop.op);
				}

				outAsm(agal, opcode, unop.target, unop.l);
			} else if (binop != null) {
				Operand dest = binop.target;
				Operand src1 = binop.l;
				Operand src2 = binop.r;

				switch (binop.op) {
					case TEX2D:
						// tex oc, v1, fs0 <2d,linear,repeat,mipnearest>
						outAsm(agal, Agal.Opcode.TEX, dest, src2, src1);
						break;
					case MUL:
						if (Objects.equals(src1.type, Type.MAT4)) {
							outAsm(agal, Agal.Opcode.M44, dest, src2, src1);
						} else {
							outAsm(agal, Agal.Opcode.MUL, dest, src1, src2);
						}
						break;
					case DIV:
						outAsm(agal, Agal.Opcode.DIV, dest, src1, src2);
						break;
					default:
						throw new RuntimeException("Unhandled binop " + binop.op);
				}
			} else {
				throw new RuntimeException("Unhandled " + ir3);
			}
		}

		return agal;
	}
}

