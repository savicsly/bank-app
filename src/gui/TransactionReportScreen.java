package src.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TransactionReportScreen extends JFrame {
    private List<Transaction> transactions;

    public TransactionReportScreen(List<Transaction> transactions) {
        this.transactions = transactions;
        setTitle("Transaction Report");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Transaction Report", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        JPanel reportPanel = new JPanel(new BorderLayout());
        reportPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        JTable transactionTable = new JTable(new TransactionTableModel(transactions));
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        reportPanel.add(scrollPane, BorderLayout.CENTER);
        add(reportPanel, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);
    }
}
