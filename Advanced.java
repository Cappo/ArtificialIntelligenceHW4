import java.lang.Math;import java.lang.Override;import java.lang.String;import java.lang.System;import java.util.ArrayList;
import java.util.Arrays;

public class Advanced extends Player {

	int depth;
	int expandedNodes;

	Advanced(char c, int iam, int depth){
		super(c, iam);
		this.depth = depth;
		this.expandedNodes = 2;
	}

	@Override
	public int[] getMove(Board gameBoard){
		
		gameBoard.displayBoard();
		int[] move = {-1,-1};

		long start = System.nanoTime();

		//perform minimax and get move
		move = minimaxDecision(depth, gameBoard, iam);

		long end = System.nanoTime();

		System.out.println("Move: " + move[0] + " " + move[1]);
		System.out.println("Expanded nodes: " + expandedNodes);
		System.out.println("Time for move: " + ((end - start)/ 1000000.00));
		System.out.println();

		this.expandedNodes = 0;

		return move;
	}

	/**
	 * @param depth - number of turns in advance to look into, 2 for this project
	 * @param gameBoard - copy of gameboard to perform minimax on
	 * @param player - current player index, 1 for first, 2 for second
	 * @return int[] current best valued move to take
	 */
	private int[] minimaxDecision(int depth, Board gameBoard, int player) {

		int[] move = {-1, -1}; //holds the current best move
		int value = -100000; //holds the current best value of move

		//copy board
		Board newBoard = new Board(gameBoard);

		//get list of possible moves
		ArrayList<int[]> actions = getActions(newBoard);

		//process each possible action
		for(int[] action : actions) {
			//play out possible move
			newBoard.playMove(action, player);

			//start stepping through states
			int result = minValue(depth, newBoard, player);

			//update move if found better
			if (result > value) {
				value = result;
				move = action;
			}
			//reset to go onto next action
			newBoard.rescindMove(action);
		}

		return move;
	}

	/**
	 * Perform max's turn
	 *
	 * @param depth - number of turns in advance to look into, 2 for this project
	 * @param gameBoard - copy of gameboard to perform minimax on
	 * @param player - current player index, 1 for first, 2 for second
	 * @return int current best valued move to take
	 */
	private int maxValue(int depth, Board gameBoard, int player) {

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
		}

