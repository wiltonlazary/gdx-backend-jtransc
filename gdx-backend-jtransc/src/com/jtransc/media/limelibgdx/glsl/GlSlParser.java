package com.jtransc.media.limelibgdx.glsl;

import com.jtransc.media.limelibgdx.util.ListReader;
import com.jtransc.media.limelibgdx.util.Token;
import com.jtransc.media.limelibgdx.util.Tokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GlSlParser {
	public static String preprocess(String shader, Map<String, String> macros) {
		return String.join("\n", CPreprocessor.preprocess(shader.split("\n"), macros));
	}

	public static Ast.Program parse(String shader, Map<String, String> macros) {
		return new GlSlParser(preprocess(shader, macros)).parseProgram();
	}

	private ListReader<String> r;

	public GlSlParser(String code) {
		this.r = new ListReader<>(Tokenizer.tokenizeStr(code));
	}

	public Ast.Program parseProgram() {
		List<Ast.Decl> decls = new ArrayList<Ast.Decl>();
		while (r.hasMore()) {
			decls.add(parseDecl());
		}
		return new Ast.Program(decls);
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

	public Ast.Type parseType() {
		return new Ast.Type.Prim(r.read());
	}

	public String parsePrecision() {
		return r.read();
	}

	public String parseId() {
		return r.read();
	}

	public Ast.Decl parseDecl() {
		String s = r.tryRead("precision", "varying", "uniform", "attribute");
		if (s != null) {
			switch (s) {
				case "precision": {
					String prec = parsePrecision();
					Ast.Type type = parseType();
					r.expect(";");
					return new Ast.Decl.Precision(prec, type);
				}
				case "varying":
				case "uniform":
				case "attribute":
				{
					List<String> mods = tryParseModifiers();
					Ast.Type type = parseType();
					String name = parseId();
					r.expect(";");
					return new Ast.Decl.Varying(mods, type, name);
				}
			}
		}

		// Parse method (global field too?) declaration
		List<String> mods = tryParseModifiers();
		Ast.Type type = parseType();
		String name = parseId();

		String endDecl = r.read();
		switch (endDecl) {
			// method
			case "(":
				ArrayList<Ast.Argument> arguments = new ArrayList<>();
				while (!Objects.equals(r.peek(), ")")) {
					arguments.add(parseArgument());
					if (Objects.equals(r.peek(), ")")) break;
					r.expect(",");
				}
				r.expect(")");
				Ast.Stm body = parseStm();
				return new Ast.Decl.Function(type, name, arguments, body);
			// global
			case ";":
				//System.out.println(";");
				throw new RuntimeException("Unsupported global variables");
			default:
				throw new RuntimeException("Invalid declaration expecting '(' or ';' but found " + endDecl);
		}
	}

	private Ast.Stm parseStm() {
		switch (r.peek()) {
			case "{":
				r.expect("{");
				ArrayList<Ast.Stm> stms = new ArrayList<>();
				while (!r.peek().equals("}")) {
					stms.add(parseStm());
				}
				r.expect("}");
				if (stms.size() == 1) {
					return stms.get(0);
				} else {
					return new Ast.Stm.Stms(stms);
				}
			case "if":
			case "while":
			case "do":
			case "switch":
			case "return":
				throw new RuntimeException("Unimplemented statement " + r.peek());
			default:
				Ast.Expr expr = parseExpr();
				r.expect(";");
				return new Ast.Stm.ExprStm(expr);
		}
	}

	private Ast.Expr parseExpr() {
		ArrayList<Ast.Expr> exprs = new ArrayList<>();
		ArrayList<String> ops = new ArrayList<>();

		while (r.hasMore()) {
			exprs.add(parseExprTerminal());
			switch (r.peek()) {
				case "=":
				case "&&": case "||":
				case "&": case "|": case "^":
				case "+": case "-": case "*": case "/": case "%":
				case "==": case "!=": case "<": case ">": case "<=": case ">=":
				{
					ops.add(r.read());
					continue;
				}
			}
			break;
		}

		if (ops.isEmpty()) {
			return exprs.get(0);
		} else {
			return new Ast.Expr.BinopList(exprs, ops);
		}
	}

	private Ast.Expr parseExprTerminal() {
		switch (r.peek()) {
			case "(": {
				r.expect("(");
				Ast.Expr out = parseExpr();
				r.expect(")");
				return out;
			}
			case "!": return new Ast.Expr.Unop("!", parseExprTerminal());
			case "~": return new Ast.Expr.Unop("~", parseExprTerminal());
			case "-": return new Ast.Expr.Unop("-", parseExprTerminal());
			case "+": return new Ast.Expr.Unop("+", parseExprTerminal());
			case "--": return new Ast.Expr.Unop("--", parseExprTerminal());
			case "++": return new Ast.Expr.Unop("++", parseExprTerminal());
		}
		Ast.Expr expr = new Ast.Expr.Id(r.read());
		while (r.hasMore()) {
			switch (r.peek()) {
				case "++": {
					r.expect("++");
					expr = new Ast.Expr.UnopPost(expr, "++");
					break;
				}
				case "--": {
					r.expect("--");
					expr = new Ast.Expr.UnopPost(expr, "--");
					break;
				}
				case "(": {
					r.expect("(");
					ArrayList<Ast.Expr> args = new ArrayList<>();
					while (!r.peek().equals(")")) {
						args.add(parseExpr());
						if (r.peek().equals(")")) break;
						r.expect(",");
					}
					r.expect(")");
					expr = new Ast.Expr.Call(expr, args);
					break;
				}
				case ".": {
					r.expect(".");
					expr = new Ast.Expr.Access(expr, r.read());
					continue;
				}
				case "[": {
					r.expect("[");
					Ast.Expr access = parseExpr();
					expr = new Ast.Expr.ArrayAccess(expr, access);
					r.expect("]");
					continue;
				}
			}
			break;
		}
		return expr;
	}

	private Ast.Argument parseArgument() {
		Ast.Type type = parseType();
		String name = parseId();
		return new Ast.Argument(type, name);
	}
}
