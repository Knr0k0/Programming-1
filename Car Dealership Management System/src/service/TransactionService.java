package service;

import model.Car;
import model.SalesTransaction;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransactionService {
    private final List<SalesTransaction> transactions;
    private static final String filePath = "src/database/Transactions.txt"; // Path to the file

    public TransactionService() {
        this.transactions = new ArrayList<>();
        loadTransactionsFromFile(); // Load transactions from file when initialized
    }

    public void addTransaction(SalesTransaction transaction) {
        transactions.add(transaction);
        saveTransactionsToFile(); // Save to file after adding
    }

    public SalesTransaction getTransactionById(String transactionId) {
        return transactions.stream()
                .filter(transaction -> transaction.getTransactionId().equals(transactionId))
                .findFirst()
                .orElse(null);
    }

    public void updateTransaction(SalesTransaction transaction) {
        SalesTransaction existingTransaction = getTransactionById(transaction.getTransactionId());
        if (existingTransaction != null) {
            transactions.remove(existingTransaction);
            transactions.add(transaction);
            saveTransactionsToFile(); // Save to file after updating
        }
    }

    public void deleteTransaction(String transactionId) {
        transactions.removeIf(transaction -> transaction.getTransactionId().equals(transactionId));
        saveTransactionsToFile(); // Save to file after deleting
    }

    public List<SalesTransaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }

    // Load transactions from file
    private void loadTransactionsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    SalesTransaction transaction = new SalesTransaction(
                            parts[0],
                            parts[1],
                            parts[2],
                            parts[3],
                            Arrays.asList(parts[4].split(";")), // Purchased items split by semicolon
                            Float.parseFloat(parts[5]),
                            Float.parseFloat(parts[6]),
                            parts[7]
                    );
                    transactions.add(transaction);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save transactions to file
    private void saveTransactionsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (SalesTransaction transaction : transactions) {
                writer.write(String.format("%s,%s,%s,%s,%s,%.0f,%.0f,%s%n",
                        transaction.getTransactionId(),
                        transaction.getTransactionDate(),
                        transaction.getClientId(),
                        transaction.getSalespersonId(),
                        String.join(";", transaction.getPurchasedItems()), // Join purchased items with semicolon
                        transaction.getDiscount(),
                        transaction.getTotalAmount(),
                        transaction.getNotes()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

