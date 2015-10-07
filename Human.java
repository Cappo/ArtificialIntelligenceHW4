import java.util.Scanner;

public class Human extends Player {
	
	Human(char c, int iam){
		super(c, iam);
	}
	
	Scanner scanner = new Scanner(System.in);
	
	@Override
	public int[] getMove(Board gameBoard){
		
		gameBoard.displayBoard();
		int[] move = {-1,-1};
		
		System.out.println("Which row? ");
		while(move[0] < 0 || (move[0] > gameBoard.getBoardSize() - 1)){
			while(!scanner.hasNextInt()) {
			    scanner.next();
			    System.out.println("Enter a number between 0 and " + (gameBoard.getBoardSize() - 1));
			}
			move[0] = scanner.nextInt();
			if(move[0] < 0 || (move[0] > gameBoard.getBoardSize() - 1))
				System.out.println("Enter a number between 0 and " + (gameBoard.getBoardSize() - 1));
		}
		System.out.println("Which column? ");
		while(move[1] < 0 || (move[1] > gameBoard.getBoardSize() - 1)){
			while(!scanner.hasNextInt()) {
			    scanner.next();
			    System.out.println("Enter a number between 0 and " + (gameBoard.getBoardSize() - 1));
			}
			move[1] = scanner.nextInt();
			if(move[1] < 0 || (move[1] > gameBoard.getBoardSize() - 1))
				System.out.println("Enter a number between 0 and " + (gameBoard.getBoardSize() - 1));
		}
		
		return move;
	}
}
