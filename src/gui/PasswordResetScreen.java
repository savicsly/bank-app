package src.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import src.data.DataManager;
import src.data.PasswordManager;

public class PasswordResetScreen extends JFrame {
    public PasswordResetScreen() {
        setTitle("Password Reset");
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Reset Password", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        JLabel accountLabel = new JLabel("Account Number:");
        JTextField accountField = new JTextField();
        JLabel newPassLabel = new JLabel("New Password:");
        JPasswordField newPassField = new JPasswordField();
        JButton resetButton = new JButton("Reset");
        JButton cancelButton = new JButton("Cancel");

        formPanel.add(accountLabel); formPanel.add(accountField);
        formPanel.add(newPassLabel); formPanel.add(newPassField);
        formPanel.add(resetButton); formPanel.add(cancelButton);
        add(formPanel, BorderLayout.CENTER);

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String acc = accountField.getText().trim();
                String newPass = new String(newPassField.getPassword());
                if (acc.isEmpty() || newPass.isEmpty()) {
                    JOptionPane.showMessageDialog(PasswordResetScreen.this, "All fields required.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    String hashed = PasswordManager.hashPassword(newPass);
                    if (DataManager.updatePassword(acc, hashed)) {
                        JOptionPane.showMessageDialog(PasswordResetScreen.this, "Password reset successful.");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(PasswordResetScreen.this, "Account not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (java.security.NoSuchAlgorithmException ex) {
                    JOptionPane.showMessageDialog(PasswordResetScreen.this, "Error hashing password.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(PasswordResetScreen.this, "Error updating password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
    }
}
