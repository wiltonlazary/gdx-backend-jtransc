/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.badlogic.gdx.graphics;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.jtransc.JTranscArrays;
import com.jtransc.JTranscSystem;
import com.jtransc.annotation.JTranscMethodBody;
import com.jtransc.annotation.JTranscNativeClass;
import com.jtransc.annotation.haxe.HaxeMethodBody;
import com.jtransc.io.JTranscIoTools;
import com.jtransc.media.limelibgdx.LimeFiles;
import com.jtransc.media.limelibgdx.imaging.ImageDecoder;
import com.jtransc.media.limelibgdx.util.ColorFormat8;
import com.jtransc.media.limelibgdx.util.ImageInfo;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pixmap implements Disposable {
	public static Map<Integer, Pixmap> pixmaps = new HashMap<Integer, Pixmap>();
	static int nextId = 0;

	/**
	 * Different pixel formats.
	 *
	 * @author mzechner
	 */
	public enum Format {
		Alpha, Intensity, LuminanceAlpha, RGB565, RGBA4444, RGB888, RGBA8888;

		public static int toGdx2DPixmapFormat(Format format) {
			if (format == Alpha) return Gdx2DPixmap.GDX2D_FORMAT_ALPHA;
			if (format == Intensity) return Gdx2DPixmap.GDX2D_FORMAT_ALPHA;
			if (format == LuminanceAlpha) return Gdx2DPixmap.GDX2D_FORMAT_LUMINANCE_ALPHA;
			if (format == RGB565) return Gdx2DPixmap.GDX2D_FORMAT_RGB565;
			if (format == RGBA4444) return Gdx2DPixmap.GDX2D_FORMAT_RGBA4444;
			if (format == RGB888) return Gdx2DPixmap.GDX2D_FORMAT_RGB888;
			if (format == RGBA8888) return Gdx2DPixmap.GDX2D_FORMAT_RGBA8888;
			throw new GdxRuntimeException("Unknown Format: " + format);
		}

		public static Format fromGdx2DPixmapFormat(int format) {
			if (format == Gdx2DPixmap.GDX2D_FORMAT_ALPHA) return Alpha;
			if (format == Gdx2DPixmap.GDX2D_FORMAT_LUMINANCE_ALPHA) return LuminanceAlpha;
			if (format == Gdx2DPixmap.GDX2D_FORMAT_RGB565) return RGB565;
			if (format == Gdx2DPixmap.GDX2D_FORMAT_RGBA4444) return RGBA4444;
			if (format == Gdx2DPixmap.GDX2D_FORMAT_RGB888) return RGB888;
			if (format == Gdx2DPixmap.GDX2D_FORMAT_RGBA8888) return RGBA8888;
			throw new GdxRuntimeException("Unknown Gdx2DPixmap Format: " + format);
		}

		public static int toGlFormat(Format format) {
			if (format == Alpha) return GL20.GL_ALPHA;
			if (format == Intensity) return GL20.GL_ALPHA;
			if (format == LuminanceAlpha) return GL20.GL_LUMINANCE_ALPHA;
			if (format == RGB565) return GL20.GL_RGB;
			if (format == RGB888) return GL20.GL_RGB;
			if (format == RGBA4444) return GL20.GL_RGBA;
			if (format == RGBA8888) return GL20.GL_RGBA;
			throw new GdxRuntimeException("unknown format: " + format);
		}

		public static int toGlType(Format format) {
			if (format == Alpha) return GL20.GL_UNSIGNED_BYTE;
			if (format == Intensity) return GL20.GL_UNSIGNED_BYTE;
			if (format == LuminanceAlpha) return GL20.GL_UNSIGNED_BYTE;
			if (format == RGB565) return GL20.GL_UNSIGNED_SHORT_5_6_5;
			if (format == RGB888) return GL20.GL_UNSIGNED_BYTE;
			if (format == RGBA4444) return GL20.GL_UNSIGNED_SHORT_4_4_4_4;
			if (format == RGBA8888) return GL20.GL_UNSIGNED_BYTE;
			throw new GdxRuntimeException("unknown format: " + format);
		}
	}

	/**
	 * Blending functions to be set with {@link Pixmap#setBlending}.
	 *
	 * @author mzechner
	 */
	public enum Blending {
		None, SourceOver
	}

	/**
	 * Filters to be used with {@link Pixmap#drawPixmap(Pixmap, int, int, int, int, int, int, int, int)}.
	 *
	 * @author mzechner
	 */
	public enum Filter {
		NearestNeighbour, BiLinear
	}

	int width;
	int height;
	int actualWidth;
	int actualHeight;
	Format format;
	int id;
	//IntBuffer buffer;
	int[] data;
	int color;
	static Blending blending;
	private boolean pixelsAvailable = true;

	public Pixmap(FileHandle file) {
		try {

			loadImage((file.file().isAbsolute()) ? file.file().getPath() : LimeFiles.fixpath(file.file().getPath()));
			//JTranscArrays.swizzle_inplace(data, 24, 16, 8, 0);
			//JTranscArrays.swizzle_inplace(data, 0, 8, 16, 24);
			if (!JTranscSystem.isPureJs()) {
				JTranscArrays.swizzle_inplace_reverse(data);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Pixmap(int width, int height, Format format) {
		create(width, height, format);
	}

	public Pixmap(byte[] encodedData, int offset, int len) {
		loadImage(encodedData, offset, len);
	}

	@JTranscMethodBody(target = "js", value = {
		"var image = __decodeImage(p0);",
		"this.INT_image = image.image;",
		"this.INT_data = image.data;",
		"this['{% FIELD com.badlogic.gdx.graphics.Pixmap:data %}'] = image.data;",
		"this['{% FIELD com.badlogic.gdx.graphics.Pixmap:width %}'] = image.width;",
		"this['{% FIELD com.badlogic.gdx.graphics.Pixmap:height %}'] = image.height;",
		"this['{% FIELD com.badlogic.gdx.graphics.Pixmap:actualWidth %}'] = image.actualWidth;",
		"this['{% FIELD com.badlogic.gdx.graphics.Pixmap:actualHeight %}'] = image.actualHeight;",
	})
	private void loadImageNativeJs(String path) {
	}

	@JTranscMethodBody(target = "js", value = {
		"this.INT_image = null;",
		"this.INT_data = this.{% FIELD com.badlogic.gdx.graphics.Pixmap:data %};",
	})
	private void createdEmpty() {

	}

	@JTranscMethodBody(target = "js", value = {
		"var that = this;",
		"var image = __decodeImageBytes(p0, p1, p2, p3, p4);",
		"this.INT_image = image.image;",
		"this.INT_data = image.data;",
		"image.image.onload = function() { that['{% METHOD com.badlogic.gdx.graphics.Pixmap:loadedTextureAsync %}'](); };",
		"this['{% FIELD com.badlogic.gdx.graphics.Pixmap:data %}'] = image.data;",
		"this['{% FIELD com.badlogic.gdx.graphics.Pixmap:width %}'] = image.width;",
		"this['{% FIELD com.badlogic.gdx.graphics.Pixmap:height %}'] = image.height;",
		"this['{% FIELD com.badlogic.gdx.graphics.Pixmap:actualWidth %}'] = image.actualWidth;",
		"this['{% FIELD com.badlogic.gdx.graphics.Pixmap:actualHeight %}'] = image.actualHeight;",
	})
	private void loadImageNativeJs(byte[] encodedData, int offset, int len, int width, int height) {
	}

	private boolean loaded = true;
	private List<Runnable> loadedImageCallbacks = new ArrayList<Runnable>();

	private void loadedTextureAsync() {
		loaded = true;
		for (Runnable loadedImage : loadedImageCallbacks) {
			loadedImage.run();
		}
		loadedImageCallbacks.clear();
	}

	public void onLoadedTextureAsync(Runnable runnable) {
		if (loaded) {
			runnable.run();
		} else {
			loadedImageCallbacks.add(runnable);
		}
	}

	private void ensureLoaded() {
		if (!pixelsAvailable) {
			throw new RuntimeException("Can't get pixels for an asynchronous loaded texture");
		}
	}

	private void loadImage(byte[] encodedData, int offset, int len) {
		if (JTranscSystem.isPureJs()) {
			ImageInfo.Size size = ImageInfo.detect(encodedData, offset, len);
			pixelsAvailable = false;
			loaded = false;
			loadImageNativeJs(encodedData, offset, len, (size != null) ? size.width : 1, (size != null) ? size.height : 1);
		} else {
			ImageDecoder.BitmapData bitmap = ImageDecoder.decode(encodedData);
			this.data = bitmap.data;
			this.actualWidth = this.width = bitmap.width;
			this.actualHeight = this.height = bitmap.height;
		}
	}

	private void loadImage(String path) throws IOException {
		//if (JTranscSystem.isSwf() || !JTranscSystem.usingJTransc()) {
		//if (true) {
		//if (JTranscSystem.isHaxe()) {
		//	loadImageNative(path);
		//}
		if (JTranscSystem.isPureJs()) {
			loadImageNativeJs(path);
		} else {
			ImageDecoder.BitmapData bitmap = ImageDecoder.decode(JTranscIoTools.readFile(new File(path)));
			this.data = bitmap.data;
			this.actualWidth = this.width = bitmap.width;
			this.actualHeight = this.height = bitmap.height;
		}
	}

	@JTranscNativeClass("lime.Assets")
	static public class Assets {
		native static public Object getImage(String path);

		native static public byte[] getBytes(String path);
	}

	@HaxeMethodBody("" +
		"var image = lime.Assets.getImage(p0._str);" +
		"this.{% FIELD com.badlogic.gdx.graphics.Pixmap:data %} = JA_I.fromBytes(image.getPixels(image.rect));" +
		"this.{% FIELD com.badlogic.gdx.graphics.Pixmap:width %} = image.width;" +
		"this.{% FIELD com.badlogic.gdx.graphics.Pixmap:height %} = image.height;"
	)
	native private void loadImageNative(String path);

	private void create(int width, int height, Format format2) {
		this.width = width;
		this.height = height;
		this.data = new int[width * height];
		this.format = Format.RGBA8888;
		createdEmpty();
	}

	/**
	 * Sets the type of {@link Blending} to be used for all operations. Default is {@link Blending#SourceOver}.
	 *
	 * @param blending the blending type
	 */
	public static void setBlending(Blending blending) {
	}

	/**
	 * @return the currently set {@link Blending}
	 */
	public static Blending getBlending() {
		return blending;
	}

	/**
	 * Sets the type of interpolation {@link Filter} to be used in conjunction with
	 * {@link Pixmap#drawPixmap(Pixmap, int, int, int, int, int, int, int, int)}.
	 *
	 * @param filter the filter.
	 */
	public static void setFilter(Filter filter) {
	}

	public Format getFormat() {
		return format;
	}

	public int getGLInternalFormat() {
		return GL20.GL_RGBA;
	}

	public int getGLFormat() {
		return GL20.GL_RGBA;
	}

	public int getGLType() {
		return GL20.GL_UNSIGNED_BYTE;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getActualWidth() {
		return actualWidth;
	}

	public int getActualHeight() {
		return actualHeight;
	}


	//public Buffer getPixels() {
	//  return buffer;
	//}

	public ByteBuffer getPixels() {
		//return ByteBuffer.wrap(this._getPixels());
		return ByteBuffer.wrap(JTranscArrays.copyReinterpretReversed(this.data)).order(ByteOrder.LITTLE_ENDIAN);
	}

	@Override
	public void dispose() {
		pixmaps.remove(id);
	}

	/**
	 * Sets the color for the following drawing operations
	 *
	 * @param color the color, encoded as RGBA8888
	 */
	public void setColor(int color) {
		this.color = color;
	}

	/**
	 * Sets the color for the following drawing operations.
	 *
	 * @param r The red component.
	 * @param g The green component.
	 * @param b The blue component.
	 * @param a The alpha component.
	 */
	public void setColor(float r, float g, float b, float a) {
		color = ColorFormat8.GDX.make((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255));
	}

	/**
	 * Sets the color for the following drawing operations.
	 *
	 * @param color The color.
	 */
	public void setColor(Color color) {
		setColor(color.r, color.g, color.b, color.a);
	}

	/**
	 * Fills the complete bitmap with the currently set color.
	 */
	native public void fill();

// /**
// * Sets the width in pixels of strokes.
// *
// * @param width The stroke width in pixels.
// */
// public void setStrokeWidth (int width);

	/**
	 * Draws a line between the given coordinates using the currently set color.
	 *
	 * @param x  The x-coodinate of the first point
	 * @param y  The y-coordinate of the first point
	 * @param x2 The x-coordinate of the first point
	 * @param y2 The y-coordinate of the first point
	 */
	public void drawLine(int x, int y, int x2, int y2) {
		line(x, y, x2, y2, DrawType.STROKE);
	}

	/**
	 * Draws a rectangle outline starting at x, y extending by width to the right and by height downwards (y-axis points downwards)
	 * using the current color.
	 *
	 * @param x      The x coordinate
	 * @param y      The y coordinate
	 * @param width  The width in pixels
	 * @param height The height in pixels
	 */
	public void drawRectangle(int x, int y, int width, int height) {
		rectangle(x, y, width, height, DrawType.STROKE);
	}

	/**
	 * Draws an area form another Pixmap to this Pixmap.
	 *
	 * @param pixmap The other Pixmap
	 * @param x      The target x-coordinate (top left corner)
	 * @param y      The target y-coordinate (top left corner)
	 */
	public void Pixmap(Pixmap pixmap, int x, int y) {
		drawPixmap(pixmap, x, y, 0, 0, pixmap.width, pixmap.height);
	}

	/**
	 * Draws an area form another Pixmap to this Pixmap.
	 *
	 * @param pixmap    The other Pixmap
	 * @param x         The target x-coordinate (top left corner)
	 * @param y         The target y-coordinate (top left corner)
	 * @param srcx      The source x-coordinate (top left corner)
	 * @param srcy      The source y-coordinate (top left corner);
	 * @param srcWidth  The width of the area form the other Pixmap in pixels
	 * @param srcHeight The height of the area form the other Pixmap in pixles
	 */
	// copyPixels (sourceImage:Image, sourceRect:Rectangle, destPoint:Vector2, alphaImage:Image = null, alphaPoint:Vector2 = null, mergeAlpha:Bool = false):Void
	public void drawPixmap(Pixmap pixmap, int x, int y, int srcx, int srcy, int srcWidth, int srcHeight) {
		ensureLoaded();
		for (int my = 0; my < srcHeight; my++) {
			for (int mx = 0; mx < srcWidth; mx++) {
				this._setPixel(x + mx, y + my, pixmap._getPixel(srcx + mx, srcy + my));
			}
		}
	}

	//private void copyPixels(sourceImage:Image, sourceRect:Rectangle, destPoint:Vector2, alphaImage:Image = null, alphaPoint:Vector2 = null, mergeAlpha:Bool = false):Void

	/**
	 * Draws an area form another Pixmap to this Pixmap. This will automatically scale and stretch the source image to the
	 * specified target rectangle. Use {@link Pixmap#setFilter(Filter)} to specify the type of filtering to be used (nearest
	 * neighbour or bilinear).
	 *
	 * @param pixmap    The other Pixmap
	 * @param srcx      The source x-coordinate (top left corner)
	 * @param srcy      The source y-coordinate (top left corner);
	 * @param srcWidth  The width of the area form the other Pixmap in pixels
	 * @param srcHeight The height of the area form the other Pixmap in pixles
	 * @param dstx      The target x-coordinate (top left corner)
	 * @param dsty      The target y-coordinate (top left corner)
	 * @param dstWidth  The target width
	 * @param dstHeight the target height
	 */
	native public void drawPixmap(Pixmap pixmap, int srcx, int srcy, int srcWidth, int srcHeight, int dstx, int dsty, int dstWidth, int dstHeight);

	/**
	 * Fills a rectangle starting at x, y extending by width to the right and by height downwards (y-axis points downwards) using
	 * the current color.
	 *
	 * @param x      The x coordinate
	 * @param y      The y coordinate
	 * @param width  The width in pixels
	 * @param height The height in pixels
	 */
	public void fillRectangle(int x, int y, int width, int height) {
		ensureLoaded();
		rectangle(x, y, width, height, DrawType.FILL);
	}

	/**
	 * Draws a circle outline with the center at x,y and a radius using the current color and stroke width.
	 *
	 * @param x      The x-coordinate of the center
	 * @param y      The y-coordinate of the center
	 * @param radius The radius in pixels
	 */
	public void drawCircle(int x, int y, int radius) {
		ensureLoaded();
		circle(x, y, radius, DrawType.STROKE);
	}

	/**
	 * Fills a circle with the center at x,y and a radius using the current color.
	 *
	 * @param x      The x-coordinate of the center
	 * @param y      The y-coordinate of the center
	 * @param radius The radius in pixels
	 */
	public void fillCircle(int x, int y, int radius) {
		ensureLoaded();
		circle(x, y, radius, DrawType.FILL);
	}

	/**
	 * Fills a triangle with vertices at x1,y1 and x2,y2 and x3,y3 using the current color.
	 *
	 * @param x1 The x-coordinate of vertex 1
	 * @param y1 The y-coordinate of vertex 1
	 * @param x2 The x-coordinate of vertex 2
	 * @param y2 The y-coordinate of vertex 2
	 * @param x3 The x-coordinate of vertex 3
	 * @param y3 The y-coordinate of vertex 3
	 */
	public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
		ensureLoaded();
		triangle(x1, y1, x2, y2, x3, y3, DrawType.FILL);
	}

	/**
	 * Returns the 32-bit RGBA8888 value of the pixel at x, y. For Alpha formats the RGB components will be one.
	 *
	 * @param x The x-coordinate
	 * @param y The y-coordinate
	 * @return The pixel color in RGBA8888 format.
	 */
	public int getPixel(int x, int y) {
		ensureLoaded();
		//if (JTranscSystem.isPureJs()) {
		//	return ColorFormat8.transform(ColorFormat8.PUREJS, ColorFormat8.GDX, _getPixel(x, y));
		//} else {
		//	return ColorFormat8.transform(ColorFormat8.LIME, ColorFormat8.GDX, _getPixel(x, y));
		//}
		return _getPixel(x, y);
	}

	/**
	 * Draws a pixel at the given location with the current color.
	 *
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 */
	public void drawPixel(int x, int y) {
		ensureLoaded();
		drawPixel(x, y, this.color);
	}

	/**
	 * Draws a pixel at the given location with the given color.
	 *
	 * @param x     the x-coordinate
	 * @param y     the y-coordinate
	 * @param color the color in RGBA8888 format.
	 */
	public void drawPixel(int x, int y, int color) {
		ensureLoaded();
		_setPixel(x, y, ColorFormat8.transform(ColorFormat8.GDX, ColorFormat8.LIME, color));
	}

	native private void circle(int x, int y, int radius, DrawType drawType);

	native private void line(int x, int y, int x2, int y2, DrawType drawType);

	native private void rectangle(int x, int y, int width, int height, DrawType drawType);

	native private void triangle(int x1, int y1, int x2, int y2, int x3, int y3, DrawType drawType);

	//native private void image(CanvasElement image, int srcX, int srcY, int srcWidth, int srcHeight, int dstX, int dstY, int dstWidth, int dstHeight);

	native private void fillOrStrokePath(DrawType drawType);

	private enum DrawType {
		FILL, STROKE
	}

	private int _getPixel(int x, int y) {
		return data[y * width + x];
	}

	private void _setPixel(int x, int y, int color) {
		data[y * width + x] = color;
	}

}
