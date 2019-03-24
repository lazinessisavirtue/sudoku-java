package lazi.sudoku.deducer;

import java.util.List;

import lazi.sudoku.Position;
import lazi.sudoku.PositionLists;
import lazi.sudoku.PossibleValues;
import lazi.sudoku.board.Board;

public class OnlyValueForASquareDeducer extends Deducer {
    
    @Override
    public Board deduce(Board board) {
        PossibleValues[][] squares = board.getSquaresCopy();
        for (List<Position> group : PositionLists.groups()) {
            PossibleValues solvedOnes = PossibleValues.EMPTY;
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
        return new Board(squares);
    }
    
}
