package lazi.sudoku.boardgenerator;

import java.util.ArrayList;
import java.util.List;

import lazi.sudoku.BoardPossibilities;
import lazi.sudoku.Position;
import lazi.sudoku.PositionLists;
import lazi.sudoku.SquarePossibilities;
import lazi.sudoku.SudokuUtil;
import lazi.sudoku.deducer.L1Deducer;

public class L1BoardGenerator extends BoardGenerator {
    
    protected BoardPossibilities tryGenerate() {
        SquarePossibilities[][] squares = BoardPossibilities.createFullSquares();
        insertSeeds(squares);
        BoardPossibilities board = new BoardPossibilities(squares);
        board = new L1Deducer().deduceUntilStable(board);
        while (!board.isSolution() && !board.isContradiction()) {
            board = makeRandomGuess(board);
            board = new L1Deducer().deduceUntilStable(board);
        }
        return board;
    }
    
    private void insertSeeds(SquarePossibilities[][] squares) {
        int[] random9;
        random9 = SudokuUtil.shuffledValues();
        for (int i = 0; i < 9; i++) {
            squares[i % 3][i / 3] = SquarePossibilities.only(random9[i]);
        }
        random9 = SudokuUtil.shuffledValues();
        for (int i = 0; i < 9; i++) {
            squares[i % 3 + 3][i / 3 + 3] = SquarePossibilities.only(random9[i]);
        }
        random9 = SudokuUtil.shuffledValues();
        for (int i = 0; i < 9; i++) {
            squares[i % 3 + 6][i / 3 + 6] = SquarePossibilities.only(random9[i]);
        }
    }
    
    private BoardPossibilities makeRandomGuess(BoardPossibilities board) {
        List<List<Position>> positionsByNumberOfPossibilities = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            positionsByNumberOfPossibilities.add(new ArrayList<>());
        }
        for (Position p : PositionLists.all()) {
            int numberOfPossibilities = board.getSquare(p).size();
            if (board.getSquare(p).size() > 1) {
                positionsByNumberOfPossibilities.get(numberOfPossibilities).add(p);
            }
        }
        for (int i = 2; i <= 9; i++)  {
            if (positionsByNumberOfPossibilities.get(i).size() == 0) {
                continue;
            }
            Position p = SudokuUtil.randomElement(positionsByNumberOfPossibilities.get(i));
            SquarePossibilities square = board.getSquare(p);
            SquarePossibilities[][] squares = board.getSquaresCopy();
            int[] random9 = SudokuUtil.shuffledValues();
            for (int j = 0; j < 9; j++) {
                if(square.contains(random9[j])) {
                    squares[p.getRow()][p.getCol()] = SquarePossibilities.only(random9[j]);
                    return new BoardPossibilities(squares);
                }
            }
        }
        throw new RuntimeException("cannot guess");
    }
}
