package com.jtransc.media.limelibgdx;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Pixmap;
import com.jtransc.media.limelibgdx.dummy.DummyGL20;
import com.jtransc.media.limelibgdx.gl.LimeGL20;
import jtransc.JTranscSystem;

public class LimeGraphics implements Graphics {
	//final private GL20 gl = new DummyGL20();
	final private GL20 gl = new LimeGL20();
	int frameId = 0;
	private Monitor2[] monitors = new Monitor2[]{
			new Monitor2(640, 480, "default")
	};
	private DisplayMode2[] displayModes = new DisplayMode2[]{
			new DisplayMode2(640, 480, 60, 32)
	};

	class Monitor2 extends Monitor {
		protected Monitor2(int virtualX, int virtualY, String name) {
			super(virtualX, virtualY, name);
		}
	}

	class DisplayMode2 extends DisplayMode {
		protected DisplayMode2(int width, int height, int refreshRate, int bitsPerPixel) {
			super(width, height, refreshRate, bitsPerPixel);
		}
	}

	int lastStamp = 0;
	float deltaTime = 0f;
	public void frame() {
		int currentStamp = JTranscSystem.stamp();
		frameId++;

		int ms = JTranscSystem.elapsedTime(lastStamp, currentStamp);
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
		return 640;
	}

	@Override
	public int getHeight() {
		return 480;
	}

	@Override
	public int getBackBufferWidth() {
		return 640;
	}

	@Override
	public int getBackBufferHeight() {
		return 480;
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
	public float getPpiX() {
		return 160;
	}

	@Override
	public float getPpiY() {
		return 160;
	}

	@Override
	public float getPpcX() {
		return 160;
	}

	@Override
	public float getPpcY() {
		return 160;
	}

	@Override
	public float getDensity() {
		return 160;
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
