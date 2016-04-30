package cs361Project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RelayTest {
	ChronoTimerControl ct = new ChronoTimerControl();
	CmdInterface ctrl = new CmdInterface(1,ct);
	ChronoTimerSystem system = ct.getSystem();
	
	/**
	 * TC 4.1, 4.2, 4.3
	 */
	@Test
	public void testRelayRace() {
		ct.execute("ON");
		ct.execute("CONN GATE 1");
		ct.execute("CONN EYE 2");
		ct.execute("TOGGLE 1");
		ct.execute("TOGGLE 2");
		ct.execute("EVENT RELAY");
		
		system.setTime("11:00:06.1");

		ct.execute("NUM 888");
		ct.execute("NUM 777");
		ct.execute("NUM 666");
		ct.execute("NUM 555");
		ct.execute("NUM 444");

		RunRELAY run = (RunRELAY) system.getRun();
		assertEquals(5, run.getRunners().size());
		assertTrue(run.getFinished().isEmpty());
		
		Runner num888 = run.getRunners().get(0);
		Runner num777 = run.getRunners().get(1);
		Runner num666 = run.getRunners().get(2);
		Runner num555 = run.getRunners().get(3);
		Runner num444 = run.getRunners().get(4);
		assertEquals(444, num444.getNumber());
		assertEquals(555, num555.getNumber());
		assertEquals(666, num666.getNumber());
		assertEquals(777, num777.getNumber());
		assertEquals(888, num888.getNumber());
		
		assertTrue(ct.isEnabled());

		system.setTime("11:00:17.0");
		ct.execute("TRIG 1"); //Start
		assertTrue(run.getRunners().get(0).getStartTime() != null);

		assertTrue(run.getFinished().isEmpty());

		system.setTime("11:00:40.0");
		ct.execute("TRIG 2"); //Finish 0001
		assertEquals(1, run.getFinished().size());

		system.setTime("11:00:41.0");
		ct.execute("TRIG 2"); //Finish 0002
		assertEquals(2, run.getFinished().size());

		system.setTime("11:00:43.0");
		ct.execute("TRIG 2"); //Finish 0003
		assertEquals(3, run.getFinished().size());
		
		system.setTime("11:00:46.0");
		ct.execute("TRIG 2"); //Finish 0004
		assertEquals(4, run.getFinished().size());
		
		system.setTime("11:00:47.0");
		ct.execute("TRIG 2"); //Finish 0005
		assertEquals(5, run.getFinished().size());
		
		
		assertEquals("11:00:17.00", num888.getStart());
		assertEquals("11:00:40.00", num888.getEnd());
		assertEquals("11:00:40.00", num777.getStart());
		assertEquals("11:00:41.00", num777.getEnd());
		assertEquals("11:00:41.00", num666.getStart());
		assertEquals("11:00:43.00", num666.getEnd());
		assertEquals("11:00:43.00", num555.getStart());
		assertEquals("11:00:46.00", num555.getEnd());
		assertEquals("11:00:46.00", num444.getStart());
		assertEquals("11:00:47.00", num444.getEnd());
		
		
		assertEquals(11,system.getTime().get(10));
		assertEquals(0,system.getTime().get(12));
		assertEquals(47,system.getTime().get(13));
		assertEquals(0,system.getTime().get(14));
	}
	
	/**
	 * 
	 */
	@Test
	public void testRelayRace_OneRunner() {
		ct.execute("OFF");
		ct.execute("ON");
		ct.execute("CONN GATE 1");
		ct.execute("CONN EYE 2");
		ct.execute("TOGGLE 1");
		ct.execute("TOGGLE 2");
		ct.execute("EVENT RELAY");
		
		system.setTime("11:00:06.1");

		RunRELAY run = (RunRELAY) system.getRun();
		assertTrue(run.getRunners().isEmpty());
		assertTrue(run.getFinished().isEmpty());
		assertEquals(null, run.getCheckpoint());
		assertEquals(1, run.getRunNumber());
		
		assertTrue(ct.isEnabled());

		system.setTime("11:00:17.0");
		ct.execute("TRIG 1"); //Start
		
		assertFalse(run.getRunners().isEmpty());
		assertEquals(1,  run.getRunners().getFirst().getNumber());
		assertEquals("11:00:17.00", run.getRunners().getFirst().getStart());
		assertEquals(null, run.getRunners().getFirst().getEndTime());
		assertTrue(run.getCheckpoint() != null);
		assertTrue(run.getFinished().isEmpty());
		
		ct.execute("CANCEL");
		assertEquals(null, run.getRunners().getFirst().getStartTime());
		assertEquals(null, run.getCheckpoint());
		
		system.setTime("11:00:25.0");
		ct.execute("START");
		assertEquals("11:00:25.00", run.getRunners().getFirst().getStart());

		system.setTime("11:00:41.0");
		ct.execute("TRIG 2"); //Finish 0001
		assertEquals(1, run.getFinished().size());
		assertEquals("11:00:41.00", run.getRunners().getFirst().getEnd());
		Runner r = new Runner(-1);
		r.setStart(run.getCheckpoint());
		assertEquals("11:00:41.00", r.getStart());
		
		ct.execute("NUM 888");
		assertEquals(888, run.getRunners().getFirst().getNumber());
		
		system.setTime("11:00:45.0");
		ct.execute("FINISH");
		assertEquals(2, run.getRunners().get(1).getNumber());
		assertEquals(2, run.getRunners().size());
		assertEquals(2, run.getFinished().size());
		assertEquals("11:00:41.00", run.getRunners().get(1).getStart());
		assertEquals("11:00:45.00", run.getRunners().get(1).getEnd());
		
	}
}
