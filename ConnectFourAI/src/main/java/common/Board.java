package common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

	final static int FAILURE_TO_PLACE = -2;
	final static int SUCCESS = 0;
	final static int WIN = 1;
	final static int LOSS = -1;

	private int[][] board;
	int height;
	int width;
	int numToWin;

	private boolean p1_used_pop = false;
	private boolean p2_used_pop = false;

	private FileLogger logger = FileLogger.getInstance();

	/**
	 * Board class constructor. Initializes a 2D array board with values of 9,
	 * as to match the behavior the referee uses when printing.
	 * 
	 * @param width
	 *            The width of the board.
	 * @param height
	 *            The height of the board.
	 */
	public Board(int width, int height, int numToWin) {
		this.height = height;
		this.width = width;
		this.numToWin = numToWin;
		this.board = new int[height][width];

		for (int[] row : board) {
			Arrays.fill(row, 9);
		}
	}

	/**
	 * Board class constructor for making a deep copy of another board
	 * 
	 * @param toCopy
	 */
	public Board(Board toCopy) {
		this.height = toCopy.height;
		this.width = toCopy.width;
		this.numToWin = toCopy.numToWin;
		this.board = new int[height][width];
		this.p1_used_pop = toCopy.p1_used_pop;
		this.p2_used_pop = toCopy.p2_used_pop;

		for (int i = 0; i < height; i++)
			board[i] = Arrays.copyOf(toCopy.board[i], width);

	}

	/**
	 * Adds a player piece to the board in the given column.
	 * 
	 * @param col
	 *            The column to place a piece in
	 * @param player
	 *            The player placing the piece
	 * @return boolean representing success to place the piece
	 */
	private boolean addPiece(int col, int player) {
		int rowToPlace = countPiecesInCol(col);
		if (rowToPlace != height) {
			board[rowToPlace][col] = player;
			return true;
		}
		return false;
	}

	/**
	 * Applies a move stored in a MoveHolder
	 * 
	 * @param move
	 * @param player
	 * @return 0 implies success, 1 implies victory for player, -1 implies
	 *         victory for opponent, -2 implies failure to place
	 */
	public int applyMove(MoveHolder move, int player) {
		boolean successful = true;
		int game_state = 0;
		switch (move.getMove()) {
		case DROP:
			successful = addPiece(move.getCol(), player);
			// Detect victory+
			// set the game state accordingly
			break;
		case POP:

			successful = popPiece(move.getCol(), player);
			// Detect end game

			// set the game_state accordingly
			break;
		}
		if (!successful)
			return FAILURE_TO_PLACE;
		return game_state;
	}

	/**
	 * Pops a piece out of the bottom of the board and drops down all pieces on
	 * top of it.
	 * 
	 * @param col
	 * @param player
	 * 
	 * @return returns true if the pop was successful
	 */
	private boolean popPiece(int col, int player) {
		// Check that the player hasn't used their pop & CAN pop
		if (board[0][col] != player)
			return false;
		if (player == 1 && p1_used_pop)
			return false;
		if (player == 2 && p2_used_pop)
			return false;

		// Move the pieces in the column down
		for (int r = 0; r < height - 1; r++) {
			board[r][col] = board[r + 1][col];
		}
		board[height - 1][col] = 9; // top is empty

		// Record the usage
		if (player == 1) {
			p1_used_pop = true;
		} else {
			p2_used_pop = true;
		}

		return true;
	}

	/**
	 * Returns the player number of the piece at the given location
	 * 
	 * @param row
	 *            the row
	 * @param col
	 *            the column
	 */
	public int getPiece(int row, int col) {
		return board[row][col];
	}

	/**
	 * Method for returning the number of pieces currently filled in a column of
	 * the board.
	 * 
	 * @param col
	 *            The column to count
	 * @return int The number of pieces in a column
	 */
	public int countPiecesInCol(int col) {
		// Optimized assuming most columns will be almost empty
		for (int row = 0; row < height; row++) {
			if (board[row][col] == 9) {
				return row;
			}
		}
		return height;
	}

	/**
	 * Method for producing all the columns that are valid possible moves at the
	 * current board state.
	 * 
	 * @return a List of the possible moves
	 */
	public List<MoveHolder> getPossibleMoves(int player) {
		List<MoveHolder> moves = new ArrayList<MoveHolder>();

		// Possible drop moves
		int top_row = height - 1;
		for (int col = 0; col < width; col++) {
			if (board[top_row][col] == 9) {
				moves.add(new MoveHolder(col));
			}
		}

		// Possible pop moves
		if (((player == 1) && !p1_used_pop) || ((player == 2) && !p2_used_pop)) {
			// if (player == 2) {
			for (int col = 0; col < width; col++) {
				if (board[0][col] == player) {
					moves.add(new MoveHolder(col).setMove(Move.POP));
				}
			}
		}

		return moves;
	}

	/**
	 * Getter Method for retrieving raw current board state data
	 * 
	 * @return int[][] representing the board
	 */
	public int[][] getBoard() {
		return board;
	}

	/**
	 * Gets the piece that is at the top of a column. Returns 9 if no piece is
	 * in that column
	 * 
	 * @param col
	 * @return
	 */
	public int getTopPiece(int col) {
		for (int r = height - 1; r >= 0; r--) {
			if (board[r][col] != 9)
				return board[r][col];
		}
		return 9;
	}

}
