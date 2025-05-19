package src.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TransactionRecordsScreen extends JFrame {

    public TransactionRecordsScreen(String accountNumber) {
        setTitle("Transaction Records");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Transaction Records", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

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
            JOptionPane.showMessageDialog(this, "Error loading transaction records!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        JTable transactionTable = new JTable(
            transactions.toArray(new Object[0][]),
            new Object[] {"Type", "Amount", "Date"}
        );
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        new TransactionRecordsScreen("1234567890"); // Example usage
    }
}
