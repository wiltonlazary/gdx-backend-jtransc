package com.jtransc.media.limelibgdx;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;

public class LimeApplicationAwtUtils {
	static private Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

	// Called reflectively on java to avoid including dependencies
	@SuppressWarnings("unused")
	static public String getClipboardContents() throws Throwable {
		return (String) clipboard.getData(DataFlavor.stringFlavor);
	}

	// Called reflectively on java to avoid including dependencies
	@SuppressWarnings("unused")
	static public void setClipboardContents(String content) throws Throwable {
		StringSelection selection = new StringSelection(content);
		clipboard.setContents(selection, selection);
	}
}
