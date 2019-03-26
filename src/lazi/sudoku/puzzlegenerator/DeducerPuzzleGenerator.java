package lazi.sudoku.puzzlegenerator;

import java.util.ArrayList;
import java.util.List;

import lazi.sudoku.Position;
import lazi.sudoku.PositionLists;
import lazi.sudoku.Puzzle;
import lazi.sudoku.SudokuUtil;
import lazi.sudoku.board.ImmutableBoard;
import lazi.sudoku.board.MutableBoard;
import lazi.sudoku.deducer.Deducer;

public class DeducerPuzzleGenerator extends PuzzleGenerator {
    
    private final Deducer deducer;
    
    public DeducerPuzzleGenerator(Deducer deducer) {
        this.deducer = deducer;
    }
    
    @Override
    public Puzzle generate(ImmutableBoard solvedBoard) {
        MutableBoard board = solvedBoard.createMutableCopy();
        while (step(board));
        return new Puzzle(solvedBoard, board.createImmutableCopy());
    }
    
    private boolean step(MutableBoard board) {
        List<Position> canHide = new ArrayList<>();
        for (Position p : PositionLists.all()) {
            if (!board.getSquare(p).containsExactlyOne()) {
                continue;
            }
            MutableBoard deducedBoard = board.createMutableCopy();
            deducedBoard.hideSquare(p);
            while (!deducedBoard.getSquare(p).containsExactlyOne() && deducer.deduce(board));
            if (deducedBoard.getSquare(p).containsExactlyOne()) {
                canHide.add(p);
            }
        }
        if (!canHide.isEmpty()) {
            board.hideSquare(SudokuUtil.randomElement(canHide));
            return true;
        }
        return false;
    }
    
}
