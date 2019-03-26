package lazi.sudoku.board;

import lazi.sudoku.PossibleValues;

public final class ImmutableBoard extends Board {
    
    ImmutableBoard(PossibleValues[] squares) {
        super(squares);
    }
    ImmutableBoard(PossibleValues square) {
        super(square);
    }

    @Override
    public ImmutableBoard createImmutableCopy() {
        return this;
    }
    @Override
    public MutableBoard createMutableCopy() {
        return new MutableBoard(squares);
    }
    
}
