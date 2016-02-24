package lab5;

public class LabFiveRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Bank b = new Bank();
		
		b.add(1234, 6789, 80);
		b.add(6789, 4321, 60);
		
		ATM a = new ATM(b);
		a.start();
		
		System.out.print("Enter Account Number: ");
		
		



	}

}
