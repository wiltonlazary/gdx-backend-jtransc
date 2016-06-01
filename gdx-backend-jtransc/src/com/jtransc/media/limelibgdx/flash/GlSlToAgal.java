package com.jtransc.media.limelibgdx.flash;

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
		convert(shader, names);
		return new Agal.Program();
	}

	static public Agal.Program convert(Shader vertex, Shader fragment) {
		Agal.Names names = new Agal.Names();
		convert(vertex, names);
		convert(fragment, names);
		return new Agal.Program();
	}

	static private void convert(Shader shader, Agal.Names alloc) {
		GlSlToAgal glSlToAgal = new GlSlToAgal(shader, alloc);
		glSlToAgal.convert();
	}

	private Agal.Source operandToSource(Operand operand) {
		System.out.println(operand);
		return new Agal.Source();
	}

	private void convert() {
		final Agal.Assembler agal = new Agal.Assembler();

		for (Ir3 ir3 : AstToIr3.convert(shader)) {
			Ir3.Binop binop = ir3 instanceof Ir3.Binop ? ((Ir3.Binop) ir3) : null;
			Ir3.Unop unop = ir3 instanceof Ir3.Unop ? ((Ir3.Unop) ir3) : null;
			if (unop != null) {
				Agal.Type type = Agal.Type.add;

				switch (unop.op.str) {
					case "=":
					case "mov":
						type = Agal.Type.mov;

						break;
					default:
						throw new RuntimeException("Unhandled unop " + unop.op.str);
				}

				agal.out(type, operandToSource(unop.target), operandToSource(unop.l));
			} else if (binop != null) {
				Agal.Type type;

				switch (binop.op.str) {
					case "texture2D":
						type = Agal.Type.tex;
						break;
					case "*":
					case "mul":
						type = Agal.Type.mul;
						break;
					default:
						throw new RuntimeException("Unhandled binop " + binop.op.str);
				}

				agal.out(type, operandToSource(binop.target), operandToSource(binop.l), operandToSource(binop.r));
			} else {
				throw new RuntimeException("Unhandled " + ir3);
			}
		}
	}
}

