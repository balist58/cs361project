/**
 * Nicholas Bialk @nbbialk
 * CS361 ChronoTimer Project
 * Run.java
 * 
 * The Run class controls the specifics for an individual run or heat at an event, as well as controlling
 * the list of runners and their status for that run.  It takes care of resolving events in that run, and
 * relays information back to its controlling Event.
 */

package cs361Project;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Run{
	//IMPORTANT:  This is the run class as would be implemented in a "Run" superclass in later releases;
	//some of the methods' functionality is only applicable to the IND event, and will need to utilize an
	//inheritance relationship in later builds to reflect the different functionality for the other event types
	
	/**
	 * Each run contains a number of entries of the private Runner class, which holds the number for each
	 * runner in the Run, as well as their start and end times, and methods to get/set the data
	 */
	private class Runner{
		private int number;
		private Calendar startTime = null;
		private Calendar endTime = null;
		private DateFormat simple = new SimpleDateFormat("HH:mm:ss.SS");
		
		public Runner(int num){
			number = num;
		}
		
		public int getNumber(){return number;}
		public String getStart(){return simple.format(startTime.getTime());}
		public String getEnd(){return simple.format(endTime.getTime());}
		public void setStart(Calendar start){startTime = start;}
		public void setEnd(Calendar end){endTime = end;}
		
		/**
		 * Runner.getElapsed returns the total length of time elapsed by the current Runner (in seconds);
		 * it is only valid for a Runner with both a startTime and an EndTime
		 */
		public String getElapsed(){
			long elapsed = endTime.getTimeInMillis() - startTime.getTimeInMillis();
			double dblElapsed = ((double)elapsed)/1000;
			return Double.toString(dblElapsed);
		}
	}
	
	
	/**
	 * Fields for the Run class - a stack for runners with no start time; a queue that holds the
	 * runners that have a start time but no end time; and a queue to hold runners that have 
	 * both start and end times - all kept separate for the purposes of the event log
	 */
	private Stack<Runner> waitingRunners;
	private Deque<Runner> activeRunners;
	private Deque<Runner> finishedRunners;
	//additionally, for data storage purposes, we track the run number
	private int runNumber;
	
	/**
	 * The run constructor creates new empty instances of each of the run queues
	 * @param number - the runNumber for this run, set by its parent event during run instantiation
	 */
	public Run(int number){
		waitingRunners = new Stack<Runner>();
		activeRunners = new LinkedList<Runner>();
		finishedRunners = new LinkedList<Runner>();
		runNumber = number;
	}
	
	/**
	 * Event.Run.print collects and returns a printable form of the current status of the run
	 * @return
	 */
	public String printRun(){
		String log = "Run " + runNumber + "\n";
		for(Runner r : waitingRunners)
			log += (r.getNumber() + "\n");
		for(Runner r : activeRunners)
			log += (r.getNumber() + " Start: " + r.getStart() + "\n");
		for(Runner r : finishedRunners){
			if(r.getEnd() == null)
				log += (r.getNumber() + " Start: " + r.getStart() + ", Finish: DNF\n");
			else
				log += (r.getNumber() + " Start: " + r.getStart() + ", Finish: " + r.getEnd() + "\n");
				log += (r.getNumber() + " Total Elapsed Time: " + r.getElapsed() + " seconds\n");
		}
		return log;
	}
	
	/**
	 * Event.Run.num adds a runner to the waitingRunners stack, but only if that runner number is
	 * not already present in any of the existing data structures and that number is between 0 and 99999
	 * @param number - the runner's number
	 */
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
	public void clr(int number){
		if(waitingRunners.peek().getNumber() == number) waitingRunners.pop();
	}
	
	/**
	 * Event.Run.swap will switch the first and second runners on the activeRunners queue; if there
	 * are not currently at least two runners in the activeRunners queue, then the method does nothing
	 */
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
	public void start(Calendar time){
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
	public void finish(Calendar time){
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
	public void dnf(){
		if(!activeRunners.isEmpty()){
			Runner temp = activeRunners.poll();
			temp.setEnd(null);
			finishedRunners.addLast(temp);
		}
	}
}