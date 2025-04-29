package game;

public class SequentialPlayer implements Player {
    private String name;
    private int place;
    private int n;
    private int m;

    SequentialPlayer (int n, int m) {
        this.n = n;    
        this.m = m;    
    }

    @Override
    public Move move(final Position position, final Cell cell, int step) {
        for (int r = 1; r <= n; r++) {
            for (int c = 1; c <= m; c++) {
                final Move move = new Move(r, c, cell, step);
                if (position.isValid(move)) {
                    return move;
                }
            }
        }
        throw new IllegalStateException("No valid moves");
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
