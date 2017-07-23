package com.jtransc.media.limelibgdx;

import com.badlogic.gdx.audio.Sound;
import com.jtransc.annotation.haxe.HaxeAddMembers;
import com.jtransc.annotation.haxe.HaxeMethodBody;
import com.jtransc.annotation.haxe.HaxeImports;

@HaxeImports(
	"@:access(lime._backend.native.NativeAudioSource)" +
		"@:access(lime.media.AudioSource)"
)

@HaxeAddMembers({"var track:lime.media.AudioSource;"})
class LimeSound implements Sound {
	@HaxeMethodBody(
		"track = new lime.media.AudioSource(lime.media.AudioBuffer.fromFile(N.i_str(p0))); " +
			"track.onComplete.add(function():Void { " +
			"track.backend.completed = false; " +
			"track.currentTime = 0; " +
			"this.{% METHOD com.jtransc.media.limelibgdx.LimeSound:onComplete %}();" +
			"});"
	)
	private native void init0(String path);

	void init(String path) {
		init0(path);
	}

	@SuppressWarnings("unused")
	void onComplete() {
	}

	@Override
	public long play() {
		return play(false, -1.0f);
	}

	@Override
	public long play(float volume) {
		return play(false, volume);
	}

	@Override
	public long play(float volume, float pitch, float pan) {
		return play(false, volume);
	}

	@Override
	public long loop() {
		return play(true, -1.0f);
	}

	@Override
	public long loop(float volume) {
		return play(true, volume);
	}

	@Override
	public long loop(float volume, float pitch, float pan) {
		return play(true, volume);
	}

	@HaxeMethodBody("track.loops = p0 ? 100000 : 0; " +
		"var vol = p1 < 0 ? track.gain : p1 > 1.0 ? 1.0 : p1; track.gain = vol;" +
		"track.play(); return 0;")
	private native long play(boolean loop, float volume);

	@Override
	@HaxeMethodBody("track.stop();")
	public native void stop();

	@Override
	@HaxeMethodBody("track.pause();")
	public native void pause();

	@Override
	public void resume() {
		play();
	}

	@Override
	@HaxeMethodBody("track.dispose();")
	public native void dispose();

	@Override
	public void stop(long soundId) {
		stop();
	}

	@Override
	public void pause(long soundId) {
		pause();
	}

	@Override
	public void resume(long soundId) {
		resume();
	}

	@Override
	@HaxeMethodBody("track.loops = p1 ? 100000 : 0;")
	public native void setLooping(long soundId, boolean looping);

	@Override
	public void setPitch(long soundId, float pitch) {
	}

	@Override
	@HaxeMethodBody("var vol = p1 < 0 ? 0 : p1 > 1.0 ? 1.0 : p1; track.gain = vol;")
	public void setVolume(long soundId, float volume) {
	}

	@Override
	public void setPan(long soundId, float pan, float volume) {

	}
}