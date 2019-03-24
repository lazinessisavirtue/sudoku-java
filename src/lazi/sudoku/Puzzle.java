package lazi.sudoku;

import lazi.sudoku.board.Board;

public class Puzzle {
    
    private final Board solvedBoard;
    private final Board hiddenBoard;
    
    public Puzzle(Board solvedBoard, Board hiddenBoard) {
        super();
        this.solvedBoard = solvedBoard;
        this.hiddenBoard = hiddenBoard;
    }
    
    public Board getSolvedBoard() {
        return solvedBoard;
    }
    public Board getHiddenBoard() {
        return hiddenBoard;
    }
    
}
