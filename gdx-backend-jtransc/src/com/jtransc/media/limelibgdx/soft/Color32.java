package com.jtransc.media.limelibgdx.soft;

import com.badlogic.gdx.math.MathUtils;

public class Color32 {
	static public int fbuild(float r, float g, float b, float a) {
		return build(
			(int) (MathUtils.clamp(r, 0f, 1f) * 255),
			(int) (MathUtils.clamp(g, 0f, 1f) * 255),
			(int) (MathUtils.clamp(b, 0f, 1f) * 255),
			(int) (MathUtils.clamp(a, 0f, 1f) * 255)
		);
	}

	static public int build(int r, int g, int b, int a) {
		return ((r & 0xFF) << 24) | ((g & 0xFF) << 16) | ((b & 0xFF) << 8) | ((a & 0xFF) << 0);
	}

	static public int getR(int color) {
		return (color >>> 24) & 0xFF;
	}

	static public int getG(int color) {
		return (color >>> 16) & 0xFF;
	}

	static public int getB(int color) {
		return (color >>> 8) & 0xFF;
	}

	static public int getA(int color) {
		return (color >>> 0) & 0xFF;
	}


	static public float getRf(int color) {
		return (float) getR(color) / 255.f;
	}

	static public float getGf(int color) {
		return (float) getG(color) / 255.f;
	}

	static public float getBf(int color) {
		return (float) getB(color) / 255.f;
	}

	static public float getAf(int color) {
		return (float) getA(color) / 255.f;
	}

	static public String toString(int color) {
		return "RGBA(" + getR(color) + "," + getG(color) + ", " + getB(color) + "," + getA(color) + ")";
	}
}
