package com.jtransc.media.limelibgdx;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.jtransc.annotation.JTranscMethodBody;

public class LimeInput implements Input {
	static public boolean[] keys = new boolean[0x200];
	static public boolean[] justPressed = new boolean[0x200];
	static public boolean[] justReleased = new boolean[0x200];

	static Pointer[] pointers;

	static private InputProcessor inputProcessor = new InputAdapter();

	static {
		pointers = new Pointer[16];
		for (int n = 0; n < pointers.length; n++) pointers[n] = new Pointer();
	}

	@SuppressWarnings("unused")
	static public void lime_onMouseUp(double x, double y, int button) {
		pointers[0].setXY(x, y);
		pointers[0].releaseButton(button);
		//System.out.println("lime_onMouseUp:" + x + "," + y + "," + button);
		inputProcessor.touchUp((int)x, (int)y, 0, button);
	}

	@SuppressWarnings("unused")
	static public void lime_onMouseDown(double x, double y, int button) {
		pointers[0].setXY(x, y);
		pointers[0].pressButton(button);
		//System.out.println("lime_onMouseDown:" + x + "," + y + "," + button);

		inputProcessor.touchDown((int)x, (int)y, 0, button);
	}

	@SuppressWarnings("unused")
	static public void lime_onMouseMove(double x, double y) {
		pointers[0].setXY(x, y);
		if (pointers[0].isPressingAnyButton()) {
			inputProcessor.touchDragged((int)x, (int)y, 0);
		} else {
			inputProcessor.mouseMoved((int)x, (int)y);
		}
	}

	@SuppressWarnings("unused")
	static public void lime_onKeyUp(int keyCode, int modifier) {
		//System.out.println("lime_onKeyUp:" + keyCode + "," + modifier);
		keys[keyCode & 0x1FF] = false;
		justReleased[keyCode & 0x1FF] = true;

		inputProcessor.keyUp(convertKeyCode(keyCode));
	}

	@SuppressWarnings("unused")
	static public void lime_onKeyDown(int keyCode, int modifier) {
		//System.out.println("lime_onKeyDown:" + keyCode + "," + modifier);
		keys[keyCode & 0x1FF] = true;
		justPressed[keyCode & 0x1FF] = true;

		inputProcessor.keyDown(convertKeyCode(keyCode));
	}

	@SuppressWarnings("unused")
	static public void lime_onKeyTyped(char character) {
		inputProcessor.keyTyped(character);
	}

	@SuppressWarnings("unused")
	static public void lime_onTouchStart(int id, double x, double y) {
		System.out.println("lime_onTouchStart:" + id + "," + x + "," + y);
		pointers[id].setXY(x, y);
		pointers[id].pressButton(0);
		inputProcessor.touchDown((int)x, (int)y, id, 0);
	}

	@SuppressWarnings("unused")
	static public void lime_onTouchMove(int id, double x, double y) {
		System.out.println("lime_onTouchMove:" + id + "," + x + "," + y);
		pointers[id].setXY(x, y);
		inputProcessor.touchDragged((int)x, (int)y, id);
	}

	@SuppressWarnings("unused")
	static public void lime_onTouchEnd(int id, double x, double y) {
		System.out.println("lime_onTouchEnd:" + id + "," + x + "," + y);
		pointers[id].setXY(x, y);
		pointers[id].releaseButton(0);
		inputProcessor.touchUp((int)x, (int)y, id, 0);
	}

	@SuppressWarnings("unused")
	public void onMouseUp(double x, double y, int button) {
		lime_onMouseUp(x, y, button);
	}

	@SuppressWarnings("unused")
	public void onMouseDown(double x, double y, int button) {
		lime_onMouseDown(x, y, button);
	}

	@SuppressWarnings("unused")
	public void onMouseMove(double x, double y) {
		lime_onMouseMove(x, y);
	}

	@SuppressWarnings("unused")
	void onKeyUp(int keyCode, int modifier) {
		lime_onKeyUp(keyCode, modifier);
	}

	@SuppressWarnings("unused")
	public void onKeyDown(int keyCode, int modifier) {
		lime_onKeyDown(keyCode, modifier);
	}

	@SuppressWarnings("unused")
	public void onKeyTyped(char character) {
		lime_onKeyTyped(character);
	}

	@SuppressWarnings("unused")
	public void onTouchStart(int id, double x, double y) {
		lime_onTouchStart(id, x, y);
	}

	@SuppressWarnings("unused")
	public void onTouchMove(int id, double x, double y) {
		lime_onTouchMove(id, x, y);
	}

	@SuppressWarnings("unused")
	public void onTouchEnd(int id, double x, double y) {
		lime_onTouchEnd(id, x, y);
	}

