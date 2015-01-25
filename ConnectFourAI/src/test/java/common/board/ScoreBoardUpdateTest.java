/**
 * Matt Costi - mscosti  
 * Rob Dabrowski - rpdabrowski
 * 
 * CS 4341 C 2015
 * Prof. Niel Heffernan
 * WPI 
 */
package common.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import common.MoveHolder;

public class ScoreBoardUpdateTest {

	ScoreBoard b;

	final int N = 3;
	final int height = 6;
	final int width = 7;
	final int EMPTY = Board.EMPTY;
	final int P1 = 1; // player
	final int P2 = 2; // opponent

	@Before
	public void setup() {
		this.b = makeBoard(width, height, N);
	}


	@Test
	public void test_updateScoreBoard_1x2x1() {
		ScoreBoard b = makeBoard(1, 2, 1);
		// Assume initialized properly
		b.updateScoreBoard(m(0, P1).setRow(0));
		
		int[][] expected_player = { { 1 }, { 1 } };
		int[][] expected_opp = { { 0 }, { 1 } };
		assertTrue(nestedArrayEquals(expected_player, b.getPlayerScoreBoard(P1)));
		assertTrue(nestedArrayEquals(expected_opp, b.getPlayerScoreBoard(P2)));
	}

	@Test
	public void test_updateScoreBoard_2x1() {
		ScoreBoard b = makeBoard(2, 1, 1);
		// Assume initialized properly
		b.updateScoreBoard(m(0, P1).setRow(0));

		int[][] expected_player = { { 1, 1 } };
		int[][] expected_opp = { { 0, 1 } };
		assertTrue(nestedArrayEquals(expected_player, b.getPlayerScoreBoard(P1)));
		assertTrue(nestedArrayEquals(expected_opp, b.getPlayerScoreBoard(P2)));
	}

	@Test
	public void test_updateScoreBoard_2x2x1() {
		ScoreBoard b = makeBoard(2, 2, 1);
		// Player 1 plays in column 0
		b.updateScoreBoard(m(0, P1).setRow(0));

		int[][] expected_player = { { 1, 1 }, { 1, 1} };
		int[][] expected_opp = { { 0, 1 }, {1 , 1 }};
		assertTrue(nestedArrayEquals(expected_player, b.getPlayerScoreBoard(P1)));
		assertTrue(nestedArrayEquals(expected_opp, b.getPlayerScoreBoard(P2)));
		
		// Player 2 plays in column 0
		b.updateScoreBoard(m(0, P2).setRow(1));

		int[][] expected_player2 = { { 1, 1 }, { 0, 1 } };
		int[][] expected_opp2 = { { 0, 1 }, { 1, 1 } };
		assertTrue(nestedArrayEquals(expected_player2,
				b.getPlayerScoreBoard(P1)));
		assertTrue(nestedArrayEquals(expected_opp2, b.getPlayerScoreBoard(P2)));
	}

	@Test
	public void test_updateScoreBoard_2x2x2() {
		ScoreBoard b = makeBoard(2, 2, 2);
		// Player 1 plays in column 0
		assertEquals(Board.SUCCESS, b.applyMove(m(0, P1)));

		int[][] expected_player = { { 3, 3 }, { 3, 3 } };
		int[][] expected_opp = { { 0, 2 }, { 2, 2 } };

		assertTrue(nestedArrayEquals(expected_player, b.getPlayerScoreBoard(P1)));
		assertTrue(nestedArrayEquals(expected_opp, b.getPlayerScoreBoard(P2)));

		// Player 2 plays in column 0
		assertEquals(Board.SUCCESS, b.applyMove(m(0, P2)));

		int[][] expected_player2 = { { 2, 2 }, { 0, 2 } };
		int[][] expected_opp2 = { { 0, 2 }, { 2, 2 } };
		assertTrue(nestedArrayEquals(expected_player2,
				b.getPlayerScoreBoard(P1)));
		assertTrue(nestedArrayEquals(expected_opp2, b.getPlayerScoreBoard(P2)));
	}

