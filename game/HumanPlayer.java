package game;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;

    // :NOTE: 2. скорее всего, это относится только к турниру. Потому что, не очень понятно, что такое место при игре двух игроков.
    private String name;
    private int place;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    public HumanPlayer() {
        this(System.out, new Scanner(System.in));
    }

    @Override
    public Move move(final Position position, final Cell cell, int step) {
        while (true) {
            out.println("Position");
            out.println(position);
            out.println(cell + "'s move");
            out.println("Enter row and column");
            int x = 0;
            int y = 0;

            while (true) {
                try {
                    x = in.nextInt();
                    y = in.nextInt();
                    in.nextLine();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Введите пожалуйста корректные координаты.");
                    in.nextLine();
                }
            }

            final Move move = new Move(x, y, cell, step);
            if (position.isValid(move)) {
                return move;
            }
            out.println("Move " + move + " is invalid");
        }
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setPlace(int place) {
        this.place = place;
    }

    @Override
    public int getPlace() {
        return place;
    }
}
