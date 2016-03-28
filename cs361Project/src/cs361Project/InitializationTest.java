package cs361Project;


import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

public class InitializationTest 
{
	ChronoTimerControl ct = new ChronoTimerControl();
	CmdInterface ctrl = new CmdInterface(1,ct);
	
	
	/**
	 * TC 1.1
	 */
	@Test
	public void testCommandsWhenOff() {
		ctrl.parseLine("OFF", "11:00:00.0");
		ctrl.parseLine("CONN GATE 1", "11:00:00.0");
		ctrl.parseLine("TIME 12:01:01:0", "11:00:00.0");
		ctrl.parseLine("CONN EYE 2", "11:00:00.0");
		
		assertFalse(ct.isOn());
		assertNotEquals("GATE", ct.getChanSensor(1));
		assertNotEquals("EYE", ct.getChanSensor(2));
		assertNull(ct.getTime());
	}
	
	/**
	 * TC 1.2
	 */
	@Test
	public void testOnWhenOn() {
		ctrl.parseLine("OFF", "11:00:00.0");
		ctrl.parseLine("ON", "11:00:00.0");
		ctrl.parseLine("CONN GATE 1", "11:00:01.0");
		ctrl.parseLine("CONN EYE 2", "11:00:02.0");
		
		assertTrue(ct.isOn());
		assertEquals("GATE", ct.getChanSensor(1));
		assertEquals("EYE", ct.getChanSensor(2));
		
		assertEquals(11,ct.getTime().get(10));
		assertEquals(0,ct.getTime().get(12));
		assertEquals(2,ct.getTime().get(13));
		assertEquals(0,ct.getTime().get(14));
		
		Calendar time = ct.getTime();
		ctrl.parseLine("ON", "11:00:03.0");
		
		assertTrue(ct.isOn());
		assertEquals("GATE", ct.getChanSensor(1));
		assertEquals("EYE", ct.getChanSensor(2));
		
		assertSame(time, ct.getTime());
		assertEquals(11,ct.getTime().get(10));
		assertEquals(0,ct.getTime().get(12));
		assertEquals(3,ct.getTime().get(13));
		assertEquals(0,ct.getTime().get(14));
	}
	
	/**
	 * TC 1.3
	 */
	@Test
	public void testStartRaceWithNoEvent() {
		ctrl.parseLine("OFF", "11:00:00.0");
		ctrl.parseLine("ON", "11:00:00.0");
		
		try {
			ctrl.parseLine("NEWRUN", "11:00:03.0");
			assertTrue(false); //If the previous line succeeds
		} catch (Exception ex) { }
		
		assertNull(ct.getEvent());
		assertNull(ct.getRun());
	}
	
	/**
	 * TC 1.4
	 */
	@Test
	public void testStartTwoRuns() {
		ctrl.parseLine("OFF", "11:00:00.0");
		ctrl.parseLine("ON", "11:00:00.0");
		ctrl.parseLine("EVENT IND", "11:00:03.0");
		ctrl.parseLine("NEWRUN", "11:00:04.0");
		
		assertEquals("IND", ct.getEvent());
		Run r = ct.getRun();
		
		ctrl.parseLine("NEWRUN", "11:00:05.0");
		
		assertSame(r, ct.getRun());
		
	}
	
	/**
	 * TC 1.5
	 */
	@Test
	public void testSetInvalidTime() {
		ctrl.parseLine("OFF", "11:00:00.0");
		ctrl.parseLine("ON", "11:00:00.0");
		ctrl.parseLine("TIME 11:12:03.2", "11:00:03.0");
		
		assertEquals(11,ct.getTime().get(10));
		assertEquals(12,ct.getTime().get(12));
		assertEquals(3,ct.getTime().get(13));
		assertEquals(2,ct.getTime().get(14));

		ctrl.parseLine("TIME 24:12:03.2", "11:12:04.0");
		
		assertEquals(11,ct.getTime().get(10));
		assertEquals(12,ct.getTime().get(12));
		assertEquals(4,ct.getTime().get(13));
		assertEquals(0,ct.getTime().get(14));

		ctrl.parseLine("TIME 11:60:03.2", "11:12:05.0");
		
		assertEquals(11,ct.getTime().get(10));
		assertEquals(12,ct.getTime().get(12));
		assertEquals(5,ct.getTime().get(13));
		assertEquals(0,ct.getTime().get(14));

		ctrl.parseLine("TIME 11:12:60.2", "11:12:05.0");
		
		assertEquals(11,ct.getTime().get(10));
		assertEquals(12,ct.getTime().get(12));
		assertEquals(5,ct.getTime().get(13));
		assertEquals(0,ct.getTime().get(14));

		ctrl.parseLine("TIME 11:12:03.100", "11:12:05.0");
		
		assertEquals(11,ct.getTime().get(10));
		assertEquals(12,ct.getTime().get(12));
		assertEquals(5,ct.getTime().get(13));
		assertEquals(0,ct.getTime().get(14));

		ctrl.parseLine("TIME 11:12:03.100", "11:12:05.0");
		
		assertEquals(11,ct.getTime().get(10));
		assertEquals(12,ct.getTime().get(12));
		assertEquals(5,ct.getTime().get(13));
		assertEquals(0,ct.getTime().get(14));

		ctrl.parseLine("TIME 337:99:1234.12340", "11:12:05.0");
		
		assertEquals(11,ct.getTime().get(10));
		assertEquals(12,ct.getTime().get(12));
		assertEquals(5,ct.getTime().get(13));
		assertEquals(0,ct.getTime().get(14));
		
	}
	
	/**
	 * TC 1.6
	 */
	@Test
	public void testWrongChannel() {
		ctrl.parseLine("OFF", "11:00:00.0");
		ctrl.parseLine("ON", "11:00:00.0");
		ctrl.parseLine("CONN GATE 13", "11:00:01.0");
		ctrl.parseLine("TOGGLE 13", "11:00:01.0");
		ctrl.parseLine("TRIG 13", "11:00:01.0");

		assertTrue(ct.isOn());
		assertNull(ct.getChanSensor(13));
		assertNull(ct.getChanStatus(13));
	}
}

