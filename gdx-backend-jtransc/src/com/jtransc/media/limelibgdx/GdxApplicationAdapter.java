package com.jtransc.media.limelibgdx;

import com.badlogic.gdx.*;
import com.badlogic.gdx.utils.Clipboard;
import com.badlogic.gdx.utils.Queue;

import java.util.ArrayList;

abstract public class GdxApplicationAdapter implements Application {
	ArrayList<LifecycleListener> listeners = new ArrayList<>();
	private Queue<Runnable> postRunnableList = new Queue<>();
	private Queue<Runnable> postRunnableListCopy = new Queue<>();
	private int logLevel = LOG_DEBUG;

	final private ApplicationListener applicationListener;
	final private Audio audio;

	public GdxApplicationAdapter(ApplicationListener applicationListener) {
		Gdx.app = this;
		this.applicationListener = applicationListener;
		Gdx.audio = audio = createAudio();
	}

	public void log(int level, String tag, String message, Throwable exception) {
		if (level <= this.logLevel) {
			System.out.println("[" + tag + "] " + message + " : " + exception);
		}
	}

	@Override
	public void log(String tag, String message) {
		log(LOG_INFO, tag, message, null);
	}

	@Override
	public void log(String tag, String message, Throwable exception) {
		log(LOG_INFO, tag, message, exception);
	}

	@Override
	public void error(String tag, String message) {
		log(LOG_ERROR, tag, message, null);
	}

	@Override
	public void error(String tag, String message, Throwable exception) {
		log(LOG_ERROR, tag, message, exception);
	}

	@Override
	public void debug(String tag, String message) {
		log(LOG_DEBUG, tag, message, null);
	}

	@Override
	public void debug(String tag, String message, Throwable exception) {
		log(LOG_DEBUG, tag, message, exception);
	}

	@Override
	public void setLogLevel(int logLevel) {
		this.logLevel = logLevel;
	}

	@Override
	public int getLogLevel() {
		return this.logLevel;
	}

	@Override
	public void exit() {
		System.exit(0);
	}

	@Override
	public void postRunnable(Runnable runnable) {
		postRunnableList.addLast(runnable);
	}

	@Override
	public void addLifecycleListener(LifecycleListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeLifecycleListener(LifecycleListener listener) {
		listeners.remove(listener);
	}

	protected void executePostRunnableQueue() {
		while (postRunnableList.size > 0) {
			postRunnableListCopy.addLast(postRunnableList.removeFirst());
		}

		while (postRunnableListCopy.size > 0) {
			Runnable runnable = postRunnableListCopy.removeFirst();
			runnable.run();
		}
	}

	public void onFrame() {
		executePostRunnableQueue();
	}

	public void onPaused() {
		applicationListener.pause();
		for (LifecycleListener listener : listeners) {
			listener.pause();
		}
	}

	public void onResumed() {
		applicationListener.resume();
		for (LifecycleListener listener : listeners) {
			listener.resume();
		}
	}

	public void onDisposed() {
		applicationListener.dispose();
		for (LifecycleListener listener : listeners) {
			listener.dispose();
		}
	}

	abstract protected Clipboard createClipboard();

	abstract protected Audio createAudio();

	private Clipboard clipboard;

	@Override
	final public Clipboard getClipboard() {
		if (this.clipboard == null) this.clipboard = createClipboard();
		return this.clipboard;
	}

	@Override
	final public ApplicationListener getApplicationListener() {
		return applicationListener;
	}

	@Override
	final public Audio getAudio() {
		return audio;
	}

	@SuppressWarnings("unused")
	public void create() {
		applicationListener.create();
		resized(LimeApplication.HaxeLimeGdxApplication.instance.getWidth(), LimeApplication.HaxeLimeGdxApplication.instance.getHeight());
	}

	@SuppressWarnings("unused")
	public void render() {
		onFrame();
		applicationListener.render();
	}

	@SuppressWarnings("unused")
	public void resized(int width, int height) {
		Gdx.gl.glViewport(0, 0, width, height);
		applicationListener.resize(width, height);
	}
}
