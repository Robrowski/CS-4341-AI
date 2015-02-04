package common;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import common.board.Board;

public class BoardUploadTest {
	Board board;
	final int height = 6;
	final int width = 7;
	final int EMPTY = Board.EMPTY;
	final int P1 = 1;
	final int P2 = 2;

	/** Boards */
	int[] a_board = { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1, 2, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 2, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 1 };
	int[] empty_board = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0 };
	int[] one_piece_board = { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0 };
	int[] two_piece_board = { 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0 };

	int[] four_piece_board = { 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0 };
	@Before
	public void setup() {
		this.board = new Board(width, height, 3);
	}


	@Test
	public void test_load_EmptyBoard() {
		board.upload(empty_board);

		for (int r = 0; r < height; r++) {
			for (int c = 0; c < width; c++) {
				assertEquals(EMPTY, board.getPiece(r, c));
			}
		}
		board.getBoard(); // for coverage
	}

	@Test
	public void test_load_one_piece_board() {
		board.upload(one_piece_board);
		assertEquals(P1, board.getPiece(0, 0));
	}

	@Test
	public void test_load_two_piece_board() {
		board.upload(two_piece_board);
		assertEquals(P1, board.getPiece(0, 0));
		assertEquals(P2, board.getPiece(1, 0));
	}

	@Test
	public void test_load_four_piece_board() {
		board.upload(four_piece_board);
		assertEquals(P1, board.getPiece(0, 0));
		assertEquals(P2, board.getPiece(1, 0));
		assertEquals(P1, board.getPiece(0, 2));
		assertEquals(P2, board.getPiece(1, 2));
	}
}
