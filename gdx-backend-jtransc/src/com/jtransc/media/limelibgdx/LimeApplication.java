package com.jtransc.media.limelibgdx;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Clipboard;
import com.jtransc.media.limelibgdx.util.GlUtils;
import jtransc.annotation.haxe.HaxeAddFiles;
import jtransc.annotation.haxe.HaxeAddLibraries;
import jtransc.annotation.haxe.HaxeCustomMain;
import jtransc.annotation.haxe.HaxeMethodBody;

import java.io.File;
import java.nio.Buffer;
import java.nio.ByteBuffer;

@HaxeAddFiles({
		"HaxeLimeGdxApplication.hx"
		//"AGALMiniAssembler.hx",
		//"HaxeLimeAudio.hx",
		//"HaxeLimeJTranscApplication.hx",
		//"HaxeLimeRender.hx",
		//"HaxeLimeRenderFlash.hx",
		//"HaxeLimeRenderGL.hx",
		//"HaxeLimeRenderImpl.hx",
		//"HaxeLimeIO.hx"
})
@HaxeCustomMain("" +
		"package $entryPointPackage;\n" +
		"class $entryPointSimpleName extends HaxeLimeGdxApplication {\n" +
		"    public function new() {\n" +
		"        super();\n" +
		"        $inits\n" +
		"        $mainClass.$mainMethod(HaxeNatives.strArray(HaxeNatives.args()));\n" +
		"    }\n" +
		"}\n"
)
@HaxeAddLibraries({"lime:2.9.0"})
public class LimeApplication implements Application {
	final private ApplicationListener applicationListener;
	final private Graphics graphics = new LimeGraphics();
	final private Audio audio = new LimeAudio();
	final private Input input = new LimeInput();
	final private Files files = new LimeFiles();
	final private Net net = new LimeNet();
	private int logLevel = LOG_DEBUG;

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

		setApplicationListenerToLime(applicationListener);
	}

	@HaxeMethodBody("HaxeLimeGdxApplication.listener = p0;")
	native private void setApplicationListenerToLime(ApplicationListener applicationListener);

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

	private void _log(int level, String tag, String message, Throwable exception) {
		if (level <= this.logLevel) {
			System.out.println("[" + tag + "] " + message + " : " + exception);
		}
	}

	@Override
	public void log(String tag, String message) {
		_log(LOG_INFO, tag, message, null);
	}

	@Override
	public void log(String tag, String message, Throwable exception) {
		_log(LOG_INFO, tag, message, exception);
	}

	@Override
	public void error(String tag, String message) {
		_log(LOG_ERROR, tag, message, null);
	}

	@Override
	public void error(String tag, String message, Throwable exception) {
		_log(LOG_ERROR, tag, message, exception);
	}

	@Override
	public void debug(String tag, String message) {
		_log(LOG_DEBUG, tag, message, null);
	}

	@Override
	public void debug(String tag, String message, Throwable exception) {
		_log(LOG_DEBUG, tag, message, exception);
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
		System.err.println("ERROR! postRunnable: " + runnable);
	}

	@Override
	public void exit() {
		System.exit(0);
	}

	@Override
	public void addLifecycleListener(LifecycleListener listener) {
		System.err.println("ERROR! addLifecycleListener: " + listener);
	}

	@Override
	public void removeLifecycleListener(LifecycleListener listener) {
		System.err.println("ERROR! removeLifecycleListener: " + listener);
	}

	public void loadTexture(int target, int textureId, File file) {
		setTexture(target, textureId, ByteBuffer.wrap(new byte[] { 0, 0, 0, 0 }), 1, 1);
		_loadTexture(this, target, textureId, file.getPath());
	}

	@HaxeMethodBody("HaxeLimeGdxApplication.loadTexture(p0, p1, p2, p3._str);")
	native private void _loadTexture(LimeApplication app, int target, int textureId, String filePath);

	public void setTexture(int target, int textureId, Buffer buffer, int width, int height) {
		int oldTextureBinding = GlUtils.getInteger(GL20.GL_TEXTURE_BINDING_2D);
		Gdx.gl.glBindTexture(target, textureId);
		Gdx.gl.glTexImage2D(target, 0, GL20.GL_RGBA, width, height, 0, GL20.GL_RGBA, GL20.GL_UNSIGNED_BYTE, buffer);
		Gdx.gl.glBindTexture(target, oldTextureBinding);
	}
}
