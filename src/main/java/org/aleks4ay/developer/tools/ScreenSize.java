package org.aleks4ay.developer.tools;

import java.awt.*;

public final class ScreenSize {
    private final static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    public static void main(String[] args) {
        System.out.println(ScreenSize.getScreenWidth());
        System.out.println(ScreenSize.getScreenHeight());
    }

    public static int getScreenWidth () {
        return gd.getDisplayMode().getWidth();
    }

    public static int getScreenHeight () {
        return gd.getDisplayMode().getHeight();
    }
}
