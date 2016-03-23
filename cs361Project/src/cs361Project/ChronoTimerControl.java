/**
 * Nicholas Bialk @nbbialk
 * CS361 ChronoTimer Project
 * ChronoTimerControl.java
 * 
 * The ChronoTimer Control class is in charge of directing commands to and from the various input and
 * output interfaces of the ChronoTimer.  That includes user input from the UI and the sensor channels
 * as well as output to the printer, back to the UI, and to file.
 */

package cs361Project;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class ChronoTimerControl {
	private class Channel{
		/**
		 * Channel fields - a boolean representing whether the channel has been toggled on, and a String
		 * which contains a String representing which type of sensor is being used
		 */
		private boolean enabled;
		private String sensor;
		
		/**
		 * The constructor sets the channel as disabled by default, with no sensor plugged in
		 */
		public Channel(){
			enabled = false;
			sensor = null;
		}
		
		/**
		 * Getters for the channel fields
		 */
		public boolean getEnabled(){return enabled;}
		public String getSensor(){return sensor;}
		
		/**
		 * Control.Channel.conn changes the sensor that is connected to the device
		 * @param sen - a String representing which sensor is being used
		 */
		public void conn(String sen){sensor = sen.toUpperCase();}
		
		/**
		 * Control.Channel.disc changes the connected sensor back to null
		 */
		public void disc(){sensor = null;}
		
		/**
		 * Control.Channel.tog switches the enabled state from off to on, or from on to off
		 */
		public void tog(){enabled = !enabled;}
		
		/**
		 * Control.Channel.trig makes a context-dependent action, depending on what devices is plugged in;
		 * if the current sensor is set to null, or the channel is not enabled, it does nothing;
		 * if a gate is plugged in, it sends a start() command to the specified Event at the specified time;
		 * if a pad or eye is plugged in, it sends a finish() command to the specified Event at the specified time
		 */
		public void trig(Event event, int channelNumber, Calendar time){
			if(enabled && (sensor != null)){
				if(sensor.equals("GATE")) event.start(channelNumber, time);
				else if(sensor.equals("EYE") || sensor.equals("PAD")) event.finish(channelNumber, time);
			}
		}
	}
	
	/**
	 * Controller fields - an array representing the channels connected to the ChronoTimer; the current system time;
	 * a pointer to the current event object, which we'll be using to forward commands regarding runs; a boolean that
	 * will flip when a run is in progress (to lock certain commands); the current system on/off state; and a running
	 * total count of all runs that have been made since the system was turned on/reset
	 */
	private Channel[] channels;
	private final int NUMCHANNELS = 12; //note this number of channels is subject to change depending on hardware specs
	private Event event;
	private Calendar time;
	private ArrayList<Event> eventList;
	private int runCounter;
	private String export;
	//NOTE:  *all* of the controller methods (except 'on') are set to not run unless the system is flagged as enabled;
	//this is a virtualization representing the system being physically turned on
	private boolean enabled;

	
	
	/**
	 * The Controller constructor initializes the channel array with new default Channels
	 * and sets event data to null until the event type can be specified using the EVENT command
	 */
	public ChronoTimerControl(){
		channels = new Channel[NUMCHANNELS];
		for(int i = 0; i < channels.length; ++i){
			channels[i] = new Channel();
		}
		event = null;
		time = null;
		enabled = false;
		eventList = new ArrayList<Event>();
		runCounter = 0;
	}
	
	/**
	 * Controller.on switches the controller to an enabled state, and initializes the other fields,
	 * with the exception of the "event" field, which must be initialized via event() call
	 */
	public void on(){
		if (!enabled) {
			time = new GregorianCalendar();
			enabled = true;
			eventList = new ArrayList<Event>();
			runCounter = 0;
		}
	}
	
	/**
	 * Controller.off is the converse of on(); it flips the enabled field to false, and wipes all fields
	 */
	public void off(){
		if (enabled) {
			event = null;
			time = null;
			enabled = false;
			eventList = null;
			runCounter = 0;
		}
	}
	
	/**
	 * Controller.reset sets the channel states and eventList back to defaults, removes the current event,
	 * and sets the system time back to its default (the current system time)
	 */
	public void reset(){
		if (enabled) {
			for (int i = 0; i < channels.length; ++i) {
				channels[i] = new Channel();
			}
			event = null;
			time = new GregorianCalendar();
			eventList.clear();
			runCounter = 0;
		}
	}
	
	public void updateTimeToCurrent() {
		time = new GregorianCalendar();
	}
	
	/**
	 * Controller.time sets the system timer to the time declared in the specified time parameter; the time
	 * must be a valid one on a 24-hour clock, or the method will do nothing
	 * @param time - a String, which must be of the format "HH:mm:ss.S", or this method will crash
	 */
	public void time(String timeString){
		if (enabled) {
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
	}
	
	/**
	 * Controller.event instantiates a new event, and makes it into the current event in the controller; it will
	 * also automatically instantiate a new run of the chosen event type
	 * @param type - specifies which of the race events is being initialized
	 */
	public void event(String type){
		if(enabled){
			if(event != null && event.getCurrentRun() != null) System.out.println("Error: Cannot change event types while a run is in progress!");
			else{
				event = new Event(type.toUpperCase());
				++runCounter;
				event.newRun(runCounter);
				eventList.add(event);
			}
		}
	}
	
	/**
	 * The following commands get forwarded to the corresponding Channel in channels; only the trig() method
	 * requires additional input - it specifies the current event, and will do nothing if there is none
	 * @param chan - specifies with which channel in the channels array the method is to interact
	 * @param sen - a String representing what sensor is plugged in, for conn() (one of: GATE, PAD, EYE)
	 */
	public void tog(int chan){
		if(enabled){
			if(channels == null) System.out.println("Error: Cannot toggle; channels have not been initialized!");
			else if(chan < 0 || chan > channels.length) System.out.println("Error: Cannot toggle; specified channel is out of bounds");
			else if(channels[chan-1] == null) System.out.println("Error: Cannot toggle; channel " + chan + " has not been initialized!");
			else channels[chan-1].tog();
		}
	}
	public void conn(String sen, int chan){
		if(enabled){
			if(channels == null) System.out.println("Error: Cannot connect; channels have not been initialized!");
			else if(chan < 0 || chan > channels.length) System.out.println("Error: Cannot connect; specified channel is out of bounds");
			else if(channels[chan-1] == null) System.out.println("Error: Cannot connect; channel " + chan + " has not been initialized!");
			else if(channels[chan-1].getSensor() != null) System.out.println("Error: Cannot connect; channel " + chan + " already has a device connected!");
			else channels[chan-1].conn(sen);
		}
	}
	public void disc(int chan){
		if(enabled){
			if(channels == null) System.out.println("Error: Cannot disconnect; channels have not been initialized!");
			else if(chan < 0 || chan > channels.length) System.out.println("Error: Cannot disconnect; specified channel is out of bounds");
			else if(channels[chan-1] == null) System.out.println("Error: Cannot disconnect; channel " + chan + " has not been initialized!");
			else if(channels[chan-1].getSensor() == null) System.out.println("Error: Cannot disconnect; channel " + chan + " has no device connected!");
			else channels[chan-1].disc();
		}
	}
	public void trig(int chan){
		if(enabled){
			if(event == null) System.out.println("Error: Cannot trigger; no event has been initialized!");
			else if(event.getCurrentRun() == null) System.out.println("Error: Cannot trigger; no run is in progress!");
			else if(channels == null) System.out.println("Error: Cannot trigger; channels have not been initialized!");
			else if(chan < 0 || chan > channels.length) System.out.println("Error: Cannot trigger; specified channel is out of bounds");
			else if(channels[chan-1] == null) System.out.println("Error: Cannot trigger; channel " + chan + " has not been initialized!");
			else if(!channels[chan-1].getEnabled()) System.out.println("Error: Cannot trigger; channel " + chan + " is disabled!");
			else channels[chan-1].trig(event, chan, time);
		}
	}
	
	/**
	 * The following commands get forwarded to the current event; if there is no current event, they do nothing
	 * @param number - the number of the runner, only needed for the Event.Run.num() and Event.Run.clr() commands
	 */
	public void newRun(){
		if(enabled){
			if(event == null) System.out.println("Error: Cannot start run; there is no current event!");
			if(event.getCurrentRun() != null) System.out.println("Error: Please close the current run before starting a new one!");
			else{
				++runCounter;
				event.newRun(runCounter);
			}
		}
	}
	public void endRun(){
		if(enabled){
			if(event == null) System.out.println("Error: Cannot end run; there is no current event!");
			else if(event.getCurrentRun() == null) System.out.println("Error: Cannot end run; there is no run in progress!");
			else event.endRun();
		}
	}
	public void num(int number){
		if(enabled){
			if(event == null || event.getCurrentRun() == null) System.out.println("Error: Cannot add runner; there is no run in progress!");
			else event.num(number);
		}
	}
	public void clr(int number){
		if(enabled){
			if(event == null || event.getCurrentRun() == null) System.out.println("Error: Cannot clear runner; there is no run in progress!");
			else event.clr(number);
		}
	}
	public void swap(){
		if(enabled){
			if(event == null || event.getCurrentRun() == null) System.out.println("Error: Cannot swap runners; there is no run in progress!");
			else event.swap();
		}
	}
	public void start(){
		if(enabled){
			if(event == null || event.getCurrentRun() == null) System.out.println("Error: Cannot start; there is no run in progress!");
			else event.start(0, time);
		}
	}
	public void cancel(){
		if(enabled){
			if(event == null || event.getCurrentRun() == null) System.out.println("Error: Cannot cancel; there is no run in progress!");
			else event.cancel();
		}
	}
	public void dnf(){
		if(enabled){
			if(event == null || event.getCurrentRun() == null) System.out.println("Error: Cannot set as DNF; there is no run in progress!");
			else event.dnf();
		}
	}
	public void finish(){
		if(enabled){
			if(event == null || event.getCurrentRun() == null) System.out.println("Error: Cannot finish; there is no run in progress!");
			else event.finish(0, time);
		}
	}
	
	/**
	 * Controller.print prompts the currentEvent's currentRun for a full log of its status; this is returned back
	 * in the form of a String, which is then printed to the console
	 */
	public void print(){
		if(event != null) {
			System.out.print(event.printRun(time));
		} else {
			System.out.println("No event to print");
		}
	}
	/**
	 * Controller.print(runNumber) is an overloaded version of print, that searches for the specified run number
	 * in the ChronoTimer's event log, and once retrieved, prints the information from that run to console
	 * @param runNumber - the run number to print out information from
	 */
	public void print(int runNumber){
		Run toPrint = null;
		for(Event e : eventList){
			for(Run r : e.getRuns()){
				if(r.getRunNumber() == runNumber) toPrint = r;
			}
		}
		if(toPrint != null) toPrint.printRun(time);
		else System.out.println("Error:  Cannot print run " +runNumber + ", run not found");
	}
	
	/**
	 * Controller.export(runNumber) searches for the specified run number in the ChronoTimer's event log; once it
	 * finds the specified run, it runs a subtype-dependent method inside of the run to convert that run's data
	 * into a JSON-formatted string.
	 */
	public void export(){
		if(event != null) export = event.exportRun(time);
		else  System.out.println("There is no current run to export!");
	}
	public void export(int runNumber){
		//TODO:  Implement EXPORT function!
		Run toExport = null;
		for(Event e : eventList){
			for(Run r : e.getRuns()){
				if(r.getRunNumber() == runNumber) toExport = r;
			}
		}
		if(toExport != null) toExport.exportRun(time);
		else System.out.println("Error:  Cannot export run " + runNumber + ", run not found!");
	}
}
