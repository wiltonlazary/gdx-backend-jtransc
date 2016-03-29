package com.jtransc.media.limelibgdx;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class LimeInput implements Input {
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
		return 0;
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
		return 0;
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
		return false;
	}

	@Override
	public boolean isKeyPressed(int i) {
		return false;
	}

	@Override
	public boolean isKeyJustPressed(int i) {
		return false;
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
