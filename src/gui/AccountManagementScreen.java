package src.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random; // Import Random for account number generation

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AccountManagementScreen extends JFrame {

    public AccountManagementScreen() {
        setTitle("Sunflower Banking - Account Management");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create components
        JLabel titleLabel = new JLabel("Account Management", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        JLabel accountTypeLabel = new JLabel("Account Type:");
        JComboBox<String> accountTypeComboBox = new JComboBox<>(new String[]{"Savings", "Current"});
        JLabel accountHolderLabel = new JLabel("Account Holder Name:");
        JTextField accountHolderField = new JTextField();
        JLabel initialDepositLabel = new JLabel("Initial Deposit:");
        JTextField initialDepositField = new JTextField();
        JButton createAccountButton = new JButton("Create Account");
        JButton cancelButton = new JButton("Cancel");

        // Add components to form panel
        formPanel.add(accountTypeLabel);
        formPanel.add(accountTypeComboBox);
        formPanel.add(accountHolderLabel);
        formPanel.add(accountHolderField);
        formPanel.add(initialDepositLabel);
        formPanel.add(initialDepositField);
        formPanel.add(createAccountButton);
        formPanel.add(cancelButton);

        // Add components to frame
        add(titleLabel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);

        // Add action listeners
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountType = (String) accountTypeComboBox.getSelectedItem();
                String accountHolder = accountHolderField.getText();
                double initialDeposit = 5000.0; // Set initial deposit to ₦5000

                // Generate a 10-digit account number
                String accountNumber = String.format("%010d", new Random().nextInt(1_000_000_000));

                saveAccountToFile(accountType, accountHolder, accountNumber, initialDeposit);
                JOptionPane.showMessageDialog(null, "Account created successfully!\nAccount Number: " + accountNumber);
            }
        });

        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void saveAccountToFile(String accountType, String accountHolder, String accountNumber, double initialDeposit) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("accounts.txt", true))) {
            writer.write(accountType + "," + accountHolder + "," + accountNumber + "," + initialDeposit);
            writer.newLine();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error saving account to file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new AccountManagementScreen();
    }
}
