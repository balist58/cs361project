package lab2;


import static org.junit.Assert.*;

import org.junit.Test;

public class ScoreSheetTest {
	ScoreSheet myScoreSheet1 = new ScoreSheet();
	
	/**test two throws and check scoring for the first frame and game*/
	@Test
	public void testTwoThrows(){
		myScoreSheet1.throwBall(4);
		myScoreSheet1.throwBall(5);	//End Frame 1
		
		assertEquals(9, myScoreSheet1.getFrameScore(1));
		assertEquals(9, myScoreSheet1.getGameScore());
	}
	
	/**
	 * Throw a spare in a frame and make sure its
	 * score is correct (counting the following frame, which should also be completed)
	 */
	@Test
	public void testSpareCountsNextFrameScore() {
		myScoreSheet1.throwBall(4);
		myScoreSheet1.throwBall(5);	//End Frame 1
		myScoreSheet1.throwBall(5);
		myScoreSheet1.throwBall(5); //End Frame 2
		myScoreSheet1.throwBall(3);
		myScoreSheet1.throwBall(3); //End Frame 3
		
		assertEquals(16, myScoreSheet1.getFrameScore(2));
	}
	
	/**
	 * Throw a strike in a frame and make sure its
	 * score is correct (counting the following frames, which should also be completed)
	 */
	@Test
	public void testStrikeCountsNextFrameScores() {
		myScoreSheet1.throwBall(4);
		myScoreSheet1.throwBall(5);	//End Frame 1
		myScoreSheet1.throwBall(5);
		myScoreSheet1.throwBall(5); //End Frame 2
		myScoreSheet1.throwBall(3);
		myScoreSheet1.throwBall(3); //End Frame 3
		myScoreSheet1.throwBall(10);//End Frame 4
		myScoreSheet1.throwBall(2);
		myScoreSheet1.throwBall(2); //End Frame 5
		myScoreSheet1.throwBall(2);
		myScoreSheet1.throwBall(2); //End Frame 6
		
		assertEquals(18, myScoreSheet1.getFrameScore(4));
	}
	
	/**
	 * Test throwing a strike on the 8th, 9th, and 10th frames
	 */
	@Test
	public void testStrikeOnLastFrames() {
		myScoreSheet1.throwBall(4);
		myScoreSheet1.throwBall(5);	//End Frame 1
		myScoreSheet1.throwBall(5);
		myScoreSheet1.throwBall(5); //End Frame 2
		myScoreSheet1.throwBall(3);
		myScoreSheet1.throwBall(3); //End Frame 3
		myScoreSheet1.throwBall(10);//End Frame 4
		myScoreSheet1.throwBall(2);
		myScoreSheet1.throwBall(2); //End Frame 5
		myScoreSheet1.throwBall(2);
		myScoreSheet1.throwBall(2); //End Frame 6
		myScoreSheet1.throwBall(2);
		myScoreSheet1.throwBall(2); //End Frame 7
		
		myScoreSheet1.throwBall(10);//End Frame 8
		myScoreSheet1.throwBall(10);//End Frame 9
		myScoreSheet1.throwBall(10);//End Frame 10
		
		//Eighth frame should be 30 because next two frames are 10 each
		assertEquals(30, myScoreSheet1.getFrameScore(8)); 
		
		//Ninth frame should be 20 because there is only 1 following frames to count scores in.
		assertEquals(20, myScoreSheet1.getFrameScore(9));
		
		//10th frame should be 10 because there are not 2 following frames to count scores in.
		assertEquals(10, myScoreSheet1.getFrameScore(10));
		
	}

}
