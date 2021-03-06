
import java.util.ArrayList;

public class Master extends Advanced {
        
    Master(char c, int iam){
        super(c, iam, 4);
    }
    
    /*
    Use this constructor to specify a different depth
    */
    Master(char c, int iam, int depth){
        super(c, iam, depth);
    }
    
    /**
     * Perform max's turn w/ alpha-beta pruning
     *
     * @param depth - number of turns in advance to look into, 2 for this project
     * @param gameBoard - copy of gameboard to perform minimax on
     * @param player - current player index, 1 for first, 2 for second
     * @param alpha - current maximum lower bound found by a max node of minimax
     * @param beta - current minimum upper bound found by a min node of minimax
     * @return int current best valued move to take
    */
    private int maxValue(int depth, Board gameBoard, int player, int alpha, int beta) {

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
                    value = Math.max(value, minValue(depth - 1, gameBoard, player, alpha, beta));
                    gameBoard.rescindMove(action);
                    
                    //prune if necessary
                    if (value >= beta) return value;
                    alpha = Math.max(alpha, value);
            }

            return value;
    }

    /**
     * (called from super class) Performs min's turn w/ alpha-beta pruning
     *  Sets the alpha value to -10000 and the beta value to 10000 and calls Master's method version
     *
     * @param depth - number of turns in advance to look into, 2 for this project
     * @param gameBoard - copy of gameboard to perform minimax on
     * @param player - current player index, 1 for first, 2 for second
     * @return int[] current best valued move to take
     */
    @Override
    protected int minValue(int depth, Board gameBoard, int player) {
        return minValue(depth, gameBoard, player, -10000, 10000);
    }

    /**
     * Performs min's turn w/ alpha-beta pruning (called by the override method above)
     *
     * @param depth - number of turns in advance to look into, 2 for this project
     * @param gameBoard - copy of gameboard to perform minimax on
     * @param player - current player index, 1 for first, 2 for second
     * @param alpha - current maximum lower bound found by a max node of minimax
     * @param beta - current minimum upper bound found by a min node of minimax
     * @return int[] current best valued move to take
     */
    private int minValue(int depth, Board gameBoard, int player, int alpha, int beta) {

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
                    value = Math.min(value, maxValue(depth - 1, gameBoard, player, alpha, beta));
                    gameBoard.rescindMove(action);
                    
                    //prune if necessary
                    if (value <= alpha) return value;
                    beta = Math.min(beta, value);
            }

            return value;
    }
    
    /* This method is never called
    @Override
    protected int maxValue(int depth, Board gameBoard, int player) {
        return maxValue(depth, gameBoard, player, -10000, 10000);
    }
    */
}
