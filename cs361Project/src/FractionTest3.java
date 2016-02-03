import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class FractionTest3 {
	
	Fraction a;
	static Fraction b;
	
	@BeforeClass
	public static void beforeClass() {
		System.out.println("BeforeClass\n");
		b = new Fraction(2,4);
	}
	
	@Before
	public void before() {
		System.out.println("Before");
		a = new Fraction(1,2);
	}
	
	@Test
	public void testEqual() {
		System.out.println("Test Equal");
		assertTrue(a.equals(b));
	}
	
	@Test(expected = ArithmeticException.class)
	public void testDenomZero() {
		Fraction temp = new Fraction(1,0);
		temp.toValue();
	}
	
	@After
	public void after() {
		System.out.println("After\n");
	}
	
	@AfterClass
	public static void afterClass() {
		System.out.println("AfterClass");
	}

}
