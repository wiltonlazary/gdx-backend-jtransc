package com.jtransc.media.limelibgdx;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class LimeInput implements Input {
	static public boolean[] keys = new boolean[0x200];
	static public boolean[] justPressed = new boolean[0x200];
	static public boolean[] justReleased = new boolean[0x200];
	static public double mouseX;
	static public double mouseY;
	static public boolean[] mouseButtons = new boolean[16];

	static private void updateMouseXY(double x, double y) {
		LimeInput.mouseX = x;
		LimeInput.mouseY = y;
	}

	static public void lime_onMouseUp(double x, double y, int button) {
		updateMouseXY(x, y);
		mouseButtons[button] = false;
		//System.out.println("lime_onMouseUp:" + x + "," + y + "," + button);
	}

	static public void lime_onMouseDown(double x, double y, int button) {
		updateMouseXY(x, y);
		mouseButtons[button] = true;
		//System.out.println("lime_onMouseDown:" + x + "," + y + "," + button);
	}

	static public void lime_onMouseMove(double x, double y) {
		updateMouseXY(x, y);
		//System.out.println("lime_onMouseMove:" + x + "," + y);
	}

	static public void lime_onKeyUp(int keyCode, int modifier) {
		//System.out.println("lime_onKeyUp:" + keyCode + "," + modifier);
		keys[keyCode & 0x1FF] = false;
		justReleased[keyCode & 0x1FF] = true;
	}

	static public void lime_onKeyDown(int keyCode, int modifier) {
		//System.out.println("lime_onKeyDown:" + keyCode + "," + modifier);
		keys[keyCode & 0x1FF] = true;
		justPressed[keyCode & 0x1FF] = true;
	}

	// Called once per frame1
	static public void lime_frame() {
		for (int n = 0; n < justPressed.length; n++) justPressed[n] = false;
		for (int n = 0; n < justReleased.length; n++) justReleased[n] = false;
	}

	@Override
	public float getAccelerometerX() {
		return 0;
	}

	@Override
	public float getAccelerometerY() {
		return 0;
	}

	@Override
	public float getAccelerometerZ() {
		return 0;
	}

	@Override
	public float getGyroscopeX() {
		return 0;
	}

	@Override
	public float getGyroscopeY() {
		return 0;
	}

	@Override
	public float getGyroscopeZ() {
		return 0;
	}

	@Override
	public int getX() {
		return (int) mouseX;
	}

	@Override
	public int getX(int i) {
		return 0;
	}

	@Override
	public int getDeltaX() {
		return 0;
	}

	@Override
	public int getDeltaX(int i) {
		return 0;
	}

	@Override
	public int getY() {
		return (int) mouseY;
	}

	@Override
	public int getY(int i) {
		return 0;
	}

	@Override
	public int getDeltaY() {
		return 0;
	}

	@Override
	public int getDeltaY(int i) {
		return 0;
	}

	@Override
	public boolean isTouched() {
		return false;
	}

	@Override
	public boolean justTouched() {
		return false;
	}

	@Override
	public boolean isTouched(int i) {
		return false;
	}

	@Override
	public boolean isButtonPressed(int i) {
		return mouseButtons[i];
	}

	private int convertKeyCode(int i) {
		// https://github.com/openfl/lime/blob/develop/lime/ui/KeyCode.hx
		switch (i) {
			case Keys.ENTER: return 0x0D;
			case Keys.ESCAPE: return 0x1B;
			case Keys.SPACE: return 0x20;
			case Keys.RIGHT: return 0x4000004F;
			case Keys.LEFT: return 0x40000050;
			case Keys.DOWN: return 0x40000051;
			case Keys.UP: return 0x40000052;
			case Keys.A: return 0x61;
			case Keys.B: return 0x62;
			case Keys.C: return 0x63;
			case Keys.D: return 0x64;
			case Keys.E: return 0x65;
			case Keys.F: return 0x66;
			case Keys.G: return 0x67;
			case Keys.H: return 0x68;
			case Keys.I: return 0x69;
			case Keys.J: return 0x6A;
			case Keys.K: return 0x6B;
			case Keys.L: return 0x6C;
			case Keys.M: return 0x6D;
			case Keys.N: return 0x6E;
			case Keys.O: return 0x6F;
			case Keys.P: return 0x70;
			case Keys.Q: return 0x71;
			case Keys.R: return 0x72;
			case Keys.S: return 0x73;
			case Keys.T: return 0x74;
			case Keys.U: return 0x75;
			case Keys.V: return 0x76;
			case Keys.W: return 0x77;
			case Keys.X: return 0x78;
			case Keys.Y: return 0x79;
			case Keys.Z: return 0x7A;
		}
		return i & 0x1FF;
	}

	private boolean checkKeyArray(boolean[] array, int i) {
		if (i < 0) {
			for (int n = 0; n < array.length; n++) if (array[n]) return true;
			return false;
		}
		return array[convertKeyCode(i) & 0x1FF];
	}

	@Override
	public boolean isKeyPressed(int i) {
		return checkKeyArray(keys, i);
	}

	@Override
	public boolean isKeyJustPressed(int i) {
		return checkKeyArray(justPressed, i);
	}

	@Override
	public void getTextInput(TextInputListener textInputListener, String s, String s1, String s2) {

	}

	@Override
	public void setOnscreenKeyboardVisible(boolean b) {

	}

	@Override
	public void vibrate(int i) {

	}

	@Override
	public void vibrate(long[] longs, int i) {

	}

	@Override
	public void cancelVibrate() {

	}

	@Override
	public float getAzimuth() {
		return 0;
	}

	@Override
	public float getPitch() {
		return 0;
	}

	@Override
	public float getRoll() {
		return 0;
	}

	@Override
	public void getRotationMatrix(float[] floats) {

	}

	@Override
	public long getCurrentEventTime() {
		return 0;
	}

	@Override
	public void setCatchBackKey(boolean b) {

	}

	@Override
	public boolean isCatchBackKey() {
		return false;
	}

	@Override
	public void setCatchMenuKey(boolean b) {

	}

	@Override
	public boolean isCatchMenuKey() {
		return false;
	}

	@Override
	public void setInputProcessor(InputProcessor inputProcessor) {
		System.out.println("Not implemented setInputProcessor!");
	}

	@Override
	public InputProcessor getInputProcessor() {
		return null;
	}

	@Override
	public boolean isPeripheralAvailable(Peripheral peripheral) {
		return false;
	}

	@Override
	public int getRotation() {
		return 0;
	}

	@Override
	public Orientation getNativeOrientation() {
		return Orientation.Landscape;
	}

	@Override
	public void setCursorCatched(boolean b) {

	}

	@Override
	public boolean isCursorCatched() {
		return false;
	}

	@Override
	public void setCursorPosition(int i, int i1) {

	}
}
