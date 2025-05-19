package src.core;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class NotificationManager {

    public static void sendNotification(String message) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, message, "Notification", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    public static void notifyUser(String message) {
        javax.swing.JOptionPane.showMessageDialog(null, message, "Notification", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    public static void notifyScheduledPaymentProcessed(String from, String to, double amount) {
        notifyUser("Scheduled payment of $" + amount + " from " + from + " to " + to + " has been processed.");
    }
}
