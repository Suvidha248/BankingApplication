package models;

import exceptions.InsufficientFundsException;

public class SavingsAccount extends Account{
	
	private double interestRate;
	private double minimumBalance;
	
	public SavingsAccount(String accountHolderName, double initialDeposit, double interestRate, double minimumBalance) {
		super(accountHolderName, initialDeposit);
		this.interestRate = interestRate;
		this.minimumBalance = minimumBalance;
	}

	@Override
	public void withdraw(double amount) throws InsufficientFundsException {
	    // FIRST: Check basic validation (amount > 0)
	    if (amount <= 0) {
	        throw new IllegalArgumentException("Withdrawal amount must be positive");
	    }
	    
	    // SECOND: Check insufficient funds (more important than minimum balance)
	    if (amount > getBalance()) {
	        throw new InsufficientFundsException(amount, getBalance());
	    }
	    
	    // THIRD: Check minimum balance requirement (savings-specific rule)
	    if (getBalance() - amount < minimumBalance) {
	        throw new IllegalArgumentException(
	            String.format("Cannot withdraw $%.2f. Balance would fall below minimum ($%.2f)", 
	                         amount, minimumBalance)
	        );
	    }
	    
	    // All checks passed - proceed with withdrawal
	    super.withdraw(amount);
	}


	@Override
	public void calculateInterest() {
		double interest = getBalance() * interestRate;
	    if (interest > 0) {
	        deposit(interest); 
	        System.out.println(String.format("Interest credited: $%.2f", interest));
	    }
		
	}

	public double getInterestRate() {
		// TODO Auto-generated method stub
		return interestRate;
	}

	public Object getMinimumBalance() {
		// TODO Auto-generated method stub
		return minimumBalance;
	}
	
	
	

}
