package src.data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class AuditLogger {

    private static final String AUDIT_LOG_FILE = "audit.log";

    public static void log(String action, String details) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(AUDIT_LOG_FILE, true))) {
            String timestamp = LocalDateTime.now().toString();
            writer.write(timestamp + " - " + action + ": " + details);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to audit log: " + e.getMessage());
        }
    }
}
