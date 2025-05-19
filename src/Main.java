package src;

import java.security.NoSuchAlgorithmException;

import src.core.BackupScheduler;
import src.data.Seeder;
import src.gui.LoginScreen;

public class Main {
    public static void main(String[] args) {
        try {
            Seeder.seedCustomers();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error seeding customers: " + e.getMessage());
        }
        try {
            Seeder.seedAccountsAndTransactions();
        } catch (Exception e) {
            System.err.println("Error seeding accounts and transactions: " + e.getMessage());
        }
        BackupScheduler.startDailyBackup();
        BackupScheduler.startScheduledPaymentsProcessor();

        String prefilledAccountNumber = "2054567890"; // Default fallback
        String prefilledPassword = "password"; // Default fallback

        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader("accounts.txt"))) {
            String firstLine = reader.readLine();
            if (firstLine != null) {
                String[] parts = firstLine.split(",");
                prefilledAccountNumber = parts[2]; // Use the account number from the first record
            }
        } catch (java.io.IOException e) {
            System.err.println("Error reading accounts.txt: " + e.getMessage());
        }

        new LoginScreen(prefilledAccountNumber, prefilledPassword); // Launch the login screen with pre-filled credentials
    }
}
