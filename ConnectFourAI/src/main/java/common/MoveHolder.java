package common;

public class MoveHolder {
	private int player = 0;
	private int col = 0;
	private int val = 0;
	private Move m = Move.DROP;

	public MoveHolder(){
	}
	
	public MoveHolder(int col) {
		this.col = col;
	}

	public MoveHolder(int col, int estimate) {
		this.val = estimate;
		this.col = col;
	}

	/**
	 * @return the player
	 */
	public int getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(int player) {
		this.player = player;
	}

	/**
	 * @return the moveCol
	 */
	public int getCol() {
		return col;
	}

	/**
	 * @param moveCol the moveCol to set
	 */
	public void setCol(int moveCol) {
		this.col = moveCol;
	}

	/**
	 * @return the moveValue
	 */
	public int getValue() {
		return val;
	}

	/**
	 * @param moveValue the moveValue to set
	 */
	public void setValue(int moveValue) {
		this.val = moveValue;
	}

	public Move getMove() {
		return m;
	}

	/**
	 * Returns 'this' for ease of use
	 * 
	 * @param m
	 * @return
	 */
	public MoveHolder setMove(Move m) {
		this.m = m;
		return this;
	}
	
	

}
