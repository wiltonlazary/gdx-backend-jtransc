package com.jtransc.media.limelibgdx;

import com.badlogic.gdx.ApplicationListener;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class GdxApplicationAdapterTest {
	final ArrayList<String> out = new ArrayList<>();

	final private LimeApplication app = new LimeApplication(new ApplicationListener() {
		@Override
		public void create() {
		}

		@Override
		public void resize(int width, int height) {
		}

		@Override
		public void render() {
			out.add("render");
		}

		@Override
		public void pause() {
		}

		@Override
		public void resume() {
		}

		@Override
		public void dispose() {
		}
	}, "test", 10, 10);

	@Test
	public void testPostRunnable() throws Exception {
		app.postRunnable(() -> {
			out.add("run1");
			app.postRunnable(() -> {
				out.add("run2");
			});
		});

		Assert.assertEquals("", String.join(",", out));
		app.render();
		Assert.assertEquals("run1,render", String.join(",", out));
		app.render();
		Assert.assertEquals("run1,render,run2,render", String.join(",", out));
		app.render();
	}

	@Test
	public void testClipboard() throws Exception {
		final String str = "hello";
		app.getClipboard().setContents(str);
		Assert.assertEquals(str, app.getClipboard().getContents());
	}

	@Test
	public void testGraphics() throws Exception {
		app.getGraphics().getGLVersion();
	}
}
