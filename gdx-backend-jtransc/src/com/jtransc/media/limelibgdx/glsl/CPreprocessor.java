package com.jtransc.media.limelibgdx.glsl;

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
	TokenReader<String> lines;

	LinkedList<Boolean> executableStack = new LinkedList<>();
	boolean currentExecuting = true;

	private CPreprocessor(String[] lines, Map<String, String> macros) {
		this.lines = new TokenReader<>(lines);
		this.macros = new HashMap<>(macros);
	}

	public boolean actualExecuting() {
		if (!currentExecuting) return false;
		for (Boolean r : executableStack) {
			if (!r) return false;
		}
		return true;
	}

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
					output.add(ReplaceCallback.replace("\\b(\\w+)\\b+", line, match -> {
						String out = match.group(1);
						if (macros.containsKey(out)) {
							return macros.get(out);
						} else {
							return out;
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


class ReplaceCallback {
	public static interface Callback {
		/**
		 * This function is called when a match is made. The string which was matched
		 * can be obtained via match.group(), and the individual groupings via
		 * match.group(n).
		 */
		public String matchFound(MatchResult match);
	}

	/**
	 * Replaces with callback, with no limit to the number of replacements.
	 * Probably what you want most of the time.
	 */
	public static String replace(String pattern, String subject, Callback callback) {
		return replace(pattern, subject, -1, null, callback);
	}

	public static String replace(String pattern, String subject, int limit, Callback callback) {
		return replace(pattern, subject, limit, null, callback);
	}

	/**
	 * @param regex    The regular expression pattern to search on.
	 * @param subject  The string to be replaced.
	 * @param limit    The maximum number of replacements to make. A negative value
	 *                 indicates replace all.
	 * @param count    If this is not null, it will be set to the number of
	 *                 replacements made.
	 * @param callback Callback function
	 */
	public static String replace(String regex, String subject, int limit,
								 AtomicInteger count, Callback callback) {
		StringBuffer sb = new StringBuffer();
		Matcher matcher = Pattern.compile(regex).matcher(subject);
		int i;
		for (i = 0; (limit < 0 || i < limit) && matcher.find(); i++) {
			String replacement = callback.matchFound(matcher.toMatchResult());
			replacement = Matcher.quoteReplacement(replacement); //probably what you want...
			matcher.appendReplacement(sb, replacement);
		}
		matcher.appendTail(sb);

		if (count != null)
			count.set(i);
		return sb.toString();
	}
}