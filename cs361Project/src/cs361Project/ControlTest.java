package cs361Project;


import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

public class ControlTest 
{
	ChronoTimerControl me = new ChronoTimerControl();
	ChronoTimerSystem system = me.getSystem();
	
	@Test
	public void testOnOff()
	{
		
		assertEquals(false,me.isEnabled());
		me.setEnabled(true);
		assertEquals(true,me.isEnabled());
		assertNull(system.getRun());
		me.setEnabled(false);
		assertEquals(false,me.isEnabled());
	}
	
	@Test
	public void testReset()
	{
		me.setEnabled(true);
		system.setEvent("IND");
		system.newRun();
		assertEquals(1,system.getRun().getRunNumber());
		system.reset();
		assertNull(system.getRun());
		assertEquals(true,me.isEnabled());
	}
	
	@Test
	public void TestUpdateTimetoCurrent()
	{
		Calendar test = new GregorianCalendar();
		system.setTime();
		assertEquals(test,system.getTime());
		
	}
	
	@Test
	public void testTime()
	{
		
		me.setEnabled(false);
		assertNotNull(system.getTime());
		me.setEnabled(true);
		system.setTime();
		Calendar test = new GregorianCalendar();
		assertEquals(test,system.getTime());
		
		system.setTime("09:13:22.0");
		assertEquals(9,system.getTime().get(10));
		assertEquals(13,system.getTime().get(12));
		assertEquals(22,system.getTime().get(13));
		assertEquals(0,system.getTime().get(14));
		
		me.setEnabled(false);
		assertNotNull(system.getTime());
	}
	
	@Test
	public void testEvent()
	{
		assertEquals("IND",system.getEvent());
		me.setEnabled(true);
		assertEquals("IND",system.getEvent());
		system.setEvent("GRP");
		system.newRun();
		assertEquals("GRP",system.getEvent());
		system.endRun();
		system.setEvent("PARIND");
		system.newRun();
		assertEquals("PARIND",system.getEvent());
	}
	
	@Test
	public void testTog()
	{
		me.setEnabled(false);
		me.setEnabled(true);
		
		for(int i = 1; i <= 12; i++ ){
		assertEquals(false,system.getChannel(i).isEnabled());
		}
		
		for(int j = 1; j <= 12; j++)
		{
			system.getChannel(j).tog();
			assertEquals(true,system.getChannel(j).isEnabled());
		}
		
		for(int j = 1; j <= 12; j++)
		{
			system.getChannel(j).tog();
			assertEquals(false,system.getChannel(j).isEnabled());
		}
		
	}
	
	@Test
	public void testConn()
	{
		me.setEnabled(true);
		
		for(int i = 1; i <= 12; i++ ){
			assertEquals(null,system.getChannel(i).getSensor());
		}
		
		system.getChannel(1).tog();
		me.execute("CONN GATE 1");
		me.execute("CONN EYE 2");
		me.execute("CONN PAD 3");
		
		assertEquals("GATE", system.getChannel(1).getSensor());
		assertEquals("EYE", system.getChannel(2).getSensor());
		assertEquals("PAD", system.getChannel(3).getSensor());

		me.execute("CONN ASDF 1");
		assertEquals("GATE",system.getChannel(1).getSensor());

		me.execute("CONN GATE 13");
		assertNull(system.getChannel(13));
	}
	
	@Test
	public void testDisc()
	{
		me.setEnabled(true);
		system.getChannel(1).disc();
		for(int i = 1; i <= 12; i++)
		{
			me.execute("CONN GATE " + i);
		}
		
		for(int i = 1; i <=12; i++){
			system.getChannel(i).disc();
		}
		
		for(int i = 1; i <=12; i++)
		{
			assertEquals(null,system.getChannel(i).getSensor());
		}
	}
	
	@Test
	public void testTrig()
	{
		me.setEnabled(true);
		me.execute("CONN GATE 1");
		me.execute("CONN EYE 2");
		system.setEvent("IND");
		system.newRun();
		system.getChannel(1).tog();
		system.getChannel(2).tog();
		me.execute("NUM 1");
		me.execute("TRIG 1");
		RunIND run = (RunIND) system.getRun();
		assertEquals(1,run.getActive().peek().getNumber());
		me.execute("TRIG 2");
		assertEquals(1,run.getFinished().peek().getNumber());		
		
		
	}
	
