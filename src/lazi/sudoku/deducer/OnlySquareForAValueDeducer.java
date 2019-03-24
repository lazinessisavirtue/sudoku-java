package lazi.sudoku.deducer;

import java.util.List;

import lazi.sudoku.BoardPossibilities;
import lazi.sudoku.Position;
import lazi.sudoku.PositionLists;
import lazi.sudoku.SquarePossibilities;

public class OnlySquareForAValueDeducer extends Deducer {
    
    @Override
    public BoardPossibilities deduce(BoardPossibilities board) {
        SquarePossibilities[][] squares = board.getSquaresCopy();
        for (List<Position> group : PositionLists.groups()) {
            SquarePossibilities seenOne = SquarePossibilities.EMPTY;
            SquarePossibilities seenTwoPlus = SquarePossibilities.EMPTY;
            for (Position p : group) {
                seenTwoPlus = seenTwoPlus.or(seenOne.and(board.getSquare(p)));
                seenOne = seenOne.or(board.getSquare(p));
                
            }
            SquarePossibilities seenExactlyOne = seenOne.substract(seenTwoPlus);
            for (Position p : group) {
                if (board.getSquare(p).and(seenExactlyOne) != SquarePossibilities.EMPTY) {
                    squares[p.getRow()][p.getCol()] = board.getSquare(p).and(seenExactlyOne);
                }
            }
        }
        return new BoardPossibilities(squares);
    }
    
}
