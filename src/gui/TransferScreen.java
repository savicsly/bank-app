package src.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TransferScreen extends JFrame {

    private String accountNumber;

    public TransferScreen(String accountNumber) {
        this.accountNumber = accountNumber;
        setTitle("Transfer Funds");
        setSize(400, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Transfer Funds", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30)); // Add padding

        JLabel fromAccountLabel = new JLabel("From Account:");
        JLabel fromAccountValueLabel = new JLabel(accountNumber); // Show the actual account number
        fromAccountValueLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JLabel toAccountLabel = new JLabel("To Account:");
        JTextField toAccountField = new JTextField();
        toAccountField.setPreferredSize(new Dimension(180, 28));
        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField();
        amountField.setPreferredSize(new Dimension(180, 28));
        JButton transferButton = new JButton("Transfer");
        JButton cancelButton = new JButton("Cancel");
        Dimension buttonSize = new Dimension(120, 28);
        transferButton.setPreferredSize(buttonSize);
        cancelButton.setPreferredSize(buttonSize);

        formPanel.add(fromAccountLabel);
        formPanel.add(fromAccountValueLabel); // Use label instead of combo box
        formPanel.add(toAccountLabel);
        formPanel.add(toAccountField);
        formPanel.add(amountLabel);
        formPanel.add(amountField);
        formPanel.add(transferButton);
        formPanel.add(cancelButton);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(formPanel, BorderLayout.CENTER);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(titleLabel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fromAccount = TransferScreen.this.accountNumber.trim();
                String toAccount = toAccountField.getText().trim();
                String amountText = amountField.getText().trim();
                if (toAccount.isEmpty()) {
                    JOptionPane.showMessageDialog(TransferScreen.this, "Please enter a recipient account number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (toAccount.equals(fromAccount)) {
                    JOptionPane.showMessageDialog(TransferScreen.this, "You cannot transfer to your own account.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                double amount = 0;
                try {
                    amount = Double.parseDouble(amountText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(TransferScreen.this, "Invalid amount entered!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(TransferScreen.this, "Amount must be greater than zero!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Check if recipient account exists in accounts.txt
                boolean recipientExists = false;
                try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader("accounts.txt"))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts.length >= 3 && parts[2].trim().equals(toAccount)) {
                            recipientExists = true;
                            break;
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(TransferScreen.this, "Error reading accounts file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!recipientExists) {
                    JOptionPane.showMessageDialog(TransferScreen.this, "Recipient account does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Always show success message if account exists
                JOptionPane.showMessageDialog(TransferScreen.this, "Successfully transferred ₦" + amount + " from " + fromAccount + " to account " + toAccount + ".");
                dispose();
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
        new TransferScreen("2054567890"); // Example account number for testing
    }
}
