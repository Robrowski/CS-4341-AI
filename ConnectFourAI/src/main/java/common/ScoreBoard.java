package common;

import java.util.Arrays;

public class ScoreBoard extends Board{
	
	private int[][] playerScoreBoard;
	private int[][] opponentScoreBoard;

	public ScoreBoard(int width, int height, int numToWin) {
		super(width, height, numToWin);
		this.playerScoreBoard = makeEmptyBoard();
		this.opponentScoreBoard = makeEmptyBoard();
		
	}
	
	public ScoreBoard(ScoreBoard toCopy) {
		super(toCopy);
		this.playerScoreBoard = makeBoardCopy(toCopy.playerScoreBoard);
		this.opponentScoreBoard = makeBoardCopy(toCopy.opponentScoreBoard);
	}
	
	@Override
	protected void updateScoreBoard(MoveHolder move) {

	}


	public void initScoreBoards() {
		int[][] scoreBoard = reCalculatePlayerScoreBoard(-1);
		this.playerScoreBoard = makeBoardCopy(scoreBoard);
		this.opponentScoreBoard = makeBoardCopy(scoreBoard);
		
		
	}

	protected int[][] reCalculatePlayerScoreBoard(int player) {
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
				int hScore = scoreHorizontal(currentSpace);
				int vScore = scoreVertical(currentSpace);
				int ldScore = scoreLDiagonal(currentSpace);
				int rdScore = scoreRDiagonal(currentSpace);
				System.out.println("row " + i);
				System.out.println("col " + j);
				System.out.println("horizontal " + hScore);
				System.out.println("vertical " + vScore);
				System.out.println("ldiag " + ldScore);
				System.out.println("rdiag " + rdScore);
				int totalScore = hScore + vScore + ldScore + rdScore;
				newScoreBoard[i][j] = totalScore;

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
			// System.out.println("vert added counting up");
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
			// System.out.println("vert added counting down");
		}

		return Math.max(0, num_spaces + 1 - this.numToWin);
	}

	public int scoreRDiagonal(MoveHolder space) {
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

	public int scoreLDiagonal(MoveHolder space) {
		int num_spaces = 1; // the piece placed
		int r = space.getRow();
		int c = space.getCol();

		int stopLUp = r + this.numToWin - 1;
		int stopL = c - this.numToWin + 1;
		int stopRDown = r - this.numToWin + 1;
		int stopR = c + this.numToWin - 1;

		// Count left
		for (int i = 1; r + i <= stopLUp && c - i >= stopL; i++) {
			if (r + i == height || c - i < 0)// off the board
				break;
			if (board[r + i][c - i] == EMPTY
					|| board[r + i][c - i] == space.getPlayer())
				num_spaces++;
			else
				break;
		}

		// Count Right
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
	
	public int[][] getPlayerScoreBoard() {
		return this.playerScoreBoard;
	}

	public int[][] getOpponentScoreBoard() {
		return this.opponentScoreBoard;
	}

}
