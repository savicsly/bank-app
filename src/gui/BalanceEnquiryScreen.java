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

public class BalanceEnquiryScreen extends JFrame {

    public BalanceEnquiryScreen(String accountNumber) {
        setTitle("Balance Enquiry");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Balance Enquiry", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        JPanel balancePanel = new JPanel(new GridLayout(2, 1, 10, 10));
        balancePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel accountLabel = new JLabel("Account Number: " + accountNumber);
        JLabel balanceLabel = new JLabel();

        try (BufferedReader reader = new BufferedReader(new FileReader("accounts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[2].equals(accountNumber)) {
                    balanceLabel.setText("Balance: ₦" + parts[3]);
                    break;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error retrieving balance!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        balancePanel.add(accountLabel);
        balancePanel.add(balanceLabel);
        add(balancePanel, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        new BalanceEnquiryScreen("1234567890"); // Example usage
    }
}
