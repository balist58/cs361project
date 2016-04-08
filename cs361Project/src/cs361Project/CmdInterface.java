package cs361Project;



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Class: CmdInterface.java
 * The CmdInterface class parses commands via the console or a text file so that the ChronoTimerControl class can interact as-if hardware was connected.
 * @author Matt Balistreri
 * @version 1.0
 */
public class CmdInterface {
	
	/**
	 * CMD interface fields
	 */
	private int inputType;            //simple method of controlling from which type of input commands are being read
	private ChronoTimerControl ct;    //reference to the Controller object to which the interface is relaying commands
	
	/**
	 * Constructor: Creates an instance of CmdInterface with an input type and the active ChronoTimer.
	 * @param inputType @values 1,2,_ : 1 allows user to input a command via console. 2 allows user to input command text file, anything else exits.
	 * @param ct - Chronotimer object that is currently in use.
	 */
	public CmdInterface(int inputType,ChronoTimerControl ct){
		this.inputType = inputType;
		this.ct = ct;
	}
	
	/**
	 * Simple getters and setters for the interface class
	 */
	public int getInputType(){return inputType;}
	public ChronoTimerControl getCT(){return ct;}
	public void setInputType(int type){inputType = type;}
	public void setCT(ChronoTimerControl controller){ct = controller;}
	
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
		} while(this.getCT().execute(cmdstring));
		
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
            	this.getCT().execute("TIME " + tokens[0]);
            	this.getCT().execute(tokens[1]);
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
}
