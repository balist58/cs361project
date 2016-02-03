package lab2;

public class ScoreSheet {
	
	private Frame[] frames;
	private int frameNumber;
	
	private class Frame {
		int first;
		int second;
		boolean isFirstFrame = true;
		
		public void throwFrame(int x){
			if (isFirstFrame) {
				first = x;
				isFirstFrame = false;
			} else {
				second = x;
			}
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
		int result = 0;
		for(int i = 0; i < 10; i++) {
			result += this.getFrameScore(i);
		}
		return 0;
	}
	
	public int getFrameScore(int frame) {
		if(frames[frame].isStrike()) {
			return frames[frame].score() + frames[frame + 1].score() + frames[frame + 2].score();
		} else if (frames[frame].isSpare()) {
			return frames[frame].score() + frames[frame + 1].score();
		} else {
			return frames[frame].score();
		}
	}
	
}
