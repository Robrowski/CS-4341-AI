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


	private int player, opponent, occupied_weight, empty_weight;
	private boolean individual;
	private String name;

	public ScoreBoardCountFeature(int player, boolean individual,
			int occupied_weight, int empty_weight,
			String name) {
		this.player = player;
		this.opponent = (player == 1) ? 2 : 1;
		this.individual = individual;
		this.occupied_weight = occupied_weight;
		this.empty_weight = empty_weight;
		this.name = name;
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

		if (individual) {
			return Integer.toString((occupied_weight * playerPieceScore)
					+ (empty_weight * playerInfluence));
		}

		return Integer
				.toString(((occupied_weight * playerPieceScore) + (empty_weight * playerInfluence))
						- ((occupied_weight * oppPieceScore) + (empty_weight * oppInfluence)));
	}

	@Override
	public String getName() {
		return name + "-P" + player + "-" + occupied_weight + "-"
				+ empty_weight;
	}

}
