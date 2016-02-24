package lab5;

import java.util.Scanner;

public class LabFiveRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		Bank b = new Bank();
		
		b.add(1234, 6789, 80);
		b.add(6789, 4321, 60);
		
		ATM a = new ATM(b);
		a.start();
		
		
		/*int accnum = 0;
		int pin = 0;
		double amount = 0.0;
		char cmd;
		
		Scanner sin = new Scanner(System.in);
		
		System.out.print("Enter Account Number: ");
		accnum = sin.nextInt();
		System.out.println();
		
		if(a.accountExists(accnum));{
			System.out.print("Insert 'W' for Withdrawl or 'D' for Deposit: ");
			cmd = (char)sin.nextByte();
			System.out.println();
			
			
			System.out.print("Insert account PIN: ");
			pin = sin.nextInt();
			System.out.println();
			
			if(a.getAccount(accnum, pin)){
				String cmdstring;
				
				if(cmd == 'W')
					cmdstring = "Withdraw";
				else
					cmdstring = "Deposit";
				
				System.out.print("Enter amount to " + cmdstring + ": ");
			}*/
		
		



	}

}
