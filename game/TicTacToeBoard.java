package game;

import java.util.Arrays;
import java.util.Map;


public class TicTacToeBoard implements Board {
    private int placesX = 0; 
    private int placesO = 0;
    private int mxPlaces = 0;
    private final int n; 
    private final int m;
    private final int k;
    private final int p;
    Position pos;

    private class PositionView implements Position {

        private Cell getCell() {
            return turn;
        }

        @Override
        public boolean isValid(final Move move) {
            boolean b1 = 0 < move.getRow() && move.getRow() <= n
                    && 0 < move.getColumn() && move.getColumn() <= m
                    && cells[move.getRow()][move.getColumn()] == Cell.E
                    && turn == getCell();
    
            
            if (move.getNowP() < p) {
                int a = -(n % 2) + 2 * (move.getNowP());
                int b = -(m % 2) + 2 * (move.getNowP());
                int n0 = (n - a) / 2 + 1;
                int m0 = (m - b) / 2 + 1;
                int n1 = a + n0 - 1;
                int m1 = b + m0 - 1;
    
                 if (move.getRow() < n0 || move.getRow() > n1 || move.getColumn() < m0 || move.getColumn() > m1) {
                     return false;
                 }
            }
            return b1;
        }
    
        @Override
        public Cell getCell(final int r, final int c) {
            return cells[r][c];
        }
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("  ");
            for (int i = 1; i <= m; ++i) {
                sb.append(i + " ");
            }
            for (int r = 1; r <= n; r++) {
                sb.append(System.lineSeparator());
                sb.append(r + " ");
                for (int c = 1; c <= m; c++) {
                    sb.append(SYMBOLS.get(cells[r][c]) + " ");
                }
            }
            return sb.toString();
        }

    }

    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.',
            Cell.N, '#'
    );

    private final Cell[][] cells;
    private Cell turn;

    // :NOTE: плохо, что вы допускаете n != m в случае ромба. Лучше завести отдельный конструктор для такого случая или даже класс
    public TicTacToeBoard(int n, int m, int k, int p, boolean rhomb) {
        this.n = n;
        this.m = m;
        this.k = k;
        this.p = p;
        this.cells = new Cell[n + 1][m + 1];

        pos = new PositionView();

        if (rhomb) {
            for (Cell[] row : cells) {
                Arrays.fill(row, Cell.N);
            }
            if (n % 2 == 0) {
                for (int i = 1; i <= n / 2; ++i) {
                    for (int j = n / 2 - i + 1; j <= n / 2 + i; ++j) {
                        cells[i][j] = Cell.E;
                        mxPlaces++;
                    }
                }
                for (int i = n / 2 + 1; i <= n; ++i) {
                    for (int j = n / 2 - (n - i); j <= n / 2 + (n - i) + 1; ++j) {
                        cells[i][j] = Cell.E;
                        mxPlaces++;
                    }
                }
            } else {
                for (int i = 1; i <= n / 2; ++i) {
                    for (int j = n / 2 - i + 2; j <= n / 2 + i; ++j) {
                        cells[i][j] = Cell.E;
                        mxPlaces++;
                    }
                }
                for (int i = n / 2 + 1; i <= n; ++i) {
                    for (int j = n / 2 - (n - i) + 1; j <= n / 2 + (n - i) + 1; ++j) {
                        cells[i][j] = Cell.E;
                        mxPlaces++;
                    }
                }
            }
        } else {
            for (Cell[] row : cells) {
                Arrays.fill(row, Cell.E);
            }
            mxPlaces = n * m;
        }
        turn = Cell.X;
    }

    @Override
    public Position getPosition() {
        return new PositionView();
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    @Override
    public Result makeMove(final Move move) {
        if (!pos.isValid(move)) {
            return Result.LOSE;
        }

        int x = move.getRow();
        int y = move.getColumn();
        cells[x][y] = move.getValue();

        if (move.getValue() == Cell.X) {
            placesX++;
        } else {
            placesO++;
        }

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                int cnt = 1;
                for (int i = x + dx, j = y + dy; (i > 0 && i <= n) && (j > 0 && j <= m); i += dx, j += dy) {
                    if (cnt == k) break;
                    if (cells[i][j] != turn) break;
                    cnt++;
                }
                for (int i = x - dx, j = y - dy; (i > 0 && i <= n) && (j > 0 && j <= m); i -= dx, j -= dy) {
                    if (cnt == k) break;
                    if (cells[i][j] != turn) break;
                    cnt++;
                }
                if (cnt == k) {
                    return Result.WIN;
                }
            }
        }

        if (placesX + placesO == mxPlaces) {
            return Result.DRAW;
        }

        turn = turn == Cell.X ? Cell.O : Cell.X;
        return Result.UNKNOWN;
    }
}
