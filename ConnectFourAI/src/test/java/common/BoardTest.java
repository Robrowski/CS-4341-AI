package common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {

	Board board;
	final int height = 6;
	final int width = 7;
	final int EMPTY = 9;
	final int P1 = 1;
	final int P2 = 2;

	@Before
	public void setup() {
		this.board = new Board(width, height, 3);
	}

	@Test
	public void test_init_EmptyBoard() {
		for (int r = 0; r < height; r++) {
			for (int c = 0; c < width; c++) {
				assertEquals(EMPTY, board.getPiece(r, c));
			}
		}
		board.getBoard(); // for coverage
	}

	@Test
	public void test_countPiecesInCol_EmptyBoard() {
		for (int c = 0; c < height; c++) {
			assertEquals(0, board.countPiecesInCol(c));
		}
	}

	@Test
	public void test_Add_FirstCol() {
		add_helper(0, P1);
		assertEquals(1, board.countPiecesInCol(0));
		assertEquals("Piece placed in the wrong place", P1,
				board.getPiece(0, 0));
	}

	@Test
	public void test_Add_2ndCol() {
		add_helper(1, P2);
		assertEquals(P2, board.getPiece(0, 1));
	}

	@Test
	public void test_AddOverflow() {
		for (int i = 0; i < height; i++) {
			assertEquals(Board.SUCCESS, add_helper(1, (i % 2) + 1));
		}

		assertEquals(Board.FAILURE_TO_PLACE, add_helper(1, P2));
	}

	@Test
	public void test_Pop_basic() {
		int col = 0;
		add_helper(col, P1);
		board.applyMove(new MoveHolder(col).setMove(Move.POP), P1);

		// Confirm that the piece is gone
		assertEquals(EMPTY, board.getPiece(0, col));
	}

	@Test
	public void test_DropDropPop() {
		int col = 2;
		add_helper(col, P2);
		add_helper(col, P1);
		board.applyMove(new MoveHolder(col).setMove(Move.POP), P2);

		// Confirm that the top piece moved down
		assertEquals(P1, board.getPiece(0, col));
		assertEquals(EMPTY, board.getPiece(1, col));
	}

	@Test
	public void test_PopEmpty() {
		assertEquals(Board.FAILURE_TO_PLACE,
				board.applyMove(new MoveHolder(0).setMove(Move.POP), P2));
	}

	@Test
	public void test_DoublePop() {
		// Player 2
		add_helper(0, P2);
		add_helper(1, P2);

		assertEquals(Board.SUCCESS,
				board.applyMove(new MoveHolder(0).setMove(Move.POP), P2));
		assertEquals(Board.FAILURE_TO_PLACE,
				board.applyMove(new MoveHolder(1).setMove(Move.POP), P2));

		// Player 1
		add_helper(2, P1);
		add_helper(3, P1);
		assertEquals(Board.SUCCESS,
				board.applyMove(new MoveHolder(2).setMove(Move.POP), P1));
		assertEquals(Board.FAILURE_TO_PLACE,
				board.applyMove(new MoveHolder(3).setMove(Move.POP), P1));

	}

	@Test
	public void test_getTopPiece_empty() {
		for (int c = 0; c < width; c++) {
			assertEquals(EMPTY, board.getTopPiece(c));
		}
	}

	@Test
	public void test_DeppCopyConstructor_AfterAdding() {
		// Add pieces then copy and check
		add_helper(0, P1);
		add_helper(0, P2);
		add_helper(4, P1);

		Board copy = new Board(this.board);

		// The boards should have the same pieces in the same places
		assertEquals(board.getPiece(0, 0), copy.getPiece(0, 0));
		assertEquals(board.getPiece(1, 0), copy.getPiece(1, 0));
		assertEquals(board.getPiece(0, 4), copy.getPiece(0, 4));
	}

	@Test
	public void test_DeppCopyConstructor_BeforeAdding() {
		// Copy, add pieces and then check
		// Add pieces then copy and check
		Board copy = new Board(this.board);

		add_helper(0, P1);
		add_helper(0, P2);
		add_helper(4, P1);

		// The boards should not have the same pieces in the same places
		assertNotEquals(board.getPiece(0, 0), copy.getPiece(0, 0));
		assertNotEquals(board.getPiece(1, 0), copy.getPiece(1, 0));
		assertNotEquals(board.getPiece(0, 4), copy.getPiece(0, 4));
	}

	@Test
	public void test_getPossibleMoves_Empty() {
		assertEquals(width, board.getPossibleMoves(P1).size());
		assertEquals(width, board.getPossibleMoves(P2).size());
	}

	@Test
	public void test_getPossibleMoves_OnePieceDown() {
		add_helper(3, P1);
		assertEquals(width + 1, board.getPossibleMoves(P1).size());
		assertEquals(width, board.getPossibleMoves(P2).size());
	}

	// TODO - Exhaustive board tests
	//
	// getPossibleMoves
	// --- Pops, late game, etc

	@Test
	public void test_applyMove_detectWin() {
		add_helper(0, P1);
		add_helper(0, P1);
		assertEquals(Board.WIN, add_helper(0, P1));

	}

	/**
	 * Applies an addition move to the board
	 * 
	 * @param col
	 * @param player
	 */
	private int add_helper(int col, int player) {
		int result = board.applyMove(new MoveHolder(col), player);

		// Confirm that there is a piece in that column matching the player
		assertEquals("The piece was not placed properly...", player,
				board.getTopPiece(col)); // Confirm the pieces are in place

		return result;
	}

}
