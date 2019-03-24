package lazi.sudoku.board;

import java.util.List;

import lazi.sudoku.Position;
import lazi.sudoku.PositionLists;
import lazi.sudoku.PossibleValues;

public class Board {
    
    PossibleValues[][] squares = new PossibleValues[9][9];
    
    public Board(Board board) {
        this.squares = copySquares(board.squares);
    }
    
    public Board(PossibleValues[][] squares) {
        this.squares = copySquares(squares);
    }
    
    public PossibleValues getSquare(int row, int col) {
        return squares[row][col];
    }
    
    public PossibleValues getSquare(Position position) {
        return squares[position.getRow()][position.getCol()];
    }
    
    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Board)) return false;
        Board that = (Board) other;
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
            PossibleValues possibility = PossibleValues.EMPTY;
            for (Position p : group) {
                possibility = possibility.or(getSquare(p));
            }
            if (possibility != PossibleValues.FULL) {
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
            PossibleValues possibility = PossibleValues.EMPTY;
            for (Position p : group) {
                possibility = possibility.or(getSquare(p));
            }
            if (possibility != PossibleValues.FULL) {
                return true;
            }
        }
        return false;
    }
    
    public PossibleValues[][] getSquaresCopy() {
        return copySquares(squares);
    }
    
    public Board hideSquare(Position p) {
        //System.out.println("hideSquare: " + p.getRow() + ", "  + p.getCol());
        return setSquare(p, PossibleValues.FULL);
    }
    
    public Board setSquare(Position p, PossibleValues square) {
        PossibleValues[][] result = getSquaresCopy();
        result[p.getRow()][p.getCol()] = square;
        return new Board(result);
    }
    
    public Board noGuess() {
        PossibleValues[][] result = copySquares(squares);
        for (Position p : PositionLists.all()) {
            if (!getSquare(p).containsExactlyOne()) {
                result[p.getRow()][p.getCol()] = PossibleValues.FULL;
            }
        }
        return new Board(result);
    }
    
    public Board and(Board other) {
        PossibleValues[][] result = createNullSquares();
        for (Position p : PositionLists.all()) {
            result[p.getRow()][p.getCol()] = getSquare(p).and(other.getSquare(p));
        }
        return new Board(result);
    }
    
    public Board or(Board other) {
        PossibleValues[][] result = createNullSquares();
        for (Position p : PositionLists.all()) {
            result[p.getRow()][p.getCol()] = getSquare(p).or(other.getSquare(p));
        }
        return new Board(result);
    }
    
    public static PossibleValues[][] createNullSquares() {
        return new PossibleValues[9][9];
    }
    public static PossibleValues[][] createEmptySquares() {
        PossibleValues[][] result = createNullSquares();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                result[i][j] = PossibleValues.EMPTY;
            }
        }
        return result;
    }
    public static PossibleValues[][] createFullSquares() {
        PossibleValues[][] result = createNullSquares();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                result[i][j] = PossibleValues.FULL;
            }
        }
        return result;
    }
    public static PossibleValues[][] copySquares(PossibleValues[][] source) {
        PossibleValues[][] result = createNullSquares();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                result[i][j] = source[i][j];
            }
        }
        return result;
    }
    
}
