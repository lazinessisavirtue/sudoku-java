package lazi.sudoku.deducer;

import java.util.function.BiPredicate;

import lazi.sudoku.board.Board;

public abstract class Deducer {
    
    public abstract Board deduce(Board board);
    
    public Board deduceUntilStable(Board board) {
        Board prev = board;
        Board next = deduce(board);
        while (!prev.equals(next)) {
            //System.out.println("deduceUntilStable");
            prev = next;
            next = deduce(prev);
        }
        return next;
    }
    
    public Board deduceUntilStableOrCondition(
            Board board,
            BiPredicate<Board, Board> condition) {
        Board prev = board;
        Board next = deduce(board);
        while (!prev.equals(next) && !condition.test(prev, next)) {
            //System.out.println("deduceUntilStable");
            prev = next;
            next = deduce(prev);
        }
        return next;
    }
    
}
