package com.jtransc.media.limelibgdx;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.LifecycleListener;
import com.badlogic.gdx.utils.Queue;

import java.util.ArrayList;

abstract public class GdxApplicationAdapter implements Application {
	ArrayList<LifecycleListener> listeners = new ArrayList<>();
	Queue<Runnable> postRunnableList = new Queue<>();
	private int logLevel = LOG_DEBUG;

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
			Runnable runnable = postRunnableList.removeFirst();
			runnable.run();
		}
	}

	public void onFrame() {
		executePostRunnableQueue();
	}

	public void onPaused() {
		for (LifecycleListener listener : listeners) {
			listener.pause();
		}
	}

	public void onResumed() {
		for (LifecycleListener listener : listeners) {
			listener.resume();
		}
	}

	public void onDisposed() {
		for (LifecycleListener listener : listeners) {
			listener.dispose();
		}
	}
}
