package com.jtransc.media.limelibgdx.glsl.transform;

import com.jtransc.media.limelibgdx.glsl.ast.*;
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

		private Operand getOperand(Expr.Id expr) {
			String id = expr.id;
			Operand operand;
			switch (id) {
				case "gl_Position":
				case "gl_FragColor":
					operand = Operand.special(id, Type.VEC4);
					break;
				default:
					if (Character.isDigit(id.charAt(0))) {
						operand = Operand.constant(Double.parseDouble(id));
					} else {
						if (shader.attributes.containsKey(id)) {
							Decl.Global global = shader.attributes.get(id);
							operand = Operand.attribute(id, global.type);
						} else if (shader.varyings.containsKey(id)) {
							Decl.Global global = shader.varyings.get(id);
							operand = Operand.varying(id, global.type);
						} else if (shader.uniforms.containsKey(id)) {
							Decl.Global global = shader.uniforms.get(id);
							operand = Operand.uniform(id, global.type);
						} else {
							throw new RuntimeException("Unknown id " + id);
						}
					}
			}
			return operand;
		}

		private Operand getOperand(Expr.Access expr) {
			Expr left = expr.expr;
			String right = expr.field;
			if (left instanceof Expr.Id) {
				return getOperand((Expr.Id) left).withSwizzle(right);
			} else {
				throw new RuntimeException("Unsupported access ");
			}
		}

		private Operand getOperand(Expr expr) {
			if (expr instanceof Expr.Id) {
				return getOperand((Expr.Id)expr);
			} else if (expr instanceof Expr.Access) {
				return getOperand((Expr.Access)expr);
			} else {
				throw new RuntimeException("Unsupported operand");
			}
		}

		@Override
		public void visit(Expr.Id expr) {
			out.add(new Sir.Get(getOperand(expr)));
		}

		@Override
		public void visit(Expr.NumberLiteral expr) {
			out.add(new Sir.Get(Operand.constant(expr.value)));
		}

		@Override
		public void visit(Expr.Binop expr) {
			switch (expr.op) {
				case "=":
					visit(expr.right);
					out.add(new Sir.Set(getOperand(expr.left)));
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
			out.add(new Sir.Get(getOperand(expr)));
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
