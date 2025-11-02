package services;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import models.Account;
import models.SavingsAccount;
import models.CheckingAccount;
import exceptions.AccountNotFoundException;
import exceptions.InsufficientFundsException;
import models.Transaction;

public class BankService {

    // Fields
    private Map<String, Account> accounts;
    private static int accountCounter;

    // Constructor
    public BankService() {
        // Initialize accounts Map
        accounts=new HashMap<>();
        accountCounter=1000;
    }

    // Helper method to generate unique account numbers
    private String generateAccountNumber() {
        return "ACC" + (++accountCounter);
    }

    // Create Savings Account
    public String createSavingsAccount(String holderName, double initialDeposit,
                                       double interestRate, double minimumBalance) {
        // 1. Create new SavingsAccount
        String accountNumber = generateAccountNumber();
        SavingsAccount account=new SavingsAccount(holderName,initialDeposit,interestRate,minimumBalance);
        // 2. Get its account number
        // 3. Store in Map
        accounts.put(accountNumber, account);
        // 4. Return account number
        return accountNumber;
    }

    // Create Checking Account
    public String createCheckingAccount(String holderName, double initialDeposit,
                                        double overdraftLimit) {
        // Similar to createSavingsAccount
        String accountNumber = generateAccountNumber();
        CheckingAccount account=new CheckingAccount(holderName, initialDeposit, overdraftLimit);
        accounts.put(accountNumber,account);
        return accountNumber;
    }

    // Get Account (with exception handling)
    public Account getAccount(String accountNumber) throws AccountNotFoundException {
        // 1. Check if accounts.containsKey(accountNumber)
        if(!accounts.containsKey(accountNumber))
        {
            throw new AccountNotFoundException(accountNumber);
        }
        // 2. If not → throw new AccountNotFoundException(accountNumber)
        // 3. If yes → return accounts.get(accountNumber)
        return accounts.get(accountNumber);
    }

    // Deposit
    public void deposit(String accountNumber, double amount)
            throws AccountNotFoundException {
        // 1. Get account using getAccount()
        Account account=getAccount(accountNumber);
        // 2. Call account.deposit(amount)
        account.deposit(amount);
    }

    // Withdraw
    public void withdraw(String accountNumber, double amount)
            throws AccountNotFoundException, InsufficientFundsException {
        // 1. Get account
        Account account=getAccount(accountNumber);
        // 2. Call account.withdraw(amount)
        account.withdraw(amount);
    }

    // Transfer
    public void transfer(String fromAccount, String toAccount, double amount)
            throws AccountNotFoundException, InsufficientFundsException {

        // Validation
        if (fromAccount.equals(toAccount)) {
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }

        // Get both accounts
        Account source = getAccount(fromAccount);
        Account destination = getAccount(toAccount);

        // Withdraw from source (if this fails, nothing happens - no rollback needed)
        source.withdraw(amount);

        // Try to deposit to destination
        try {
            destination.deposit(amount);
        } catch (Exception e) {
            // Rollback: refund the source account
            source.deposit(amount);
            throw new RuntimeException("Transfer failed during deposit: " + e.getMessage());
        }

        System.out.println(String.format("Transfer successful: $%.2f from %s to %s",
                amount, fromAccount, toAccount));
    }

    // Check Balance
    public double checkBalance(String accountNumber) throws AccountNotFoundException {
        // Get account and return balance
        return getAccount(accountNumber).getBalance();
    }

    // Get All Accounts
    public List<Account> getAllAccounts() {
        // Return new ArrayList<>(accounts.values())
        return new ArrayList<>(accounts.values());
    }

    // Other methods
    // Get Transaction History
    public List<Transaction> getTransactionHistory(String accountNumber)
            throws AccountNotFoundException {
        Account account = getAccount(accountNumber);
        return account.getTransactionHistory();  // Already returns defensive copy
    }

    // Apply Interest to Savings Accounts
    public void applyInterestToSavings(String accountNumber)
            throws AccountNotFoundException {
        Account account = getAccount(accountNumber);

        // Check if it's a SavingsAccount using instanceof
        if (account instanceof SavingsAccount) {
            account.calculateInterest();
            System.out.println("Interest applied to account: " + accountNumber);
        } else {
            throw new IllegalArgumentException(
                    "Account " + accountNumber + " is not a savings account"
            );
        }
    }

    // Apply Interest to ALL Savings Accounts (bonus method!)
    public void applyInterestToAllSavings() {
        int count = 0;
        for (Account account : accounts.values()) {
            if (account instanceof SavingsAccount) {
                account.calculateInterest();
                count++;
            }
        }
        System.out.println("Interest applied to " + count + " savings accounts");
    }

    // Search accounts by holder name
    public List<Account> searchAccountsByName(String searchTerm) {
        List<Account> results = new ArrayList<>();
        String searchLower = searchTerm.toLowerCase();

        for (Account account : accounts.values()) {
            if (account.getAccountHolderName().toLowerCase().contains(searchLower)) {
                results.add(account);
            }
        }
        return results;
    }

    // Print detailed account statement
    public void printAccountStatement(String accountNumber)
            throws AccountNotFoundException {
        Account account = getAccount(accountNumber);

        System.out.println("\n" + "=".repeat(70));
        System.out.println("                    ACCOUNT STATEMENT");
        System.out.println("=".repeat(70));
        System.out.println(account);
        System.out.println("-".repeat(70));

        List<Transaction> transactions = account.getTransactionHistory();
        System.out.println("Transaction History (" + transactions.size() + " transactions):");
        System.out.println();

        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (int i = 0; i < transactions.size(); i++) {
                System.out.println((i + 1) + ". " + transactions.get(i));
            }
        }
        System.out.println("=".repeat(70) + "\n");
    }

    // Get total number of accounts
    public int getAccountCount() {
        return accounts.size();
    }

    // Check if account exists
    public boolean accountExists(String accountNumber) {
        return accounts.containsKey(accountNumber);
    }

    // Get accounts by type
    public List<Account> getSavingsAccounts() {
        List<Account> savingsAccounts = new ArrayList<>();
        for (Account account : accounts.values()) {
            if (account instanceof SavingsAccount) {
                savingsAccounts.add(account);
            }
        }
        return savingsAccounts;
    }

    public List<Account> getCheckingAccounts() {
        List<Account> checkingAccounts = new ArrayList<>();
        for (Account account : accounts.values()) {
            if (account instanceof CheckingAccount) {
                checkingAccounts.add(account);
            }
        }
        return checkingAccounts;
    }

    // Print all accounts with basic details
    public void printAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts found in the system.");
            return;
        }

        System.out.println("\n=== All Accounts Summary ===");
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            Account acc = entry.getValue();
            System.out.printf("Account No: %s | Holder: %s | Type: %s | Balance: $%.2f%n",
                    acc.getAccountNumber(),
                    acc.getAccountHolderName(),
                    acc.getClass().getSimpleName(),
                    acc.getBalance());
        }
    }

}