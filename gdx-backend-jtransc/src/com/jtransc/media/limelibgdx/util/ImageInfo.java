package com.jtransc.media.limelibgdx.util;

import com.jtransc.mem.BytesRead;

public class ImageInfo {
	static public class Size {
		public final int width;
		public final int height;

		Size(int width, int height) {
			this.width = width;
			this.height = height;
		}
	}

	static public Size detect(byte[] data, int offset, int len) {
		if (BytesRead.s32b(data, offset) == 0x89504E47) { // .PNG
			for (int n = 0; n < len; n++) {
				if (BytesRead.s32b(data, offset + n) == 0x49484452) { // IHDR
					int width = BytesRead.s32b(data, offset + n + 4);
					int height = BytesRead.s32b(data, offset + n + 8);
					return new Size(width, height);
				}
			}
		}

		if (BytesRead.s16b(data, offset) == 0xFFD8) { // SOI_MARKER (JPEG)
			int n = 2;
			while (n < data.length) {
				int type = BytesRead.s16b(data, offset + n + 0);
				int length = BytesRead.s16b(data, offset + n + 2);
				switch (type) {
					case 0xFFC0: // M_SOF0
					case 0xFFC2: // M_SOF2
						BytesRead.s8b(data, offset + n + 4);
						int height = BytesRead.s16b(data, offset + n + 5);
						int width = BytesRead.s16b(data, offset + n + 7);
						return new Size(width, height);
				}
				n += length + 2;
			}
		}

		return null;
 	}
}
