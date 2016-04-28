package com.jtransc.media.limelibgdx;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Clipboard;
import com.jtransc.media.limelibgdx.util.GlUtils;
import com.jtransc.JTranscSystem;
import com.jtransc.annotation.haxe.HaxeAddFiles;
import com.jtransc.annotation.haxe.HaxeAddLibraries;
import com.jtransc.annotation.haxe.HaxeCustomMain;
import com.jtransc.annotation.haxe.HaxeMethodBody;
import com.jtransc.io.JTranscSyncIO;
import com.jtransc.util.JTranscFiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

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
@HaxeAddLibraries({"lime:2.9.1"})
public class LimeApplication implements Application {
	final private ApplicationListener applicationListener;
	final private LimeGraphics graphics = new LimeGraphics();
	final private LimeAudio audio = new LimeAudio();
	final private LimeInput input = new LimeInput();
	final private LimeFiles files = new LimeFiles();
	final private LimeNet net = new LimeNet();
	private int logLevel = LOG_DEBUG;
	private ApplicationType type = ApplicationType.WebGL;

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

		if (JTranscSystem.isJs()) {
			type = ApplicationType.WebGL;
		} else {
			// @TODO: Check android and ios!
			type = ApplicationType.Desktop;
		}

		setApplicationToLime(this);
	}

	@HaxeMethodBody("HaxeLimeGdxApplication.app = p0;")
	native private void setApplicationToLime(LimeApplication app);

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
		return type;
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
		return new Preferences() {
			Map<String, Object> prefs = new HashMap<>();

			@Override
			public Preferences putBoolean(String key, boolean val) {
				prefs.put(key, val);
				return this;
			}

			@Override
			public Preferences putInteger(String key, int val) {
				prefs.put(key, val);
				return this;
			}

			@Override
			public Preferences putLong(String key, long val) {
				prefs.put(key, val);
				return this;
			}

			@Override
			public Preferences putFloat(String key, float val) {
				prefs.put(key, val);
				return this;
			}

			@Override
			public Preferences putString(String key, String val) {
				prefs.put(key, val);
				return this;
			}

			@Override
			public Preferences put(Map<String, ?> vals) {
				for (Map.Entry<String, ?> item : vals.entrySet()) {
					prefs.put(item.getKey(), item.getValue());
				}
				return this;
			}

			@Override
			public boolean getBoolean(String key) {
				return getBoolean(key, false);
			}

			@Override
			public int getInteger(String key) {
				return getInteger(key, 0);
			}

			@Override
			public long getLong(String key) {
				return getLong(key, 0L);
			}

			@Override
			public float getFloat(String key) {
				return getFloat(key, 0f);
			}

			@Override
			public String getString(String key) {
				return getString(key, "");
			}

			@Override
			public boolean getBoolean(String key, boolean defValue) {
				return prefs.containsKey(key) ? (boolean) prefs.get(key) : defValue;
			}

			@Override
			public int getInteger(String key, int defValue) {
				return prefs.containsKey(key) ? (int) prefs.get(key) : defValue;
			}

			@Override
			public long getLong(String key, long defValue) {
				return prefs.containsKey(key) ? (long) prefs.get(key) : defValue;
			}

			@Override
			public float getFloat(String key, float defValue) {
				return prefs.containsKey(key) ? (float) prefs.get(key) : defValue;
			}

			@Override
			public String getString(String key, String defValue) {
				return prefs.containsKey(key) ? (String) prefs.get(key) : defValue;
			}

			@Override
			public Map<String, ?> get() {
				return new HashMap<>(prefs);
			}

			@Override
			public boolean contains(String key) {
				return prefs.containsKey(key);
			}

			@Override
			public void clear() {
				prefs.clear();
			}

			@Override
			public void remove(String key) {
				prefs.remove(key);
			}

			@Override
			public void flush() {
				// @TODO: write!
			}
		};
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

	public void create() {
		applicationListener.create();
	}

	public void render() {
		applicationListener.render();
		LimeInput.lime_frame();
		graphics.frame();
	}

	public void resized(int width, int height) {
		graphics.width = width;
		graphics.height = height;
		Gdx.gl.glViewport(0, 0, width, height);
	}
}
