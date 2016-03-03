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
		 * The constructor doesn't do much - it sets the channel as disabled, with nothing plugged in
		 */
		public Channel(){
			enabled = false;
			sensor = null;
		}
		
		/**
		 * Control.Channel.conn changes the sensor that is connected to the device
		 * @param sen - a String representing which sensor is being used
		 */
		public void conn(String sen){sensor = sen;}
		
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
				if(sensor == "GATE") event.start(time);
				else if(sensor == "EYE" || sensor == "PAD") event.finish(time);
			}
		}
	}
	
	/**
	 * Controller fields - an array representing the channels connected to the ChronoTimer; the current system time;
	 * a pointer to the current event object, which we'll be using to forward commands regarding runs; and a list
	 * of all of the events that have been run since the system was turned on (or reset)
	 */
	private Channel[] channels;
	private ArrayList<Event> eventList;
	private Event event;
	private Calendar time;
	//private LocalDateTime time2;
	//private Clock clock;
	
	
	/**
	 * The Controller constructor (run through the ON command) initializes the channel array with new default Channels
	 * and sets event data to null until the event type can be specified using the EVENT command
	 */
	public ChronoTimerControl(){
		channels = new Channel[12]; //note this number of values is subject to change depending on hardware specs
		for(int i = 0; i < channels.length; ++i){
			channels[i] = new Channel();
		}
		eventList = new ArrayList<Event>();
		event = null;
		time = new GregorianCalendar();
	}
	
	/**
	 * Controller.reset is much like the constructor, but doesn't need to re-initialize the channels or eventList;
	 * instead, it overwrites the channels with (new) default states and clears the eventList
	 */
	public void reset(){
		for(int i = 0; i < channels.length; ++i){
			channels[i] = new Channel();
		}
		eventList.clear();
		event = null;
		time = new GregorianCalendar();
	}
	
	/**
	 * Controller.time sets the system timer to the time declared in the specified time parameter
	 * @param time - a String, which must be of the format "HH:mm:ss"
	 */
	public void time(String timeString){
		
		String delims = (":|\\.");
		
		String[] timeParts = timeString.split(delims);
		time.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeParts[0]));
		time.set(Calendar.MINUTE, Integer.parseInt(timeParts[1]));
		time.set(Calendar.SECOND, Integer.parseInt(timeParts[2]));
		time.set(Calendar.MILLISECOND, Integer.parseInt(timeParts[3]));
		
		//System.out.println(time.getTime());
	}
	
	/**
	 * Controller.event instantiates a new event
	 * @param type
	 */
	public void event(String type){
		//TODO:  Implement multiple types of events based on the "type" String specified in the command line
		event = new Event();
		eventList.add(event);
	}
	
	/**
	 * The following commands get forwarded to the corresponding Channel in channels; only the trig() method
	 * requires additional input - it specifies the current event, and will do nothing if there is none
	 * @param chan
	 */
	public void tog(int chan){channels[chan].tog();}
	public void conn(String sen, int chan){channels[chan].conn(sen);}
	public void disc(int chan){channels[chan].disc();}
	public void trig(int chan){if(event != null) channels[chan].trig(event, time);}
	
	/**
	 * The following commands get forwarded to the current event; if there is no current event, they do nothing
	 */
	public void newRun(){event.newRun();}
	public void endRun(){event.endRun();}
	public void num(int number){event.num(number);}
	public void clr(int number){event.clr(number);}
	public void swap(){event.swap();}
	public void start(){event.start(time);}
	public void cancel(){event.cancel();}
	public void dnf(){event.dnf();}
	public void finish(){event.finish(time);}
	
	/**
	 * Controller.print prompts the currentEvent's currentRun for a full log of its status; this is returned back
	 * in the form of a String, which is then printed to the console
	 */
	//TODO:  Change the print() method to output to a different stream once a proper GUI is implemented
	public void print(){System.out.print(event.print());}
}
