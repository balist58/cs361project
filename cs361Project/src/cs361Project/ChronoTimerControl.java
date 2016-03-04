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
		public void trig(Event event, Calendar time){
			if(enabled && (sensor != null)){
				if(sensor.equals("GATE")) event.start(time);
				else if(sensor.equals("EYE") || sensor.equals("PAD")) event.finish(time);
			}
		}
	}
	
	/**
	 * Controller fields - an array representing the channels connected to the ChronoTimer; the current system time;
	 * a pointer to the current event object, which we'll be using to forward commands regarding runs; a list
	 * of all of the Events that have been run since the system was turned on (or reset); the system state (on/off)
	 */
	private Channel[] channels;
	private ArrayList<Event> eventList;
	private Event event;
	private Calendar time;
	private boolean runInProgress;

	//private LocalDateTime time2;
	//private Clock clock;
	
	
	//NOTE:  *all* of the controller methods are set to not run unless the system is flagged as enabled; this
	//is a virtualization representing the system being physically turned on
	private boolean enabled;

	
	
	/**
	 * The Controller constructor initializes the channel array with new default Channels
	 * and sets event data to null until the event type can be specified using the EVENT command
	 */
	public ChronoTimerControl(){
		channels = new Channel[12]; //note this number of values is subject to change depending on hardware specs
		for(int i = 0; i < channels.length; ++i){
			channels[i] = new Channel();
		}
		eventList = null;
		event = null;
		time = null;
		enabled = false;
		runInProgress = false;
	}
	
	/**
	 * Controller.on switches the controller to an enabled state, and initializes the other fields,
	 * with the exception of the "event" field, which must be initialized via event() call
	 */
	public void on(){
		if (!enabled) {
			eventList = new ArrayList<Event>();
			time = new GregorianCalendar();
			enabled = true;
			runInProgress = false;
		}
	}
	
	/**
	 * Controller.off is the converse of on(); it flips the enabled field to false, and wipes all fields
	 */
	public void off(){
		if (enabled) {
			eventList = null;
			event = null;
			time = null;
			enabled = false;
			runInProgress = false;
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
			eventList.clear();
			event = null;
			time = new GregorianCalendar();
			runInProgress = false;
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

		//String delims = (":|\\.");
		
		//String[] timeParts = timeString.split(delims);
		//time.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeParts[0]));
		//time.set(Calendar.MINUTE, Integer.parseInt(timeParts[1]));
		//time.set(Calendar.SECOND, Integer.parseInt(timeParts[2]));
	    // time.set(Calendar.MILLISECOND, Integer.parseInt(timeParts[3]));
		
		//System.out.println(time.getTime());

		if (enabled) {
			String[] timeParts = timeString.split(":");
			String[] secondParts = timeParts[2].split("\\.");
			int hour = Integer.parseInt(timeParts[0]);
			int minute = Integer.parseInt(timeParts[1]);
			int second = Integer.parseInt(secondParts[0]);
			int millisecond = Integer.parseInt(secondParts[1]);
			if((hour >= 0 && hour < 24) && (minute >= 0 && minute < 60) &&
					(second >=0 && second < 60) && (millisecond >=0 && millisecond < 1000)){
				time.set(Calendar.HOUR_OF_DAY, hour);
				time.set(Calendar.MINUTE, minute);
				time.set(Calendar.SECOND, second);
				time.set(Calendar.MILLISECOND, millisecond);
			}
		}
	}
	
	/**
	 * Controller.event instantiates a new event, and makes it into the current event in the controller
	 * @param type
	 */
	public void event(String type){
		if (enabled && runInProgress) {
			event = new Event(type);
			eventList.add(event);
			event.newRun();
		}
	}
	
	/**
	 * The following commands get forwarded to the corresponding Channel in channels; only the trig() method
	 * requires additional input - it specifies the current event, and will do nothing if there is none
	 * @param chan - specifies with which channel in the channels array the method is to interact
	 * @param sen - a String representing what sensor is plugged in, for conn() (one of: GATE, PAD, EYE)
	 */
	public void tog(int chan){if(enabled)channels[chan-1].tog();}
	public void conn(String sen, int chan){if(enabled) channels[chan-1].conn(sen);}
	public void disc(int chan){if(enabled) channels[chan-1].disc();}
	public void trig(int chan){if(enabled && event != null) channels[chan-1].trig(event, time);}
	
	/**
	 * The following commands get forwarded to the current event; if there is no current event, they do nothing
	 * @param number - the number of the runner, only needed for the Event.Run.num() and Event.Run.clr() commands
	 */
	public void newRun(){if(enabled && !runInProgress) { runInProgress = true; }}
	public void endRun(){if(enabled && runInProgress && event != null) { runInProgress = false; event.endRun(); }}
	public void num(int number){if(enabled && event != null) event.num(number);}
	public void clr(int number){if(enabled && event != null) event.clr(number);}
	public void swap(){if(enabled && event != null) event.swap();}
	public void start(){if(enabled && event != null) event.start(time);}
	public void cancel(){if(enabled && event != null) event.cancel();}
	public void dnf(){if(enabled && event != null) event.dnf();}
	public void finish(){if(enabled && event != null) event.finish(time);}
	
	/**
	 * Controller.print prompts the currentEvent's currentRun for a full log of its status; this is returned back
	 * in the form of a String, which is then printed to the console
	 */
	//TODO:  Change the print() method to output to a different stream once a proper GUI is implemented
	public void print(){
		if(event != null) {
			System.out.print(event.printRun());
		} else {
			System.out.println("No event to print");
		}
	}
}
