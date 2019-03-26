package lazi.sudoku;

public class Position {
    
    private final int index;
    private final int row;
    private final int col;
    
    private Position(int index) {
        super();
        this.index = index;
        this.row = index / 9;
        this.col = index % 9;
    }
    
    public int getIndex() {
        return index;
    }
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
    
    @Override
    public int hashCode() {
        return index;
    }
    
    private static Position[] POSITIONS;
    static {
        Position[] positions = new Position[9 * 9];
        for (int index = 0; index < 9 * 9; index++) {
            positions[index] = new Position(index);
        }
        POSITIONS = positions;
    }
    public static Position of(int index) {
        return POSITIONS[index];
    }
    public static Position of(int row, int col) {
        return POSITIONS[row * 9 + col];
    }
    
}
