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

public class ChronoTimerControl{
	/**
	 * Controller fields - a pointer to the ChronoTimer system, and the system's "on/off switch" which restricts commands
	 */
	private ChronoTimerSystem system;  //references the ChronoTimer system to which the controller relays commands
	private boolean enabled;           //controls whether or not commands can be made
	
	/**
	 * The Controller constructor initializes the System, but leaves the interface in a disabled state
	 */
	public ChronoTimerControl(){
		system = new ChronoTimerSystem();
		enabled = false;
	}
	
	/**
	 * Simple getters and setters for the controller fields
	 * 
	 */
	public ChronoTimerSystem getSystem(){return system;}
	public boolean isEnabled(){return enabled;}
	public void setEnabled(boolean setTo){enabled = setTo;}
	public void setSystem(ChronoTimerSystem newSystem){system = newSystem;}
	
	/**
	 * Controller.execute takes a command String of the form [<COMMAND> <ARG1(opt)> <ARG2(opt)>], and converts it into a
	 * method call to either the ChronoTimer System, one of the Channels, or the current active Run based on the leading command
	 * @param String cmdString - a String composed of the command and args, separated by spaces.  Any other string will be invalid.
	 * @return boolean - only returns false when the "EXIT" command was called, to be relayed back to the calling object
	 */
	public boolean execute(String cmdString){
		//Escape out if the string is null, to avoid null pointers
		if(cmdString == null){
			System.out.println("ERROR: Unable to execute command; no command was entered!");
			return true;
		}
		else{
			//Parse the command string into a String for the command, and an ArrayList for any arguments
			ArrayList<String> cmdArgs = new ArrayList<String>();
			String[] tokens = cmdString.split(" ");
			String cmd = tokens[0].toUpperCase();                             //Set the command itself to a String called "cmd"
			for(int i = 1; i < tokens.length; ++i) cmdArgs.add(tokens[i]);    //Arguments, if any, go into an ArrayList called "cmdArgs"
		
			//If the controller is currently off, only accept the "ON" command, or commands relating to connecting sensors
			if(!this.isEnabled()){
				switch(cmd){
				case "ON":
					this.setEnabled(true);
					break;
				case "CONN":
					if(cmdArgs.size() == 2) this.getSystem().getChannel(Integer.parseInt(cmdArgs.get(0))).conn(cmdArgs.get(1)); //NOTE: Removed -1 from get(0)-1
					break;
				case "DISC":
					if(cmdArgs.size() == 1) this.getSystem().getChannel(Integer.parseInt(cmdArgs.get(0))).disc();
					break;
			    default: System.out.println("Error: Unable to execute command; the system is not on!");
				}
				return true;
			}
			//However, if the controller is on, then run the corresponding call for any command (except "ON", which is meaningless)
			else{
				switch(cmd){
				case "OFF":
					this.setEnabled(false);
					break;
				case "RESET":
					this.getSystem().reset();
					break;
				case "TIME":
					if(cmdArgs.size() == 1) this.getSystem().setTime(cmdArgs.get(0));
					else this.getSystem().setTime();
					break;
				case "EVENT" :
					if(cmdArgs.size() == 1) this.getSystem().setEvent(cmdArgs.get(0));
					break;
				case "NEWRUN":
					this.getSystem().newRun();
					break;
				case "ENDRUN":
					this.getSystem().endRun();
					break;
				case "NUM":
					if(!this.getSystem().isActive()) System.out.println("Error: Unable to execute NUM; there is no active run!");
					else if(cmdArgs.size() == 1) this.getSystem().getRun().num(Integer.parseInt(cmdArgs.get(0)));
					break;
				case "CLR":
					if(!this.getSystem().isActive()) System.out.println("Error: Unable to execute CLR; there is no active run!");
					else if(cmdArgs.size() == 1) this.getSystem().getRun().clr(Integer.parseInt(cmdArgs.get(0)));
					break;
				case "SWAP":
					if(!this.getSystem().isActive()) System.out.println("Error: Unable to execute SWAP; there is no active run!");
					else this.getSystem().getRun().swap();
					break;
				case "START":
					if(!this.getSystem().isActive()) System.out.println("Error: Unable to execute START; there is no active run!");
					else if(cmdArgs.isEmpty()) this.getSystem().getRun().start(0, this.getSystem().getTime());
					else if(cmdArgs.size() == 1) this.getSystem().getRun().start(Integer.parseInt(cmdArgs.get(0)), this.getSystem().getTime());
					break;
				case "CANCEL":
					if(!this.getSystem().isActive()) System.out.println("Error: Unable to execute CANCEL; there is no active run!");
					else this.getSystem().getRun().cancel();
					break;
				case "DNF":
					if(!this.getSystem().isActive()) System.out.println("Error: Unable to execute DNF; there is no active run!");
					else this.getSystem().getRun().dnf();
					break;
				case "FINISH":
					if(!this.getSystem().isActive()) System.out.println("Error: Unable to execute FINISH; there is no active run!");
					else if(cmdArgs.isEmpty()) this.getSystem().getRun().finish(0, this.getSystem().getTime());
					else if(cmdArgs.size() == 1) this.getSystem().getRun().finish(Integer.parseInt(cmdArgs.get(0)), this.getSystem().getTime());
					break;
				case "TOG": case "TOGGLE":
					if(cmdArgs.size() == 1) this.getSystem().getChannel(Integer.parseInt(cmdArgs.get(0))).tog(); //NOTE: removed -1  from .get(0 -1 
					break;
				case "CONN":
					if(cmdArgs.size() == 2) this.getSystem().getChannel(Integer.parseInt(cmdArgs.get(1))).conn(cmdArgs.get(0));
					break;
				case "DISC":
					if(cmdArgs.size() == 1) this.getSystem().getChannel(Integer.parseInt(cmdArgs.get(0))).disc();
					break;
				case "TRIG":
					if(cmdArgs.size() == 1){
						//the Channel.trig() method returns a command String, which is to be executed upon the method returning
						String toRun = this.getSystem().getChannel(Integer.parseInt(cmdArgs.get(0))).trig(Integer.parseInt(cmdArgs.get(0)));
						execute(toRun);
					}
					break;
				case "PRINT":
					if(cmdArgs.isEmpty()) this.getSystem().print();
					else if(cmdArgs.size() == 1) this.getSystem().print(Integer.parseInt(cmdArgs.get(0)));
					break;
				case "EXPORT":
					if(cmdArgs.isEmpty()) this.getSystem().export();
					else if(cmdArgs.size() == 1) this.getSystem().export(Integer.parseInt(cmdArgs.get(0)));
					break;
				case "EXIT":
					return false;
				default : break;
				}//END OF SWITCH
				return true;
			}
		}
	}

}