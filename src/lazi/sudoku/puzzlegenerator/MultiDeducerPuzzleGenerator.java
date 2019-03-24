package lazi.sudoku.puzzlegenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lazi.sudoku.BoardPossibilities;
import lazi.sudoku.Position;
import lazi.sudoku.PositionLists;
import lazi.sudoku.Puzzle;
import lazi.sudoku.SudokuUtil;
import lazi.sudoku.deducer.Deducer;

public class MultiDeducerPuzzleGenerator extends PuzzleGenerator {
    
    private final Deducer[] deducers;
    
    public MultiDeducerPuzzleGenerator(Deducer[] deducers) {
        this.deducers = Arrays.copyOf(deducers, deducers.length);
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
        List<List<Position>> canHide = new ArrayList<>();
        for (int i = 0; i < deducers.length; i++) {
            canHide.add(new ArrayList<>());
        }
        for (Position p : PositionLists.all()) {
            if (!board.getSquare(p).containsExactlyOne()) {
                continue;
            }
            BoardPossibilities prev = board.hideSquare(p);
            BoardPossibilities next = prev;
            int max = 0;
            for (int i = 0; i < deducers.length; ) {
                prev = next;
                next = deducers[i].deduce(prev);
                if (prev.equals(next)) {
                    i++;
                } else {
                    max = Math.max(i, max);
                    i = 0;
                    if (next.getSquare(p).containsExactlyOne()) {
                        canHide.get(max).add(p);
                        break;
                    }
                }
            }
        }
        System.out.print("canHide size: [ ");
        for (int i = 0; i < deducers.length; i++) {
            System.out.print(canHide.get(i).size() + ", ");
        }
        System.out.println("]");
        for (int i = deducers.length - 1; i >= 0; i--) {
            if (canHide.get(i).size() > 0) {
                Position p = SudokuUtil.randomElement(canHide.get(i));
                System.out.println(String.format(
                        "hide (%s, %s) by deducer #%s %s",
                        p.getRow(),
                        p.getCol(),
                        i,
                        deducers[i].getClass().getSimpleName()));
                return board.hideSquare(p);
            }
        }
        return board;
    }
    
}
