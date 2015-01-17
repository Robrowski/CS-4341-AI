package player;

import java.io.IOException;
import java.util.Random;

public class RandomPlayer extends AbstractPlayer {
	Random rng = new Random();

	public RandomPlayer(String[] args) {
		super(args);
	}

	public static void main(String[] args) throws IOException {
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
