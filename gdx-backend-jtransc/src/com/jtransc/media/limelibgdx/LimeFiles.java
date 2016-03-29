package com.jtransc.media.limelibgdx;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.files.FileHandle;

public class LimeFiles implements Files {
	@Override
	public FileHandle getFileHandle(String s, FileType fileType) {
		return new MyFileHandle(s);
	}

	@Override
	public FileHandle classpath(String s) {
		return new MyFileHandle(s);
	}

	@Override
	public FileHandle internal(String s) {
		return new MyFileHandle(s);
	}

	@Override
	public FileHandle external(String s) {
		return new MyFileHandle(s);
	}

	@Override
	public FileHandle absolute(String s) {
		return new MyFileHandle(s);
	}

	@Override
	public FileHandle local(String s) {
		return new MyFileHandle(s);
	}

	@Override
	public String getExternalStoragePath() {
		return "/";
	}

	@Override
	public boolean isExternalStorageAvailable() {
		return false;
	}

	@Override
	public String getLocalStoragePath() {
		return "/";
	}

	@Override
	public boolean isLocalStorageAvailable() {
		return false;
	}

	static private class MyFileHandle extends FileHandle {
		public MyFileHandle(String s) {
			super(s);
			System.out.println("MyFileHandle: " + s);
		}
	}
}
