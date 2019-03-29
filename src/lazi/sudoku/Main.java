package lazi.sudoku;

import java.util.Arrays;

import lazi.sudoku.board.ImmutableBoard;
import lazi.sudoku.boardgenerator.BoardGenerator;
import lazi.sudoku.boardgenerator.L1BoardGenerator;
import lazi.sudoku.boardgenerator.MultipleDeducerBoardGenerator;
import lazi.sudoku.deducer.Deducer;
import lazi.sudoku.deducer.MultipleSquareForAValueDeducer;
import lazi.sudoku.deducer.MultipleValueForASquareDeducer;
import lazi.sudoku.deducer.OnlySquareForAValueDeducer;
import lazi.sudoku.deducer.OnlyValueForASquareDeducer;
import lazi.sudoku.printer.BoardPossibilitiesPrinter;
import lazi.sudoku.printer.BoardPrinter;
import lazi.sudoku.puzzlegenerator.MultiDeducerPuzzleGenerator;

/*
    TODOs:
    - add cascading deducers
    - add chaining deducers
    - add solver
    - add solver puzzle generator
*/
/*
    References:
    - http://www.sudokuwiki.org/strategy_families
    - https://www.stolaf.edu/people/hansonr/sudoku/analyst.htm
*/
public class Main {
    
    public static void main(String[] args) {
        //generateSolvedBoard();
        //testBoardGeneratorSuccessRate();
        //generatePuzzle();
        generateHardPuzzle();
    }
    
    public static ImmutableBoard generateSolvedBoard() {
        ImmutableBoard board = new L1BoardGenerator().generate();
        BoardPossibilitiesPrinter.print(board);
        BoardPrinter.print(board);
        return board;
    }
    
    public static void testBoardGeneratorSuccessRate() {
        //BoardGenerator boardGenerator = new L1BoardGenerator();
        BoardGenerator boardGenerator = new MultipleDeducerBoardGenerator(new Deducer[] {
                new OnlyValueForASquareDeducer(),
                new OnlySquareForAValueDeducer(),
                new MultipleValueForASquareDeducer(2, 4),
                new MultipleSquareForAValueDeducer(2, 4),
        });
        boardGenerator.testSuccessRate(1000);
    }
    
    public static Puzzle generatePuzzle() {
        long startTime = System.currentTimeMillis();
        
        //BoardPossibilities solvedBoard = new L1BoardGenerator().generate();
        ImmutableBoard solvedBoard = new MultipleDeducerBoardGenerator(new Deducer[] {
                new OnlyValueForASquareDeducer(),
                new OnlySquareForAValueDeducer(),
                new MultipleValueForASquareDeducer(2, 4),
                new MultipleSquareForAValueDeducer(2, 4),
        }).generate();
        long solvedBoardTime = System.currentTimeMillis();
        
        Puzzle puzzle = new MultiDeducerPuzzleGenerator(new Deducer[] {
                new OnlyValueForASquareDeducer(),
                new OnlySquareForAValueDeducer(),
                new MultipleValueForASquareDeducer(2, 4),
                new MultipleSquareForAValueDeducer(2, 4),
                new MultipleValueForASquareDeducer(3, 6),
                new MultipleSquareForAValueDeducer(3, 6),
        }).generate(solvedBoard);
        long puzzleTime = System.currentTimeMillis();

        System.out.println("solvedBoard:");
        //BoardPossibilitiesPrinter.print(puzzle.getSolvedBoard());
        BoardPrinter.print(puzzle.getSolvedBoard());
        System.out.println("hiddenBoard:");
        //BoardPossibilitiesPrinter.print(puzzle.getHiddenBoard());
        BoardPrinter.print(puzzle.getPuzzleBoard());
        System.out.println("solvedBoard took " + (solvedBoardTime - startTime) + "ms");
        System.out.println("puzzle took " + (puzzleTime - solvedBoardTime) + "ms");
        
        int shownCount = 0;
        for (Position p : PositionLists.all()) {
            if (puzzle.getPuzzleBoard().getSquare(p).containsExactlyOne()) {
                shownCount++;
            }
        }
        System.out.println("squares shown: " + shownCount);
        System.out.println("squares hidden: " + (81 - shownCount));
        return puzzle;
    }
    
