/**
 * Nicholas Bialk @nbbialk
 * CS361 ChronoTimer Project
 * Event.java
 * 
 * The Event class is used by the ChronoTimer to control the active event, which can be one of four
 * broad categories (IND, PARIND, GRP, or PARGRP); it controls Runs that house the data for individual
 * runs or heats in that event, and relays this information back to the Controller
 */

package cs361Project;

import java.util.ArrayList;
import java.util.Calendar;

public class Event {
	/**
	 * Fields for the Event class - an ArrayList containing each of the runs that have been done in this event,
	 * a pointer to whichever run is currently in progress (if any), and a marker as to the type of event being run
	 */
	private ArrayList<Run> runs;
	private Run currentRun;
	private String eventType;
	
	/**
	 * The constructor for the Event class instantiates an empty ArrayList for the runs;
	 * it does not instantiate a currentRun until specified to do so by the newRun command
	 */
	public Event(String type){
		runs = new ArrayList<Run>();
		currentRun = null;
		eventType = type;
	}
	
	/**
	 * Getters for Event's fields
	 * @return the specified field
	 */
	public ArrayList<Run> getRuns(){return runs;}
	public Run getCurrentRun(){return currentRun;}
	public String getEventType(){return eventType;}
	
	/**
	 * Event.newRun instantiates a new run object, sets it as the current run, and adds it to the runs database;
	 * if there is already a currentRun, then the method does nothing
	 */
	public void newRun(int runCounter){
		if(currentRun == null){
			Run newRun;
			switch(eventType){
			case "IND":
				newRun = new RunIND(runCounter);
				currentRun = newRun;
				runs.add(currentRun);
				break;
			case "PARIND":
				newRun = new RunPARIND(runCounter);
				currentRun = newRun;
				runs.add(currentRun);
				break;
			//case "GRP":
				//newRun = new RunGRP(runCounter);
				//currentRun = newRun;
				//runs.add(currentRun);
				//break;
			//case "PARGRP":
				//newRun = new RunPARGRP(runCounter);
				//currentRun = newRun;
				//runs.add(currentRun);
				//break;
			default:
				System.out.println("Error: Cannot start a new run; invalid event type!");
			}

		}
	}
	
	/**
	 * Event.endRun removes the "currentRun" pointer; the run still exists in the run log, but it can no longer
	 * be interacted with through any of the methods in the Event class, save to pull data from the list
	 * NOTE:  While redundant, this code can still run if there is no currentRun; it just makes no real change
	 */
	public void endRun(){
		currentRun = null;
	}
	
	//NOTE:  The remaining methods simply forward the command into the nested Run class and return accordingly;
	//this only applies when the currentRun is actually pointing to a Run object - if null, they do nothing
	public String printRun(Calendar time){
		if(currentRun != null) return currentRun.printRun(time);
		else return "There is no current run in progress.";}
	public void num(int number){if(currentRun != null) currentRun.num(number);}
	public void clr(int number){if(currentRun != null) currentRun.clr(number);}
	public void swap(){if(currentRun != null) currentRun.swap();}
	public void start(int channelNumber, Calendar time){if(currentRun != null) currentRun.start(channelNumber, time);}
	public void cancel(){if(currentRun != null) currentRun.cancel();}
	public void finish(int channelNumber, Calendar time){if(currentRun != null) currentRun.finish(channelNumber, time);}
	public void dnf(){if(currentRun != null) currentRun.dnf();}
	public String exportRun(Calendar time){if(currentRun != null) currentRun.exportRun(time);}
}
