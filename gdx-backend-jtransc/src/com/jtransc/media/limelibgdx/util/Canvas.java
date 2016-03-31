package com.jtransc.media.limelibgdx.util;

public class Canvas {
    native public CanvasElement getCanvasElement();

    native public static Canvas createIfSupported();

    native public Context2d getContext2d();
}
