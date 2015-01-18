package common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

	private int[][] board;
	private int height;
	private int width;
	
	private FileLogger logger = FileLogger.getInstance();
	
	/**
	 * Board class constructor. Initializes a 2D array board with
	 * values of 9, as to match the behavior the referee uses
	 * when printing.
	 * 
	 * @param width The width of the board.
	 * @param height The height of the board.
	 */
	public Board(int width, int height){
		this.height = height;
		this.width = width;
		this.board = new int[height][width];
		
		for (int[] row : board){
			Arrays.fill(row, 9);
		}
	}
	
	/**
	 * Board class constructor for making a deep copy of another board
	 * @param toCopy
	 */
	public Board(Board toCopy){
		this.height = toCopy.height;
		this.width = toCopy.width;
		this.board = new int[height][width];
		
		for (int i = 0; i < height; i++)
		     board[i] = Arrays.copyOf(toCopy.board[i], width);
		
	}
	
	/**
	 * Adds a player piece to the board in the given column.
	 * 
	 * @param col  The column to place a piece in
	 * @param player  The player placing the piece
	 * @return boolean representing success to place the piece
	 */
	public boolean addPiece(int col, int player){
		int rowToPlace = height - countPiecesInCol(col);
		if (rowToPlace != 0){
			board[rowToPlace-1][col] = player;
			return true;
		}
		else return false;
	}
	
	/**
	 * Method for returning the number of pieces currently filled
	 * in a column of the board.
	 * 
	 * @param col The column to count
	 * @return int The number of pieces in a column
	 */
	public int countPiecesInCol(int col){
		int count = 0;
		for (int[] row : board){
			if (row[col] != 9) { count ++; }
		}
		return count;
	}
	
	/**
	 * Method for producing all the columns that 
	 * are valid possible moves at the current board state.
	 * 
	 * @return a List of the possible moves
	 */
	public  List<Integer> getPossibleMoves(){
		List<Integer> moves = new ArrayList<Integer>();
		for (int col = 0; col < width; col++){
			int colPieces = countPiecesInCol(col);
			logger.println("pieces in " + col + ": " + colPieces);
			if (colPieces != height) { moves.add(col); }
		}
		
		return moves;
	}
	
	/**
	 *  Getter Method for retrieving raw current board state data
	 *  
	 * @return int[][] representing the board
	 */
	public int[][] getBoard(){
		return board;
	}
	
	
}
