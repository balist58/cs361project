import static org.junit.Assert.*;

import org.junit.Test;


public class FractionTest2 {
	
	@Test
	public void testSimplify() {
		Fraction f = new Fraction(2, 4);
		f = f.simplify();
		assertEquals(f.toString(), "1 / 2");
	}

}
