package lazi.sudoku.boardgenerator;

import lazi.sudoku.board.ImmutableBoard;
import lazi.sudoku.board.MutableBoard;
import lazi.sudoku.deducer.Deducer;
import lazi.sudoku.deducer.L1Deducer;

public class L1BoardGenerator extends DeducerBoardGenerator {
    
    private final Deducer deducer = new L1Deducer();
    
    @Override
    protected void deduceUntilStable(MutableBoard board) {
        ImmutableBoard prev = board.createImmutableCopy();
        deducer.deduce(board);
        while (!prev.matches(board)) {
            prev = board.createImmutableCopy();
            deducer.deduce(board);
        }
    }
    
}
