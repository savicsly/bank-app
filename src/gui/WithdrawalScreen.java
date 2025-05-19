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

public class WithdrawalScreen extends JFrame {

    public WithdrawalScreen(String accountNumber) {
        setTitle("Withdraw Funds");
        setSize(400, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Withdraw Funds", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // --- Modern, consistent UI styling (matches TransferScreen/DepositScreen) ---
        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField();
        amountField.setPreferredSize(new Dimension(140, 14));

        Dimension buttonSize = new Dimension(120, 28);
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 8, 0));
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setPreferredSize(buttonSize);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(buttonSize);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(cancelButton);

        JPanel formPanel = new JPanel(new GridLayout(3, 1, 8, 8));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        formPanel.add(amountLabel);
        formPanel.add(amountField);
        formPanel.add(buttonPanel);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(formPanel, BorderLayout.CENTER);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

        add(titleLabel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountText = amountField.getText();
                if (!src.core.InputValidator.isValidAmount(amountText)) {
                    JOptionPane.showMessageDialog(WithdrawalScreen.this, "Invalid amount format! Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    double amount = Double.parseDouble(amountText);
                    if (amount > 0) {
                        if (amount > 10000) {
                            int confirm = JOptionPane.showConfirmDialog(WithdrawalScreen.this, "This is a large withdrawal. Are you sure you want to proceed?", "Authorization Required", JOptionPane.YES_NO_OPTION);
                            if (confirm != JOptionPane.YES_OPTION) {
                                return;
                            }
                        }
                        String timestamp = java.time.LocalDateTime.now().toString();
                        try {
                            // Use accountNumber for both transaction and balance update
                            src.data.DataManager.saveTransaction("Withdrawal", accountNumber, amount, timestamp);
                            src.data.DataManager.updateBalance(accountNumber, -amount);
                        } catch (src.core.InvalidAccountException ex) {
                            JOptionPane.showMessageDialog(WithdrawalScreen.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        src.data.AuditLogger.log("Withdrawal", "Account: " + accountNumber + ", Amount: " + amount);
                        // src.core.NotificationManager.notifyUser("Withdrawal of ₦" + amount + " from account was successful.");
                        JOptionPane.showMessageDialog(WithdrawalScreen.this, "Successfully withdrew ₦" + amount + " from your account.");
                    } else {
                        JOptionPane.showMessageDialog(WithdrawalScreen.this, "Amount must be greater than zero!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(WithdrawalScreen.this, "Error processing withdrawal: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
