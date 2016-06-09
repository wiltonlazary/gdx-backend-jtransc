package com.jtransc.media.limelibgdx.soft;

import com.jtransc.media.limelibgdx.StateGL20;
import org.junit.Assert;
import org.junit.Test;

public class SoftGlTest {
	final int width = 2;
	final int height = 2;
	StateGL20<SoftGL20.Impl> gl = SoftGL20.create(new BitmapData32(width, height));
	SoftGL20.Impl impl = gl.getImpl();

	@Test
	public void name() throws Exception {
		gl.glClearColor(1f, 0f, 0.5f, 1f);
		gl.glClear(0);
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Assert.assertEquals("RGBA(255,0, 127,255)", Color32.toString(impl.backbuffer.get(x, y)));
			}
		}
	}
}
