package lazi.sudoku.deducer;

import java.util.ArrayList;
import java.util.List;

import lazi.sudoku.Position;
import lazi.sudoku.PositionLists;
import lazi.sudoku.PossibleValues;
import lazi.sudoku.board.ImmutableBoard;
import lazi.sudoku.board.MutableBoard;

public class MultipleSquareForAValueDeducer extends Deducer {
    
    private final L1Deducer l1Deducer = new L1Deducer();
    private final int multiple;
    private final int forwardSteps;
    
    public MultipleSquareForAValueDeducer(int multiple, int forwardSteps) {
        this.multiple = multiple;
        this.forwardSteps = forwardSteps;
    }
    
    @Override
    public boolean deduce(MutableBoard board) {
        ImmutableBoard before = board.createImmutableCopy();
        for (int i = 1; i < 9; i++) {
            for (List<Position> group : PositionLists.groups()) {
                List<Position> ps = new ArrayList<>();
                for (Position p : group) {
                    if (board.getSquare(p).contains(i)) {
                        ps.add(p);
                    }
                }
                if (ps.size() != multiple) {
                    continue;
                }
                MutableBoard or = MutableBoard.empty();
                for (Position p : ps) {
                    MutableBoard hypothesis = board.createMutableCopy();
                    hypothesis.setSquare(p, PossibleValues.only(i));
                    checkHypothesis(hypothesis);
                    or.or(hypothesis);
                }
                board.and(or);
            }
        }
        return !board.matches(before);
    }

    private void checkHypothesis(MutableBoard board) {
        for (int i = 0; i < forwardSteps; i ++) {
            l1Deducer.deduce(board);
        }
    }
    
}
