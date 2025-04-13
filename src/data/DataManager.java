package src.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataManager {

    private static final String ACCOUNTS_FILE = "accounts.txt";
    private static final String TRANSACTIONS_FILE = "transactions.txt";

    // Save account details to file
    public static void saveAccount(String accountType, String accountHolder, String accountNumber, double initialDeposit, String state,  String gender, String cardType, String email, String phoneNumber, String password) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE, true))) {
            writer.write(accountType + "," + accountHolder + "," + accountNumber + "," + initialDeposit + "," + state + "," + gender + "," +  cardType + "," + email + "," + phoneNumber + "," + password);
            writer.newLine();
        }
    }

    // Read all accounts from file
    public static List<String> readAccounts() throws IOException {
        List<String> accounts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                accounts.add(line);
            }
        }
        return accounts;
    }

    public static void saveTransaction(String transactionType, String accountType, double amount, String timestamp) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTIONS_FILE, true))) {
            writer.write(transactionType + "," + accountType + "," + amount + "," + timestamp);
            writer.newLine();
        }
    }

    // Read all transactions from file
    public static List<String> readTransactions() throws IOException {
        List<String> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(TRANSACTIONS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                transactions.add(line);
            }
        }
        return transactions;
    }

    // Update balance for a given account type
    public static void updateBalance(String accountType, double amount) throws IOException {
        List<String> updatedAccounts = new ArrayList<>();
        boolean accountFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(accountType)) {
                    double currentBalance = Double.parseDouble(parts[2]);
                    currentBalance += amount;
                    parts[2] = String.valueOf(currentBalance);
                    accountFound = true;
                }
                updatedAccounts.add(String.join(",", parts));
            }
        }

        if (!accountFound) {
            throw new IllegalArgumentException("Account type not found: " + accountType);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE))) {
            for (String account : updatedAccounts) {
                writer.write(account);
                writer.newLine();
            }
        }
    }
}
