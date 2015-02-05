package evaluators.features;

import common.board.ScoreBoard;

public class PieceCountFeature implements Feature {

	private int player;

	public PieceCountFeature(int player) {
		this.player = player;
	}

	@Override
	public String score(ScoreBoard state) {
		return (player == 1) ? Integer.toString(state.p1_pieces) : Integer
				.toString(state.p2_pieces);
	}

	@Override
	public String getName() {
		return "PieceCount-P" + player;
	}

}
