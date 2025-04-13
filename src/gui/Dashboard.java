package src.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class Dashboard extends JFrame {

    private SessionManager sessionManager; // Add session management to the Dashboard

    public Dashboard() {
        sessionManager = new SessionManager(this);
        CustomUI.applyCustomStyling(this);

        setTitle("Sunflower Banking Management System - Dashboard");

        ScreenUtils.setInitialScreenSize(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(new BorderLayout(10, 10));

        // Create a panel for the welcome message
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        String userName = System.getProperty("user.name");
        LocalDateTime now = LocalDateTime.now();
        String timeOfDay = now.getHour() < 12 ? "morning" : now.getHour() < 18 ? "afternoon" : "evening";
        String formattedDate = now.format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"));
        JLabel welcomeLabel = new JLabel("Welcome, " + userName + "! Good " + timeOfDay + ". Today is " + formattedDate + ".");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomePanel.add(welcomeLabel);

        // Create a sample chart using JFreeChart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(5000, "Savings", "April");
        dataset.addValue(2000, "Current", "April");
        JFreeChart chart = ChartFactory.createBarChart(
            "Account Balances", // Chart title
            "Account Type", // X-axis label
            "Balance (₦)", // Y-axis label
            dataset
        );
        ChartPanel chartPanel = new ChartPanel(chart);

        // Add the chart to the chart panel
        chartPanel.setPreferredSize(new java.awt.Dimension(400, 300));
        chartPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Create a panel for account summary and chart
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(new Color(240, 240, 240)); // Light gray background for the top panel
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding to the top panel
        topPanel.setBackground(Color.WHITE); // Set background color to white
        topPanel.setOpaque(true); // Ensure the panel is opaque

        // Account summary heading and table
        JLabel accountSummaryLabel = new JLabel("Account Summary", JLabel.CENTER);
        accountSummaryLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTable accountTable = new JTable(new Object[][] {
            {"Savings", "₦5000"},
            {"Current", "₦2000"}
        }, new Object[] {"Account Type", "Balance"});
        JScrollPane accountScrollPane = new JScrollPane(accountTable);

        // Add account summary and chart to the top panel
        JPanel accountSummaryPanel = new JPanel(new BorderLayout());
        accountSummaryPanel.add(accountSummaryLabel, BorderLayout.NORTH);
        accountSummaryPanel.add(accountScrollPane, BorderLayout.CENTER);

        // Remove the percentage-based layout and ensure account summary and chart are displayed
        topPanel.setLayout(new BorderLayout(10, 10));

        // Create a pie chart for transaction types
        DefaultPieDataset<String> pieDataset = new DefaultPieDataset<>();
        pieDataset.setValue("Transfer", 30);
        pieDataset.setValue("Withdrawal", 40);
        pieDataset.setValue("Deposit", 30);
        // Update the pie chart to include the legend
        JFreeChart pieChart = ChartFactory.createPieChart(
            "Transaction Types", // Chart title
            pieDataset, // Dataset
            true, // Include legend
            true, // Tooltips
            false // URLs
        );

        // Customize the pie chart to display percentages
        @SuppressWarnings("unchecked")
        org.jfree.chart.plot.PiePlot<String> plot = (org.jfree.chart.plot.PiePlot<String>) pieChart.getPlot();
        plot.setLabelGenerator(new org.jfree.chart.labels.StandardPieSectionLabelGenerator("{0} ({2})")); // Show percentages
        plot.setLabelFont(new Font("Arial", Font.PLAIN, 12));
        plot.setLabelBackgroundPaint(new Color(255, 255, 255, 200)); // Semi-transparent background for labels

        ChartPanel pieChartPanel = new ChartPanel(pieChart);
        pieChartPanel.setPreferredSize(new java.awt.Dimension(300, 300));
        pieChartPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Add account summary, and chart and pie chart to the top panel without specific proportions
        topPanel.add(accountSummaryPanel, BorderLayout.WEST);
        topPanel.add(chartPanel, BorderLayout.CENTER);
        topPanel.add(pieChartPanel, BorderLayout.EAST);

        // Ensure the recent transactions table is properly displayed
        JPanel transactionsPanel = new JPanel(new BorderLayout(10, 10));
        JLabel transactionsLabel = new JLabel("Recent Transactions", JLabel.CENTER);
        transactionsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTable transactionsTable = new JTable(new Object[][] {
            {"Deposit", "₦1000", "Savings", "2025-04-10"},
            {"Withdrawal", "₦500", "Current", "2025-04-09"}
        }, new Object[] {"Type", "Amount", "Account", "Date"});
        JScrollPane transactionsScrollPane = new JScrollPane(transactionsTable);

        // Add components to the transactions panel
        transactionsPanel.add(transactionsLabel, BorderLayout.NORTH);
        transactionsPanel.add(transactionsScrollPane, BorderLayout.CENTER);



        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton accountManagementButton = new JButton("Account Management");
        JButton logoutButton = new JButton("Logout");
        JButton reportsButton = new JButton("Reports"); // Add a new button for reports

        // Add buttons to panel
        buttonPanel.add(accountManagementButton);
        buttonPanel.add(logoutButton);
        buttonPanel.add(reportsButton); // Add the reports button to the panel

        // Add a button for loan management
        JButton loanManagementButton = new JButton("Loan Management");
        buttonPanel.add(loanManagementButton);

        // Add a button for searching transactions
        JButton searchTransactionsButton = new JButton("Search Transactions");
        buttonPanel.add(searchTransactionsButton);

        // Add a button to view the audit report
        JButton viewAuditReportButton = new JButton("View Audit Report");
        viewAuditReportButton.setFont(new Font("Arial", Font.PLAIN, 16));
        viewAuditReportButton.setBackground(new Color(33, 150, 243)); // Blue background
        viewAuditReportButton.setForeground(Color.WHITE);
        viewAuditReportButton.setFocusPainted(false);
        viewAuditReportButton.setPreferredSize(new Dimension(180, 40));

        // Update the action listener to load the audit log into the AuditReportScreen
        viewAuditReportButton.addActionListener(e -> {
            new AuditReportScreen(); // Open the AuditReportScreen instead of the .log file
        });

        // Add the button to the button panel
        buttonPanel.add(viewAuditReportButton);

        // Add components to frame
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        accountManagementButton.addActionListener(e -> new AccountManagementScreen());
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginScreen();
        });
        reportsButton.addActionListener(e -> new ReportsScreen()); // Add action listener for the reports button

        // Add action listener for the loan management button
        loanManagementButton.addActionListener(e -> new LoanManagementScreen());

        // Add action listener for the search transactions button
        searchTransactionsButton.addActionListener(e -> new SearchTransactionsScreen());

        // Add a mouse listener to reset the session timer on user interaction
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                sessionManager.resetTimer();
            }
        });

        // Add a key listener to reset the session timer on user interaction
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                sessionManager.resetTimer();
            }
        });

        // Create a JTable to display the transactions
        String[] columnNames = {"Date", "Description", "Amount"};
        // Read data from the transactions.txt file
        java.util.List<Object[]> transactionList = new java.util.ArrayList<>();
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader("./transactions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 4) {
                String type = parts[0];
                String account = parts[1];
                String amount = "₦" + parts[2];
                String date = parts[3].split("T")[0]; // Extract only the date part
                transactionList.add(new Object[]{date, type, amount, account});
            }
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        // Convert the list to an array
        Object[][] data = transactionList.toArray(new Object[0][]);

        // Ensure the topPanel is added to the content pane and visible
        add(topPanel, BorderLayout.CENTER);
        topPanel.revalidate();
        topPanel.repaint();

        setVisible(true);

        // Add panels to the dashboard
        mainPanel.add(welcomePanel, BorderLayout.NORTH);
        mainPanel.add(topPanel, BorderLayout.CENTER);
        mainPanel.add(transactionsPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        new Dashboard();
    }
}
