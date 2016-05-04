/**
 * Nicholas Bialk @nbbialk
 * CS361 ChronoTimer Project
 * RunPARGRP.java
 * 
 * The RunPARGRP class controls the specifics implementation of methods in Run as pertains to the PARGRP subtype
 */

package cs361Project;

import java.util.Calendar;

import com.google.gson.Gson;

import java.util.ArrayList;

public class RunPARGRP implements Run{
	/**
	 * Fields for PARGRP - the run number, the list of "lanes" for the run and maximum allowable runners,
	 * and a list of finished runners from the race 
	 */
	int runNumber;
	Runner[] runners;
	final int MAX_RUNNERS = 8;
	ArrayList<Runner> finishedRunners;
	
	/**
	 * The RunPARGRP constructor initializes the run number that it was passed, and sets up the lanes for the
	 * race (with no Runners in any lane), and an empty list of finished runners
	 * @param runNum
	 */
	public RunPARGRP(int runNum){
		runNumber = runNum;
		runners = new Runner[MAX_RUNNERS];
		finishedRunners = new ArrayList<Runner>();
	}
	
	/**
	 * Public accessor methods for the PARGRP fields
	 */
	public int getRunNumber(){return runNumber;}
	public Runner[] getRunners(){return runners;}
	public ArrayList<Runner> getFinished(){return finishedRunners;}
	
	/**
	 * RunPARGRP.isWaiting returns true only if some runner on the list of runners has no valid start time yet
	 */
	@Override
	public boolean isWaiting(){
		boolean waiting = false;
		
		for(Runner r : getRunners()){
			if(r == null) break;
			else 
				if(r.getStartTime() == null) waiting = true;
		}
		return waiting;
	}
	
	/**
	 * RunPARGRP.isActive returns true only if some runner on the list of runners has a start time, but is not included in "finished"
	 */
	@Override
	public boolean isActive(){
		boolean active = false;
		for(Runner r : getRunners()){
			if(r == null)
			break;
			else
				if(r.getStartTime() != null && !getFinished().contains(r)) active = true;
		}
		return active;
	}
	
	/**
	 * RunPARGRP.printRun converts the current status of the run into a printable output format
	 * @return - a String, to be printed out via the system printer
	 */
	@Override
	public String printRun(Calendar time){
		String log = "Run " + getRunNumber() + "\n";
		for(int i = 0; i < MAX_RUNNERS; ++i){
			log += "LANE " + i + ": ";
			if(getRunners()[i] != null){
				log += getRunners()[i].getNumber();
				if(getRunners()[i].getStartTime() == null && !getFinished().contains(getRunners()[i])){
					log += " - Elapsed - " + getRunners()[i].getElapsed(time);
				}
				else if(getFinished().contains(getRunners()[i])){
					log += " - Total Time - " + getRunners()[i].getTotalTime();
				}
			}
			log += "\n";
		}
		return log;
	}
	
	/**
	 * RunPARGRP.exportRun converts the current run status into JSON format for export to file/web server
	 * @return - a String, to be exported from the system
	 */
	@Override
	public String exportRun(Calendar time){
		
		ExportedRun e =  new ExportedRun();
		e.raceNumber = runNumber;
		e.raceType = "PARGRP";
		
		/*for(Runner r : finishedRunners) {
			e.runners.add(new ExportedRunner(r.getNumber(), r.getTotalTime()));
		}*/
		
		for(int i = 0; i < MAX_RUNNERS; ++i){
			if(runners[i] != null) {
				e.runners.add(new ExportedRunner(runners[i].getNumber(), runners[i].getTotalTime()));
			}
		}

		Gson g = new Gson();
		return g.toJson(e);
		
		/*String ex = "{\n\"RunNumber\":";
		ex += runNumber;
		ex += ",\n\"Runners\":[\n{";
		for(int i = 0; i < MAX_RUNNERS; ++i){
			ex += "\n\"Lane\": " + (i + 1) + ",\n\"Number\": ";
			if(getRunners()[i] != null){
				ex += getRunners()[i].getNumber();
				if(getRunners()[i].getStartTime() != null && !getFinished().contains(getRunners()[i])){
					ex += ",\n\"ElapsedTime\": ";
					ex += getRunners()[i].getElapsed(time);
				}
				else if(getRunners()[i].getEndTime() == null){
					ex += ",\n\"ElapsedTime\": DNF";
				}
				else{
					ex += ",\n\"TotalTime\": ";
					ex += getRunners()[i].getTotalTime();
				}
			}
			else ex += "n/a";
			if((i + 1) != MAX_RUNNERS) ex += ",";
		}
		ex += "}\n]\n}";
		return ex;*/
	}
	
	/**
	 * RunPARGRP.printToDisplay converts the current status of the race into a String to be displayed to the main GUI display
	 * @return - a String to be displayed on the GUI
	 */
	@Override
	public String printToDisplay(Calendar time){
		String out = "";
		
		for(int i = 0; i < MAX_RUNNERS; ++i){
			out += "LANE " + (i + 1) + ": ";
			if(getRunners()[i] == null) out += "empty";
			else{
				out += getRunners()[i].getNumber();
				if(getRunners()[i].getStartTime() != null && !getFinished().contains(getRunners()[i])){
					out += " " + getRunners()[i].getElapsed(time) + " R";
				}
				else if(getRunners()[i].getEndTime() == null) out += " DNF";
				else out += " " + getRunners()[i].getTotalTime() + " F";
			}
			out += "\n";
		}
		
		return out;
	}
	
