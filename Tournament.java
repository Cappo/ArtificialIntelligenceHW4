
import java.io.OutputStream;
import java.io.PrintStream;


public class Tournament {
    
    Player p1, p2;
    PrintStream nullStream, out; //used to hide output
    
    public Tournament () {
        //The following is used to hide the output during tournament play
        nullStream = new PrintStream(new OutputStream(){
            @Override
            public void write(int b) {
                //NO-OP
            }
        });
        out = System.out;
    };
    
    public void runTests() {
        
        System.out.println("\nBeginning tournament...");
        
        //Beginner vs. Advanced
        p1 = new Beginner('X', 1);
        p2 = new Advanced('O', 2, 2);
        play50Times(p1,p2);
        
        //Advanced vs. Beginner
        p1 = new Advanced('X', 1, 2);
        p2 = new Beginner('O', 2);
        play50Times(p1,p2);
        
        //Beginner vs. Master
        p1 = new Beginner('X', 1);
        p2 = new Master('O', 2);
        play50Times(p1,p2);
        
        //Master vs. Beginner
        p1 = new Master('X', 1);
        p2 = new Beginner('O', 2);
        play50Times(p1,p2);
        
        //Advanced vs. Master
        p1 = new Advanced('X', 1, 2);
        p2 = new Master('O', 2);
        play50Times(p1,p2);
        
        //Master vs. Advanced
        p1 = new Master('X', 1);
        p2 = new Advanced('O', 2, 2);
        play50Times(p1,p2);
        
        System.out.println("");
    }
    
    public void play50Times(Player play1, Player play2) {
        
        //get names of players
        String p1Type = play1.getClass().toString().split(" ", 2)[1];
        String p2Type = play2.getClass().toString().split(" ", 2)[1];
        
        //keep track of total wins for each player
        int wins1=0, wins2=0;

        //run through 50 games
        System.out.println("\nRunning 50 tests for " + p1Type + " vs " + p2Type + ". Please be patient...");
        System.setOut(nullStream);
        long start = System.nanoTime();
        for (int i=0; i<50; i++){
            Game game = new Game(p1, p2);
            Player winner = game.playGame();
            if (winner == play1) wins1 += 1;
            else if (winner == play2) wins2 += 1;
        }
        long end = System.nanoTime();
        System.setOut(out);
        
        //output results
        System.out.println(p1Type + " Player won " +  wins1*2 + "% of the matches.");
        System.out.println(p2Type + " Player won " +  wins2*2 + "% of the matches.");
        System.out.println("Average game time: " + ((end - start)/ 50000000.00) + " ms"); //rough calculation
    }
    
}
