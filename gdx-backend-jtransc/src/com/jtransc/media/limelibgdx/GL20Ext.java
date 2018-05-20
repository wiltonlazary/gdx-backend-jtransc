package com.jtransc.media.limelibgdx;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;

public interface GL20Ext extends GL20 {
	void present();

	public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, Pixmap pixmap);
}
