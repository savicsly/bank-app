package src.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import src.data.DataManager;
import src.data.PasswordManager;

public class RegistrationScreen extends JFrame {

    private JTextField fullNameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JComboBox<String> stateDropdown;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private JCheckBox masterCardCheckbox;
    private JCheckBox visaCardCheckbox;
    private JCheckBox verveCardCheckbox;
    private Color primaryColor = new Color(0, 102, 204);
    private Color secondaryColor = new Color(240, 248, 255);

    public RegistrationScreen() {
        setTitle("Account Management System - Registration");
        ScreenUtils.setInitialScreenSize(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 15, 15, 15);

        JPanel welcomePanel = new JPanel();
        welcomePanel.setBackground(new Color(245, 245, 245));
        JLabel titleLabel = new JLabel("Welcome to COSC 212 Group B Bank", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(primaryColor);
        JLabel subtitleLabel = new JLabel("Please fill in the form to create an account", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setForeground(Color.GRAY);
        welcomePanel.add(titleLabel);
        welcomePanel.add(subtitleLabel);
        welcomePanel.setLayout(new GridBagLayout());
        GridBagConstraints welcomeGbc = new GridBagConstraints();
        welcomeGbc.gridx = 0;
        welcomeGbc.gridy = 0;
        welcomePanel.add(titleLabel, welcomeGbc);
        welcomeGbc.gridy = 1;
        welcomeGbc.gridy = 1;
        welcomePanel.add(subtitleLabel, welcomeGbc);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(40, 20, 40, 20));

        // Account Type (should be at the top, above Full Name)
        JLabel accountTypeLabel = new JLabel("Account Type:");
        accountTypeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JComboBox<String> accountTypeCombo = new JComboBox<>(new String[]{"Savings", "Current"});
        accountTypeCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(accountTypeLabel);
        formPanel.add(accountTypeCombo);

        // Full Name
        JLabel fullNameLabel = new JLabel("Full Name:");
        fullNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(fullNameLabel, gbc);

        fullNameField = new JTextField();
        fullNameField.setPreferredSize(new java.awt.Dimension(250, 35));
        fullNameField.setFont(new Font("Arial", Font.PLAIN, 14));
        fullNameField.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor, 1));

        gbc.gridx = 1;
        formPanel. add(fullNameField, gbc);

        // Email Field
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(emailLabel, gbc);

