package com.jtransc.media.limelibgdx.util;

public class ImageElement {
    private int width;
    private int height;

    public ImageElement(int width, int height) {
        this.width = width;
        this.height = height;
    }

    native public int getWidth();

    native public int getHeight();
}
