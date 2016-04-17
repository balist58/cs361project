/**
 * Nicholas Bialk @nbbialk
 * CS361 ChronoTimer Project
 * RunGRP.java
 * 
 * The RunGRP class controls the specifics implementation of methods in Run as pertains to the GRP subtype
 */

package cs361Project;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.ArrayList;

public class RunGRP implements Run{
	/**
	 * Fields for the RunGRP class - the run number, passed along during creation; the list of runners for the event;
	 * and an int tracking the next index to which a manual runner number is to be assigned
	 */
	private int runNumber;
	private Calendar checkpoint;
	private LinkedList<Runner> runners;
	private ArrayList<Runner> finishedRunners;
	private int manIndex;
	private boolean dnf;
	
	/**
	 * The RunGRP constructor takes its run number as argument, and initializes a new list of runners and no checkpoint
	 * @param number - the run number for this run
	 */
	public RunGRP(int number){
		runNumber = number;
		checkpoint = null;
		runners = new LinkedList<Runner>();
		finishedRunners = new ArrayList<Runner>();
		manIndex = 0;
		dnf = false;
	}
	
	/**
	 * Getters for the RunGRP fields
	 */
	public int getRunNumber(){return runNumber;}
	public Calendar getCheckpoint(){return checkpoint;}
	public LinkedList<Runner> getRunners(){return runners;}
	public ArrayList<Runner> getFinished(){return finishedRunners;}
	public int getManIndex(){return manIndex;}
	public boolean isDNF(){return dnf;}
	
	/**
	 * RunGRP.isWaiting will return true anytime there are more manual runners queued up than completed runners
	 */
	public boolean isWaiting(){
		return this.getManIndex() > this.getFinished().size();
	}
	/**
	 * RunGRP.isActive will similarly return true when there are as many finished runners as there are total runners
	 */
	public boolean isActive(){
		return this.getRunners().size() > this.getFinished().size();
	}
	
	/**
	 * Setter for the Checkpoint field - used for keeping a snapshot of the last finish time
	 * @param Calendar newPt - the new value for the checkpoint field
	 */
	public void setCheckpoint(Calendar newPt){checkpoint = newPt;}
	/**
	 * Setter for the DNF field - used for keeping track of whether further FINISH events should be processed
	 */
	public void setDNF(boolean toSet){dnf = toSet;}
	
	/**
	 * RunGRP.printRun creates a String recording the start/end/elapsed times for all runners involved in the run,
	 * in the order that they were added to the list of runners for the run
	 * @return - String - a printed representation of the state of the current run
	 */
	public String printRun(Calendar currentTime){
		String log = "Run " + runNumber + "\n";
		for(Runner r : runners){
			if(r.getStartTime() == null) log += r.getNumber();
			else if(finishedRunners.contains(r) && r.getEndTime() == null){
				log += (r.getNumber() + " Start: " + r.getStart() + ", Finish: DNF\n");
			}
			else if(finishedRunners.contains(r) && r.getEndTime() != null){
				log += (r.getNumber() + " Start: " + r.getStart() + ", Finish: " + r.getEnd() + "\n");
				log += (r.getNumber() + " Total Elapsed Time: " + r.getTotalTime() + "\n");
			}
			else{
				log += (r.getNumber() + " Start: " + r.getStart() + "\n");
				log += (r.getNumber() + " Elapsed Time: " + r.getElapsed(currentTime) + "\n");
			}
		}
		return log;
	}
	
	/**
	 * RunGRP.num adds the selected runner number onto the runner list (if the number is in bounds, and
	 * has not already been used by a different *manually set* runner
	 * @param - runnerNumber - the int for the new Runner number, to be set at the current manual index
	 */
	public void num(int runnerNumber){
		if(runnerNumber < 0 || runnerNumber > 99999) System.out.println("Error: Cannot add runner number " + runnerNumber + ", number is out of bounds!");
		else{
			if(this.getManIndex() <= this.getRunners().size()){
				boolean match = false;
				for(int i = 0; i < this.getManIndex(); ++i){
					if(this.getRunners().get(i).getNumber() == runnerNumber) match = true;
				}
				if(!match){
					this.getRunners().get(this.getManIndex()).setNumber(runnerNumber);
					++manIndex;
				}
				else System.out.println("Error: Cannot add runner number " + runnerNumber + ", this number is already being used!");
			}
			else{
				this.getRunners().add(new Runner(runnerNumber));
				++manIndex;
			}
		}
	}
	
