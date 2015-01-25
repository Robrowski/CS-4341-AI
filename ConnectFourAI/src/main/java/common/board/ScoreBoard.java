/**
 * Matt Costi - mscosti  
 * Rob Dabrowski - rpdabrowski
 * 
 * CS 4341 C 2015
 * Prof. Niel Heffernan
 * WPI 
 */
package common.board;

import java.util.Arrays;

import common.Move;
import common.MoveHolder;


public class ScoreBoard extends Board{
	
	private int[][] player_one_score_board;
	private int[][] player_two_score_board;

	public ScoreBoard(int width, int height, int numToWin) {
		super(width, height, numToWin);
		this.player_one_score_board = makeEmptyBoard();
		this.player_two_score_board = makeEmptyBoard();
		initScoreBoards();
		
	}
	
	/**
	 * Constructor used for deep copying
	 * 
	 * @param copy
	 */
	protected ScoreBoard(Board copy, int[][] player_one_score_board,
			int[][] player_two_score_board) {
		super(copy.width, copy.height, copy.numToWin, copy.board,
				copy.p1_used_pop, copy.p2_used_pop);
		this.player_one_score_board = makeBoardCopy(player_one_score_board,
				height, width);
		this.player_two_score_board = makeBoardCopy(player_two_score_board,
				height, width);
	}

	@Override
	public void updateScoreBoard(MoveHolder move) {
		int[][] to_update = (move.getPlayer() == 2) ? player_one_score_board
				: player_two_score_board;

		// Totally different procedure for updating after Pops..
		if (move.getMove().equals(Move.POP)) {
			this.player_two_score_board = reCalculatePlayerScoreBoard(2);
			this.player_one_score_board = reCalculatePlayerScoreBoard(1);
			return;
		}
		
		int r = move.getRow();
		int c = move.getCol();
		int N = numToWin - 1;

		// 1. Decrease the newly occupied space to zero
		to_update[r][c] = 0;

		// Update in each direction
		for (int i = 0; i < numToWin; i++) {
			// Vertical
			if (inBounds(r + i, c) && inBounds(r + i - 1 * N, c))
				update(board, move, to_update, N, r + i, c, -1, 0);

			// Diagonals and horizontal use the same column
			int ccd = c + i;
			if (ccd < width && ccd - 1 * N >= 0) { // either ends are in bounds
				// Horizontal
				if (inBounds(r, ccd) && inBounds(r, ccd - 1 * N))
					update(board, move, to_update, N, r, ccd, 0, -1);

				// Right diagonal, check Row
				// direction = -1 because right diagonal looks downleft =
				// backwards
				if (r + i - 1 * N >= 0 && r + i < height)
					update(board, move, to_update, N, r + i, ccd, -1, -1);

				// Left diagonal, check row
				if (r - i + N < height && r - i >= 0)
					update(board, move, to_update, N, r - i, ccd, 1, -1);
			}
		}
	}

	/**
	 * A recursive algorithm that looks backwards to see if a win could have
	 * been made with the pieces found. Returns 1 if a win could have been made
	 * and subtracts from the score at the given location.
	 * 
	 * @param board
	 *            Reference to the board
	 * @param m
	 *            The move to update by
	 * @param to_update
	 *            Reference to the appropriate score board
	 * @param remN
	 *            remaining needed to make a "win"
	 * @param r
	 *            the row to start at
	 * @param c
	 *            the column to start at
	 * @param ydir
	 *            the vertical direction to look back in
	 * @param xdir
	 *            the horizontal direction to look back in
	 * @return
	 */
	static private int update(int[][] board, MoveHolder m, int[][] to_update,
			int remN, int r, int c, int ydir, int xdir) {
		// No updating is needed if the ___ has old opponent piece already
		if (board[r][c] == m.getPlayer()
				&& (m.getRow() != r || m.getCol() != c))
			return 0;
		// Terminal case!
		if (remN == 0) {
			to_update[r][c] = Math.max(0, to_update[r][c] - 1);
			return 1;
		}

		// Call update on the next square, update accordingly and return
		int ret = update(board, m, to_update, remN - 1, r + ydir, c + xdir,
				ydir, xdir);
		to_update[r][c] = Math.max(0, to_update[r][c] - ret);
		return ret;
	}


	private void initScoreBoards() {
		// don't copy twice.. derrr
		this.player_two_score_board = reCalculatePlayerScoreBoard(-1);
		this.player_one_score_board = makeBoardCopy(player_two_score_board,
				height, width);
	}

	private int[][] reCalculatePlayerScoreBoard(int player) {
		int[][] newScoreBoard = makeEmptyBoard();
		MoveHolder currentSpace = new MoveHolder();
		currentSpace.setPlayer(player);
		if (numToWin == 1) {
			for (int[] row : newScoreBoard)
				Arrays.fill(row, 1);
			return newScoreBoard;
		}
		for (int i = 0; i < this.height; i++) {
			currentSpace.setRow(i);
			for (int j = 0; j < this.width; j++) {
				currentSpace.setCol(j);
				if (board[i][j] == player || board[i][j] == EMPTY) {
					int hScore = scoreHorizontal(currentSpace);
					int vScore = scoreVertical(currentSpace);
					int ldScore = scoreLDiagonal(currentSpace);
					int rdScore = scoreRDiagonal(currentSpace);
					int totalScore = hScore + vScore + ldScore + rdScore;
					newScoreBoard[i][j] = totalScore;
				} else {
					newScoreBoard[i][j] = 0;
				}

			}
		}
		return newScoreBoard;
	}

