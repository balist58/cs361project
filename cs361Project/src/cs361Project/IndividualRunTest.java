package cs361Project;


import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

public class IndividualRunTest 
{
	ChronoTimerControl ct = new ChronoTimerControl();
	CmdInterface ctrl = new CmdInterface(1,ct);
	
	/**
	 * TC 2.1
	 */
	@Test
	public void testStartAndFinishWithNoRun() {
		ctrl.parseLine("OFF", "11:00:00.0");
		ctrl.parseLine("ON", "11:00:00.0");
		ctrl.parseLine("CONN GATE 1", "11:00:01.0");
		ctrl.parseLine("CONN EYE 2", "11:00:02.0");
		ctrl.parseLine("TOGGLE 1", "11:00:03.0");
		ctrl.parseLine("TOGGLE 2", "11:00:04.0");
		ctrl.parseLine("EVENT IND", "11:00:05.0");
		ctrl.parseLine("NUM 444", "11:00:05.0");
		
		assertTrue(ct.isOn());
		assertNull(ct.getRun());

		ctrl.parseLine("START", "11:00:07.0");

		assertNull(ct.getRun());
		
		ctrl.parseLine("FINISH", "11:00:18.0");
		
		assertNull(ct.getRun());
		
		assertEquals(11,ct.getTime().get(10));
		assertEquals(0,ct.getTime().get(12));
		assertEquals(18,ct.getTime().get(13));
		assertEquals(0,ct.getTime().get(14));
	}
	
	/**
	 * TC 2.2
	 */
	@Test
	public void testStartWithoutRacers() {
		ctrl.parseLine("OFF", "11:00:00.0");
		ctrl.parseLine("ON", "11:00:00.0");
		ctrl.parseLine("CONN GATE 1", "11:00:01.0");
		ctrl.parseLine("CONN EYE 2", "11:00:02.0");
		ctrl.parseLine("TOGGLE 1", "11:00:03.0");
		ctrl.parseLine("TOGGLE 2", "11:00:04.0");
		ctrl.parseLine("EVENT IND", "11:00:05.0");
		ctrl.parseLine("NEWRUN", "11:00:06.0");
		//No racers
		
		assertTrue(ct.isOn());
		assertEquals(1, ct.getRun().getRunNumber());
		assertNull(ct.getNextRunner());

		ctrl.parseLine("START", "11:00:07.0");

		assertNull(ct.getActiveRunner());
		
		ctrl.parseLine("FINISH", "11:00:18.0");
		
		assertNull(ct.getFinishedRunner());
		
		assertEquals(11,ct.getTime().get(10));
		assertEquals(0,ct.getTime().get(12));
		assertEquals(18,ct.getTime().get(13));
		assertEquals(0,ct.getTime().get(14));
	}
	
	/**
	 * TC 2.3
	 */
	@Test
	public void testFinishWithoutStartedRacers() {
		ctrl.parseLine("OFF", "11:00:00.0");
		ctrl.parseLine("ON", "11:00:00.0");
		ctrl.parseLine("CONN GATE 1", "11:00:01.0");
		ctrl.parseLine("CONN EYE 2", "11:00:02.0");
		ctrl.parseLine("TOGGLE 1", "11:00:03.0");
		ctrl.parseLine("TOGGLE 2", "11:00:04.0");
		ctrl.parseLine("EVENT IND", "11:00:05.0");
		ctrl.parseLine("NEWRUN", "11:00:06.0");
		ctrl.parseLine("NUM 444", "11:00:07.0");
		
		assertTrue(ct.isOn());
		assertEquals(1, ct.getRun().getRunNumber());
		assertEquals(444, ct.getNextRunner().getNumber());
		assertNull(ct.getActiveRunner());
		assertNull(ct.getFinishedRunner());
		
		ctrl.parseLine("FINISH", "11:00:18.0");

		assertEquals(444, ct.getNextRunner().getNumber());
		assertNull(ct.getActiveRunner());
		assertNull(ct.getFinishedRunner()); //No runners were active so none finish
		
		assertEquals(11,ct.getTime().get(10));
		assertEquals(0,ct.getTime().get(12));
		assertEquals(18,ct.getTime().get(13));
		assertEquals(0,ct.getTime().get(14));
	}
	
