package src.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import src.data.PasswordManager;

public class LoginScreen extends JFrame {

    private JTextField accountNumberField;
    private JPasswordField passwordField;
    private Color primaryColor = new Color(0, 102, 204);
    private Color secondaryColor = new Color(240, 248, 255);

    public LoginScreen() {
        setTitle("Account Management System - Login");
        ScreenUtils.setInitialScreenSize(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a wrapper panel to center the content
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridy = 1;

        JPanel logoPanel = new JPanel();
        JLabel logoLabel = new JLabel(new javax.swing.ImageIcon("abu-logo.png"));
        logoPanel.add(logoLabel);
        logoPanel.setBackground(new Color(245, 245, 245));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerPanel.add(logoPanel, gbc);

        JPanel welcomePanel = new JPanel();
        welcomePanel.setBackground(new Color(245, 245, 245));

        JLabel titleLabel = new JLabel("Welcome to COSC 212 Group B Bank", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(primaryColor);

        JLabel subtitleLabel = new JLabel("Please enter your credentials to login", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 0, 0, 0));
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

        JLabel accountNumberLabel = new JLabel("Account Number:");
        accountNumberLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; // Column 0 for labels
        gbc.gridy = 0; // Row 0 for account number label
        formPanel.add(accountNumberLabel, gbc);

        accountNumberField = new JTextField();
        accountNumberField.setFont(new Font("Arial", Font.PLAIN, 14));
        accountNumberField.setPreferredSize(new Dimension(250, 35));
        accountNumberField.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor, 1));
        accountNumberField.setMargin(new Insets(0, 5, 0, 5));

        accountNumberField.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            accountNumberField.getBorder(),
            javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 10) // 5px left padding
        ));

        gbc.gridx = 0; // Column 0 for input fields
        gbc.gridy = 1; // Row 1 for account number field
        formPanel.add(accountNumberField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; // Column 0 for labels
        gbc.gridy = 2; // Row 2 for password label
        formPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(250, 35));
        passwordField.setBorder(javax.swing.BorderFactory.createLineBorder(primaryColor, 1));
        passwordField.setMargin(new Insets(0, 5, 0, 5));
        // Add left padding to the input fields
        passwordField.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            passwordField.getBorder(),
            javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 10) // 5px left padding
        ));

        gbc.gridx = 0; // Column 0 for input fields
        gbc.gridy = 3; // Row 3 for password field
        formPanel.add(passwordField, gbc);


        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 16));
        exitButton.setPreferredSize(new Dimension(120, 40));
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.setBorderPainted(false);
        exitButton.setFocusPainted(false);
        exitButton.setOpaque(true);
        exitButton.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        exitButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 16));
        loginButton.setPreferredSize(new Dimension(120, 40));
        loginButton.setBackground(primaryColor);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginButton.setOpaque(true);
        loginButton.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        loginButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints buttonGbc = new GridBagConstraints();
        buttonGbc.insets = new Insets(5, 10, 5, 10);

        buttonGbc.gridx = 0;
        buttonPanel.add(exitButton, buttonGbc);

        buttonGbc.gridx = 1;
        buttonGbc.gridy = 0;
        buttonPanel.add(loginButton, buttonGbc);

        buttonPanel.setBackground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        // Add a label for registration link
        JLabel registerLabel = new JLabel("If you don't have an account, click here to register.");
        registerLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        registerLabel.setForeground(primaryColor);
        registerLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        registerLabel.setHorizontalAlignment(JLabel.CENTER);
        registerLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose();
                new RegistrationScreen();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        formPanel.add(registerLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        centerPanel.setBackground(new Color(245, 245, 245));
        centerPanel.add(welcomePanel, gbc);

        gbc.gridy = 2;
        centerPanel.add(formPanel, gbc);

        setContentPane(centerPanel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountNumber = accountNumberField.getText();
                String password = new String(passwordField.getPassword());

                if (validateLogin(accountNumber, password)) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    dispose();
                    new Dashboard();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        exitButton.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    public LoginScreen(String prefilledAccountNumber, String prefilledPassword) {
        this();
        accountNumberField.setText(prefilledAccountNumber);
        passwordField.setText(prefilledPassword);
    }

    private boolean validateLogin(String accountNumber, String password) {
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader("accounts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 9) {
                    continue;
                }
                if (parts[2].equals(accountNumber)) {
                    String storedHashedPassword = parts[9];
                    String hashedPassword = PasswordManager.hashPassword(password);
                    return storedHashedPassword.equals(hashedPassword);
                }
            }
        } catch (java.io.IOException | java.security.NoSuchAlgorithmException e) {
            System.err.println("Error validating login: " + e.getMessage());
        }
        return false;
    }

    public static void main(String[] args) {
        new LoginScreen();
    }
}
