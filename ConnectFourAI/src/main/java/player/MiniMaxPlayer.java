package player;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import common.Board;
import common.MoveHolder;

public class MiniMaxPlayer extends AbstractPlayer {

	private boolean alpha_beta_enabled = false;
	private boolean gamma_enabled = false;
	private int tabbed_logging_activated = 0;
	/** The maximum depth we will allow for mini max */
	private int MAXDEPTH = 4;
	Random random = new Random();

	/* The following are statistics on each move */
	private int leaves_visited, branches_made;

	public MiniMaxPlayer(String[] args) {
		super(args);

		List<String> argsList = Arrays.asList(args);
		if (argsList.contains("--alpha-beta")) {
			alpha_beta_enabled = true;
		}
		if (argsList.contains("--tabbed-logging")) {
			tabbed_logging_activated = 1;
		}
		if (argsList.contains("--gamma-pruning")) {
			gamma_enabled = true;
		}
		// Read for a max depth and the next number after it
		if (argsList.contains("MAXDEPTH=")) {
			String max_depth_arg = argsList
					.get(argsList.indexOf("MAXDEPTH=") + 1);
			try {
				MAXDEPTH = Integer.parseInt(max_depth_arg);
				logger.println("Max depth set to " + MAXDEPTH);
			} catch (NumberFormatException mne) {
				logger.println("Couldn't read the argument for MAXDEPTH= ... arg = "
						+ max_depth_arg);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		MiniMaxPlayer mmp = new MiniMaxPlayer(args);
		mmp.run();
	}

	@Override
	protected MoveHolder decideNextMove() {
		Board copy = new Board(this.gameBoard);

		leaves_visited = 0;
		branches_made = 0;
		MoveHolder next = miniMax(null, copy, 0, this.playerNumber,
				Integer.MIN_VALUE);
		logger.println("next move is: " + next.getCol());
		logger.println("Leaves: " + leaves_visited + "   Branches: "
				+ branches_made);
		return next;
	}

	/**
	 * An implementation of the miniMax algorithm for determining the current
	 * best possible move for the player by minimizing the opponent's chance of
	 * winning while increasing the player's chance.
	 * 
	 * Currently Recurses through every possibility of moves, up to a certain
	 * depth.
	 * 
	 * TODO: instead of passing board copies, pass a list of moves instead TODO:
	 * Consider possibility of using a stack for board and pop changes when
	 * going back up recursion tree.
	 * 
	 * @param move2
	 *            The previous move made at a parent state
	 * @param current
	 *            The current board state we are looking at
	 * @param depth
	 *            the current depth of the tree we are at
	 * @param player
	 *            The player whose turn we are evaluating. If it is us, we are
	 *            maximizing, if opponent we are minimizing.
	 * 
	 * @return The decided best move
	 */
	private MoveHolder miniMax(MoveHolder parent_move, Board current,
			int depth,
			int player, int bestValue) {
		// logger.println("Current depth is: " + depth, tabbed_logging_activated
		// * depth);
		List<MoveHolder> moves = current.getPossibleMoves(player);

		/**
		 * If depth limit reached or If no possible moves, we can proceed to
		 * estimate the current board's value
		 */
		if (depth == MAXDEPTH || moves.size() == 0) {
			// logger.println("depth = max, stopping at move " + parentMove,
			// tabbed_logging_activated * depth);
			this.leaves_visited += 1;
			int estimate = estimateBoard(current, depth);
			parent_move.setValue(estimate);
			logger.printMove(parent_move, tabbed_logging_activated * depth);
			return parent_move;
		} else {
			int newDepth = depth + 1; // Depth of the proposed moves
			this.branches_made += 1; // recording number of branches made

			if (player == this.playerNumber) { // maximizing score

				MoveHolder bestMove = new MoveHolder(-1, Integer.MIN_VALUE);
				for (MoveHolder move : moves) {
					/**
					 * copy the current board in order to split and add a new
					 * board state to the tree.
					 */
					Board newBoard = new Board(current);
					MoveHolder minMaxMove;
					int move_result = newBoard.applyMove(move, player);
					if (move_result == Board.LOSS) {
						// logger.println("Not taking a pop loss",
						// tabbed_logging_activated * newDepth);
						continue; // Continue through the loop
					}
					if (gamma_enabled && move_result == Board.WIN) {
						bestMove = move.setValue(Integer.MAX_VALUE - newDepth
								* 100); // GREAT!!
						logger.printMove(bestMove, tabbed_logging_activated
								* newDepth);

						break;
					} else {
						/**
						 * Run miniMax on the next layer of the tree, which is
						 * to maximize the move of the opponent.
						 */
						minMaxMove = miniMax(move, newBoard, newDepth,
								this.opponentNum, bestMove.getValue());
						minMaxMove.setCol(move.getCol());
					}

					if (minMaxMove.getValue() > bestMove.getValue()) {
						/**
						 * set the best move value to the better move we just
						 * found. Set the Column to the move that leads to this
						 * value before recursing to eventually use the first
						 * split, NOT the last move in the tree.
						 */
						bestMove = minMaxMove;

						// AB pruning - break out when the current max is
						// less than the Best found for the branch above because
						// it won't be picked as min anyways
						if (alpha_beta_enabled
								&& bestMove.getValue() > bestValue
								&& depth != 0) {
							break;
						}
					}
				}
				logger.printMove(bestMove,
						tabbed_logging_activated * depth);

				return bestMove;
			} else { // minimizing score

				MoveHolder bestMove = new MoveHolder(-1, Integer.MAX_VALUE);
				for (MoveHolder move : moves) {
					/**
					 * copy the current board in order to split and add a new
					 * board state to the tree.
					 */
					Board newBoard = new Board(current);
					MoveHolder minMaxMove;
					int move_result = newBoard.applyMove(move, player);
					if (move_result == Board.LOSS) {
						continue; // Continue through the loop
					}
					if (gamma_enabled && move_result == Board.WIN) {
						bestMove = move.setValue(Integer.MIN_VALUE + newDepth
								* 100); // GREAT!!
						logger.printMove(bestMove, tabbed_logging_activated
								* newDepth);
						break;
					} else {
						/**
						 * Run miniMax on the next layer of the tree, which is
						 * to maximize the move of the opponent.
						 */
						minMaxMove = miniMax(move, newBoard, newDepth,
								this.playerNumber, bestMove.getValue());
						minMaxMove.setCol(move.getCol());
					}


					if (minMaxMove.getValue() < bestMove.getValue()) {
						/**
						 * set the best move value to the better move we just
						 * found. Set the Column to the move that leads to this
						 * value before recursing to eventually use the first
						 * split, NOT the last move in the tree.
						 */
						bestMove = minMaxMove;

						// AB pruning - break out when the current min is
						// less than the Best found for the branch above because
						// it won't be picked as max anyways
						if (alpha_beta_enabled
								&& bestMove.getValue() < bestValue) {
							break;
						}
					}

				}
				logger.printMove(bestMove.setCol(parent_move.getCol()),
						tabbed_logging_activated * depth);
				return bestMove;
			}
		}
	}

	/**
	 * The method that evaluates a given board, estimating how good of a board
	 * state it is
	 * 
	 * TODO: Implement this
	 * 
	 * @param current
	 *            The board to estimate
	 * @return
	 */
	private int estimateBoard(Board current, int depth) {
		Random random = new Random();
		int randomNumber = (random.nextInt(20));

		return randomNumber;
	}

}