	/**
	 * TC 2.4
	 */
	@Test
	public void testDuplicateRacers() {
		ctrl.parseLine("OFF", "11:00:00.0");
		ctrl.parseLine("ON", "11:00:00.0");
		ctrl.parseLine("CONN GATE 1", "11:00:01.0");
		ctrl.parseLine("CONN EYE 2", "11:00:02.0");
		ctrl.parseLine("TOGGLE 1", "11:00:03.0");
		ctrl.parseLine("TOGGLE 2", "11:00:04.0");
		ctrl.parseLine("EVENT IND", "11:00:05.0");
		ctrl.parseLine("NEWRUN", "11:00:06.0");
		ctrl.parseLine("NUM 444", "11:00:07.0");
		ctrl.parseLine("NUM 444", "11:00:07.0");
		ctrl.parseLine("NUM 444", "11:00:07.0"); //Try adding same number several times
		
		assertTrue(ct.isOn());
		assertEquals(1, ct.getRun().getRunNumber());
		assertEquals(444, ct.getNextRunner().getNumber());
		assertNull(ct.getActiveRunner());
		assertNull(ct.getFinishedRunner());
		
		ctrl.parseLine("START", "11:00:17.0");
		ctrl.parseLine("NUM 444", "11:00:18.0"); //Try adding again

		assertNull(ct.getNextRunner()); //No other runners because the number already exists
		assertEquals(444, ct.getActiveRunner().getNumber());
		assertNull(ct.getFinishedRunner());
		
		ctrl.parseLine("FINISH", "11:00:22.0");
		ctrl.parseLine("NUM 444", "11:00:18.0"); //Try adding again
		
		assertNull(ct.getNextRunner()); //No other runners because the number already exists
		assertNull(ct.getActiveRunner());
		assertEquals(444, ct.getFinishedRunner().getNumber());
		
		assertEquals(11,ct.getTime().get(10));
		assertEquals(0,ct.getTime().get(12));
		assertEquals(18,ct.getTime().get(13));
		assertEquals(0,ct.getTime().get(14));
	}
	
	/**
	 * TC 2.5
	 */
	@Test
	public void testCancel() {
		ctrl.parseLine("OFF", "11:00:00.0");
		ctrl.parseLine("ON", "11:00:00.0");
		ctrl.parseLine("CONN GATE 1", "11:00:01.0");
		ctrl.parseLine("CONN EYE 2", "11:00:02.0");
		ctrl.parseLine("TOGGLE 1", "11:00:03.0");
		ctrl.parseLine("TOGGLE 2", "11:00:04.0");
		ctrl.parseLine("EVENT IND", "11:00:05.0");
		ctrl.parseLine("NEWRUN", "11:00:06.0");
		ctrl.parseLine("NUM 555", "11:00:07.0");
		ctrl.parseLine("NUM 444", "11:00:07.0");
		
		assertTrue(ct.isOn());
		assertEquals(1, ct.getRun().getRunNumber());
		Runner num444 = ct.getNextRunner();
		assertEquals(444, num444.getNumber());
		
		ctrl.parseLine("START", "11:00:17.0");
		
		assertEquals(555, ct.getNextRunner().getNumber());
		assertSame(num444, ct.getActiveRunner());
		assertEquals("11:00:17.00", num444.getStart());
		
		ctrl.parseLine("CANCEL", "11:00:22.0");

		assertSame(num444, ct.getNextRunner());
		assertNull(ct.getActiveRunner());
		assertNull(ct.getFinishedRunner());
		assertEquals("N/A", num444.getStart());
		
		assertEquals(11,ct.getTime().get(10));
		assertEquals(0,ct.getTime().get(12));
		assertEquals(22,ct.getTime().get(13));
		assertEquals(0,ct.getTime().get(14));
	}
	
