package src.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DataManager {

    private static final String ACCOUNTS_FILE = "accounts.txt";
    private static final String TRANSACTIONS_FILE = "transactions.txt";
    private static final String SCHEDULED_PAYMENTS_FILE = "scheduled_payments.txt";

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

    public static void saveTransaction(String transactionType, String accountNumber, double amount, String timestamp) throws IOException, src.core.InvalidAccountException {
        boolean accountFound = false;
        String trimmedAccountNumber = accountNumber.trim();
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[2].trim().equals(trimmedAccountNumber)) {
                    accountFound = true;
                    break;
                }
            }
        }
        if (!accountFound) {
            throw new src.core.InvalidAccountException("Account not found: " + trimmedAccountNumber);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTIONS_FILE, true))) {
            String creditOrDebit = "Debit";
            if (transactionType.equalsIgnoreCase("Deposit")) {
                creditOrDebit = "Credit";
            } else if (transactionType.equalsIgnoreCase("Withdrawal")) {
                creditOrDebit = "Debit";
            } // else: for Transfer, Scheduled, etc, keep as Debit or handle as needed
            writer.write(transactionType + "," + trimmedAccountNumber + "," + amount + "," + timestamp + "," + creditOrDebit);
            writer.newLine();
        } catch (IOException e) {
            throw new IOException("Failed to write transaction: " + e.getMessage(), e);
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
    public static void updateBalance(String accountNumber, double amount) throws IOException, src.core.InvalidAccountException {
        List<String> updated = new ArrayList<>();
        boolean found = false;
        String trimmedAccountNumber = accountNumber.trim();
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    System.out.println("DEBUG: Checking account number in file: '" + parts[2] + "'");
                }
                if (parts.length >= 3 && parts[2].trim().equals(trimmedAccountNumber)) {
                    System.out.println("DEBUG: Matched account. Raw balance string: '" + parts[3] + "'");
                    double balance = Double.parseDouble(parts[3]);
                    System.out.println("DEBUG: Parsed balance as double: " + balance);
                    balance += amount;
                    System.out.println("DEBUG: New balance after update: " + balance);
                    if (balance < 0) throw new src.core.InvalidAccountException("Insufficient funds.");
                    parts[3] = String.valueOf(balance);
                    found = true;
                    updated.add(String.join(",", parts));
                } else {
                    updated.add(line);
                }
            }
        }
        if (!found) {
            System.out.println("DEBUG: Account not found for updateBalance: '" + trimmedAccountNumber + "'");
            throw new src.core.InvalidAccountException("Account not found: " + trimmedAccountNumber);
        }
        try (FileOutputStream fos = new FileOutputStream(ACCOUNTS_FILE, false);
             FileChannel channel = fos.getChannel();
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos))) {
            channel.lock(); // lock acquired for exclusive access
            for (String l : updated) writer.write(l + "\n");
            System.out.println("DEBUG: Finished writing updated accounts file.");
        }
    }

    public static void saveScheduledPayment(String fromAccount, String toAccount, String amount, String dateTime, String type) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(SCHEDULED_PAYMENTS_FILE, true);
             FileChannel channel = fos.getChannel();
             FileLock lock = channel.lock(); // lock acquired for exclusive access, intentionally unused
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos))) {
            writer.write(fromAccount + "," + toAccount + "," + amount + "," + dateTime + "," + type);
            writer.newLine();
        }
    }

    public static void processDueScheduledPayments() throws IOException {
        List<String> remaining = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        try (BufferedReader reader = new BufferedReader(new FileReader(SCHEDULED_PAYMENTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 5) continue;
                String from = parts[0];
                String to = parts[1];
                String amount = parts[2];
                String dateTime = parts[3];
                String type = parts[4];
                LocalDateTime scheduledTime = LocalDateTime.parse(dateTime);
                if (!scheduledTime.isAfter(now)) {
                    // Process payment/transfer
                    try {
                        double amt = Double.parseDouble(amount);
                        // Withdraw from 'from', deposit to 'to'
                        updateBalance(from, -amt);
                        updateBalance(to, amt);
                        saveTransaction("Scheduled " + type, from, amt, now.toString());
                        src.core.NotificationManager.notifyScheduledPaymentProcessed(from, to, amt);
                    } catch (Exception ex) {
                        // Log error, skip
                    }
                } else {
                    remaining.add(line);
                }
            }
        }
        // Rewrite file with remaining
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCHEDULED_PAYMENTS_FILE))) {
            for (String l : remaining) writer.write(l + "\n");
        }
    }

    public static boolean updatePassword(String accountNumber, String newHashedPassword) throws IOException {
        List<String> updated = new ArrayList<>();
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 10 && parts[2].equals(accountNumber)) {
                    parts[9] = newHashedPassword;
                    found = true;
                    updated.add(String.join(",", parts));
                } else {
                    updated.add(line);
                }
            }
        }
        try (FileOutputStream fos = new FileOutputStream(ACCOUNTS_FILE, false);
             FileChannel channel = fos.getChannel();
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos))) {
            channel.lock(); // lock acquired for exclusive access
            for (String l : updated) writer.write(l + "\n");
        }
        return found;
    }
}
