package evaluators;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import common.FileLogger;

import evaluators.features.Feature;
import evaluators.features.ScoreBoardCountFeature;



public class EvaluatorFactory {

	/** A logger for recording debug and game state information */
	protected FileLogger logger;

	public EvaluatorFactory() {
		
	}
	

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
