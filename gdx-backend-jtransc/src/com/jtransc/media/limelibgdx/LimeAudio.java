package com.jtransc.media.limelibgdx;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.audio.AudioRecorder;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

public class LimeAudio implements Audio {
	@Override
	public AudioDevice newAudioDevice(int i, boolean b) {
		return new AudioDevice() {
			@Override
			public boolean isMono() {
				return false;
			}

			@Override
			public void writeSamples(short[] samples, int offset, int numSamples) {

			}

			@Override
			public void writeSamples(float[] samples, int offset, int numSamples) {

			}

			@Override
			public int getLatency() {
				return 0;
			}

			@Override
			public void dispose() {

			}

			@Override
			public void setVolume(float volume) {

			}
		};
	}

	@Override
	public AudioRecorder newAudioRecorder(int i, boolean b) {
		return new AudioRecorder() {
			@Override
			public void read(short[] samples, int offset, int numSamples) {

			}

			@Override
			public void dispose() {

			}
		};
	}

	@Override
	public Sound newSound(final FileHandle fileHandle) {
		return new Sound() {
			@Override
			public long play() {
				return 0;
			}

			@Override
			public long play(float volume) {
				return 0;
			}

			@Override
			public long play(float volume, float pitch, float pan) {
				return 0;
			}

			@Override
			public long loop() {
				return 0;
			}

			@Override
			public long loop(float volume) {
				return 0;
			}

			@Override
			public long loop(float volume, float pitch, float pan) {
				return 0;
			}

			@Override
			public void stop() {

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

			@Override
			public void stop(long soundId) {

			}

			@Override
			public void pause(long soundId) {

			}

			@Override
			public void resume(long soundId) {

			}

			@Override
			public void setLooping(long soundId, boolean looping) {

			}

			@Override
			public void setPitch(long soundId, float pitch) {

			}

			@Override
			public void setVolume(long soundId, float volume) {

			}

			@Override
			public void setPan(long soundId, float pan, float volume) {

			}
		};
	}

	@Override
	public Music newMusic(final FileHandle fileHandle) {
		return new Music() {

			@Override
			public void play() {
			}

			@Override
			public void pause() {

			}

			@Override
			public void stop() {

			}

			@Override
			public boolean isPlaying() {
				return false;
			}

			@Override
			public void setLooping(boolean isLooping) {

			}

			@Override
			public boolean isLooping() {
				return false;
			}

			@Override
			public void setVolume(float volume) {

			}

			@Override
			public float getVolume() {
				return 0;
			}

			@Override
			public void setPan(float pan, float volume) {

			}

			@Override
			public void setPosition(float position) {

			}

			@Override
			public float getPosition() {
				return 0;
			}

			@Override
			public void dispose() {

			}

			@Override
			public void setOnCompletionListener(OnCompletionListener listener) {

			}
		};
	}
}
