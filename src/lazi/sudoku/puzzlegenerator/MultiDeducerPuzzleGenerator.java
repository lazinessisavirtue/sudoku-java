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
import lazi.sudoku.board.Board;
import lazi.sudoku.deducer.Deducer;

public class MultiDeducerPuzzleGenerator extends PuzzleGenerator {
    
    private final Deducer[] deducers;
    
    public MultiDeducerPuzzleGenerator(Deducer[] deducers) {
        this.deducers = Arrays.copyOf(deducers, deducers.length);
    }
    
    @Override
    public Puzzle generate(Board solvedBoard) {
        Set<Position> candidates = new HashSet<>(PositionLists.all());
        Board prev = solvedBoard;
        Board next = step(prev, candidates);
        while (!prev.equals(next)) {
            prev = next;
            next = step(prev, candidates);
        }
        return new Puzzle(solvedBoard, next);
    }
    
    private Board step(Board board, Set<Position> candidates) {
        List<List<Position>> canHide = new ArrayList<>();
        for (int i = 0; i < deducers.length; i++) {
            canHide.add(new ArrayList<>());
        }
        for (Iterator<Position> it = candidates.iterator(); it.hasNext();) {
            Position p = it.next();
            if (!board.getSquare(p).containsExactlyOne()) {
                //BoardPossibilitiesPrinter.print(board);
                throw new RuntimeException("candidate should have already been hidden: "
                        + p.getRow() + ", " + p.getCol());
            }
            Board prev = board.hideSquare(p);
            Board next = prev;
            int max = 0;
            for (int i = 0; ; ) {
                prev = next;
                next = deducers[i].deduce(prev);
                if (prev.equals(next)) {
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
                        ""));
                        //deducers[i].getClass().getSimpleName()));
                candidates.remove(p); // because now hidden
                return board.hideSquare(p);
            }
        }
        return board;
    }
    
}
