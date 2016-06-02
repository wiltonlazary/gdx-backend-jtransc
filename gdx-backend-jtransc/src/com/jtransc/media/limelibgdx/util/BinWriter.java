package com.jtransc.media.limelibgdx.util;

import com.jtransc.FastMemory;

import java.io.ByteArrayOutputStream;

public class BinWriter {
	private ByteArrayOutputStream os = new ByteArrayOutputStream();
	private FastMemory fm = new FastMemory(8);

	public BinWriter() {
	}

	public byte[] toByteArray() {
		return os.toByteArray();
	}

	public void f32(float value) {
		fm.setFloat32(0, value);
		os.write(fm.getInt8(0));
		os.write(fm.getInt8(1));
		os.write(fm.getInt8(2));
		os.write(fm.getInt8(3));
	}

	public void i64(long value) {
		fm.setInt64(0, value);
		os.write(fm.getInt8(0));
		os.write(fm.getInt8(1));
		os.write(fm.getInt8(2));
		os.write(fm.getInt8(3));
		os.write(fm.getInt8(4));
		os.write(fm.getInt8(5));
		os.write(fm.getInt8(6));
		os.write(fm.getInt8(7));
	}

	public void i32(int value) {
		fm.setInt32(0, value);
		os.write(fm.getInt8(0));
		os.write(fm.getInt8(1));
		os.write(fm.getInt8(2));
		os.write(fm.getInt8(3));
	}

	public void i16(int value) {
		fm.setInt16(0, value);
		os.write(fm.getInt8(0));
		os.write(fm.getInt8(1));
	}

	public void i8(int value) {
		os.write(value);
	}
}