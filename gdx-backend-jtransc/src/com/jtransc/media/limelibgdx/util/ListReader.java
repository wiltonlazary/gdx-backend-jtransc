package com.jtransc.media.limelibgdx.util;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListReader<T> {
	List<T> items;
	public int offset;

	public ListReader(List<T> items) {
		this(items, 0);
	}

	public ListReader(T[] items) {
		this(Arrays.asList(items), 0);
	}

	public ListReader(List<T> items, int offset) {
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

	public T tryRead(T... expected) {
		T actual = peek();
		for (T t : expected) {
			if (Objects.equals(t, actual)) {
				read();
				return t;
			}
		}
		return null;
	}

	public void expect(T expected) {
		T actual = read();
		if (!Objects.equals(actual, expected)) {
			throw new RuntimeException("Expected " + expected + " but found " + actual);
		}
	}

	public T read() {
		return items.get(offset++);
	}
}
