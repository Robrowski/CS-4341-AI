package player;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import common.Board;
import common.MoveHolder;

public class MiniMaxPlayer extends AbstractPlayer {

	private boolean alpha_beta_enabled = false;
	/** The maximum depth we will allow for mini max */
	int MAXDEPTH = 4;
	Random random = new Random();

	/**
	 * The current max for a given move across all branches Intended to be the
	 * highest valued move for the next immediate move. Depth = 0 choice
	 */
	int max_value_found;
	/**
	 * Min value moved found so far (unused)
	 */
	int min_value_found;

	public MiniMaxPlayer(String[] args) {
		super(args);

		List<String> argsList = Arrays.asList(args);
		if (argsList.contains("--alpha-beta")) {
			alpha_beta_enabled = true;
		}
	}

	public static void main(String[] args) throws IOException {
		MiniMaxPlayer mmp = new MiniMaxPlayer(args);
		mmp.run();
	}

	@Override
	protected int decideNextMove() {
		Board copy = new Board(this.gameBoard);

		min_value_found = Integer.MAX_VALUE;
		max_value_found = Integer.MIN_VALUE;
		MoveHolder next = miniMax(-1, copy, 0, this.playerNumber);
		logger.println("next move is: " + next.getCol());
		return next.getCol();
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
	 * @param parentMove
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
	private MoveHolder miniMax(int parentMove, Board current, int depth,
			int player) {
		logger.println("current depth is: " + depth);
		List<Integer> moves = current.getPossibleMoves();
		/**
		 * If depth limit reached or If no possible moves, we can procede to
		 * estimate the current board's value
		 */
		if (depth == MAXDEPTH || moves.size() == 0) {
			logger.println("depth = max, stopping at move " + parentMove);
			int estimate = estimateBoard(current);
			MoveHolder moveEval = new MoveHolder(parentMove, estimate);
			return moveEval;
		} else {
			if (player == this.playerNumber) { // maximizing score
				// Even number depth moves?

				int bestValue = Integer.MIN_VALUE;
				MoveHolder bestMove = new MoveHolder(parentMove, bestValue);
				int newDepth = depth + 1; // Depth of the proposed moves
				for (int move : moves) {
					/**
					 * copy the current board in order to split and add a new
					 * board state to the tree.
					 */
					Board newBoard = new Board(current);
					newBoard.addPiece(move, player);

					/**
					 * Run miniMax on the next layer of the tree, which is to
					 * maximize the move of the opponent.
					 */
					MoveHolder minMaxMove = miniMax(move, newBoard, newDepth,
							this.opponentNum);

					if (minMaxMove.getValue() > bestMove.getValue()) {
						/**
						 * set the best move value to the better move we just
						 * found. Set the Column to the move that leads to this
						 * value before recursing to eventually use the first
						 * split, NOT the last move in the tree.
						 */
						bestMove.setValue(minMaxMove.getValue());
						bestMove.setCol(move);

						if (alpha_beta_enabled && newDepth == 1) {
							// Keep track for AB pruning
							// - simple pruning from the top branch
							// TODO keep track of MAX/MIN at each branching?
							max_value_found = Math
									.max(max_value_found, bestMove.getValue());
						}
					}
				}
				logger.println("best score for depth (max) " + depth + " : "
						+ bestMove.getValue());
				logger.println("best move  for depth (max) " + depth + " : "
						+ bestMove.getCol());
				return bestMove;
			} else { // minimizing score

				int bestValue = Integer.MAX_VALUE;
				MoveHolder bestMove = new MoveHolder(parentMove, bestValue);
				int newDepth = depth + 1; // Depth of the proposed moves
				for (int move : moves) {
					/**
					 * copy the current board in order to split and add a new
					 * board state to the tree.
					 */
					Board newBoard = new Board(current);
					newBoard.addPiece(move, player);

					/**
					 * Run miniMax on the next layer of the tree, which is to
					 * maximize the move of the opponent.
					 */
					MoveHolder minMaxMove = miniMax(move, newBoard, newDepth,
							this.playerNumber);

					if (minMaxMove.getValue() < bestMove.getValue()) {
						/**
						 * set the best move value to the better move we just
						 * found. Set the Column to the move that leads to this
						 * value before recursing to eventually use the first
						 * split, NOT the last move in the tree.
						 */
						logger.println("best found!");
						bestMove.setValue(minMaxMove.getValue());
						bestMove.setCol(move);

						// Basic AB pruning - break out when the current min is
						// less than the Best found for the top branch
						if (alpha_beta_enabled
								&& bestMove.getValue() < this.max_value_found) {
							logger.println("AB PRUNNED");
							break;
						}
					}

				}
				logger.println("best score for depth (min) " + depth + " : "
						+ bestMove.getValue());
				logger.println("best move  for depth (min) " + depth + " : "
						+ bestMove.getCol());
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
	private int estimateBoard(Board current) {
		Random random = new Random();
		int randomNumber = (random.nextInt(20));
		logger.println("at leaf. score is: " + randomNumber);

		return randomNumber;
	}

}
