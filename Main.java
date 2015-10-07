import java.util.Scanner;

public class Main {
	
	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
		Boolean exit = false;
		String input;
		while(!exit){
			System.out.print("Artificial Intelligence HW4:\n\nType 'play' to start a game\nType 'tournament' to run AI statistics\nType 'quit' to exit\n\nInput: ");
			input = scanner.nextLine();
			if (new String("quit").equals(input)) exit = true;
			else if (new String("play").equals(input)){
				System.out.print("\nNew game:\n\nSelect player 1:\n1 - Human\n2 - Beginner AI\n3 - Advanced AI\nInput: ");
				Player p1, p2;
				int playerSelection = 0;
				while(playerSelection < 1 || playerSelection > 3){
					while(!scanner.hasNextInt()) {
					    scanner.next();
					    System.out.println("Enter 1 - 3: ");
					}
					playerSelection = scanner.nextInt();
					if(playerSelection < 1 || playerSelection > 3)
						System.out.println("Enter 1 - 3: ");
				}
				switch (playerSelection){
				case 1: p1 = new Human('X', 1); break;
				case 2: p1 = new Beginner('X', 1); break;
				case 3: p1 = new Advanced('X', 1, 2); break;
				default: p1 = new Human('X', 1); break;
				}
				System.out.print("\nSelect player 2:\n1 - Human\n2 - Beginner AI\n3 - Advanced AI\nInput: ");
				playerSelection = 0;
				while(playerSelection < 1 || playerSelection > 3){
					while(!scanner.hasNextInt()) {
					    scanner.next();
					    System.out.println("Enter 1 - 3: ");
					}
					playerSelection = scanner.nextInt();
					if(playerSelection < 1 || playerSelection > 3)
						System.out.println("Enter 1 - 3: ");
				}
				switch (playerSelection){
				case 1: p2 = new Human('O', 2); break;
				case 2: p2 = new Beginner('O', 2); break;
				case 3: p2 = new Advanced('O', 1, 2); break;
				default: p2 = new Human('O', 2); break;
				}
				Game game = new Game(p1, p2);
				Player winner = game.playGame();
				input = scanner.nextLine(); // Get some junk that gets left over and menu only displays once after game
			} else if (new String("tournament").equals(input)){}
		}
			
		System.out.println("\nThanks for playing!");
		System.exit(0);
	}
}
