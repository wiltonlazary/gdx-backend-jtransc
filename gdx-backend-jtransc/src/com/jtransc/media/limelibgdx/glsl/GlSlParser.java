package com.jtransc.media.limelibgdx.glsl;

import com.jtransc.media.limelibgdx.glsl.ast.*;
import com.jtransc.media.limelibgdx.util.ListReader;
import com.jtransc.media.limelibgdx.util.NumberUtils;
import com.jtransc.media.limelibgdx.util.Operators;
import com.jtransc.media.limelibgdx.util.Tokenizer;

import java.util.*;

public class GlSlParser {
	public static String preprocess(String shader, Map<String, String> macros) {
		return String.join("\n", CPreprocessor.preprocess(shader.split("\n"), macros));
	}

	public static Shader parse(String shader, Map<String, String> macros) {
		return new GlSlParser(preprocess(shader, macros)).parseProgram();
	}

	private ListReader<String> r;

	public GlSlParser(String code) {
		this.r = new ListReader<>(Tokenizer.tokenizeStr(code));
	}

	public Shader parseProgram() {
		List<Decl> decls = new ArrayList<Decl>();
		while (r.hasMore()) {
			decls.add(parseDecl());
		}
		return new Shader(decls);
	}

	private String tryParseModifier() {
		switch (r.peek()) {
			case "precision":
			case "varying":
			case "uniform":
			case "lowp":
			case "mediump":
			case "highp":
				return r.read();
			default:
				return null;
		}
	}

	private List<String> tryParseModifiers() {
		ArrayList<String> out = new ArrayList<>();
		while (r.hasMore()) {
			String mod = tryParseModifier();
			if (mod == null) break;
			out.add(mod);
		}
		return out;
	}

	public Type parseBasicType() {
		String token = r.read();
		switch (token) {
			case "void": return Type.VOID;
			case "bool": return Type.BOOL;
			case "int": return Type.INT;
			case "uint": return Type.UINT;
			case "float": return Type.FLOAT;
			case "double": return Type.DOUBLE;

			// Vector types
			case "bvec2": return Type.BVEC2;
			case "bvec3": return Type.BVEC3;
			case "bvec4": return Type.BVEC4;
			case "ivec2": return Type.IVEC2;
			case "ivec3": return Type.IVEC3;
			case "ivec4": return Type.IVEC4;
			case "uvec2": return Type.UVEC2;
			case "uvec3": return Type.UVEC3;
			case "uvec4": return Type.UVEC4;
			case "vec2": return Type.VEC2;
			case "vec3": return Type.VEC3;
			case "vec4": return Type.VEC4;
			case "dvec2": return Type.DVEC2;
			case "dvec3": return Type.DVEC3;
			case "dvec4": return Type.DVEC4;

			// Matrix types
			case "mat2": return Type.MAT2;
			case "mat3": return Type.MAT3;
			case "mat4": return Type.MAT4;

			case "sampler2D": return Type.SAMPLER2D;
			default:
				throw new RuntimeException("Unsupported type " + token);
		}
	}

	public Type parseType() {
		Type result = parseBasicType();
		if (Objects.equals(r.peek(), "[")) {
			throw new RuntimeException("Not supported array types yet");
		}
		return result;
	}

	public String parsePrecision() {
		return r.read();
	}

	public String parseId() {
		return r.read();
	}

	public Decl parseDecl() {
		String s = r.tryRead("precision", "varying", "uniform", "attribute");
		if (s != null) {
			switch (s) {
				case "precision": {
					String prec = parsePrecision();
					Type type = parseType();
					r.expect(";");
					return new Decl.Precision(prec, type);
				}
				case "varying":
				case "uniform":
				case "attribute": {
					List<String> mods = tryParseModifiers();
					Type type = parseType();
					String name = parseId();
					r.expect(";");
					return new Decl.Global(s, mods, type, name);
				}
			}
		}

		// Parse method (global field too?) declaration
		List<String> mods = tryParseModifiers();
		Type type = parseType();
		String name = parseId();

		String endDecl = r.read();
		switch (endDecl) {
			// method
			case "(":
				ArrayList<Argument> arguments = new ArrayList<>();
				while (!Objects.equals(r.peek(), ")")) {
					arguments.add(parseArgument());
					if (Objects.equals(r.peek(), ")")) break;
					r.expect(",");
				}
				r.expect(")");
				Stm body = parseStm();
				return new Decl.Function(type, name, arguments, body);
			// global
			case ";":
				//System.out.println(";");
				throw new RuntimeException("Unsupported global variables");
			default:
				throw new RuntimeException("Invalid declaration expecting '(' or ';' but found " + endDecl);
		}
	}

