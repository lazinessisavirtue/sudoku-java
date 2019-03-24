package lazi.sudoku.printer;

import lazi.sudoku.PossibleValues;
import lazi.sudoku.board.Board;

public class BoardPossibilitiesPrinter {
    
    private static final int NUM_ROW_PER_SQUARE = 3;
    private static final int NUM_COL_PER_SQUARE = 3;
    
    public static void print(Board board) {
        for (int row = 0; row < 9; row++) {
            if (row % 3 == 0) {
                printBarLine();
            } else {
                printBlankLine();
            }
            for (int subRow = 0; subRow < NUM_ROW_PER_SQUARE; subRow++) {
                printValueLine(board, row, subRow);
            }
        }
        printBarLine();
    }
    private static void printBarLine() {
        for (int col = 0; col < 9; col++) {
            System.out.print(col % 3 == 0 ? '+' : '-');
            for (int subCol = 0; subCol < NUM_COL_PER_SQUARE; subCol++) {
                System.out.print('-');
            }
        }
        System.out.println('+');
    }
    private static void printBlankLine() {
        for (int col = 0; col < 9; col++) {
            System.out.print(col % 3 == 0 ? '|' : ' ');
            for (int subCol = 0; subCol < NUM_COL_PER_SQUARE; subCol++) {
                System.out.print(' ');
            }
        }
        System.out.println('|');
    }
    private static void printValueLine(
            Board board,
            int row,
            int subRow) {
        for (int col = 0; col < 9; col++) {
            System.out.print(col % 3 == 0 ? '|' : ' ');
            for (int j = 0; j < NUM_COL_PER_SQUARE; j++) {
                int value = 1 + subRow * NUM_COL_PER_SQUARE + j;
                printValue(board.getSquare(row, col), value);
            }
        }
        System.out.println('|');
    }
    private static void printValue(
            PossibleValues square,
            int value) {
        if (square.isEmpty()) {
            System.out.print('X');
        } else if (square.contains(value)) {
            System.out.print(value);
        } else if (square.containsExactlyOne()) {
            System.out.print('.');
            /*for (int i = 1; i <= 9; i++) {
                if (square == SquarePossibilities.only(i)) {
                    System.out.print(i);
                }
            }*/
        } else {
            System.out.print(' ');
        }
    }
    
}
