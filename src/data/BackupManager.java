package src.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class BackupManager {

    private static final String BACKUP_DIR = "backups";

    public static void createDailyBackup() {
        try {
            Files.createDirectories(Paths.get(BACKUP_DIR));
            String backupFileName = BACKUP_DIR + "/backup_" + LocalDate.now() + ".zip";
            try (FileOutputStream fos = new FileOutputStream(backupFileName);
                 ZipOutputStream zos = new ZipOutputStream(fos)) {

                addFileToZip("accounts.txt", zos);
                addFileToZip("transactions.txt", zos);
                addFileToZip("loans.txt", zos);
            }
            System.out.println("Backup created: " + backupFileName);
        } catch (IOException e) {
            System.err.println("Error creating backup: " + e.getMessage());
        }
    }

    private static void addFileToZip(String fileName, ZipOutputStream zos) throws IOException {
        File file = new File(fileName);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zos.putNextEntry(zipEntry);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }
                zos.closeEntry();
            }
        }
    }
}
