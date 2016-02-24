package lab5;
import java.util.ArrayList;

public class Bank {
	public class Account{
		private int accountNumber;
		private int pin;
		private double balance;
		
		public Account(int acct, int pinNumber, int initBalance){
			accountNumber = acct;
			pin = pinNumber;
			balance = initBalance;
		}
		
		public int getAccountNumber(){return accountNumber;}
		public int getPIN(){return pin;}
		public double getBal(){return balance;}
		
		public boolean modifyBal(double amount){
			if((balance + amount) < 0) return false;
			else{
				balance += amount;
				return true;
			}
		}
	}
	

	ArrayList<Account> accounts;
	public Bank(){
		accounts = new ArrayList<Account>();
	}
	
	public void add(int acct, int pinNumber, int initBalance){
		accounts.add(new Account(acct, pinNumber, initBalance));
	}
	public Account getAccount(int acctNum){
		for(Account a : accounts){
			if(a.getAccountNumber() == acctNum) return a;
		}
		return null;
	}
	
	public boolean validate(int account, int enteredPIN){
		Account acctToValidate = getAccount(account);
		if(acctToValidate == null) return false;
		else{
			if(acctToValidate.getPIN() == enteredPIN) return true;
			else return false;
		}
	}
	
	public boolean modify(int acct, int amount){
		Account acctToModify = getAccount(acct);
		if(acctToModify == null) return false;
		else return acctToModify.modifyBal(amount);
	}
}
