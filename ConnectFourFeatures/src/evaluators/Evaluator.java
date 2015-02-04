/**
 * Matt Costi - mscosti  
 * Rob Dabrowski - rpdabrowski
 * 
 * CS 4341 C 2015
 * Prof. Niel Heffernan
 * WPI 
 */
package evaluators;

import java.util.List;

import evaluators.features.Feature;

public class Evaluator {

	private List<Feature> features;
	public int player;

	public Evaluator(List<Feature> features, int player) {
		this.features = features;
		this.player = player;
	}

	public List<Feature> getFeatures() {
		return features;
	}
}
