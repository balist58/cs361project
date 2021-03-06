package lab5;

import java.util.Scanner;

public class ATM 
{
	Bank bank;
	int acct;
	int pin;
	char action;
	Scanner stdIn = new Scanner(System.in);
	
	/**
	 * The ATM constructor specifies the bank being used - in later cases, this could be linked up to the account, but
	 * for simplicity's sake, we're linking up the bank to the ATM first
	 */
	public ATM(Bank b) 
	{
		bank = b;
	}
	
	/**
	 * Start() runs a pair of validation loops - the account validation, which prompts for acct and pin numbers and validates them,
	 * and an "action loop" which prompts for Deposit or Withdrawal actions (or can quit the loop with any other entry)
	 */
	public void start(){
		boolean accountValidated = false;
		do{
			System.out.print("Please enter an account number: ");
			acct = stdIn.nextInt();
			System.out.print("Please enter your PIN: ");
			pin = stdIn.nextInt();
			
			accountValidated = bank.validate(acct,  pin);
			if(!accountValidated) System.out.println("Sorry, the PIN was incorrect.  Please try again.");
		}while(!accountValidated);
		
		do{
			System.out.println("Welcome to account "+ acct);
			System.out.println("Your current balance is $" + bank.getAccount(acct).getBal());
			System.out.println("------------------------------");
			System.out.println();
			System.out.println("Please select an action:");
			System.out.println("W  for Withdrawal");
			System.out.println("D for Deposit");
			System.out.println("Any other key to quit: ");
			String response = stdIn.next();
			action = response.charAt(0);
			System.out.println("");
			
			if(action == 'D' || action == 'W') modify(acct);
		}while(action != 'D' && action != 'W');
	}
	
	
	/**
	 * Modify is a helper method that takes the active account as a parameter, and prompts the user to deposit or withdraw from the account,
	 * performs the action (if possible), and prints back the resultant balance when done
	 */
	public void modify(int account){
		if(action == 'D'){
			int dep;
			System.out.print("Select the number of dollars to deposit: ");
			dep = stdIn.nextInt();
			bank.modify(acct, dep);
			System.out.println();
			System.out.println("Your new account balance is $" + bank.getAccount(acct).getBal());
		}
		else{
			int with;
			boolean success;
			System.out.print("Select the number of dollars to withdraw: ");
			with = stdIn.nextInt();
			with *= (-1);
			success = bank.modify(acct, with);
			System.out.println();
			if(success) System.out.println("Your new account balance is $" + bank.getAccount(acct).getBal());
			else System.out.println("Sorry, your account only has $" + bank.getAccount(acct).getBal() + ", you cannot withdraw that much money.");
		}
	}
}
