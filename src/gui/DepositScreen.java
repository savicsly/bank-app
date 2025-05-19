package src.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDateTime; // Import LocalDateTime for timestamps

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import src.core.InputValidator; // Import the InputValidator class
import src.data.AuditLogger; // Import the AuditLogger class
import src.data.DataManager; // Import the DataManager class

public class DepositScreen extends JFrame {

    public DepositScreen(String accountNumber) {
        setTitle("Deposit Funds");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Deposit Funds", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(3, 1, 8, 8));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField();
        amountField.setPreferredSize(new Dimension(180, 28));

        Dimension buttonSize = new Dimension(120, 28);
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 8, 0));
        JButton depositButton = new JButton("Deposit");
        depositButton.setPreferredSize(buttonSize);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(buttonSize);
        buttonPanel.add(depositButton);
        buttonPanel.add(cancelButton);

        formPanel.add(amountLabel);
        formPanel.add(amountField);
        formPanel.add(buttonPanel);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(formPanel, BorderLayout.CENTER);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        add(centerPanel, BorderLayout.CENTER);

        // Add action listeners
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountText = amountField.getText();
                if (!InputValidator.isValidAmount(amountText)) {
                    JOptionPane.showMessageDialog(null, "Invalid amount format! Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (accountNumber == null || accountNumber.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(DepositScreen.this, "Account number is missing. Please log in again.", "Account Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    double amount = Double.parseDouble(amountText);
                    if (amount > 0) {
                        if (amount > 10000) {
                            int confirm = JOptionPane.showConfirmDialog(DepositScreen.this, "This is a large deposit. Are you sure you want to proceed?", "Authorization Required", JOptionPane.YES_NO_OPTION);
                            if (confirm != JOptionPane.YES_OPTION) {
                                return;
                            }
                        }
                        String timestamp = LocalDateTime.now().toString();
                        try {
                            // Debug: Print account number and amount
                            System.out.println("[DEBUG] Deposit to account: " + accountNumber + ", amount: " + amount);
                            DataManager.saveTransaction("Deposit", accountNumber, amount, timestamp);
                            DataManager.updateBalance(accountNumber, amount); // Add the deposited amount to the balance
                        } catch (src.core.InvalidAccountException ex) {
                            JOptionPane.showMessageDialog(DepositScreen.this, ex.getMessage(), "Account Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        } catch (IOException ex) {
                            ex.printStackTrace(); // Print stack trace for debugging
                            JOptionPane.showMessageDialog(DepositScreen.this, "I/O Error: " + ex.getMessage() + "\nCheck that the account exists and files are accessible.", "File Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        AuditLogger.log("Deposit", "Account: " + accountNumber + ", Amount: " + amount);
                        // NotificationManager.sendNotification("Deposit of $" + amount + " to account " + accountNumber + " was successful.");
                        JOptionPane.showMessageDialog(null, "Successfully deposited ₦" + amount + " to account " + accountNumber + ".");
                    } else {
                        JOptionPane.showMessageDialog(null, "Amount must be greater than zero!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid amount entered!", "Error", JOptionPane.ERROR_MESSAGE);
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

    public static void main(String[] args) {
        // Example usage for testing
        new DepositScreen("2054094495");
    }
}