		return value;
	}

	/**
	 * Performs min's turn
	 *
	 * @param depth - number of turns in advance to look into, 2 for this project
	 * @param gameBoard - copy of gameboard to perform minimax on
	 * @param player - current player index, 1 for first, 2 for second
	 * @return int[] current best valued move to take
	 */
	private int minValue(int depth, Board gameBoard, int player) {

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
		}

		return value;
	}

	/**
	 * Get the possible actions for a given state
	 *
	 * @param gameBoard - current game state
	 * @return list of actions
	 */
	private ArrayList<int[]> getActions(Board gameBoard) {
		ArrayList<int[]> actions = new ArrayList<int[]>();

		//traverse through board and find open spots
		for (int i = 0; i < gameBoard.getBoardSize(); i++) {
			for (int j = 0; j < gameBoard.getBoardSize(); j++) {
				if (gameBoard.getPosition(i, j) == gameBoard.getNoPlayerChar()) {
					int[] move = {i,j};
					actions.add(move);
				}
			}
		}

		return actions;
	}

	/**
	 * Evaluate the utility of a given resulting state with heuristic
	 *
	 * @param gameBoard - current resulting game state
	 * @param player - current players turn
	 * @return - value of utility
	 */
	private int evaluateUtility(Board gameBoard, int player) {
		int fourMe = 0;
		int fourYou = 0;
		int openThreeMe = 0;
		int openThreeYou = 0;
		int openTwoMe = 0;
		int openTwoYou = 0;

		char me = (player == 1) ? gameBoard.getPlayer1Char() : gameBoard.getPlayer2Char();
		char you = (player == 1) ? gameBoard.getPlayer2Char() : gameBoard.getPlayer1Char();
		char no = gameBoard.getNoPlayerChar();

		//list of possible open two combinations
		String a = "" + no + me + me;
		String b = "" + me + me + no;
		String c = "" + me + no + me;
		String d = "" + no + you + you;
		String e = "" + you + you + no;
		String f = "" + you + no + you;

		//list of possible open three combinations
		String g = "" + me + no + me + me;
		String h = "" + me + me + no + me;
		String j = "" + no + me + me + me;
		String k = "" + me + me + me + no;
		String l = "" + you + no + you + you;
		String m = "" + you + you + no + you;
		String n = "" + no + you + you + you;
		String o = "" + you + you + you + no;

		//list of possible four combinations
		String p = "" + me + me + me + me;
		String q = "" + you + you + you + you;

		String[] columns = new String[gameBoard.getBoardSize()];
		Arrays.fill(columns, "");

		String[] diagonals = new String[6];
		Arrays.fill(diagonals, "");

		//iteration through rows
		for (int i = 0; i < gameBoard.getBoardSize(); i++) {
			//get string for row
			String row = new String (gameBoard.getGameBoard()[i]);

			//get strings of columns
			for (int z = 0; z < gameBoard.getBoardSize(); z++) {
				columns[z] += gameBoard.getPosition(i,z);
			}

			//get strings of diagonals
			diagonals[0] += gameBoard.getPosition(i,i);
			if (i != gameBoard.getBoardSize() - 1) diagonals[1] += gameBoard.getPosition(i, i+1);
			if (i != 0) diagonals[2] += gameBoard.getPosition(i, i - 1);
			diagonals[3] += gameBoard.getPosition(i, gameBoard.getBoardSize() - 1 - i);
			if (i != gameBoard.getBoardSize() - 1) diagonals[4] += gameBoard.getPosition(i, gameBoard.getBoardSize() - 2 - i);
			if (i != 0) diagonals[5] += gameBoard.getPosition(i, gameBoard.getBoardSize() - i);

			//check each combination of substrings for row
			if (row.contains(p)) fourMe++;
			else if (row.contains(q)) fourYou++;

			else if (row.contains(g)) openThreeMe++;
			else if (row.contains(h)) openThreeMe++;
			else if (row.contains(j)) openThreeMe++;
			else if (row.contains(k)) openThreeMe++;
			else if (row.contains(l)) openThreeYou++;
			else if (row.contains(m)) openThreeYou++;
			else if (row.contains(n)) openThreeYou++;
			else if (row.contains(o)) openThreeYou++;

			else if (row.contains(a + ".") || row.contains("." + a)) openTwoMe++;
			else if (row.contains(b + ".") || row.contains("." + b)) openTwoMe++;
			else if (row.contains(c + ".") || row.contains("." + c)) openTwoMe++;
			else if (row.contains(d + ".") || row.contains("." + d)) openTwoYou++;
			else if (row.contains(e + ".") || row.contains("." + e)) openTwoYou++;
			else if (row.contains(f + ".") || row.contains("." + f)) openTwoYou++;

		}

		//iterate through columns
		for (int i = 0; i < gameBoard.getBoardSize(); i++) {
			String column = new String (columns[i]);

			//check each combination of substrings for column
			if (column.contains(p)) fourMe++;
			else if (column.contains(q)) fourYou++;

			else if (column.contains(g)) openThreeMe++;
			else if (column.contains(h)) openThreeMe++;
			else if (column.contains(j)) openThreeMe++;
			else if (column.contains(k)) openThreeMe++;
			else if (column.contains(l)) openThreeYou++;
			else if (column.contains(m)) openThreeYou++;
			else if (column.contains(n)) openThreeYou++;
			else if (column.contains(o)) openThreeYou++;

			else if (column.contains(a + ".") || column.contains("." + a)) openTwoMe++;
			else if (column.contains(b + ".") || column.contains("." + b)) openTwoMe++;
			else if (column.contains(c + ".") || column.contains("." + c)) openTwoMe++;
			else if (column.contains(d + ".") || column.contains("." + d)) openTwoYou++;
			else if (column.contains(e + ".") || column.contains("." + e)) openTwoYou++;
			else if (column.contains(f + ".") || column.contains("." + f)) openTwoYou++;

		}

		//iterate through diagonals
		for (int i = 0; i < gameBoard.getBoardSize(); i++) {
			String diagonal = new String (diagonals[i]);

			//check each combination of substring for diagonal
			if (diagonal.contains(p)) fourMe++;
			else if (diagonal.contains(q)) fourYou++;

			else if (diagonal.contains(g)) openThreeMe++;
			else if (diagonal.contains(h)) openThreeMe++;
			else if (diagonal.contains(j)) openThreeMe++;
			else if (diagonal.contains(k)) openThreeMe++;
			else if (diagonal.contains(l)) openThreeYou++;
			else if (diagonal.contains(m)) openThreeYou++;
			else if (diagonal.contains(n)) openThreeYou++;
			else if (diagonal.contains(o)) openThreeYou++;

			else if (diagonal.contains(a + ".") || diagonal.contains("." + a)) openTwoMe++;
			else if (diagonal.contains(b + ".") || diagonal.contains("." + b)) openTwoMe++;
			else if (diagonal.contains(c + ".") || diagonal.contains("." + c)) openTwoMe++;
			else if (diagonal.contains(d + ".") || diagonal.contains("." + d)) openTwoYou++;
			else if (diagonal.contains(e + ".") || diagonal.contains("." + e)) openTwoYou++;
			else if (diagonal.contains(f + ".") || diagonal.contains("." + f)) openTwoYou++;

		}

		//calculate and return utility
		return 10 * (fourMe - fourYou) + 3 * (openThreeMe - openThreeYou) + (openTwoMe - openTwoYou);
	}

}
