package lazi.sudoku.boardgenerator;

import lazi.sudoku.board.ImmutableBoard;

public abstract class BoardGenerator {

    public ImmutableBoard generate() {
        ImmutableBoard board = tryGenerate();
        while (!board.isSolution()) {
            board = tryGenerate();
        }
        return board;
    }
    
    public void testSuccessRate(int attempts) {
        System.out.println("testSolutionGeneratorSuccessRate");
        int solution = 0, contradiction = 0;
        for (int i = 0; i < attempts; i++) {
            ImmutableBoard board = tryGenerate();
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
    
    protected abstract ImmutableBoard tryGenerate();
    
}