	/**
	 * RunGrp.clr clears out the selected runner number, if they were the last manually selected runner on the runners list;
	 * if replaces the manually assigned number with the auto-assigned number that racer originally had
	 * @param - runnerNumber - the int of the runner to be cleared
	 */
	public void clr(int runnerNumber){
		if(this.getRunners().get(this.getManIndex()-1).getNumber() == runnerNumber){
			this.getRunners().get(this.getManIndex()-1).setNumber(this.getManIndex()-1);
			--manIndex;
		}
		else System.out.println("Error: Cannot clear runner number " + runnerNumber + ", it is not the last manually added runner!");
	}
	
	/**
	 * RunGRP.swap is not a functional method; the SWAP command is only meant for use in the IND race type
	 */
	public void swap(){System.out.println("Error: The swap command is not enabled for group runs!");}
	
	/**
	 * RunGRP.start provides the start time for the first runner; it has no function for subsequent runners (who go off checkpoint time)
	 * @params - int chanNumber - the channel providing the Start signal; Calendar startTime - the time of the start signal
	 */
	public void start(int chanNumber, Calendar startTime){
		if(chanNumber == 0 || chanNumber == 1){  //Start only works when pressed manually or run off of Channel 1
			if(this.getRunners().isEmpty()){this.getRunners().add(new Runner(1));}
			if(this.getRunners().size() == 1 && this.getRunners().get(0).getStartTime() == null){
				this.getRunners().get(0).setStart(startTime);
				this.setCheckpoint(startTime);
			}
		}
	}
	
	/**
	 * RunGRP.cancel is similar to start; it only works if the first runner has a start time and no end time, and otherwise does nothing
	 */
	public void cancel(){
		if(this.getRunners().size() == 1 && this.getRunners().get(0).getStartTime() != null && this.getRunners().get(0).getEnd() == null){
			this.getRunners().get(0).setStart(null);
			this.setCheckpoint(null);
		}
		else if(this.getRunners().isEmpty() || this.getRunners().get(0).getStartTime() == null){
			System.out.println("Error: Cannot cancel; the run has not started!");
		}
		else System.out.println("Error: Cannot cancel; the first runner has already finished running!");
	}
	
	/**
	 * RunGRP.finish can be run anytime after the run is started; it will give the current runner a finish time, and allocate the same
	 * checkpoint time as the start time for the next runner (creating a new racer if necessary)
	 */
	public void finish(int chanNumber, Calendar finishTime){
		if(chanNumber == 0 || chanNumber == 2){ //Finish only works when pressed manually or run off of Channel 2
			if(this.getRunners().isEmpty() || this.getRunners().get(0).getStartTime() == null){
				System.out.println("Error: Cannot finish current runner; the run has not started!");
			}
			else if(!isDNF()){
				//if there was no manually added runner for the next place, make a new one with the default number
				if(this.getRunners().get(0).getEndTime() != null && this.getManIndex() <= this.getRunners().size()){
					this.getRunners().add(new Runner(this.getFinished().size()+1));
				}
				Runner toFinish = this.getRunners().get(this.getFinished().size());
				toFinish.setStart(checkpoint);              //set this runner's start time to the last checkpoint
				toFinish.setEnd(finishTime);                //and their finish time to the new finish time
				this.getFinished().add(toFinish);
				this.setCheckpoint(finishTime);             //change the checkpoint to the new latest finish time
			}
			//if the run is flagged *DNF* then further finish events do nothing
		}	
	}
	
	/**
	 * RunGRP.dnf is used for the sole purpose of killing runs, as it makes no logical sense to have continued finishes after
	 * one leg of the run gets a DNF.  The function is used to complete all remaining queued runners (by adding them to finished)
	 */
	public void dnf(){
		if(this.getRunners().size() >= 1 && this.getCheckpoint() != null){
			for(Runner r : this.getRunners()){if(!this.getFinished().contains(r)) this.getFinished().add(r);}
			this.setDNF(true);
		}	
	}
	
	
	//TODO:  turn this into a readable JSON format!
	public String exportRun(Calendar currentTime){
		return null;
	}
}
