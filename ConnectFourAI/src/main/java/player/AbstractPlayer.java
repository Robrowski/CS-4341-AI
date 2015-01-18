package player;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import common.Board;
import common.FileLogger;

// TODO add some timing construct so we can just check how much time we have left at any point during a turn

abstract class AbstractPlayer {
	protected String playerName = "GiveANameAsAnArgument";
	protected int width, height, numToWin, playerNumber, timeLimit,
			opponentNum, playerTurn;
	private boolean game_over = false;

	private BufferedReader input = new BufferedReader(new InputStreamReader(
			System.in));
	private Writer logWriter;

	protected FileLogger logger;
	private Board gameBoard;
	
	/**
	 * Constructor and argument parser for core Player settings
	 * 
	 * @param args
	 */
	public AbstractPlayer(String[] args) {
		if (args.length == 1) {
			playerName = args[0];
		}

		logger = FileLogger.getInstance();
		logger.init(playerName);
		logger.println("Abstract initialized");

	}

	/**
	 * Take the args input into the program and runs the AI
	 * 
	 * @param args
	 *            straight from the main function
	 */
	protected void run() throws IOException {
		// Step 1. Send the player name to the Referee
		System.out.println(playerName);
		System.out.flush();

		// Step 2. Wait for the game configuration
		waitForGameConfiguration();

		// Step 3. Initialize game board, game state, etc.
		// TODO Are we going to make a "Board" class? how to represent?
		gameBoard = new Board(width,height);

		// Step 4. If going second, wait for opponent to make a move
		if (playerNumber != playerTurn) {
			waitForOpponentsMove();
		}

		// Step 5. Start main loop (Waiting for move, making move)
		while (!game_over) { // until game ends
			// Make a move
			int column = decideNextMove();
			sendMove(column);
			recordMove(column, playerNumber);

			// Wait for opponent to make a move
			waitForOpponentsMove();
		}
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
		// TODO Put the move into the board
		gameBoard.addPiece(column, playerNumber);
		
		logger.println("Move made: " + column + " " + playerNumber);
		logger.println("column " + column + 
				" has " + gameBoard.countPiecesInCol(column) + " pieces");
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
		logger.println("Player assignment received1.");
		if (player_assignment.length < 4) { // names can have spaces....
			logger.println("Invalid player assignment given.");
			return;
		}
		playerNumber = (player_assignment[1].equals(playerName)) ? 1 : 2;
		opponentNum = (playerNumber == 1) ? 2 : 1;
		logger.println("Player number: " + playerNumber);

		// Read the game configuration
		String[] gameConfig = listenToReferee();
		logger.println("Game configuration received.");
		if (gameConfig.length != 5) {
			logger.println("Invalid configuration given.");
			return;
		}

		height = Integer.parseInt(gameConfig[0]);
		width = Integer.parseInt(gameConfig[1]);
		numToWin = Integer.parseInt(gameConfig[2]);
		playerTurn = Integer.parseInt(gameConfig[3]);
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
			logWriter.close();
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
	 * @return
	 */
	abstract protected int decideNextMove();
}
