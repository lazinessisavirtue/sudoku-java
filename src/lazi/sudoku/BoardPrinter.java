package lazi.sudoku;

public class BoardPrinter {
    
    public static void print(BoardPossibilities board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board.getSquare(row, col).containsExactlyOne()) {
                    System.out.print(board.getSquare(row, col).smallest());
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }
    
}
