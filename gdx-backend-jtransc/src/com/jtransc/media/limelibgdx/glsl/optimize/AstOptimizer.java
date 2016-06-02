package com.jtransc.media.limelibgdx.glsl.optimize;

import com.jtransc.media.limelibgdx.glsl.ast.Decl;
import com.jtransc.media.limelibgdx.glsl.ast.Expr;
import com.jtransc.media.limelibgdx.glsl.ast.Shader;
import com.jtransc.media.limelibgdx.glsl.ast.Stm;

import java.util.ArrayList;

public class AstOptimizer {
	public Shader optimize(Shader shader) {
		ArrayList<Decl> out = new ArrayList<>();

		for (Decl decl : shader.decls) {
			out.add(optimize(decl));
		}

		return new Shader(out);
	}

	public Decl optimize(Decl decl) {
		if (decl instanceof Decl.Function) {
			return optimize((Decl.Function) decl);
		} else if (decl instanceof Decl.Global) {
			return optimize((Decl.Global) decl);
		} else if (decl instanceof Decl.Precision) {
			return optimize((Decl.Precision) decl);
		} else {
			throw new RuntimeException("Not supported decl " + decl);
		}
	}

	public Decl optimize(Decl.Function decl) {
		Decl.Function out = new Decl.Function(decl.type, decl.name, decl.arguments, optimize(decl.body));
		return out;
	}

	public Decl optimize(Decl.Global decl) {
		return decl;
	}

	public Decl optimize(Decl.Precision decl) {
		return decl;
	}

	public Stm optimize(Stm stm) {
		if (stm instanceof Stm.ExprStm) {
			return optimize((Stm.ExprStm) stm);
		} else if (stm instanceof Stm.Stms) {
			return optimize((Stm.Stms) stm);
		} else {
			throw new RuntimeException("Not supported stm " + stm);
		}
	}

	public Stm optimize(Stm.ExprStm stm) {
		return new Stm.ExprStm(optimize(stm.expr));
	}

	public Stm optimize(Stm.Stms stm) {
		ArrayList<Stm> out = new ArrayList<>();
		for (Stm child : stm.stms) {
			out.add(optimize(child));
		}
		if (out.size() == 1) {
			return out.get(0);
		} else {
			return new Stm.Stms(out);
		}
	}

	public Expr optimize(Expr expr) {
		if (expr instanceof Expr.Access) {
			return optimize((Expr.Access) expr);
		} else if (expr instanceof Expr.ArrayAccess) {
			return optimize((Expr.ArrayAccess) expr);
		} else if (expr instanceof Expr.Binop) {
			return optimize((Expr.Binop) expr);
		} else if (expr instanceof Expr.Call) {
			return optimize((Expr.Call) expr);
		} else if (expr instanceof Expr.Id) {
			return optimize((Expr.Id) expr);
		} else if (expr instanceof Expr.NumberLiteral) {
			return optimize((Expr.NumberLiteral) expr);
		} else if (expr instanceof Expr.Unop) {
			return optimize((Expr.Unop) expr);
		} else if (expr instanceof Expr.UnopPost) {
			return optimize((Expr.UnopPost) expr);
		} else {
			throw new RuntimeException("Not supported expr " + expr);
		}
	}

	public Expr optimize(Expr.Access expr) {
		return new Expr.Access(optimize(expr.expr), expr.field);
	}

	public Expr optimize(Expr.ArrayAccess expr) {
		return new Expr.ArrayAccess(optimize(expr.array), optimize(expr.index));
	}

	public Expr optimize(Expr.Binop expr) {
		if (expr.left instanceof Expr.NumberLiteral && expr.right instanceof Expr.NumberLiteral) {
			double l = ((Expr.NumberLiteral) expr.left).value;
			double r = ((Expr.NumberLiteral) expr.right).value;
			switch (expr.op) {
				case "/":
					return new Expr.NumberLiteral(l / r);
				case "*":
					return new Expr.NumberLiteral(l * r);
				default:
					throw new RuntimeException("Unimplemented optimization for operator on constants " + expr.op);
			}
		} else {
			return new Expr.Binop(optimize(expr.left), expr.op, optimize(expr.right));
		}
	}

	public Expr optimize(Expr.Call expr) {
		ArrayList<Expr> args = new ArrayList<>();
		for (Expr arg : expr.args) {
			args.add(optimize(arg));
		}
		return new Expr.Call(expr.name, args);
	}

	public Expr optimize(Expr.Id expr) {
		//System.out.println(expr.id);
		return expr;
	}

	public Expr optimize(Expr.NumberLiteral expr) {
		//System.out.println(expr.id);
		return expr;
	}

	public Expr optimize(Expr.Unop expr) {
		return new Expr.Unop(expr.op, optimize(expr.expr));
	}

	public Expr optimize(Expr.UnopPost expr) {
		return new Expr.UnopPost(optimize(expr.expr), expr.op);
	}
}
