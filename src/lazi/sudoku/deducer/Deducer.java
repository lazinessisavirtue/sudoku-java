package lazi.sudoku.deducer;

import lazi.sudoku.board.MutableBoard;

public abstract class Deducer {
    
    public abstract boolean deduce(MutableBoard board);
    
}
