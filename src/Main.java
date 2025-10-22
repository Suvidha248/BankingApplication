import java.time.LocalDateTime;

import exceptions.InsufficientFundsException;
import models.Account;
import models.CheckingAccount;
import models.SavingsAccount;
import models.Transaction;
import utils.TransactionType;

public class Main {

	public static void main(String[] args) {
		
//		Transaction t1= new Transaction(
//				Transaction.generateTransactionId(),
//				TransactionType.DEPOSIT,
//				500.00,
//				1500.00,
//				LocalDateTime.now(),
//				"Initial Deposit"
//				);
//        System.out.println(t1);
//        System.out.println();																			
//				
//      
//	Transaction t2= new Transaction(
//				Transaction.generateTransactionId(),
//				TransactionType.WITHDRAWAL,
//				200.00,
//				1300.00,
//				LocalDateTime.now(),
//				"ATM Withdrawal"
//				);
//    	System.out.println(t2);
//    	System.out.println();
//    	
//        try {
//            Transaction t3 = new Transaction(
//                "",  // Empty ID - should fail!
//                TransactionType.DEPOSIT,
//                100.00,
//                1400.00,
//                LocalDateTime.now(),
//                "Test"
//            );
//        } catch (IllegalArgumentException e) {
//            System.out.println("✓ Validation working! Error caught: " + e.getMessage());
//        }
        // Create a SavingsAccount object
	try {
        SavingsAccount a1 = new SavingsAccount("Alice", 1000, 0.04, 500);
        CheckingAccount checking = new CheckingAccount("Jane Smith", 500.00, 200.00);

        // Test deposit
        a1.deposit(500);
        System.out.println("Balance after deposit: $" + a1.getBalance());

        // Test withdraw (should fail if below minimum)
//        a1.withdraw(1200);
//        System.out.println("Balance after withdrawal attempt: $" + a1.getBalance());

        // Calculate interest
        a1.calculateInterest();
        System.out.println("Balance after interest: $" + a1.getBalance());

        // Print transaction history
        System.out.println("\nTransaction History:");
        a1.getTransactionHistory().forEach(System.out::println);
        
       
        System.out.println(a1);
        System.out.println(checking);
        System.out.println();
        
        // Test multiple transactions (first 5 are free)
        System.out.println("=== Testing Free Transactions ===");
        checking.deposit(100);  // Trans 1
        checking.withdraw(50);  // Trans 2
        checking.deposit(50);   // Trans 3
        checking.withdraw(25);  // Trans 4
        checking.deposit(25);   // Trans 5
        System.out.println("Balance: $" + checking.getBalance());
        System.out.println("Transactions used: " + checking.getTransactionsThisMonth());
        System.out.println();
        
        // 6th transaction - should incur fee
        System.out.println("=== Testing Transaction Fee ===");
        checking.deposit(10);  // Trans 6 - $1.50 fee
        System.out.println("Balance: $" + checking.getBalance());
        System.out.println();
        
        // Test overdraft (balance ~608.50, can go to -200)
        System.out.println("=== Testing Overdraft ===");
        checking.withdraw(700);  // Should work (leaves balance at ~-91.50)
        System.out.println("Balance: $" + checking.getBalance());
        System.out.println();
        
        // Test overdraft limit exceeded
        try {
            checking.withdraw(200);  // Should fail (would exceed -$200 limit)
        } catch (InsufficientFundsException e) {
            System.out.println("✓ Overdraft limit protection: " + e.getMessage());
        }
        System.out.println();
        
        // Show all transactions
        System.out.println("=== Transaction History ===");
        checking.getTransactionHistory().forEach(System.out::println);
        
    } catch (InsufficientFundsException e) {
        System.out.println("Error: " + e.getMessage());
    }
	}

}
	

