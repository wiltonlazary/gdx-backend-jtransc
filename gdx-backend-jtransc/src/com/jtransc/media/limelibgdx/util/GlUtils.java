package com.jtransc.media.limelibgdx.util;

import com.badlogic.gdx.Gdx;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class GlUtils {
	static private IntBuffer ib = ByteBuffer.allocate(4).asIntBuffer();

	static public int getInteger(int pname) {
		Gdx.gl.glGetIntegerv(pname, ib);
		return ib.get(0);
	}
}