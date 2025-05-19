package src.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ViewProfileScreen extends JFrame {

    public ViewProfileScreen(String accountNumber, String userName) {
        setTitle("View Profile");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("User Profile", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        JPanel profilePanel = new JPanel(new GridLayout(0, 2, 10, 10));
        profilePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        try (BufferedReader reader = new BufferedReader(new FileReader("accounts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[2].equals(accountNumber)) {
                    profilePanel.add(new JLabel("Name:"));
                    profilePanel.add(new JLabel(parts[1]));

                    profilePanel.add(new JLabel("Account Number:"));
                    profilePanel.add(new JLabel(parts[2]));

                    profilePanel.add(new JLabel("Balance:"));
                    profilePanel.add(new JLabel(parts[3]));

                    profilePanel.add(new JLabel("Email:"));
                    profilePanel.add(new JLabel(parts[7]));

                    profilePanel.add(new JLabel("Phone Number:"));
                    profilePanel.add(new JLabel(parts[8]));

                    break;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading profile details!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        add(profilePanel, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        new ViewProfileScreen("1234567890", "John Doe"); // Example usage
    }
}
