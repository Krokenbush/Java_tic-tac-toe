package game;

public class Main {
    public static void main(String[] args) {
        final Game game = new Game(false, new HumanPlayer(), new HumanPlayer());
        int result;
        do {
            result = game.play(new TicTacToeBoard(3 , 3, 3, 0, true));
            System.out.println("Game result: " + result);
        } while (result != 0);
    }
}
