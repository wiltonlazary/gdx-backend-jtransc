package com.jtransc.media.limelibgdx.glsl;

import com.jtransc.media.limelibgdx.util.ListReader;
import com.jtransc.media.limelibgdx.util.ReplaceCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CPreprocessor {
	private final HashMap<String, String> macros;
	private final ArrayList<String> output = new ArrayList<>();
	ListReader<String> lines;

	LinkedList<Boolean> executableStack = new LinkedList<>();
	boolean currentExecuting = true;

	private CPreprocessor(String[] lines, Map<String, String> macros) {
		this.lines = new ListReader<>(lines);
		this.macros = new HashMap<>(macros);
	}

	public boolean actualExecuting() {
		if (!currentExecuting) return false;
		for (Boolean r : executableStack) {
			if (!r) return false;
		}
		return true;
	}

	@SuppressWarnings("all")
	private void preprocess() {
		while (this.lines.hasMore()) {
			String line = this.lines.read();

			if (line.trim().startsWith("#")) {
				String[] parts = line.trim().substring(1).trim().split("\\s+", 2);
				String command = parts[0];

				switch (command) {
					case "ifdef":
						executableStack.addLast(currentExecuting);
						currentExecuting = macros.containsKey(parts[1]);
						break;
					case "else":
						currentExecuting = !currentExecuting;
						break;
					case "endif":
						currentExecuting = executableStack.removeLast();
						break;
					case "define":
						if (actualExecuting()) {
							String[] pp = parts[1].split("\\s+", 2);
							String key = pp[0];
							String value = (pp.length >= 2) ? pp[1] : "";
							macros.put(key.trim(), value.trim());
						}
						break;
					default:
						throw new RuntimeException("Not implemented: " + command);
				}
			} else {
				if (actualExecuting()) {
					output.add(ReplaceCallback.replace("\\b(\\w+)\\b+", line, new ReplaceCallback.Callback() {
						@Override
						public String matchFound(MatchResult match) {
							String out = match.group(1);
							if (macros.containsKey(out)) {
								return macros.get(out);
							} else {
								return out;
							}
						}
					}));
				}
			}
		}
	}

	static public String[] preprocess(String[] lines, Map<String, String> macros) {
		CPreprocessor preprocessor = new CPreprocessor(lines, macros);
		preprocessor.preprocess();
		return preprocessor.output.toArray(new String[0]);
	}
}
