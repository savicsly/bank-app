package src.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class AccountStatementScreen extends JFrame {

    public AccountStatementScreen(String accountNumber) {
        setTitle("Account Statement");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Account Statement", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        JPanel statementPanel = new JPanel(new BorderLayout());
        statementPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30)); // Add padding: top, left, bottom, right

        List<Object[]> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[1].equals(accountNumber)) {
                    transactions.add(new Object[] {parts[0], parts[2], parts[3]});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading account statement!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        JTable statementTable = new JTable(
            transactions.toArray(new Object[0][]),
            new Object[] {"Type", "Amount", "Date"}
        );
        JScrollPane scrollPane = new JScrollPane(statementTable);
        statementPanel.add(scrollPane, BorderLayout.CENTER);

        add(statementPanel, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        new AccountStatementScreen("1234567890"); // Example usage
    }
}
