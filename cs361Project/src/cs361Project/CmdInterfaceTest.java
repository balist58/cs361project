package cs361Project;

import java.util.Scanner;

/**
 * CmdInterfaceTest.java
 * 
 * Driver class for the CmdInterface class. Creates ChronoTimer object to pass to CmdInterface object.
 * @author Matt Balistreri
 */
public class CmdInterfaceTest {

	public static void main(String[] args) {
		ChronoTimerControl ct = new ChronoTimerControl();
		
		Scanner sin1 = new Scanner(System.in);
		int option = 0;
		
		System.out.println("Enter 1 to enter a command via console.");
		System.out.println("Enter 2 to enter a command via text file.");
		System.out.println("Enter 3 to exit.");
		
		option = Integer.parseInt(sin1.nextLine());
		CmdInterface test = new CmdInterface(option,ct);
		
		test.go();
		sin1.close();
	}

}
