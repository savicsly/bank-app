package src.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class SessionManager {

    private static final int TIMEOUT_DURATION = 5 * 60 * 1000; // 5 minutes in milliseconds
    private Timer timer;
    private JFrame currentFrame;

    public SessionManager(JFrame frame) {
        this.currentFrame = frame;
        startSessionTimer();
    }

    private void startSessionTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(currentFrame, "Session timed out due to inactivity.", "Session Timeout", JOptionPane.WARNING_MESSAGE);
                    currentFrame.dispose();
                    new LoginScreen(); // Redirect to login screen
                });
            }
        }, TIMEOUT_DURATION);
    }

    public void resetTimer() {
        timer.cancel();
        startSessionTimer();
    }
}
