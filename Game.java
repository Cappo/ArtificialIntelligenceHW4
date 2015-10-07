
public class Game {
	private Board gameBoard;
	private Player player1, player2, turn;
	public int turns = 0;
	
	public Game(Player player1, Player player2){
		this.player1 = player1;
		this.player2 = player2;
		this.gameBoard = new Board(5,player1.playerChar,player2.playerChar,'.');
		this.turn = player1;
		
		System.out.println("\nNew game created!\n\nPlayer 1 - " + this.player1.toString() + "\nPlayer 2 - " + this.player2.toString());
	}
	
	public Player playGame(){
		Player winner = null;
		int[] move = {-1,-1}; // Default, not valid move
		
		while(winner == null && this.turns < this.gameBoard.getBoardSize()*this.gameBoard.getBoardSize()){
			// Player 1's turn
			System.out.println("Player 1's turn:");
			this.turn = player1;
			while(!this.isValidMove(move, this.gameBoard)){
				move = this.turn.getMove(this.gameBoard);
				if(!this.isValidMove(move, this.gameBoard)) System.out.println("Invalid move, try again!");
			}
			this.gameBoard.playMove(move, 1);
			this.turns++;
			winner = this.isWinner2(this.gameBoard);
			if (winner == null && this.turns < this.gameBoard.getBoardSize()*this.gameBoard.getBoardSize()){
				// Player 2's turn (if player 1 hasn't won already)
				System.out.println("Player 2's turn:");
				this.turn = player2;
				while(!this.isValidMove(move, gameBoard)){
					move = this.turn.getMove(this.gameBoard);
					if(!this.isValidMove(move, this.gameBoard)) System.out.println("Invalid move, try again!");
				}
				this.gameBoard.playMove(move, 2);
				
				this.turns++;
				winner = this.isWinner2(this.gameBoard);
			}
		}
		if(winner == this.player1) System.out.println("Player 1 wins!");
		else if (winner == this.player2) System.out.println("Player 2 wins!");
		else System.out.println("Nobody won!");
		this.gameBoard.displayBoard();
		
		return winner;
	}
	
	private Player isWinner2(Board b){
		String p1w = "" + b.getPlayer1Char() + b.getPlayer1Char() + b.getPlayer1Char() + b.getPlayer1Char();
		String p2w = "" + b.getPlayer2Char() + b.getPlayer2Char() + b.getPlayer2Char() + b.getPlayer2Char();
		// Rows
		String temp = "";
		for(int i = 0; i < b.getBoardSize(); i++){
			temp = "";
			for(int j = 0; j < b.getBoardSize(); j++)
				temp += b.getPosition(i, j);
			if(temp.contains(p1w)) return this.player1;
			else if (temp.contains(p2w)) return this.player2;
		}
		// Columns
		temp = "";
		for(int i = 0; i < b.getBoardSize(); i++){
			temp = "";
			for(int j = 0; j < b.getBoardSize(); j++)
				temp += b.getPosition(j, i);
			if(temp.contains(p1w)) return this.player1;
			else if (temp.contains(p2w)) return this.player2;
		}
		// Diagonals
		// l > r
		temp = "";
		for(int i = 0; i < b.getBoardSize(); i++){
			temp += b.getPosition(i, i);
		}
		if(temp.contains(p1w)) return this.player1;
		else if (temp.contains(p2w)) return this.player2;
		// l > r offset 1 column
		temp = "";
		for(int i = 1; i < b.getBoardSize(); i++){
			temp += b.getPosition(i-1, i);
		}
		if(temp.contains(p1w)) return this.player1;
		else if (temp.contains(p2w)) return this.player2;
		// l > r offset 1 row
		temp = "";
		for(int i = 1; i < b.getBoardSize(); i++){
			temp += b.getPosition(i, i-1);
		}
		if(temp.contains(p1w)) return this.player1;
		else if (temp.contains(p2w)) return this.player2;
		// r > l
		temp = "";
		for(int i = b.getBoardSize() - 1; i >= 0; i--){
			temp += b.getPosition(b.getBoardSize() - i - 1, i);
		}
		if(temp.contains(p1w)) return this.player1;
		else if (temp.contains(p2w)) return this.player2;
		// r > l offset 1 column
		temp = "";
		for(int i = b.getBoardSize() - 2; i >= 0; i--){
			temp += b.getPosition(b.getBoardSize() - i - 2, i);
		}
		if(temp.contains(p1w)) return this.player1;
		else if (temp.contains(p2w)) return this.player2;
		// r > l offset 1 row
		temp = "";
		for(int i = b.getBoardSize() - 1; i > 0; i--){
			temp += b.getPosition(b.getBoardSize() - i, i);
		}
		if(temp.contains(p1w)) return this.player1;
		else if (temp.contains(p2w)) return this.player2;
		
		
		return null;
	}
	
