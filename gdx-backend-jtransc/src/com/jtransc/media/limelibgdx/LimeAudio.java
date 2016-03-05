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
		return null;
	}

	@Override
	public AudioRecorder newAudioRecorder(int i, boolean b) {
		return null;
	}

	@Override
	public Sound newSound(FileHandle fileHandle) {
		return null;
	}

	@Override
	public Music newMusic(FileHandle fileHandle) {
		return null;
	}
}
