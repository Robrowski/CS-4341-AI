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

import evaluators.features.Connect2Feature;
import evaluators.features.Feature;
import evaluators.features.OneTurnWinFeature;
import evaluators.features.PieceCountFeature;
import evaluators.features.ScoreBoardCountFeature;

public class FeatureFactory {

	public static List<Feature> makeFeatures(String[] feature_list,
			String[] args) {
		List<String> all_args = new LinkedList<String>();
		all_args.addAll(Arrays.asList(feature_list));
		all_args.addAll(Arrays.asList(args));

		List<Feature> features = new LinkedList<Feature>();
		// Regular ScoreBoard evaluator

		// player
		if (all_args.contains("score-board-occupied")) {
			features.add(new ScoreBoardCountFeature(1, false, 1, 0,
					"score-board-occupied")); // Original
		}
		if (all_args.contains("influence-score-board")) {
			features.add(new ScoreBoardCountFeature(1, false, 100, 30,
					"influence-score-board")); // Original
			features.add(new ScoreBoardCountFeature(1, false, 1, 1,
					"equal-weight-score-board"));
			features.add(new ScoreBoardCountFeature(1, false, 0, 1,
					"score-board-empty"));
			// To try more weights, give a new weight
			// features.add(new ScoreBoardCountFeature(1, false, OCC_WEIGHT,
			// EMPTY_WEIGHT, "influence-score-board"));

		}

		
		

		if (all_args.contains("individual-empty")) {
			features.add(new ScoreBoardCountFeature(1, true, 0, 1,
					"individual-empty"));
			features.add(new ScoreBoardCountFeature(2, true, 0, 1,
					"individual-empty"));
		}

		if (all_args.contains("individual-occupied")) {
			features.add(new ScoreBoardCountFeature(1, true, 1, 0,
					"individual-occupied"));
			features.add(new ScoreBoardCountFeature(2, true, 1, 0,
					"individual-occupied"));
		}

		if (all_args.contains("PieceCount")) {
			features.add(new PieceCountFeature(1));
			features.add(new PieceCountFeature(2));
		}

		if (all_args.contains("OneTurnWin")) {
			features.add(new OneTurnWinFeature(1));
			features.add(new OneTurnWinFeature(2));
		}

		if (all_args.contains("Connect2")) {
			features.add(new Connect2Feature(1));
			features.add(new Connect2Feature(2));
		}

		// TODO MORE FEATURES

		return features;
	}

}