	@Test
	public void test_updateScoreBoard_3x2x2() {
		ScoreBoard b = makeBoard(3, 2, 2);
		// Player 1 plays in column 0
		assertEquals(Board.SUCCESS, b.applyMove(m(0, P1)));

		int[][] expected_player = { { 3, 5, 3 }, { 3, 5, 3 } };
		int[][] expected_opp = { { 0, 4, 3 }, { 2, 4, 3 } };

		assertTrue(nestedArrayEquals(expected_player, b.getPlayerScoreBoard(P1)));
		assertTrue(nestedArrayEquals(expected_opp, b.getPlayerScoreBoard(P2)));

		// Player 2 plays in column 0
		assertEquals(Board.SUCCESS, b.applyMove(m(0, P2)));

		int[][] expected_player2 = { { 2, 4, 3 }, { 0, 4, 3 } };
		int[][] expected_opp2 = { { 0, 4, 3 }, { 2, 4, 3 } };
		assertTrue(nestedArrayEquals(expected_player2,
				b.getPlayerScoreBoard(P1)));
		assertTrue(nestedArrayEquals(expected_opp2, b.getPlayerScoreBoard(P2)));

		// Player 1 plays in column 2
		assertEquals(Board.SUCCESS, b.applyMove(m(2, P1)));

		int[][] expected_player3 = { { 2, 4, 3 }, { 0, 4, 3 } };
		int[][] expected_opp3 = { { 0, 3, 0 }, { 2, 3, 2 } };

		assertTrue(nestedArrayEquals(expected_player3,
				b.getPlayerScoreBoard(P1)));
		assertTrue(nestedArrayEquals(expected_opp3, b.getPlayerScoreBoard(P2)));
	}

	@Test
	public void test_updateScoreBoard_3x3x3() {
		ScoreBoard b = makeBoard(3, 3, 3);
		// Player 1 plays in column 0
		assertEquals(Board.SUCCESS, b.applyMove(m(0, P1)));

		int[][] expected_player = { { 3, 2, 3 }, { 2, 4, 2 }, { 3, 2, 3 } };
		int[][] expected_opp = { { 0, 1, 2 }, { 1, 3, 2 }, { 2, 2, 2 } };

		assertTrue(nestedArrayEquals(expected_player, b.getPlayerScoreBoard(P1)));
		assertTrue(nestedArrayEquals(expected_opp, b.getPlayerScoreBoard(P2)));

		// Player 2 plays in column 0
		assertEquals(Board.SUCCESS, b.applyMove(m(0, P2)));

		int[][] expected_player2 = { { 2, 2, 3 }, { 0, 3, 1 }, { 2, 2, 3 } };
		int[][] expected_opp2 = { { 0, 1, 2 }, { 1, 3, 2 }, { 2, 2, 2 } };
		assertTrue(nestedArrayEquals(expected_player2,b.getPlayerScoreBoard(P1)));
		assertTrue(nestedArrayEquals(expected_opp2, b.getPlayerScoreBoard(P2)));

		// Player 1 plays in column 1
		assertEquals(Board.SUCCESS, b.applyMove(m(1, P1)));

		int[][] expected_player3 = { { 2, 2, 3 }, { 0, 3, 1 }, { 2, 2, 3 } };
		int[][] expected_opp3 = { { 0, 0, 2 }, { 1, 2, 2 }, { 2, 1, 2 } };

		printBoard(b, P2);
		assertTrue(nestedArrayEquals(expected_player3, b.getPlayerScoreBoard(P1)));
		assertTrue(nestedArrayEquals(expected_opp3, b.getPlayerScoreBoard(P2)));

		// Player 2 plays in column 2
		assertEquals(Board.SUCCESS, b.applyMove(m(2, P2)));

		int[][] exp_player4 = { { 1, 1, 0 }, { 0, 2, 0 }, { 1, 2, 2 } };
		int[][] expected_opp4 = { { 0, 0, 2 }, { 1, 2, 2 }, { 2, 1, 2 } };
		assertTrue(nestedArrayEquals(exp_player4, b.getPlayerScoreBoard(P1)));
		assertTrue(nestedArrayEquals(expected_opp4, b.getPlayerScoreBoard(P2)));

	}

