package player;

import java.io.IOException;
import java.util.Random;

public class RandomPlayer extends AbstractPlayer {
	Random rng = new Random();

	public RandomPlayer(String[] args) {
		super(args);
		printlnLog("Initialized");
	}

	public static void main(String[] args) throws IOException {
		RandomPlayer rp = new RandomPlayer(args);
		rp.printlnLog("about to start");
		rp.run();

		/** Part of the sample working code */
		// System.out.println(args[0]);
		// rp.processInput();
		// rp.processInput();
		// rp.processInput();
		// rp.processInput();
		// rp.processInput();
		// rp.processInput();
		// rp.processInput();
	}

	@Override
	protected int decideNextMove() {

		int randomNum = rng.nextInt(width);

		// TODO Check that 'randomNum' is a valid move

		return randomNum;
	}

	/** This is sample code and it DOES work */
	// public void processInput() throws IOException {
	//
	// String s = input.readLine();
	// List<String> ls = Arrays.asList(s.split(" "));
	// if (ls.size() == 2) {
	// System.out.println(ls.get(0) + " " + ls.get(1));
	// } else if (ls.size() == 1) {
	// System.out.println("game over!!!");
	// } else if (ls.size() == 5) { // ls contains game info
	// System.out.println("0 1"); // first move
	// } else if (ls.size() == 4) { // player1: aa player2: bb
	//
	// System.err.println("Does this even print?");
	// // TODO combine this information with game information to decide who
	// // is the first player
	// } else
	// System.err.println("not what I want");
	// }
}
