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

import com.google.gson.Gson;

import java.util.ArrayList;

public class RunGRP implements Run{
	/**
	 * Fields for the RunGRP class - the run number, passed along during creation; the list of runners for the event;
	 * and an int tracking the next index to which a manual runner number is to be assigned
	 */
	private int runNumber;
	private Calendar startPoint;
	private Calendar lastFinish;
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
		startPoint = null;
		lastFinish = null;
		runners = new LinkedList<Runner>();
		finishedRunners = new ArrayList<Runner>();
		manIndex = 0;
		dnf = false;
	}
	
	/**
	 * Getters for the RunGRP fields
	 */
	public int getRunNumber(){return runNumber;}
	public Calendar getStart(){
		if(startPoint == null) return null;
		else return (Calendar)startPoint.clone();}
	public Calendar getFinish(){
		if(lastFinish == null) return null;
		else return (Calendar)lastFinish.clone();
	}
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
	 * RunGRP.isActive will return true when there are more total runners than completed runners
	 */
	public boolean isActive(){
		return this.getRunners().size() > this.getFinished().size();
	}
	
	/**
	 * Setter for the startPoint field - sets the universal "start time" for each competitor
	 * @param Calendar newPt - the new value for the startPoint field
	 */
	public void setStart(Calendar newPt){
		Calendar newStart;
		if(newPt == null) newStart = null;
		else newStart = (Calendar)newPt.clone();
		startPoint = newStart;
	}
	/**
	 * Similarly, setFinish is the setter for the lastFinish field - tracks the finish time of the last runner to finish
	 * @param Calendar newPt - the new value for the lastFinish field
	 */
	public void setFinish(Calendar newPt){
		Calendar newFinish;
		if(newPt == null) newFinish = null;
		else newFinish = (Calendar)newPt.clone();
		lastFinish = newFinish;
	}
	
	/**
	 * Setter for the DNF field - used for keeping track of whether further FINISH events should be processed
	 */
	public void setDNF(boolean toSet){dnf = toSet;}
	
	/**
	 * RunGRP.printRun creates a String recording the start/end/elapsed times for all runners involved in the run,
	 * in the order that they were added to the list of runners for the run
	 * @return - String - a printed representation of the state of the current run
	 */
	@Override
	public String printRun(Calendar currentTime){
		String log = "Run " + runNumber + "\n";
		for(Runner r : runners){
			if(!finishedRunners.contains(r) && r.getStartTime() == null) log += (r.getNumber() + "\n");
			else if(finishedRunners.contains(r) && r.getStartTime() == null) log += (r.getNumber() + " Did Not Run\n");
			else if(finishedRunners.contains(r) && r.getEndTime() == null){
				log += (r.getNumber() + " Did Not Finish\n");
			}
			else if(finishedRunners.contains(r) && r.getEndTime() != null){
				log += (r.getNumber() + " Time: " + r.getTotalTime() + "\n");
			}
			else{
				log += (r.getNumber() + " Elapsed: " + r.getElapsed(currentTime) + "\n");
			}
		}
		return log;
	}
	
	/**
	 * RunGRP.num adds the selected runner number onto the runner list (if the number is in bounds, and
	 * has not already been used by a different *manually set* runner
	 * @param - runnerNumber - the int for the new Runner number, to be set at the current manual index
	 */
	@Override
	public String num(int runnerNumber){
		if(runnerNumber < 0 || runnerNumber > 99999){
			System.out.println("Error: Cannot add runner number " + runnerNumber + ", number is out of bounds!");
			return "error - invalid number";
		}
		else{
			if(this.getManIndex() < this.getRunners().size()){
				boolean match = false;
				for(int i = 0; i <= this.getManIndex(); ++i){
					if(this.getRunners().get(i).getNumber() == runnerNumber) match = true;
				}
				if(!match){
					this.getRunners().get(this.getManIndex()).setNumber(runnerNumber);
					++manIndex;
					return "added runner " + runnerNumber;
				}
				else{
					System.out.println("Error: Cannot add runner number " + runnerNumber + ", this number is already being used!");
					return "error - duplicate number";
				}
			}
			else{
				boolean match = false;
				for(Runner r : this.getRunners()){
					if(r.getNumber() == runnerNumber) match = true;
				}
				if(!match){
					runners.add(new Runner(runnerNumber));
					++manIndex;
					return "added runner " + runnerNumber;
				}
				else{
					System.out.println("Error: Cannot add runner number " + runnerNumber + ", this number is already being used!");
					return "error - duplicate number";
				}
			}
		}
	}
	
	/**
	 * RunGrp.clr clears out the selected runner number, if they were the last manually selected runner on the runners list;
	 * if replaces the manually assigned number with the auto-assigned number that racer originally had
	 * @param - runnerNumber - the int of the runner to be cleared
	 */
	@Override
	public String clr(int runnerNumber){
		if(this.getRunners().get(this.getManIndex()-1).getNumber() == runnerNumber){
			this.getRunners().get(this.getManIndex()-1).setNumber(this.getManIndex()-1);
			--manIndex;
			return "cleared runner " + runnerNumber;
		}
		else{
			System.out.println("Error: Cannot clear runner number " + runnerNumber + ", it is not the last manually added runner!");
			return "error - " + runnerNumber + " not last runner";
		}
	}
	
	/**
	 * RunGRP.swap is not a functional method; the SWAP command is only meant for use in the IND race type
	 */
	@Override
	public String swap(){
		System.out.println("Error: The swap command is not enabled for group runs!");
		return "error - swap not available in GRP";
	}
	
	/**
	 * RunGRP.start provides the start time and adds the first active runner (if necessary)
	 * @params - int chanNumber - the channel providing the Start signal; Calendar startTime - the time of the start signal
	 */
	@Override
	public String start(int chanNumber, Calendar startTime){
		if(chanNumber == 0 || chanNumber == 1){  //Start only works when pressed manually or run off of Channel 1
			if(this.getRunners().isEmpty()){runners.add(new Runner(1));}
			if(this.getRunners().getFirst().getStartTime() == null){
				this.getRunners().getFirst().setStart(startTime);
				this.setStart(startTime);
				return this.getRunners().getFirst().getNumber() + ": start - " + this.getRunners().getFirst().getStart();
			}
			else return "error - run already started";
		}
		else return "error - invalid start channel";
	}
	
	/**
	 * RunGRP.cancel will cancel the run only when it has started, but no competitor has yet finished
	 */
	@Override
	public String cancel(){
		if(this.getRunners().size() == 1 && this.getRunners().getFirst().getStartTime() != null && this.getRunners().getFirst().getEndTime() == null){
			this.getRunners().getFirst().setStart(null);
			this.setStart(null);
			return "cancelled run";
		}
		else if(this.getRunners().isEmpty() || this.getRunners().getFirst().getStartTime() == null){
			System.out.println("Error: Cannot cancel; the run has not started!");
			return "error - run not started";
		}
		else{
			System.out.println("Error: Cannot cancel; the first runner has already finished running!");
			return "error - can no longer cancel run";
		}
	}
	
	/**
	 * RunGRP.finish can be run anytime after the run is started; it will set the current system time as the finish time for the next
	 * "active runner" (manually set), or create a new placeholder runner and set the current system time to that runner's finish time
	 * @param - int - the calling channel; Calendar - the time to set as the runner's end time
	 */
	@Override
	public String finish(int chanNumber, Calendar finishTime){
		if(chanNumber == 0 || chanNumber == 2){ //Finish only works when pressed manually or run off of Channel 2
			if(this.getRunners().isEmpty() || this.getRunners().get(0).getStartTime() == null){
				System.out.println("Error: Cannot finish current runner; the run has not started!");
				return "error - run not started";
			}
			else if(!isDNF()){
				//if there was no manually added runner for the next place, make a new one with the default number
				if(this.getRunners().get(0).getEndTime() != null && this.getManIndex() <= this.getRunners().size()){
					runners.add(new Runner(this.getFinished().size()+1));
				}
				Calendar sp = (Calendar)startPoint.clone();
				this.getRunners().get(this.getFinished().size()).setStart(sp);
				this.getRunners().get(this.getFinished().size()).setEnd(finishTime);
				this.getFinished().add(this.getRunners().get(this.getFinished().size()));
				this.setFinish(finishTime);  //set the "last finish" field to match the time of this finish event
				return "Place " + this.getFinished().size() + ": time - " + this.getFinished().get(this.getFinished().size() -1).getTotalTime();
			}
			else return "error - run is over";
			//if the run is flagged *DNF* then further finish events do nothing
		}
		else return "error - invalid finish channel";
	}
	
	/**
	 * RunGRP.dnf is used for the sole purpose of killing runs, as it makes no logical sense to have runners continue finishing
	 * after one of them (in order) failed to finish the race; this sets the flag to prevent further finish events
	 */
	@Override
	public String dnf(){
		if(this.getRunners().size() >= 1 && this.getStart() != null){
			for(Runner r : this.getRunners()){if(!this.getFinished().contains(r)) this.getFinished().add(r);}
			this.setDNF(true);
			return "all remaining runners DNF";
		}
		else return "error - run has not started";
	}
	
	/**
	 * RunGRP.exportRun will convert the current status of the run, including the runNumber and the status of each
	 * of the runners in the runners list (according to whether they are listed, active, or finished), into JSON format for export
	 * @return - a String to be converted into JSON
	 */
	@Override
	public String exportRun(Calendar currentTime){
		ExportedRun e =  new ExportedRun();
		e.raceNumber = runNumber;
		e.raceType = "GRP";
		
		for(Runner r : runners) {
			if(r.getStart() != null) {
				e.runners.add(new ExportedRunner(r.getNumber(), r.getTotalTime()));				
			}
		}

		Gson g = new Gson();
		return g.toJson(e);
		
		/*String ex = "{\n\"RunNumber\":";
		ex += runNumber;
		ex += ",\n\"Runners\":[";
		for(Runner r : runners){
			if(!finishedRunners.contains(r) && r.getStart() == null){
				ex += "\n\"Number\": ";
				ex += r.getNumber();
				if(r != runners.peekLast()) ex += ",";
			}
			else if(!finishedRunners.contains(r) && r.getStart() != null){
				ex += "{\n\"Number\": ";
				ex += r.getNumber();
				ex += ",\n\"ElapsedTime\": ";
				ex += r.getElapsed(currentTime);
				if(r != runners.peekLast()) ex += "\n},";
				else ex += "\n}";
			}
			else{
				ex += "{\n\"Number\": ";
				ex += r.getNumber();
				ex += ",\n\"ElapsedTime\": ";
				if(r.getEndTime() == null) ex += "DNF";
				else ex += r.getTotalTime();
				if(r != runners.peekLast()) ex += "\n},";
				else ex += "\n}";
			}
		}
		ex += "\n]\n}";
		return ex;*/
	}
	
	/**
	 * RunGRP.printToDisplay returns a String representing the current run state in the correct format for the ChronoTimerGUI run display
	 * @return String - display output for the current run
	 */
	@Override
	public String printToDisplay(Calendar time){
		String out = "";
		
		if(!this.getRunners().isEmpty()){
			Runner elapsed = new Runner(-1);
			elapsed.setStart(this.getStart());
			out += ("Elapsed: " + elapsed.getElapsed(time) + "\n");
		}
		else out += ("Run not started\n");
		
		out += "\n";
		if(!this.getRunners().isEmpty() && this.getRunners().getFirst().getEnd() != null){
			Runner check = new Runner(-1);
			check.setStart(this.getStart());
			check.setEnd(this.getFinish());
			//out += ("Last finish: " + check.getElapsed(this.getStart()) + "\n");
			out += ("Last finish: " + check.getEnd() + "\n");
		}
		else out += ("Last finish: n/a");
		
		return out;
	}
}
