package lazi.sudoku.board;

import lazi.sudoku.Position;
import lazi.sudoku.PositionLists;
import lazi.sudoku.PossibleValues;

public final class MutableBoard extends Board {
    
    MutableBoard(PossibleValues[] squares) {
        super(squares);
    }
    MutableBoard(PossibleValues square) {
        super(square);
    }

    @Override
    public ImmutableBoard createImmutableCopy() {
        return new ImmutableBoard(squares);
    }
    @Override
    public MutableBoard createMutableCopy() {
        return new MutableBoard(squares);
    }
    
    public void setSquare(Position p, PossibleValues square) {
        squares[p.getIndex()] = square;
    }
    public void hideSquare(Position p) {
        setSquare(p, PossibleValues.FULL);
    }
    
    public void and(Board other) {
        for (Position p : PositionLists.all()) {
            squares[p.getIndex()] = getSquare(p).and(other.getSquare(p));
        }
    }
    public void or(Board other) {
        for (Position p : PositionLists.all()) {
            squares[p.getIndex()] = getSquare(p).or(other.getSquare(p));
        }
    }
    
    public static MutableBoard empty() {
        return new MutableBoard(PossibleValues.EMPTY);
    }
    public static MutableBoard full() {
        return new MutableBoard(PossibleValues.FULL);
    }
    
}
