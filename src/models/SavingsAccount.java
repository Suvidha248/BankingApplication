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
	public void withdraw(double amount) throws InsufficientFundsException{
		if(getBalance()-amount<minimumBalance) {
			System.out.println("Cannot withdraw: balance would fall below minimum balance!");
		}else {
			super.withdraw(amount);
		}
		
	}


	@Override
	public void calculateInterest() {
		double interest = getBalance() * interestRate;
		
	}
	
	
	

}
