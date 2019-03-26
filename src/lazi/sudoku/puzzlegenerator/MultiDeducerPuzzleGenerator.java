package lazi.sudoku.puzzlegenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lazi.sudoku.Position;
import lazi.sudoku.PositionLists;
import lazi.sudoku.Puzzle;
import lazi.sudoku.SudokuUtil;
import lazi.sudoku.board.ImmutableBoard;
import lazi.sudoku.board.MutableBoard;
import lazi.sudoku.deducer.Deducer;

public class MultiDeducerPuzzleGenerator extends PuzzleGenerator {
    
    private final Deducer[] deducers;
    
    public MultiDeducerPuzzleGenerator(Deducer[] deducers) {
        this.deducers = Arrays.copyOf(deducers, deducers.length);
    }
    
    @Override
    public Puzzle generate(ImmutableBoard solvedBoard) {
        Set<Position> candidates = new HashSet<>(PositionLists.all());
        MutableBoard board = solvedBoard.createMutableCopy();
        while (step(board, candidates));
        return new Puzzle(solvedBoard, board.createImmutableCopy());
    }
    
    private boolean step(MutableBoard board, Set<Position> candidates) {
        List<List<Position>> canHide = new ArrayList<>();
        for (int i = 0; i < deducers.length; i++) {
            canHide.add(new ArrayList<>());
        }
        for (Iterator<Position> it = candidates.iterator(); it.hasNext();) {
            Position p = it.next();
            if (!board.getSquare(p).containsExactlyOne()) {
                throw new RuntimeException("candidate should have already been hidden: "
                        + p.getRow() + ", " + p.getCol());
            }
            MutableBoard deducedBoard = board.createMutableCopy();
            deducedBoard.hideSquare(p);
            int max = 0;
            for (int i = 0; ; ) {
                if (deducers[i].deduce(deducedBoard)) {
                    max = Math.max(i, max);
                    i = 0;
                    if (deducedBoard.getSquare(p).containsExactlyOne()) {
                        canHide.get(max).add(p);
                        break;
                    }
                } else {
                    i++;
                    if (i == deducers.length) {
                        System.out.println(String.format(
                                "#%s (%s, %s): not deducible",
                                candidates.size(),
                                p.getRow(),
                                p.getCol()));
                        it.remove(); // because now not deducible
                        break;
                    }
                }
            }
        }
        System.out.println("    canHide size: "
                + canHide.stream().map(l -> l.size()).collect(Collectors.toList()));
        for (int i = deducers.length - 1; i >= 0; i--) {
            if (canHide.get(i).size() > 0) {
                Position p = SudokuUtil.randomElement(canHide.get(i));
                System.out.println(String.format(
                        "#%s (%s, %s): hide by deducer #%s %s",
                        candidates.size(),
                        p.getRow(),
                        p.getCol(),
                        i,
                        "")); // deducers[i].getClass().getSimpleName()));
                candidates.remove(p); // because now hidden
                board.hideSquare(p);
                return true;
            }
        }
        return false;
    }
    
}
