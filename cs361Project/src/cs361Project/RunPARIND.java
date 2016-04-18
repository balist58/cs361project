/**
 * Nicholas Bialk @nbbialk
 * CS361 ChronoTimer Project
 * RunPARIND.java
 * 
 * The RunPARIND class controls the specifics implementation of methods in Run as pertains to the PARIND subtype
 */

package cs361Project;

import java.util.Calendar;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

public class RunPARIND implements Run{
	/**
	 * Fields for the Run class - a stack for runners with no start time; a queue that holds the
	 * runners that have a start time but no end time; and a queue to hold runners that have 
	 * both start and end times - all kept separate for the purposes of the event log.
	 * In addition, there is a field denoting the current active queue for the PARIND event type,
	 * as well as tools for setting up the channel sensors to match up to the two parallel run queues
	 */
	private int runNumber;
	private Stack<Runner> waitingRunners;
	private Deque<Runner>[] activeRunners;
	private Deque<Runner> finishedRunners;
	private int lastUsed;
	private int[] channelMap;
	private final int NUMCHANNELS = 12;
	private int startCounter;
	private int finishCounter;
	
	/**
	 * The run constructor creates new empty instances of each of the run queues; for the PARIND
	 * event type, there are two separate instances of the activeRunners queue; it also sets up the
	 * channel map, which we initialize to -1 for all indices for later registration purposes, as well
	 * as the lastUsed, start and finish counters, which are all initialized to queue 0.
	 * @param number - the runNumber for this run, set by its parent event during run instantiation
	 */
	@SuppressWarnings("unchecked")
	public RunPARIND(int number){
		runNumber = number;
		lastUsed = 0;
		waitingRunners = new Stack<Runner>();
		activeRunners = new Deque[2];
		activeRunners[0] = new LinkedList<Runner>();
		activeRunners[1] = new LinkedList<Runner>();
		finishedRunners = new LinkedList<Runner>();
		channelMap = new int[NUMCHANNELS];
		for(int i = 0; i < NUMCHANNELS; ++i){
			channelMap[i] = -1;
		}
		startCounter = 0;
		finishCounter = 0;
	}
	
