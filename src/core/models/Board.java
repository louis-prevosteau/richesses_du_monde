package core.models;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final List<ISquare> squares;
    private static final int BOARD_SIZE = 65;

    public Board() {
        this.squares = new ArrayList<>(BOARD_SIZE);
        for (int i = 0; i < BOARD_SIZE; i++) squares.add(null);
    }

    public List<ISquare> getSquares() {
        return squares;
    }

    public ISquare getSquare(int position) {
        return squares.get(position);
    }

    public void setSquare(int position, ISquare square) {
        squares.set(position, square);
    }

    public int getSize() {
        return BOARD_SIZE;
    }
}
