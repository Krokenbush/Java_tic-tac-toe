package game;

import java.util.Random;

public class RandomPlayer implements Player {
    private final Random random;
    public String name;
    public int place;
    public int n;
    public int m;

    public RandomPlayer(final Random random) {
        this.random = random;
    }

    public RandomPlayer(int n, int m) {
        this.n = n;
        this.m = m;
        this.random = new Random();
    }

    @Override
    public Move move(final Position position, final Cell cell, int step) {
        while (true) {
            int r = random.nextInt(n) + 1;
            int c = random.nextInt(m) + 1;
            final Move move = new Move(r, c, cell, step);
            if (position.isValid(move)) {
                return move;
            }
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
