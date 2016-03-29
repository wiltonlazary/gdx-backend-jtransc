package com.badlogic.gdx.graphics;

import com.badlogic.gdx.files.FileHandle;

public interface TextureData {
	public enum TextureDataType {
		Pixmap, Custom
	}

	public TextureDataType getType();

	public boolean isPrepared();

	public void prepare();

	public Pixmap consumePixmap();

	public boolean disposePixmap();

	public void consumeCustomData(int target);

	public int getWidth();

	public int getHeight();

	public Pixmap.Format getFormat();

	public boolean useMipMaps();

	public boolean isManaged();

	public static class Factory {
		public static TextureData loadFromFile(FileHandle file, boolean useMipMaps) {
			return loadFromFile(file, null, useMipMaps);
		}

		public static TextureData loadFromFile(FileHandle file, Pixmap.Format format, boolean useMipMaps) {
			//throw new RuntimeException("Not implemented TextureData.Factory");
			return new TextureData() {
				private boolean prepared = false;

				@Override
				public TextureDataType getType() {
					return TextureDataType.Custom;
				}

				@Override
				public boolean isPrepared() {
					return prepared;
				}

				@Override
				public void prepare() {
					prepared = true;
				}

				@Override
				public Pixmap consumePixmap() {
					throw new RuntimeException("Not providing a pixmap.");
				}

				@Override
				public boolean disposePixmap() {
					return true;
				}

				@Override
				public void consumeCustomData(int target) {
					System.out.println("consumeCustomData: " + file);
				}

				@Override
				public int getWidth() {
					return 512;
				}

				@Override
				public int getHeight() {
					return 512;
				}

				@Override
				public Pixmap.Format getFormat() {
					return Pixmap.Format.RGBA8888;
				}

				@Override
				public boolean useMipMaps() {
					return false;
				}

				@Override
				public boolean isManaged() {
					return true;
				}
			};
		}
	}
}