	/**
	 * Getters for the RunPARIND fields
	 */
	public int getRunNumber(){return runNumber;}
	public Deque<Runner> getActive(){
		Deque<Runner> res = new LinkedList<Runner>();
		res.addAll(activeRunners[0]);
		res.addAll(activeRunners[1]);
		return res;
	}
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
		for(Runner r : activeRunners[0]){
			log += (r.getNumber() + " Elapsed: " + r.getElapsed(time) + "\n");
		}
		for(Runner r : activeRunners[1]){
			log += (r.getNumber() + " Elapsed: " + r.getElapsed(time) + "\n");
		}
		for(Runner r : finishedRunners){
			if(r.getStart() == null)
				log += (r.getNumber() + " Did Not Run\n");
			if(r.getEnd() == null)
				log += (r.getNumber() + " Did Not Finish\n");
			else
				log += (r.getNumber() + " Time: " + r.getTotalTime() + "\n");
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
			for(Runner r : activeRunners[0]){
				if(r.getNumber() == number) notADuplicate = false;
			}
			for(Runner r: activeRunners[1]){
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
	 * Event.Run.swap is not enabled for the PARIND event type; it prints back an error and does nothing
	 */
	@Override
	public void swap(){
		System.out.println("Error: The swap command is not supported in the PARIND event!");
	}
	
	/**
	 * Event.Run.start will either take a waiting runner and add it to the corresponding queue for the calling channel,
	 * or if done manually, will switch to the next channel over from the last channel used, and add the runner to that queue
	 * @param time - the time stamp for when the run began
	 */
	@Override
	public void start(int channel, Calendar time){
		if(waitingRunners.isEmpty()) System.out.println("Error: Cannot start; there are no runners awaiting start!");
		else{
			if(channel == 0){
				lastUsed = (lastUsed++)%2;
				Runner temp = waitingRunners.pop();
				temp.setStart(time);
				activeRunners[lastUsed].addLast(temp);
			}
			else{
				startRegister(channel);
				if(channelMap[channel-1] == -1) System.out.println("ERROR: THE CHANNEL IS NOT REGISTERED!");
				else{
					Runner temp = waitingRunners.pop();
					temp.setStart(time);
					activeRunners[channelMap[channel-1]].addLast(temp);
				}
			}
		}
	}
	
	/**
	 * Event.Run.cancel will cancel the active status of the la
	 */
	@Override
	public void cancel(){
		if(activeRunners[0].isEmpty() && activeRunners[1].isEmpty())System.out.println("Error:  Cannot cancel; no active runners!");
		else if(activeRunners[0].isEmpty()){
			Runner temp = activeRunners[1].pollLast();
			temp.setStart(null);
			waitingRunners.push(temp);
		}
		else if(activeRunners[1].isEmpty()){
			Runner temp = activeRunners[0].pollLast();
			temp.setStart(null);
			waitingRunners.push(temp);
		}
		else{
			int toCancel =((compareTimes(activeRunners[0].peekLast(), activeRunners[1].peekLast()))+1)%2;
			Runner temp = activeRunners[toCancel].pollLast();
			temp.setStart(null);
			waitingRunners.push(temp);
		}
	}
	
	/**
	 * Event.Run.finish checks 
	 * @param time - the time stamp for when the run ended
	 */
	@Override
	public void finish(int channel, Calendar time){
		//If this was a manual finish, find the racer with the longest current run time and finish them
		if(channel == 0){
			if(activeRunners[0].isEmpty() && activeRunners[1].isEmpty()) System.out.println("Error:  Cannot finish; no active runners!");
			else if(activeRunners[0].isEmpty()){
				Runner temp = activeRunners[1].poll();
				temp.setEnd(time);
				finishedRunners.addLast(temp);
				lastUsed = 1;
			}
			else if(activeRunners[1].isEmpty()){
				Runner temp = activeRunners[0].poll();
				temp.setEnd(time);
				finishedRunners.addLast(temp);
				lastUsed = 0;
			}
			else{
				lastUsed = compareTimes(activeRunners[0].peek(), activeRunners[1].peek());
				Runner temp = activeRunners[lastUsed].poll();
				temp.setEnd(time);
				finishedRunners.addLast(temp);
			}
		}
		//Otherwise, register the calling channel (if needed) and finish the racer in the matching queue (if there is one)
		else{
			finishRegister(channel);
			if(channelMap[channel-1] == -1) System.out.println("Error: THE CHANNEL IS NOT REGISTERED");
			else if(activeRunners[channelMap[channel-1]].isEmpty()) System.out.println("Error: Cannot finish; no active runner on this queue");
			else{
				Runner temp = activeRunners[channelMap[channel-1]].poll();
				temp.setEnd(time);
				finishedRunners.addLast(temp);
				lastUsed = channelMap[channel-1];
			}
		}
	}
	
	/**
	 * Event.Run.dnf will immediately move the current lead active runner from the currently active queue into
	 * the finished runners queue; if that active queue is empty, it will re-attempt this process on the other queue.
	 * The runner that is moved is assigned no end time.  If there are no active runners, DNF does nothing.
	 */
	@Override
	public void dnf(){
		if(activeRunners[0].isEmpty() && activeRunners[1].isEmpty()) System.out.println("Error:  Cannot finish; no active runners!");
		else if(activeRunners[0].isEmpty()){
			Runner temp = activeRunners[1].poll();
			temp.setEnd(null);
			finishedRunners.addLast(temp);
			lastUsed = 1;
		}
		else if(activeRunners[1].isEmpty()){
			Runner temp = activeRunners[0].poll();
			temp.setEnd(null);
			finishedRunners.addLast(temp);
			lastUsed = 0;
		}
		else{
			lastUsed = compareTimes(activeRunners[0].peek(), activeRunners[1].peek());
			Runner temp = activeRunners[lastUsed].poll();
			temp.setEnd(null);
			finishedRunners.addLast(temp);
		}
	}
	
	/**
	 * startRegister and finishRegister are helper methods that are used by the start and finish methods; they
	 * take the calling channel and assign it to one or the other active queue (as long as they have not already
	 * been registered this way); these queues are kept separate from each other, as well as from the activeQueue
	 * which is used in the same way for manual start and finishes.
	 * @param channel - the calling channel/sensor number
	 */
	private void startRegister(int channel){
		if(channelMap[channel-1] == -1){
			channelMap[channel-1] = startCounter;
			startCounter = (++startCounter)%2;
		}
	}
	private void finishRegister(int channel){
		if(channelMap[channel-1] == -1){
			channelMap[channel-1] = finishCounter;
			finishCounter = (++finishCounter)%2;
		}
	}
	
	/**
	 * compareTimes is a simple comparator used in the manual finish operations, which takes two runners as
	 * arguments, and returns whichever has the lower start time (that is, has been running for longer)
	 * @param run1 - the next runner set to finish from queue 0
	 * @param run2 - the next runner set to finish from queue 1
	 * @return - whichever of the two has been running for longer
	 */
	private int compareTimes(Runner run1, Runner run2){
		long run1time = run1.getStartTime().getTimeInMillis();
		long run2time = run2.getStartTime().getTimeInMillis();
		if(run1time <= run2time) return 0;
		else return 1;
	}
	
	/**
	 * Event.Run.exportRun will convert the current status of the run, including the runNumber and the status of each
	 * of the runners from all 4 of the run queues, into a String that can be converted into JSON format
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
		ex += "\n],\n\"ActiveRunners0\": [\n{";
		for(Runner r : activeRunners[0]){
			ex += "\n\"Number\": ";
			ex += r.getNumber();
			ex += ",\n\"ElapsedTime\": ";
			ex += r.getElapsed(time);
			if(!(r == activeRunners[0].peekLast())) ex+= "\n},";
			else ex+= "\n}";
		}
		ex += "\n],\n\"ActiveRunners1\": [\n{";
		for(Runner r : activeRunners[1]){
			ex += "\n\"Number\": ";
			ex += r.getNumber();
			ex += ",\n\"ElapsedTime\": ";
			ex += r.getElapsed(time);
			if(!(r == activeRunners[0].peekLast())) ex+= "\n},";
			else ex+= "\n}";
		}
		ex += "\n],\n\"FinishedRunners\": [\n{";
		for(Runner r : finishedRunners){
			ex += "\n\"Number\": ";
			ex += r.getNumber();
			ex += ",\n\"ElapsedTime\": ";
			if(r.getEndTime() == null) ex += "DNF";
			else ex += r.getTotalTime();
			if(!(r == finishedRunners.peekLast())) ex += "\n},";
			else ex+= "\n}";
		}
		ex += "\n]\n}";
		return ex;
	}
	
	/**
	 * RunPARIND.printToDisplay returns a String representing the current run state in the correct format for the ChronoTimerGUI run display
	 * @return String - display output for the current run
	 */
	@Override
	public String printToDisplay(Calendar time){
		String out = "";
		
		if(this.getwaitingRunners().size() >= 2) out += (this.getwaitingRunners().get(1).getNumber() + " >\n");
		else out += "\n";
		if(this.getwaitingRunners().size() >= 1) out += (this.getwaitingRunners().get(0).getNumber() + " >\n");
		else out += "\n";
		
		out += "\n\n";
		if(!activeRunners[0].isEmpty()) out += (activeRunners[0].peekFirst().getNumber() + " " + activeRunners[0].peekFirst().getElapsed(time) + " R\n");
		else out += "\n";
		if(!activeRunners[1].isEmpty()) out += (activeRunners[1].peekFirst().getNumber() + " " + activeRunners[1].peekFirst().getElapsed(time) + " R\n");
		else out += "\n";
		
		if(this.getFinished().size() >= 2){
			Runner last = this.getFinished().pollLast();
			Runner second = this.getFinished().pollLast();
			out += (second.getNumber() + " " + second.getTotalTime() + " F\n");
			out += (last.getNumber() + " " + last.getTotalTime() + " F\n");
			this.getFinished().add(second);
			this.getFinished().add(last);
		}
		else if(this.getFinished().size() == 1) out += (this.getFinished().peekLast().getNumber() + " " + this.getFinished().peekLast().getTotalTime() + " F\n\n");
		else out += "\n\n";
		
		return out;
	}
}
