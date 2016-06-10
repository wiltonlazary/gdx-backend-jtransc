package com.jtransc.media.limelibgdx.soft;

public class BitmapData32 {
	private final int[] data;
	public final int width;
	public final int height;

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

	public BitmapData32 sliceCopy(int sx, int sy, int swidth, int sheight) {
		BitmapData32 out = new BitmapData32(swidth, sheight);
		for (int y = 0; y < sheight; y++) {
			for (int x = 0; x < swidth; x++) {
				out.set(x, y, this.get(sx + x, sy + y));
			}
		}
		return out;
	}

	public String dump() {
		return Color32.toString(data);
	}
}
