package com.jtransc.media.limelibgdx;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Clipboard;
import com.jtransc.annotation.JTranscNativeClass;
import com.jtransc.annotation.haxe.*;

import java.util.HashMap;
import java.util.Map;

@HaxeAddFilesTemplate({
	"HaxeLimeGdxApplication.hx"
})
@HaxeAddFilesBeforeBuildTemplate({
	"program.xml"
})
@HaxeCustomMain("" +
	"package {{ entryPointPackage }};\n" +
	"class {{ entryPointSimpleName }} extends HaxeLimeGdxApplication {\n" +
	"    public function new() {\n" +
	"        super();\n" +
	"        {{ inits }}\n" +
	"        {{ mainClass }}.{{ mainMethod }}(HaxeNatives.strArray(HaxeNatives.args()));\n" +
	"    }\n" +
	"}\n"
)
@HaxeAddSubtargetList({
	@HaxeAddSubtarget(name = "android"),
	@HaxeAddSubtarget(name = "blackberry"),
	@HaxeAddSubtarget(name = "desktop"),
	@HaxeAddSubtarget(name = "emscripten"),
	@HaxeAddSubtarget(name = "flash", alias = {"swf", "as3"}),
	@HaxeAddSubtarget(name = "html5", alias = {"js"}),
	@HaxeAddSubtarget(name = "ios"),
	@HaxeAddSubtarget(name = "linux"),
	@HaxeAddSubtarget(name = "mac"),
	@HaxeAddSubtarget(name = "tizen"),
	@HaxeAddSubtarget(name = "tvos"),
	@HaxeAddSubtarget(name = "webos"),
	@HaxeAddSubtarget(name = "windows"),
	@HaxeAddSubtarget(name = "neko")
})
@HaxeCustomBuildCommandLine({
	"@limebuild.cmd"
})
@HaxeCustomBuildAndRunCommandLine({
	"@limetest.cmd"
})
@HaxeAddLibraries({
	"lime:2.9.1"
})
@HaxeAddAssets({
	"com/badlogic/gdx/utils/arial-15.fnt",
	"com/badlogic/gdx/utils/arial-15.png"
})
public class LimeApplication implements Application {
	final private ApplicationListener applicationListener;
	final private LimeGraphics graphics;
	final private LimeAudio audio;
	final private LimeInput input;
	final private LimeFiles files;
	final private LimeNet net;
	private int logLevel = LOG_DEBUG;
	private ApplicationType type = ApplicationType.WebGL;

	public LimeApplication(ApplicationListener applicationListener, String title, int width, int height) {
		this(applicationListener, title, width, height, false);
	}

	public LimeApplication(ApplicationListener applicationListener, String title, int width, int height, boolean trace) {
		this.applicationListener = applicationListener;

		Gdx.app = this;
		Gdx.graphics = graphics = new LimeGraphics(trace);
		Gdx.audio = audio = new LimeAudio();
		;
		Gdx.input = input = new LimeInput();
		Gdx.files = files = new LimeFiles();
		Gdx.net = net = new LimeNet();

		Gdx.gl = graphics.getGL20();
		Gdx.gl20 = graphics.getGL20();
		Gdx.gl30 = graphics.getGL30();

		if (LimeDevice.isJs()) {
			type = ApplicationType.WebGL;
		} else if (LimeDevice.isIos()) {
			type = ApplicationType.iOS;
		} else if (LimeDevice.isAndroid()) {
			type = ApplicationType.Android;
		} else {
			type = ApplicationType.Desktop;
		}

		setApplicationToLime(this);

		referenceClasses();
	}

	// @TODO: mark package to include!
	static private void referenceClasses() {
		//new com.badlogic.gdx.graphics.g2d.BitmapFont();
		new com.badlogic.gdx.graphics.Color(0xFFFFFFFF);
		new com.badlogic.gdx.scenes.scene2d.ui.Skin.TintedDrawable();
		new com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle();
		new com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle();
		new com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle();
		new com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle();
		new com.badlogic.gdx.scenes.scene2d.ui.SplitPane.SplitPaneStyle();
		new com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle();
		new com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle();
		new com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle();
		new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle();
		new com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle();
		new com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle();
		new com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle();
		new com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle();
		new com.badlogic.gdx.scenes.scene2d.ui.Tree.TreeStyle();
		new com.badlogic.gdx.scenes.scene2d.ui.TextTooltip.TextTooltipStyle();
		new GlyphLayout.GlyphRun();
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
		System.err.println("WARNING NOT IMPLEMENTED! addLifecycleListener: " + listener);
	}

	@Override
	public void removeLifecycleListener(LifecycleListener listener) {
		System.err.println("WARNING NOT IMPLEMENTED! removeLifecycleListener: " + listener);
	}

	@SuppressWarnings("unused")
	public void create() {
		applicationListener.create();
		resized(HaxeLimeGdxApplication.instance.getWidth(), HaxeLimeGdxApplication.instance.getHeight());
	}

	@SuppressWarnings("unused")
	public void render() {
		applicationListener.render();
		LimeInput.lime_frame();
		graphics.frame();
	}

	@SuppressWarnings("unused")
	public void resized(int width, int height) {
		graphics.width = width;
		graphics.height = height;
		Gdx.gl.glViewport(0, 0, width, height);
		applicationListener.resize(width, height);
	}

	@JTranscNativeClass("HaxeLimeGdxApplication")
	static public class HaxeLimeGdxApplication {
		static public HaxeLimeGdxApplication instance;

		native public int getWidth();

		native public int getHeight();
	}
}
