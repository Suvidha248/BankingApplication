package models;

import exceptions.InsufficientFundsException;

public class CheckingAccount extends Account {

    // Fields
    private double overdraftLimit;
    private double transactionFee;
    private int freeTransactionsPerMonth;
    private int transactionsThisMonth;

    // Constructor
    public CheckingAccount(String accountHolderName, double initialDeposit, double overdraftLimit) {
        super(accountHolderName, initialDeposit);
        
        // Validation
        if (overdraftLimit < 0) {
            throw new IllegalArgumentException("Overdraft limit cannot be negative");
        }
        
        this.overdraftLimit = overdraftLimit;
        this.transactionFee = 1.50;
        this.freeTransactionsPerMonth = 5;
        this.transactionsThisMonth = 0;
    }

    // Override deposit to count transactions
    @Override
    public void deposit(double amount) {
        super.deposit(amount);
        transactionsThisMonth++;
        applyTransactionFee();
    }

    // Override withdraw to allow overdraft
    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        
        // Check if withdrawal would exceed overdraft limit
        // FIX: Correct logic - check if new balance would be below negative limit
        if (getBalance() - amount < -overdraftLimit) {
            throw new InsufficientFundsException(
                String.format("Withdrawal of $%.2f would exceed overdraft limit. Available: $%.2f",
                             amount, getBalance() + overdraftLimit)
            );
        }
        
        // Allow the withdrawal (even if it makes balance negative)
        super.withdraw(amount);
        transactionsThisMonth++;
        applyTransactionFee();
    }

    // Checking accounts don't earn interest
    @Override
    public void calculateInterest() {
        System.out.println("Checking accounts do not earn interest.");
    }

    // Helper method to apply transaction fees
    private void applyTransactionFee() {
        if (transactionsThisMonth > freeTransactionsPerMonth) {
            // Directly update balance to avoid recursion
            updateBalance(getBalance() - transactionFee);
            
            // Record the fee transaction (if recordTransaction is protected)
            // recordTransaction(TransactionType.FEE_DEBIT, transactionFee, "Transaction fee");
            
            System.out.println(String.format("Transaction fee applied: $%.2f", transactionFee));
        }
    }

    // Reset monthly transaction counter (would be called monthly in real system)
    public void resetMonthlyTransactions() {
        this.transactionsThisMonth = 0;
        System.out.println("Monthly transaction count reset.");
    }

    // Getters
    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public double getTransactionFee() {
        return transactionFee;
    }

    public int getFreeTransactionsPerMonth() {
        return freeTransactionsPerMonth;
    }

    public int getTransactionsThisMonth() {
        return transactionsThisMonth;
    }

    // toString - include parent info
    @Override
    public String toString() {
        return super.toString() + 
               String.format(" | Type: CHECKING | Overdraft: $%.2f | Fee: $%.2f | Free Trans: %d | Used: %d",
                            overdraftLimit, transactionFee, freeTransactionsPerMonth, transactionsThisMonth);
    }
}