package com.jtransc.media.limelibgdx.glsl.transform;

import com.jtransc.media.limelibgdx.glsl.ast.AstVisitor;
import com.jtransc.media.limelibgdx.glsl.ast.Expr;
import com.jtransc.media.limelibgdx.glsl.ast.Shader;
import com.jtransc.media.limelibgdx.glsl.ir.Operand;
import com.jtransc.media.limelibgdx.glsl.ir.Operator;
import com.jtransc.media.limelibgdx.glsl.ir.Sir;

import java.util.ArrayList;

public class AstToSir {
	static public ArrayList<Sir> convert(Shader shader) {
		Impl impl = new Impl(shader);
		impl.visit(shader);
		return impl.out;
	}

	static private class Impl extends AstVisitor {
		ArrayList<Sir> out = new ArrayList<>();
		Shader shader;

		public Impl(Shader shader) {
			this.shader = shader;
		}

		@Override
		public void visit(Expr.Id expr) {
			String id = expr.id;
			Operand operand;
			switch (id) {
				case "gl_Position":
				case "gl_FragColor":
					operand = Operand.special(id);
					break;
				default:
					if (shader.attributes.containsKey(id)) {
						operand = Operand.attribute(id);
					} else if (shader.varyings.containsKey(id)) {
						operand = Operand.varying(id);
					} else if (shader.uniforms.containsKey(id)) {
						operand = Operand.uniform(id);
					} else {
						throw new RuntimeException("Unknown id " + id);
					}
			}
			out.add(new Sir.Get(operand));
		}

		@Override
		public void visit(Expr.Binop expr) {
			switch (expr.op) {
				case "=":
					visit(expr.right);
					if (expr.left instanceof Expr.Id) {
						Expr.Id left = (Expr.Id) expr.left;
						out.add(new Sir.Set(Operand.special(left.id)));
					} else if (expr.left instanceof Expr.Access) {
						Expr.Access left = (Expr.Access) expr.left;
						if (left.expr instanceof Expr.Id) {
							out.add(new Sir.Set(Operand.special(((Expr.Id) left.expr).id).withSwizzle(left.field)));
						} else {
							throw new RuntimeException("Unsupported assignment " + left.expr);
						}
					} else {
						throw new RuntimeException("Unsupported assignment " + expr.left);
					}
					break;
				default:
					visit(expr.left);
					visit(expr.right);
					out.add(new Sir.Binop(Operator.fromString(expr.op)));
					break;
			}
		}

		@Override
		public void visit(Expr.UnopPost expr) {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public void visit(Expr.Access expr) {
			Expr left = expr.expr;
			String right = expr.field;
			if (left instanceof Expr.Id) {
				out.add(new Sir.Get(Operand.special(((Expr.Id) left).id).withSwizzle(right)));
			} else {
				throw new RuntimeException("Unsupported access ");
			}
		}

		@Override
		public void visit(Expr.ArrayAccess expr) {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public void visit(Expr.Unop expr) {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public void visit(Expr.Call expr) {
			for (Expr arg : expr.args) {
				visit(arg);
			}
			switch (expr.args.size()) {
				case 2:
					out.add(new Sir.Binop(Operator.fromString(expr.name)));
					break;
				case 1:
					out.add(new Sir.Unop(Operator.fromString(expr.name)));
					break;
				default:
					throw new RuntimeException("Unsupported custom functions");
			}
		}
	}
}
