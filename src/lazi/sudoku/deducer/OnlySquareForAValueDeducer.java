package lazi.sudoku.deducer;

import java.util.List;

import lazi.sudoku.Position;
import lazi.sudoku.PositionLists;
import lazi.sudoku.PossibleValues;
import lazi.sudoku.board.Board;

public class OnlySquareForAValueDeducer extends Deducer {
    
    @Override
    public Board deduce(Board board) {
        PossibleValues[][] squares = board.getSquaresCopy();
        for (List<Position> group : PositionLists.groups()) {
            PossibleValues seenOne = PossibleValues.EMPTY;
            PossibleValues seenTwoPlus = PossibleValues.EMPTY;
            for (Position p : group) {
                seenTwoPlus = seenTwoPlus.or(seenOne.and(board.getSquare(p)));
                seenOne = seenOne.or(board.getSquare(p));
                
            }
            PossibleValues seenExactlyOne = seenOne.substract(seenTwoPlus);
            for (Position p : group) {
                if (board.getSquare(p).and(seenExactlyOne) != PossibleValues.EMPTY) {
                    squares[p.getRow()][p.getCol()] = board.getSquare(p).and(seenExactlyOne);
                }
            }
        }
        return new Board(squares);
    }
    
}
