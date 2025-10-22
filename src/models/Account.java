package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import exceptions.InsufficientFundsException;
import utils.TransactionType;

public abstract class Account {
	
	 private final String accountNumber;
	 private String accountHolderName;
	 private double balance;
	 private final List<Transaction> transactionHistory;
	 private LocalDateTime dateCreated;
	 
	 public Account(String accountHolderName, double initialDeposit) {
		if(initialDeposit<=0) {
			throw new IllegalArgumentException("Initial deposit can't be Negative");
		}
		
		this.accountNumber = generateAccountNumber();
		this.accountHolderName = accountHolderName;
		this.balance = initialDeposit;
		this.transactionHistory = new ArrayList<>();
		this.dateCreated = dateCreated;
		
		recordTransaction(TransactionType.DEPOSIT, initialDeposit, "Initial Deposit");
	 }
	 protected void recordTransaction(TransactionType type, double amount, String description) {
		    
		 Transaction txn = new Transaction(type, amount, balance, description);
		    transactionHistory.add(txn);

		
	 }
	 
	 protected void updateBalance(double newBalance) {
		    this.balance = newBalance;
	 }
	 
	 private static int nextAccountNumber = 2486;

	 private String generateAccountNumber() {
	    nextAccountNumber++; // increment for every new account
	    return "ACCT-" + nextAccountNumber;
	 }
	 
	 public void deposit(double amount)
	 {
		 if(amount<=0) {
			 throw new IllegalArgumentException("Deposit should be greater than zero");
		 }
		 balance+=amount;
		 recordTransaction(TransactionType.DEPOSIT, amount, "Deposit");
		 
	 }
	 
	 public void withdraw(double amount) throws InsufficientFundsException
	 {
		 if(amount<=0) {
			 throw new IllegalArgumentException("withdrawal should be greater than zero");
		 }
		 if(amount>balance) {
			 throw new IllegalArgumentException("Insufficiant Balance");
		 }
		 balance-=amount;
		 recordTransaction(TransactionType.WITHDRAWAL, amount, "Withdraw");
		 
	 }
	public String getAccountNumber() {
		return accountNumber;
	}
	public String getAccountHolderName() {
		return accountHolderName;
	}
	public double getBalance() {
		return balance;
	}
	public List<Transaction> getTransactionHistory() {
		return transactionHistory;
	}

    @Override
    public String toString() {
        return String.format(
            "Account[%s] - %s | Balance: $%.2f",
            accountNumber,
            accountHolderName,
            balance
        );
    }

    public abstract void calculateInterest();
	 
	 

}
