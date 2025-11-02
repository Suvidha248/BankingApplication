import java.util.*;
        import models.*;
        import services.*;
        import exceptions.*;

public class BankingApp {

    private static final BankService bank = new BankService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        clearScreen();
        displayWelcome();

        boolean running = true;

        while (running) {
            displayMenu();
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine();

            try {
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1 -> createAccount();
                    case 2 -> viewAccountStatement();
                    case 3 -> searchAccounts();
                    case 4 -> listAllAccounts();
                    case 5 -> deposit();
                    case 6 -> withdraw();
                    case 7 -> transfer();
                    case 8 -> checkBalance();
                    case 9 -> viewTransactions();
                    case 10 -> applyInterest();
                    case 11 -> applyInterestToAll();
                    case 12 -> showStatistics();
                    case 13 -> {
                        displayGoodbye();
                        running = false;
                    }
                    default -> System.out.println("⚠️ Invalid choice. Please select 1-13.");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("❌ Unexpected error: " + e.getMessage());
            }

            if (running) pressEnterToContinue();
        }

        scanner.close();
    }

    // ─────────────────────────────── MENU ───────────────────────────────
    private static void displayWelcome() {
        System.out.println("╔═══════════════════════════════════════════════════╗");
        System.out.println("║           WELCOME TO JAVA BANKING APP             ║");
        System.out.println("║                                                   ║");
        System.out.println("║        Your Complete Banking Solution             ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");
    }

    private static void displayMenu() {
        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║                    MAIN MENU                         ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
        System.out.println("  📁 ACCOUNT MANAGEMENT");
        System.out.println("   1. Create New Account");
        System.out.println("   2. View Account Statement");
        System.out.println("   3. Search Accounts by Name");
        System.out.println("   4. List All Accounts");
        System.out.println("\n  💳 TRANSACTIONS");
        System.out.println("   5. Deposit Money");
        System.out.println("   6. Withdraw Money");
        System.out.println("   7. Transfer Money");
        System.out.println("\n  📊 INQUIRY");
        System.out.println("   8. Check Balance");
        System.out.println("   9. View Transaction History");
        System.out.println("\n  💹 SPECIAL OPERATIONS");
        System.out.println("  10. Apply Interest (Single Account)");
        System.out.println("  11. Apply Interest (All Savings)");
        System.out.println("  12. View Statistics");
        System.out.println("\n  13. Exit");
        System.out.println("────────────────────────────────────────────────────────");
    }

    private static void displayGoodbye() {
        System.out.println("\n╔═══════════════════════════════════════════════════╗");
        System.out.println("║       Thank you for using Java Banking App!      ║");
        System.out.println("║                  Come back soon!                  ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");
    }

    // ─────────────────────────────── OPERATIONS ───────────────────────────────
    private static void createAccount() {
        try {
            System.out.println("\n📘 CREATE ACCOUNT");
            System.out.println("1. Savings Account");
            System.out.println("2. Checking Account");

            System.out.print("Select account type (1-2): ");
            String choice = scanner.nextLine().trim();

            String name = getNonEmptyString("Enter Account Holder Name: ");
            double deposit = getPositiveDouble("Enter Initial Deposit: $");

            String accNum;

            if ("1".equals(choice)) {
                System.out.print("Enter Interest Rate (e.g., 0.04 for 4%): ");
                double rate = Double.parseDouble(scanner.nextLine());
                double minBal = getPositiveDouble("Enter Minimum Balance: $");
                accNum = bank.createSavingsAccount(name, deposit, rate, minBal);
            } else if ("2".equals(choice)) {
                double limit = getPositiveDouble("Enter Overdraft Limit: $");
                accNum = bank.createCheckingAccount(name, deposit, limit);
            } else {
                System.out.println("❌ Invalid selection.");
                return;
            }

            System.out.println("\n✅ Account created successfully!");
            System.out.println("📄 Your Account Number: " + accNum);
            System.out.println("💡 Please save this number for future transactions.");

        } catch (Exception e) {
            System.out.println("❌ Error creating account: " + e.getMessage());
        }
    }

    private static void deposit() {
        try {
            System.out.println("\n💰 DEPOSIT");
            String acc = getNonEmptyString("Enter Account Number: ");
            double amount = getPositiveDouble("Enter Deposit Amount: $");
            bank.deposit(acc, amount);
            System.out.printf("✅ Successfully deposited $%.2f%n", amount);
        } catch (AccountNotFoundException e) {
            System.out.println("❌ " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private static void withdraw() {
        try {
            System.out.println("\n💸 WITHDRAW");
            String acc = getNonEmptyString("Enter Account Number: ");
            double amount = getPositiveDouble("Enter Withdrawal Amount: $");
            bank.withdraw(acc, amount);
            System.out.printf("✅ Successfully withdrew $%.2f%n", amount);
        } catch (AccountNotFoundException | InsufficientFundsException e) {
            System.out.println("❌ " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private static void transfer() {
        try {
            System.out.println("\n🔄 TRANSFER");
            String from = getNonEmptyString("Enter Source Account: ");
            String to = getNonEmptyString("Enter Destination Account: ");
            double amount = getPositiveDouble("Enter Transfer Amount: $");

            // Confirmation
            System.out.printf("\n⚠️  Confirm: Transfer $%.2f from %s to %s? (y/n): ",
                    amount, from, to);
            String confirm = scanner.nextLine().trim().toLowerCase();

            if ("y".equals(confirm) || "yes".equals(confirm)) {
                bank.transfer(from, to, amount);
                System.out.println("✅ Transfer completed successfully!");
            } else {
                System.out.println("❌ Transfer cancelled.");
            }
        } catch (AccountNotFoundException | InsufficientFundsException e) {
            System.out.println("❌ " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private static void checkBalance() {
        try {
            System.out.println("\n💵 CHECK BALANCE");
            String acc = getNonEmptyString("Enter Account Number: ");
            double balance = bank.checkBalance(acc);
            System.out.printf("Current Balance: $%.2f%n", balance);
        } catch (AccountNotFoundException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private static void viewTransactions() {
        try {
            System.out.println("\n📜 TRANSACTION HISTORY");
            String acc = getNonEmptyString("Enter Account Number: ");
            List<Transaction> txns = bank.getTransactionHistory(acc);

            if (txns.isEmpty()) {
                System.out.println("No transactions yet.");
            } else {
                System.out.println("\nTotal Transactions: " + txns.size());
                System.out.println("─────────────────────────────────────────");
                for (int i = 0; i < txns.size(); i++) {
                    System.out.println((i + 1) + ". " + txns.get(i));
                }
            }
        } catch (AccountNotFoundException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private static void applyInterest() {
        try {
            System.out.println("\n💹 APPLY INTEREST");
            String acc = getNonEmptyString("Enter Savings Account Number: ");
            bank.applyInterestToSavings(acc);
            System.out.println("✅ Interest applied successfully!");
        } catch (AccountNotFoundException e) {
            System.out.println("❌ " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private static void applyInterestToAll() {
        System.out.println("\n💹 APPLY INTEREST TO ALL SAVINGS ACCOUNTS");
        bank.applyInterestToAllSavings();
    }

    private static void viewAccountStatement() {
        try {
            System.out.println("\n📄 ACCOUNT STATEMENT");
            String acc = getNonEmptyString("Enter Account Number: ");
            bank.printAccountStatement(acc);
        } catch (AccountNotFoundException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private static void searchAccounts() {
        System.out.println("\n🔍 SEARCH ACCOUNTS");
        System.out.print("Enter name to search: ");
        String searchTerm = scanner.nextLine().trim();

        if (searchTerm.isEmpty()) {
            System.out.println("❌ Search term cannot be empty.");
            return;
        }

        List<Account> results = bank.searchAccountsByName(searchTerm);

        if (results.isEmpty()) {
            System.out.println("No accounts found matching: " + searchTerm);
        } else {
            System.out.println("\nSearch Results (" + results.size() + " found):");
            System.out.println("─────────────────────────────────────────");
            results.forEach(System.out::println);
        }
    }

    private static void listAllAccounts() {
        System.out.println("\n🏦 ALL ACCOUNTS");
        List<Account> all = bank.getAllAccounts();

        if (all.isEmpty()) {
            System.out.println("No accounts in the system yet.");
        } else {
            System.out.println("Total Accounts: " + all.size());
            System.out.println("─────────────────────────────────────────");
            for (int i = 0; i < all.size(); i++) {
                System.out.println((i + 1) + ". " + all.get(i));
            }
        }
    }

    private static void showStatistics() {
        System.out.println("\n📊 BANKING STATISTICS");
        System.out.println("═══════════════════════════════════════════");
        System.out.println("Total Accounts: " + bank.getAccountCount());
        System.out.println("Savings Accounts: " + bank.getSavingsAccounts().size());
        System.out.println("Checking Accounts: " + bank.getCheckingAccounts().size());

        double totalBalance = 0;
        for (Account acc : bank.getAllAccounts()) {
            totalBalance += acc.getBalance();
        }
        System.out.printf("Total Bank Balance: $%.2f%n", totalBalance);
        System.out.println("═══════════════════════════════════════════");
    }

    // ─────────────────────────────── UTILITIES ───────────────────────────────
    private static String getNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("⚠️ This field cannot be empty. Please try again.");
        }
    }

    private static double getPositiveDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine());
                if (value > 0) {
                    return value;
                }
                System.out.println("⚠️ Amount must be positive. Please try again.");
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Invalid number format. Please try again.");
            }
        }
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void pressEnterToContinue() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
        clearScreen();
    }
}
