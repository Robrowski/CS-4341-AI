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

		// TODO - ScoreBoardCountFeature can return return a dif column for each
		// player
		if (all_args.contains("--score-board-feature")) {
			features.add(new ScoreBoardCountFeature(1, false));
			// features.add(new ScoreBoardCountFeature(2, false)); // Redundant
		}
		if (all_args.contains("--influence-score-board-feature")) {
			features.add(new ScoreBoardCountFeature(1, true));
			// features.add(new ScoreBoardCountFeature(2, true)); // Redundant
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
