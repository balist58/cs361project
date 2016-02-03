package lab2;

public class ScoreSheet {
	
	private Frame[] frames;
	private int frameNumber;
	
	private class Frame {
		int first;
		int second;
		
		public void setFirst(int x){
			
		}
		
		public boolean isStrike() {
			return first == 10;
		}
		
		public boolean isSpare() {
			return !isStrike() && (first + second) == 10;
		}
		
		public int score() {
			return first + second;
		}
	}
	
	public ScoreSheet(){
		frames = new Frame[10];
		frameNumber=0;
	}
	
	public boolean throwBall(int x){
		
		
		return true;
	}
	
	public int getGameScore() {
		return 0;
	}
	
	public int getFrameScore(int frame) {
		return 0;
	}
	
}
