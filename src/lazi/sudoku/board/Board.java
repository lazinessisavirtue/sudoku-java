package lazi.sudoku.board;

import java.util.Arrays;
import java.util.List;

import lazi.sudoku.Position;
import lazi.sudoku.PositionLists;
import lazi.sudoku.PossibleValues;

abstract class Board {
    
    final PossibleValues[] squares;
    
    Board(PossibleValues[] squares) {
        this.squares = Arrays.copyOf(squares, 9 * 9);
    }
    Board(PossibleValues square) {
        this.squares = new PossibleValues[9 * 9];
        Arrays.fill(squares, square);
    }
    
    public abstract ImmutableBoard createImmutableCopy();
    public abstract MutableBoard createMutableCopy();
    
    public PossibleValues getSquare(Position position) {
        return squares[position.getIndex()];
    }
    
    public boolean matches(Board other) {
        if (other == null) return false;
        if (other == this) return true;
        for (Position p : PositionLists.all()) {
            if (this.getSquare(p) != other.getSquare(p)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isSolution() {
        for (Position p : PositionLists.all()) {
            if (!getSquare(p).containsExactlyOne()) {
                return false;
            }
        }
        for (List<Position> group : PositionLists.groups()) {
            PossibleValues possibility = PossibleValues.EMPTY;
            for (Position p : group) {
                possibility = possibility.or(getSquare(p));
            }
            if (possibility != PossibleValues.FULL) {
                return false;
            }
        }
        return true;
    }
    public boolean isContradiction() {
        for (Position p : PositionLists.all()) {
            if (getSquare(p).isEmpty()) {
                return true;
            }
        }
        for (List<Position> group : PositionLists.groups()) {
            PossibleValues possibility = PossibleValues.EMPTY;
            for (Position p : group) {
                possibility = possibility.or(getSquare(p));
            }
            if (possibility != PossibleValues.FULL) {
                return true;
            }
        }
        return false;
    }
    
}
