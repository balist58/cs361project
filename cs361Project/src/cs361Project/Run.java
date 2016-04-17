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
	public void num(int number);
	public void clr(int number);
	public void swap();
	public void start(int channel, Calendar time);
	public void cancel();
	public void finish(int channel, Calendar time);
	public void dnf();
	public String exportRun(Calendar time);
	public boolean isActive();
	public boolean isWaiting();
	public int getRunNumber();
}