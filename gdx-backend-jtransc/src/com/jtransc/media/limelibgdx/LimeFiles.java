package com.jtransc.media.limelibgdx;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.files.FileHandle;

public class LimeFiles implements Files {
	@Override
	public FileHandle getFileHandle(String s, FileType fileType) {
		return null;
	}

	@Override
	public FileHandle classpath(String s) {
		return null;
	}

	@Override
	public FileHandle internal(String s) {
		return null;
	}

	@Override
	public FileHandle external(String s) {
		return null;
	}

	@Override
	public FileHandle absolute(String s) {
		return null;
	}

	@Override
	public FileHandle local(String s) {
		return null;
	}

	@Override
	public String getExternalStoragePath() {
		return null;
	}

	@Override
	public boolean isExternalStorageAvailable() {
		return false;
	}

	@Override
	public String getLocalStoragePath() {
		return null;
	}

	@Override
	public boolean isLocalStorageAvailable() {
		return false;
	}
}