	private Stm parseStm() {
		switch (r.peek()) {
			case "{":
				r.expect("{");
				ArrayList<Stm> stms = new ArrayList<>();
				while (!r.peek().equals("}")) {
					stms.add(parseStm());
				}
				r.expect("}");
				if (stms.size() == 1) {
					return stms.get(0);
				} else {
					return new Stm.Stms(stms);
				}
			case "if":
			case "while":
			case "do":
			case "switch":
			case "return":
				throw new RuntimeException("Unimplemented statement " + r.peek());
			default:
				Expr expr = parseBinopExpr();
				r.expect(";");
				return new Stm.ExprStm(expr);
		}
	}


	private Expr parseBinopExpr() {
		LinkedList<Expr> exprs = new LinkedList<>();
		LinkedList<String> ops = new LinkedList<>();

		//while (r.hasMore()) {
		while (true) {
			exprs.add(parseExprTerminal());
			if (Operators.BINOPS.containsKey(r.peek())) {
				ops.add(r.read());
				continue;
			} else {
				break;
			}
		}

		if (ops.isEmpty()) {
			return exprs.get(0);
		} else {
			return constructBinopList(exprs, ops);
		}
	}

	private int compareOperator(String l, String r) {
		return Integer.compare(Operators.BINOPS.get(l), Operators.BINOPS.get(r));
	}

	private Expr constructBinopList(LinkedList<Expr> exprs, LinkedList<String> ops) {
		if (exprs.size() < 2 || exprs.size() != ops.size() + 1) {
			throw new AssertionError();
		}
		Expr.Binop out = new Expr.Binop(exprs.removeFirst(), ops.removeFirst(), exprs.removeFirst());
		while (!ops.isEmpty()) {
			Expr l = out.left;
			String op1 = out.op;
			Expr c = out.right;
			String op2 = ops.removeFirst();
			Expr r = exprs.removeFirst();

			if (compareOperator(op1, op2) < 0) {
				out = new Expr.Binop(l, op1, new Expr.Binop(c, op2, r));
			} else {
				out = new Expr.Binop(new Expr.Binop(l, op1, c), op2, r);
			}
		}
		return out;
	}

	private Expr parseExprTerminal() {
		switch (r.peek()) {
			case "(": {
				r.expect("(");
				Expr out = parseBinopExpr();
				r.expect(")");
				return out;
			}
			case "!":
				return new Expr.Unop("!", parseExprTerminal());
			case "~":
				return new Expr.Unop("~", parseExprTerminal());
			case "-":
				return new Expr.Unop("-", parseExprTerminal());
			case "+":
				return new Expr.Unop("+", parseExprTerminal());
			case "--":
				return new Expr.Unop("--", parseExprTerminal());
			case "++":
				return new Expr.Unop("++", parseExprTerminal());
		}
		String idOrNumber = r.read();

		Expr expr = NumberUtils.isNumber(idOrNumber) ? new Expr.NumberLiteral(Double.parseDouble(idOrNumber)) : new Expr.Id(idOrNumber);
		while (r.hasMore()) {
			switch (r.peek()) {
				case "++": {
					r.expect("++");
					expr = new Expr.UnopPost(expr, "++");
					break;
				}
				case "--": {
					r.expect("--");
					expr = new Expr.UnopPost(expr, "--");
					break;
				}
				case "(": {
					r.expect("(");
					ArrayList<Expr> args = new ArrayList<>();
					while (!r.peek().equals(")")) {
						args.add(parseBinopExpr());
						if (r.peek().equals(")")) break;
						r.expect(",");
					}
					r.expect(")");
					if (expr instanceof Expr.Id) {
						expr = new Expr.Call(((Expr.Id) expr).id, args);
					} else {
						//expr = new Expr.Call(expr, args);
						throw new RuntimeException("Unsupported calling complex functions");
					}
					break;
				}
				case ".": {
					r.expect(".");
					expr = new Expr.Access(expr, r.read());
					continue;
				}
				case "[": {
					r.expect("[");
					Expr access = parseBinopExpr();
					expr = new Expr.ArrayAccess(expr, access);
					r.expect("]");
					continue;
				}
			}
			break;
		}
		return expr;
	}

	private Argument parseArgument() {
		Type type = parseType();
		String name = parseId();
		return new Argument(type, name);
	}
}
