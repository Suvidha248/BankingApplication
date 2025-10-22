import java.time.LocalDateTime;

import models.Account;
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
        SavingsAccount a1 = new SavingsAccount("Alice", 1000, 0.04, 500);

        // Test deposit
        a1.deposit(500);
        System.out.println("Balance after deposit: $" + a1.getBalance());

        // Test withdraw (should fail if below minimum)
        a1.withdraw(1200);
        System.out.println("Balance after withdrawal attempt: $" + a1.getBalance());

        // Calculate interest
        a1.calculateInterest();
        System.out.println("Balance after interest: $" + a1.getBalance());

        // Print transaction history
        System.out.println("\nTransaction History:");
        a1.getTransactionHistory().forEach(System.out::println);

	}
	

}
