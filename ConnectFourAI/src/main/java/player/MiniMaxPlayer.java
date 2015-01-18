package player;

import java.util.List;
import java.io.IOException;

import common.Board;
import common.MoveHolder;

public class MiniMaxPlayer extends AbstractPlayer{

	int MAXDEPTH = 2;
	
	public MiniMaxPlayer(String[] args) {
		super(args);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		MiniMaxPlayer mmp = new MiniMaxPlayer(args);
		mmp.run();

	}

	@Override
	protected int decideNextMove() {
		// TODO Auto-generated method stub
		MoveHolder next = miniMax(-1, this.gameBoard,0,this.playerNumber);
		return next.getCol();
	}
	
	private MoveHolder miniMax(int parentMove, Board current, int depth, int player){
		if (depth == MAXDEPTH){
			int estimate = estimateBoard(current);
			MoveHolder moveEval = new MoveHolder(parentMove,estimate);
			return moveEval;
		}
		else{
			List<Integer> moves = current.getPossibleMoves();
			
			// If no possible moves, we are at a leaf in our move
			// tree and can procede to estimate its value
			if (moves.size() == 0) { 
				int estimate = estimateBoard(current);
				MoveHolder moveEval = new MoveHolder(parentMove,estimate);
				return moveEval;
			}
			else{
				if (player == this.playerNumber){ // maximizing
					int bestValue = 0; // init to - infinity
					MoveHolder bestMove = new MoveHolder();
					for (int move : moves){
						// copy the current board in order to split and
						// add a new board state to the tree.
						Board newBoard = new Board(current);
						newBoard.addPiece(move, player);
						
						// Run miniMax on the next layer of the tree, which
						// is to minimize the move of the opponent.
						MoveHolder minMaxMove = miniMax(move,newBoard,
												depth++,this.opponentNum);
						
						if (minMaxMove.getValue() > bestMove.getValue()){
							// set the best move value to the better move
							// we just found. Set the Column to the move
							// that leads to this value before recursing to 
							// eventually use the first split, NOT the 
							// last move in the tree.
							bestMove.setValue(minMaxMove.getValue());
							bestMove.setCol(move);
						}
					}
					return bestMove;
				}
				else { // minimizing
					int bestValue = 0; // init to + infinity
					MoveHolder bestMove = new MoveHolder();
					for (int move : moves){
						// copy the current board in order to split and
						// add a new board state to the tree.
						Board newBoard = new Board(current);
						newBoard.addPiece(move, player);
						
						// Run miniMax on the next layer of the tree, which
						// is to maximize the move of the opponent.
						MoveHolder minMaxMove = miniMax(move,newBoard,
												depth++,this.playerNumber);
						if (minMaxMove.getValue() < bestMove.getValue()){
							// set the best move value to the better move
							// we just found. Set the Column to the move
							// that leads to this value before recursing to 
							// eventually use the first split, NOT the 
							// last move in the tree.
							bestMove.setValue(minMaxMove.getValue());
							bestMove.setCol(move);
						}
					}
					return bestMove;
				}
			}
		}
	}
	
	private int estimateBoard(Board current){
		return 0;
	}

}
