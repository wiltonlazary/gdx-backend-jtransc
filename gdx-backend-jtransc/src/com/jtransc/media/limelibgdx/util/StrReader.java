package com.jtransc.media.limelibgdx.util;

public class StrReader {
	private String str;
	private int offset;

	public StrReader(String str) {
		this.str = str;
		this.offset = 0;
	}

	public int size() {
		return this.str.length();
	}

	public int available() {
		return this.size() - offset;
	}

	public int offset() {
		return this.offset;
	}

	public boolean hasMore() {
		return available() > 0;
	}

	public char peekch() {
		return this.str.charAt(offset);
	}

	public char readch() {
		return this.str.charAt(offset++);
	}

	public String peek(int count) {
		return slice(offset, offset + count);
	}

	public String read(int count) {
		String out = slice(offset, offset + count);
		this.offset += count;
		return out;
	}

	public void skip(int count) {
		this.offset += count;
	}

	public void skipch() {
		this.offset++;
	}

	public String slice(int start, int end) {
		return this.str.substring(
			Math.min(Math.max(start, 0), this.size()),
			Math.min(Math.max(end, 0), this.size())
		);
	}

	public String readWhile(FilterChar cond) {
		int start = this.offset;
		while (this.hasMore() && cond.filter(this.peekch())) {
			skipch();
		}
		int end = this.offset;
		return this.slice(start, end);
	}

	public String readUntil(FilterChar cond) {
		int start = this.offset;
		while (this.hasMore() && !cond.filter(this.peekch())) {
			skipch();
		}
		int end = this.offset;
		return this.slice(start, end);
	}

	public void skipSpaces() {
		readWhile(Character::isWhitespace);
	}

	interface FilterChar {
		boolean filter(char ch);
	}
}
