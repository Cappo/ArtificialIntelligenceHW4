
public class Board {
	private int boardSize;
	private char player1Char;
	private char player2Char;
	private char noPlayerChar;
	private char[][] gameBoard;
	
	public Board(int size, char p1, char p2, char none){
		this.boardSize = size;
		this.player1Char = p1;
		this.player2Char = p2;
		this.noPlayerChar = none;
		this.gameBoard = new char[size][size];
		
		this.resetBoard();
	}
	
	public Board(Board gameBoard) {
		this.boardSize = gameBoard.getBoardSize();
		this.player1Char = gameBoard.getPlayer1Char();
		this.player2Char = gameBoard.getPlayer2Char();
		this.noPlayerChar = gameBoard.getNoPlayerChar();
		this.gameBoard = gameBoard.getGameBoard();
	}
	
	public void displayBoard(){
		for(int i = 0; i < boardSize; i++){
			System.out.print("\t"+i);
		}
		System.out.print("\n");
		for(int i = 0; i < boardSize; i++){
			System.out.print(i);
			for(int j = 0; j < boardSize; j++){
				System.out.print("\t" + this.gameBoard[i][j]);
			}
			System.out.println("");
			System.out.println("");
		}
	}
	
	public void playMove(int[] move, int player){
		if(player == 1) this.gameBoard[move[0]][move[1]] = this.player1Char;
		else this.gameBoard[move[0]][move[1]] = this.player2Char;
	}
	
	public void rescindMove(int[] move){
		this.gameBoard[move[0]][move[1]] = this.noPlayerChar;
	}
	
	public char[][] getGameBoard() {
		return this.gameBoard;
	}
	
	public void resetBoard(){
		for(int i = 0; i < boardSize; i++){
			for(int j = 0; j < boardSize; j++){
				this.gameBoard[i][j] = noPlayerChar;
			}
		}
	}
	
	public char getPlayer1Char(){
		return this.player1Char;
	}
	
	public char getPlayer2Char(){
		return this.player2Char;
	}
	
	public char getNoPlayerChar(){
		return this.noPlayerChar;
	}
	
	public int getBoardSize(){
		return this.boardSize;
	}
	
	public char getPosition(int po1, int po2){
		if(po1 > this.boardSize - 1 || po2 > this.boardSize - 1
			|| po1 < 0 || po2 < 0)
			return 0; // junk char
		else
			return this.gameBoard[po1][po2];
	}
	
	public Boolean isValidMove(int[] move){
		int size = this.getBoardSize() - 1;
		if(move[0] < 0 || move[0] > size || move[1] < 0 || move[1] > size) return false;
		else if(this.getPosition(move[0], move[1]) != this.getNoPlayerChar()) return false;
		else return true;
	}
}
