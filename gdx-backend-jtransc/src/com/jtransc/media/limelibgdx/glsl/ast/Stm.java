package com.jtransc.media.limelibgdx.glsl.ast;

import java.util.ArrayList;

public interface Stm {
	static public class Stms implements Stm {
		public ArrayList<Stm> stms;

		public Stms(ArrayList<Stm> stms) {

			this.stms = stms;
		}
	}

	static public class ExprStm implements Stm {
		public Expr expr;

		public ExprStm(Expr expr) {
			this.expr = expr;
		}
	}
}