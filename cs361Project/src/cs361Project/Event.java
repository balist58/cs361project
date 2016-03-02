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
	 * and a pointer to whichever run is currently in progress (if any)
	 */
	private ArrayList<Run> runs;
	private Run currentRun;
	
	/**
	 * The constructor for the Event class instantiates an empty ArrayList for the runs;
	 * it does not instantiate a currentRun until specified to do so by the newRun command
	 */
	public Event(){
		runs = new ArrayList<Run>();
		currentRun = null;
	}
	
	/**
	 * Event.newRun instantiates a new run object, sets it as the current run, and adds it to the runs database;
	 * if there is already a currentRun, then the method does nothing
	 */
	public void newRun(){
		if(currentRun == null){
			currentRun = new Run(runs.size() + 1);
			runs.add(currentRun);
		}
	}
	
	/**
	 * Event.endRun removes the "currentRun" pointer; the run still exists in the runs arrayList, but it can no longer
	 * be interacted with through any of the methods in the Event class, save to pull data from the list
	 * NOTE:  While redundant, this code can still run if there is no currentRun; it just makes no real change
	 */
	public void endRun(){
		currentRun = null;
	}
	
	//NOTE:  The remaining methods simply forward the command into the nested Run class and return accordingly;
	//this only applies when the currentRun is actually pointing to a Run object - if null, they do nothing
	public String print(){
		if(currentRun != null) return currentRun.print();
		else return "There is no current run in progress.";}
	public void num(int number){if(currentRun != null) currentRun.num(number);}
	public void clr(int number){if(currentRun != null) currentRun.clr(number);}
	public void swap(){if(currentRun != null) currentRun.swap();}
	public void start(Calendar time){if(currentRun != null) currentRun.start(time);}
	public void cancel(){if(currentRun != null) currentRun.cancel();}
	public void finish(Calendar time){if(currentRun != null) currentRun.finish(time);}
	public void dnf(){if(currentRun != null) currentRun.dnf();}
}
