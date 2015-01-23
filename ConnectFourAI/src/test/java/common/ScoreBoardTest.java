package common;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class ScoreBoardTest {

	@Test
	public void test_initScore_2x2_connect1_fail() {
		ScoreBoard board = new ScoreBoard(2, 2, 1);

		// Sanity check that Arrays.deepEquals is working correctly
		int[][] expectedScore = { { 1, 1 }, { 0, 1 } };

		assertFalse(boardEquals(expectedScore, board.getPlayerScoreBoard()));
	}

	@Test
	public void test_initScore_2x2_connect1() {
		ScoreBoard board = new ScoreBoard(2, 2, 1);

		int[][] expectedScore = { { 1, 1 }, { 1, 1 } };

		System.out.println("2x2 connect1");
		printBoard(board, "player");

		assertTrue(boardEquals(expectedScore, board.getPlayerScoreBoard()));
	}

	@Test
	public void test_initScore_3x3_connect2() {
		ScoreBoard board = new ScoreBoard(3, 3, 2);

		int[][] expectedScore =
			{
				{ 3, 5, 3 },
				{ 5, 8, 5 },
				{ 3, 5, 3 } };

		System.out.println("3x3 connect2");
		printBoard(board, "player");

		assertTrue(boardEquals(expectedScore, board.getPlayerScoreBoard()));
	}

	@Test
	public void test_initScore_7x1_connect4() {
		ScoreBoard board = new ScoreBoard(7, 1, 4);

		int[][] expectedScore = { { 1, 2, 3, 4, 3, 2, 1 } };

		System.out.println("7x1 connect4");
		printBoard(board, "player");

		assertTrue(boardEquals(expectedScore, board.getPlayerScoreBoard()));
	}

	@Test
	public void test_initScore_7x1_connect3() {
		ScoreBoard board = new ScoreBoard(7, 1, 3);

		int[][] expectedScore = { { 1, 2, 3, 3, 3, 2, 1 } };

		System.out.println("7x1 connect3");
		printBoard(board, "player");

		assertTrue(boardEquals(expectedScore, board.getPlayerScoreBoard()));
	}

	@Test
	public void test_initScore_1x7_connect4() {
		ScoreBoard board = new ScoreBoard(1, 7, 4);

		int[][] expectedScore = { { 1 }, { 2 }, { 3 }, { 4 }, { 3 }, { 2 },
				{ 1 } };

		System.out.println("1x7 connect4");
		printBoard(board, "player");

		assertTrue(boardEquals(expectedScore, board.getPlayerScoreBoard()));
	}

	@Test
	public void test_initScore_1x7_connect3() {
		ScoreBoard board = new ScoreBoard(1, 7, 3);

		int[][] expectedScore = { { 1 }, { 2 }, { 3 }, { 3 }, { 3 }, { 2 },
				{ 1 } };

		System.out.println("1x7 connect4");
		printBoard(board, "player");

		assertTrue(boardEquals(expectedScore, board.getPlayerScoreBoard()));
	}

	@Test
	public void test_initScore_7x2_connect2() {
		ScoreBoard board = new ScoreBoard(7, 2, 2);

		int[][] expectedScore =
			{
				{ 3, 5, 5, 5, 5, 5, 3 },
				{ 3, 5, 5, 5, 5, 5, 3 } };

		System.out.println("7x2 connect2");
		printBoard(board, "player");

		assertTrue(boardEquals(expectedScore, board.getPlayerScoreBoard()));
	}

	@Test
	public void test_initScore_7x6_connect4() {
		ScoreBoard board = new ScoreBoard(7, 6, 4);

		int[][] expectedScore =
			{
				{ 3, 4, 5, 7, 5, 4, 3 },
				{ 4, 6, 8, 10, 8, 6, 4 },
				{ 5, 8, 11, 13, 11, 8, 5 },
				{ 5, 8, 11, 13, 11, 8, 5 },
				{ 4, 6, 8, 10, 8, 6, 4 },
				{ 3, 4, 5, 7, 5, 4, 3 } };

		System.out.println("7x6 connect4");
		printBoard(board, "player");

		assertTrue(boardEquals(expectedScore, board.getPlayerScoreBoard()));
	}

	public boolean boardEquals(int[][] b1, int[][] b2) {
		return Arrays.deepEquals(b1, b2);
	}

	public void printBoard(ScoreBoard board, String player) {
		int[][] b = (player.equals("player") ? board.getPlayerScoreBoard()
				: board.getOpponentScoreBoard());

		for (int i = board.height - 1; i >= 0; i--) {
			for (int item : b[i]) {
				System.out.print(item + " ");
			}
			System.out.println();
		}
	}
}
