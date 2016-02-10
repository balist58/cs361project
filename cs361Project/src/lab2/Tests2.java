package lab2;

import static org.junit.Assert.*;

import org.junit.Test;

public class Tests2 {
	ScoreSheet testScoreSheet = new ScoreSheet();
	
	/**Test a single throw, make sure that it matches the frame score and game score*/
	@Test
	public void testOneThrow(){
		testScoreSheet = new ScoreSheet();
		testScoreSheet.throwBall(7); //throw goes in frame 1
		
		assertEquals(7, testScoreSheet.getFrameScore(1));
		assertEquals(7, testScoreSheet.getGameScore());
	}
	
	/**Test three throws in a row (with no spares/strikes); check to make sure that
	 * the scores for both frames 1 and 2 are correct, and that the game score is correct
	 */
	@Test
	public void testThreeThrows(){
		testScoreSheet = new ScoreSheet();
		testScoreSheet.throwBall(7); //throw goes in frame 1
		testScoreSheet.throwBall(2); //throw goes in frame 1
		testScoreSheet.throwBall(8); //throw goes in frame 2
		
		assertEquals(9, testScoreSheet.getFrameScore(1)); //verify frame 1's score is the sum of throws 1 and 2
		assertEquals(8, testScoreSheet.getFrameScore(2)); //verify frame 2's score is the same as throw 3
		assertEquals(17, testScoreSheet.getGameScore()); //verify game score is the sum of all 3 throws
		assertEquals(2, testScoreSheet.getFrame()); //verify that the game is still on frame 2
	}
	
	/**Test that the frame increments by one when a strike is thrown*/
	@Test
	public void testStrikeMovesToNextFrame(){
		testScoreSheet = new ScoreSheet();
		
		assertEquals(1, testScoreSheet.getFrame());
		
		testScoreSheet.throwBall(10); //throw a strike in frame 1
		
		assertEquals(10, testScoreSheet.getFrameScore(1)); //verify the frame is set to the max score (for now)
		assertEquals(10, testScoreSheet.getGameScore()); //verify that the game score matches
		assertEquals(2, testScoreSheet.getFrame()); //check that the Score Sheet is now on frame 2
	}
	
	/**Test the functionality of throwing a spare on the final frame; verify both the score
	 * in the 10th frame, and the total game score, and that the frame increments to the game's end state*/
	public void testSpareOnLastFrame(){
		testScoreSheet = new ScoreSheet();
		for(int i = 0; i < 18; ++i){
			testScoreSheet.throwBall(2); //throw for 2 pins, 18 times in a row
		}
		testScoreSheet.throwBall(4); //throw goes in frame 10
		testScoreSheet.throwBall(6); //throw goes in frame 10; achieves a spare
		
		assertEquals(10, testScoreSheet.getFrameScore(10)); //verify that frame 10 has a score of 10 (all pins down)
		assertEquals(46, testScoreSheet.getGameScore()); //verify the game score is correct
		assertEquals(11, testScoreSheet.getFrame()); //check that the Score Sheet is now on "frame 11"
	}
	
	/**Attempt to throw the ball on the "11th frame"; with the way the Score Sheet is designed,
	 * this will take the form of the throwBall() method returning false*/
	public void testThrowOn11thFrame(){
		testScoreSheet = new ScoreSheet();
		for(int i = 0; i < 20; ++i){
			testScoreSheet.throwBall(4); //throw for 4 pins, 20 throws in a row
		}
		
		assertEquals(80, testScoreSheet.getGameScore()); //verify that the game score is correct
		assertEquals(11, testScoreSheet.getFrame()); //verify that the frame has incremented to 11
		assertFalse(testScoreSheet.throwBall(1)); //assert that attempting another throw will return false
	}
}
