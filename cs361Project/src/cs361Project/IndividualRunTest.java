package cs361Project;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

public class IndividualRunTest {
	ChronoTimerControl ct = new ChronoTimerControl();
	CmdInterface ctrl = new CmdInterface(1, ct);
	ChronoTimerSystem system = ct.getSystem();

	/**
	 * TC 2.1
	 */
	@Test
	public void testStartAndFinishWithNoRun() {

		ct.execute("OFF");
		ct.execute("ON");
		ct.execute("CONN GATE 1");
		ct.execute("CONN EYE 2");
		ct.execute("TOGGLE 1");
		ct.execute("TOGGLE 2");
		ct.execute("EVENT IND");
		ct.execute("NUM 444");

		assertTrue(ct.isEnabled());
		assertNull(system.getRun());

		system.setTime("11:00:07.0");
		ct.execute("START");

		assertNull(system.getRun());

		system.setTime("11:00:18.0");
		ct.execute("FINISH");

		assertNull(system.getRun());

		assertEquals(11, system.getTime().get(10));
		assertEquals(0, system.getTime().get(12));
		assertEquals(18, system.getTime().get(13));
		assertEquals(0, system.getTime().get(14));
	}

	/**
	 * TC 2.2
	 */
	@Test
	public void testStartWithoutRacers() {
		ct.execute("OFF");
		ct.execute("ON");
		ct.execute("CONN GATE 1");
		ct.execute("CONN EYE 2");
		ct.execute("TOGGLE 1");
		ct.execute("TOGGLE 2");
		ct.execute("EVENT IND");
		ct.execute("NEWRUN");
		// No racers
		
		RunIND run = (RunIND) system.getRun();

		assertTrue(ct.isEnabled());
		assertEquals(1, run.getRunNumber());
		assertEquals(1, system.getRun().getRunNumber());

		system.setTime("11:00:07.0");
		ct.execute("START");

		assertTrue(run.getActive().isEmpty());

		system.setTime("11:00:18.0");
		ct.execute("FINISH");

		assertTrue(run.getFinished().isEmpty());

		assertEquals(11, system.getTime().get(10));
		assertEquals(0, system.getTime().get(12));
		assertEquals(18, system.getTime().get(13));
		assertEquals(0, system.getTime().get(14));
	}

	/**
	 * TC 2.3
	 */
	@Test
	public void testFinishWithoutStartedRacers() {
		ct.execute("OFF");
		ct.execute("ON");
		ct.execute("CONN GATE 1");
		ct.execute("CONN EYE 2");
		ct.execute("TOGGLE 1");
		ct.execute("TOGGLE 2");
		ct.execute("EVENT IND");
		ct.execute("NEWRUN");
		ct.execute("NUM 444");
		
		RunIND run = (RunIND) system.getRun();

		assertTrue(ct.isEnabled());
		assertEquals(1, run.getRunNumber());
		assertEquals(444, run.getwaitingRunners().peek().getNumber());
		assertTrue(run.getActive().isEmpty());
		assertTrue(run.getFinished().isEmpty());

		system.setTime("11:00:18.0");
		ct.execute("FINISH");

		assertEquals(444, run.getwaitingRunners().peek().getNumber());
		assertTrue(run.getActive().isEmpty());
		assertTrue(run.getFinished().isEmpty()); // No runners were active so none finish

		assertEquals(11, system.getTime().get(10));
		assertEquals(0, system.getTime().get(12));
		assertEquals(18, system.getTime().get(13));
		assertEquals(0, system.getTime().get(14));
	}

	/**
	 * TC 2.4
	 */
	@Test
	public void testDuplicateRacers() {
		ct.execute("OFF");
		ct.execute("ON");
		ct.execute("CONN GATE 1");
		ct.execute("CONN EYE 2");
		ct.execute("TOGGLE 1");
		ct.execute("TOGGLE 2");
		ct.execute("EVENT IND");
		ct.execute("NEWRUN");
		ct.execute("NUM 444");
		ct.execute("NUM 444");
		ct.execute("NUM 444"); // Try adding same number several times

		RunIND run = (RunIND) system.getRun();
		
		assertTrue(ct.isEnabled());
		assertEquals(1, run.getRunNumber());
		assertEquals(444, run.getwaitingRunners().peek().getNumber());
		assertTrue(run.getActive().isEmpty());
		assertTrue(run.getFinished().isEmpty());

		system.setTime("11:00:17.0");
		ct.execute("START");
		ct.execute("NUM 444"); // Try adding again
		
		assertTrue(run.getwaitingRunners().isEmpty()); // No other runners because the number already exists
		assertEquals(444, run.getActive().peek().getNumber());
		assertTrue(run.getFinished().isEmpty());

		system.setTime("11:00:22.0");
		ct.execute("FINISH");
		ct.execute("NUM 444"); // Try adding again

		assertTrue(run.getwaitingRunners().isEmpty()); // No other runners because the number already exists
		assertTrue(run.getActive().isEmpty());
		assertEquals(444, run.getFinished().peek().getNumber());

		assertEquals(11, system.getTime().get(10));
		assertEquals(0, system.getTime().get(12));
		assertEquals(22, system.getTime().get(13));
		assertEquals(0, system.getTime().get(14));
	}

