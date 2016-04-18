/**
 * Nicholas Bialk @nbbialk
 * CS361 ChronoTimer Project
 * Runner.java
 * 
 * The Runner class controls each of the runners (by number) as well as the start and end times for their race;
 * the methods in this class are designed to be called independently by every subtype of the Run class
 */

package cs361Project;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Runner {
	private int number;
	private Calendar startTime;
	private Calendar endTime;
	private DateFormat simple = new SimpleDateFormat("HH:mm:ss.SS");
	
	public Runner(int num){
		number = num;
		startTime = null;
		endTime = null;
	}
	
	public int getNumber(){return number;}
	public String getStart(){return startTime != null ? simple.format(startTime.getTime()) : "N/A";}
	public Calendar getStartTime(){return startTime;}
	public String getEnd(){return endTime != null ? simple.format(endTime.getTime()) : "N/A";}
	public Calendar getEndTime(){return endTime;}
	public void setNumber(int newNumber){number = newNumber;}
	public void setStart(Calendar start){
		if(start == null)
			startTime = null;
		else
			startTime = (Calendar) start.clone();
		}
	
	public void setEnd(Calendar end){
		if(end == null)
			endTime = null;
		else
			endTime = (Calendar) end.clone();
		}
	
	/**
	 * Runner.getTotalTime returns the total length of time elapsed by the current Runner (in seconds);
	 * it is only valid for a Runner with both a startTime and an EndTime
	 * @return - the elapsed time of the race, or "N/A" if there is not a valid start or end time
	 */
	public String getTotalTime(){
		if(endTime != null && startTime != null) {
			long elapsed = endTime.getTimeInMillis() - startTime.getTimeInMillis();
			double dblElapsed = ((double)elapsed)/1000;
			return Double.toString(dblElapsed) + " seconds";				
		} else {
			return "DNF";
		}
	}
	
	/**
	 * Runner.getElapsed is similar to getTotalTime, but instead of measuring the total time from start to finish,
	 * it measures the currently elapsed time from start until the current system time
	 * @param time - imports the current system time from the ChronoTimer's controller
	 * @return - the current elapsed time for the race, or "N/A" if there is not a valid start time
	 */
	public String getElapsed(Calendar time){
		if(startTime == null) return "N/A";
		else{
			long elapsed = time.getTimeInMillis() - startTime.getTimeInMillis();
			double dblElapsed = ((double)elapsed)/1000;
			return Double.toString(dblElapsed) + " seconds";
		}
	}
}
