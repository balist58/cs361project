package cs361Project;



import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class: CmdInterface.java
 * The CmdInterface class parses commands via the console or a text file so that the ChronoTimerControl class can interact as-if hardware was connected.
 * @author Matt Balistreri
 * @version 1.0
 */
public class CmdInterface {
	
	//<TIMESTAMP> 	<CMD> <ARGUMENT LIST>	<EOL>
	private String timestamp;
	private String cmd;
	private ArrayList<String> argList;
	private int inputType;
	ChronoTimerControl ct;
	
	
	/**
	 * Constructor: Creates an instance of CmdInterface with an input type and the active ChronoTimer.
	 * @param inputType @values 1,2,_ : 1 allows user to input a command via console. 2 allows user to input command text file, anything else exits.
	 * @param ct - Chronotimer object that is currently in use.
	 */
	public CmdInterface(int inputType,ChronoTimerControl ct){
		
		this.inputType = inputType;
		this.ct = ct;
		this.argList = new ArrayList<String>();
	}
	
	/**
	 * go(): Starts the command gathering/parsing process.
	 */
	public void go(){
		
		if(this.inputType == 1){
			this.inputCommand();
		}
		else if(this.inputType == 2){
			this.inputFile();
		}
		else 
			System.exit(3);	
	}
	
	/**
	 * inputCommand(): Private helper method to facilitate console command entry.
	 */
	private void inputCommand(){
		
		String cmdstring;
		Scanner sin = new Scanner(System.in);	
		System.out.println("Enter command in format: <TIMESTAMP> <COMMAND> <ARGLIST><EOL>");
		cmdstring = sin.nextLine();
		sin.close();
		
		parseLine(cmdstring);
	}
	
	/**
	 * inputFile(): Private helper method to facilitate commands read from a text file.
	 */
	private void inputFile(){
		String filename;
		Scanner sin = new Scanner(System.in);	
		System.out.println("Enter name of text file containing commands: >");
		filename = sin.nextLine();
		sin.close();
		
		parseFile(filename);
	}
	
	/**
	 * parseLine(): Private helper method that takes a string and parses into the appropriate command parts.
	 * @param cmdstring - String containing the user entered command line to parse.
	 */
	private void parseLine(String cmdstring){
		String delims = "[ ]+|\t";
		String[] tokens = cmdstring.split(delims);
		
		
		
		for(int i = 0; i < tokens.length; i++){
			
			if(i==0){
				this.timestamp = tokens[0];
			}
			
			else if(i==1){
				this.cmd = tokens[1];
			}
			else{
				this.argList.add(tokens[i]);
			}
		}
		
		execute(timestamp, cmd, argList,ct);
		
	}
	
	/**
	 * parseFile(): Private helper method that takes a string containing a file name full of commands to parse.
	 * @param filename - Name of text file to read commands from.
	 */
	private void parseFile(String filename){
		
	}
	
	/**
	 * execute(): Private helper method that executes the given command within the passed ChronoTimerControl object.
	 * @param timestamp - Timestamp parsed from command line.
	 * @param cmd - command literal parsed from command line
	 * @param argList - List of arguments to pass to command.
	 * @param ct - ChronoTimerControl object to execute commands from.
	 */
	private void execute(String timestamp, String cmd, ArrayList<String> argList,ChronoTimerControl ct){
		
		switch(cmd.toUpperCase())
		{
		case "ON":
		case "OFF":
		case "TIME":
			ct.time(argList.get(0));
			break;
		case "NEWRUN":
			ct.newRun();
			break;
		case "ENDRUN":
			ct.endRun();
			break;
		case "NUM":
			ct.num(Integer.parseInt(argList.get(0)));
			break;
		case "CLR":
			ct.clr(Integer.parseInt(argList.get(0)));
			break;
		case "SWAP":
			ct.swap();
			break;
		case "START":
			ct.start();
			break;
		case "CANCEL":
			ct.cancel();
			break;
		case "DNF":
			ct.dnf();
			break;
		case "FINISH":
			ct.finish();
			break;
		case "TOG":
			ct.tog(Integer.parseInt(argList.get(0)));
			break;
		case "CONN":
			ct.conn(argList.get(0),Integer.parseInt(argList.get(1)));
			break;
		case "DISC":
			ct.disc(Integer.parseInt(argList.get(0)));
			break;
		case "TRIG":
			ct.trig(Integer.parseInt(argList.get(0)));
			break;
		case "PRINT":
			ct.print();
			break;
		default : break;
			
		}	
	}
	
}
