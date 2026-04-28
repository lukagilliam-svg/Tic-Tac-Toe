package tictactoe;

import java.util.Arrays;
import java.util.Optional;

public final class Board {
    public static final int SIZE = 3;
    private static final int[][] LINES = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };

    private final Mark[] cells = new Mark[SIZE * SIZE];

    public Board() {
        Arrays.fill(cells, Mark.EMPTY);
    }

    public Mark get(int index) {
        return cells[index];
    }

    public boolean isEmpty(int index) {
        return cells[index] == Mark.EMPTY;
    }

    public void place(int index, Mark mark) {
        if (!isEmpty(index)) {
            throw new IllegalStateException("Cell already taken: " + index);
        }
        cells[index] = mark;
    }

    public void clear() {
        Arrays.fill(cells, Mark.EMPTY);
    }

    public boolean isFull() {
        for (Mark c : cells) {
            if (c == Mark.EMPTY) {
                return false;
            }
        }
        return true;
    }

    public Optional<Mark> winner() {
        for (int[] line : LINES) {
            Mark a = cells[line[0]];
            Mark b = cells[line[1]];
            Mark c = cells[line[2]];
            if (a != Mark.EMPTY && a == b && b == c) {
                return Optional.of(a);
            }
        }
        return Optional.empty();
    }
}
