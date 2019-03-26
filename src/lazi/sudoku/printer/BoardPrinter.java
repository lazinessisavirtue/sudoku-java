package lazi.sudoku.printer;

import lazi.sudoku.Position;
import lazi.sudoku.board.ImmutableBoard;

public class BoardPrinter {
    
    public static void print(ImmutableBoard board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board.getSquare(Position.of(row, col)).containsExactlyOne()) {
                    System.out.print(board.getSquare(Position.of(row, col)).smallest());
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }
    
}
