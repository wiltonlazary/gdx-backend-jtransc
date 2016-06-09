package com.jtransc.media.limelibgdx.soft;

import com.badlogic.gdx.math.MathUtils;

public class BitmapData32 {
	public int[] data;
	public int width;
	public int height;

	public BitmapData32(int width, int height) {
		this.data = new int[width * height];
		this.width = width;
		this.height = height;
	}

	public void set(int x, int y, int color) {
		this.data[y * width + x] = color;
	}

	public int get(int x, int y) {
		return this.data[y * width + x];
	}

	public void fill(int color32) {
		for (int n = 0; n < data.length; n++) data[n] = color32;
	}
}
