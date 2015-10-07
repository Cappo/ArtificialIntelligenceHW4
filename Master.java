
import java.util.ArrayList;

public class Master extends Advanced {
    
    int alpha, beta;
    
    Master(char c, int iam){
        super(c, iam, 4);
        alpha = -10000;
        beta = 10000;
    }
    
    /**
	 * Perform max's turn w/ alpha-beta pruning
	 *
	 * @param depth - number of turns in advance to look into, 2 for this project
	 * @param gameBoard - copy of gameboard to perform minimax on
	 * @param player - current player index, 1 for first, 2 for second
	 * @return int current best valued move to take
	 */
        @Override
	protected int maxValue(int depth, Board gameBoard, int player) {

		int value = -10000;

		expandedNodes++;

		//get heuristic value and return if depth reached
		if (depth == 0) return evaluateUtility(gameBoard, player);

		//change player
		player = (player == 1) ? 2 : 1;

		//get list of actions
		ArrayList<int[]> actions = getActions(gameBoard);

		//process action and determine min play
		for(int[] action : actions) {
			gameBoard.playMove(action, player);
			value = Math.max(value, minValue(depth - 1, gameBoard, player));
			gameBoard.rescindMove(action);
                        if (value >= beta) return value;
                        alpha = Math.max(alpha, value);
		}

		return value;
	}

	/**
	 * Performs min's turn w/ alpha-beta pruning
	 *
	 * @param depth - number of turns in advance to look into, 2 for this project
	 * @param gameBoard - copy of gameboard to perform minimax on
	 * @param player - current player index, 1 for first, 2 for second
	 * @return int[] current best valued move to take
	 */
        @Override
	protected int minValue(int depth, Board gameBoard, int player) {

		int value = 10000;

		expandedNodes++;

		//return heuristic value if depth reached
		if (depth == 0) return evaluateUtility(gameBoard, player);

		//change player
		player = (player == 1) ? 2 : 1;

		//get actions
		ArrayList<int[]> actions = getActions(gameBoard);

		//process action and find max play
		for(int[] action : actions) {
			gameBoard.playMove(action, player);
			value = Math.min(value, maxValue(depth - 1, gameBoard, player));
			gameBoard.rescindMove(action);
                        if (value <= alpha) return value;
                        beta = Math.min(beta, value);
		}

		return value;
	}
    
}
