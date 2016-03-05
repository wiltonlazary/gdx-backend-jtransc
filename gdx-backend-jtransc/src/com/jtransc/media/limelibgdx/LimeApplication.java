package com.jtransc.media.limelibgdx;

import com.badlogic.gdx.*;
import com.badlogic.gdx.utils.Clipboard;

public class LimeApplication implements Application {
	@Override
	public ApplicationListener getApplicationListener() {
		return new LimeApplicationListener();
	}

	@Override
	public Graphics getGraphics() {
		return new LimeGraphics();
	}

	@Override
	public Audio getAudio() {
		return new LimeAudio();
	}

	@Override
	public Input getInput() {
		return new LimeInput();
	}

	@Override
	public Files getFiles() {
		return new LimeFiles();
	}

	@Override
	public Net getNet() {
		return new LimeNet();
	}

	@Override
	public void log(String tag, String message) {

	}

	@Override
	public void log(String tag, String message, Throwable exception) {

	}

	@Override
	public void error(String tag, String message) {

	}

	@Override
	public void error(String tag, String message, Throwable exception) {

	}

	@Override
	public void debug(String tag, String message) {

	}

	@Override
	public void debug(String tag, String message, Throwable exception) {

	}

	@Override
	public void setLogLevel(int logLevel) {

	}

	@Override
	public int getLogLevel() {
		return 0;
	}

	@Override
	public ApplicationType getType() {
		return null;
	}

	@Override
	public int getVersion() {
		return 0;
	}

	@Override
	public long getJavaHeap() {
		return 0;
	}

	@Override
	public long getNativeHeap() {
		return 0;
	}

	@Override
	public Preferences getPreferences(String name) {
		return null;
	}

	@Override
	public Clipboard getClipboard() {
		return null;
	}

	@Override
	public void postRunnable(Runnable runnable) {

	}

	@Override
	public void exit() {

	}

	@Override
	public void addLifecycleListener(LifecycleListener listener) {

	}

	@Override
	public void removeLifecycleListener(LifecycleListener listener) {

	}
}
