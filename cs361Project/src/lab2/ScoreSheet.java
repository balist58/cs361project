package lab2;

public class ScoreSheet {
	
	private final int  MAX_FRAME_SCORE = 10;
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
				if(x == MAX_FRAME_SCORE) ++frameNumber;
			} else {
				second = x;
				++frameNumber; //Move on to the next frame
			}
		}
		
		public boolean isStrike() {
			return first == MAX_FRAME_SCORE;
		}
		
		public boolean isSpare() {
			return !isStrike() && (first + second) == MAX_FRAME_SCORE;
		}
		
		public int score() {
			return first + second;
		}
	}
	
	public ScoreSheet() {
		frames = new Frame[10];
		frameNumber = 0;
	}
	
	/**
	 * Throws the ball for the current frame
	 * @param x the score for that frame
	 * @return returns false if there are no more frames to throw, otherwise true
	 */
	public boolean throwBall(int x){
		if(frameNumber < 10) {
			if(frames[frameNumber] == null) frames[frameNumber] = new Frame();
			frames[frameNumber].throwFrame(x); //Handles advancing to the next frame
			return true;
		}
		return false;
	}
	
	/**
	 * Get the current score of the entire game
	 * @return the score of the current game
	 */
	public int getGameScore() {
		int result = 0;
		for(int i = 0; i < 10; i++) {
			result += this.getFrameScore(i); //This handles frames that aren't thrown yet
		}
		return result;
	}
	
	/**
	 * Get the current frame number
	 * @return the current frame number
	 */
	public int getFrame() {
		return frameNumber + 1; //The frames are stored as 0 based
	}
	
	/**
	 * Gets the score of the frame number passed in
	 * @param frame the frame number to get the score of (between 1-10)
	 * @return the score of the frame
	 */
	public int getFrameScore(int frame) {
		int result = 0;
		--frame; //the frame passed in is 1 based
		
		//Sanity check
		if(frame < frames.length && frame >= 0 && frames[frame] != null) {
			result = frames[frame].score();
			
			if(frames[frame].isStrike() && frame <= 8 && frames[frame + 1] != null) {
				result += frames[frame + 1].score();
				if(frame <= 7 && frames[frame + 2] != null) {
					result += frames[frame + 2].score();
				}
			} else if (frames[frame].isSpare() && frame <= 8 && frames[frame + 1] != null) {
				result += frames[frame + 1].score();
			}
		}
		
		return result;
	}
	
}
