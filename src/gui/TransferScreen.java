package src.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDateTime; // Import LocalDateTime for timestamps

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import src.core.InputValidator; // Import the InputValidator class
import src.core.NotificationManager; // Import the NotificationManager class
import src.data.AuditLogger; // Import the AuditLogger class
import src.data.DataManager; // Import the DataManager class

public class TransferScreen extends JFrame {

    public TransferScreen() {
        setTitle("Sunflower Banking - Transfer");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create components
        JLabel titleLabel = new JLabel("Transfer Funds", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        JLabel fromAccountLabel = new JLabel("From Account:");
        JComboBox<String> fromAccountComboBox = new JComboBox<>(new String[]{"Savings", "Current"});
        JLabel toAccountLabel = new JLabel("To Account:");
        JTextField toAccountField = new JTextField();
        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField();
        JButton transferButton = new JButton("Transfer");
        JButton cancelButton = new JButton("Cancel");

        // Add components to form panel
        formPanel.add(fromAccountLabel);
        formPanel.add(fromAccountComboBox);
        formPanel.add(toAccountLabel);
        formPanel.add(toAccountField);
        formPanel.add(amountLabel);
        formPanel.add(amountField);
        formPanel.add(transferButton);
        formPanel.add(cancelButton);

        // Add components to frame
        add(titleLabel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);

        // Add action listeners
        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fromAccount = (String) fromAccountComboBox.getSelectedItem();
                String toAccount = toAccountField.getText();
                String amountText = amountField.getText();
                if (!InputValidator.isValidAmount(amountText)) {
                    JOptionPane.showMessageDialog(null, "Invalid amount format! Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    double amount = Double.parseDouble(amountText);
                    if (amount > 0) {
                        String timestamp = LocalDateTime.now().toString();
                        DataManager.saveTransaction("Transfer", fromAccount, amount, timestamp);
                        AuditLogger.log("Transfer", "From: " + fromAccount + ", To: " + toAccount + ", Amount: " + amount); // Log the transfer transaction
                        NotificationManager.sendNotification("Transfer of $" + amount + " from " + fromAccount + " to " + toAccount + " was successful.");
                        // Update the success message to include Naira (₦)
                        JOptionPane.showMessageDialog(null, "Successfully transferred ₦" + amount + " from " + fromAccount + " to account " + toAccount + ".");
                        // Update the balance after a transfer
                        DataManager.updateBalance(fromAccount, -amount); // Subtract the transferred amount from the sender's balance
                        DataManager.updateBalance(toAccount, amount); // Add the transferred amount to the recipient's balance
                        // Placeholder for actual transfer logic
                    } else {
                        JOptionPane.showMessageDialog(null, "Amount must be greater than zero!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error saving transaction to file!", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid amount entered!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    public static void main(String[] args) {
        new TransferScreen();
    }
}
