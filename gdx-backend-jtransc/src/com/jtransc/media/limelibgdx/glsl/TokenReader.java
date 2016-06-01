package com.jtransc.media.limelibgdx.glsl;

import java.util.Arrays;
import java.util.List;

public class TokenReader<T> {
	List<T> items;
	public int offset;

	public TokenReader(List<T> items) {
		this(items, 0);
	}

	public TokenReader(T[] items) {
		this(Arrays.asList(items), 0);
	}

	public TokenReader(List<T> items, int offset) {
		this.items = items;
		this.offset = offset;
	}

	public int size() {
		return items.size();
	}

	public int available() {
		return items.size() - offset;
	}

	public boolean hasMore() {
		return available() > 0;
	}

	public T peek() {
		return items.get(offset);
	}

	public T read() {
		return items.get(offset++);
	}
}
