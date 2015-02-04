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
		super(copy.width, copy.height, copy.numToWin, copy.board);
		this.player_one_score_board = makeBoardCopy(player_one_score_board,
				height, width);
		this.player_two_score_board = makeBoardCopy(player_two_score_board,
				height, width);
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
