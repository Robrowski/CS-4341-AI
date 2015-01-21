package evaluators;

import evaluators.features.Feature;
import evaluators.features.WinLikelihoodFeature;



public class EvaluatorFactory {

	public EvaluatorFactory() {
		
	}
	
	public Evaluator makeEvaluator(String[] featureStrings){
		int numFeatures = featureStrings.length;
		Feature[] desiredFeatures = new Feature[featureStrings.length];
		
		if (numFeatures == 0){
			throw new RuntimeException("No Features Given");
		}
		
		for (int i = 0; i < numFeatures; i++){
			switch (featureStrings[i]){
				case "WinLikelihood":
					desiredFeatures[i] = new WinLikelihoodFeature();
					break;
				default:
					throw new RuntimeException("Unrecognized Feature given");
			}
		}
		
		return new Evaluator(desiredFeatures);
	}

}
