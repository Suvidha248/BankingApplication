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
        // 1. Get both accounts
        Account source=getAccount(fromAccount);
        Account destination=getAccount(toAccount);
        // 2. Withdraw from source
        // 3. Try to deposit to destination
        try{
            source.withdraw(amount);
            destination.deposit(amount);
        }catch (InsufficientFundsException e){
            System.out.println("Transfer Failed..refund source");
            source.deposit(amount);
            throw e;
        }
        // 4. If deposit fails, rollback (refund source)
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
    public List<Transaction> getTransactionHistory(String accountNumber) throws Exception
    // Return account.getTransactionHistory()
        account=getAccount(accountNumber);
        return

    public void applyInterestToSavings(String accountNumber) throws Exception
    // Get account, check if SavingsAccount, apply interest
}