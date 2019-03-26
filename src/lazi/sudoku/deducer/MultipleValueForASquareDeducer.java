package lazi.sudoku.deducer;

import lazi.sudoku.Position;
import lazi.sudoku.PositionLists;
import lazi.sudoku.PossibleValues;
import lazi.sudoku.board.ImmutableBoard;
import lazi.sudoku.board.MutableBoard;

public class MultipleValueForASquareDeducer extends Deducer {
    
    private final L1Deducer l1Deducer = new L1Deducer();
    private final int multiple;
    private final int forwardSteps;
    
    public MultipleValueForASquareDeducer(int multiple, int forwardSteps) {
        this.multiple = multiple;
        this.forwardSteps = forwardSteps;
    }
    
    @Override
    public boolean deduce(MutableBoard board) {
        ImmutableBoard before = board.createImmutableCopy();
        for (Position p : PositionLists.all()) {
            if (board.getSquare(p).size() != multiple) {
                continue;
            }
            MutableBoard or = MutableBoard.empty();
            PossibleValues remaining = board.getSquare(p);
            while (!remaining.isEmpty()) {
                int value = remaining.smallest();
                remaining = remaining.remove(value);
                MutableBoard hypothesis = board.createMutableCopy();
                hypothesis.setSquare(p, PossibleValues.only(value));
                checkHypothesis(hypothesis);
                or.or(hypothesis);
            }
            board.and(or);
        }
        return !board.matches(before);
    }
    
    private void checkHypothesis(MutableBoard board) {
        for (int i = 0; i < forwardSteps; i ++) {
            l1Deducer.deduce(board);
        }
    }
    
}
