package common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

	public final static int FAILURE_TO_PLACE = -2;
	public final static int SUCCESS = 0;
	public final static int WIN = 1;
	public final static int LOSS = -1;
	final static int EMPTY = 9;

	protected int[][] board;
	public int height;
	public int width;
	int numToWin;

	private boolean p1_used_pop = false;
	private boolean p2_used_pop = false;

	private FileLogger logger = FileLogger.getInstance();

	/**
	 * Board class constructor. Initializes a 2D array board with values of
	 * EMPTY, as to match the behavior the referee uses when printing.
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
		this.board = makeEmptyBoard();
		

//		for (int[] row : board) {
//			Arrays.fill(row, EMPTY);
//		}
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
		this.board = makeBoardCopy(toCopy.board);
		this.p1_used_pop = toCopy.p1_used_pop;
		this.p2_used_pop = toCopy.p2_used_pop;

//		for (int i = 0; i < height; i++)
//			board[i] = Arrays.copyOf(toCopy.board[i], width);

	}
	
	protected int[][] makeBoardCopy(int[][] toCopy) {
		int[][] newBoard = new int[height][width];
		for (int i = 0; i < height; i++)
			newBoard[i] = Arrays.copyOf(toCopy[i], width);
	
		return newBoard;
	}
	
	protected int[][] makeEmptyBoard() {
		int[][] empty = new int[this.height][this.width];
		
		for (int[] row : empty) {
			Arrays.fill(row, EMPTY);
		}
		
		return empty;
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
	private boolean addPiece(MoveHolder move) {
		int rowToPlace = countPiecesInCol(move.getCol());
		if (rowToPlace != height) {
			board[rowToPlace][move.getCol()] = move.getPlayer();
			move.setRow(rowToPlace);
			return true;
		}
		return false;
	}

	/**
	 * Applies a move stored in a MoveHolder
	 * 
	 * @param move
	 * @return0 implies success, 1 implies victory for player, -1 implies
	 *          victory for opponent, -2 implies failure to place
	 */
	public int applyMove(MoveHolder move) {
		return applyMove(move, move.getPlayer());
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
		move.setPlayer(player);
		switch (move.getMove()) {
		case DROP:
			// Try to add the piece
			if (!addPiece(move))
				return FAILURE_TO_PLACE;

			// Check for victories
			if (detect_win(move) == 1)
				return WIN;
			break;
		case POP:
			// Try to pop the piece and adjust the column
			if (!popPiece(move.getCol(), player))
				return FAILURE_TO_PLACE;

			// Check each piece for a victory
			boolean isWin = false;
			for (int r = 0; r < height && board[r][move.getCol()] != EMPTY; r++) {
				MoveHolder fake_move = new MoveHolder(move.getCol());
				fake_move.setPlayer(getPiece(r, move.getCol()));
				fake_move.setRow(r);


				int result = detect_win(fake_move);
				if (result == WIN && player != fake_move.getPlayer())
					return LOSS; // Always report a loss when the opponent wins.
									// We hate ties
				else if (result == WIN)
					isWin = true; // Don't exit early because we could lose
			}
			if (isWin)
				return WIN;
			break;
		}

		// Update scores because a piece was successfully placed or popped
		updateScoreBoard(move);

		return SUCCESS;
	}
	

	/**
	 * Given a move, update any internal scores that may be of interest
	 * 
	 * @param move
	 *            with row, column, owner and POP/DROP information
	 */
	protected void updateScoreBoard(MoveHolder move) {
		// TODO Override this in your extensions

	}

	/**
	 * Detects a win or loss from a given move (assumed to be a DROP)
	 * 
	 * @param move
	 * @return
	 */
	private int detect_win(MoveHolder move) {
		// count pieces in each direction
		int vert = countVertical(move);
		int hori = countHorizontal(move);
		int ldiag = countLDiagonal(move);
		int rdiag = countRDiagonal(move);

		// TODO could evaluate HOW MANY wins here

		return ((vert >= numToWin) || (hori >= numToWin)
				|| (ldiag >= numToWin) || (rdiag >= numToWin)) ? WIN : SUCCESS;
	}

	/**
	 * counts how many pieces there are in a row right diagonally from a given
	 * move
	 * 
	 * Right diagonal means sloping up
	 * 
	 * @param move
	 * @return
	 */
	private int countRDiagonal(MoveHolder move) {
		int num_pieces = 1; // the piece placed
		int r = move.getRow();
		int c = move.getCol();

		// Count left
		for (int i = -1; ; i--) {
			if (r + i < 0 || c + i < 0)// off the board
				break;
			if (board[r + i][c + i] == move.getPlayer())
				num_pieces++;
			else
				break;
		}

		// Count Right
		for (int i = 1; ; i++) {
			if (r + i == height || c + i == width)// off the board
				break;
			if (board[r + i][c + i] == move.getPlayer())
				num_pieces++;
			else
				break;
		}

		return num_pieces;
	}

	/**
	 * counts how many pieces there are in a row left diagonally from a given
	 * move
	 * 
	 * Left diagonal means sloping down
	 * 
	 * @param move
	 * @return
	 */
	private int countLDiagonal(MoveHolder move) {
		int num_pieces = 1; // the piece placed
		int r = move.getRow();
		int c = move.getCol();

		// Count left
		for (int i = 1;; i++) {
			if (r + i == height || c - i < 0)// off the board
				break;
			if (board[r + i][c - i] == move.getPlayer())
				num_pieces++;
			else
				break;
		}

		// Count Right
		for (int i = 1;; i++) {
			if (r - i < 0 || c + i == width)// off the board
				break;
			if (board[r - i][c + i] == move.getPlayer())
				num_pieces++;
			else
				break;
		}

		return num_pieces;
	}

	/**
	 * counts how many pieces there are in a row horizontally from a given move
	 * 
	 * @param move
	 * @return
	 */
	private int countHorizontal(MoveHolder move) {
		int num_pieces = 1; // the piece placed

		// Count left
		for (int c = move.getCol() - 1; c >= 0; c--) {
			if (board[move.getRow()][c] == move.getPlayer())
				num_pieces++;
			else
				break;
		}

		// Count right
		for (int c = move.getCol() + 1; c < width; c++) {
			if (board[move.getRow()][c] == move.getPlayer())
				num_pieces++;
			else
				break;
		}

		return num_pieces;
	}

	/**
	 * counts how many pieces there are in a row vertically from a given move
	 * 
	 * @param move
	 * @return
	 */
	private int countVertical(MoveHolder move) {
		int num_pieces = 1; // the piece placed

		for (int r = move.getRow() - 1; r >= 0; r--) {
			if (board[r][move.getCol()] == move.getPlayer())
				num_pieces++;
			else
				break;
		}

		return num_pieces;
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
		board[height - 1][col] = EMPTY; // top is empty

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
			if (board[row][col] == EMPTY) {
				return row;
			}
		}
		return height;
	}

	/** For each width, these are the columns ordered from most to least important */
	protected int[][] move_order = new int[][] {
			  { 0 },
			  { 1, 0 },
			  { 1, 2, 0 },
			  { 2, 1, 3, 0 },
			  { 2, 3, 1, 0, 4 },
			  { 3, 2, 4, 1, 5, 0},
			  { 3, 4, 2, 5, 1, 6, 0 },
			  { 4, 3, 5, 2, 6, 1, 7, 0 },
			  { 4, 5, 3, 6, 2, 7, 1, 8, 0 },
		      { 5, 4, 6, 3, 7, 2, 8, 1, 9, 0 }
			};
	
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
		for (int col : move_order[width - 1]) {
			if (board[top_row][col] == EMPTY) {
				moves.add(new MoveHolder(col).setPlayer(player));
			}
		}

		// Possible pop moves
		if (((player == 1) && !p1_used_pop) || ((player == 2) && !p2_used_pop)) {
			for (int col : move_order[width - 1]) {
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
	 * Gets the piece that is at the top of a column. Returns EMPTY if no piece
	 * is in that column
	 * 
	 * @param col
	 * @return
	 */
	public int getTopPiece(int col) {
		for (int r = height - 1; r >= 0; r--) {
			if (board[r][col] != EMPTY)
				return board[r][col];
		}
		return EMPTY;
	}

}
