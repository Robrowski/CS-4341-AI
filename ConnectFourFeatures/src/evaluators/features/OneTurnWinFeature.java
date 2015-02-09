package evaluators.features;

import common.MoveHolder;
import common.board.Board;
import common.board.ScoreBoard;

/** IF a player can win in one more turn, return true */
public class OneTurnWinFeature implements Feature {

	private int player;
	private MoveHolder m;

	public OneTurnWinFeature(int player) {
		this.player = player;
		m = new MoveHolder().setPlayer(player);
	}

	@Override
	public String score(ScoreBoard state) {
		// For each empty space on the board, detect if the given player could
		// win on their next move if placed there
		for (int x = 0; x < state.width; x++) {
			m.setCol(x);
			for (int y = 0; y < state.height; y++) {
				if (state.getPiece(y, x) != Board.EMPTY) {
					continue;
				}

				m.setRow(y);

				if (state.detect_win(m) == Board.WIN)
					return "1";
			}
		}

		return "0";
	}

	@Override
	public String getName() {
		return "OneTurnWin-P" + player;
	}

}
