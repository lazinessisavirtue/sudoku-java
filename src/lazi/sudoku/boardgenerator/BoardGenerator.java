package lazi.sudoku.boardgenerator;

import lazi.sudoku.board.Board;

public abstract class BoardGenerator {

    public Board generate() {
        Board board = tryGenerate();
        while (!board.isSolution()) {
            board = tryGenerate();
        }
        return board;
    }
    
    public void testSuccessRate(int attempts) {
        System.out.println("testSolutionGeneratorSuccessRate");
        int solution = 0, contradiction = 0;
        for (int i = 0; i < attempts; i++) {
            Board board = tryGenerate();
            if (board.isSolution()) {
                solution++;
            }
            if (board.isContradiction()) {
                contradiction++;
            }
        }
        System.out.println("solution = " + solution);
        System.out.println("contradiction = " +  contradiction);
    }
    
    protected abstract Board tryGenerate();
    
}
