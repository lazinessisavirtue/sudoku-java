package lazi.sudoku.boardgenerator;

import java.util.Arrays;

import lazi.sudoku.board.ImmutableBoard;
import lazi.sudoku.board.MutableBoard;
import lazi.sudoku.deducer.Deducer;

public class MultipleDeducerBoardGenerator extends DeducerBoardGenerator {
    
    private final Deducer[] deducers;
    
    public MultipleDeducerBoardGenerator(Deducer[] deducers) {
        this.deducers = Arrays.copyOf(deducers, deducers.length);
    }
    
    @Override
    protected void deduceUntilStable(MutableBoard board) {
        for (int i = 0; i < deducers.length; ) {
            ImmutableBoard prev = board.createImmutableCopy();
            deducers[i].deduce(board);
            if (prev.matches(board)) {
                i++;
            } else {
                i = 0;
            }
        }
    }
    
}
