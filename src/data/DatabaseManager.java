package src.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static final String DATABASE_URL = "jdbc:sqlite:sunflower.db";

    public static Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE_URL);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public static void initializeDatabase() {
        String createAccountsTable = "CREATE TABLE IF NOT EXISTS accounts (\n"
                + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    account_type TEXT NOT NULL,\n"
                + "    name TEXT NOT NULL,\n"
                + "    account_number TEXT NOT NULL UNIQUE,\n"
                + "    balance REAL NOT NULL,\n"
                + "    state TEXT,\n"
                + "    gender TEXT,\n"
                + "    card_type TEXT\n"
                + ");";

        try (Connection conn = connect();
             java.sql.Statement stmt = conn.createStatement()) {
            stmt.execute(createAccountsTable);
            System.out.println("Database initialized successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void migrateAccountsFromFile(String filePath) {
        try (Connection conn = connect();
             java.sql.PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO accounts (account_type, name, account_number, balance) VALUES (?, ?, ?, ?)");
             java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(filePath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                pstmt.setString(1, parts[0]); // account_type
                pstmt.setString(2, parts[1]); // name
                pstmt.setString(3, parts[2]); // account_number
                pstmt.setDouble(4, Double.parseDouble(parts[3])); // balance
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            System.out.println("Accounts migrated successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void syncDatabaseWithTextFile(String filePath) {
        try (Connection conn = connect();
             java.sql.PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT OR IGNORE INTO accounts (account_type, name, account_number, balance) VALUES (?, ?, ?, ?)");
             java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(filePath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                pstmt.setString(1, parts[0]); // account_type
                pstmt.setString(2, parts[1]); // name
                pstmt.setString(3, parts[2]); // account_number
                pstmt.setDouble(4, Double.parseDouble(parts[3])); // balance
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            System.out.println("Database synchronized with text file successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void syncTextFileWithDatabase(String filePath) {
        String query = "SELECT account_type, name, account_number, balance FROM accounts";

        try (Connection conn = connect();
             java.sql.Statement stmt = conn.createStatement();
             java.sql.ResultSet rs = stmt.executeQuery(query);
             java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter(filePath))) {

            while (rs.next()) {
                String line = rs.getString("account_type") + "," +
                              rs.getString("name") + "," +
                              rs.getString("account_number") + "," +
                              rs.getDouble("balance");
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Text file synchronized with database successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        initializeDatabase();
        migrateAccountsFromFile("accounts.txt");
    }
}
