package evaluators;

import common.Board;
import common.ScoreBoard;

import evaluators.features.Feature;

public class Evaluator {
	
	private Feature[] features;
	public int player;

	public Evaluator(Feature[] features, int player) {
		this.features = features;
		this.player = player;
	}

	public int estimateGameState(Board state){
		int total_score = 0;
		for(Feature feature : features){
			total_score += feature.score((ScoreBoard) state);
		}
		return total_score;
	}
	
	public Feature[] getFeatures(){
		return features;
	}
}
