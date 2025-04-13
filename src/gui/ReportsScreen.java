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

    public ReportsScreen() {
        setTitle("Sunflower Banking - Reports and Statements");
        setSize(600, 400);
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

            String[][] data = transactions.stream()
                .filter(t -> filterDate == null || t.contains(filterDate))
                .map(t -> t.split(","))
                .toArray(String[][]::new);

            String[] columnNames = {"Type", "Account", "Amount", "Timestamp"};
            table.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error loading transactions!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportTransactions() throws IOException {
        List<String> transactions = DataManager.readTransactions();
        try (java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter("transactions_report.txt"))) {
            for (String transaction : transactions) {
                writer.write(transaction);
                writer.newLine();
            }
        }
    }

    public static void main(String[] args) {
        new ReportsScreen();
    }
}
