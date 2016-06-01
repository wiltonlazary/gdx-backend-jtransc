package com.jtransc.media.limelibgdx.imaging;

import com.jtransc.JTranscSystem;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * https://github.com/mattdesl/slim/tree/master/slim/src/slim/texture/io
 */
public class ImageDecoder {
	static public class BitmapData {
		public int[] data;
		public int width;
		public int height;

		public BitmapData(int[] data, int width, int height) {
			this.data = data;
			this.width = width;
			this.height = height;
		}
	}

	enum Format {
		JPEG, PNG, UNKNOWN
	}

	static private char[] PNG_MAGIC = "\u0089\u0050\u004E\u0047".toCharArray();
	static private char[] JPG_MAGIC = "\u00FF\u00D8\u00FF\u00E0".toCharArray();

	static public boolean checkMagic(byte[] buffer, char[] magic) {
		for (int n = 0; n < magic.length; n++) if ((byte)buffer[n] != (byte)magic[n]) return false;
		return true;
	}

	static public Format detect(byte[] buffer) {
		System.out.println("ImageDecoder.Format: Length:" + buffer.length + ": Header:" + buffer[0] + "," + buffer[1] + "," + buffer[2] + "," + buffer[3]);
		if (checkMagic(buffer, PNG_MAGIC)) return Format.PNG;
		if (checkMagic(buffer, JPG_MAGIC)) return Format.JPEG;
		return Format.UNKNOWN;
	}

	static public int[] toIntArray(ByteBuffer buffer) {
		//return com.jtransc.io.JTranscBufferTools.toIntArray(buffer);
		IntBuffer intBuffer = buffer.asIntBuffer();
		int[] out = new int[intBuffer.limit()];
		for (int n = 0; n < out.length; n++) {
			out[n] = intBuffer.get(n);
		}
		return out;
	}

	static public BitmapData decode(byte[] buffer) {
		try {
			Format format = detect(buffer);
			System.out.println("Format: " + format);
			BitmapData out;
			double start = JTranscSystem.stamp();
			switch (format) {
				case JPEG: {
					final JPEGDecoder decoder = new JPEGDecoder(new ByteArrayInputStream(buffer));
					final int width = decoder.getImageWidth();
					final int height = decoder.getImageHeight();
					final ByteBuffer data = ByteBuffer.allocate(width * height * 4);
					decoder.decodeRGB(data, width * 4, decoder.getNumMCURows());
					data.rewind();
					out = new BitmapData(toIntArray(data), width, height);
					break;
				}
				case PNG: {
					final PNGDecoder decoder = new PNGDecoder(new ByteArrayInputStream(buffer));
					final int width = decoder.getWidth();
					final int height = decoder.getHeight();
					final ByteBuffer data = ByteBuffer.allocate(width * height * 4);
					decoder.decode(data, width * 4, PNGDecoder.Format.ABGR);
					data.rewind();
					out = new BitmapData(toIntArray(data), width, height);
					break;
				}
				default:
					throw new RuntimeException("Unsupported format " + format);
			}
			double end = JTranscSystem.stamp();
			System.out.println("Decoding time: " + (end - start));
			return out;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}
}
