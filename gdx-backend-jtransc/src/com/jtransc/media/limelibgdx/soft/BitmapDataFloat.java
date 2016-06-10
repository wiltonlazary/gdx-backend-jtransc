package com.jtransc.media.limelibgdx.soft;

public class BitmapDataFloat {
	public float[] data;
	public int width;
	public int height;

	public BitmapDataFloat(int width, int height) {
		this.data = new float[width * height];
		this.width = width;
		this.height = height;
	}

	public void set(int x, int y, float value) {
		this.data[y * width + x] = value;
	}

	public float get(int x, int y) {
		return this.data[y * width + x];
	}

	public void fill(float value) {
		for (int n = 0; n < data.length; n++) data[n] = value;
	}
}
