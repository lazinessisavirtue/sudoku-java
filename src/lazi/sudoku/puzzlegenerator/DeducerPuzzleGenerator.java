package lazi.sudoku.puzzlegenerator;

import java.util.ArrayList;
import java.util.List;

import lazi.sudoku.BoardPossibilities;
import lazi.sudoku.Position;
import lazi.sudoku.PositionLists;
import lazi.sudoku.Puzzle;
import lazi.sudoku.SudokuUtil;
import lazi.sudoku.deducer.Deducer;

public class DeducerPuzzleGenerator extends PuzzleGenerator {
    
    private final Deducer deducer;
    
    public DeducerPuzzleGenerator(Deducer deducer) {
        this.deducer = deducer;
    }
    
    @Override
    public Puzzle generate(BoardPossibilities solvedBoard) {
        BoardPossibilities prev = solvedBoard;
        BoardPossibilities next = step(prev).noGuess();
        while (!prev.equals(next)) {
            prev = next;
            next = step(prev);
        }
        return new Puzzle(solvedBoard, next);
    }
    
    private BoardPossibilities step(BoardPossibilities board) {
        List<Position> canHide = new ArrayList<>();
        for (Position p : PositionLists.all()) {
            if (!board.getSquare(p).containsExactlyOne()) {
                continue;
            }
            BoardPossibilities hiddenBoard = board.hideSquare(p);
            BoardPossibilities deducedBoard = deducer.deduceUntilStableOrCondition(
                    hiddenBoard,
                    (prev, next) -> next.getSquare(p).containsExactlyOne());;
            if (deducedBoard.getSquare(p).containsExactlyOne()) {
                canHide.add(p);
            }
        }
        return canHide.isEmpty() ? board : board.hideSquare(SudokuUtil.randomElement(canHide));
    }
    
}
