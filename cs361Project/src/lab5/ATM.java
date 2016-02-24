package lab5;

import java.util.Scanner;

public class ATM 
{
	Bank bank;
	int acct;
	int pin;
	char action;
	Scanner stdIn = new Scanner(System.in);
	
	public ATM(Bank b) 
	{
		bank = b;
	}
	
	public void start(){
		boolean accountValidated = false;
		do{
			System.out.print("Please enter an account number: ");
			acct = stdIn.nextInt();
			System.out.print("Please enter your PIN: ");
			pin = stdIn.nextInt();
			
			accountValidated = bank.validate(acct,  pin);
		}while(!accountValidated);
		
		do{
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