	/**
	 * TC 2.6
	 */
	@Test
	public void testDNF() {
		ctrl.parseLine("OFF", "11:00:00.0");
		ctrl.parseLine("ON", "11:00:00.0");
		ctrl.parseLine("CONN GATE 1", "11:00:01.0");
		ctrl.parseLine("CONN EYE 2", "11:00:02.0");
		ctrl.parseLine("TOGGLE 1", "11:00:03.0");
		ctrl.parseLine("TOGGLE 2", "11:00:04.0");
		ctrl.parseLine("EVENT IND", "11:00:05.0");
		ctrl.parseLine("NEWRUN", "11:00:06.0");
		ctrl.parseLine("NUM 444", "11:00:07.0");
		
		assertTrue(ct.isOn());
		assertEquals(1, ct.getRun().getRunNumber());
		assertEquals(444, ct.getNextRunner().getNumber());
		
		ctrl.parseLine("START", "11:00:17.0");
		
		assertEquals(444, ct.getActiveRunner().getNumber());
		
		ctrl.parseLine("DNF", "11:00:22.0");
		
		assertNull(ct.getNextRunner());
		assertNull(ct.getActiveRunner());
		assertEquals(444, ct.getFinishedRunner().getNumber());
		assertEquals("11:00:17.00", ct.getFinishedRunner().getStart());
		assertEquals("N/A", ct.getFinishedRunner().getEnd());
		assertEquals("N/A", ct.getFinishedRunner().getTotalTime());
		
		assertEquals(11,ct.getTime().get(10));
		assertEquals(0,ct.getTime().get(12));
		assertEquals(22,ct.getTime().get(13));
		assertEquals(0,ct.getTime().get(14));
	}
	

	/**
	 * TC 2.7
	 */
	@Test
	public void testStartWithRacers() {
		ctrl.parseLine("OFF", "11:00:00.0");
		ctrl.parseLine("ON", "11:00:00.0");
		ctrl.parseLine("CONN GATE 1", "11:00:01.0");
		ctrl.parseLine("CONN EYE 2", "11:00:02.0");
		ctrl.parseLine("TOGGLE 1", "11:00:03.0");
		ctrl.parseLine("TOGGLE 2", "11:00:04.0");
		ctrl.parseLine("EVENT IND", "11:00:05.0");
		ctrl.parseLine("NEWRUN", "11:00:06.0");
		ctrl.parseLine("NUM 555", "11:00:07.0");
		ctrl.parseLine("NUM 444", "11:00:08.0");
		
		assertTrue(ct.isOn());
		assertEquals(1, ct.getRun().getRunNumber());
		assertEquals(444, ct.getNextRunner().getNumber());
		assertNull(ct.getActiveRunner());
		assertNull(ct.getFinishedRunner());

		ctrl.parseLine("START", "11:00:10.0");

		assertEquals(555, ct.getNextRunner().getNumber());
		assertEquals(444, ct.getActiveRunner().getNumber());
		assertNull(ct.getFinishedRunner());
		
		ctrl.parseLine("FINISH", "11:00:18.0");
		ctrl.parseLine("START", "11:00:19.0");

		assertNull(ct.getNextRunner());
		assertEquals(555, ct.getActiveRunner().getNumber());
		assertEquals(444, ct.getFinishedRunner().getNumber());
		assertEquals("8.0 seconds", ct.getFinishedRunner().getTotalTime());

		ctrl.parseLine("FINISH", "11:00:28.0");

		assertNull(ct.getNextRunner());
		assertNull(ct.getActiveRunner());
		assertEquals(555, ct.getFinishedRunner().getNumber());
		assertEquals("9.0 seconds", ct.getFinishedRunner().getTotalTime());
		
		assertEquals(11,ct.getTime().get(10));
		assertEquals(0,ct.getTime().get(12));
		assertEquals(28,ct.getTime().get(13));
		assertEquals(0,ct.getTime().get(14));
	}
}

