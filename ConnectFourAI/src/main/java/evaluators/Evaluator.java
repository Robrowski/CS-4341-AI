package evaluators;

import java.util.List;

import common.board.Board;
import common.board.ScoreBoard;

import evaluators.features.Feature;

public class Evaluator {
	
	private List<Feature> features;
	public int player;

	public Evaluator(List<Feature> features, int player) {
		this.features = features;
		this.player = player;
	}

	public int estimateGameState(Board state) {
		int total_score = 0;
		for(Feature feature : features){
			total_score += feature.score((ScoreBoard) state);
		}
		return total_score;
	}
	
	public List<Feature> getFeatures() {
		return features;
	}
}
