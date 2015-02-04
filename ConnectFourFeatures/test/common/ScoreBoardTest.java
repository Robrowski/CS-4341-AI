/**
 * Matt Costi - mscosti  
 * Rob Dabrowski - rpdabrowski
 * 
 * CS 4341 C 2015
 * Prof. Niel Heffernan
 * WPI 
 */
package common;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import common.board.ScoreBoard;

public class ScoreBoardTest {
	final int P1 = 1;
	final int P2 = 2;

	@Test
	public void test_initScore_2x2_connect1_fail() {
		ScoreBoard board = new ScoreBoard(2, 2, 1);

		// Sanity check that Arrays.deepEquals is working correctly
		int[][] expectedScore = { { 1, 1 }, { 0, 1 } };

		assertFalse(boardEquals(expectedScore, board.getPlayerScoreBoard(P1)));
	}

	@Test
	public void test_initScore_2x2_connect1() {
		ScoreBoard board = new ScoreBoard(2, 2, 1);

		int[][] expectedScore = { { 1, 1 }, { 1, 1 } };

		assertTrue(boardEquals(expectedScore, board.getPlayerScoreBoard(P1)));
	}

	@Test
	public void test_initScore_3x3_connect2() {
		ScoreBoard board = new ScoreBoard(3, 3, 2);

		int[][] expectedScore = { { 3, 5, 3 }, { 5, 8, 5 }, { 3, 5, 3 } };

		assertTrue(boardEquals(expectedScore, board.getPlayerScoreBoard(P1)));
	}

	@Test
	public void test_initScore_4x4_connect3() {
		ScoreBoard board = new ScoreBoard(4, 4, 3);

		int[][] expectedScore = { { 3, 4, 4, 3 }, { 4, 7, 7, 4 },
				{ 4, 7, 7, 4 }, { 3, 4, 4, 3 } };

		assertTrue(boardEquals(expectedScore, board.getPlayerScoreBoard(P1)));
	}

	@Test
	public void test_initScore_3x2_connect2() {
		ScoreBoard board = new ScoreBoard(3, 2, 2);

		int[][] expectedScore = { { 3, 5, 3 }, { 3, 5, 3 } };

		assertTrue(boardEquals(expectedScore, board.getPlayerScoreBoard(P1)));
	}

	@Test
	public void test_initScore_7x1_connect4() {
		ScoreBoard board = new ScoreBoard(7, 1, 4);

		int[][] expectedScore = { { 1, 2, 3, 4, 3, 2, 1 } };

		assertTrue(boardEquals(expectedScore, board.getPlayerScoreBoard(P1)));
	}

	@Test
	public void test_initScore_7x1_connect3() {
		ScoreBoard board = new ScoreBoard(7, 1, 3);

		int[][] expectedScore = { { 1, 2, 3, 3, 3, 2, 1 } };

		assertTrue(boardEquals(expectedScore, board.getPlayerScoreBoard(P1)));
	}

	@Test
	public void test_initScore_1x7_connect4() {
		ScoreBoard board = new ScoreBoard(1, 7, 4);

		int[][] expectedScore = { { 1 }, { 2 }, { 3 }, { 4 }, { 3 }, { 2 },
				{ 1 } };

		assertTrue(boardEquals(expectedScore, board.getPlayerScoreBoard(P1)));
	}

	@Test
	public void test_initScore_1x7_connect3() {
		ScoreBoard board = new ScoreBoard(1, 7, 3);

		int[][] expectedScore = { { 1 }, { 2 }, { 3 }, { 3 }, { 3 }, { 2 },
				{ 1 } };

		assertTrue(boardEquals(expectedScore, board.getPlayerScoreBoard(P1)));
	}

	@Test
	public void test_initScore_7x2_connect2() {
		ScoreBoard board = new ScoreBoard(7, 2, 2);

		int[][] expectedScore = { { 3, 5, 5, 5, 5, 5, 3 },
				{ 3, 5, 5, 5, 5, 5, 3 } };

		assertTrue(boardEquals(expectedScore, board.getPlayerScoreBoard(P1)));
	}

