package evaluators.features;

import common.ScoreBoard;

public class ScoreBoardCountFeature implements Feature {

	private int player, opponent;

	public ScoreBoardCountFeature(int player) {
		this.player = player;
		this.opponent = (player == 1) ? 2 : 1;
		// TODO Auto-generated constructor stub
	}
	
	

	@Override
	public int score(ScoreBoard state) {
		int[][] b = state.getBoard();
		int[][] playerBoard = state.getPlayerScoreBoard(player);
		int[][] oppBoard = state.getPlayerScoreBoard(opponent);

		int playerScore = 0;
		int oppScore = 0;

		for (int i = 0; i < state.height; i++) {
			for (int j = 0; j < state.width; j++) {
				if (b[i][j] == player) {
					System.out.println(playerBoard[i][j]);
					playerScore += playerBoard[i][j];
				}
				else if (b[i][j] != 9) {
					oppScore += oppBoard[i][j];
				}
			}
		}
		return playerScore - oppScore;
	}

}
