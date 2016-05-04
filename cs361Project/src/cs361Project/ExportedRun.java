package cs361Project;

import java.util.ArrayList;

public class ExportedRun implements Comparable<Object> {
	public String raceType;
	public int raceNumber;
	public ArrayList<ExportedRunner> runners;
	
	public ExportedRun() {
		runners = new ArrayList<ExportedRunner>();
	}
	
	@Override
	public int compareTo(Object o) {
		if (o instanceof ExportedRun) {
			ExportedRun other = (ExportedRun) o;
			return this.raceNumber - other.raceNumber;
		}
		return 0;
	}
}
