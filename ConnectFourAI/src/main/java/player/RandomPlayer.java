package player;

import java.io.IOException;
import java.util.Random;

/**
 * This Connect-N AI picks a random column for each of its moves.
 * 
 * @author Robrowski
 *
 */
public class RandomPlayer extends AbstractPlayer {
	/** Random number generator */
	Random rng = new Random();

	/**
	 * Basic constructor to initialize a random player
	 * 
	 * @param args
	 */
	public RandomPlayer(String[] args) {
		super(args);
	}

	/**
	 * Main for this player
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) {
		RandomPlayer rp = new RandomPlayer(args);
		rp.run();
	}

	@Override
	protected int decideNextMove() {
		// Fancy wait statement
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int randomNum = rng.nextInt(width);

		// TODO Check that 'randomNum' is a valid move

		return randomNum;
	}
}
