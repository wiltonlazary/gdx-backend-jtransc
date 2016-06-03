package com.jtransc.media.limelibgdx.glsl.fix;

import com.jtransc.media.limelibgdx.glsl.ShaderType;
import com.jtransc.media.limelibgdx.glsl.ir.Ir3;
import com.jtransc.media.limelibgdx.glsl.ir.Operand;
import com.jtransc.media.limelibgdx.glsl.ir.UnaryOperator;

import java.util.*;

public class Ir3Fixer {
	//private ArrayList<Ir3> items;

	static public ArrayList<Ir3> fix(ShaderType shaderType, ArrayList<Ir3> in) {
		ArrayList<Ir3> out = in;
		if (shaderType == ShaderType.Vertex) {
			out = fixReadVaryingsInVertexShader(out);
		}
		return out;
	}

	static private HashMap<Operand, Operand> getVaryingTransforms(ArrayList<Ir3> in) {
		HashMap<Operand, Operand> set = new HashMap<>();

		// @TODO: probably we should define temp ids here!
		int lastTempId = 1;

		for (Ir3 ir3 : in) {
			for (Operand operand : ir3.getReadOperands()) {
				Operand operandWOS = operand.withoutSwizzle();

				if (operand.kind == Operand.Kind.Varying) {
					if (!set.containsKey(operandWOS)) {
						set.put(operandWOS, Operand.temp(lastTempId++, operandWOS.type));
					}
				}
			}
		}

		return set;
	}

	static private ArrayList<Ir3> replaceOperands(ArrayList<Ir3> in, HashMap<Operand, Operand> map) {
		ArrayList<Ir3> out = new ArrayList<>();

		for (Ir3 ir3Ori : in) {
			Ir3 ir3 = ir3Ori;
			if (map.containsKey(ir3.getTarget().withoutSwizzle())) {
				ir3 = ir3.withTarget(map.get(ir3.getTarget().withoutSwizzle()).withSwizzle(ir3.getTarget().swizzle));
			}
			Operand[] readOperands = ir3.getReadOperands();
			for (int n = 0; n < readOperands.length; n++) {
				if (map.containsKey(readOperands[n].withoutSwizzle())) {
					readOperands[n] = map.get(readOperands[n].withoutSwizzle()).withSwizzle(readOperands[n].swizzle);
				}
			}
			ir3 = ir3.withReadOperands(readOperands);
			out.add(ir3);
		}

		return out;
	}

	static private ArrayList<Ir3> fixReadVaryingsInVertexShader(ArrayList<Ir3> in) {
		HashMap<Operand, Operand> map = getVaryingTransforms(in);
		ArrayList<Ir3> out = replaceOperands(in, map);
		for (Map.Entry<Operand, Operand> e : map.entrySet()) {
			out.add(new Ir3.Unop(e.getKey(), UnaryOperator.ASSIGN, e.getValue()));
		}

		return out;
	}
}