	// Called once per frame1
	static public void lime_frame() {
		for (int n = 0; n < justPressed.length; n++) justPressed[n] = false;
		for (int n = 0; n < justReleased.length; n++) justReleased[n] = false;
		for (int n = 0; n < pointers.length; n++) pointers[n].frame();
	}

	// @TODO: https://github.com/openfl/lime/blob/develop/lime/system/Sensor.hx
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
		return getX(0);
	}

	@Override
	public int getX(int i) {
		return (int) pointers[i].getX();
	}

	@Override
	public int getDeltaX() {
		return getDeltaX(0);
	}

	@Override
	public int getDeltaX(int i) {
		return (int) pointers[i].getDeltaX();
	}

	@Override
	public int getY() {
		return getY(0);
	}

	@Override
	public int getY(int i) {
		return (int) pointers[i].getY();
	}

	@Override
	public int getDeltaY() {
		return getDeltaY(0);
	}

	@Override
	public int getDeltaY(int i) {
		return (int) pointers[i].getDeltaY();
	}

	@Override
	public boolean isTouched() {
		for (int n = 0; n < pointers.length; n++) if (pointers[n].isPressingAnyButton()) return true;
		return false;
	}

	@Override
	public boolean justTouched() {
		for (int n = 0; n < pointers.length; n++) if (pointers[n].justPressedAnyButton()) return true;
		return false;
	}

	@Override
	public boolean isTouched(int i) {
		return pointers[i].isPressingAnyButton();
	}

	@Override
	public boolean isButtonPressed(int i) {
		return pointers[0].isPressingButton(i);
	}

	@JTranscMethodBody(target = "js", value = "return p0;")
	static private int convertKeyCode(int i) {
		// https://github.com/openfl/lime/blob/develop/lime/ui/KeyCode.hx
		switch (i) {
			case Keys.ENTER:
				return 0x0D;
			case Keys.ESCAPE:
				return 0x1B;
			case Keys.SPACE:
				return 0x20;
			case Keys.RIGHT:
				return 0x4000004F;
			case Keys.LEFT:
				return 0x40000050;
			case Keys.DOWN:
				return 0x40000051;
			case Keys.UP:
				return 0x40000052;
			case Keys.A:
				return 0x61;
			case Keys.B:
				return 0x62;
			case Keys.C:
				return 0x63;
			case Keys.D:
				return 0x64;
			case Keys.E:
				return 0x65;
			case Keys.F:
				return 0x66;
			case Keys.G:
				return 0x67;
			case Keys.H:
				return 0x68;
			case Keys.I:
				return 0x69;
			case Keys.J:
				return 0x6A;
			case Keys.K:
				return 0x6B;
			case Keys.L:
				return 0x6C;
			case Keys.M:
				return 0x6D;
			case Keys.N:
				return 0x6E;
			case Keys.O:
				return 0x6F;
			case Keys.P:
				return 0x70;
			case Keys.Q:
				return 0x71;
			case Keys.R:
				return 0x72;
			case Keys.S:
				return 0x73;
			case Keys.T:
				return 0x74;
			case Keys.U:
				return 0x75;
			case Keys.V:
				return 0x76;
			case Keys.W:
				return 0x77;
			case Keys.X:
				return 0x78;
			case Keys.Y:
				return 0x79;
			case Keys.Z:
				return 0x7A;
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
		this.inputProcessor = inputProcessor;
	}

	@Override
	public InputProcessor getInputProcessor() {
		return this.inputProcessor;
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

	private static class Pointer {
		private int lastB;
		private int currentB;
		private double lastX;
		private double lastY;
		private double currentX;
		private double currentY;

		public void setXY(double x, double y) {
			this.currentX = x;
			this.currentY = y;
		}

		public void setB(int b) {
			this.currentB = b;
		}

		public void frame() {
			this.lastX = currentX;
			this.lastY = currentY;
			this.lastB = currentB;
		}

		public double getX() {
			return currentX;
		}

		public double getY() {
			return currentY;
		}

		public double getDeltaX() {
			return currentX - lastX;
		}

		public double getDeltaY() {
			return currentY - lastY;
		}

		public boolean isPressingButton(int i) {
			return (currentB & (1 << i)) != 0;
		}

		public boolean isPressingAnyButton() {
			return currentB != 0;
		}

		public boolean justPressedAnyButton() {
			return isPressingAnyButton() && (currentB != lastB);
		}

		public void pressButton(int button) {
			int mask = (1 << button);
			currentB = (currentB & ~mask) | mask;
		}

		public void releaseButton(int button) {
			int mask = (1 << button);
			currentB = (currentB & ~mask);
		}
	}
}
