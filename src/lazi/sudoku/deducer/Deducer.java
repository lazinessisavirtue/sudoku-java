package lazi.sudoku.deducer;

import java.util.function.BiPredicate;

import lazi.sudoku.BoardPossibilities;

public abstract class Deducer {
    
    public abstract BoardPossibilities deduce(BoardPossibilities board);
    
    public BoardPossibilities deduceUntilStable(BoardPossibilities board) {
        BoardPossibilities prev = board;
        BoardPossibilities next = deduce(board);
        while (!prev.equals(next)) {
            //System.out.println("deduceUntilStable");
            prev = next;
            next = deduce(prev);
        }
        return next;
    }
    
    public BoardPossibilities deduceUntilStableOrCondition(
            BoardPossibilities board,
            BiPredicate<BoardPossibilities, BoardPossibilities> condition) {
        BoardPossibilities prev = board;
        BoardPossibilities next = deduce(board);
        while (!prev.equals(next) && !condition.test(prev, next)) {
            //System.out.println("deduceUntilStable");
            prev = next;
            next = deduce(prev);
        }
        return next;
    }
    
}
