package src.gui;

public class Transaction {
    private String type;
    private String accountNumber;
    private double amount;
    private String date;

    public Transaction(String type, String accountNumber, double amount, String date) {
        this.type = type;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.date = date;
    }

    public String getType() { return type; }
    public String getAccountNumber() { return accountNumber; }
    public double getAmount() { return amount; }
    public String getDate() { return date; }
}
