package lab5;
import java.util.ArrayList;

public class Bank {
	public class Account{
		private int accountNumber;
		private int pin;
		private double balance;
		
		/**
		 * Account's constructor
		 * @param acct - initializes the account number
		 * @param pinNumber - initializes the PIN for this account
		 * @param initBalance - initializes the balance for the account
		 */
		public Account(int acct, int pinNumber, int initBalance){
			accountNumber = acct;
			pin = pinNumber;
			balance = initBalance;
		}
		
		//Getters for the fields
		public int getAccountNumber(){return accountNumber;}
		public int getPIN(){return pin;}
		public double getBal(){return balance;}
		
		/**
		 * modifyBal takes an amount to modify this account's balance by (negative for withdrawals);
		 * it will either detect an overdraw and return false, or make the modification and return true
		 * 
		 * @return - boolean - whether modification to the account was made
		 */
		public boolean modifyBal(double amount){
			if((balance + amount) < 0) return false;
			else{
				balance += amount;
				return true;
			}
		}
	}
	
	//The bank uses a series of accounts; we're using an ArrayList to organize them
	ArrayList<Account> accounts;
	public Bank(){
		accounts = new ArrayList<Account>();
	}
	
	//Adding a new account is not performed through the ATM, but is available to be done
	//through outside sources (used for setting up accounts to test the ATM functionality)
	public void add(int acct, int pinNumber, int initBalance){
		accounts.add(new Account(acct, pinNumber, initBalance));
	}
	/**
	 * getAccount is a helper method - it takes an account number to search for and returns that account;
	 * if no account is found, it returns null
	 * 
	 * @param acctNum - takes the account to search for
	 * @return the matching account, or null
	 */
	public Account getAccount(int acctNum){
		for(Account a : accounts){
			if(a.getAccountNumber() == acctNum) return a;
		}
		return null;
	}
	
	/**
	 * validate takes an account number and PIN, checks for the matching Account, and checks whether the
	 * provided PIN matches the one on the account.  It returns the result of this check.
	 * 
	 * @return - boolean - whether or not the PIN matches the account
	 */
	public boolean validate(int account, int enteredPIN){
		Account acctToValidate = getAccount(account);
		if(acctToValidate == null) return false;
		else{
			if(acctToValidate.getPIN() == enteredPIN) return true;
			else return false;
		}
	}
	
	/**
	 * modify takes an account number and an amount to modify the account by, and forwards it to the Account object;
	 * it returns whatever the Account modifyBal() method returns
	 * 
	 * @return - boolean - whether the modification was successful
	 */
	public boolean modify(int acct, int amount){
		Account acctToModify = getAccount(acct);
		if(acctToModify == null) return false;
		else return acctToModify.modifyBal(amount);
	}
}
