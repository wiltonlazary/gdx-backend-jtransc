package com.jtransc.media.limelibgdx.flash.agal;

import com.jtransc.media.limelibgdx.glsl.GlSlParser;
import com.jtransc.media.limelibgdx.glsl.ShaderType;
import com.jtransc.media.limelibgdx.glsl.ast.Shader;
import com.jtransc.media.limelibgdx.glsl.ir.Ir3;
import com.jtransc.media.limelibgdx.glsl.ir.Operand;
import com.jtransc.media.limelibgdx.glsl.transform.AstToIr3;

import java.util.Map;

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

	private Agal.Assembler compile(ShaderType type, boolean optimize) {
		final Agal.Assembler agal = new Agal.Assembler(type, names);

		for (Ir3 ir3 : AstToIr3.convert(shader, optimize)) {
			Ir3.Binop binop = ir3 instanceof Ir3.Binop ? ((Ir3.Binop) ir3) : null;
			Ir3.Unop unop = ir3 instanceof Ir3.Unop ? ((Ir3.Unop) ir3) : null;
			if (unop != null) {
				Agal.Opcode opcode = Agal.Opcode.ADD;

				switch (unop.op) {
					case ASSIGN:
						opcode = Agal.Opcode.MOV;

						break;
					default:
						throw new RuntimeException("Unhandled unop " + unop.op);
				}

				agal.out(opcode, unop.target, unop.l);
			} else if (binop != null) {
				Operand dest = binop.target;
				Operand src1 = binop.l;
				Operand src2 = binop.r;

				switch (binop.op) {
					case TEX2D:
						// tex oc, v1, fs0 <2d,linear,repeat,mipnearest>
						agal.out(Agal.Opcode.TEX, dest, src2, src1);
						break;
					case MUL:
						agal.out(Agal.Opcode.MUL, dest, src1, src2);
						break;
					case DIV:
						agal.out(Agal.Opcode.DIV, dest, src1, src2);
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

