package src.core;

import src.data.BackupManager;
import java.util.concurrent.*;

public class BackupScheduler {

    public static void startDailyBackup() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            BackupManager.createDailyBackup();
        }, 0, 1, TimeUnit.DAYS);
    }
}
