package com.jtransc.media.limelibgdx.util;

import com.jtransc.FastMemory;

import java.io.ByteArrayOutputStream;

public class BinWriter {
	private ByteArrayOutputStream os = new ByteArrayOutputStream();
	private byte[] data = null;
	private FastMemory fm = new FastMemory(8);

	public BinWriter() {
	}

	public byte[] toByteArray() {
		if (data == null) {
			data = os.toByteArray();
		}
		return data;
	}

	public void f32(float value) {
		fm.setFloat32(0, value);
		os.write(fm.getInt8(0));
		os.write(fm.getInt8(1));
		os.write(fm.getInt8(2));
		os.write(fm.getInt8(3));
		this.data = null;
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
		this.data = null;
	}

	public void i32(int value) {
		fm.setInt32(0, value);
		os.write(fm.getInt8(0));
		os.write(fm.getInt8(1));
		os.write(fm.getInt8(2));
		os.write(fm.getInt8(3));
		this.data = null;
	}

	public void i16(int value) {
		fm.setInt16(0, value);
		os.write(fm.getInt8(0));
		os.write(fm.getInt8(1));
		this.data = null;
	}

	public void i8(int value) {
		os.write(value);
		this.data = null;
	}

	public int length() {
		return this.os.size();
	}

	public byte get(int index) {
		return this.os.toByteArray()[index];
	}

	public void clear() {
		this.os = new ByteArrayOutputStream();
		this.data = null;
	}
}