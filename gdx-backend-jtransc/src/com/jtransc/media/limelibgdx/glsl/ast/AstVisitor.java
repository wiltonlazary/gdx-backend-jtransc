package com.jtransc.media.limelibgdx.glsl.ast;

public class AstVisitor {
	public void visit(Shader shader) {
		for (Decl decl : shader.decls) {
			visit(decl);
		}
	}

	public void visit(Decl decl) {
		if (decl instanceof Decl.Precision) {
			visit((Decl.Precision)decl);
		} else if (decl instanceof Decl.Global) {
			visit((Decl.Global)decl);
		} else if (decl instanceof Decl.Function) {
			visit((Decl.Function)decl);
		} else {
			throw new RuntimeException("Unhandled decl " + decl);
		}
	}

	public void visit(Decl.Precision decl) {
	}

	public void visit(Decl.Global decl) {
	}

	public void visit(Decl.Function decl) {
		for (Argument argument : decl.arguments) {
			visit(argument);
		}
		visit(decl.body);
	}

	public void visit(Argument argument) {
	}

	public void visit(Stm stm) {
		if (stm instanceof Stm.Stms) {
			visit((Stm.Stms)stm);
		} else if (stm instanceof Stm.ExprStm) {
			visit((Stm.ExprStm)stm);
		} else {
			throw new RuntimeException("Unhandled stm " + stm);
		}
	}

	public void visit(Stm.Stms stms) {
		for (Stm stm : stms.stms) {
			visit(stm);
		}
	}

	public void visit(Stm.ExprStm stm) {
		visit(stm.expr);
	}

	public void visit(Expr expr) {
		if (expr instanceof Expr.Id) {
			visit((Expr.Id)expr);
		} else if (expr instanceof Expr.NumberLiteral) {
			visit((Expr.NumberLiteral)expr);
		} else if (expr instanceof Expr.Access) {
			visit((Expr.Access)expr);
		} else if (expr instanceof Expr.ArrayAccess) {
			visit((Expr.ArrayAccess)expr);
		} else if (expr instanceof Expr.Binop) {
			visit((Expr.Binop)expr);
		} else if (expr instanceof Expr.Call) {
			visit((Expr.Call)expr);
		} else if (expr instanceof Expr.Unop) {
			visit((Expr.Unop)expr);
		} else if (expr instanceof Expr.UnopPost) {
			visit((Expr.UnopPost)expr);
		} else {
			throw new RuntimeException("Unhandled expr " + expr);
		}
	}

	public void visit(Expr.Id expr) {

	}

	public void visit(Expr.NumberLiteral expr) {

	}

	public void visit(Expr.Access expr) {

	}

	public void visit(Expr.ArrayAccess expr) {

	}

	public void visit(Expr.Binop expr) {
		visit(expr.left);
		visit(expr.right);
	}

	public void visit(Expr.Call expr) {

	}

	public void visit(Expr.Unop expr) {
		visit(expr.expr);
	}

	public void visit(Expr.UnopPost expr) {

	}

}
