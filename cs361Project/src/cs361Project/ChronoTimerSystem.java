/**
 * Nicholas Bialk @nbbialk
 * CS361 ChronoTimer Project
 * ChronoTimerSystem.java
 * 
 * The ChronoTimer System class is in charge of holding active pointers to its component classes (the channels and runs), as well
 * as system information (such as the current system time and the log of previous runs)
 */

package cs361Project;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class ChronoTimerSystem {
	/****************
	 * SYSTEM FIELDS
	 ****************/
	private Channel[] channels;         //represents the channels connected to the ChronoTimer system
	private final int NUMCHANNELS = 12; //this is the maximum number of channels supported by the ChronoTimer hardware
	private String eventType;           //keeps track of the current event type (IND, PARIND, GRP, PARGRP)
	private Run activeRun;              //a pointer to the currently active run, if there is one
	private Calendar time;              //the system time (N.B. this can be manually or automatically set)
	private ArrayList<Run> runList;     //the log of all runs that have been run since system initialization/reset
	private int runCounter;             //the count of all active runs since system initialization/reset
	
	/**
	 * The System constructor initializes the channel array with new default Channels
	 * and sets event data to null until the event type can be specified using the EVENT command
	 */
	public ChronoTimerSystem(){
		channels = new Channel[NUMCHANNELS];
		for(int i = 0; i < channels.length; ++i){
			channels[i] = new Channel();
		}
		eventType = "IND";
		activeRun = null;
		time = new GregorianCalendar();
		runList = new ArrayList<Run>();
		runCounter = 0;
	}
	
	/**
	 * System.reset sets the ChronoTimer system functions back to defaults - this includes the current
	 * event, active run, system time, and run counter, as well as clearing the run log
	 */
	public void reset(){
		eventType = "IND";
		activeRun = null;
		time = new GregorianCalendar();
		runList.clear();
		runCounter = 0;
	}
	
	/*********************
	 * ACCESSOR FUNCTIONS
	 *********************/
	
	/**
	 *Getter methods for the connected channels
	 *@param a specific channel number (not required)
	 *@return the channel array, or an individual channel if specified
	 */
	public Channel[] getChannels(){return channels;}
	//Overloaded getChannels method for a specific channel number
	public Channel getChannel(int chanNumber){
		if(chanNumber <= 0 || chanNumber > NUMCHANNELS){
			System.out.println("Invalid channel number!");
			return null;
		}
		else return this.getChannels()[chanNumber-1];
	}
	
	/**
	 * Getter method for the system time
	 * @return the system time, as a Calendar object
	 */
	public Calendar getTime(){return time;}
	
	
	/**
	 * Getter method for the event type
	 * @return the event type, as a String object
	 */
	public String getEvent(){return eventType;}
	
	/**
	 * Getter methods for runs in the system
	 * @param a specific run number (not required)
	 * @return the current run, or the run of the number specified
	 */
	public Run getRun(){
		if(activeRun == null){
			System.out.println("WARNING: There is no active run!");
			return null;
		}
		else return activeRun;
	}
	//Overloaded getRun method for a specific run number
	public Run getRun(int runNumber){
		if(runNumber < 0 || runNumber >= runList.size()){
			System.out.println("Invalid run number!");
			return null;
		}
		else return this.getRunList().get(runNumber);
	}
	/**
	 * System.isActive - helper method, tracks active run
	 * @return boolean - whether or not there is an active run
	 */
	public boolean isActive(){return activeRun != null;}
	
	/**
	 * Getter method for the run log
	 * @return a reference to the system run log
	 */
	public ArrayList<Run> getRunList(){return runList;}
	
	/**
	 * Getter method for the current run count
	 * @return the current run count
	 */
	public int getRunCount(){return runCounter;}
    
	/********************
	 * MUTATOR FUNCTIONS
	 ********************/

	/**
	 * System.setTime can either take no argument (in which case it sets the system back to the current
	 * time), or a String of valid format (in which case it sets the system time to the specified time)
	 * @param time - none; or a String, which must be of the format "HH:mm:ss.S", or this method will crash
	 */
	public void setTime() {
		time = new GregorianCalendar();
	}
	public void setTime(String timeString){
		if (timeString.matches("^\\d{2}:\\d{2}:\\d{2}.\\d{1,2}")) {
			String[] timeParts = timeString.split(":");
			String[] secondParts = timeParts[2].split("\\.");
			int hour = Integer.parseInt(timeParts[0]);
			int minute = Integer.parseInt(timeParts[1]);
			int second = Integer.parseInt(secondParts[0]);
			int millisecond = Integer.parseInt(secondParts[1]);
			if((hour >= 0 && hour < 24) && (minute >= 0 && minute < 60) &&
					(second >=0 && second < 60) && (millisecond >=0 && millisecond < 100)){
				time.set(Calendar.HOUR_OF_DAY, hour);
				time.set(Calendar.MINUTE, minute);
				time.set(Calendar.SECOND, second);
				time.set(Calendar.MILLISECOND, millisecond);
			}
		}
		else System.out.println("Error: Cannot set time, invalid date format!");
	}
	
	/**
	 * System.setEvent sets the event type to that specified by its argument, but only if it is valid;
	 * changing the event is not allowed while there is currently a run in progress!
	 * @param String type - defines which event type to switch to (must be one of: IND, PARIND, GRP, PARGRP)
	 */
	public void setEvent(String type){
		if(this.getRun() != null) System.out.println("Error: Cannot change event types while a run is in progress!");
		else if(type.equalsIgnoreCase("IND") || type.equalsIgnoreCase("PARIND") || type.equalsIgnoreCase("GRP") || type.equalsIgnoreCase("PARGRP")){
			eventType = type;
		}
		else System.out.println("Error: Cannot set event type; invalid event type!");
	}

	
	/**
	 * System.newRun is a mutator for the activeRun field; it will initialize a new run, but only
	 * if there is not already an active run in progress - if there is, it prints an error and does nothing
	 */
	public void newRun(){
		if(!this.isActive()){
			Run newRun;
			++runCounter;
			switch(eventType){
			case "IND":
				newRun = new RunIND(this.getRunCount());
				activeRun = newRun;
				this.getRunList().add(this.getRun());
				break;
			case "PARIND":
				newRun = new RunPARIND(this.getRunCount());
				activeRun = newRun;
				this.getRunList().add(this.getRun());
				break;
			case "GRP":
				newRun = new RunGRP(this.getRunCount());
				activeRun = newRun;
				this.getRunList().add(this.getRun());
				break;
			//case "PARGRP":
				//newRun = new RunPARGRP(this.getRunCount());
				//activeRun = newRun;
				//this.getRunList().add(this.getRun());
				//break;
			default:
				System.out.println("Error: Cannot start a new run; invalid event type!");
			}
		}
		else System.out.println("Error: Cannot start new run; there is already a run in progress!");
	}
	/**
	 * System.endRun is the other mutator for the activeRun field; it will set the field back to null, but
	 * only if there is an active run in progress - if there is not, it prints and error and does nothing
	 */
	public void endRun(){
		if(this.isActive()) activeRun = null;
		else System.out.println("Error: Cannot end run; there is no run in progress!");
	}

	//N.B.- The following methods are meant for debugging ONLY - use at your own risk!
	
	//public void setRun(Run toRun){activeRun = toRun;}
	//public void setRunList(ArrayList<Run> toRunList){runList = toRunList;}
	//public void setRunListIndex(int index, Run toRun){runList.set(index, toRun);}
	//public void setRunCount(int toRunCount){runCounter = toRunCount;}
	
	/*******************
	 * MEMBER FUNCTIONS
	 *******************/
	
	/**
	 * Controller.print prompts the activeRun (or a specified run) for a full log of its status; this is returned back
	 * in the form of a String, which is then printed to the console
	 * @param - none; or int - the run number of the run to print
	 */
	public void print(){
		if(this.isActive()) {
			System.out.print(this.getRun().printRun(this.getTime()));
		} else {
			System.out.println("Error: Cannot print active run, there is no active run!");
		}
	}
	public void print(int runNumber){
		Run toPrint = this.getRun(runNumber);
		if(toPrint != null) System.out.print(toPrint.printRun(this.getTime()));
		else System.out.println("Error: Could not print run number " + runNumber + ", run not found!");
	}
	
	/**
	 * Controller.export prompts the activeRun (or a specified run) to run a type-dependent conversion to a JSON-formatted
	 * String; this String is then returned back to the calling Object
	 * @param - none; or int - the run number of the run to print
	 * @return - String - a JSON-formatted representation of the requested run
	 */
	public String export(){
		if(this.isActive()){
			String export = this.getRun().exportRun(this.getTime());
			return export;
		}
		else{
			System.out.println("There is no current run to export!");
			return null;
		}
	}
	public String export(int runNumber){
		Run toExport = this.getRun(runNumber);
		if(toExport != null){
			String export = toExport.exportRun(this.getTime());
			return export;
		}
		else{
			System.out.println("Error: Could not export run number " + runNumber + ", run not found!");
			return null;
		}
	}
}

