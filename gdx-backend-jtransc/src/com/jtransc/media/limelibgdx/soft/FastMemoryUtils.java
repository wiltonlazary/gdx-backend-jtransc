package com.jtransc.media.limelibgdx.soft;

import com.jtransc.FastMemory;

import java.nio.*;

public class FastMemoryUtils {
	static public FastMemory copy(Buffer buffer) {
		if (buffer instanceof ByteBuffer) return copy((ByteBuffer)buffer);
		//if (buffer instanceof ShortBuffer) return copy((ShortBuffer)buffer);
		//if (buffer instanceof IntBuffer) return copy((IntBuffer)buffer);
		if (buffer instanceof FloatBuffer) return copy((FloatBuffer)buffer);
		throw new RuntimeException("Unsupported buffer " + buffer);
	}

	static public FastMemory copy(ByteBuffer buffer) {
		int size = buffer.limit();
		FastMemory out = FastMemory.alloc(size * 1);
		for (int n = 0; n < size; n++) {
			out.setAlignedInt8(n, buffer.get(n));
		}
		return out;
	}

	static public FastMemory copy(FloatBuffer buffer) {
		int size = buffer.limit();
		FastMemory out = FastMemory.alloc(size * 4);
		for (int n = 0; n < size; n++) {
			out.setAlignedFloat32(n, buffer.get(n));
		}
		return out;
	}
}
