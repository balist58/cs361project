import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.internal.runners.JUnit38ClassRunner;

public class FractionTest {
	Fraction myFraction1= new Fraction(4, 5);
	Fraction myFraction2= new Fraction(3, 5);
	
	Fraction myFraction3= new Fraction(5, 7);
	Fraction myFraction4= new Fraction(2, 7);

	@Test
	public void testAdd() {
		assertEquals(myFraction1.add(myFraction2).toString(), "7 / 5");
		assertEquals(myFraction3.add(myFraction4).toString(), "1");
	}
	
	@Test
	public void testSubtract() {
		assertEquals(myFraction1.subtract(myFraction2).toString(), "1 / 5");
		assertEquals(myFraction3.subtract(myFraction4).toString(), "3 / 7");
		
	}
	
	@Test
	public void testMultiply() {
		assertEquals(myFraction1.multiply(myFraction2).toString(), "12 / 25");
	    assertEquals(myFraction3.multiply(myFraction4).toString(), "10 / 49");
   }
	
	@Test
    public void testDivide() {
		assertEquals(myFraction1.divide(myFraction2).toString(), "4 / 3");
		assertEquals(myFraction3.divide(myFraction4).toString(), "5 / 2");
	
	}

}
