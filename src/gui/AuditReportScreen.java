package src.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AuditReportScreen extends JFrame {

    public AuditReportScreen() {
        setTitle("Audit Report");

        // Set the screen size to 60% of the entire screen
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.6);
        int height = (int) (screenSize.height * 0.6);
        setSize(width, height);

        // Center the screen in the viewport
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create a panel to hold the table
        JPanel panel = new JPanel(new BorderLayout());

        // Create the table model and table
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Timestamp", "Action", "Details"}, 0);
        JTable table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        // Filter the log entries to display only the records of the current login user
        String currentUser = System.getProperty("user.name"); // Replace with actual logged-in user logic

        try (BufferedReader reader = new BufferedReader(new FileReader("audit.log"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" - ", 2); // Split by " - "
                if (parts.length == 2) {
                    String timestamp = parts[0].trim();
                    String[] actionDetails = parts[1].split(":", 2); // Further split action and details
                    String action = actionDetails[0].trim();
                    String details = actionDetails.length > 1 ? actionDetails[1].trim() : "";

                    // Check if the details contain the current user's account number or username
                    if (details.contains(currentUser)) {
                        tableModel.addRow(new Object[]{timestamp, action, details});
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add the panel to the frame
        add(panel);

        setVisible(true);
    }

    public static void main(String[] args) {
        new AuditReportScreen();
    }
}
