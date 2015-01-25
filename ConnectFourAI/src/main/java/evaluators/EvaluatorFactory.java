package evaluators;

import common.FileLogger;

import evaluators.features.Feature;
import evaluators.features.ScoreBoardCountFeature;



public class EvaluatorFactory {

	/** A logger for recording debug and game state information */
	protected FileLogger logger;

	public EvaluatorFactory() {
		
	}
	
	public static Evaluator makeEvaluator(String[] featureStrings, int player) {
		int numFeatures = featureStrings.length;
		Feature[] desiredFeatures = new Feature[featureStrings.length];
		
		// logger = FileLogger.getInstance();
		// logger.println("num Features" + numFeatures);
		if (numFeatures == 0){
			throw new RuntimeException("No Features Given");
		}

		
		for (int i = 0; i < numFeatures; i++){
			switch (featureStrings[i]){
			case "scoreBoardCount":
				desiredFeatures[i] = new ScoreBoardCountFeature(player);
					break;
				default:
					throw new RuntimeException("Unrecognized Feature given");
			}
		}
		
		return new Evaluator(desiredFeatures, player);
	}

}
