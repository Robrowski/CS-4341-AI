/**
 * Matt Costi - mscosti  
 * Rob Dabrowski - rpdabrowski
 * 
 * CS 4341 C 2015
 * Prof. Niel Heffernan
 * WPI 
 */
package common;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import common.board.Board;

public class BoardTest {

	Board board;
	final int height = 6;
	final int width = 7;
	final int EMPTY = Board.EMPTY;
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
	public void test_getPossibleMoves_OnePieceDown() {
		int[] one_piece_board = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0 };
		board.upload(one_piece_board);
		assertEquals(width, board.getPossibleMoves(P1).size());
		assertEquals(width, board.getPossibleMoves(P2).size());
	}

	@Test
	public void test_deep_copy() {
		int[] four_piece_board = { 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0 };
		board.upload(four_piece_board);

		Board c = board.copy();
		assertEquals(P1, c.getPiece(0, 0));
		assertEquals(P2, c.getPiece(1, 0));
		assertEquals(P1, c.getPiece(0, 2));
		assertEquals(P2, c.getPiece(1, 2));
	}

	@Test
	public void test_getTopPiece_empty() {
		for (int c = 0; c < width; c++) {
			assertEquals(EMPTY, board.getTopPiece(c));
		}
	}

	@Test
	public void test_getPossibleMoves_Empty() {
		assertEquals(width, board.getPossibleMoves(P1).size());
		assertEquals(width, board.getPossibleMoves(P2).size());
	}

	@Test
	public void test_applyMove_detectVerticalWin() {

		int[] a_board = { 1, 1, 1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0 };
		board.upload(a_board);
		assertEquals(Board.WIN,
				board.detect_win(new MoveHolder(0).setRow(2).setPlayer(P1)));
	}

	@Test
	public void test_applyMove_detectHorizontalWin() {
		int[] a_board = { 1, 1, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0,
				2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0 };
		board.upload(a_board);
		assertEquals(Board.WIN,
				board.detect_win(new MoveHolder(3).setRow(0).setPlayer(P2)));
	}

	@Test
	public void test_applyMove_detectHorizontalWin2() {
		int[] a_board = { 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 0,
				0, 0, 0, 0 };
		board.upload(a_board);
		assertEquals(Board.WIN,
				board.detect_win(new MoveHolder(4).setRow(0).setPlayer(P2)));
	}

	@Test
	public void test_applyMove_detectRDiagonalWin() {
		int[] a_board = { 1, 1, 0, 0, 0, 0, 2, 1, 0, 0, 0, 0, 2, 2, 1, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0 };
		board.upload(a_board);
		assertEquals(Board.WIN,
				board.detect_win(new MoveHolder(2).setRow(2).setPlayer(P1)));
	}

	@Test
	public void test_applyMove_detectRDiagonalWin2() {
		int[] a_board = { 1, 1, 0, 0, 0, 0, 2, 2, 1, 0, 0, 0, 2, 1, 2, 1, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0 };
		board.upload(a_board);
		assertEquals(Board.WIN,
				board.detect_win(new MoveHolder(2).setRow(3).setPlayer(P1)));
	}

	@Test
	public void test_applyMove_detectLDiagonalWin() {
		int[] a_board = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 1, 1, 0, 0,
				1, 2, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0 };
		board.upload(a_board);
		assertEquals(Board.WIN,
				board.detect_win(new MoveHolder(2).setRow(3).setPlayer(P1)));
	}

}
