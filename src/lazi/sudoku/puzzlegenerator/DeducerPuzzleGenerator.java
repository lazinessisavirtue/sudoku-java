package lazi.sudoku.puzzlegenerator;

import java.util.ArrayList;
import java.util.List;

import lazi.sudoku.Position;
import lazi.sudoku.PositionLists;
import lazi.sudoku.Puzzle;
import lazi.sudoku.SudokuUtil;
import lazi.sudoku.board.Board;
import lazi.sudoku.deducer.Deducer;

public class DeducerPuzzleGenerator extends PuzzleGenerator {
    
    private final Deducer deducer;
    
    public DeducerPuzzleGenerator(Deducer deducer) {
        this.deducer = deducer;
    }
    
    @Override
    public Puzzle generate(Board solvedBoard) {
        Board prev = solvedBoard;
        Board next = step(prev).noGuess();
        while (!prev.equals(next)) {
            prev = next;
            next = step(prev);
        }
        return new Puzzle(solvedBoard, next);
    }
    
    private Board step(Board board) {
        List<Position> canHide = new ArrayList<>();
        for (Position p : PositionLists.all()) {
            if (!board.getSquare(p).containsExactlyOne()) {
                continue;
            }
            Board hiddenBoard = board.hideSquare(p);
            Board deducedBoard = deducer.deduceUntilStableOrCondition(
                    hiddenBoard,
                    (prev, next) -> next.getSquare(p).containsExactlyOne());;
            if (deducedBoard.getSquare(p).containsExactlyOne()) {
                canHide.add(p);
            }
        }
        return canHide.isEmpty() ? board : board.hideSquare(SudokuUtil.randomElement(canHide));
    }
    
}
