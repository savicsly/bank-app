package src.gui;

import javax.swing.*;
import java.awt.*;

public class CustomUI {

    public static void applyCustomStyling(JFrame frame) {
        frame.getContentPane().setBackground(new Color(240, 248, 255)); // Light blue background
        UIManager.put("Button.background", new Color(173, 216, 230)); // Light blue buttons
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 14));
        UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 14));
        UIManager.put("Table.font", new Font("Arial", Font.PLAIN, 12));
        UIManager.put("Table.background", Color.WHITE);
        UIManager.put("Table.gridColor", Color.LIGHT_GRAY);
    }
}
