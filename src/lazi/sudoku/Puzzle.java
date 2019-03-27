package lazi.sudoku;

import lazi.sudoku.board.ImmutableBoard;

public class Puzzle {
    
    private final ImmutableBoard solvedBoard;
    private final ImmutableBoard puzzleBoard;
    private final PuzzleMetadata metadata = new PuzzleMetadata();
    
    public Puzzle(ImmutableBoard solvedBoard, ImmutableBoard puzzleBoard) {
        super();
        this.solvedBoard = solvedBoard;
        this.puzzleBoard = puzzleBoard;
    }
    
    public ImmutableBoard getSolvedBoard() {
        return solvedBoard;
    }
    public ImmutableBoard getPuzzleBoard() {
        return puzzleBoard;
    }
    public PuzzleMetadata getMetadata() {
        return metadata;
    }
    
}
