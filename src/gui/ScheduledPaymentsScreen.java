package src.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import src.core.InputValidator;
import src.data.DataManager;

public class ScheduledPaymentsScreen extends JFrame {
    public ScheduledPaymentsScreen(String accountNumber) {
        setTitle("Scheduled Payments");
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Schedule a Payment or Transfer", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30)); // Add padding: top, left, bottom, right
        JLabel typeLabel = new JLabel("Type:");
        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Payment", "Transfer"});
        JLabel toAccountLabel = new JLabel("To Account:");
        JTextField toAccountField = new JTextField();
        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField();
        JLabel dateLabel = new JLabel("Date/Time (yyyy-MM-dd HH:mm):");
        JTextField dateField = new JTextField();
        JButton scheduleButton = new JButton("Schedule");
        JButton cancelButton = new JButton("Cancel");

        formPanel.add(typeLabel); formPanel.add(typeCombo);
        formPanel.add(toAccountLabel); formPanel.add(toAccountField);
        formPanel.add(amountLabel); formPanel.add(amountField);
        formPanel.add(dateLabel); formPanel.add(dateField);
        formPanel.add(scheduleButton); formPanel.add(cancelButton);
        add(formPanel, BorderLayout.CENTER);

        scheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String type = (String) typeCombo.getSelectedItem();
                String toAccount = toAccountField.getText().trim();
                String amountStr = amountField.getText().trim();
                String dateStr = dateField.getText().trim();
                if (!InputValidator.isValidAccountNumber(toAccount)) {
                    JOptionPane.showMessageDialog(ScheduledPaymentsScreen.this, "Invalid account number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!InputValidator.isValidAmount(amountStr)) {
                    JOptionPane.showMessageDialog(ScheduledPaymentsScreen.this, "Invalid amount.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (type.equals("Transfer")) {
                    if (toAccount.equals(accountNumber)) {
                        JOptionPane.showMessageDialog(ScheduledPaymentsScreen.this, "Cannot transfer to your own account.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                try {
                    LocalDateTime dateTime = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    // Save scheduled payment or transfer
                    DataManager.saveScheduledPayment(accountNumber, toAccount, amountStr, dateTime.toString(), type);
                    JOptionPane.showMessageDialog(ScheduledPaymentsScreen.this, "Scheduled successfully.");
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(ScheduledPaymentsScreen.this, "Invalid date/time format.", "Error", JOptionPane.ERROR_MESSAGE);
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
