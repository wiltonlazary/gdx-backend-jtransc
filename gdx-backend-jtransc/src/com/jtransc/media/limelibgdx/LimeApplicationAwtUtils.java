package com.jtransc.media.limelibgdx;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;

public class LimeApplicationAwtUtils {
	static public class AwtClipboardAdaptor implements com.badlogic.gdx.utils.Clipboard {
		static private Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

		@Override
		public String getContents() {
			try {
				return (String) clipboard.getData(DataFlavor.stringFlavor);
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		@Override
		public void setContents(String content) {
			StringSelection selection = new StringSelection(content);
			clipboard.setContents(selection, selection);
		}
	}
}
