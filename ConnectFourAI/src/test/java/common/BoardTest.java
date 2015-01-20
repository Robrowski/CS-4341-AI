package common;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {
	
	Board board;
	final int height = 6;
	final int width = 7;
	final int EMPTY = 9;

	@Before
	public void setup() {
		this.board = new Board(width, height);
	}

	@Test
	public void test_init_EmptyBoard() {
		for (int r = 0; r < height; r++) {
			for (int c = 0; c < width; c++) {
				assertEquals(EMPTY, board.getPiece(r, c));
			}
		}
	}

	@Test
	public void test_countPiecesInCol_EmptyBoard() {
		for (int c = 0; c < height; c++) {
			assertEquals(0, board.countPiecesInCol(c));
		}

	}
		
	@Test
	public void test_Add_FirstCol() {
		int player = 1;
		add_helper(0, player);
		assertEquals(1, board.countPiecesInCol(0));
		assertEquals("Piece placed in the wrong place", player,
				board.getPiece(0, 0));
	}

	@Test
	public void test_Add_2ndCol() {
		int player = 2;
		add_helper(1, player);
		assertEquals(player, board.getPiece(0, 1));
	}


	/**
	 * Applies an addition move to the board
	 * 
	 * @param col
	 * @param player
	 */
	private void add_helper(int col, int player) {
		board.applyMove(new MoveHolder(col), player);
	}

}


