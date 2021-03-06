/**
 * Matt Costi - mscosti  
 * Rob Dabrowski - rpdabrowski
 * 
 * CS 4341 C 2015
 * Prof. Niel Heffernan
 * WPI 
 */
package evaluators.features;

import common.board.ScoreBoard;

public interface Feature {

	public String score(ScoreBoard state);

	public String getName();
}
