package ru.ylab.util;

import javax.swing.*;
import java.awt.*;

public class GraphicUtil {

    public static void setCenteredToScreen(JFrame parent, JFrame frame) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension size = frame.getSize();
        if (parent == null) {
            frame.setLocation(dim.width / 2 - size.width / 2, dim.height / 2 - size.height / 2);
        } else {
            Point locationOnScreen = parent.getLocationOnScreen();
            frame.setLocation(locationOnScreen.x + 20, locationOnScreen.y + 20);
        }
    }

    public static void setCenteredToScreen(JFrame frame) {
        setCenteredToScreen(null, frame);
    }

}