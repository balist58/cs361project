package cs361Project;

public class ExportedRunner implements Comparable<Object> {
	public int number;
	public String elapsedTime;
	public ExportedRunner(int n, String e) { 
		number = n;
		elapsedTime = e;
	}
	
	@Override
	public int compareTo(Object o) {
		if (o instanceof ExportedRunner) {
			ExportedRunner other = (ExportedRunner) o;
			if(this.elapsedTime.compareTo("DNF") == 0 && other.elapsedTime.compareTo("DNF") == 0) {
				return this.number - other.number;
			} else if(this.elapsedTime.compareTo("DNF") == 0) {
				return 1;
			} else if (other.elapsedTime.compareTo("DNF") == 0) {
				return -1;
			}
			
			Double thisTime = Double.parseDouble(this.elapsedTime.split(" ")[0]);
			Double otherTime = Double.parseDouble(other.elapsedTime.split(" ")[0]);
			
			return thisTime.compareTo(otherTime);
		}
		return 0;
	}
}

