package com.jtransc.media.limelibgdx.glsl.transform;

import com.jtransc.media.limelibgdx.glsl.ast.Shader;
import com.jtransc.media.limelibgdx.glsl.fix.Ir3Fixer;
import com.jtransc.media.limelibgdx.glsl.ir.Ir3;
import com.jtransc.media.limelibgdx.glsl.optimize.AstOptimizer;
import com.jtransc.media.limelibgdx.glsl.optimize.Ir3Optimizer;

import java.util.ArrayList;

public class AstToIr3 {
	static public ArrayList<Ir3> convert(Shader shader) {
		return Ir3Fixer.fix(shader.type, SirToIr3.convert(AstToSir.convert(shader)));
	}

	static public ArrayList<Ir3> convert(Shader shader, boolean optimize) {
		if (optimize) {
			return convertAndOptimize(shader);
		} else {
			return convert(shader);
		}
	}

	static public ArrayList<Ir3> convertAndOptimize(Shader shader) {
		return Ir3Fixer.fix(shader.type, Ir3Optimizer.optimize(
			SirToIr3.convert(
				AstToSir.convert(new AstOptimizer().optimize(shader))
			)
		));
	}
}
