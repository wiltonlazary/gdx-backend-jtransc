package com.jtransc.media.limelibgdx;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.files.FileHandle;
import jtransc.JTranscWrapped;
import jtransc.annotation.haxe.HaxeMethodBody;
import jtransc.io.JTranscSyncIO;

public class LimeFiles implements Files {
	public LimeFiles() {
		JTranscSyncIO.impl = new JTranscSyncIOLimeImpl(JTranscSyncIO.impl);
	}

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

	static public String fixpath(String path) {
		if (!path.startsWith("assets")) path = "assets/" + path;
		path = path.replace("//", "/");
		return path;
	}

	static private class JTranscSyncIOLimeImpl extends JTranscSyncIO.Impl {
		public JTranscSyncIOLimeImpl(JTranscSyncIO.Impl parent) {
			super(parent);
		}

		@Override
		public JTranscSyncIO.ImplStream open(String path, int mode) {
			return new JTranscSyncIO.ByteStream(readBytes(fixpath(path), mode));
		}

		@HaxeMethodBody("return HaxeByteArray.fromBytes(lime.Assets.getBytes(p0._str));")
		private byte[] readBytes(String path, int mode) {
			return new byte[0];
		}
	}
}
