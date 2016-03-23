/**
 * Nicholas Bialk @nbbialk
 * CS361 ChronoTimer Project
 * Run.java
 * 
 * The Run class controls the specifics for an individual run or heat at an event; this is a superclass,
 * from which an individual run is instantiated based on the event type (RunIND, RunPARIND, RunGRP, RunPARGRP)
 */

package cs361Project;

import java.util.Calendar;

public class Run{
	/**
	 * The common field for all Run types is a number denoting which overall run number this run is in the
	 * ChronoTimer system; this is used when printing or exporting run information
	 */
	protected int runNumber;
	
	/**
	 * The run constructor sets the current run number
	 * @param number - the runNumber for this run, set by its parent event during run instantiation
	 */
	public Run(int number){
		runNumber = number;
	}
	
	/**
	 * The getter for the Run's run number is common to all Run subtypes, and so is included in the supertype
	 * @return this Run's run number
	 */
	public int getRunNumber(){return runNumber;}
	
	/**
	 * Method stubs, to be individually initialized for each subtype of Run
	 */
	public String printRun(Calendar time){return null;}
	public void num(int number){}
	public void clr(int number){}
	public void swap(){}
	public void start(int channel, Calendar time){}
	public void cancel(){};
	public void finish(int channel, Calendar time){}
	public void dnf(){}
	public String exportRun(Calendar time){return null;}
}