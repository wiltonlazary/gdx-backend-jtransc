package com.badlogic.gdx.backends.lwjgl;

import com.badlogic.gdx.ApplicationListener;
import com.jtransc.media.limelibgdx.LimeApplication;
import com.jtransc.media.limelibgdx.LimeGraphics;

/**
 * Compatibility class to run games without touching them.
 */
public class LwjglApplication extends LimeApplication {
	public LwjglApplication(ApplicationListener applicationListener, String title, int width, int height) {
		super(applicationListener, title, width, height);
	}

	public LwjglApplication(ApplicationListener listener) {
		this(listener, (String) null, LimeGraphics.defaultWidth, LimeGraphics.defaultHeight);
	}


	public LwjglApplication(ApplicationListener listener, LwjglApplicationConfiguration config) {
		this(listener, config.title, config.width, config.height);
	}
}

