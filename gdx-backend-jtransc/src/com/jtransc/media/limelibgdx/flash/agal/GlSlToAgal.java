package com.jtransc.media.limelibgdx.flash.agal;

import com.jtransc.media.limelibgdx.glsl.ast.*;
import com.jtransc.media.limelibgdx.glsl.ir.Ir3;
import com.jtransc.media.limelibgdx.glsl.ir.Operand;
import com.jtransc.media.limelibgdx.glsl.transform.AstToIr3;

// http://www.adobe.com/devnet/flashplayer/articles/what-is-agal.html
// http://help.adobe.com/en_US/as3/dev/WSd6a006f2eb1dc31e-310b95831324724ec56-8000.html
public class GlSlToAgal {
	Shader shader;
	Agal.Names names;

	public GlSlToAgal(Shader shader, Agal.Names names) {
		this.shader = shader;
		this.names = names;
	}

	static public Agal.Program convertTest(Shader shader) {
		Agal.Names names = new Agal.Names();
		Agal.Assembler shaderAssembler = convert(shader, names);
		return new Agal.Program(shaderAssembler.generateResult(), shaderAssembler.generateResult(), names);
	}

	static public Agal.Program convert(Shader vertex, Shader fragment) {
		Agal.Names names = new Agal.Names();
		Agal.Assembler fragmentAssembler = convert(fragment, names);
		Agal.Assembler vertexAssembler = convert(vertex, names);
		return new Agal.Program(vertexAssembler.generateResult(), fragmentAssembler.generateResult(), names);
	}

	static private Agal.Assembler convert(Shader shader, Agal.Names alloc) {
		GlSlToAgal glSlToAgal = new GlSlToAgal(shader, alloc);
		return glSlToAgal.convert();
	}

	private Agal.Register operandToSource(Operand operand) {
		Agal.Register.Type type;
		switch (operand.type) {
			case Uniform:
				type = Agal.Register.Type.Constant;
				break;
			case Attribute:
				type = Agal.Register.Type.Attribute;
				break;
			case Varying:
				type = Agal.Register.Type.Varying;
				break;
			case Temp:
				type = Agal.Register.Type.Temporary;
				break;
			case Special:
				// @TODO: Or sampler!!
				type = Agal.Register.Type.Output;
				break;
			case Constant:
				type = Agal.Register.Type.Constant;
				int id = names.get(type).alloc(operand.name);
				names.addFixedConstant(id, operand.constant);
				break;
			default:
				throw new RuntimeException("Unhandled " + operand);
		}
		int id = names.get(type).alloc(operand.name);
		return new Agal.Register(type, id, operand.swizzle);
	}

	private Agal.Assembler convert() {
		final Agal.Assembler agal = new Agal.Assembler();

		for (Ir3 ir3 : AstToIr3.convertAndOptimize(shader)) {
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

