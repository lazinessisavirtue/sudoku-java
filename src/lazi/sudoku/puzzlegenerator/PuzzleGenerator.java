package lazi.sudoku.puzzlegenerator;

import lazi.sudoku.Puzzle;
import lazi.sudoku.board.ImmutableBoard;

public abstract class PuzzleGenerator {
    
    public abstract Puzzle generate(ImmutableBoard solvedBoard);
    
}
