package game;

public class Game {
    private final boolean log;
    private final Player player1, player2;

    public Game(final boolean log, final Player player1, final Player player2) {
        this.log = log;
        this.player1 = player1;
        this.player2 = player2;
    }

    public int play(Board board) {
        int steps = 1;
        while (true) {
            final int result1 = move(board, player1, 1, steps++);
            if (result1 != -1) {
                return result1;
            }
            final int result2 = move(board, player2, 2, steps++);
            if (result2 != -1) {
                return result2;
            }
        }
    }

    private int move(final Board board, final Player player, final int no, int step) {
        try {
            final Move move = player.move(board.getPosition(), board.getCell(), step);
            final Result result = board.makeMove(move);
            log("Player " + no + " move: " + move);
            log("Position:\n" + board);
            if (result == Result.WIN) {
                log("Player " + no + " won");
                return no;
            } else if (result == Result.LOSE) {
                log("Player " + no + " lose");
                return 3 - no;
            } else if (result == Result.DRAW) {
                log("Draw");
                return 0;
            } else {
                return -1;
            }
        } catch (RuntimeException e) {
            System.err.println("Что-то пошло не так. Методы игрока вызывают ошибку! " + e);
            return 3 - no;
        }
    }

    private void log(final String message) {
        if (log) {
            System.out.println(message);
        }
    }
}
