package com.jtransc.media.limelibgdx.util;

public class ColorFormat8 {
	static public final ColorFormat8 GDX = new ColorFormat8(24, 16, 8, 0);
	//static public final ColorFormat8 LIME = new ColorFormat8(24, 16, 8, 24);
	//static public final ColorFormat8 LIME = new ColorFormat8(0, 16, 8, 24);
	static public final ColorFormat8 LIME = GDX;

	public int rShift;
	public int gShift;
	public int bShift;
	public int aShift;

	public ColorFormat8(int rShift, int gShift, int bShift, int aShift) {
		this.rShift = rShift;
		this.gShift = gShift;
		this.bShift = bShift;
		this.aShift = aShift;
	}

	static public int transform(ColorFormat8 from, ColorFormat8 to, int color) {
		if (from == to) {
			return color;
		} else {
			return to.make(from.getR(color), from.getG(color), from.getB(color), from.getA(color));
		}
	}

	public int make(byte r, byte g, byte b, byte a) {
		return ((r & 0xFF) << rShift) |
			((g & 0xFF) << gShift) |
			((b & 0xFF) << bShift) |
			((a & 0xFF) << aShift);
	}

	public int make(int r, int g, int b, int a) {
		return ((r & 0xFF) << rShift) |
			((g & 0xFF) << gShift) |
			((b & 0xFF) << bShift) |
			((a & 0xFF) << aShift);
	}

	public int make(float r, float g, float b, float a) {
		return (((int) (r * 0xFF) & 0xFF) << rShift) |
			(((int) (g * 0xFF) & 0xFF) << gShift) |
			(((int) (b * 0xFF) & 0xFF) << bShift) |
			(((int) (a * 0xFF) & 0xFF) << aShift);
	}

	public byte getR(int color) {
		return (byte) (color >>> rShift);
	}

	public byte getG(int color) {
		return (byte) (color >>> gShift);
	}

	public byte getB(int color) {
		return (byte) (color >>> bShift);
	}

	public byte getA(int color) {
		return (byte) (color >>> aShift);
	}
}