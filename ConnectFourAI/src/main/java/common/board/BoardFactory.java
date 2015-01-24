/**
 * 
 */
package common.board;

import java.util.List;

/**
 * Factory implementation for making Boards
 */
public class BoardFactory {

	/** Private constructor */
	private BoardFactory() {
	}

	/**
	 * Make a board based on parameters in the argsList
	 * 
	 * @param width
	 * @param height
	 * @param numToWin
	 * @param argsList
	 * @return
	 */
	public static Board makeBoard(int width, int height, int numToWin,
			List<String> argsList) {

		if (argsList.contains("--score-board"))
			return new ScoreBoard(width, height, numToWin);

		return new Board(width, height, numToWin);
	}
}
