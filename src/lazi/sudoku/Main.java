package lazi.sudoku;

import lazi.sudoku.boardgenerator.BoardGenerator;
import lazi.sudoku.boardgenerator.L1BoardGenerator;
import lazi.sudoku.boardgenerator.MultipleDeducerBoardGenerator;
import lazi.sudoku.deducer.Deducer;
import lazi.sudoku.deducer.MultipleSquareForAValueDeducer;
import lazi.sudoku.deducer.MultipleValueForASquareDeducer;
import lazi.sudoku.deducer.OnlySquareForAValueDeducer;
import lazi.sudoku.deducer.OnlyValueForASquareDeducer;
import lazi.sudoku.puzzlegenerator.MultiDeducerPuzzleGenerator;

public class Main {
    
    public static void main(String[] args) {
        //generateSolvedBoard();
        //testBoardGeneratorSuccessRate();
        generatePuzzle();
    }
    
    public static BoardPossibilities generateSolvedBoard() {
        BoardPossibilities board = new L1BoardGenerator().generate();
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
        BoardPossibilities solvedBoard = new MultipleDeducerBoardGenerator(new Deducer[] {
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
        
        BoardPossibilitiesPrinter.print(puzzle.getSolvedBoard());
        BoardPrinter.print(puzzle.getSolvedBoard());
        BoardPossibilitiesPrinter.print(puzzle.getHiddenBoard());
        BoardPrinter.print(puzzle.getHiddenBoard());
        System.out.println("solvedBoard took " + (solvedBoardTime - startTime) + "ms");
        System.out.println("puzzle took " + (puzzleTime - solvedBoardTime) + "ms");
        
        int shownCount = 0;
        for (Position p : PositionLists.all()) {
            if (puzzle.getHiddenBoard().getSquare(p).containsExactlyOne()) {
                shownCount++;
            }
        }
        System.out.println("squares shown " + shownCount);
        return puzzle;
    }
    
}
