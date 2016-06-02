package com.jtransc.media.limelibgdx.flash.agal;

import com.jtransc.media.limelibgdx.glsl.GlSlParser;
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
				GlSlParser.parse(vertex, macros),
				GlSlParser.parse(fragment, macros),
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
		return glSlToAgal.compile(optimize);
	}

	private Agal.Register operandToSource(Operand operand) {
		Agal.Register.Type kind;
		int size = 4;
		switch (operand.kind) {
			case Uniform:
				kind = Agal.Register.Type.Constant;
				size = 4;
				break;
			case Attribute:
				kind = Agal.Register.Type.Attribute;
				size = 4;
				break;
			case Varying:
				kind = Agal.Register.Type.Varying;
				size = 4;
				break;
			case Temp:
				kind = Agal.Register.Type.Temporary;
				size = 4;
				break;
			case Special:
				// @TODO: Or sampler!!
				kind = Agal.Register.Type.Output;
				size = 4;
				break;
			case Constant:
				size = 1;
				kind = Agal.Register.Type.Constant;
				Agal.AllocatedLanes id = names.get(kind).alloc(operand.name, operand.type, size);
				names.addFixedConstant(id, operand.constant);
				break;
			default:
				throw new RuntimeException("Unhandled " + operand);
		}
		Agal.AllocatedLanes id = names.get(kind).alloc(operand.name, operand.type, size);
		return new Agal.Register(kind, id, operand.swizzle);
	}

	private Agal.Assembler compile(boolean optimize) {
		final Agal.Assembler agal = new Agal.Assembler();

		for (Ir3 ir3 : AstToIr3.convert(shader, optimize)) {
			Ir3.Binop binop = ir3 instanceof Ir3.Binop ? ((Ir3.Binop) ir3) : null;
			Ir3.Unop unop = ir3 instanceof Ir3.Unop ? ((Ir3.Unop) ir3) : null;
			if (unop != null) {
				Agal.Opcode opcode = Agal.Opcode.add;

				switch (unop.op.str) {
					case "=":
					case "mov":
						opcode = Agal.Opcode.mov;

						break;
					default:
						throw new RuntimeException("Unhandled unop " + unop.op.str);
				}

				agal.out(opcode, operandToSource(unop.target), operandToSource(unop.l));
			} else if (binop != null) {
				Agal.Opcode opcode;

				switch (binop.op.str) {
					case "texture2D":
						opcode = Agal.Opcode.tex;
						break;
					case "*":
					case "mul":
						opcode = Agal.Opcode.mul;
						break;
					case "/":
					case "div":
						opcode = Agal.Opcode.div;
						break;
					default:
						throw new RuntimeException("Unhandled binop " + binop.op.str);
				}

				agal.out(opcode, operandToSource(binop.target), operandToSource(binop.l), operandToSource(binop.r));
			} else {
				throw new RuntimeException("Unhandled " + ir3);
			}
		}

		return agal;
	}
}

