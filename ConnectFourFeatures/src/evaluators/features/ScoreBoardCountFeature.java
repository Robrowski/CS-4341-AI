/**
 * Matt Costi - mscosti  
 * Rob Dabrowski - rpdabrowski
 * 
 * CS 4341 C 2015
 * Prof. Niel Heffernan
 * WPI 
 */
package evaluators.features;

import common.board.ScoreBoard;

public class ScoreBoardCountFeature implements Feature {

	private int player, opponent;
	private boolean influence, individual;

	public ScoreBoardCountFeature(int player, boolean enableInfluence,
			boolean individual) {
		this.player = player;
		this.opponent = (player == 1) ? 2 : 1;
		this.influence = enableInfluence;
		this.individual = individual;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String score(ScoreBoard state) {
		int[][] b = state.getBoard();
		int[][] playerBoard = state.getPlayerScoreBoard(player);
		int[][] oppBoard = state.getPlayerScoreBoard(opponent);

		int playerPieceScore = 0;
		int oppPieceScore = 0;

		int playerInfluence = 0;
		int oppInfluence = 0;

		for (int i = 0; i < state.height; i++) {
			for (int j = 0; j < state.width; j++) {
				if (b[i][j] == player) {
					playerPieceScore += playerBoard[i][j];
				} else if (b[i][j] != 9) {
					oppPieceScore += oppBoard[i][j];
				} else if (b[i][j] == 9) {
					playerInfluence += playerBoard[i][j];
					oppInfluence += oppBoard[i][j];
				}
			}
		}

		int estimate = 0;
		if (influence) {
			estimate = ((100 * playerPieceScore) + (30 * playerInfluence))
					- ((100 * oppPieceScore) + (30 * oppInfluence));
		} else if (individual) {
			estimate = playerPieceScore;
		} else {
			estimate = playerPieceScore - oppPieceScore;
		}
		return Integer.toString(estimate);
	}

	@Override
	public String getName() {
		if (influence) {
			return "influence-score-board";
		} else if (individual) {
			return "individual-score-board-P" +player;
		} else {
			return "score-board";
		}
	}

}
