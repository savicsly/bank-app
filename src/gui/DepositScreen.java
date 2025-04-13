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

public class DepositScreen extends JFrame {

    public DepositScreen() {
        setTitle("Sunflower Banking - Deposit");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create components
        JLabel titleLabel = new JLabel("Deposit Funds", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel accountLabel = new JLabel("Account Type:");
        JComboBox<String> accountComboBox = new JComboBox<>(new String[]{"Savings", "Current"});
        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField();
        JButton depositButton = new JButton("Deposit");
        JButton cancelButton = new JButton("Cancel");

        // Add components to form panel
        formPanel.add(accountLabel);
        formPanel.add(accountComboBox);
        formPanel.add(amountLabel);
        formPanel.add(amountField);
        formPanel.add(depositButton);
        formPanel.add(cancelButton);

        // Add components to frame
        add(titleLabel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);

        // Add action listeners
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountType = (String) accountComboBox.getSelectedItem();
                String amountText = amountField.getText();
                if (!InputValidator.isValidAmount(amountText)) {
                    JOptionPane.showMessageDialog(null, "Invalid amount format! Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    double amount = Double.parseDouble(amountText);
                    if (amount > 0) {
                        String timestamp = LocalDateTime.now().toString();
                        DataManager.saveTransaction("Deposit", accountType, amount, timestamp);
                        AuditLogger.log("Deposit", "Account: " + accountType + ", Amount: " + amount); // Log the deposit transaction
                        DataManager.updateBalance(accountType, amount); // Add the deposited amount to the balance
                        NotificationManager.sendNotification("Deposit of $" + amount + " to " + accountType + " account was successful.");
                        JOptionPane.showMessageDialog(null, "Successfully deposited ₦" + amount + " to " + accountType + " account.");
                        // Placeholder for actual deposit logic
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
        new DepositScreen();
    }
}
