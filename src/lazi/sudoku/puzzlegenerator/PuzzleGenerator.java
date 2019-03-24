package lazi.sudoku.puzzlegenerator;

import lazi.sudoku.Puzzle;
import lazi.sudoku.board.Board;

public abstract class PuzzleGenerator {
    
    public abstract Puzzle generate(Board solvedBoard);
    
}
