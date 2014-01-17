import java.util.InputMismatchException;
import java.util.Scanner;
public class Mancala {
    private enum Player {
        One ((board.length - 2) / 2, board.length - 1),
        Two (board.length - 1, (board.length - 2) / 2);
        private int kalahLocation;
        private int kalahSkip;
        Player(int location, int skip) {
        	kalahLocation = location;
        	kalahSkip = skip;
        }
        int getKalah() {
        	return board[kalahLocation];
        }
        int getKalahLoc() {
        	return kalahLocation;
        }
        int getSkip() {
        	return kalahSkip;
        }
        
    }
    private static int[] board;
    private Player turn;
    private final int STARTING_AMOUNT = 4;
    private boolean computer;
 
    public static void main(String[] args) throws InterruptedException {
    	Mancala game = new Mancala();
//    	BasicGui gui = new BasicGui();
//        gui.launchFrame();
    	System.out.println("  -----------------------");
    	System.out.println("  --------Mancala--------");
    	System.out.println("  -----------------------");
    	System.out.println("  ----By-Jeffrey-Pyke----");
    	System.out.println();
    	boolean playAgain = true;
    	while (playAgain) {
    	   	Scanner s = new Scanner(System.in);
        	System.out.print("Enter 1 to play against the computer, 2 to play against a human (1 or 2):");
        	if (s.nextLine().contains("1")) {
        		game.computer = true;
        	}
    		game.reset();
        	game.printBoard();
	        while (!game.isOver()) { //game play
	        	boolean again = true;
		        while (again) {
			        int position = 0;
		        	if (game.computer && game.getTurn() == Player.Two) {
			        	System.out.println("The computer is thinking...");
				        Thread.sleep(1800);
				        position = game.choose();
			        } else {
			        	System.out.print("Player "+game.getTurn()+", choose which pile to take (1-6):");
			        	position = game.readValue();
			        }
		        	again = game.markBoard(position);
		        }
			    game.switchTurn();
	        }
	        System.out.println("The game is over! Press enter to count the pieces.");
	        s.nextLine();
	        Player winner = game.getWinner();
	        game.printBoard();
	        game.printWin(winner);
	        System.out.println("\nPlay Again? (Y/N)");
	        if (s.nextLine().equalsIgnoreCase("n")) {
	        	playAgain = false;
	        }
    	}
    }    
    public Mancala() {
    	board = new int[14];
    	turn = Player.One;
    }
    public Player getTurn() {
    	return turn;
    }
    
    public int choose() {
    	boolean valid = false;
    	int location = 0;
    	while (!valid) {
        	location = (board.length / 2 ) + (int)(Math.random() *
        			((board.length - 2 - (board.length / 2)) + 1));
        	if (board[location] != 0) {
        		valid = true;
        	}
    	}
    	return location;
    }
    
    public void switchTurn() {
    	if (turn == Player.One) {
    		turn = Player.Two;
    	} else {
    		turn = Player.One;
    	}
    }
    public void printWin(Player winner) {
    	if (winner == Player.One) {
        	if (computer) {
        		System.out.println("Congratulations, you win!");
        	} else {
        		System.out.println("Player One WINS!");
        	}
        } else if (winner == Player.Two) {
        	if (computer) {
        		System.out.println("The computer beat you!");
        	} else {
	        	System.out.println("Player Two WINS!");
        	}
        } else {
        	System.out.println("The game is a tie!");
        }
    }
    
    public void reset() {
    	for (int i = 0; i < board.length; i++) {
    		board[i] = STARTING_AMOUNT;
		}
    	for (Player p : Player.values()) {
    		board[p.getKalahLoc()] = 0;
    	}
    	turn = Player.One;
    }
    
    public void printBoard() {
    	if (!computer) {
        	System.out.println("    (1) (2) (3) (4) (5) (6) ");
    	}
    	System.out.println("-------------------------------");
    	System.out.print("|  ");
    	for (int i = board.length - 2; i >= board.length / 2; i--) {
    		System.out.print("| ");
    		System.out.printf("%-2s",board[i]);
    	}
    	System.out.print("|  |\n|");
    	System.out.printf("%-2d|-----------------------|%2d|\n", Player.Two.getKalah(), Player.One.getKalah());
    	System.out.print("|  ");
    	for (int i = 0; i < (board.length / 2) - 1; i++) {
    		System.out.print("| ");
    		System.out.printf("%-2s",board[i]);
    	}
    	System.out.println("|  |");
    	System.out.println("-------------------------------");
    	System.out.println("    (1) (2) (3) (4) (5) (6) ");
    }
    
    //returns true if the game is over (one side has no pieces)
    public boolean isOver() {
    	return sum(Player.One) == 0 || sum(Player.Two) == 0;
    }
    
    //checks if a player has won
    //returns null if the game isn't over or the game is a tie
    public Player getWinner() { 
    	Player winner = null;
    	if (isOver()) {
    		for (Player p : Player.values()) {
        		board[p.getKalahLoc()] += sum(p);
        	}
			int totalOne = Player.One.getKalah();
			int totalTwo = Player.Two.getKalah();
	    	if (totalOne > totalTwo) {
	    		winner = Player.One;
	    	} else if (totalOne < totalTwo) {
	    		winner = Player.Two;
	    	}
			for (int i = 0; i < board.length; i++) {
	    		if (i != Player.One.getKalahLoc() && i != Player.Two.getKalahLoc()) {
					board[i] = 0;
	    		}
			}
    	}
    	return winner;
    }
    
    public int sum(Player m) {
    	int sum = 0;
    	int start = (m.getSkip() + 1) % board.length;
    	for (int i = start; i < start + (board.length - 1) / 2; i++) {
    		sum += board[i];
    	}
    	return sum;
    }
    
    //carries out a move for the player of the current turn
    public boolean markBoard(int pos) { 
		int handAmount = board[pos];
		board[pos] = 0;
		while (handAmount > 0) {
			pos = (pos + 1) % board.length;
			handAmount--;
			if (pos == turn.getSkip()) {
				pos = (pos + 1) % board.length;	
			} 
			board[pos]++;
		}
		boolean taken = false;
		if (pos != turn.getKalahLoc() && board[pos] == 1 && board[board.length - 2 - pos] != 0) {
				board[turn.getKalahLoc()] += board[pos] + board[board.length - 2 - pos];
				board[pos] = 0;
				board[board.length - 2 - pos] = 0;
				taken = true;
		}
		printBoard();
		if (taken) {
			System.out.println("Pieces taken!");
		} else if (!isOver() && pos == turn.getKalahLoc()) {
			System.out.println("Go again player "+turn+". You landed in the Kalah.");
			return true;
		}
		return false;
    }
    
    //reads a value from a scanner in the console
    public int readValue() {
    	Scanner s = new Scanner(System.in); 
    	int position = 2;
    	boolean valid = false;
        while (!valid) {
     		try {
         		position = s.nextInt();
         		if (position < 1 || position > 6) {
         			System.out.println("Invalid Position, input again:");
         		} else {
         			if (turn == Player.One) {
         				position--;
         			} else {
         				position = board.length - 1 - position;
         			}
         			if (board[position] == 0) {
         				System.out.print("Spot is empty. Choose another spot:");
	         		} else {
	         			valid = true;
	         		}
         		}
     		}
     		catch (InputMismatchException e) {
     			System.out.println("Invalid Position, input again:");
     		}
         }
         return position;
    }

}