	@Test
	public void test_updateScoreBoard_8x1x4() {
		ScoreBoard b = makeBoard(8, 1, 4);
		// Player 1 plays in column 0
		assertEquals(Board.SUCCESS, b.applyMove(m(0, P1)));

		int[][] expected_player = { { 1, 2, 3, 4, 4, 3, 2, 1 } };
		int[][] expected_opp = { { 0, 1, 2, 3, 4, 3, 2, 1 } };

		assertTrue(nestedArrayEquals(expected_player, b.getPlayerScoreBoard(P1)));
		assertTrue(nestedArrayEquals(expected_opp, b.getPlayerScoreBoard(P2)));

		// Player 2 plays in column 4
		assertEquals(Board.SUCCESS, b.applyMove(m(4, P2)));

		int[][] expected_player2 = { { 1, 1, 1, 1, 0, 0, 0, 0 } };
		int[][] expected_opp2 = { { 0, 1, 2, 3, 4, 3, 2, 1 } };

		assertTrue(nestedArrayEquals(expected_player2,
				b.getPlayerScoreBoard(P1)));
		assertTrue(nestedArrayEquals(expected_opp2, b.getPlayerScoreBoard(P2)));

	}

	@Test
	public void test_updateScoreBoard_1x8x4() {
		ScoreBoard b = makeBoard(1, 8, 4);
		// Player 1 plays in column 0
		assertEquals(Board.SUCCESS, b.applyMove(m(0, P1)));

		int[][] expected_player = { { 1 }, { 2 }, { 3 }, { 4 }, { 4 }, { 3 },
				{ 2 }, { 1 } };
		int[][] expected_opp = { { 0 }, { 1 }, { 2 }, { 3 }, { 4 }, { 3 },
				{ 2 }, { 1 } };

		assertTrue(nestedArrayEquals(expected_player, b.getPlayerScoreBoard(P1)));
		assertTrue(nestedArrayEquals(expected_opp, b.getPlayerScoreBoard(P2)));

		// Player 2 plays in column 4
		assertEquals(Board.SUCCESS, b.applyMove(m(0, P2)));

		int[][] expected_player2 = { { 0 }, { 0 }, { 1 }, { 2 }, { 3 }, { 3 },
				{ 2 }, { 1 } };
		int[][] expected_opp2 = { { 0 }, { 1 }, { 2 }, { 3 }, { 4 }, { 3 },
				{ 2 }, { 1 } };
		assertTrue(nestedArrayEquals(expected_player2,
				b.getPlayerScoreBoard(P1)));
		assertTrue(nestedArrayEquals(expected_opp2, b.getPlayerScoreBoard(P2)));

	}

	@Test
	public void test_updateScoreBoard_4x4x3_funny_case1() {
		ScoreBoard b = makeBoard(4, 4, 3);
		// Player 1 plays in column 0
		assertEquals(Board.SUCCESS, b.applyMove(m(0, P1)));
		assertEquals(Board.SUCCESS, b.applyMove(m(3, P1)));
		assertEquals(Board.SUCCESS, b.applyMove(m(0, P2)));
		assertEquals(Board.SUCCESS, b.applyMove(m(3, P2)));
		/**
		 *  _   _   _   _
		 *  _   _   _   _
		 *  2   _   _   2
		 *  1   n   _   1
		 */
		int[][] expected_player = { { 2, 4, 4, 2 }, { 0, 5, 5, 0 }, { 2, 6, 6, 2 }, {2, 3, 3, 2} };
		int[][] expected_opp = { { 0, 2, 2, 0 }, 
								 { 3, 6, 6, 3 },
								 { 3, 6, 6, 3 }, 
								 { 3, 4, 4, 3 } };

		assertTrue(nestedArrayEquals(expected_player, b.getPlayerScoreBoard(P1)));
		assertTrue(nestedArrayEquals(expected_opp, b.getPlayerScoreBoard(P2)));

		// Now the funny business
		assertEquals(Board.SUCCESS, b.applyMove(m(1, P1)));

		int[][] expected_opp2 = { { 0, 0, 2, 0 }, 
								  { 3, 5, 5, 3 },
								  { 3, 5, 6, 2 },
								  { 3, 4, 4, 3 } };

		assertTrue(nestedArrayEquals(expected_player, b.getPlayerScoreBoard(P1)));
		assertTrue(nestedArrayEquals(expected_opp2, b.getPlayerScoreBoard(P2)));
	}
	
