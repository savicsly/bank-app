package src.data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Seeder {

    public static void seedCustomers() throws NoSuchAlgorithmException {
        String[] accounts = {
            "Current,Chinedu Okafor,0876900522,5000.0,Lagos,Male,MasterCard,chinedu.okafor@example.com,08012345678,password",
            "Current,Aisha Bello,0103113043,5000.0,Kano,Female,Visa,aisha.bello@example.com,08023456789,password",
            "Savings,Ngozi Nwosu,0173829054,5000.0,Enugu,Female,Verve,ngozi.nwosu@example.com,08034567890,password",
            "Current,Emeka Obi,0261240717,5000.0,Abuja,Male,MasterCard,emeka.obi@example.com,08045678901,password",
            "Savings,Funke Adeyemi,0140856879,5000.0,Oyo,Female,Visa,funke.adeyemi@example.com,08056789012,password",
            "Current,Ibrahim Yusuf,0087200532,5000.0,Kaduna,Male,Verve,ibrahim.yusuf@example.com,08067890123,password",
            "Current,Amaka Eze,0005144749,5000.0,Anambra,Female,MasterCard,amaka.eze@example.com,08078901234,password",
            "Current,Tunde Bakare,0998814882,5000.0,Ogun,Male,Visa,tunde.bakare@example.com,08089012345,password",
            "Current,Zainab Mohammed,0592515299,5000.0,Borno,Female,Verve,zainab.mohammed@example.com,08090123456,password",
            "Savings,Bola Akinwale,0377409842,5000.0,Ekiti,Male,MasterCard,bola.akinwale@example.com,08001234567,password"
        };

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("accounts.txt"))) {
            writer.write(""); // Clear the file content
        } catch (IOException e) {
            System.err.println("Error clearing the file: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("accounts.txt", true))) {
            for (String account : accounts) {
                String[] parts = account.split(",");
                String accountType = parts[0];
                String name = parts[1];
                String accountNumber = generateAccountNumber();
                double balance = Double.parseDouble(parts[3]);
                String state = parts[4];
                String gender = parts[5];
                String cardType = parts[6];
                String email = parts[7];
                String phoneNumber = parts[8];
                String password = parts[9];

                String hashedPassword = PasswordManager.hashPassword(password);
                writer.write(accountType + "," + name + "," + accountNumber + "," + balance + "," + state + "," + gender + "," + cardType + "," + email + "," + phoneNumber + "," + hashedPassword);
                writer.newLine();
            }
            System.out.println("10 customers have been seeded successfully.");
        } catch (IOException e) {
            System.err.println("Error seeding customers: " + e.getMessage());
        }
    }

    private static String generateAccountNumber() {
        Random random = new Random();
        return "20" + String.format("%08d", random.nextInt(100000000));
    }

    public static void seedTransactions() {
        try {
            String accountNumber = null;
            double balance = 10000;
            // Get the first account from accounts.txt
            String[] firstAccountParts = null;
            java.util.List<String> allAccounts = new java.util.ArrayList<>();
            try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader("accounts.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (firstAccountParts == null) {
                        firstAccountParts = line.split(",");
                        if (firstAccountParts.length >= 3) {
                            accountNumber = firstAccountParts[2].trim();
                        }
                    }
                    allAccounts.add(line);
                }
            }
            // If no accounts, or first account is missing, do nothing
            if (accountNumber == null) return;
            // Seed transactions for the first account
            try (java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter("transactions.txt", false))) {
                for (int i = 0; i < 20; i++) { // Changed from 12 to 20
                    String transactionType = (i % 2 == 0) ? "Deposit" : "Withdrawal";
                    int amount = 1000 + (int)(Math.random() * 9000);
                    String timestamp = java.time.LocalDateTime.now().minusDays(20 - i).toString();
                    if (transactionType.equals("Deposit")) {
                        balance += amount;
                        writer.write("Deposit," + accountNumber + "," + amount + "," + timestamp + ",Credit\n");
                    } else {
                        if (balance >= amount) {
                            balance -= amount;
                            writer.write("Withdrawal," + accountNumber + "," + amount + "," + timestamp + ",Debit\n");
                        } else {
                            i--; // Try again if not enough balance
                        }
                    }
                }
            }
            // Update the first account's balance in accounts.txt
            if (firstAccountParts != null && firstAccountParts.length >= 4) {
                firstAccountParts[3] = String.valueOf(balance);
                allAccounts.set(0, String.join(",", firstAccountParts));
                try (java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter("accounts.txt", false))) {
                    for (String acc : allAccounts) {
                        writer.write(acc);
                        writer.newLine();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void seedAccountsAndTransactions() {
        try {
            seedCustomers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        seedTransactions();
    }

    public static void main(String[] args) {
        try {
            seedCustomers();
            seedTransactions();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error hashing password: " + e.getMessage());
        }
    }
}
