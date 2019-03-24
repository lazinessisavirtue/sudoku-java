package lazi.sudoku;

import java.util.List;

public class BoardPossibilities {
    
    private SquarePossibilities[][] squares = new SquarePossibilities[9][9];
    
    public BoardPossibilities(SquarePossibilities[][] squares) {
        this.squares = copySquares(squares);
    }
    
    public SquarePossibilities getSquare(int row, int col) {
        return squares[row][col];
    }
    
    public SquarePossibilities getSquare(Position position) {
        return squares[position.getRow()][position.getCol()];
    }
    
    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof BoardPossibilities)) return false;
        BoardPossibilities that = (BoardPossibilities) other;
        for (Position p : PositionLists.all()) {
            if (this.getSquare(p) != that.getSquare(p)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isSolution() {
        for (Position p : PositionLists.all()) {
            if (!getSquare(p).containsExactlyOne()) {
                return false;
            }
        }
        for (List<Position> group : PositionLists.groups()) {
            SquarePossibilities possibility = SquarePossibilities.EMPTY;
            for (Position p : group) {
                possibility = possibility.or(getSquare(p));
            }
            if (possibility != SquarePossibilities.FULL) {
                return false;
            }
        }
        return true;
    }
    public boolean isContradiction() {
        for (Position p : PositionLists.all()) {
            if (getSquare(p).isEmpty()) {
                return true;
            }
        }
        for (List<Position> group : PositionLists.groups()) {
            SquarePossibilities possibility = SquarePossibilities.EMPTY;
            for (Position p : group) {
                possibility = possibility.or(getSquare(p));
            }
            if (possibility != SquarePossibilities.FULL) {
                return true;
            }
        }
        return false;
    }
    
    public SquarePossibilities[][] getSquaresCopy() {
        return copySquares(squares);
    }
    
    public BoardPossibilities hideSquare(Position p) {
        return setSquare(p, SquarePossibilities.FULL);
    }
    
    public BoardPossibilities setSquare(Position p, SquarePossibilities square) {
        SquarePossibilities[][] result = getSquaresCopy();
        result[p.getRow()][p.getCol()] = square;
        return new BoardPossibilities(result);
    }
    
    public BoardPossibilities noGuess() {
        SquarePossibilities[][] result = copySquares(squares);
        for (Position p : PositionLists.all()) {
            if (!getSquare(p).containsExactlyOne()) {
                result[p.getRow()][p.getRow()] = SquarePossibilities.FULL;
            }
        }
        return new BoardPossibilities(result);
    }
    
    public BoardPossibilities and(BoardPossibilities other) {
        SquarePossibilities[][] result = createNullSquares();
        for (Position p : PositionLists.all()) {
            result[p.getRow()][p.getCol()] = getSquare(p).and(other.getSquare(p));
        }
        return new BoardPossibilities(result);
    }
    
    public BoardPossibilities or(BoardPossibilities other) {
        SquarePossibilities[][] result = createNullSquares();
        for (Position p : PositionLists.all()) {
            result[p.getRow()][p.getCol()] = getSquare(p).or(other.getSquare(p));
        }
        return new BoardPossibilities(result);
    }
    
    public static SquarePossibilities[][] createNullSquares() {
        return new SquarePossibilities[9][9];
    }
    public static SquarePossibilities[][] createEmptySquares() {
        SquarePossibilities[][] result = createNullSquares();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                result[i][j] = SquarePossibilities.EMPTY;
            }
        }
        return result;
    }
    public static SquarePossibilities[][] createFullSquares() {
        SquarePossibilities[][] result = createNullSquares();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                result[i][j] = SquarePossibilities.FULL;
            }
        }
        return result;
    }
    public static SquarePossibilities[][] copySquares(SquarePossibilities[][] source) {
        SquarePossibilities[][] result = createNullSquares();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                result[i][j] = source[i][j];
            }
        }
        return result;
    }
    
}