        emailField = new JTextField();
        emailField.setPreferredSize(new java.awt.Dimension(250, 35));
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor, 1));
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        // Phone Number Field
        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3; // Adjusted to place it after the email field
        formPanel.add(phoneNumberLabel, gbc);

        JTextField phoneNumberField = new JTextField();
        phoneNumberField.setPreferredSize(new java.awt.Dimension(250, 35));
        phoneNumberField.setFont(new Font("Arial", Font.PLAIN, 14));
        phoneNumberField.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor, 1));
        gbc.gridx = 1;
        formPanel.add(phoneNumberField, gbc);

        // Password Field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new java.awt.Dimension(250, 35));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor, 1));
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        // State Dropdown
        JLabel stateLabel = new JLabel("State:");
        stateLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(stateLabel, gbc);

        String[] states = {"Abia", "Adamawa", "Akwa Ibom", "Anambra", "Bauchi", "Bayelsa", "Benue", "Borno", "Cross River", "Delta", "Ebonyi", "Edo", "Ekiti", "Enugu", "Gombe", "Imo", "Jigawa", "Kaduna", "Kano", "Katsina", "Kebbi", "Kogi", "Kwara", "Lagos", "Nasarawa", "Niger", "Ogun", "Ondo", "Osun", "Oyo", "Plateau", "Rivers", "Sokoto", "Taraba", "Yobe", "Zamfara"};
        stateDropdown = new JComboBox<String>(states);
        stateDropdown.setPreferredSize(new java.awt.Dimension(250, 35));
        stateDropdown.setFont(new Font("Arial", Font.PLAIN, 14));
        stateDropdown.setBackground(Color.WHITE);
        gbc.gridx = 1;
        formPanel.add(stateDropdown, gbc);

        // Gender Radio Buttons
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(genderLabel, gbc);

        maleRadioButton = new JRadioButton("Male");
        femaleRadioButton = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);

        JPanel genderPanel = new JPanel();
        genderPanel.add(maleRadioButton);
        genderPanel.add(femaleRadioButton);
        gbc.gridx = 1;
        formPanel.add(genderPanel, gbc);

        // Card Type Checkboxes
        JLabel cardTypeLabel = new JLabel("Card Type:");
        cardTypeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(cardTypeLabel, gbc);

        masterCardCheckbox = new JCheckBox("Master Card");
        visaCardCheckbox = new JCheckBox("Visa Card");
        verveCardCheckbox = new JCheckBox("Verve Card");

        JPanel cardTypePanel = new JPanel();
        // cardTypePanel.setBackground(Color.WHITE);
        cardTypePanel.add(masterCardCheckbox);
        cardTypePanel.add(visaCardCheckbox);
        cardTypePanel.add(verveCardCheckbox);
        ButtonGroup cardTypeGroup = new ButtonGroup();
        cardTypeGroup.add(masterCardCheckbox);
        cardTypeGroup.add(visaCardCheckbox);
        cardTypeGroup.add(verveCardCheckbox);
        gbc.gridx = 1;
        formPanel.add(cardTypePanel, gbc);

        // Buttons
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 14));
        exitButton.setPreferredSize(new java.awt.Dimension(150, 40));
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.setBorderPainted(false);
        exitButton.setFocusPainted(false);
        exitButton.setOpaque(true);
        exitButton.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        exitButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exitButton.setFocusable(false);
        exitButton.setMargin(new Insets(0, 5, 0, 5));

        JButton clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Arial", Font.PLAIN, 14));
        clearButton.setPreferredSize(new java.awt.Dimension(150, 40));
        clearButton.setBackground(secondaryColor);
        clearButton.setForeground(Color.BLACK);
        clearButton.setBorderPainted(false);
        clearButton.setFocusPainted(false);
        clearButton.setOpaque(true);
        clearButton.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        clearButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearButton.setFocusable(false);
        clearButton.setMargin(new Insets(0, 5, 0, 5));

        JButton saveAndContinueButton = new JButton("Register");
        saveAndContinueButton.setFont(new Font("Arial", Font.PLAIN, 14));
        saveAndContinueButton.setPreferredSize(new java.awt.Dimension(150, 40));
        saveAndContinueButton.setBackground(primaryColor);
        saveAndContinueButton.setForeground(Color.WHITE);
        saveAndContinueButton.setBorderPainted(false);
        saveAndContinueButton.setFocusPainted(false);
        saveAndContinueButton.setOpaque(true);
        saveAndContinueButton.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        saveAndContinueButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        saveAndContinueButton.setFocusable(false);
        saveAndContinueButton.setMargin(new Insets(0, 5, 0, 5));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(exitButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(saveAndContinueButton);
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        JLabel loginLabel = new JLabel("Have an account? Login here.");
        loginLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        loginLabel.setForeground(new Color(33, 150, 243));
        loginLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loginLabel.setHorizontalAlignment(JLabel.CENTER);
        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose();
                new LoginScreen();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 18;
        gbc.gridwidth = 2;
        formPanel.add(loginLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        centerPanel.setBackground(new Color(245, 245, 245));
        centerPanel.add(welcomePanel, gbc);

        // Wrap the form panel in a JScrollPane to make it scrollable
        JScrollPane scrollPane = new JScrollPane(formPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        centerPanel.add(scrollPane, gbc);

        // Adjust layout constraints to place labels and input fields on separate lines
        gbc.gridx = 0; // Column 0 for labels
        gbc.gridy = 0; // Row 0 for account type label
        formPanel.add(accountTypeLabel, gbc);

        gbc.gridx = 0; // Column 0 for input fields
        gbc.gridy = 1; // Row 1 for account type combo
        formPanel.add(accountTypeCombo, gbc);

        gbc.gridx = 0; // Column 0 for labels
        gbc.gridy = 2; // Row 2 for full name label
        formPanel.add(fullNameLabel, gbc);

        gbc.gridx = 0; // Column 0 for input fields
        gbc.gridy = 3; // Row 3 for full name field
        formPanel.add(fullNameField, gbc);

        gbc.gridx = 0; // Column 0 for labels
        gbc.gridy = 4; // Row 4 for email label
        formPanel.add(emailLabel, gbc);

        gbc.gridx = 0; // Column 0 for input fields
        gbc.gridy = 5; // Row 5 for email field
        formPanel.add(emailField, gbc);

        gbc.gridx = 0; // Column 0 for labels
        gbc.gridy = 6; // Row 6 for phone number label
        formPanel.add(phoneNumberLabel, gbc);

        gbc.gridx = 0; // Column 0 for input fields
        gbc.gridy = 7; // Row 7 for phone number field
        formPanel.add(phoneNumberField, gbc);

        gbc.gridx = 0; // Column 0 for labels
        gbc.gridy = 8; // Row 8 for password label
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 0; // Column 0 for input fields
        gbc.gridy = 9; // Row 9 for password field
        formPanel.add(passwordField, gbc);

        gbc.gridx = 0; // Column 0 for labels
        gbc.gridy = 10; // Row 10 for state label
        formPanel.add(stateLabel, gbc);

        gbc.gridx = 0; // Column 0 for input fields
        gbc.gridy = 11; // Row 11 for state dropdown
        formPanel.add(stateDropdown, gbc);

        gbc.gridx = 0; // Column 0 for labels
        gbc.gridy = 12; // Row 12 for gender label
        formPanel.add(genderLabel, gbc);

        gbc.gridx = 0; // Column 0 for input fields
        gbc.gridy = 13; // Row 13 for gender panel
        formPanel.add(genderPanel, gbc);

        gbc.gridx = 0; // Column 0 for labels
        gbc.gridy = 14; // Row 14 for card type label
        formPanel.add(cardTypeLabel, gbc);

        gbc.gridx = 0; // Column 0 for input fields
        gbc.gridy = 15; // Row 15 for card type panel
        formPanel.add(cardTypePanel, gbc);

        gbc.gridx = 0; // Column 0 for buttons
        gbc.gridy = 16; // Row 16 for button panel
        formPanel.add(buttonPanel, gbc);

        // Ensure the JScrollPane and its contents are properly configured for scrolling
        centerPanel.setPreferredSize(new java.awt.Dimension(400, 800)); // Set a preferred size for the center panel

        // Removed duplicate setContentPane call
        // The JScrollPane wrapping the centerPanel is already set as the content pane.
        // Removed duplicate scrollPane definition
        setContentPane(scrollPane);

        // Action Listeners
        clearButton.addActionListener(e -> clearFields());

        // Save registration data to accounts file and database
        saveAndContinueButton.addActionListener(e -> {
            String generatedAccountNumber = generateAccountNumber();

            String fullName = fullNameField.getText();
            String email = emailField.getText();
            String phoneNumber = phoneNumberField.getText();
            String password = new String(passwordField.getPassword());
            String state = (String) stateDropdown.getSelectedItem();
            String gender = maleRadioButton.isSelected() ? "Male" : "Female";
            String cardType = masterCardCheckbox.isSelected() ? "MasterCard" : visaCardCheckbox.isSelected() ? "Visa" : "Verve";
            String accountType = (String) accountTypeCombo.getSelectedItem();
            String accountNumber = generatedAccountNumber;

            // Hash the password directly without a salt
            String hashedPassword;
            try {
                hashedPassword = PasswordManager.hashPassword(password);
            } catch (java.security.NoSuchAlgorithmException ex) {
                JOptionPane.showMessageDialog(this, "Error hashing password: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Save to accounts.txt
            try (java.io.FileWriter writer = new java.io.FileWriter("accounts.txt", true)) {
                writer.write(String.format("%s,%s,%s,5000.0,%s,%s,%s,%s,%s,%s\n", accountTypeCombo.getSelectedItem(), fullName, accountNumber, state, gender, cardType, email, phoneNumber, hashedPassword));
            } catch (java.io.IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving to accounts file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                DataManager.saveAccount(accountType, fullName, generatedAccountNumber, 5000.0, state, gender, cardType, email, phoneNumber, hashedPassword);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error saving to database: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this, "Registration successful! Your account number is: " + generatedAccountNumber);
            dispose();
            new LoginScreen(accountNumber, "");
        });

        exitButton.addActionListener(e -> System.exit(0));

        // Removed duplicate setContentPane call
        // The JScrollPane wrapping the centerPanel is already set as the content pane.

        setVisible(true);
    }

    private String generateAccountNumber() {
        Random random = new Random();
        return "20" + String.format("%08d", random.nextInt(100000000)); // Ensure 10 digits by prefixing with '20'
    }

    private void clearFields() {
        fullNameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        stateDropdown.setSelectedIndex(0);
        maleRadioButton.setSelected(false);
        femaleRadioButton.setSelected(false);
        masterCardCheckbox.setSelected(false);
        visaCardCheckbox.setSelected(false);
        verveCardCheckbox.setSelected(false);
    }

    public static void main(String[] args) {
        new RegistrationScreen();
    }
}
