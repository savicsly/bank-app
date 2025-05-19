package src.core;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import src.data.BackupManager;

public class BackupScheduler {

    public static void startDailyBackup() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            BackupManager.createDailyBackup();
        }, 0, 1, TimeUnit.DAYS);
    }

    public static void startScheduledPaymentsProcessor() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            try {
                src.data.DataManager.processDueScheduledPayments();
            } catch (Exception e) {
                System.err.println("Error processing scheduled payments: " + e.getMessage());
            }
        }, 0, 1, TimeUnit.MINUTES);
    }
}
