package common;

public class MoveHolder {
	public int player = 0;
	public int col = 0;
	public int val = 0;

	public MoveHolder(){
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
	
	

}
