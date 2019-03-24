package lazi.sudoku.deducer;

import lazi.sudoku.Position;
import lazi.sudoku.PositionLists;
import lazi.sudoku.PossibleValues;
import lazi.sudoku.board.Board;

public class MultipleValueForASquareDeducer extends Deducer {
    
    private final L1Deducer l1Deducer = new L1Deducer();
    private final int multiple;
    private final int forwardSteps;
    
    public MultipleValueForASquareDeducer(int multiple, int forwardSteps) {
        this.multiple = multiple;
        this.forwardSteps = forwardSteps;
    }

    @Override
    public Board deduce(Board board) {
        Board result = board;
        for (Position p : PositionLists.all()) {
            if (board.getSquare(p).size() != multiple) {
                continue;
            }
            Board or = new Board(Board.createEmptySquares());
            PossibleValues remaining = board.getSquare(p);
            while (!remaining.isEmpty()) {
                int value = remaining.smallest();
                remaining = remaining.remove(value);
                Board hypothesis = checkHypothesis(
                        board.setSquare(p, PossibleValues.only(value)));
                or = or.or(hypothesis);
            }
            result= result.and(or);
        }
        return result;
    }

    private Board checkHypothesis(Board board) {
        for (int i = 0; i < forwardSteps; i ++) {
            board = l1Deducer.deduce(board);
        }
        return board;
    }
    
}
