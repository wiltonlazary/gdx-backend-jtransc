package com.jtransc.media.limelibgdx;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.GLVersion;
import com.jtransc.annotation.haxe.HaxeMethodBody;
import com.jtransc.media.limelibgdx.dummy.DummyGL20;
import com.jtransc.media.limelibgdx.flash.FlashGL20;
import com.jtransc.media.limelibgdx.gl.LimeGL20;
import com.jtransc.JTranscSystem;
import com.jtransc.media.limelibgdx.logger.LoggerGL20;

public class LimeGraphics implements Graphics {
	//final private GL20 gl = new DummyGL20();
	final private GL20 gl;
	//final private GL20 gl = new LoggerGL20(new LimeGL20());
	int frameId = 0;

	public LimeGraphics(boolean trace) {
		GL20Ext gl;
		if (JTranscSystem.isSwf()) {
			gl = FlashGL20.create();
		} else {
			gl = new LimeGL20();
		}
		if (trace) gl = new LoggerGL20(gl);
		this.gl = gl;
	}

	private Monitor2[] monitors = new Monitor2[]{
			new Monitor2(640, 480, "default")
	};
	private DisplayMode2[] displayModes = new DisplayMode2[]{
			new DisplayMode2(640, 480, 60, 32)
	};
	public int width = 640;
	public int height = 480;

	static public class Monitor2 extends Monitor {
		public Monitor2(int virtualX, int virtualY, String name) {
			super(virtualX, virtualY, name);
		}
	}

	static public class DisplayMode2 extends DisplayMode {
		public DisplayMode2(int width, int height, int refreshRate, int bitsPerPixel) {
			super(width, height, refreshRate, bitsPerPixel);
		}
	}

	double lastStamp = 0.0;
	float deltaTime = 0f;
	public void frame() {
		double currentStamp = JTranscSystem.stamp();
		frameId++;

		double ms = JTranscSystem.elapsedTime(lastStamp, currentStamp);
		deltaTime = (float)ms / 1000f;

		lastStamp = currentStamp;
	}

	@Override
	public boolean isGL30Available() {
		return false;
	}

	@Override
	public GL20 getGL20() {
		return gl;
	}

	@Override
	public GL30 getGL30() {
		return null;
	}

	@Override
	public int getWidth() {
		return LimeApplication.HaxeLimeGdxApplication.instance.getWidth();
	}

	@Override
	public int getHeight() {
		return LimeApplication.HaxeLimeGdxApplication.instance.getHeight();
	}

	@Override
	public int getBackBufferWidth() {
		return getWidth();
	}

	@Override
	public int getBackBufferHeight() {
		return getHeight();
	}

	@Override
	public long getFrameId() {
		return frameId;
	}

	@Override
	public float getDeltaTime() {
		return deltaTime;
	}

	@Override
	public float getRawDeltaTime() {
		return deltaTime;
	}

	@Override
	public int getFramesPerSecond() {
		return 60;
	}

	@Override
	public GraphicsType getType() {
		return GraphicsType.WebGL;
	}

	@Override
	public GLVersion getGLVersion() {
		return new GLVersion(LimeDevice.getType(), "2.0", "lime", "lime");
	}

	// https://github.com/openfl/lime/blob/develop/lime/system/System.hx
	// https://github.com/openfl/lime/blob/develop/lime/system/Display.hx
	@HaxeMethodBody("return lime.system.System.getDisplay(0).dpi;")
	private float getPpi() {
		return 96;
	}

	@Override
	public float getPpiX() {
		return getPpi();
	}

	@Override
	public float getPpiY() {
		return getPpi();
	}

	static private final float CENTIMETERS_PER_INCH = 2.54f;
	static private final float DENSITY_FACTOR = 160f;

	@Override
	public float getPpcX() {
		return getPpiX() / CENTIMETERS_PER_INCH;
	}

	@Override
	public float getPpcY() {
		return getPpiY() / CENTIMETERS_PER_INCH;
	}

	@Override
	public float getDensity() {
		return getPpi() / DENSITY_FACTOR;
	}

	@Override
	public boolean supportsDisplayModeChange() {
		return false;
	}

	@Override
	public Monitor getPrimaryMonitor() {
		return monitors[0];
	}

	@Override
	public Monitor getMonitor() {
		return monitors[0];
	}

	@Override
	public Monitor[] getMonitors() {
		return monitors;
	}

	@Override
	public DisplayMode[] getDisplayModes() {
		return displayModes;
	}

	@Override
	public DisplayMode[] getDisplayModes(Monitor monitor) {
		return displayModes;
	}

	@Override
	public DisplayMode getDisplayMode() {
		return displayModes[0];
	}

	@Override
	public DisplayMode getDisplayMode(Monitor monitor) {
		return displayModes[0];
	}

	@Override
	public boolean setFullscreenMode(DisplayMode displayMode) {
		return false;
	}

	@Override
	public boolean setWindowedMode(int width, int height) {
		return false;
	}

	@Override
	public void setTitle(String s) {

	}

	@Override
	public void setUndecorated(boolean b) {

	}

	@Override
	public void setResizable(boolean b) {

	}

	@Override
	public void setVSync(boolean b) {

	}

	@Override
	public BufferFormat getBufferFormat() {
		return null;
	}

	@Override
	public boolean supportsExtension(String s) {
		return false;
	}

	@Override
	public void setContinuousRendering(boolean b) {

	}

	@Override
	public boolean isContinuousRendering() {
		return false;
	}

	@Override
	public void requestRendering() {

	}

	@Override
	public boolean isFullscreen() {
		return false;
	}

	@Override
	public Cursor newCursor(Pixmap pixmap, int i, int i1) {
		return null;
	}

	@Override
	public void setCursor(Cursor cursor) {

	}

	@Override
	public void setSystemCursor(Cursor.SystemCursor systemCursor) {

	}
}
