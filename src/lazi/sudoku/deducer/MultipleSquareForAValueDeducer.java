package lazi.sudoku.deducer;

import java.util.ArrayList;
import java.util.List;

import lazi.sudoku.BoardPossibilities;
import lazi.sudoku.Position;
import lazi.sudoku.PositionLists;
import lazi.sudoku.SquarePossibilities;

public class MultipleSquareForAValueDeducer extends Deducer {
    
    private final L1Deducer l1Deducer = new L1Deducer();
    private final int multiple;
    private final int forwardSteps;
    
    public MultipleSquareForAValueDeducer(int multiple, int forwardSteps) {
        this.multiple = multiple;
        this.forwardSteps = forwardSteps;
    }
    
    @Override
    public BoardPossibilities deduce(BoardPossibilities board) {
        BoardPossibilities result = board;
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
                
                BoardPossibilities or = new BoardPossibilities(BoardPossibilities.createEmptySquares());
                for (Position p : ps) {
                    BoardPossibilities hypothesis = checkHypothesis(
                            board.setSquare(p, SquarePossibilities.only(i)));
                    or = or.or(hypothesis);
                }
                result= result.and(or);
            }
        }
        return result;
    }

    private BoardPossibilities checkHypothesis(BoardPossibilities board) {
        for (int i = 0; i < forwardSteps; i ++) {
            board = l1Deducer.deduce(board);
        }
        return board;
    }
    
}
