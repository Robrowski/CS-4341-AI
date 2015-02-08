package evaluators.features;

import common.MoveHolder;
import common.board.Board;
import common.board.ScoreBoard;

public class Connect2Feature implements Feature {

	private int player;
	private MoveHolder m;

	public Connect2Feature(int player) {
		this.player = player;
		m = new MoveHolder().setPlayer(player);
	}

	@Override
	public String score(ScoreBoard state) {
		// Change the numToWin to 3 and For each empty space on the board,
		// detect if the given player could win on their next move if
		// placed there, and keep track of the total connect 2s.
		state.setNumToWin(3);
		int connect2s = 0;
		for (int x = 0; x < state.width; x++) {
			m.setCol(x);
			for (int y = 0; y < state.height; y++) {
				m.setCol(y);
				if (state.detect_win(m) == Board.WIN)
					connect2s++;
			}
		}
		return Integer.toString(connect2s);
	}

	@Override
	public String getName() {
		return "numConnect2-P" + player;
	}

}
