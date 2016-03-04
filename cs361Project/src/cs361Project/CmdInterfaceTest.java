package cs361Project;

/**
 * CmdInterfaceTest.java
 * 
 * Driver class for the CmdInterface class. Creates ChronoTimer object to pass to CmdInterface object.
 * @author Matt Balistreri
 */
public class CmdInterfaceTest {

	public static void main(String[] args) {
		ChronoTimerControl ct = new ChronoTimerControl();
		
		CmdInterface test = new CmdInterface(2,ct);
		
		test.go();
	}

}
