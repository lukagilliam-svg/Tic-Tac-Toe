package tictactoe;

public enum Mark {
    EMPTY,
    X,
    O;

    public Mark other() {
        return this == X ? O : X;
    }
}
