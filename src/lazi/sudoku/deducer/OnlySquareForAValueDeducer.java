package lazi.sudoku.deducer;

import java.util.Arrays;
import java.util.List;

import lazi.sudoku.Position;
import lazi.sudoku.PositionLists;
import lazi.sudoku.PossibleValues;
import lazi.sudoku.board.MutableBoard;

public class OnlySquareForAValueDeducer extends Deducer {
    
    @Override
    public boolean deduce(MutableBoard board) {
        boolean changed = false;
        List<List<Position>> groups = PositionLists.groups();
        PossibleValues[] seenExactlyOne = new PossibleValues[groups.size()];
        Arrays.fill(seenExactlyOne, PossibleValues.EMPTY);
        for (int i = 0; i < groups.size(); i++) {
            PossibleValues seenOne = PossibleValues.EMPTY;
            PossibleValues seenTwoPlus = PossibleValues.EMPTY;
            for (Position p : groups.get(i)) {
                seenTwoPlus = seenTwoPlus.or(seenOne.and(board.getSquare(p)));
                seenOne = seenOne.or(board.getSquare(p));
            }
            seenExactlyOne[i] = seenOne.substract(seenTwoPlus);
        }
        for (int i = 0; i < groups.size(); i++) {
            if (seenExactlyOne[i] == PossibleValues.EMPTY) {
                continue;
            }
            for (Position p : groups.get(i)) {
                if (board.getSquare(p).and(seenExactlyOne[i]) != PossibleValues.EMPTY) {
                    PossibleValues newSquare = board.getSquare(p).and(seenExactlyOne[i]);
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