	@Test
	public void testNewRun()
	{
		me.setEnabled(true);
		me.execute("CONN GATE 1");
		me.execute("CONN EYE 2");
		system.setEvent("IND");
		system.newRun();
		system.getChannel(1).tog();
		system.getChannel(2).tog();
		me.execute("NUM 1");
		system.getChannel(1).trig(1);
		system.getChannel(2).trig(2);
		system.endRun();
		
		try { system.newRun(); } catch (Exception ex) {}
		assertEquals(2,system.getRun().getRunNumber());
		me.execute("NUM 2");
		system.getChannel(1).trig(1);
		system.getChannel(2).trig(2);
	}
	
	@Test
	public void testEndRun()
	{
		me.setEnabled(true);
		me.execute("CONN GATE 1");
		me.execute("CONN EYE 2");
		system.setEvent("IND");
		system.newRun();
		me.execute("TOG 1");
		me.execute("TOG 2");
		me.execute("NUM 1");
		system.getChannel(1).trig(1);
		system.getChannel(2).trig(2);
		system.endRun();
		
		assertNull(system.getRun());
	}
	
	@Test
	public void testNum()
	{
		me.setEnabled(true);
		me.execute("CONN GATE 1");
		me.execute("CONN EYE 2");
		system.setEvent("IND");
		system.newRun();
		me.execute("TOG 1");
		me.execute("TOG 2");
		me.execute("NUM 1");

		RunIND run = (RunIND) system.getRun();
		assertEquals(1,run.getwaitingRunners().peek().getNumber());
	}
	
	@Test
	public void testClr()
	{
		me.setEnabled(true);
		me.execute("CONN GATE 1");
		me.execute("CONN EYE 2");
		system.setEvent("IND");
		system.newRun();
		me.execute("TOG 1");
		me.execute("TOG 2");
		me.execute("NUM 1");
		system.getRun().clr(1);
		
		
		RunIND run = (RunIND) system.getRun();
		assertTrue(run.getwaitingRunners().isEmpty());

	}
	
	
	@Test
	public void testStart()
	{
		me.setEnabled(true);
		me.execute("CONN GATE 1");
		me.execute("CONN EYE 2");
		system.setEvent("IND");
		system.newRun();
		me.execute("TOG 1");
		me.execute("TOG 2");
		me.execute("NUM 1");
		system.getRun().start(1, new GregorianCalendar());
		
		RunIND run = (RunIND) system.getRun();
		assertTrue(run.getwaitingRunners().isEmpty());
		assertEquals(1,run.getActive().peek().getNumber());
		me.execute("TRIG 2");
		assertEquals(1,run.getFinished().peek().getNumber());		
	}
	
	@Test
	public void testCancel()
	{
		me.setEnabled(true);
		me.execute("CONN GATE 1");
		me.execute("CONN EYE 2");
		system.setEvent("IND");
		system.newRun();
		me.execute("TOG 1");
		me.execute("TOG 2");
		me.execute("NUM 1");
		me.execute("TRIG 1");
		RunIND run = (RunIND) system.getRun();
		assertEquals(1,run.getActive().peek().getNumber());
		run.cancel();
		assertEquals(1,run.getwaitingRunners().peek().getNumber());		
	}
	
	@Test
	public void testDnf()
	{
		me.setEnabled(true);
		me.execute("CONN GATE 1");
		me.execute("CONN EYE 2");
		system.setEvent("IND");
		system.newRun();
		me.execute("TOG 1");
		me.execute("TOG 2");
		me.execute("NUM 1");
		me.execute("TRIG 1");
		RunIND run = (RunIND) system.getRun();
		assertEquals(1,run.getActive().peek().getNumber());
		run.dnf();
		assertEquals(1,run.getFinished().peek().getNumber());		
	}
	
	@Test
	public void testFinish()
	{
		me.setEnabled(true);
		me.execute("CONN GATE 1");
		me.execute("CONN EYE 2");
		system.setEvent("IND");
		system.newRun();
		me.execute("TOG 1");
		me.execute("TOG 2");
		me.execute("NUM 1");
		RunIND run = (RunIND) system.getRun();
		run.start(1, new GregorianCalendar());;
		assertEquals(1,run.getActive().peek().getNumber());
		run.finish(2, new GregorianCalendar());
		assertEquals(1,run.getFinished().peek().getNumber());		
	}
	
	//TODO: Find way to test print and export functions.
	@Test
	public void testPrint()
	{
		
	}
	
	@Test
	public void testExport()
	{
		
	}
}

