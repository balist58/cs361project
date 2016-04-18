/**
 * Nicholas Bialk @nbbialk
 * CS361 ChronoTimer Project
 * Run.java
 * 
 * The Run interface specifies the commands which must be included in each of the different
 * event types (IND, PARIND, GRP, and PARGRP) for the ChronoTimer to function properly
 */

package cs361Project;

import java.util.Calendar;

public interface Run{
	/**
	 * Method stubs, to be individually initialized for each sub-type of Run
	 */
	public String printRun(Calendar time);
	public String num(int number);
	public String clr(int number);
	public String swap();
	public String start(int channel, Calendar time);
	public String cancel();
	public String finish(int channel, Calendar time);
	public String dnf();
	public String exportRun(Calendar time);
	public boolean isActive();
	public boolean isWaiting();
	public int getRunNumber();
	public String printToDisplay(Calendar time);
}