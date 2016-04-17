package cs361Project;


import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

public class ParallelIndividualRunTest 
{
	ChronoTimerControl ct = new ChronoTimerControl();
	CmdInterface ctrl = new CmdInterface(1,ct);
	ChronoTimerSystem system = ct.getSystem();
	
	/**
	 * TC 3.1, 3.2, 3.3, 3.4
	 */
	@Test
	public void testParallelRace() {
		ct.execute("OFF");
		ct.execute("ON");
		ct.execute("CONN GATE 1");
		ct.execute("CONN EYE 2");
		ct.execute("CONN GATE 3");
		ct.execute("CONN EYE 4");
		ct.execute("TOGGLE 1");
		ct.execute("TOGGLE 2");
		ct.execute("TOGGLE 3");
		ct.execute("TOGGLE 4");
		ct.execute("EVENT PARIND");
		ct.execute("NEWRUN");
		
		system.setTime("11:00:06.1");
		ct.execute("TRIG 1"); //Shouldn't do anything
		ct.execute("TRIG 2"); 

		ct.execute("NUM 888");
		ct.execute("NUM 777");
		ct.execute("NUM 666");
		ct.execute("NUM 555");
		ct.execute("NUM 444");

		RunPARIND run = (RunPARIND) system.getRun();
		
		assertTrue(ct.isEnabled());
		assertEquals(1, run.getRunNumber());
		Runner num444 = run.getwaitingRunners().peek();
		assertEquals(444, num444.getNumber());
		assertTrue(run.getActive().isEmpty());
		assertTrue(run.getFinished().isEmpty());

		system.setTime("11:00:17.0");
		ct.execute("TRIG 1"); //Start 444 in lane 1

		Runner num555 = run.getwaitingRunners().peek();
		assertEquals(555, num555.getNumber());
		assertSame(num444, run.getActive().peek());
		assertEquals("11:00:17.00", num444.getStart());
		assertTrue(run.getFinished().isEmpty());

		system.setTime("11:00:17.0");
		ct.execute("TRIG 3"); //Start 555 in lane 2
		
		Runner num666 = run.getwaitingRunners().peek();
		assertEquals(666, num666.getNumber()); 
		assertSame(num555, run.getActive().peekLast());
		assertTrue(run.getFinished().isEmpty());

		system.setTime("11:00:30.0");
		ct.execute("TRIG 2"); //Finish 444 in lane 1
		
		assertSame(num666, run.getwaitingRunners().peek()); 
		assertSame(num555, run.getActive().peekLast());
		assertSame(num444, run.getFinished().peekLast());

		system.setTime("11:00:32.0");
		ct.execute("TRIG 4"); //Finish 555 in lane 2
		system.setTime("11:00:33.0");
		ct.execute("TRIG 3"); //Start 666 in lane 2
		
		Runner num777 = run.getwaitingRunners().peek();
		assertEquals(777, num777.getNumber()); 
		assertSame(num666, run.getActive().peek());
		assertSame(num555, run.getFinished().peekLast());

		system.setTime("11:00:33.0");
		ct.execute("TRIG 1"); //Start 777 in lane 1
		system.setTime("11:00:35.0");
		ct.execute("TRIG 3"); //Start 888 in lane 2
		
		assertTrue(run.getwaitingRunners().isEmpty());
		Runner num888 = run.getActive().peekLast();
		assertEquals(888, num888.getNumber());
		assertSame(num555, run.getFinished().peekLast());

		system.setTime("11:00:40.0");
		ct.execute("TRIG 2"); //Finish 777 in lane 1
		assertSame(num777, run.getFinished().peekLast());

		system.setTime("11:00:41.0");
		ct.execute("TRIG 4"); //Finish 666 in lane 2
		assertSame(num666, run.getFinished().peekLast());

		system.setTime("11:00:43.0");
		ct.execute("TRIG 4"); //Finish 888 in lane 2
		assertSame(num888, run.getFinished().peekLast());
		
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
		
		assertEquals(11,system.getTime().get(10));
		assertEquals(0,system.getTime().get(12));
		assertEquals(43,system.getTime().get(13));
		assertEquals(0,system.getTime().get(14));
		
		system.export(1);
	}
}

