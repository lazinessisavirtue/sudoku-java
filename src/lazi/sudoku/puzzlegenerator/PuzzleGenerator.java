package lazi.sudoku.puzzlegenerator;

import lazi.sudoku.BoardPossibilities;
import lazi.sudoku.Puzzle;

public abstract class PuzzleGenerator {
    
    public abstract Puzzle generate(BoardPossibilities solvedBoard);
    
}
