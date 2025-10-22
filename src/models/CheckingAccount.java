package models;

import exceptions.InsufficientFundsException;
import utils.TransactionType;

public class CheckingAccount extends Account {
    
    // Fields
    private double overdraftLimit;
    private double transactionFee;
    private int freeTransactionsPerMonth;
    private int transactionsThisMonth;
    
    // Constructor
    public CheckingAccount(String accountHolderName, double initialDeposit, double overdraftLimit) {
		super(accountHolderName, initialDeposit);
		this.overdraftLimit = overdraftLimit;
		this.transactionFee = 1.50;
		this.freeTransactionsPerMonth = 5;
		this.transactionsThisMonth = 0;
	}

	// Override withdraw to allow overdraft
//    @Override
//    public void withdraw(double amount) throws InsufficientFundsException {
//        // Check if withdrawal would exceed overdraft limit
//        // If valid, call super.withdraw()
//        // Increment transactionsThisMonth
//        // Apply fee if over free transaction limit
//    }
    
    // Override deposit to count transactions
    @Override
    public void deposit(double amount) {
        // Call super.deposit()
        // Increment transactionsThisMonth
        // Apply fee if needed
    }
    
    @Override
	public void withdraw(double amount) throws InsufficientFundsException{
        
    	if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
    	// Check if withdrawal would exceed overdraft limit
		if(getBalance()-amount<=overdraftLimit) {
			throw new InsufficientFundsException(amount, getBalance() + overdraftLimit);
		}
		// If valid, call super.withdraw()
		super.withdraw(amount);
		// Increment transactionsThisMonth
		transactionsThisMonth++;
		// Apply fee if over free transaction limit
		if(transactionsThisMonth>freeTransactionsPerMonth) {
			double fee = transactionFee;
			super.withdraw(fee);
			recordTransaction(TransactionType.FEE_DEBIT, fee, "Transaction fee applied");
		}
	}

	// Checking accounts don't earn interest
    @Override
    public void calculateInterest() {
        // Do nothing or print message
    }
    
    // Helper method
    private void applyTransactionFee() {
        // If transactionsThisMonth > freeTransactionsPerMonth
        // Deduct transactionFee from balance
    }
    
    // Getters
    // Add getters for all fields
    
    // toString
    // Override to show checking-specific info
}