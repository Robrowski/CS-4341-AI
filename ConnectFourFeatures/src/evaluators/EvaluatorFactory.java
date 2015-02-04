/**
 * Matt Costi - mscosti  
 * Rob Dabrowski - rpdabrowski
 * 
 * CS 4341 C 2015
 * Prof. Niel Heffernan
 * WPI 
 */
package evaluators;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import evaluators.features.Feature;
import evaluators.features.ScoreBoardCountFeature;



public class EvaluatorFactory {


	

	public static Evaluator makeEvaluator(List<String> argsList, int player) {
		List<Feature> features = new LinkedList<Feature>();
		
		// Regular ScoreBoard evaluator
		if (argsList.contains("--score-board-feature")) {
			features.add(new ScoreBoardCountFeature(player, false));
		} else if (argsList.contains("--influence-score-board-feature")) {
			features.add(new ScoreBoardCountFeature(player, true));
		}
		
		// Add more arguments to add more features or use customized versions of
		// existing features features

		if (features.size() == 0) {
			throw new RuntimeException("No Features Given");
		}

		return new Evaluator(features, player);
	}

	public Evaluator makeEvaluator(String[] scoreBoardCount, int player) {
		return makeEvaluator(Arrays.asList(scoreBoardCount), player);
	}

}
