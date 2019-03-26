package lazi.sudoku;

import lazi.sudoku.board.ImmutableBoard;

public class Puzzle {
    
    private final ImmutableBoard solvedBoard;
    private final ImmutableBoard hiddenBoard;
    
    public Puzzle(ImmutableBoard solvedBoard, ImmutableBoard hiddenBoard) {
        super();
        this.solvedBoard = solvedBoard;
        this.hiddenBoard = hiddenBoard;
    }
    
    public ImmutableBoard getSolvedBoard() {
        return solvedBoard;
    }
    public ImmutableBoard getHiddenBoard() {
        return hiddenBoard;
    }
    
}
