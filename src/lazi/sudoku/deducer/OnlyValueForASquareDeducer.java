package lazi.sudoku.deducer;

import java.util.List;

import lazi.sudoku.BoardPossibilities;
import lazi.sudoku.Position;
import lazi.sudoku.PositionLists;
import lazi.sudoku.SquarePossibilities;

public class OnlyValueForASquareDeducer extends Deducer {
    
    @Override
    public BoardPossibilities deduce(BoardPossibilities board) {
        SquarePossibilities[][] squares = board.getSquaresCopy();
        for (List<Position> group : PositionLists.groups()) {
            SquarePossibilities solvedOnes = SquarePossibilities.EMPTY;
            for (Position p : group) {
                if (board.getSquare(p).containsExactlyOne()) {
                    solvedOnes = solvedOnes.or(board.getSquare(p));
                }
            }
            for (Position p : group) {
                if (!board.getSquare(p).containsExactlyOne()) {
                    squares[p.getRow()][p.getCol()] = squares[p.getRow()][p.getCol()].substract(solvedOnes);
                }
            }
        }
        return new BoardPossibilities(squares);
    }
    
}
