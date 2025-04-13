# COSC 212 - Object Oriented Programming II (Group Assignment)

## Overview

The Banking Management Application is a Java-based banking system that provides a full-featured GUI using AWT and Swing components. It supports core banking operations such as account management, transactions, loan management, and reporting, with persistent data storage in text files.

## Features

- **Login and Authentication**: Secure login with password encryption.
- **Dashboard**: Displays account summaries, recent transactions, and charts.
- **Transactions**: Deposit, withdrawal, and transfer functionalities.
- **Account Management**: Create and manage savings and current accounts.
- **Loan Management**: Apply for and manage loans.
- **Reports**: Generate and export transaction reports.
- **Search**: Query transactions by date or amount.
- **Security**: Password hashing, session timeout, and audit logging.
- **Backup**: Daily backups of critical data.

## Project Structure

- **src**: Contains the source code organized into packages:
  - `core`: Core functionalities like backup scheduling, input validation, and interest calculation.
  - `data`: Data management, including file I/O and database operations.
  - `gui`: Graphical user interface components.
- **bin**: Compiled `.class` files.
- **lib**: External libraries (e.g., JFreeChart for charts).
- **backups**: Directory for daily backup files.
- **accounts.txt**: Stores account details.
- **transactions.txt**: Stores transaction history.
- **audit.log**: Logs critical operations.

## Prerequisites

- **Java Development Kit (JDK)**: Version 8 or higher.
- **JFreeChart Library**: Ensure `jfreechart-1.5.3.jar` is in the `lib` directory.

## Compilation and Execution

1. **Compile the Project**:

   ```bash
   javac -d bin -cp lib/jfreechart-1.5.3.jar src/**/*.java
   ```

   This compiles all `.java` files in the `src` directory and outputs `.class` files to the `bin` directory.

2. **Run the Application**:
   ```bash
   java -cp bin:lib/jfreechart-1.5.3.jar src.Main
   ```
   This launches the application starting with the `LoginScreen`.

## Usage

1. **Login**:
   - Use the pre-seeded credentials (e.g., account number: `2054567890`, password: `password`).
2. **Dashboard**:
   - View account summaries, recent transactions, and charts.
3. **Transactions**:
   - Navigate to deposit, withdrawal, or transfer screens to perform transactions.
4. **Account Management**:
   - Create or edit accounts.
5. **Reports**:
   - Generate and export transaction reports.
6. **Loan Management**:
   - Apply for loans and view repayment schedules.
7. **Search**:
   - Query transactions by date or amount.

## Advanced Features

- **Multi-threading**: Used for daily interest calculation and backups.
- **Custom Exceptions**: Handles banking-specific errors.
- **Regex Validation**: Ensures input integrity.
- **Java Date and Time API**: Manages timestamps and scheduling.
- **Collections Framework**: Efficient data management.

## Security

- Passwords are hashed using SHA-256.
- Session timeout after 5 minutes of inactivity.
- Audit logs track critical operations.

## Backup and Restore

- Daily backups are stored in the `backups` directory.
- To restore, extract the desired backup file and replace the corresponding `.txt` files.

## Contribution

- Ensure code adheres to the modular structure.
- Document new features using Javadoc.

## License

This project is for educational purposes under the COSC 212 course.

---

For further details, refer to the `product-description.md` and `product-requirement-documents.md` files.
