import java.util.Random;

public class Beginner extends Player{
	char youChar;
	
	Beginner(char c, int iam){
		super(c, iam);
	}
	
	@Override
	public int[] getMove(Board gameBoard){
		Random r = new Random(System.currentTimeMillis());
		
		gameBoard.displayBoard();
		int[] move = {-1,-1};
		
		// You char?
		if(this.iam == 1)youChar = gameBoard.getPlayer2Char();
		else youChar = gameBoard.getPlayer1Char();
		
		
		// Check each row/column for longest streak with number of me and number of you
		// if number of me is 3 and longest streak is 3, then I'm about to win, all else, there is nothing useful
		// Same goes for if you're about to win
		// When going for block/win, go through row/column until you get to a blank space AND THE NEXT SPACE IS NOT BLANK
		// ... so that you aren't making a useless move
		
		// ... or ...
		
		// Turn array of characters for each row/columna and then turn them into string
		// then use contains() to see if any of the substrings ".XXX", ".XXX.", "XXX." are found
		// THEN do the block technique mentioned above..
		
		//String u = "" + gameBoard.getPosition(0, 0);
		//u += gameBoard.getPosition(0, 1);
		
		
		while(!gameBoard.isValidMove(move)){
			move[0] = move[1] = -1; //start over
			int me, you;
			// Check can win, rows
			for(int i = 0; i < gameBoard.getBoardSize(); i++){
				me = you = 0;
				for(int j = 0; j < gameBoard.getBoardSize(); j++){
					if(gameBoard.getPosition(i, j) == this.playerChar) me++;
					else if(gameBoard.getPosition(i, j) == youChar) you++;
				}
				// Handle the info
				if(me == 3 && you < 2){ move[0] = i; move[1] = winRow(gameBoard,i);}
				else if (you == 3 && me < 2) { move[0] = i; move[1] = blockRow(gameBoard,i);}
				
				if (move[0] != -1) break; // get out of here if we already came up with a move!
			}
			// Check can win, columns
			for(int i = 0; i < gameBoard.getBoardSize(); i++){
				me = you = 0;
				for(int j = 0; j < gameBoard.getBoardSize(); j++){
					if(gameBoard.getPosition(j, i) == this.playerChar) me++;
					else if(gameBoard.getPosition(j, i) == youChar) you++;
				}
				// Handle the info
				if(me == 3 && you < 2){ move[1] = i; move[0] = winCol(gameBoard,i);}
				else if (you == 3 && me < 2) { move[1] = i; move[0] = blockCol(gameBoard,i);}
				
				if (move[0] != -1) break; // get out of here if we already came up with a move!
			}
			
			if(move[0] == -1){
				move[0] = r.nextInt(gameBoard.getBoardSize());
				move[1] = r.nextInt(gameBoard.getBoardSize());
			}
		}
		
		System.out.println(move[0] + " " + move[1]);

		return move;
	}
	
	private int winRow(Board b, int row){
		for(int i = 0; i < b.getBoardSize(); i++){
			if(b.getPosition(row, i) != this.playerChar && b.getPosition(row, i) != this.youChar) return i;
		}
		return 0;
	}
	private int winCol(Board b, int col){
		for(int i = 0; i < b.getBoardSize(); i++){
			if(b.getPosition(i, col) != this.playerChar && b.getPosition(i, col) != this.youChar) return i;
		}
		return 0;
	}
	private int blockRow(Board b, int row){
		for(int i = 0; i < b.getBoardSize(); i++){
			if(b.getPosition(row, i) == b.getNoPlayerChar()) return i;
		}
		return 0;
	}
	private int blockCol(Board b, int col){
		for(int i = 0; i < b.getBoardSize(); i++){
			if(b.getPosition(i, col) == b.getNoPlayerChar()) return i;
		}
		return 0;
	}
}
