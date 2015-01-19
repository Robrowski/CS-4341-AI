package player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import common.Board;
import common.FileLogger;
import common.timing.CountDownTimer;

/**
 * The AbstractPlayer is intended to be the AI player framework for the game of
 * Connect-N. This framework handles all move logic, basic logging,
 * communication with the referee, basic time keeping and makes is so a new
 * Player just needs to decide moves.
 */
abstract class AbstractPlayer {
	/** The name of the current player */
	protected String playerName;

	/** Game configuration details */
	protected int width, height, numToWin, playerNumber, timeLimit,
			opponentNum, firstTurn;

	/** State of the game */
	private boolean game_over = false;

	/** A buffer read to read standard I/O for communication with the referee */
	private BufferedReader input = new BufferedReader(new InputStreamReader(
			System.in));

	/** A logger for recording debug and game state information */
	protected FileLogger logger;

	/** The count down timer used to keep time during moves */
	CountDownTimer ctd;

	/** A game board to keep track of moves played */
	protected Board gameBoard;

	/** A copy of the arguments input into the program */
	private List<String> argsList;

	/**
	 * Constructor and argument parser for core Player settings
	 * 
	 * @param args
	 */
	public AbstractPlayer(String[] args) {
		// Parse the arguments
		List<String> argsList = Arrays.asList(args);

		// First argument is expected to be a player name
		if (args.length >= 1) {
			playerName = args[0];
		} else {
			playerName = "DefaultPlayerName";
		}
		logger = FileLogger.getInstance();
		logger.init(playerName, argsList);
		logger.println("AbstractPlayer initialized. ");
	}

	/**
	 * Runs the AI through a game of Connect-N
	 */
	protected void run() {
		try {
			// Step 1. Send the player name to the Referee
			System.out.println(playerName);
			System.out.flush();

			// Step 2. Wait for the game configuration
			waitForGameConfiguration();
			ctd = CountDownTimer.getInstance();
			ctd.init(playerName, timeLimit, argsList);

			// Step 3. Initialize game board, game state, etc.
			gameBoard = new Board(width, height);

			// Step 4. If going second, wait for opponent to make a move
			if (playerNumber != firstTurn) {
				waitForOpponentsMove();
			}

			// Step 5. Start main loop (Make a move, wait for opponent's move)
			while (!game_over) {
				ctd.start();
				sendMove(decideNextMove());
				ctd.stop();
				logger.println("Time taken making a move: "
						+ CountDownTimer.elapsed_milli + " milliseconds");
				waitForOpponentsMove();
			}
		} catch (Exception e) {
			logger.logException(e);
		}
		logger.close();
		ctd.close();
	}

	/**
	 * Log the move for the given player to our internal board representation
	 * 
	 * @param column
	 *            the column number of the move
	 * @param playerNumber
	 *            the player number/ID
	 */
	private void recordMove(int column, int playerNumber) {
		gameBoard.addPiece(column, playerNumber);

		logger.println("\nMove made: " + column + " " + playerNumber);
		logger.println("column " + column + " has "
				+ gameBoard.countPiecesInCol(column) + " pieces");
		logger.printBoard(gameBoard);
	}

	/**
	 * Waits for the game configuration to be sent.
	 * 
	 * @throws IOException
	 */
	private void waitForGameConfiguration() throws IOException {
		// Read the statement of player numbers
		String[] player_assignment = listenToReferee();
		logger.println("Player assignment received.");
		if (player_assignment.length < 4) { // names can have spaces....
			logger.println("!!!!! Invalid player assignment given.");
			return;
		}

		// The name of player 1 will be the second string - as documented
		playerNumber = (player_assignment[1].equals(playerName)) ? 1 : 2;
		opponentNum = (playerNumber == 1) ? 2 : 1;
		logger.println("Player number: " + playerNumber);

		// Read the game configuration
		String[] gameConfig = listenToReferee();
		logger.println("Game configuration received.");
		if (gameConfig.length != 5) {
			logger.println("!!!!! Invalid configuration given.");
			return;
		}

		// Read the components of the configuration as documented
		height = Integer.parseInt(gameConfig[0]);
		width = Integer.parseInt(gameConfig[1]);
		numToWin = Integer.parseInt(gameConfig[2]);
		firstTurn = Integer.parseInt(gameConfig[3]);
		timeLimit = Integer.parseInt(gameConfig[4]);
	}

	/**
	 * Sends a move to the referee
	 * 
	 * TODO Support the "popping out" move
	 * 
	 * @param column
	 *            the column number to play in
	 */
	private void sendMove(int column) {
		System.out.println(Integer.toString(column) + " 1");
		System.out.flush();
		recordMove(column, playerNumber);
	}

	/**
	 * Waits for the opponents move then updates the board states
	 * 
	 * @throws IOException
	 */
	private void waitForOpponentsMove() throws IOException {
		String[] opponents_move = listenToReferee();

		// If there is only one string received, the game has ended
		if (opponents_move.length == 1) {
			// TODO translate game-over codes
			logger.println("Game over!");
			game_over = true;
			return;
		}
		recordMove(Integer.parseInt(opponents_move[0]), opponentNum);
	}

	/**
	 * Wait for the referee to say something, then return it
	 * 
	 * @return the message sent by the referee split by spaces
	 * @throws IOException
	 */
	private String[] listenToReferee() throws IOException {
		return input.readLine().split(" ");
	}

	/**
	 * Decide the next move that the player should make.
	 * 
	 * @return the column number to place a piece
	 */
	abstract protected int decideNextMove();
}
