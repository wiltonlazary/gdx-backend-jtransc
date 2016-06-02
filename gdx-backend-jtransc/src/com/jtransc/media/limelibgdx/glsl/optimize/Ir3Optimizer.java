package com.jtransc.media.limelibgdx.glsl.optimize;

import com.jtransc.media.limelibgdx.glsl.ir.Ir3;

import java.util.ArrayList;
import java.util.Objects;

public class Ir3Optimizer {
	public static ArrayList<Ir3> optimize(ArrayList<Ir3> list) {
		ArrayList<Ir3> out = new ArrayList<>(list);
		for (int n = 0; n < out.size() - 1; n++) {
			Ir3 prev = out.get(n + 0);
			Ir3 next = out.get(n + 1);
			
			// -------------
			// Optimization:
			// -------------
			// xxx XX, l, r
			// mov YY, XX
			// --->
			// xxx YY, l, r
			// -------------
			if ((prev instanceof Ir3.Binop) && (next instanceof Ir3.Unop)) {
				Ir3.Binop p1 = (Ir3.Binop)prev;
				Ir3.Unop n1 = (Ir3.Unop)next;
				if (n1.op.str.equals("=") && Objects.equals(p1.target, n1.l)) {
					out.set(n + 0, new Ir3.Binop(n1.target, p1.op, p1.l, p1.r));
					out.remove(n + 1);
					n--;
				}
			}
		}
		return out;
	}
}
