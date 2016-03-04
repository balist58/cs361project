package cs361Project;



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
		else {
			System.exit(3);	
		}
	}
	
	/**
	 * inputCommand(): Private helper method to facilitate console command entry.
	 */
	private void inputCommand(){
		
		String cmdstring;
		Scanner sin = new Scanner(System.in);	
		System.out.println("Enter commands in format <COMMAND> <ARGLIST><EOL>:");
		
		do {
			cmdstring = sin.nextLine();	
		} while(parseLine(cmdstring, null));
		
		sin.close();
	}
	
	/**
	 * inputFile(): Private helper method to facilitate commands read from a text file.
	 */
	private void inputFile(){
		String filename;
		Scanner sin = new Scanner(System.in);	
		System.out.println("Enter filepath of text file containing commands: ");
		filename = sin.nextLine();
		sin.close();
		
		parseFile(filename);
	}
	
	/**
	 * parseLine(): Private helper method that takes a string and parses into the appropriate command parts.
	 * @param cmdstring - String containing the user entered command line to parse.
	 */
	private boolean parseLine(String cmdstring, String timeToUpdateTo){
		//Break up the command from the arguments
		String[] tokens = cmdstring.split(" ");
		this.argList.clear(); //TODO remove this
		this.cmd = tokens[0];
		
		for(int i = 1; i < tokens.length; i++){
			this.argList.add(tokens[i]);
		}
		if(timeToUpdateTo == null) {
			ct.updateTimeToCurrent();
		} else {
			ct.time(timeToUpdateTo);
		}
		return execute(timestamp, cmd, argList);
		
	}
	
	/**
	 * parseFile(): Private helper method that takes a string containing a file name full of commands to parse.
	 * @param filename - Name of text file to read commands from.
	 */
	private void parseFile(String fileName){

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
            	//Separate the time out from the command
            	String[] tokens = line.split("\t");
            	this.parseLine(tokens[1], tokens[0]);
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");                  
            ex.printStackTrace();
        }
	}
	
	/**
	 * execute(): Private helper method that executes the given command within the passed ChronoTimerControl object.
	 * @param timestamp - Timestamp parsed from command line.
	 * @param cmd - command literal parsed from command line
	 * @param argList - List of arguments to pass to command.
	 * @param ct - ChronoTimerControl object to execute commands from.
	 */
	private boolean execute(String timestamp, String cmd, ArrayList<String> argList){
		
		switch(cmd.toUpperCase())
		{
			case "ON":
				ct.on();
				break;
			case "OFF":
				ct.off();
				break;
			case "RESET":
				ct.reset();
				break;
			case "TIME":
				ct.time(argList.get(0));
				break;
			case "EVENT" :
				ct.event(argList.get(0));
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
			case "TOG": case "TOGGLE":
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
			case "EXIT":
				return false;
			default : break;
				
		}
		return true;
	}
	
}