	@Test
	public void test_updateScoreBoard_7x6x4() {
		ScoreBoard b = makeBoard(7, 6, 4);
		// Column 0
		assertEquals(Board.SUCCESS, b.applyMove(m(0, P1)));
		assertEquals(Board.SUCCESS, b.applyMove(m(0, P1)));
		assertEquals(Board.SUCCESS, b.applyMove(m(0, P1)));
		assertEquals(Board.SUCCESS, b.applyMove(m(0, P2)));

		// Column 1
		assertEquals(Board.SUCCESS, b.applyMove(m(1, P2)));
		assertEquals(Board.SUCCESS, b.applyMove(m(1, P2)));
		assertEquals(Board.SUCCESS, b.applyMove(m(1, P2)));

		// Column 3
		assertEquals(Board.SUCCESS, b.applyMove(m(3, P1)));
		assertEquals(Board.SUCCESS, b.applyMove(m(3, P1)));
		assertEquals(Board.SUCCESS, b.applyMove(m(3, P1)));
		assertEquals(Board.SUCCESS, b.applyMove(m(3, P2)));

		// Column 4
		assertEquals(Board.SUCCESS, b.applyMove(m(4, P1)));
		assertEquals(Board.SUCCESS, b.applyMove(m(4, P2)));
		assertEquals(Board.SUCCESS, b.applyMove(m(4, P1)));
		assertEquals(Board.SUCCESS, b.applyMove(m(4, P1)));
		assertEquals(Board.SUCCESS, b.applyMove(m(4, P1)));
		assertEquals(Board.SUCCESS, b.applyMove(m(4, P2)));

		// Column 5
		assertEquals(Board.SUCCESS, b.applyMove(m(5, P2)));
		assertEquals(Board.SUCCESS, b.applyMove(m(5, P2)));
		assertEquals(Board.SUCCESS, b.applyMove(m(5, P1)));

		// Column 6
		assertEquals(Board.SUCCESS, b.applyMove(m(6, P2)));
		assertEquals(Board.SUCCESS, b.applyMove(m(6, P2)));
		assertEquals(Board.SUCCESS, b.applyMove(m(6, P2)));
		assertEquals(Board.SUCCESS, b.applyMove(m(6, P1)));

		/**
		 *  _   _   _   _   2   _   _
		 *  _   _   _   _   1   _   _
		 *  2   _   _   2   1   _   1
		 *  1   2   _   1   1   1   2
		 *  1   2   _   1   2   2   2
		 *  1   2   _   1   1   2   2
		 */
				
		int[][] expected_player =
			{
				{ 0, 0, 2, 0, 1, 0, 0 },
				{ 0, 0, 3, 4, 0, 0, 0 },
				{ 1, 0, 6, 4, 3, 3, 0 },
				{ 0, 3, 4, 0, 3, 3, 0 },
				{ 2, 3, 6, 5, 3, 5, 2 },
				{ 2, 1, 3, 2, 0, 1, 1 } };
		int[][] expected_opp =
			{
				{ 0, 1, 1, 0, 0, 0, 0 },
				{ 0, 2, 2, 0, 0, 0, 0 },
				{ 0, 4, 3, 0, 0, 0, 0 },
				{ 1, 4, 5, 1, 0, 0, 0 },
				{ 1, 3, 3, 2, 0, 0, 0 },
				{ 1, 3, 4, 4, 4, 2, 1 } };
		assertTrue(nestedArrayEquals(expected_player, b.getPlayerScoreBoard(P1)));
		assertTrue(nestedArrayEquals(expected_opp, b.getPlayerScoreBoard(P2)));
	}

	
	
	/**
	 * Deep compare two arrays
	 * 
	 * @param expected
	 * @param actual
	 */
	public static boolean nestedArrayEquals(int[][] expected, int[][] actual) {
		return Arrays.deepEquals(expected, actual);
	}

	/**
	 * Make a MoveHolder representing the given move
	 * 
	 * @param col
	 * @param player
	 * @return
	 */
	public MoveHolder m(int col, int player) {
		return new MoveHolder(col).setPlayer(player);
	}

	/**
	 * Make a board of the given size
	 * 
	 * @param width
	 * @param height
	 * @param N
	 * @return
	 */
	private ScoreBoard makeBoard(int width, int height, int N) {
		final String[] args = { "--score-board" };
		return (ScoreBoard) BoardFactory.makeBoard(width, height, N,
				Arrays.asList(args));
	}

	public void printBoard(ScoreBoard board, int player) {
		int[][] b = (player == P1 ? board.getPlayerScoreBoard(P1) : board
				.getPlayerScoreBoard(P2));

		for (int i = board.height - 1; i >= 0; i--) {
			for (int item : b[i]) {
				System.out.print(item + " ");
			}
			System.out.println();
		}
	}

}
