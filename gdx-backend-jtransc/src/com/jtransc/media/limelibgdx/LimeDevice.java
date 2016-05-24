package com.jtransc.media.limelibgdx;

import com.jtransc.JTranscSystem;
import com.jtransc.annotation.haxe.HaxeMethodBody;

public class LimeDevice {
	@HaxeMethodBody(target = "android", value = "return true;")
	@HaxeMethodBody("return false;")
	native static public boolean isAndroid();

	@HaxeMethodBody(target = "android", value = "return true;")
	@HaxeMethodBody("return false;")
	native static public boolean isIos();

	static public boolean isJs() {
		return JTranscSystem.isJs();
	}

	static public boolean isFlash() {
		return JTranscSystem.isSwf();
	}
}
