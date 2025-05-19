package src.gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class TransactionTableModel extends AbstractTableModel {
    private final String[] columns = {"Type", "Account", "Amount", "Date"};
    private final List<Transaction> transactions;

    public TransactionTableModel(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public int getRowCount() {
        return transactions.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int col) {
        return columns[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        Transaction t = transactions.get(row);
        switch (col) {
            case 0: return t.getType();
            case 1: return t.getAccountNumber();
            case 2: return t.getAmount();
            case 3: return t.getDate();
            default: return null;
        }
    }
}
