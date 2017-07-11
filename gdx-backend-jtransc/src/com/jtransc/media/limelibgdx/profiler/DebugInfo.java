package com.jtransc.media.limelibgdx.profiler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.jtransc.JTranscSystem;
import com.jtransc.annotation.haxe.HaxeMethodBody;

import java.util.ArrayList;

/**
 * A nicer class for showing framerate that doesn't spam the console
 * like Logger.log()
 *
 * @author William Hartman
 */
public class DebugInfo implements Disposable {

	private BitmapFont font;
	private SpriteBatch batch;
	private OrthographicCamera cam;

	private long frameStart = 0;
	private int frames = 0;
	private int fps = 0;

	private ArrayList<String> debugInfo = new ArrayList<String>();

	public DebugInfo() {
		font = new BitmapFont();
		batch = new SpriteBatch();
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	public void resize(int screenWidth, int screenHeight) {
		cam = new OrthographicCamera(screenWidth, screenHeight);
		cam.translate(screenWidth / 2, screenHeight / 2);
		cam.update();
		batch.setProjectionMatrix(cam.combined);
	}

	public void update() {
		long time = System.nanoTime();

		if (time - frameStart >= 1000000000) {
			fps = frames;
			frames = 0;
			frameStart = time;
			prepareDebugInfo();
		}

		frames++;
	}

	private void prepareDebugInfo() {
		debugInfo.clear();
		if (isShowFPS()) {
			debugInfo.add(fps + " fps");
		}

		if (isShowMemoryInfo()) {
			debugInfo.add("Memory info:");

			JTranscSystem.gc();
			debugInfo.add("used: " + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) >> 20) + " MB");
			debugInfo.add("free: " + (Runtime.getRuntime().freeMemory() >> 20) + " MB");
			debugInfo.add("total: " + (Runtime.getRuntime().totalMemory() >> 20) + " MB");
		}
	}

	@HaxeMethodBody("cpp.vm.Gc.run(true);")
	private void gc() {
		System.gc();
	}

	public void render() {
		batch.begin();
		int x = 3;
		int y = Gdx.graphics.getHeight() - 3;
		int offset = (int) font.getLineHeight();
		for (String info : debugInfo) {
			font.draw(batch, info, x, y);
			y -= offset;
		}
		batch.end();
	}

	@HaxeMethodBody("return cpp.vm.Gc.memInfo(p0);")
	public int memInfo(int type) {
		return 0;
	}

	@HaxeMethodBody("{% if extra.showFPS %}return {{ extra.showFPS }};{% else %}return false;{% end %}")
	private static boolean isShowFPS() {
		return false;
	}

	@HaxeMethodBody("{% if extra.showMemInfo %}return {{ extra.showMemInfo }};{% else %}return false;{% end %}")
	private static boolean isShowMemoryInfo() {
		return false;
	}

	public static boolean isShowDebugInfo() {
		return isShowFPS() || isShowMemoryInfo();
	}

	public void dispose() {
		font.dispose();
		batch.dispose();
	}
}