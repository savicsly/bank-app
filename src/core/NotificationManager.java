package src.core;

import javax.swing.*;

public class NotificationManager {

    public static void sendNotification(String message) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, message, "Notification", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}
