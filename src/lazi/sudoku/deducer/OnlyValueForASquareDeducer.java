package lazi.sudoku.deducer;

import java.util.Arrays;
import java.util.List;

import lazi.sudoku.Position;
import lazi.sudoku.PositionLists;
import lazi.sudoku.PossibleValues;
import lazi.sudoku.board.MutableBoard;

public class OnlyValueForASquareDeducer extends Deducer {
    
    @Override
    public boolean deduce(MutableBoard board) {
        boolean changed = false;
        List<List<Position>> groups = PositionLists.groups();
        PossibleValues[] solvedOnes = new PossibleValues[groups.size()];
        Arrays.fill(solvedOnes, PossibleValues.EMPTY);
        for (int i = 0; i < groups.size(); i++) {
            for (Position p : groups.get(i)) {
                if (board.getSquare(p).containsExactlyOne()) {
                    solvedOnes[i] = solvedOnes[i].or(board.getSquare(p));
                }
            }
        }
        for (int i = 0; i < groups.size(); i++) {
            if (solvedOnes[i] == PossibleValues.EMPTY) {
                continue;
            }
            for (Position p : groups.get(i)) {
                if (!board.getSquare(p).containsExactlyOne()) {
                    PossibleValues newSquare = board.getSquare(p).substract(solvedOnes[i]);
                    if (newSquare != board.getSquare(p)) {
                        board.setSquare(p, newSquare);
                        changed = true;
                    }
                }
            }
        }
        return changed;
    }
    
}
