package src.gui;

import javax.swing.JFrame;

public abstract class ScreenUtils {
    public static void setInitialScreenSize(JFrame frame) {
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.75);
        int height = (int) (screenSize.height * 0.75);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null); // Center the window
    }

    public static void setLoginScreenSize(JFrame frame) {
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.5);
        int height = (int) (screenSize.height * 0.5);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null); // Center the window
    }
}
