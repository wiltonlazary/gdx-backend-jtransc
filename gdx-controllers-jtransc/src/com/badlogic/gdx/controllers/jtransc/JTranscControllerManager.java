package com.badlogic.gdx.controllers.jtransc;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.ControllerManager;
import com.badlogic.gdx.utils.Array;

public class JTranscControllerManager implements ControllerManager {
	@Override
	public Array<Controller> getControllers() {
		return new Array<>();
	}

	@Override
	public void addListener(ControllerListener listener) {
	}

	@Override
	public void removeListener(ControllerListener listener) {
	}

	@Override
	public void clearListeners() {
	}
}
