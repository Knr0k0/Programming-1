package model;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import service.*;
import static main.Main.*;

public class SalesTransaction {
    private String transactionId; // formatted as t-number
    private String transactionDate;
    private String clientId;
    private String salespersonId;
    private List<String> purchasedItems; // cars or parts
    private float discount;
    private float totalAmount;
    private String notes;

    // Constructor
    public SalesTransaction(String transactionId, String transactionDate, String clientId,
                             String salespersonId, List<String> purchasedItems, float discount,
                             float totalAmount, String notes) {
        if (!transactionId.matches("t-\\d+")) {
            throw new IllegalArgumentException("Part ID must be in the format p-number");
        }
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
        this.clientId = clientId;
        this.salespersonId = salespersonId;
        this.purchasedItems = purchasedItems;
        this.discount = discount;
        this.totalAmount = totalAmount;
        this.notes = notes;
    }

    // Getters and setters for all fields, including sale-specific fields

    public String getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionDate() {
        return transactionDate;
    }
    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSalespersonId() {
        return salespersonId;
    }
    public void setSalespersonId(String salespersonId) {
        this.salespersonId = salespersonId;
    }

    public List<String> getPurchasedItems() {
        return purchasedItems;
    }
    public void setPurchasedItems(List<String> purchasedItems) {
        this.purchasedItems = purchasedItems;
    }

    public double getDiscount() {
        return discount;
    }
    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        String formattedTotalAmount = numberFormat.format(totalAmount);

        return "transactionId: " + transactionId + '\n' +
                ", transactionDate: " + transactionDate + '\n' +
                ", clientId: " + clientId + '\n' +
                ", salespersonId: " + salespersonId + '\n' +
                ", purchasedItems: " + purchasedItems + '\n' +
                ", discount: " + discount + '\n' +
                ", totalAmount: " + formattedTotalAmount + " VND" + '\n' +
                ", notes: " + notes + '\n';
    }

    // Transactions Menu
    public static void transactionMenu(Scanner scanner, TransactionService transactionService) {
        while (true) {
            System.out.println("--- Transactions Menu ---");
            System.out.println("1. Add Transactions");
            System.out.println("2. Update Transactions");
            System.out.println("3. Delete Transactions");
            System.out.println("4. View All Transactions");
            System.out.println("5. Back");
            System.out.println("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addTransactions(scanner, transactionService);
                case 2 -> updateTransactions(scanner, transactionService);
                case 3 -> deleteTransactions(scanner, transactionService);
                case 4 -> viewAllTransactions(transactionService);
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    // Method to add transactions
    private static void addTransactions(Scanner scanner, TransactionService transactionService) {
        String transactionId = promptForTransactionId(scanner);
        String transactionDate = promptForNonEmptyString(scanner, "Enter Transaction Date: ");
        String clientId = promptForNonEmptyString(scanner, "Enter Client ID: ");
        String salespersonId = promptForNonEmptyString(scanner, "Enter Salesperson ID: ");
        List<String> purchasedItems = promptForPurchasedItems(scanner);
        double discount = promptForPositiveDouble(scanner, "Enter Discount: ");
        double totalAmount = promptForPositiveDouble(scanner, "Enter Total Amount: ");
        String notes = promptForNonEmptyString(scanner, "Enter Notes: ");

        SalesTransaction newTransaction = new SalesTransaction(transactionId, transactionDate, clientId, salespersonId, purchasedItems, (float) discount, (float) totalAmount, notes);
        transactionService.addTransaction(newTransaction);
        System.out.println("Transaction added successfully!");
    }


    // Method to prompt for transactions ID
    private static String promptForTransactionId(Scanner scanner) {
        String transactionId;
        while (true) {
            System.out.println("Enter Transaction ID (format t-number): ");
            transactionId = scanner.nextLine();
            if (transactionId.matches("t-\\d+")) {
                break;
            } else {
                System.out.println("Invalid Transaction ID format. Please enter in the format t-number.");
            }
        }
        return transactionId;
    }

    // Method to update a transactions
    private static void updateTransactions(Scanner scanner, TransactionService transactionService) {
        String transactionId = promptForNonEmptyString(scanner, "Enter Transaction ID to update: ");
        SalesTransaction existingTransaction = transactionService.getTransactionById(transactionId);

        if (existingTransaction != null) {
            String transactionDate = promptForOptionalString(scanner, "Enter Transaction Date (" + existingTransaction.getTransactionDate() + "): ", existingTransaction.getTransactionDate());
            String clientId = promptForOptionalString(scanner, "Enter Client ID (" + existingTransaction.getClientId() + "): ", existingTransaction.getClientId());
            String salespersonId = promptForOptionalString(scanner, "Enter Salesperson ID (" + existingTransaction.getSalespersonId() + "): ", existingTransaction.getSalespersonId());
            List<String> purchasedItems = promptForPurchasedItems(scanner);
            double discount = promptForOptionalDouble(scanner, "Enter Discount (" + existingTransaction.getDiscount() + "): ", existingTransaction.getDiscount());
            double totalAmount = promptForOptionalDouble(scanner, "Enter Total Amount (" + existingTransaction.getTotalAmount() + "): ", existingTransaction.getTotalAmount());
            String notes = promptForOptionalString(scanner, "Enter Notes (" + existingTransaction.getNotes() + "): ", existingTransaction.getNotes());

            existingTransaction.setTransactionDate(transactionDate);
            existingTransaction.setClientId(clientId);
            existingTransaction.setSalespersonId(salespersonId);
            existingTransaction.setPurchasedItems(purchasedItems);
            existingTransaction.setDiscount((float) discount);
            existingTransaction.setTotalAmount((float) totalAmount);
            existingTransaction.setNotes(notes);

            transactionService.updateTransaction(existingTransaction);
            System.out.println("Transaction updated successfully!");
        } else {
            System.out.println("Transaction not found!");
        }
    }

    // Method to delete a transactions
    private static void deleteTransactions(Scanner scanner, TransactionService transactionService) {
        String transactionId = promptForNonEmptyString(scanner, "Enter Transaction ID to delete: ");
        transactionService.deleteTransaction(transactionId);
        System.out.println("Transaction deleted successfully!");
    }

    // Method to view all transactions
    private static void viewAllTransactions(TransactionService transactionService) {
        List<SalesTransaction> transactions = transactionService.getAllTransactions();
        if (transactions.isEmpty()) {
            System.out.println("No transactions available.");
        } else {
            for (SalesTransaction transaction : transactions) {
                System.out.println("--------------------------------");
                System.out.print(transaction);
            }
        }
    }
}
