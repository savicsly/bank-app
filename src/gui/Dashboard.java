package src.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class Dashboard extends JFrame {

    private SessionManager sessionManager; // Add session management to the Dashboard
    private String accountNumber;
    private String userName;

    public Dashboard(String accountNumber, String userName) {
        this.accountNumber = accountNumber;
        this.userName = userName;
        sessionManager = new SessionManager(this);
        CustomUI.applyCustomStyling(this);

        setTitle("Banking Application System - Dashboard");

        ScreenUtils.setInitialScreenSize(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(new BorderLayout(10, 10));

        // Add a menu bar with View Profile and Logout options
        javax.swing.JMenuBar menuBar = new javax.swing.JMenuBar();

        javax.swing.JMenu accountManagementMenu = new javax.swing.JMenu("Account Management");
        javax.swing.JMenuItem balanceEnquiryItem = new javax.swing.JMenuItem("Balance Enquiry");
        accountManagementMenu.add(balanceEnquiryItem);

        javax.swing.JMenuItem accountStatementItem = new javax.swing.JMenuItem("Account Statement");
        accountManagementMenu.add(accountStatementItem);
        menuBar.add(accountManagementMenu);

        javax.swing.JMenu transactionMenu = new javax.swing.JMenu("Transactions");
        javax.swing.JMenuItem searchTransactionsItem = new javax.swing.JMenuItem("Search Transactions");
        javax.swing.JMenuItem transferItem = new javax.swing.JMenuItem("Transfer");
        javax.swing.JMenuItem depositItem = new javax.swing.JMenuItem("Deposit");
        javax.swing.JMenuItem withdrawalItem = new javax.swing.JMenuItem("Withdrawal");
        javax.swing.JMenuItem scheduledPaymentsItem = new javax.swing.JMenuItem("Scheduled Payments");

        transactionMenu.add(searchTransactionsItem);
        transactionMenu.add(transferItem);
        transactionMenu.add(depositItem);
        transactionMenu.add(withdrawalItem);
        transactionMenu.add(scheduledPaymentsItem);
        menuBar.add(transactionMenu);

        // Add a Reports menu with Transaction and Audit Report options
        javax.swing.JMenu reportsMenu = new javax.swing.JMenu("Reports");
        javax.swing.JMenuItem transactionReportItem = new javax.swing.JMenuItem("Transaction Report");
        javax.swing.JMenuItem auditReportItem = new javax.swing.JMenuItem("Audit Report");
        reportsMenu.add(transactionReportItem);
        reportsMenu.add(auditReportItem);
        menuBar.add(reportsMenu);

        // Create a Settings menu and move View Profile to it
        javax.swing.JMenu settingsMenu = new javax.swing.JMenu("Settings");
        javax.swing.JMenuItem viewProfileItem = new javax.swing.JMenuItem("View Profile");
        settingsMenu.add(viewProfileItem);
        menuBar.add(settingsMenu);

        // Add an Admin menu with an Admin Panel option
        javax.swing.JMenu adminMenu = new javax.swing.JMenu("Admin");
        javax.swing.JMenuItem adminPanelItem = new javax.swing.JMenuItem("Admin Panel");
        adminMenu.add(adminPanelItem);
        menuBar.add(adminMenu);
        adminPanelItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminPanelScreen();
            }
        });

        javax.swing.JMenu logoutMenu = new javax.swing.JMenu("Logout");
        javax.swing.JMenuItem logoutItem = new javax.swing.JMenuItem("Logout");
        logoutMenu.add(logoutItem);
        menuBar.add(logoutMenu);

        // Add action listener for Logout menu item
        logoutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginScreen();
            }
        });

        // Add action listener for View Profile menu item
        viewProfileItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewProfileScreen(accountNumber, userName);
            }
        });

        // Set the menu bar for the frame
        setJMenuBar(menuBar);

        // Add action listeners for menu items

        // Add action listeners for transaction menu items
        transferItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TransferScreen(accountNumber);
            }
        });
        depositItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DepositScreen(accountNumber);
            }
        });
        withdrawalItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WithdrawalScreen(accountNumber);
            }
        });
        scheduledPaymentsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ScheduledPaymentsScreen(accountNumber);
            }
        });

        // Add action listener for Search Transactions menu item
        searchTransactionsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SearchTransactionsScreen();
            }
        });

        // Add action listener for Account Statement menu item
        accountStatementItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AccountStatementScreen(accountNumber);
            }
        });

        // Add action listeners for report menu items
        transactionReportItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReportsScreen(accountNumber);
            }
        });
        auditReportItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AuditReportScreen();
            }
        });

        // Add action listener for Balance Enquiry menu item
        balanceEnquiryItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BalanceEnquiryScreen(accountNumber);
            }
        });

        // Add tooltips to menu items
        scheduledPaymentsItem.setToolTipText("View and schedule future payments or transfers");
        transferItem.setToolTipText("Transfer funds to another account");
        depositItem.setToolTipText("Deposit money into your account");
        withdrawalItem.setToolTipText("Withdraw money from your account");
        searchTransactionsItem.setToolTipText("Search your transaction history");
        accountStatementItem.setToolTipText("View and export your account statement");
        transactionReportItem.setToolTipText("Generate a transaction report");
        auditReportItem.setToolTipText("View audit logs for your account");
        balanceEnquiryItem.setToolTipText("Check your current account balance");

        // Create a panel for the welcome message
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        LocalDateTime now = LocalDateTime.now();
        String timeOfDay = now.getHour() < 12 ? "morning" : now.getHour() < 18 ? "afternoon" : "evening";
        String formattedDate = now.format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"));
        JLabel welcomeLabel = new JLabel("Welcome, " + userName + "! Good " + timeOfDay + ". Today is " + formattedDate + ".");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomePanel.add(welcomeLabel);

        JPanel copyrightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel copyrightLabel = new JLabel("© 2025 Banking Application System - COSC 212. All rights reserved.");
        copyrightLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        copyrightPanel.add(copyrightLabel);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader("transactions.txt"))) {
            String line;
            int totalCredits = 0;
            int totalDebits = 0;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String transactionType = parts[4];
                    String account = parts[1];
                    double amount = Double.parseDouble(parts[2]);

                    if (account.equals(accountNumber)) {
                        if ("Credit".equals(transactionType)) {
                            totalCredits += amount;
                        } else if ("Debit".equals(transactionType)) {
                            totalDebits += amount;
                        }
                    }
                }
            }

            dataset.addValue(totalCredits, "Credits", "Account");
            dataset.addValue(totalDebits, "Debits", "Account");
        } catch (java.io.IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        // Create a bar chart using the updated dataset
        JFreeChart chart = ChartFactory.createBarChart(
            "Transactions",
            "Transaction Type",
            "Amount (₦)",
            dataset
        );
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(400, 300));
        chartPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Create a panel for account summary and chart
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(Color.WHITE);
        topPanel.setOpaque(true);

        // Create a split panel for account summary and transactions
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(150);

        // Upper part: Account summary
        JPanel accountSummaryPanel = new JPanel(new BorderLayout());
        JLabel accountSummaryLabel = new JLabel("Account Summary", JLabel.CENTER);
        accountSummaryLabel.setFont(new Font("Arial", Font.BOLD, 14));

        String balance = "₦0";
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader("accounts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[2].equals(accountNumber)) {
                    double bal = Double.parseDouble(parts[3]);
                    balance = String.format("₦%,.2f", bal);
                    break;
                }
            }
        } catch (java.io.IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        JTable accountTable = new JTable(new Object[][] {
            {"Balance", balance}
        }, new Object[] {"Account Type", "Amount"});
        JScrollPane accountScrollPane = new JScrollPane(accountTable);

        accountSummaryPanel.add(accountSummaryLabel, BorderLayout.NORTH);
        accountSummaryPanel.add(accountScrollPane, BorderLayout.CENTER);

        // Lower part: Transactions table
        JPanel transactionsPanel = new JPanel(new BorderLayout());
        JLabel transactionsLabel = new JLabel("Recent Transactions", JLabel.CENTER);
        transactionsLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Defensive: Only display transactions for the logged-in user and skip malformed lines
        java.util.List<Object[]> allTransactions = new java.util.ArrayList<>();
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader("transactions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 5) continue;
                String type = parts[0];
                String acc = parts[1];
                String amount = parts[2];
                String dateTime = parts[3];
                String date = dateTime;
                if (dateTime.contains("T")) date = dateTime.split("T")[0];
                String creditOrDebit = parts[4];
                String counterparty = "";
                // Only show transactions for the logged-in user
                if (!acc.trim().equals(accountNumber.trim())) continue;
                // Defensive: skip lines with extra/malformed columns
                if (!amount.matches("[0-9.]+")) continue;
                // Defensive: skip if date is not a valid date or timestamp
                if (date == null || date.isEmpty() || date.length() < 8) continue;
                // Defensive: skip if creditOrDebit is not Credit or Debit
                if (!creditOrDebit.equals("Credit") && !creditOrDebit.equals("Debit")) continue;
                // Handle transfer direction and counterparty
                if (type.equalsIgnoreCase("Transfer") && parts.length >= 6) {
                    if (creditOrDebit.equals("Debit")) {
                        counterparty = "To: " + parts[5];
                    } else if (creditOrDebit.equals("Credit")) {
                        counterparty = "From: " + parts[5];
                    }
                }
                String displayAmount = "₦" + amount;
                if ((type.equalsIgnoreCase("Transfer") && creditOrDebit.equals("Debit")) || type.equalsIgnoreCase("Withdrawal")) {
                    displayAmount = "-₦" + amount;
                }
                String displayType = type;
                if (!counterparty.isEmpty()) {
                    displayType = type + " (" + counterparty + ")";
                }
                allTransactions.add(new Object[] {displayType, displayAmount, date, creditOrDebit});
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        // Sort by full timestamp descending (not just date)
        allTransactions.sort((a, b) -> {
            try {
                // Use the original timestamp for sorting if available
                String tsA = (String)a[2];
                String tsB = (String)b[2];
                return tsB.compareTo(tsA);
            } catch (Exception ex) {
                return 0;
            }
        });
        java.util.List<Object[]> recentTransactions = allTransactions.size() > 10 ? allTransactions.subList(0, 10) : allTransactions;
        JTable transactionsTable = new JTable(
            recentTransactions.toArray(new Object[0][]),
            new Object[] {"Type", "Amount", "Date", "Credit/Debit"}
        );
        // Custom renderer for coloring credit/debit and formatting amount
        transactionsTable.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String creditOrDebit = (String) table.getValueAt(row, 3);
                String amountStr = value.toString();
                if (creditOrDebit.equals("Credit")) {
                    c.setForeground(new Color(0, 153, 51)); // Green
                } else if (creditOrDebit.equals("Debit")) {
                    c.setForeground(Color.RED);
                    // Ensure a space after the minus sign
                    if (amountStr.startsWith("-₦") && !amountStr.startsWith("- ₦")) {
                        amountStr = "- " + amountStr.substring(1);
                        setText(amountStr);
                    }
                } else {
                    c.setForeground(Color.BLACK);
                }
                return c;
            }
        });
        JScrollPane transactionsScrollPane = new JScrollPane(transactionsTable);
        transactionsPanel.removeAll();
        transactionsPanel.add(transactionsLabel, BorderLayout.NORTH);
        transactionsPanel.add(transactionsScrollPane, BorderLayout.CENTER);

        // Add both panels to the split pane
        splitPane.setTopComponent(accountSummaryPanel);
        splitPane.setBottomComponent(transactionsPanel);

        // Adjust the splitPane and chartPanel to occupy the full size of the topPanel
        topPanel.setLayout(new GridLayout(1, 2)); // Use GridLayout to split the panel equally
        topPanel.add(splitPane); // Add the splitPane to the left
        topPanel.add(chartPanel); // Add the chartPanel to the right

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

        setVisible(true);

        // Add panels to the dashboard
        mainPanel.add(welcomePanel, BorderLayout.NORTH);
        mainPanel.add(topPanel, BorderLayout.CENTER);
        mainPanel.add(copyrightPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);
    }

    public static void main(String[] args) {
        new Dashboard("1234567890", "John Doe");
    }
}
