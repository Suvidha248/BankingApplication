import java.time.LocalDateTime;

import models.Account;
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
		 Account a1 = new Account("Alice", 1000);
		 System.out.println(a1.getBalance());

	}
	

}
