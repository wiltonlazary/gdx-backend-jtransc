package com.jtransc.media.limelibgdx.glsl.transform;

import com.jtransc.media.limelibgdx.glsl.ast.Shader;
import com.jtransc.media.limelibgdx.glsl.ir.Ir3;

import java.util.ArrayList;

public class AstToIr3 {
	static public ArrayList<Ir3> convert(Shader shader) {
		return SirToIr3.convert(AstToSir.convert(shader));
	}
}