	/**
	 * RunPARGRP.num verifies runner eligibility, and if runner is verified, adds them to the next available lane
	 * @param - int - the bib number of the runner to add
	 * @return - String - the system message to print back
	 */
	@Override
	public String num(int number){
		if((number >= 0) && (number <= 99999)){
			boolean notADuplicate = true;
			for(int i = 0; i < MAX_RUNNERS; ++i){
				if(getRunners()[i] != null && getRunners()[i].getNumber() == number) notADuplicate = false;
			}
			if(notADuplicate){
				for(int i = 0; i < MAX_RUNNERS; ++i){
					if(getRunners()[i] == null){
						getRunners()[i] = new Runner(number);
						return "added runner " + number;
					}
				}
				return "error - all lanes are full";
			}
			else return "error - duplicate number";
		}
		else return "error - invalid number";
	}
	
	/**
	 * RunPARGRP.clr finds and clears the selected runner number from their current lane, if that number exists
	 * @param - int - the number to clear
	 * @return - String - the system message to print back
	 */
	@Override
	public String clr(int number){
		for(int i = 0; i < MAX_RUNNERS; ++i){
			if(getRunners()[i] != null && getRunners()[i].getNumber() == number){
				if(getRunners()[i].getStartTime() == null){
					getRunners()[i] = null;
					return "removed runner " + number;
				}
				else return "error - runner " + number + " already started";
			}
		}
		return "error - runner not found";
	}
	
	/**
	 * RunPARGRP.swap is not supported for PARGRP - if selected, it prints back an error message
	 */
	@Override
	public String swap(){
		System.out.println("Error: The swap command is not supported in the PARGRP event!");
		return "Swap unavailable for PARGRP";
	}
	
	/**
	 * RunPARGRP.start will start a race (if it isn't already started/over) - it gives the same start time to all runners
	 * @param - int - the channel calling start(); and Calendar - the current system time
	 * @return - String - the system message to print back
	 */
	@Override
	public String start(int channel, Calendar time){
		if(channel == 0 || channel == 1){
			if(isWaiting()){
				for(int i = 0; i < MAX_RUNNERS; ++i){
					if(getRunners()[i] != null) getRunners()[i].setStart(time);
				}
				Runner temp = new Runner(-1);
				temp.setStart(time);
				return "start - " + temp.getStart();
			}
			else return "error - run already started";
		}
		else return "error - invalid start channel";
	}
	
	/**
	 * RunPARGRP.cancel will cancel the current run, as long as it has started but no current runner has finished
	 * @return - String - the system message to print back
	 */
	@Override
	public String cancel(){
		boolean started = true;
		for(int i = 0; i < MAX_RUNNERS; ++i){
			if(getRunners()[i] != null && getRunners()[i].getStartTime() == null) started = false;
		}
		if(!started) return "error - run not started";
		else if(!getFinished().isEmpty()) return "error - cannot cancel run now";
		else{
			for(int i = 0; i < MAX_RUNNERS; ++i){
				if(getRunners()[i] != null) getRunners()[i].setStart(null);
			}
			return "cancelled run";
		}
	}
	
	/**
	 * RunPARGRP.finish validates the calling channel, and finishes the active runner for the corresponding lane (if applicable)
	 * @param - int - the calling channel number; Calendar - the current system time
	 * @return - String - the system message to print back
	 */
	@Override
	public String finish(int channel, Calendar time){
		//First, make a special case check - if the race hasn't started and the calling channel is channel 1,
		//call the start() method rather than running the regular finish() behavior; for any other calling channel,
		//if the race hasn't started, it should return with an error message
		boolean started = false;
		for(int i = 0; i < MAX_RUNNERS; ++i){
			if(getRunners()[i] != null && getRunners()[i].getStartTime() != null) started = true;
		}
		if(!started && channel == 1){
			String out = start(1, time);
			return out;
		}
		else if(!started) return "error - race not started";
		//If the race has started, then check to see that the calling channel/lane is valid, and the runner is active;
		//if so, apply the system time as their end time and add that runner to the finished runners list
		if(channel <= MAX_RUNNERS && channel >= 0){
			//For the generic "finish" button, finish the next available active runner (starting with lane 1)
			if(channel == 0){
				for(int i = 0; i < MAX_RUNNERS; ++i){
					if(getRunners()[i] != null && !getFinished().contains(getRunners()[i])){
						getRunners()[i].setEnd(time);
						getFinished().add(getRunners()[i]);
						return getRunners()[i].getNumber() + ": time - " + getRunners()[i].getTotalTime();
					}
				}
				return "error - all runners finished";
			}
			//Otherwise, verify that the runner in the calling channel's lane is active, and if so move them to finished
			if(getRunners()[channel-1] != null){
				if(getFinished().contains(getRunners()[channel-1])){
					return "error - runner already finished";
				}
				else{
					getRunners()[channel-1].setEnd(time);
					getFinished().add(getRunners()[channel-1]);
					return getRunners()[channel-1].getNumber() + ": time - " + getRunners()[channel-1].getTotalTime();
				}
			}
			else return "error - no runner in this lane";
		}
		else return "error - invalid channel number";
	}
	
	/**
	 * RunPARGRP.dnf kills all current runners (on an active run) by adding all remaining active runners to the finishedRunners list
	 * @return - String - the system message to print back
	 */
	@Override
	public String dnf(){
		boolean started = false;
		boolean finished = true;
		for(int i = 0; i < MAX_RUNNERS; ++i){
			if(getRunners()[i] != null && getRunners()[i].getStartTime() != null) started = true;
			if(!getFinished().contains(getRunners()[i])) finished = false;
		}
		if(!started) return "error - run not started";
		else if(finished) return "error - all runners have finished";
		else{
			for(int i = 0 ; i < MAX_RUNNERS; ++i){
				if(!getFinished().contains(getRunners()[i])) getFinished().add(getRunners()[i]);
			}
			return "all remaining runners dnf";
		}
	}
}
