package com.jtransc.media.limelibgdx.glsl.transform;

import com.jtransc.media.limelibgdx.glsl.ast.Shader;
import com.jtransc.media.limelibgdx.glsl.ir.Ir3;
import com.jtransc.media.limelibgdx.glsl.optimize.Ir3Optimizer;

import java.util.ArrayList;

public class AstToIr3 {
	static public ArrayList<Ir3> convert(Shader shader) {
		return SirToIr3.convert(AstToSir.convert(shader));
	}

	static public ArrayList<Ir3> convertAndOptimize(Shader shader) {
		return Ir3Optimizer.optimize(SirToIr3.convert(AstToSir.convert(shader)));
	}
}
