package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import utils.TransactionType;

public class Transaction {
    
    // Fields
    private String transactionId;
    private TransactionType type;
    private double amount;
    private double balanceAfter;
    private LocalDateTime timestamp;
    private String description;
    
    // Static counter for generating unique transaction IDs
    private static int transactionCounter = 1000;
    
    // Constructor with validation
    public Transaction(String transactionId, TransactionType type, double amount, 
                       double balanceAfter, LocalDateTime timestamp, String description) {
        
        // Validation
        if (transactionId == null || transactionId.trim().isEmpty()) {
            throw new IllegalArgumentException("Transaction ID cannot be null or empty");
        }
        if (type == null) {
            throw new IllegalArgumentException("Transaction type cannot be null");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }
        
        // Assignment
        this.transactionId = transactionId;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.timestamp = timestamp;
        this.description = description;
    }
    
    // Static method to generate unique transaction IDs
    public static String generateTransactionId() {
        return "TXN" + (transactionCounter++);
    }
    
    // Getters (no setters - immutable!)
    public String getTransactionId() {
        return transactionId;
    }
    
    public TransactionType getType() {
        return type;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public double getBalanceAfter() {
        return balanceAfter;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public String getDescription() {
        return description;
    }
    
    // Formatted toString for better readability
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format(
            "Transaction ID: %s | Type: %s | Amount: $%.2f | Balance After: $%.2f | Time: %s | Description: %s",
            transactionId,
            type,
            amount,
            balanceAfter,
            timestamp.format(formatter),
            description != null ? description : "N/A"
        );
    }
}