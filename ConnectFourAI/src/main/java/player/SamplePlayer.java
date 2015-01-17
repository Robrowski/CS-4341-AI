package player;

public class SamplePlayer extends AbstractPlayer {
	public SamplePlayer(String[] args) {
		super(args);
	}

	private static int myTurn;

	public static void main(String args[]) throws Exception {
		// System.err.println("Poooping ");
		// int width, height, numToWin, playerNumber, timeLimit, move;
		//
		// // send player name
		// System.out.println("sample-player");
		// System.out.flush();
		//
		// // read game config
		// String[] gameConfig = input.readLine().split(" ");
		// height = Integer.parseInt(gameConfig[0]);
		// width = Integer.parseInt(gameConfig[1]);
		// numToWin = Integer.parseInt(gameConfig[2]);
		// playerNumber = Integer.parseInt(gameConfig[3]);
		// timeLimit = Integer.parseInt(gameConfig[4]);
		//
		// int currentTurn = 0; // first player starts
		// while (true) {
		// if (currentTurn == myTurn) {
		// // TODO: use a mechanism for timeout(threads, java.util.Timer,
		// // ..)
		//
		// // call alpha-beta algorithm to get the move
		// move = getMove();
		//
		// // send move
		// System.out.println(String.valueOf(move));
		// System.out.flush();
		// } else {
		// // read move
		// move = Integer.parseInt(input.readLine());
		//
		// // check for end
		// if (move < 0)
		// break;
		// }
		//
		// // switch turns
		// currentTurn = 1 - currentTurn;
		// }
	}

	private static int getMove() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int decideNextMove() {
		// TODO Auto-generated method stub
		return 0;
	}
}
