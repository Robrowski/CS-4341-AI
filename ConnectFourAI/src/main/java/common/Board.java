package common;

import java.util.Arrays;

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
	 * in a column of the board
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
	
	public int[][] getBoard(){
		return board;
	}
}
