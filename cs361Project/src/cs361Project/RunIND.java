/**
 * Nicholas Bialk @nbbialk
 * CS361 ChronoTimer Project
 * RunIND.java
 * 
 * The RunIND class controls the specifics implementation of methods in Run as pertains to the IND subtype
 */

package cs361Project;

import java.util.Calendar;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

public class RunIND implements Run{
	/**
	 * Fields for the Run class - a stack for runners with no start time; a queue that holds the
	 * runners that have a start time but no end time; and a queue to hold runners that have 
	 * both start and end times - all kept separate for the purposes of the event log
	 */
	private int runNumber;
	private Stack<Runner> waitingRunners;
	private Deque<Runner> activeRunners;
	private Deque<Runner> finishedRunners;
	
	/**
	 * The run constructor creates new empty instances of each of the run queues
	 * @param number - the runNumber for this run, set by its parent event during run instantiation
	 */
	public RunIND(int number){
		runNumber = number;
		waitingRunners = new Stack<Runner>();
		activeRunners = new LinkedList<Runner>();
		finishedRunners = new LinkedList<Runner>();
	}
	
	/**
	 * Getters for the RunIND fields
	 */
	public int getRunNumber(){return runNumber;}
	public Deque<Runner> getActive(){return activeRunners;}
	public Deque<Runner> getFinished(){return finishedRunners;}
	public Stack<Runner> getwaitingRunners(){return waitingRunners;}
    //isWaiting and isActive return whether their respective queues are *not* empty
	public boolean isWaiting(){return !this.getwaitingRunners().isEmpty();}
	public boolean isActive(){return !this.getActive().isEmpty();}
	
	/**
	 * Event.Run.print collects and returns a printable form of the current status of the run
	 * @return
	 */
	@Override
	public String printRun(Calendar time){
		String log = "Run " + runNumber + "\n";
		for(Runner r : waitingRunners)
			log += (r.getNumber() + "\n");
		for(Runner r : activeRunners){
			log += (r.getNumber() + " Start: " + r.getStart() + "\n");
			log += (r.getNumber() + " Elapsed Time: " + r.getElapsed(time) + "\n");
		}
		for(Runner r : finishedRunners){
			if(r.getEnd() == null)
				log += (r.getNumber() + " Start: " + r.getStart() + ", Finish: DNF\n");
			else
				log += (r.getNumber() + " Start: " + r.getStart() + ", Finish: " + r.getEnd() + "\n");
				log += (r.getNumber() + " Total Elapsed Time: " + r.getTotalTime() + "\n");
		}
		return log;
	}
	
	/**
	 * Event.Run.num adds a runner to the waitingRunners stack, but only if that runner number is
	 * not already present in any of the existing data structures and that number is between 0 and 99999
	 * @param number - the runner's number
	 */
	@Override
	public void num(int number){
		if((number >= 0) && (number <= 99999)){
			boolean notADuplicate = true;
			for(Runner r : waitingRunners){
				if(r.getNumber() == number) notADuplicate = false;
			}
			for(Runner r : activeRunners){
				if(r.getNumber() == number) notADuplicate = false;
			}
			for(Runner r : finishedRunners){
				if(r.getNumber() == number) notADuplicate = false;
			}
			if(notADuplicate) waitingRunners.push(new Runner(number));
		}
	}
	
	/**
	 * Event.Run.clr will remove the specified runner only from the top of the waitingRunners stack;
	 * if the specified runner is not the next to run, then the method does nothing
	 * @param number - the number of the runner being removed
	 */
	@Override
	public void clr(int number){
		if(waitingRunners.peek().getNumber() == number) waitingRunners.pop();
	}
	
	/**
	 * Event.Run.swap will switch the first and second runners on the activeRunners queue; if there
	 * are not currently at least two runners in the activeRunners queue, then the method does nothing
	 */
	@Override
	public void swap(){
		if(activeRunners.size() >= 2){
			Runner temp1 = activeRunners.poll();
			Runner temp2 = activeRunners.poll();
			activeRunners.addFirst(temp1);
			activeRunners.addFirst(temp2);
		}
	}
	
	/**
	 * Event.Run.start will move the next runner on the waiting stack and move it to the active queue;
	 * if there is no Runner in the waitingRunners stack, then the method does nothing
	 * @param time - the time stamp for when the run began
	 */
	@Override
	public void start(int channel, Calendar time){
		//given there is only one stream of runners, the channel argument is ignored in this run type
		if(!waitingRunners.isEmpty()){
			Runner temp = waitingRunners.pop();
			temp.setStart(time);
			activeRunners.addLast(temp);
		}
	}
	
	/**
	 * Event.Run.cancel will move the current runner at the end of the activeRunners queue, and return it
	 * to the top of the waitingRunners stack (it also nullifies the startTime); if there is no Runner in
	 * the activeRunners queue, then the method does nothing
	 */
	@Override
	public void cancel(){
		if(!activeRunners.isEmpty()){
			Runner temp = activeRunners.pollLast();
			temp.setStart(null);
			waitingRunners.push(temp);
		}
	}
	
	/**
	 * Event.Run.finish will move the current lead active runner from the active queue to the finished queue;
	 * if there is no Runner in the activeRunners queue, then the method does nothing
	 * @param time - the time stamp for when the run ended
	 */
	@Override
	public void finish(int channel, Calendar time){
		//given there is only one stream of runners, the channel argument is ignored in this run type
		if(!activeRunners.isEmpty()){
			Runner temp = activeRunners.poll();
			temp.setEnd(time);
			finishedRunners.addLast(temp);
		}
	}
	
	/**
	 * Event.Run.dnf will immediately move the current lead active runner to the finished queue, and set their
	 * endTime to null; if there is no Runner in the activeRunners queue, then the method does nothing
	 */
	@Override
	public void dnf(){
		if(!activeRunners.isEmpty()){
			Runner temp = activeRunners.poll();
			temp.setEnd(null);
			finishedRunners.addLast(temp);
		}
	}
	
	/**
	 * Event.Run.exportRun will convert the current status of the run, including the runNumber and the status of each
	 * of the runners from all 3 of the run queues, into a String that can be converted into JSON format
	 * @return - a String to be converted into JSON
	 */
	@Override
	public String exportRun(Calendar time){
		String ex = "{\n\"RunNumber\":";
		ex += runNumber;
		ex += ",\n\"WaitingRunners\":[";
		for(Runner r : waitingRunners){
			ex += "\n\"Number\": ";
			ex += r.getNumber();
			if(!(r == waitingRunners.lastElement())) ex += ",";
		}
		ex += "\n],\n\"ActiveRunners\": [\n{";
		for(Runner r : activeRunners){
			ex += "\n\"Number\": ";
			ex += r.getNumber();
			ex += ",\n\"ElapsedTime\": ";
			ex += r.getElapsed(time);
			if(!(r == activeRunners.peekLast())) ex += "\n},";
		}
		ex += "\n],\n\"FinishedRunners\": [\n{";
		for(Runner r : finishedRunners){
			ex += "\n\"Number\": ";
			ex += r.getNumber();
			ex += ",\n\"ElapsedTime\": ";
			if(r.getEndTime() == null) ex += "DNF";
			else ex += r.getTotalTime();
			if(!(r == finishedRunners.peekLast())) ex += "\n},";
		}
		ex += "\n]\n}";
		return ex;
	}
	
}
