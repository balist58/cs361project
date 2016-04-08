/**
 * Nicholas Bialk @nbbialk
 * CS361 ChronoTimer Project
 * Channel.java
 * 
 * The Channel class is a representation of the physical channels that are a part of the ChronoTimer hardware; they can be
 * enabled or disabled, a sensor connected or disconnected, and can be "triggered" - which calls a start or finish event
 */


package cs361Project;

public class Channel {
	/**
	 * Channel fields - a boolean representing whether the channel has been toggled on, and a String
	 * which contains a String representing which type of sensor is being used
	 */
	private boolean enabled;  //represents whether the channel has been disabled
	private String sensor;    //represents which sensor is currently plugged into the channel
	
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
	public boolean isEnabled(){return enabled;}
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
     * if a GATE is plugged in, it triggers a START event; if an EYE or PAD is plugged in, a FINISH event is triggered
     * @return - String - a follow-up event call to be run through the Controller (START or FINISH, plus the channel number)
	 */
	public String trig(int channelNumber){
		if(this.isEnabled() && this.getSensor() != null){
			if(this.getSensor().equals("GATE")) return "START " + channelNumber;
			else if(this.getSensor().equals("EYE") || this.getSensor().equals("PAD")) return "FINISH " + channelNumber;
			else return null;
		}
		else return null;
	}
}
