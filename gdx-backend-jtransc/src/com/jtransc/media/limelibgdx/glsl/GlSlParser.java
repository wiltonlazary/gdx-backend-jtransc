package com.jtransc.media.limelibgdx.glsl;

import com.jtransc.media.limelibgdx.util.ListReader;
import com.jtransc.media.limelibgdx.util.Tokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

	public Ast.Decl parseDecl() {
		String s = r.tryRead("precision", "varying", "uniform", "attribute");
		if (s != null) {
			switch (s) {
				case "precision":
					String prec = parsePrecision();
					Ast.Type type = parseType();
					r.expect(";");
					return new Ast.Decl.Precision(prec, type);
			}
		}

		List<String> mods = tryParseModifiers();
		switch (r.read()) {

		}
		return null;
	}
}