	@Test
	public void test_initScore_7x6_connect4() {
		ScoreBoard board = new ScoreBoard(7, 6, 4);

		int[][] expectedScore = { { 3, 4, 5, 7, 5, 4, 3 },
				{ 4, 6, 8, 10, 8, 6, 4 }, { 5, 8, 11, 13, 11, 8, 5 },
				{ 5, 8, 11, 13, 11, 8, 5 }, { 4, 6, 8, 10, 8, 6, 4 },
				{ 3, 4, 5, 7, 5, 4, 3 } };

		assertTrue(boardEquals(expectedScore, board.getPlayerScoreBoard(P1)));
	}

	@Test
	public void test_updatePlayerScoreBoard_3x3_connect2() {
		ScoreBoard board = new ScoreBoard(3, 3, 2);

		int[][] opponentMoves_col1 = { { 9, 9, 9 }, { 9, 9, 9 }, { 9, 2, 9 } };

		board.setBoard(opponentMoves_col1);

		int[][] expectedPlayerScore = { { 3, 5, 3 }, { 4, 7, 4 }, { 2, 0, 2 } };

		int[][] expectedOpponentScore = { { 3, 5, 3 }, { 5, 8, 5 }, { 3, 5, 3 } };

		assertTrue(boardEquals(expectedPlayerScore,
				board.getPlayerScoreBoard(P1)));
		assertTrue(boardEquals(expectedOpponentScore,
				board.getPlayerScoreBoard(P2)));

	}

	@Test
	public void test_updateOpponentScoreBoard_3x3_connect2() {

		ScoreBoard board = new ScoreBoard(3, 3, 2);

		int[][] playerMoves_col0 = { { 9, 9, 9 }, { 9, 9, 9 }, { 1, 9, 9 } };

		board.setBoard(playerMoves_col0);

		int[][] expectedPlayerScore = { { 3, 5, 3 }, { 5, 8, 5 }, { 3, 5, 3 } };

		int[][] expectedOpponentScore = { { 3, 5, 3 }, { 4, 7, 5 }, { 0, 4, 3 } };

		assertTrue(boardEquals(expectedPlayerScore,
				board.getPlayerScoreBoard(P1)));
		assertTrue(boardEquals(expectedOpponentScore,
				board.getPlayerScoreBoard(P2)));

	}

	@Test
	public void test_2moves_ScoreBoard_3x3_connect2() {

		ScoreBoard board = new ScoreBoard(3, 3, 2);

		int[][] moves = { { 9, 9, 9 }, { 9, 9, 9 }, { 1, 2, 9 } };

		board.setBoard(moves);

		int[][] expectedPlayerScore = { { 3, 5, 3 }, { 4, 7, 4 }, { 2, 0, 2 } };

		int[][] expectedOpponentScore = { { 3, 5, 3 }, { 4, 7, 5 }, { 0, 4, 3 } };

		assertTrue(boardEquals(expectedPlayerScore,
				board.getPlayerScoreBoard(P1)));
		assertTrue(boardEquals(expectedOpponentScore,
				board.getPlayerScoreBoard(P2)));

	}

	@Test
	public void test_3moves_ScoreBoard_3x3_connect2() {

		ScoreBoard board = new ScoreBoard(3, 3, 2);

		int[][] moves = { { 9, 9, 9 }, { 2, 9, 9 }, { 1, 2, 9 } };

		board.setBoard(moves);

		int[][] expectedPlayerScore = { { 2, 4, 3 }, { 0, 6, 4 }, { 1, 0, 2 } };

		int[][] expectedOpponentScore = { { 3, 5, 3 }, { 4, 7, 5 }, { 0, 4, 3 } };

		assertTrue(boardEquals(expectedPlayerScore,
				board.getPlayerScoreBoard(P1)));
		assertTrue(boardEquals(expectedOpponentScore,
				board.getPlayerScoreBoard(P2)));

	}

	@Test
	public void test_3moves_ScoreBoard_4x4_connect3() {

		ScoreBoard board = new ScoreBoard(4, 4, 3);

		int[][] moves = { { 9, 9, 9, 9 }, { 9, 9, 9, 9 }, { 9, 9, 9, 9 },
				{ 2, 2, 9, 2 } };

		board.setBoard(moves);

		int[][] expectedPlayerScore = { { 3, 4, 4, 3 }, { 3, 5, 6, 2 },
				{ 3, 5, 5, 3 }, { 0, 0, 2, 0 } };

		assertTrue(boardEquals(expectedPlayerScore,
				board.getPlayerScoreBoard(P1)));

	}

	public boolean boardEquals(int[][] b1, int[][] b2) {
		return Arrays.deepEquals(b1, b2);
	}

}