	private Player isWinner(Board b){
		// REWRITE FOR NEW GAME UGH>>>>>>>
		char temp;
		int streak;
		Boolean good = true;
		// Check across each row
		for(int i = 0; i < b.getBoardSize(); i++){
			temp = b.getPosition(i, 0);
			streak = 0;
			if(temp != b.getNoPlayerChar()){
				for(int j = 0; j < 4; j++){
					if(b.getPosition(i, j) != temp && good){
						good = false;
					} else streak++;
					if(good && streak == 4){
						if(temp == b.getPlayer1Char()) return this.player1;
						else return player2;
					}
				}
			}
			good = true;
			temp = b.getPosition(i, 1);
			streak = 0;
			if(temp != b.getNoPlayerChar()){
				for(int j = 1; j < 1 + 4; j++){
					if(b.getPosition(i, j) != temp && good){
						good = false;
					} else streak++;
					if(good && streak == 4){
						if(temp == b.getPlayer1Char()) return this.player1;
						else return player2;
					}
				}
			}
			good = true;
		}
		
		// Check across each column
		for(int i = 0; i < b.getBoardSize(); i++){
			temp = b.getPosition(0, i);
			streak = 0;
			if(temp != b.getNoPlayerChar()){
				for(int j = 0; j < 4; j++){
					if(b.getPosition(j, i) != temp && good){
						good = false;
					} else streak++;
					if(good && streak == 4){
						if(temp == b.getPlayer1Char()) return this.player1;
						else return player2;
					}
				}
			}
			good = true;
			temp = b.getPosition(1, i);
			streak = 0;
			if(temp != b.getNoPlayerChar()){
				for(int j = 1; j < 1 + 4; j++){
					if(b.getPosition(j, i) != temp && good){
						good = false;
					} else streak++;
					if(good && streak == 4){
						if(temp == b.getPlayer1Char()) return this.player1;
						else return player2;
					}
				}
			}
			good = true;
		}
		
		
		// Check 8 diagonals
		//1
		temp = b.getPosition(0, 0);
		if(temp != b.getNoPlayerChar()){
			for(int i = 0; i < 4; i++){
				if(b.getPosition(i, i) != temp && good) good = false;
			}
			if(good){ // return the winning player
				if(temp == b.getPlayer1Char()) return this.player1;
				else return player2;
			}
			else good = true;
		}
		//2
		temp = b.getPosition(1, 1);
		if(temp != b.getNoPlayerChar()){
			for(int i = 1; i < 4+1; i++){
				if(b.getPosition(i, i) != temp && good) good = false;
			}
			if(good){ // return the winning player
				if(temp == b.getPlayer1Char()) return this.player1;
				else return player2;
			}
			else good = true;
		}
		//3
		int row = b.getBoardSize() - 1;
		temp = b.getPosition(row, 0);
		if(temp != b.getNoPlayerChar()){
			for(int i = 0; i < 4; i++){
				if(b.getPosition(row, i) != temp && good) good = false;
				row--;
			}
			if(good){ // return the winning player
				if(temp == b.getPlayer1Char()) return this.player1;
				else return player2;
			}
			else good = true;
		}
		//4
		row = b.getBoardSize() - 2;
		temp = b.getPosition(row, 0);
		if(temp != b.getNoPlayerChar()){
			for(int i = 1; i < 4 + 1; i++){
				if(b.getPosition(row, i) != temp && good) good = false;
				row--;
			}
			if(good){ // return the winning player
				if(temp == b.getPlayer1Char()) return this.player1;
				else return player2;
			} else good = true;
		}
		//5
		temp = b.getPosition(0, 1);
		if(temp != b.getNoPlayerChar()){
			for(int i = 0; i < 4; i++){
				if(b.getPosition(i, i+1) != temp && good) good = false;
			}
			if(good){ // return the winning player
				if(temp == b.getPlayer1Char()) return this.player1;
				else return player2;
			}
			else good = true;
		}
		//6
		temp = b.getPosition(1, 0);
		if(temp != b.getNoPlayerChar()){
			for(int i = 0; i < 4; i++){
				if(b.getPosition(1+i, i) != temp && good) good = false;
			}
			if(good){ // return the winning player
				if(temp == b.getPlayer1Char()) return this.player1;
				else return player2;
			}
			else good = true;
		}
		//7
		row = b.getBoardSize() - 1;
		temp = b.getPosition(row, 1);
		if(temp != b.getNoPlayerChar()){
			for(int i = 0; i < 4; i++){
				if(b.getPosition(row, i+1) != temp && good) good = false;
				row--;
			}
			if(good){ // return the winning player
				if(temp == b.getPlayer1Char()) return this.player1;
				else return player2;
			}
			else good = true;
		}
		//8
		row = b.getBoardSize() - 1;
		temp = b.getPosition(row-1, 0);
		if(temp != b.getNoPlayerChar()){
			for(int i = 0; i < 4; i++){
				if(b.getPosition(row-1, i) != temp && good) good = false;
				row--;
			}
			if(good){ // return the winning player
				if(temp == b.getPlayer1Char()) return this.player1;
				else return player2;
			}
			else good = true;
		}
		
		return null;
	}
	
	private Boolean isValidMove(int[] move, Board b){
		int size = b.getBoardSize() - 1;
		if(move[0] < 0 || move[0] > size || move[1] < 0 || move[1] > size) return false;
		else if(b.getPosition(move[0], move[1]) != b.getNoPlayerChar()) return false;
		else return true;
	}
	
}
