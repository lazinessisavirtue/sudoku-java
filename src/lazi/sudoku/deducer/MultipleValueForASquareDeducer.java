package lazi.sudoku.deducer;

import lazi.sudoku.BoardPossibilities;
import lazi.sudoku.Position;
import lazi.sudoku.PositionLists;
import lazi.sudoku.SquarePossibilities;

public class MultipleValueForASquareDeducer extends Deducer {
    
    private final L1Deducer l1Deducer = new L1Deducer();
    private final int multiple;
    private final int forwardSteps;
    
    public MultipleValueForASquareDeducer(int multiple, int forwardSteps) {
        this.multiple = multiple;
        this.forwardSteps = forwardSteps;
    }

    @Override
    public BoardPossibilities deduce(BoardPossibilities board) {
        BoardPossibilities result = board;
        for (Position p : PositionLists.all()) {
            if (board.getSquare(p).size() != multiple) {
                continue;
            }
            BoardPossibilities or = new BoardPossibilities(BoardPossibilities.createEmptySquares());
            SquarePossibilities remaining = board.getSquare(p);
            while (!remaining.isEmpty()) {
                int value = remaining.smallest();
                remaining = remaining.remove(value);
                BoardPossibilities hypothesis = checkHypothesis(
                        board.setSquare(p, SquarePossibilities.only(value)));
                or = or.or(hypothesis);
            }
            result= result.and(or);
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
