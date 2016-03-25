package cs361Project;


import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

public class ControlTest 
{
	ChronoTimerControl me = new ChronoTimerControl();
	
	@Test
	public void testOnOff()
	{
		
		assertEquals(false,me.isOn());
		me.on();
		assertEquals(true,me.isOn());
		assertEquals(0,me.getRunNumber());
		me.off();
		assertEquals(false,me.isOn());
	}
	
	@Test
	public void testReset()
	{
		me.on();
		me.event("IND");
		assertEquals(1,me.getRunNumber());
		me.reset();
		assertEquals(0,me.getRunNumber());
		assertEquals(true,me.isOn());
	}
	
	@Test
	public void TestUpdateTimetoCurrent()
	{
		Calendar test = new GregorianCalendar();
		me.updateTimeToCurrent();
		assertEquals(test,me.getTime());
		
	}
	
	@Test
	public void testTime()
	{
		
		me.off();
		assertEquals(null,me.getTime());
		me.on();
		Calendar test = new GregorianCalendar();
		assertEquals(test,me.getTime());
		
		me.time("09:13:22.0");
		assertEquals(9,me.getTime().get(10));
		assertEquals(13,me.getTime().get(12));
		assertEquals(22,me.getTime().get(13));
		assertEquals(0,me.getTime().get(14));
		
		me.off();
		assertEquals(null,me.getTime());
	}
	
	@Test
	public void testEvent()
	{
		assertEquals(null,me.getEvent());
		me.on();
		assertEquals(null,me.getEvent());
		me.event("ind");
		assertEquals("IND",me.getEvent());
	}
	
	@Test
	public void testTog()
	{
		me.off();
		me.on();
		
		for(int i = 1; i <= 12; i++ ){
		assertEquals(false,me.getChanStatus(i));
		}
		
		for(int j = 1; j <= 12; j++)
		{
			me.tog(j);
			assertEquals(true,me.getChanStatus(j));
		}
		
		for(int j = 1; j <= 12; j++)
		{
			me.tog(j);
			assertEquals(false,me.getChanStatus(j));
		}
		
	}
	
	@Test
	public void testConn()
	{
		//sensor in channel is null if not connected.
		me.on();
		
		for(int i = 1; i <= 12; i++ ){
			assertEquals(null,me.getChanSensor(i));
			}
		
		me.tog(1);
		me.conn("gate", 1);
		me.conn("eye", 2);
		me.conn("pad", 3);
		
		assertEquals("GATE",me.getChanSensor(1));
		assertEquals("EYE",me.getChanSensor(2));
		assertEquals("PAD",me.getChanSensor(3));
		
		me.conn("asdf", 1);
		assertEquals("GATE",me.getChanSensor(1));
		me.conn("gate", 13);
	}
	
	@Test
	public void testDisc()
	{
		me.on();
		me.disc(1);
		for(int i = 1; i <= 12; i++)
		{
			me.conn("gate", i);
		}
		
		for(int i = 1; i <=12; i++){
			me.disc(i);
		}
		
		for(int i = 1; i <=12; i++)
		{
			assertEquals(null,me.getChanSensor(i));
		}
	}
	
	@Test
	public void testTrig()
	{
		me.on();
		me.conn("GATE", 1);
		me.conn("EYE",2);
		me.event("IND");
		me.tog(1);
		me.tog(2);
		me.num(1);
		me.trig(1);
		assertEquals(1,me.getActiveRunner().getNumber());
		me.trig(2);
		assertEquals(1,me.getFinishedRunner().getNumber());		
		
		
	}
	
	@Test
	public void testNewRun()
	{
		
	}
	
	@Test
	public void testEndRun()
	{
		
	}
	
	@Test
	public void testNum()
	{
		
	}
	
	@Test
	public void testClr()
	{
		
	}
	
	@Test
	public void testSwap()
	{
	
	}
	
	@Test
	public void testStart()
	{
		
	}
	
	@Test
	public void testCancel()
	{
		
	}
	
	@Test
	public void testDnf()
	{
		
	}
	
	@Test
	public void testFinish()
	{
		
	}
	
	@Test
	public void testPrint()
	{
		
	}
	
	@Test
	public void testExport()
	{
		
	}
}

