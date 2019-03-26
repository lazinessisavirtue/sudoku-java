package lazi.sudoku.deducer;

import lazi.sudoku.board.MutableBoard;

public class L1Deducer extends Deducer {
    
    private final OnlyValueForASquareDeducer onlyValueForASquareDeducer = new OnlyValueForASquareDeducer();
    private final OnlySquareForAValueDeducer onlySquareForAValueDeducer = new OnlySquareForAValueDeducer();
    @Override
    public boolean deduce(MutableBoard board) {
        boolean changed = false;
        changed = changed || onlyValueForASquareDeducer.deduce(board);
        changed = changed || onlySquareForAValueDeducer.deduce(board);
        return changed;
    }
    
}
