package src.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CustomerRegistrationScreen extends JFrame {

    public CustomerRegistrationScreen() {
        setTitle("Sunflower Banking - Customer Registration");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create components
        JLabel titleLabel = new JLabel("Customer Registration", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel nameLabel = new JLabel("Customer Name:");
        JTextField nameField = new JTextField();
        JLabel accountTypeLabel = new JLabel("Account Type:");
        JComboBox<String> accountTypeComboBox = new JComboBox<>(new String[]{"Savings", "Current"});
        JButton registerButton = new JButton("Register");
        JButton cancelButton = new JButton("Cancel");

        // Add components to form panel
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(accountTypeLabel);
        formPanel.add(accountTypeComboBox);
        formPanel.add(registerButton);
        formPanel.add(cancelButton);

        // Add components to frame
        add(titleLabel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);

        // Add action listeners
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String customerName = nameField.getText();
                String accountType = (String) accountTypeComboBox.getSelectedItem();
                double initialDeposit = 5000.0; // Set initial deposit to ₦5000

                // Generate a 10-digit account number
                String accountNumber = String.format("%010d", new Random().nextInt(1_000_000_000));

                try {
                    saveCustomerToFile(customerName, accountType, accountNumber, initialDeposit);
                    JOptionPane.showMessageDialog(null, "Customer registered successfully!\nAccount Number: " + accountNumber);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error saving customer to file!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void saveCustomerToFile(String customerName, String accountType, String accountNumber, double initialDeposit) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("accounts.txt", true))) {
            writer.write(accountType + "," + customerName + "," + accountNumber + "," + initialDeposit);
            writer.newLine();
        }
    }

    public static void main(String[] args) {
        new CustomerRegistrationScreen();
    }
}
