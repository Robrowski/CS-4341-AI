/**
 * Matt Costi - mscosti  
 * Rob Dabrowski - rpdabrowski
 * 
 * CS 4341 C 2015
 * Prof. Niel Heffernan
 * WPI 
 */
package evaluators.features;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import common.board.ScoreBoard;

import evaluators.Evaluator;
import evaluators.EvaluatorFactory;

public class ScoreBoardCountFeatureTest {
	
	Evaluator scoreBoardCountEval;

	@Before
	public void setUp() throws Exception {
		EvaluatorFactory factory = new EvaluatorFactory();
		String[] scoreBoardCount = { "--score-board-feature" };
		scoreBoardCountEval = factory.makeEvaluator(scoreBoardCount, 1);
	}

	@Test
	public void test_3x3_connect2_featureScore() {
		ScoreBoard board = new ScoreBoard(3, 3, 2);

		int[][] moves = {
				{ 9, 9, 9 },
				{ 9, 9, 9 },
				{ 1, 2, 9 } };

		board.setBoard(moves);

		/**
		current player score 
				{ 3, 5, 3 }
				{ 4, 7, 4 } 
				{ 2, 0, 2 }
		
		current opponent score
				{3, 5, 3}
				{ 4, 7, 5 } 
				{ 0, 4, 3 }
		**/
		
		

		int estimate = scoreBoardCountEval.estimateGameState(board);
		int expected = 2 - 4;
		
		assertEquals(expected, estimate);

	}

}
