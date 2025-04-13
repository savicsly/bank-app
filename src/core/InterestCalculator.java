package src.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InterestCalculator {

    private static final double SAVINGS_INTEREST_RATE = 0.025; // 2.5% daily interest
    private static final String ACCOUNTS_FILE = "accounts.txt";

    public static void startDailyInterestCalculation() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            try {
                calculateInterest();
            } catch (IOException e) {
                System.err.println("Error calculating interest: " + e.getMessage());
            }
        }, 0, 1, TimeUnit.DAYS);
    }

    private static void calculateInterest() throws IOException {
        List<String> updatedAccounts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String accountType = parts[0];
                String accountHolder = parts[1];
                double balance = Double.parseDouble(parts[2]);

                if ("Savings".equalsIgnoreCase(accountType)) {
                    balance += balance * SAVINGS_INTEREST_RATE;
                }

                updatedAccounts.add(accountType + "," + accountHolder + "," + balance);
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE))) {
            for (String account : updatedAccounts) {
                writer.write(account);
                writer.newLine();
            }
        }
    }
}