    private static Puzzle tryGenerateHardPuzzle() {
        System.out.println("tryGenerateHardPuzzle");
        long startTime = System.currentTimeMillis();
        
        ImmutableBoard solvedBoard = new MultipleDeducerBoardGenerator(new Deducer[] {
                new OnlyValueForASquareDeducer(),
                new OnlySquareForAValueDeducer(),
                new MultipleValueForASquareDeducer(2, 4),
                new MultipleSquareForAValueDeducer(2, 4),
        }).generate();
        long solvedBoardTime = System.currentTimeMillis();
        
        Puzzle puzzle = new MultiDeducerPuzzleGenerator(new Deducer[] {
                new OnlyValueForASquareDeducer(),
                new OnlySquareForAValueDeducer(),
                new MultipleValueForASquareDeducer(2, 1),
                new MultipleSquareForAValueDeducer(2, 1),
                new MultipleValueForASquareDeducer(2, 2),
                new MultipleSquareForAValueDeducer(2, 2),
                new MultipleValueForASquareDeducer(2, 3),
                new MultipleSquareForAValueDeducer(2, 3),
                new MultipleValueForASquareDeducer(2, 4),
                new MultipleSquareForAValueDeducer(2, 4),
                new MultipleValueForASquareDeducer(2, 5),
                new MultipleSquareForAValueDeducer(2, 5),
                new MultipleValueForASquareDeducer(2, 6),
                new MultipleSquareForAValueDeducer(2, 6),
                new MultipleValueForASquareDeducer(3, 1),
                new MultipleSquareForAValueDeducer(3, 1),
                new MultipleValueForASquareDeducer(3, 2),
                new MultipleSquareForAValueDeducer(3, 2),
                new MultipleValueForASquareDeducer(3, 3),
                new MultipleSquareForAValueDeducer(3, 3),
                new MultipleValueForASquareDeducer(3, 4),
                new MultipleSquareForAValueDeducer(3, 4),
                new MultipleValueForASquareDeducer(3, 5),
                new MultipleSquareForAValueDeducer(3, 5),
                new MultipleValueForASquareDeducer(3, 6),
                new MultipleSquareForAValueDeducer(3, 6),
        }).generate(solvedBoard);
        long puzzleTime = System.currentTimeMillis();
        
        System.out.println("  solvedBoard took " + (solvedBoardTime - startTime) + "ms");
        System.out.println("  puzzle took " + (puzzleTime - solvedBoardTime) + "ms");
        System.out.println("  squares shown: " + PositionLists.all().stream()
                .mapToInt(p -> puzzle.getPuzzleBoard().getSquare(p).containsExactlyOne() ? 1 : 0)
                .sum());
        System.out.println("  hardness: " + puzzle.getMetadata().hardness);
        System.out.println("  hardnessArray: " + Arrays.toString(puzzle.getMetadata().hardnessArray));
        return puzzle;
    }
    
    private static boolean isHardEnough(Puzzle puzzle) {
        //return puzzle.getMetadata().hardness >= 14;
        int summary = 0;
        for (int i = 6; i < puzzle.getMetadata().hardnessArray.length; i++) {
            summary += puzzle.getMetadata().hardnessArray[i];
        }
        for (int i = 14; i < puzzle.getMetadata().hardnessArray.length; i++) {
            summary += puzzle.getMetadata().hardnessArray[i];
        }
        System.out.println("  hardnessArray.summary: " + summary);
        return summary >= 8;
    }
    
    public static Puzzle generateHardPuzzle() {
        long startTime = System.currentTimeMillis();
        
        Puzzle puzzle = tryGenerateHardPuzzle();
        int attempt = 1;
        while (!isHardEnough(puzzle)) {
            puzzle = tryGenerateHardPuzzle();
            attempt++;
        }
        System.out.println("solvedBoard:");
        BoardPrinter.print(puzzle.getSolvedBoard());
        System.out.println("hiddenBoard:");
        BoardPrinter.print(puzzle.getPuzzleBoard());
        System.out.println("attempts: " + attempt);
        System.out.println("total took " + (System.currentTimeMillis() - startTime) + "ms");
        return puzzle;
    }
    
}
