package src.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class SearchTransactionsScreen extends JFrame {

    public SearchTransactionsScreen() {
        setTitle("Sunflower Banking - Search Transactions");

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
        JLabel titleLabel = new JLabel("Search Transactions", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel searchPanel = new JPanel(new FlowLayout());
        JLabel searchLabel = new JLabel("Search by Date or Amount:");
        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("Search");

        JTable resultsTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(resultsTable);

        // Add components to search panel
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Add components to frame
        add(titleLabel, BorderLayout.NORTH);
        add(searchPanel, BorderLayout.CENTER);
        add(tableScrollPane, BorderLayout.SOUTH);

        // Add action listener for search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText();
                List<String[]> results = searchTransactions(query);
                String[][] data = results.toArray(new String[0][]);
                String[] columnNames = {"Type", "Account", "Amount", "Timestamp"};
                resultsTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
            }
        });

        setVisible(true);
    }

    private List<String[]> searchTransactions(String query) {
        List<String[]> results = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(query)) {
                    results.add(line.split(","));
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error reading transactions file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return results;
    }

    public static void main(String[] args) {
        new SearchTransactionsScreen();
    }
}
