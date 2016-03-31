package com.jtransc.media.limelibgdx.util;

public class Context2d {
    native public void setFillStyle(String color);

    native public void setStrokeStyle(String color);

    native public void clearRect(int x, int y, int width, int height);

    native public ImageData getImageData(int x, int y, int width, int height);

    native public void setGlobalCompositeOperation(Composite s);

    native public void setGlobalCompositeOperation(String s);

    native public void beginPath();

    native public void arc(int x, int y, int radius, int i, double v, boolean b);

    native public void closePath();

    native public void moveTo(int x, int y);

    native public void lineTo(int x2, int y2);

    native public void rect(int x, int y, int width, int height);

    native public void drawImage(CanvasElement image, int srcX, int srcY, int srcWidth, int srcHeight, int dstX, int dstY, int dstWidth, int dstHeight);

    native public void fill();

    native public void stroke();

    native public void drawImage(ImageElement img, int x, int y);
}
