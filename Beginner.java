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
			move[0] = move[1] = -1; // start over
			char mec = (iam == 1)? gameBoard.getPlayer1Char() : gameBoard.getPlayer2Char();
			char youc = (iam == 1)? gameBoard.getPlayer2Char() : gameBoard.getPlayer1Char();
			
			// Possible winning strings
			String m1 = "" + gameBoard.getNoPlayerChar() + mec + mec + mec;
			String m2 = "" + mec + mec + mec + gameBoard.getNoPlayerChar();
			String y1 = "" + gameBoard.getNoPlayerChar() + youc + youc + youc;
			String y2 = "" + youc + youc + youc + gameBoard.getNoPlayerChar();
			
			// Check rows
			String temp = "";
			for(int i = 0; i < gameBoard.getBoardSize(); i++){
				for(int j = 0; j < gameBoard.getBoardSize(); j++)
					temp += gameBoard.getPosition(i, j);
				if(temp.contains(m1) || temp.contains(m2)){
					move[0] = i;
					move[1] = winRow(gameBoard, i);
				} else if (temp.contains(y1) || temp.contains(y2)){
					move[0] = i;
					move[1] = blockRow(gameBoard, i);
				}
				temp = "";
			}
			// Check rows
			temp = "";
			for(int i = 0; i < gameBoard.getBoardSize(); i++){
				for(int j = 0; j < gameBoard.getBoardSize(); j++)
					temp += gameBoard.getPosition(j, i);
				if(temp.contains(m1) || temp.contains(m2)){
					move[0] = winCol(gameBoard, i);
					move[1] = i;
				} else if (temp.contains(y1) || temp.contains(y2)){
					move[0] = blockCol(gameBoard, i);
					move[1] = i;
				}
				temp = "";
			}
			// Check diagonals
			// l > r (D1)
			temp = "";
			for(int i = 0; i < gameBoard.getBoardSize(); i++){
				temp += gameBoard.getPosition(i, i);
			}
			if(temp.contains(m1) || temp.contains(m2)){
				move = winD1(gameBoard);
			} else if (temp.contains(y1) || temp.contains(y2)){
				move = blockD1(gameBoard);
			}
			// l > r offset 1 column (D2)
			temp = "";
			for(int i = 1; i < gameBoard.getBoardSize(); i++){
				temp += gameBoard.getPosition(i-1, i);
			}
			if(temp.contains(m1) || temp.contains(m2)){
				move = winD2(gameBoard);
			} else if (temp.contains(y1) || temp.contains(y2)){
				move = blockD2(gameBoard);
			}
			// l > r offset 1 row (D3)
			temp = "";
			for(int i = 1; i < gameBoard.getBoardSize(); i++){
				temp += gameBoard.getPosition(i, i-1);
			}
			if(temp.contains(m1) || temp.contains(m2)){
				move = winD3(gameBoard);
			} else if (temp.contains(y1) || temp.contains(y2)){
				move = blockD3(gameBoard);
			}
			// r > l (D4)
			temp = "";
			for(int i = gameBoard.getBoardSize() - 1; i >= 0; i--){
				temp += gameBoard.getPosition(gameBoard.getBoardSize() - i - 1, i);
			}
			if(temp.contains(m1) || temp.contains(m2)){
				move = winD4(gameBoard);
			} else if (temp.contains(y1) || temp.contains(y2)){
				move = blockD4(gameBoard);
			}
			// r > l offset 1 column (D5)
			temp = "";
			for(int i = gameBoard.getBoardSize() - 2; i >= 0; i--){
				temp += gameBoard.getPosition(gameBoard.getBoardSize() - i - 2, i);
			}
			if(temp.contains(m1) || temp.contains(m2)){
				move = winD5(gameBoard);
			} else if (temp.contains(y1) || temp.contains(y2)){
				move = blockD5(gameBoard);
			}
			// r > l offset 1 row (D6)
			temp = "";
			for(int i = gameBoard.getBoardSize() - 1; i > 0; i--){
				temp += gameBoard.getPosition(gameBoard.getBoardSize() - i, i);
			}
			if(temp.contains(m1) || temp.contains(m2)){
				move = winD6(gameBoard);
			} else if (temp.contains(y1) || temp.contains(y2)){
				move = blockD6(gameBoard);
			}
			
			
			if(move[0] == -1){
				move[0] = r.nextInt(gameBoard.getBoardSize());
				move[1] = r.nextInt(gameBoard.getBoardSize());
			}
		}
		
		System.out.println("Move: " + move[0] + " " + move[1]);

		return move;
	}
	
	private int winRow(Board b, int row){
		for(int i = 0; i < b.getBoardSize(); i++){
			if(b.getPosition(row, i) == b.getNoPlayerChar() && 
					(b.getPosition(row, i+1) != b.getNoPlayerChar() || 
						b.getPosition(row, i-1) == this.playerChar))
				return i;
		}
		return 0;
	}
	private int winCol(Board b, int col){
		for(int i = 0; i < b.getBoardSize(); i++){
			if(b.getPosition(i, col) == b.getNoPlayerChar() && 
					(b.getPosition(i+1, col) != b.getNoPlayerChar() || 
						b.getPosition(i-1, col) == this.playerChar))
				return i;
		}
		return 0;
	}
	private int blockRow(Board b, int row){
		char youc = (iam == 1)? b.getPlayer2Char() : b.getPlayer1Char();
		
		for(int i = 0; i < b.getBoardSize(); i++){
			if(b.getPosition(row, i) == b.getNoPlayerChar() && 
					(b.getPosition(row, i+1) != b.getNoPlayerChar() || 
						b.getPosition(row, i-1) == youc))
				return i;
		}
		return 0;
	}
	private int blockCol(Board b, int col){
		char youc = (iam == 1)? b.getPlayer2Char() : b.getPlayer1Char();
		
		for(int i = 0; i < b.getBoardSize(); i++){
			if(b.getPosition(i, col) == b.getNoPlayerChar() && 
					(b.getPosition(i+1, col) != b.getNoPlayerChar() || 
						b.getPosition(i-1, col) == youc))
				return i;
		}
		return 0;
	}
	private int[] blockD1(Board b){
		int[] move = {0,0};
		char youc = (iam == 1)? b.getPlayer2Char() : b.getPlayer1Char();
		
		for(int i = 0; i < b.getBoardSize(); i++){
			if(b.getPosition(i, i) == b.getNoPlayerChar() && 
					(b.getPosition(i+1, i+1) != b.getNoPlayerChar() || 
						b.getPosition(i-1, i-1) == youc)){
				move[0] = move[1] = i;
			}
		}
		
		return move;
	}
	private int[] winD1(Board b){
		int[] move = {0,0};
		
		for(int i = 0; i < b.getBoardSize(); i++){
			if(b.getPosition(i, i) == b.getNoPlayerChar() && 
					(b.getPosition(i+1, i+1) != b.getNoPlayerChar() || 
						b.getPosition(i-1, i-1) == this.playerChar)){
				move[0] = move[1] = i;
			}
		}
		
		return move;
	}
	private int[] blockD2(Board b){
		int[] move = {0,0};
		char youc = (iam == 1)? b.getPlayer2Char() : b.getPlayer1Char();
		
		for(int i = 1; i < b.getBoardSize(); i++){
			if(b.getPosition(i-1, i) == b.getNoPlayerChar() && 
					(b.getPosition(i, i+1) != b.getNoPlayerChar() || 
						b.getPosition(i-2, i-1) == youc)){
				move[0] = i - 1;
				move[1] = i;
			}
		}
		
		return move;
	}
	private int[] winD2(Board b){
		int[] move = {0,0};
		
		for(int i = 1; i < b.getBoardSize(); i++){
			if(b.getPosition(i-1, i) == b.getNoPlayerChar() && 
					(b.getPosition(i, i+1) != b.getNoPlayerChar() || 
						b.getPosition(i-2, i-1) == this.playerChar)){
				move[0] = i - 1;
				move[1] = i;
			}
		}
		
		return move;
	}
	private int[] blockD3(Board b){
		int[] move = {0,0};
		char youc = (iam == 1)? b.getPlayer2Char() : b.getPlayer1Char();
		
		for(int i = 1; i < b.getBoardSize(); i++){
			if(b.getPosition(i, i-1) == b.getNoPlayerChar() && 
					(b.getPosition(i+1, i) != b.getNoPlayerChar() || 
						b.getPosition(i-1, i-2) == youc)){
				move[0] = i;
				move[1] = i - 1;
			}
		}
		
		return move;
	}
	private int[] winD3(Board b){
		int[] move = {0,0};
		
		for(int i = 1; i < b.getBoardSize(); i++){
			if(b.getPosition(i, i-1) == b.getNoPlayerChar() && 
					(b.getPosition(i+1, i) != b.getNoPlayerChar() || 
						b.getPosition(i-1, i-2) == this.playerChar)){
				move[0] = i;
				move[1] = i - 1;
			}
		}
		
		return move;
	}
	private int[] blockD4(Board b){
		int[] move = {0,0};
		char youc = (iam == 1)? b.getPlayer2Char() : b.getPlayer1Char();
		
		for(int i = b.getBoardSize() - 1; i >= 0; i--){
			if(b.getPosition(b.getBoardSize() - i - 1, i) == b.getNoPlayerChar() && 
					(b.getPosition(b.getBoardSize() - i, i-1) != b.getNoPlayerChar() || 
						b.getPosition(b.getBoardSize() - i - 2, i+1) == youc)){
				move[0] = b.getBoardSize() - i - 1;
				move[1] = i;
			}
		}
		
		return move;
	}
	private int[] winD4(Board b){
		int[] move = {0,0};
		
		for(int i = b.getBoardSize() - 1; i >= 0; i--){
			if(b.getPosition(b.getBoardSize() - i - 1, i) == b.getNoPlayerChar() && 
					(b.getPosition(b.getBoardSize() - i, i-1) != b.getNoPlayerChar() || 
					b.getPosition(b.getBoardSize() - i - 2, i+1) == this.playerChar)){
				move[0] = b.getBoardSize() - i - 1;
				move[1] = i;
			}
		}
		
		return move;
	}
	private int[] blockD5(Board b){
		int[] move = {0,0};
		char youc = (iam == 1)? b.getPlayer2Char() : b.getPlayer1Char();
		
		for(int i = b.getBoardSize() - 2; i >= 0; i--){
			if(b.getPosition(b.getBoardSize() - i - 2, i) == b.getNoPlayerChar() && 
					(b.getPosition(b.getBoardSize() - i - 1, i-1) != b.getNoPlayerChar() || 
						b.getPosition(b.getBoardSize() - i - 3, i+1) == youc)){
				move[0] = b.getBoardSize() - i - 2;
				move[1] = i;
			}
		}
		
		return move;
	}
	private int[] winD5(Board b){
		int[] move = {0,0};
		
		for(int i = b.getBoardSize() - 2; i >= 0; i--){
			if(b.getPosition(b.getBoardSize() - i - 2, i) == b.getNoPlayerChar() && 
					(b.getPosition(b.getBoardSize() - i - 1, i-1) != b.getNoPlayerChar() || 
					b.getPosition(b.getBoardSize() - i - 3, i+1) == this.playerChar)){
				move[0] = b.getBoardSize() - i -2;
				move[1] = i;
			}
		}
		
		return move;
	}
	/*for(int i = gameBoard.getBoardSize() - 1; i > 0; i--){
		temp += gameBoard.getPosition(gameBoard.getBoardSize() - i, i);
	}*/
	private int[] blockD6(Board b){
		int[] move = {0,0};
		char youc = (iam == 1)? b.getPlayer2Char() : b.getPlayer1Char();
		
		for(int i = b.getBoardSize() - 1; i >= 0; i--){
			if(b.getPosition(b.getBoardSize() - i, i) == b.getNoPlayerChar() && 
					(b.getPosition(b.getBoardSize() - i + 1, i-1) != b.getNoPlayerChar() || 
						b.getPosition(b.getBoardSize() - i - 1, i+1) == youc)){
				move[0] = b.getBoardSize() - i;
				move[1] = i;
			}
		}
		
		return move;
	}
	private int[] winD6(Board b){
		int[] move = {0,0};
		
		for(int i = b.getBoardSize() - 1; i >= 0; i--){
			if(b.getPosition(b.getBoardSize() - i, i) == b.getNoPlayerChar() && 
					(b.getPosition(b.getBoardSize() - i + 1, i-1) != b.getNoPlayerChar() || 
					b.getPosition(b.getBoardSize() - i - 1, i+1) == this.playerChar)){
				move[0] = b.getBoardSize() - i;
				move[1] = i;
			}
		}
		
		return move;
	}
}