	private int scoreHorizontal(MoveHolder space) {

		int num_spaces = 1; // the piece placed
		int stopLeft = space.getCol() - this.numToWin + 1;
		int stopRight = space.getCol() + this.numToWin - 1;

		// Count left
		for (int c = space.getCol() - 1; c >= stopLeft; c--) {
			if (c < 0)// off the board
				break;
			if (board[space.getRow()][c] == EMPTY
					|| board[space.getRow()][c] == space.getPlayer())
				num_spaces++;
			else
				break;
		}

		// Count right
		for (int c = space.getCol() + 1; c <= stopRight; c++) {
			if (c >= this.width) // off the board
				break;
			if (board[space.getRow()][c] == EMPTY
					|| board[space.getRow()][c] == space.getPlayer())
				num_spaces++;
			else
				break;
		}

		return Math.max(0, num_spaces + 1 - this.numToWin);
	}

	private int scoreVertical(MoveHolder space) {
		int num_spaces = 1; // the piece placed
		int stopDown = space.getRow() - this.numToWin + 1;
		int stopUp = space.getRow() + this.numToWin - 1;

		// count up
		for (int r = space.getRow() + 1; r <= stopUp; r++) {
			if (r >= this.height) // off the board
				break;
			if (board[r][space.getCol()] == EMPTY
					|| board[r][space.getCol()] == space.getPlayer())
				num_spaces++;
			else
				break;
		}

		// count down
		for (int r = space.getRow() - 1; r >= stopDown; r--) {
			if (r < 0) // off the board
				break;
			if (board[r][space.getCol()] == EMPTY
					|| board[r][space.getCol()] == space.getPlayer())
				num_spaces++;
			else
				break;
		}

		return Math.max(0, num_spaces + 1 - this.numToWin);
	}

	private int scoreRDiagonal(MoveHolder space) {
		int num_spaces = 1; // the piece placed
		int r = space.getRow();
		int c = space.getCol();
		int stopLDown = r - this.numToWin + 1;
		int stopL = c - this.numToWin + 1;
		int stopRUp = r + this.numToWin - 1;
		int stopR = c + this.numToWin - 1;

		// Count down left
		for (int i = -1; r + i >= stopLDown && c + i >= stopL; i--) {
			if (r + i < 0 || c + i < 0)// off the board
				break;
			if (board[r + i][c + i] == EMPTY
					|| board[r + i][c + i] == space.getPlayer())
				num_spaces++;
			else
				break;
		}

		// Count up Right
		for (int i = 1; r + i <= stopRUp && c + i <= stopR; i++) {
			if (r + i >= height || c + i >= width)// off the board
				break;
			if (board[r + i][c + i] == EMPTY
					|| board[r + i][c + i] == space.getPlayer())
				num_spaces++;
			else
				break;
		}

		return Math.max(0, num_spaces + 1 - this.numToWin);
	}

	private int scoreLDiagonal(MoveHolder space) {
		int num_spaces = 1; // the piece placed
		int r = space.getRow();
		int c = space.getCol();

		int stopLUp = r + this.numToWin - 1;
		int stopL = c - this.numToWin + 1;
		int stopRDown = r - this.numToWin + 1;
		int stopR = c + this.numToWin - 1;

		// Count up left
		for (int i = 1; r + i <= stopLUp && c - i >= stopL; i++) {
			if (r + i == height || c - i < 0)// off the board
				break;
			if (board[r + i][c - i] == EMPTY
					|| board[r + i][c - i] == space.getPlayer())
				num_spaces++;
			else
				break;
		}

		// Count down Right
		for (int i = 1; r - i >= stopRDown && c + i <= stopR; i++) {
			if (r - i < 0 || c + i == width)// off the board
				break;
			if (board[r - i][c + i] == EMPTY
					|| board[r - i][c + i] == space.getPlayer())
				num_spaces++;
			else
				break;
		}

		return Math.max(0, num_spaces + 1 - this.numToWin);
	}
	
	public int[][] getPlayerScoreBoard(int player) {
		return (player == 1) ? player_one_score_board : player_two_score_board;
	}

	/**
	 * A convenience function used only for testing.
	 * Allows us to set a board artificially and see if
	 * the scoreBoards were appropriately updated for each player
	 * 
	 * @param newBoard
	 * @return
	 */
	public void setBoard(int[][] newBoard) {
		this.board = makeBoardCopy(newBoard, height, width);
		this.player_one_score_board = reCalculatePlayerScoreBoard(1);
		this.player_two_score_board = reCalculatePlayerScoreBoard(2);
	}

	@Override
	public Board copy() {
		return new ScoreBoard(this, player_one_score_board,
				player_two_score_board);
	}

}
