package exceptions;

public class AccountNotFoundException extends Exception {

    public AccountNotFoundException(String accountNumber) {
        super("Account not found: " + accountNumber);
    }

    public AccountNotFoundException(String message, String accountNumber) {
        super(message + " Account: " + accountNumber);
    }
}