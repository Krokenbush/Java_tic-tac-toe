package game;

public final class Move {
    private final int row;
    private final int column;
    private final Cell value;
    private final int nowP;

    public Move(final int row, final int column, final Cell value, int nowP) {
        this.row = row;
        this.column = column;
        this.value = value;
        this.nowP = nowP;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Cell getValue() {
        return value;
    }

    public int getNowP() {
        return nowP;
    }

    @Override
    public String toString() {
        return "row=" + row + ", column=" + column + ", value=" + value;
    }
}
