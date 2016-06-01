package com.jtransc.media.limelibgdx.util;

import java.util.*;

public class Tokenizer {
	private final ArrayList<Token> out = new ArrayList<>();
	private final StrReader r;

	static public final Set<String> OPS = new HashSet<>(Arrays.asList(
		"&&", "||",
		"==", "!=", "<", ">", "<=", ">=",
		"<<", ">>", ">>>",
		"=",
		"(", ")",
		"[", "]",
		"{", "}",
		"+", "-", "*", "/", "%",
		"&", "|", "^",
		"!", "~",
		"++", "--",
		".", ",", ";"
	));

	private Tokenizer(String str) {
		r = new StrReader(str);
	}

	static public List<Token> tokenize(String str) {
		Tokenizer tokenizer = new Tokenizer(str);
		tokenizer.tokenize();
		return tokenizer.out;
	}

	static public List<String> tokenizeStr(String str) {
		List<String> out = new ArrayList<String>();
		List<Token> tokens = tokenize(str);
		for (Token token : tokens) {
			out.add(token.content);
		}
		return out;
	}

	private void emit(Token.Type type, String str) {
		out.add(new Token(type, str));
	}

	private void tokenize() {
		while (r.hasMore()) {
			int start = r.offset();
			tokenizeStep();
			int end = r.offset();
			if (r.hasMore() && start == end) {
				throw new RuntimeException("Can't tokenize " + r.peekch());
			}
		}
	}

	private void tokenizeStep() {
		r.skipSpaces();
		if (OPS.contains(r.peek(2))) {
			emit(Token.Type.OPERATOR, r.read(2));
		}
		if (OPS.contains(r.peek(1))) {
			emit(Token.Type.OPERATOR, r.read(1));
		}
		String id = readId();
		if (id.length() > 0) {
			emit(Token.Type.ID, id);
		}
	}

	private String readId() {
		return r.readWhile(ch -> Character.isJavaIdentifierPart(ch));
	}
}
