package com.jtransc.media.limelibgdx;

import com.badlogic.gdx.*;
import com.badlogic.gdx.utils.Clipboard;
import jtransc.annotation.haxe.HaxeMethodBody;

public class LimeApplication implements Application {
	final private ApplicationListener applicationListener;
	final private Graphics graphics = new LimeGraphics();
	final private Audio audio = new LimeAudio();
	final private Input input = new LimeInput();
	final private Files files = new LimeFiles();
	final private Net net = new LimeNet();

	public LimeApplication(ApplicationListener applicationListener, String title, int width, int height) {
		this.applicationListener = applicationListener;

		Gdx.app = this;
		Gdx.graphics = graphics;
		Gdx.audio = audio;
		Gdx.input = input;
		Gdx.files = files;
		Gdx.net = net;

		Gdx.gl = graphics.getGL20();
		Gdx.gl20 = graphics.getGL20();
		Gdx.gl30 = graphics.getGL30();
	}

	@Override
	public ApplicationListener getApplicationListener() {
		return applicationListener;
	}

	@Override
	public Graphics getGraphics() {
		return graphics;
	}

	@Override
	public Audio getAudio() {
		return audio;
	}

	@Override
	public Input getInput() {
		return input;
	}

	@Override
	public Files getFiles() {
		return files;
	}

	@Override
	public Net getNet() {
		return net;
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
		return ApplicationType.WebGL;
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
		return new Clipboard() {
			@Override
			@HaxeMethodBody("return HaxeNatives.str(lime.system.Clipboard.text);")
			native public String getContents();

			@Override
			@HaxeMethodBody("lime.system.Clipboard.text = p0._str;")
			native public void setContents(String content);
		};
	}

	@Override
	public void postRunnable(Runnable runnable) {

	}

	@Override
	public void exit() {
		System.exit(0);
	}

	@Override
	public void addLifecycleListener(LifecycleListener listener) {

	}

	@Override
	public void removeLifecycleListener(LifecycleListener listener) {

	}
}
