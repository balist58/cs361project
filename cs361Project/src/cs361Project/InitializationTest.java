package cs361Project;


import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

public class InitializationTest 
{
	ChronoTimerControl ct = new ChronoTimerControl();
	CmdInterface ctrl = new CmdInterface(1,ct);
	ChronoTimerSystem system = ct.getSystem();
	
	/**
	 * TC 1.1
	 */
	@Test
	public void testCommandsWhenOff() {
		ct.execute("OFF");
		ct.execute("CONN GATE 1");
		ct.execute("TIME 12:01:01:0");
		ct.execute("CONN EYE 2");
		
		assertFalse(ct.isEnabled());
		assertEquals("GATE", system.getChannel(1).getSensor());
		assertEquals("EYE", system.getChannel(2).getSensor());
		assertNotEquals(12,system.getTime().get(10));
		assertNotEquals(1,system.getTime().get(12));
		assertNotEquals(1,system.getTime().get(13));
		assertNotEquals(0,system.getTime().get(14));
	}
	
	/**
	 * TC 1.2
	 */
	@Test
	public void testOnWhenOn() {
		ct.execute("OFF");
		ct.execute("ON");
		ct.execute("CONN GATE 1");
		ct.execute("CONN EYE 2");
		system.setTime("11:00:02.0");
		
		assertTrue(ct.isEnabled());
		assertEquals("GATE", system.getChannel(1).getSensor());
		assertEquals("EYE", system.getChannel(2).getSensor());
		
		assertEquals(11,system.getTime().get(10));
		assertEquals(0,system.getTime().get(12));
		assertEquals(2,system.getTime().get(13));
		assertEquals(0,system.getTime().get(14));
		
		Calendar time = system.getTime();
		ct.execute("ON");
		system.setTime("11:00:03.0");
		
		assertTrue(ct.isEnabled());
		assertEquals("GATE", system.getChannel(1).getSensor());
		assertEquals("EYE", system.getChannel(2).getSensor());
		
		assertSame(time, system.getTime());
		assertEquals(11,system.getTime().get(10));
		assertEquals(0,system.getTime().get(12));
		assertEquals(3,system.getTime().get(13));
		assertEquals(0,system.getTime().get(14));
	}
	
	/**
	 * TC 1.3
	 */
	@Test
	public void testStartRaceWithNoEvent() {
		ct.execute("OFF");
		ct.execute("ON");
		
		ct.execute("NEWRUN");

		assertEquals("IND", system.getEvent());
		assertNotNull(system.getRun());
	}
	
	/**
	 * TC 1.4
	 */
	@Test
	public void testStartTwoRuns() {
		ct.execute("OFF");
		ct.execute("ON");
		ct.execute("EVENT IND");
		ct.execute("NEWRUN");
		
		assertEquals("IND", system.getEvent());
		Run r = system.getRun();
		
		ct.execute("NEWRUN");
		
		assertSame(r, system.getRun());
		
	}
	
	/**
	 * TC 1.5
	 */
	@Test
	public void testSetInvalidTime() {
		ct.execute("OFF");
		ct.execute("ON");
		ct.execute("TIME 11:12:03.2");
		
		assertEquals(11,system.getTime().get(10));
		assertEquals(12,system.getTime().get(12));
		assertEquals(3,system.getTime().get(13));
		assertEquals(2,system.getTime().get(14));

		ct.execute("TIME 24:12:03.2");
		
		assertEquals(11,system.getTime().get(10));
		assertEquals(12,system.getTime().get(12));
		assertEquals(3,system.getTime().get(13));
		assertEquals(2,system.getTime().get(14));

		ct.execute("TIME 11:60:03.2");
		
		assertEquals(11,system.getTime().get(10));
		assertEquals(12,system.getTime().get(12));
		assertEquals(3,system.getTime().get(13));
		assertEquals(2,system.getTime().get(14));

		ct.execute("TIME 11:12:60.2");
		
		assertEquals(11,system.getTime().get(10));
		assertEquals(12,system.getTime().get(12));
		assertEquals(3,system.getTime().get(13));
		assertEquals(2,system.getTime().get(14));

		ct.execute("TIME 11:12:03.100");
		
		assertEquals(11,system.getTime().get(10));
		assertEquals(12,system.getTime().get(12));
		assertEquals(3,system.getTime().get(13));
		assertEquals(2,system.getTime().get(14));

		ct.execute("TIME 11:12:03.100");
		
		assertEquals(11,system.getTime().get(10));
		assertEquals(12,system.getTime().get(12));
		assertEquals(3,system.getTime().get(13));
		assertEquals(2,system.getTime().get(14));

		ct.execute("TIME 337:99:1234.12340");
		
		assertEquals(11,system.getTime().get(10));
		assertEquals(12,system.getTime().get(12));
		assertEquals(3,system.getTime().get(13));
		assertEquals(2,system.getTime().get(14));
		
	}
	
	/**
	 * TC 1.6
	 */
	@Test
	public void testWrongChannel() {
		ct.execute("OFF");
		ct.execute("ON");
		ct.execute("CONN GATE 13");
		ct.execute("TOGGLE 13");
		ct.execute("TRIG 13");

		assertTrue(ct.isEnabled());
		assertNull(system.getChannel(13));
	}
}

