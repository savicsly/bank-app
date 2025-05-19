package src.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoanManagementScreen extends JFrame {

    public LoanManagementScreen() {
        setTitle("Sunflower Banking - Loan Management");

        // Set the screen size to 60% of the entire screen
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.6);
        int height = (int) (screenSize.height * 0.6);
        setSize(width, height);

        // Center the screen in the viewport
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create components
        JLabel titleLabel = new JLabel("Loan Application", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        JLabel accountLabel = new JLabel("Account Type:");
        JComboBox<String> accountComboBox = new JComboBox<>(new String[]{"Savings", "Current"});
        JLabel loanAmountLabel = new JLabel("Loan Amount:");
        JTextField loanAmountField = new JTextField();
        JLabel repaymentPeriodLabel = new JLabel("Repayment Period (months):");
        JTextField repaymentPeriodField = new JTextField();
        JButton applyLoanButton = new JButton("Apply for Loan");
        JButton cancelButton = new JButton("Cancel");

        // Add components to form panel
        formPanel.add(accountLabel);
        formPanel.add(accountComboBox);
        formPanel.add(loanAmountLabel);
        formPanel.add(loanAmountField);
        formPanel.add(repaymentPeriodLabel);
        formPanel.add(repaymentPeriodField);
        formPanel.add(applyLoanButton);
        formPanel.add(cancelButton);

        // Add components to frame
        add(titleLabel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);

        // Add action listeners
        applyLoanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountType = (String) accountComboBox.getSelectedItem();
                String loanAmountText = loanAmountField.getText();
                String repaymentPeriodText = repaymentPeriodField.getText();
                try {
                    double loanAmount = Double.parseDouble(loanAmountText);
                    int repaymentPeriod = Integer.parseInt(repaymentPeriodText);
                    if (loanAmount > 0 && repaymentPeriod > 0) {
                        saveLoanToFile(accountType, loanAmount, repaymentPeriod);
                        JOptionPane.showMessageDialog(null, "Loan application submitted successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Loan amount and repayment period must be greater than zero!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter numeric values.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void saveLoanToFile(String accountType, double loanAmount, int repaymentPeriod) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("loans.txt", true))) {
            String timestamp = LocalDateTime.now().toString();
            writer.write(accountType + "," + loanAmount + "," + repaymentPeriod + "," + timestamp);
            writer.newLine();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error saving loan application!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new LoanManagementScreen();
    }
}
