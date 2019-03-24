package lazi.sudoku.deducer;

import lazi.sudoku.board.Board;

public class L1Deducer extends Deducer {
    
    private final OnlyValueForASquareDeducer onlyValueForASquareDeducer = new OnlyValueForASquareDeducer();
    private final OnlySquareForAValueDeducer onlySquareForAValueDeducer = new OnlySquareForAValueDeducer();
    @Override
    public Board deduce(Board board) {
        board = onlyValueForASquareDeducer.deduce(board);
        board = onlySquareForAValueDeducer.deduce(board);
        return board;
    }
    
}
