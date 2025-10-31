//import models.Account;
//import models.SavingsAccount;
//import models.CheckingAccount;
//import models.Transaction;
//import exceptions.InsufficientFundsException;
//
//public class Main {
//    public static void main(String[] args) {
//        System.out.println("╔════════════════════════════════════════════════════════════════╗");
//        System.out.println("║          BANKING APPLICATION - COMPREHENSIVE TEST              ║");
//        System.out.println("╚════════════════════════════════════════════════════════════════╝");
//        System.out.println();
//
//        // Test Savings Account
//        testSavingsAccount();
//
//        System.out.println("\n" + "=".repeat(70) + "\n");
//
//        // Test Checking Account
//        testCheckingAccount();
//
//        System.out.println("\n" + "=".repeat(70) + "\n");
//
//        // Test Polymorphism
//        testPolymorphism();
//    }
//
//    // ==================== SAVINGS ACCOUNT TESTS ====================
//    private static void testSavingsAccount() {
//        System.out.println("╔════════════════════════════════════════════════════════════════╗");
//        System.out.println("║                    SAVINGS ACCOUNT TEST                        ║");
//        System.out.println("╚════════════════════════════════════════════════════════════════╝");
//        System.out.println();
//
//        try {
//            // Create savings account: 4% interest, $500 minimum balance
//            SavingsAccount savings = new SavingsAccount(
//                "John Doe",
//                1000.00,    // Initial deposit
//                0.04,       // 4% interest rate
//                500.00      // Minimum balance
//            );
//
//            System.out.println("✓ Account Created:");
//            System.out.println(savings);
//            System.out.println();
//
//            // TEST 1: Deposit
//            System.out.println("--- TEST 1: Deposit $500 ---");
//            savings.deposit(500.00);
//            System.out.println("✓ Deposit successful");
//            System.out.println("New Balance: $" + String.format("%.2f", savings.getBalance()));
//            System.out.println();
//
//            // TEST 2: Valid Withdrawal (above minimum balance)
//            System.out.println("--- TEST 2: Withdraw $800 (valid) ---");
//            savings.withdraw(800.00);
//            System.out.println("✓ Withdrawal successful");
//            System.out.println("New Balance: $" + String.format("%.2f", savings.getBalance()));
//            System.out.println();
//
//            // TEST 3: Attempt to violate minimum balance
//            System.out.println("--- TEST 3: Try to withdraw $400 (would violate minimum) ---");
//            try {
//                savings.withdraw(400.00);
//                System.out.println("✗ ERROR: Should have been blocked!");
//            } catch (IllegalArgumentException e) {
//                System.out.println("✓ Correctly blocked: " + e.getMessage());
//            }
//            System.out.println("Balance unchanged: $" + String.format("%.2f", savings.getBalance()));
//            System.out.println();
//
//            // TEST 4: Calculate and apply interest
//            System.out.println("--- TEST 4: Calculate Interest (4% APR) ---");
//            double balanceBefore = savings.getBalance();
//            System.out.println("Balance before interest: $" + String.format("%.2f", balanceBefore));
//            savings.calculateInterest();
//            double balanceAfter = savings.getBalance();
//            double interest = balanceAfter - balanceBefore;
//            System.out.println("Interest earned: $" + String.format("%.2f", interest));
//            System.out.println("New Balance: $" + String.format("%.2f", balanceAfter));
//            System.out.println();
//
//            // TEST 5: Insufficient Funds
//            System.out.println("--- TEST 5: Try to withdraw more than balance ---");
//            try {
//                savings.withdraw(10000.00);
//                System.out.println("✗ ERROR: Should have thrown exception!");
//            } catch (InsufficientFundsException e) {
//                System.out.println("✓ Correctly blocked: " + e.getMessage());
//            }
//            System.out.println();
//
//            // TEST 6: Invalid Operations
//            System.out.println("--- TEST 6: Invalid Operations ---");
//            try {
//                savings.deposit(-100);
//                System.out.println("✗ ERROR: Negative deposit should be blocked!");
//            } catch (IllegalArgumentException e) {
//                System.out.println("✓ Negative deposit blocked: " + e.getMessage());
//            }
//
//            try {
//                savings.withdraw(0);
//                System.out.println("✗ ERROR: Zero withdrawal should be blocked!");
//            } catch (IllegalArgumentException e) {
//                System.out.println("✓ Zero withdrawal blocked: " + e.getMessage());
//            }
//            System.out.println();
//
//            // TEST 7: Transaction History
//            System.out.println("--- TEST 7: Transaction History ---");
//            System.out.println("Total transactions: " + savings.getTransactionHistory().size());
//            System.out.println();
//            System.out.println("Recent Transactions:");
//            int count = 0;
//            for (Transaction txn : savings.getTransactionHistory()) {
//                System.out.println((++count) + ". " + txn);
//            }
//            System.out.println();
//
//            // TEST 8: Account Info
//            System.out.println("--- TEST 8: Final Account Summary ---");
//            System.out.println(savings);
//            System.out.println("Interest Rate: " + (savings.getInterestRate() * 100) + "%");
//            System.out.println("Minimum Balance: $" + String.format("%.2f", savings.getMinimumBalance()));
//
//        } catch (Exception e) {
//            System.out.println("✗ UNEXPECTED ERROR: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    // ==================== CHECKING ACCOUNT TESTS ====================
//    private static void testCheckingAccount() {
//        System.out.println("╔════════════════════════════════════════════════════════════════╗");
//        System.out.println("║                   CHECKING ACCOUNT TEST                        ║");
//        System.out.println("╚════════════════════════════════════════════════════════════════╝");
//        System.out.println();
//
//        try {
//            // Create checking account with $200 overdraft limit
//            CheckingAccount checking = new CheckingAccount(
//                "Jane Smith",
//                500.00,     // Initial deposit
//                200.00      // Overdraft limit
//            );
//
//            System.out.println("✓ Account Created:");
//            System.out.println(checking);
//            System.out.println();
//
//            // TEST 1: Free Transactions (first 5)
//            System.out.println("--- TEST 1: Free Transactions (5 allowed) ---");
//            System.out.println("Transaction 1: Deposit $100");
//            checking.deposit(100.00);
//
//            System.out.println("Transaction 2: Withdraw $50");
//            checking.withdraw(50.00);
//
//            System.out.println("Transaction 3: Deposit $50");
//            checking.deposit(50.00);
//
//            System.out.println("Transaction 4: Withdraw $25");
//            checking.withdraw(25.00);
//
//            System.out.println("Transaction 5: Deposit $25");
//            checking.deposit(25.00);
//
//            System.out.println("✓ Completed 5 free transactions");
//            System.out.println("Current Balance: $" + String.format("%.2f", checking.getBalance()));
//            System.out.println("Transactions used: " + checking.getTransactionsThisMonth() + "/" + checking.getFreeTransactionsPerMonth());
//            System.out.println();
//
//            // TEST 2: Transaction Fee Application
//            System.out.println("--- TEST 2: Transaction with Fee (6th transaction) ---");
//            double balanceBefore = checking.getBalance();
//            System.out.println("Balance before: $" + String.format("%.2f", balanceBefore));
//            checking.deposit(100.00);
//            double balanceAfter = checking.getBalance();
//            System.out.println("Balance after deposit: $" + String.format("%.2f", balanceAfter));
//            double expectedFee = checking.getTransactionFee();
//            System.out.println("Expected fee: $" + String.format("%.2f", expectedFee));
//            System.out.println("Actual change: $" + String.format("%.2f", balanceAfter - balanceBefore - 100.00));
//            System.out.println("Transactions used: " + checking.getTransactionsThisMonth());
//            System.out.println();
//
//            // TEST 3: More fees
//            System.out.println("--- TEST 3: Another transaction with fee ---");
//            balanceBefore = checking.getBalance();
//            checking.withdraw(50.00);
//            balanceAfter = checking.getBalance();
//            System.out.println("Balance: $" + String.format("%.2f", balanceAfter));
//            System.out.println("Fee charged again: $" + String.format("%.2f", expectedFee));
//            System.out.println();
//
//            // TEST 4: Overdraft - Valid (within limit)
//            System.out.println("--- TEST 4: Overdraft (within $200 limit) ---");
//            System.out.println("Current balance: $" + String.format("%.2f", checking.getBalance()));
//            System.out.println("Available with overdraft: $" + String.format("%.2f", checking.getBalance() + checking.getOverdraftLimit()));
//            double largeWithdrawal = checking.getBalance() + 100; // Go $100 into overdraft
//            System.out.println("Attempting to withdraw: $" + String.format("%.2f", largeWithdrawal));
//            checking.withdraw(largeWithdrawal);
//            System.out.println("✓ Overdraft withdrawal successful");
//            System.out.println("New Balance: $" + String.format("%.2f", checking.getBalance()) + " (negative balance allowed)");
//            System.out.println();
//
//            // TEST 5: Overdraft - Exceed Limit
//            System.out.println("--- TEST 5: Try to exceed overdraft limit ---");
//            System.out.println("Current balance: $" + String.format("%.2f", checking.getBalance()));
//            System.out.println("Overdraft limit: $" + String.format("%.2f", checking.getOverdraftLimit()));
//            System.out.println("Maximum overdraft allowed: $" + String.format("%.2f", -checking.getOverdraftLimit()));
//            try {
//                checking.withdraw(200.00); // Would exceed -$200 limit
//                System.out.println("✗ ERROR: Should have been blocked!");
//            } catch (InsufficientFundsException e) {
//                System.out.println("✓ Correctly blocked: " + e.getMessage());
//            }
//            System.out.println();
//
//            // TEST 6: Deposit to recover from negative balance
//            System.out.println("--- TEST 6: Deposit to recover from overdraft ---");
//            System.out.println("Balance before deposit: $" + String.format("%.2f", checking.getBalance()));
//            checking.deposit(200.00);
//            System.out.println("Balance after $200 deposit: $" + String.format("%.2f", checking.getBalance()));
//            System.out.println();
//
//            // TEST 7: Interest (should do nothing)
//            System.out.println("--- TEST 7: Calculate Interest (checking accounts don't earn interest) ---");
//            checking.calculateInterest();
//            System.out.println();
//
//            // TEST 8: Reset Monthly Transactions
//            System.out.println("--- TEST 8: Reset Monthly Transaction Counter ---");
//            System.out.println("Transactions this month (before reset): " + checking.getTransactionsThisMonth());
//            checking.resetMonthlyTransactions();
//            System.out.println("Transactions this month (after reset): " + checking.getTransactionsThisMonth());
//            System.out.println();
//
//            // TEST 9: Transaction History
//            System.out.println("--- TEST 9: Transaction History ---");
//            System.out.println("Total transactions: " + checking.getTransactionHistory().size());
//            System.out.println();
//            System.out.println("Last 5 Transactions:");
//            var transactions = checking.getTransactionHistory();
//            int start = Math.max(0, transactions.size() - 5);
//            for (int i = start; i < transactions.size(); i++) {
//                System.out.println((i + 1) + ". " + transactions.get(i));
//            }
//            System.out.println();
//
//            // TEST 10: Final Account Summary
//            System.out.println("--- TEST 10: Final Account Summary ---");
//            System.out.println(checking);
//            System.out.println("Overdraft Limit: $" + String.format("%.2f", checking.getOverdraftLimit()));
//            System.out.println("Transaction Fee: $" + String.format("%.2f", checking.getTransactionFee()));
//            System.out.println("Free Transactions/Month: " + checking.getFreeTransactionsPerMonth());
//
//        } catch (Exception e) {
//            System.out.println("✗ UNEXPECTED ERROR: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    // ==================== POLYMORPHISM TEST ====================
//    private static void testPolymorphism() {
//        System.out.println("╔════════════════════════════════════════════════════════════════╗");
//        System.out.println("║                     POLYMORPHISM TEST                          ║");
//        System.out.println("╚════════════════════════════════════════════════════════════════╝");
//        System.out.println();
//
//        try {
//            // Create array of Account references
//            Account[] accounts = new Account[2];
//
//            accounts[0] = new SavingsAccount("Alice Johnson", 2000.00, 0.05, 1000.00);
//            accounts[1] = new CheckingAccount("Bob Williams", 1500.00, 300.00);
//
//            System.out.println("--- Testing Polymorphism ---");
//            System.out.println("Created 2 accounts of different types stored in Account[] array");
//            System.out.println();
//
//            // Process all accounts polymorphically
//            for (int i = 0; i < accounts.length; i++) {
//                System.out.println("Account " + (i + 1) + ":");
//                System.out.println(accounts[i]); // Calls overridden toString()
//
//                // Deposit (same method, different implementations)
//                accounts[i].deposit(100.00);
//                System.out.println("After $100 deposit: $" + String.format("%.2f", accounts[i].getBalance()));
//
//                // Calculate interest (different behavior for each type)
//                System.out.print("Calculate interest: ");
//                accounts[i].calculateInterest(); // Polymorphic call
//                System.out.println("Balance after interest: $" + String.format("%.2f", accounts[i].getBalance()));
//
//                System.out.println();
//            }
//
//            System.out.println("✓ Polymorphism working correctly!");
//            System.out.println("  - Same method calls (deposit, calculateInterest)");
//            System.out.println("  - Different behaviors based on actual object type");
//            System.out.println("  - Code works without knowing specific account type");
//
//        } catch (Exception e) {
//            System.out.println("✗ ERROR: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//}
import services.BankService;
import exceptions.*;

public class Main {
    public static void main(String[] args) {
        BankService bank = new BankService();

        String acc1 = bank.createSavingsAccount("Alice", 1000, 0.03, 500);
        String acc2 = bank.createCheckingAccount("Bob", 500, 200);

        System.out.println("Created Accounts:");
        System.out.println("Alice -> " + acc1);
        System.out.println("Bob -> " + acc2);

        try {
            bank.deposit(acc1, 300);
            bank.withdraw(acc2, 100);
            bank.transfer(acc1, acc2, 200);

            System.out.println("Alice Balance: $" + bank.checkBalance(acc1));
            System.out.println("Bob Balance: $" + bank.checkBalance(acc2));

        } catch (AccountNotFoundException | InsufficientFundsException e) {
            System.out.println(e.getMessage());
        }
    }
}
