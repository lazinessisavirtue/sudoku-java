package lazi.sudoku;

public class Puzzle {
    
    private final BoardPossibilities solvedBoard;
    private final BoardPossibilities hiddenBoard;
    
    public Puzzle(BoardPossibilities solvedBoard, BoardPossibilities hiddenBoard) {
        super();
        this.solvedBoard = solvedBoard;
        this.hiddenBoard = hiddenBoard;
    }
    
    public BoardPossibilities getSolvedBoard() {
        return solvedBoard;
    }
    public BoardPossibilities getHiddenBoard() {
        return hiddenBoard;
    }
    
}
