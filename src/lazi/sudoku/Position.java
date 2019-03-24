package lazi.sudoku;

public class Position {
    
    private final int row;
    private final int col;
    
    private Position(int row, int col) {
        super();
        this.row = row;
        this.col = col;
    }
    
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
    
    private static Position[][] POSITIONS = createPositions();
    private static Position[][] createPositions() {
        Position[][] positions = new Position[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                positions[row][col] = new Position(row, col);
            }
        }
        return positions;
    }
    public static Position of(int row, int col) {
        return POSITIONS[row][col];
    }
    
}