	/**
	 * TC 2.5
	 */
	@Test
	public void testCancel() {
		ct.execute("OFF");
		ct.execute("ON");
		ct.execute("CONN GATE 1");
		ct.execute("CONN EYE 2");
		ct.execute("TOGGLE 1");
		ct.execute("TOGGLE 2");
		ct.execute("EVENT IND");
		ct.execute("NEWRUN");
		ct.execute("NUM 555");
		ct.execute("NUM 444");

		RunIND run = (RunIND) system.getRun();
		assertTrue(ct.isEnabled());
		assertEquals(1, system.getRun().getRunNumber());
		Runner num444 = run.getwaitingRunners().peek();
		assertEquals(444, num444.getNumber());

		system.setTime("11:00:17.0");
		ct.execute("START");

		assertEquals(555, run.getwaitingRunners().peek().getNumber());
		assertSame(num444, run.getActive().peek());
		assertEquals("11:00:17.00", num444.getStart());

		system.setTime("11:00:22.0");
		ct.execute("CANCEL");

		assertSame(num444, run.getwaitingRunners().peek());
		assertTrue(run.getActive().isEmpty());
		assertTrue(run.getFinished().isEmpty());
		assertEquals("N/A", num444.getStart());

		assertEquals(11, system.getTime().get(10));
		assertEquals(0, system.getTime().get(12));
		assertEquals(22, system.getTime().get(13));
		assertEquals(0, system.getTime().get(14));
	}

	/**
	 * TC 2.6
	 */
	@Test
	public void testDNF() {
		ct.execute("OFF");
		ct.execute("ON");
		ct.execute("CONN GATE 1");
		ct.execute("CONN EYE 2");
		ct.execute("TOGGLE 1");
		ct.execute("TOGGLE 2");
		ct.execute("EVENT IND");
		ct.execute("NEWRUN");
		ct.execute("NUM 444");

		RunIND run = (RunIND) system.getRun();
		
		assertTrue(ct.isEnabled());
		assertEquals(1, run.getRunNumber());
		assertEquals(444, run.getwaitingRunners().peek().getNumber());

		system.setTime("11:00:17.0");
		ct.execute("START");

		assertEquals(444, run.getActive().peek().getNumber());

		system.setTime("11:00:22.0");
		ct.execute("DNF");

		assertTrue(run.getwaitingRunners().isEmpty());
		assertTrue(run.getActive().isEmpty());
		assertEquals(444, run.getFinished().peek().getNumber());
		assertEquals("11:00:17.00", run.getFinished().peek().getStart());
		assertEquals("N/A", run.getFinished().peek().getEnd());
		assertEquals("N/A", run.getFinished().peek().getTotalTime());

		assertEquals(11, system.getTime().get(10));
		assertEquals(0, system.getTime().get(12));
		assertEquals(22, system.getTime().get(13));
		assertEquals(0, system.getTime().get(14));
	}

	/**
	 * TC 2.7
	 */
	@Test
	public void testStartWithRacers() {
		ct.execute("OFF");
		ct.execute("ON");
		ct.execute("CONN GATE 1");
		ct.execute("CONN EYE 2");
		ct.execute("TOGGLE 1");
		ct.execute("TOGGLE 2");
		ct.execute("EVENT IND");
		ct.execute("NEWRUN");
		ct.execute("NUM 555");
		ct.execute("NUM 444");

		RunIND run = (RunIND) system.getRun();
		
		assertTrue(ct.isEnabled());
		assertEquals(1, run.getRunNumber());
		assertEquals(444, run.getwaitingRunners().peek().getNumber());
		assertTrue(run.getActive().isEmpty());
		assertTrue(run.getFinished().isEmpty());

		system.setTime("11:00:10.0");
		ct.execute("START");

		assertEquals(555, run.getwaitingRunners().peek().getNumber());
		assertEquals(444, run.getActive().peek().getNumber());
		assertTrue(run.getFinished().isEmpty());

		system.setTime("11:00:18.0");
		ct.execute("FINISH");
		system.setTime("11:00:19.0");
		ct.execute("START");

		assertTrue(run.getwaitingRunners().isEmpty());
		assertEquals(555, run.getActive().peek().getNumber());
		assertEquals(444, run.getFinished().peek().getNumber());
		assertEquals("8.0 seconds", run.getFinished().peek().getTotalTime());

		system.setTime("11:00:28.0");
		ct.execute("FINISH");

		assertTrue(run.getwaitingRunners().isEmpty());
		assertTrue(run.getActive().isEmpty());
		assertEquals(555, run.getFinished().peekLast().getNumber());
		assertEquals("9.0 seconds", run.getFinished().peekLast().getTotalTime());

		assertEquals(11, system.getTime().get(10));
		assertEquals(0, system.getTime().get(12));
		assertEquals(28, system.getTime().get(13));
		assertEquals(0, system.getTime().get(14));
	}
}
