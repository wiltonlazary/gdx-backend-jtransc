package com.jtransc.media.limelibgdx;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Clipboard;
import com.jtransc.JTranscSystem;
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
public class LimeApplication extends GdxApplicationAdapter implements Application {
	final private LimeGraphics graphics;
	final private LimeInput input;
	final private LimeFiles files;
	final private LimeNet net;
	private ApplicationType type = ApplicationType.WebGL;

	public LimeApplication(ApplicationListener applicationListener, String title, int width, int height) {
		this(applicationListener, title, width, height, false);
	}

	public LimeApplication(ApplicationListener applicationListener, String title, int width, int height, boolean trace) {
		super(applicationListener);

		Gdx.graphics = graphics = new LimeGraphics(trace);
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
	private void setApplicationToLime(LimeApplication app) {
	}


	@Override
	public Graphics getGraphics() {
		return graphics;
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
		return new GdxPreferencesAdapter(name, false) {
			@Override
			protected Map<String, Object> load(String name) {
				return new HashMap<>();
			}

			@Override
			protected void store(String name, Map<String, Object> prefs) {
			}
		};
	}

	@Override
	protected Clipboard createClipboard() {
		if (JTranscSystem.usingJTransc()) {
			return new Clipboard() {
				private String content = "";

				@Override
				@HaxeMethodBody("return HaxeNatives.str(lime.system.Clipboard.text);")
				native public String getContents();

				@Override
				@HaxeMethodBody("lime.system.Clipboard.text = p0._str;")
				native public void setContents(String content);
			};
		} else {
			try {
				return (com.badlogic.gdx.utils.Clipboard) Class.forName("com.jtransc.media.limelibgdx.LimeApplicationAwtUtils$AwtClipboardAdaptor").newInstance();
			}catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}
	}

	@Override
	protected Audio createAudio() {
		return new LimeAudio();
	}

	@SuppressWarnings("unused")
	public void create() {
		super.create();
		resized(HaxeLimeGdxApplication.instance.getWidth(), HaxeLimeGdxApplication.instance.getHeight());
	}

	@SuppressWarnings("unused")
	public void render() {
		super.render();
		LimeInput.lime_frame();
		graphics.frame();
	}

	@SuppressWarnings("unused")
	public void resized(int width, int height) {
		graphics.width = width;
		graphics.height = height;
		super.resized(width, height);
	}

	@JTranscNativeClass("HaxeLimeGdxApplication")
	static public class HaxeLimeGdxApplication {
		static public HaxeLimeGdxApplication instance;

		native public int getWidth();

		native public int getHeight();
	}

	@Override
	public void onResumed() {
		super.onResumed();
	}

	@Override
	public void onPaused() {
		super.onPaused();
	}

	@Override
	public void onDisposed() {
		super.onDisposed();
	}
}
