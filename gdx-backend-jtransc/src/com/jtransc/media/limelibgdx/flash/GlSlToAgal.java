package com.jtransc.media.limelibgdx.flash;

import com.jtransc.media.limelibgdx.glsl.ast.*;

// http://www.adobe.com/devnet/flashplayer/articles/what-is-agal.html
// http://help.adobe.com/en_US/as3/dev/WSd6a006f2eb1dc31e-310b95831324724ec56-8000.html
public class GlSlToAgal extends Visitor {
	final Shader program;
	final Agal agal = new Agal();

	private GlSlToAgal(Shader program) {
		this.program = program;
	}

	@Override
	public void visit(Expr.Binop expr) {
		super.visit(expr);
	}

	static public void convert(Shader program) {
		GlSlToAgal conv = new GlSlToAgal(program);
		conv.visit(program.functions.get("main"));
	}
}

