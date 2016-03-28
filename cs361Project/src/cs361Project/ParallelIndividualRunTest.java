package cs361Project;


import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

public class ParallelIndividualRunTest 
{
	ChronoTimerControl ct = new ChronoTimerControl();
	CmdInterface ctrl = new CmdInterface(1,ct);
	
	/**
	 * TC 3.1, 3.2, 3.3, 3.4
	 */
	@Test
	public void testParallelRace() {
		ctrl.parseLine("OFF", "11:00:00.0");
		ctrl.parseLine("ON", "11:00:00.0");
		ctrl.parseLine("CONN GATE 1", "11:00:01.0");
		ctrl.parseLine("CONN EYE 2", "11:00:02.0");
		ctrl.parseLine("CONN GATE 3", "11:00:02.1");
		ctrl.parseLine("CONN EYE 4", "11:00:02.2");
		ctrl.parseLine("TOGGLE 1", "11:00:03.0");
		ctrl.parseLine("TOGGLE 2", "11:00:04.0");
		ctrl.parseLine("TOGGLE 3", "11:00:04.0");
		ctrl.parseLine("TOGGLE 4", "11:00:04.0");
		ctrl.parseLine("EVENT PARIND", "11:00:05.0");
		ctrl.parseLine("NEWRUN", "11:00:06.0");
		
		ctrl.parseLine("TRIG 1", "11:00:06.1"); //Shouldn't do anything
		ctrl.parseLine("TRIG 2", "11:00:06.1"); 

		ctrl.parseLine("NUM 888", "11:00:07.0");
		ctrl.parseLine("NUM 777", "11:00:07.0");
		ctrl.parseLine("NUM 666", "11:00:07.0");
		ctrl.parseLine("NUM 555", "11:00:07.0");
		ctrl.parseLine("NUM 444", "11:00:07.0");
		
		assertTrue(ct.isOn());
		assertEquals(1, ct.getRun().getRunNumber());
		Runner num444 = ct.getNextRunner();
		assertEquals(444, num444.getNumber());
		assertNull(ct.getActiveRunner());
		assertNull(ct.getFinishedRunner());
		
		ctrl.parseLine("TRIG 1", "11:00:17.0"); //Start 444 in lane 1

		Runner num555 = ct.getNextRunner();
		assertEquals(555, num555.getNumber());
		assertSame(num444, ct.getActiveRunner());
		assertEquals("11:00:17.00", num444.getStart());
		assertNull(ct.getFinishedRunner());

		ctrl.parseLine("TRIG 3", "11:00:17.0"); //Start 555 in lane 2
		
		Runner num666 = ct.getNextRunner();
		assertEquals(666, num666.getNumber()); 
		assertSame(num555, ct.getActiveRunner());
		assertNull(ct.getFinishedRunner());
		
		ctrl.parseLine("TRIG 2", "11:00:30.0"); //Finish 444 in lane 1
		
		assertSame(num666, ct.getNextRunner()); 
		assertSame(num555, ct.getActiveRunner());
		assertSame(num444, ct.getFinishedRunner());

		ctrl.parseLine("TRIG 4", "11:00:32.0"); //Finish 555 in lane 2
		ctrl.parseLine("TRIG 3", "11:00:33.0"); //Start 666 in lane 2
		
		Runner num777 = ct.getNextRunner();
		assertEquals(777, num777.getNumber()); 
		assertSame(num666, ct.getActiveRunner());
		assertSame(num555, ct.getFinishedRunner());
		
		ctrl.parseLine("TRIG 1", "11:00:33.0"); //Start 777 in lane 1
		ctrl.parseLine("TRIG 3", "11:00:35.0"); //Start 888 in lane 2
		
		assertNull(ct.getNextRunner());
		Runner num888 = ct.getActiveRunner();
		assertEquals(888, num888.getNumber());
		assertSame(num555, ct.getFinishedRunner());
		
		ctrl.parseLine("TRIG 2", "11:00:40.0"); //Finish 777 in lane 1
		assertSame(num777, ct.getFinishedRunner());

		ctrl.parseLine("TRIG 4", "11:00:41.0"); //Finish 666 in lane 2
		assertSame(num666, ct.getFinishedRunner());
		
		ctrl.parseLine("TRIG 4", "11:00:43.0"); //Finish 888 in lane 2
		assertSame(num888, ct.getFinishedRunner());
		
		assertEquals("11:00:17.00", num444.getStart());
		assertEquals("11:00:30.00", num444.getEnd());
		assertEquals("13.0 seconds", num444.getTotalTime());
		
		assertEquals("11:00:17.00", num555.getStart());
		assertEquals("11:00:32.00", num555.getEnd());
		assertEquals("15.0 seconds", num555.getTotalTime());
		
		assertEquals("11:00:33.00", num666.getStart());
		assertEquals("11:00:41.00", num666.getEnd());
		assertEquals("8.0 seconds", num666.getTotalTime());
		
		assertEquals("11:00:33.00", num777.getStart());
		assertEquals("11:00:40.00", num777.getEnd());
		assertEquals("7.0 seconds", num777.getTotalTime());
		
		assertEquals("11:00:35.00", num888.getStart());
		assertEquals("11:00:43.00", num888.getEnd());
		assertEquals("8.0 seconds", num888.getTotalTime());
		
		assertEquals(11,ct.getTime().get(10));
		assertEquals(0,ct.getTime().get(12));
		assertEquals(43,ct.getTime().get(13));
		assertEquals(0,ct.getTime().get(14));
		
		ct.export(1);
	}
}

