package src.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class AdminPanelScreen extends JFrame {
    public AdminPanelScreen() {
        setTitle("Admin Panel");
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Admin Panel", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        JButton restoreBackupButton = new JButton("Restore from Backup");
        JButton userMgmtButton = new JButton("User Management (View Only)");
        panel.add(restoreBackupButton);
        panel.add(userMgmtButton);
        add(panel, BorderLayout.CENTER);

        restoreBackupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser(new File("backups"));
                int result = chooser.showOpenDialog(AdminPanelScreen.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File backup = chooser.getSelectedFile();
                    try {
                        // For demo: just copy backup to current directory (manual unzip required)
                        Files.copy(backup.toPath(), new File("restored_backup.zip").toPath(), StandardCopyOption.REPLACE_EXISTING);
                        JOptionPane.showMessageDialog(AdminPanelScreen.this, "Backup copied. Please extract manually.");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(AdminPanelScreen.this, "Restore failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        userMgmtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // For demo: show a dialog with all account numbers
                try {
                    java.util.List<String> accounts = src.data.DataManager.readAccounts();
                    StringBuilder sb = new StringBuilder();
                    for (String acc : accounts) {
                        String[] parts = acc.split(",");
                        if (parts.length >= 3) sb.append(parts[2]).append("\n");
                    }
                    JTextArea area = new JTextArea(sb.toString());
                    area.setEditable(false);
                    JScrollPane scroll = new JScrollPane(area);
                    scroll.setPreferredSize(new Dimension(300, 200));
                    JOptionPane.showMessageDialog(AdminPanelScreen.this, scroll, "User Accounts", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AdminPanelScreen.this, "Error loading users: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        setVisible(true);
    }
}
