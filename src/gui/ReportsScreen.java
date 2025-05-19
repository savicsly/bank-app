package src.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import src.data.DataManager;

public class ReportsScreen extends JFrame {

    private String accountNumber;

    public ReportsScreen(String accountNumber) {
        this.accountNumber = accountNumber;
        setTitle("Banking System - Reports and Statements");

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
        JLabel titleLabel = new JLabel("Transaction Reports", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JTable transactionTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(transactionTable);

        JPanel filterPanel = new JPanel(new FlowLayout());
        JLabel filterLabel = new JLabel("Filter by Date:");
        JTextField filterField = new JTextField(10);
        JButton filterButton = new JButton("Filter");
        JButton exportButton = new JButton("Export");

        // Add components to filter panel
        filterPanel.add(filterLabel);
        filterPanel.add(filterField);
        filterPanel.add(filterButton);
        filterPanel.add(exportButton);

        // Add components to frame
        add(titleLabel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(filterPanel, BorderLayout.SOUTH);

        // Load all transactions initially
        loadTransactions(transactionTable, null);

        // Add action listeners
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filterDate = filterField.getText();
                loadTransactions(transactionTable, filterDate);
            }
        });

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    exportTransactions();
                    JOptionPane.showMessageDialog(null, "Transactions exported successfully!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error exporting transactions!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }

    private void loadTransactions(JTable table, String filterDate) {
        try {
            List<String> transactions = DataManager.readTransactions();

            // Handle empty transactions gracefully
            if (transactions.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No transactions available to display.", "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Filter transactions for the logged-in user
            java.util.List<Object[]> userTransactions = new java.util.ArrayList<>();
            for (String transaction : transactions) {
                String[] parts = transaction.split(",");
                if (parts.length >= 5 && parts[1].equals(accountNumber)) {
                    if (filterDate == null || transaction.contains(filterDate)) {
                        userTransactions.add(new Object[] {parts[0], parts[2], parts[3], parts[4]});
                    }
                }
            }

            // Populate the JTable with the filtered transactions
            table.setModel(new javax.swing.table.DefaultTableModel(
                userTransactions.toArray(new Object[0][]),
                new Object[] {"Type", "Amount", "Date", "Transaction Type"}
            ));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error loading transactions!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportTransactions() throws IOException {
        List<String> transactions = DataManager.readTransactions();
        try (java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter("transactions_report.txt"))) {
            for (String transaction : transactions) {
                String[] parts = transaction.split(",");
                if (parts.length >= 5 && parts[1].equals(accountNumber)) {
                    writer.write(transaction);
                    writer.newLine();
                }
            }
        }
    }

    public static void main(String[] args) {
        new ReportsScreen("123456789"); // Example account number
    }
}
