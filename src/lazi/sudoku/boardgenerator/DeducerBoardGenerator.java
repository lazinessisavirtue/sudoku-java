package lazi.sudoku.boardgenerator;

import java.util.ArrayList;
import java.util.List;

import lazi.sudoku.Position;
import lazi.sudoku.PositionLists;
import lazi.sudoku.PossibleValues;
import lazi.sudoku.SudokuUtil;
import lazi.sudoku.board.ImmutableBoard;
import lazi.sudoku.board.MutableBoard;

public abstract class DeducerBoardGenerator extends BoardGenerator {
    
    protected abstract void deduceUntilStable(MutableBoard board);
    
    protected ImmutableBoard tryGenerate() {
        MutableBoard board = MutableBoard.full();
        insertSeeds(board);
        deduceUntilStable(board);
        while (!board.isSolution() && !board.isContradiction()) {
            makeRandomGuess(board);
            deduceUntilStable(board);
        }
        return board.createImmutableCopy();
    }
    
    private void insertSeeds(MutableBoard board) {
        int[] random9;
        random9 = SudokuUtil.shuffledValues();
        for (int i = 0; i < 9; i++) {
            board.setSquare(
                    Position.of(i % 3, i / 3),
                    PossibleValues.only(random9[i]));
        }
        random9 = SudokuUtil.shuffledValues();
        for (int i = 0; i < 9; i++) {
            board.setSquare(
                    Position.of(i % 3 + 3, i / 3 + 3),
                    PossibleValues.only(random9[i]));
        }
        random9 = SudokuUtil.shuffledValues();
        for (int i = 0; i < 9; i++) {
            board.setSquare(
                    Position.of(i % 3 + 6, i / 3 + 6),
                    PossibleValues.only(random9[i]));
        }
    }
    
    private void makeRandomGuess(MutableBoard board) {
        List<List<Position>> positionsByNumberOfPossibilities = new ArrayList<>();
        for (int i = 2; i <= 9; i++) {
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
            PossibleValues square = board.getSquare(p);
            int[] random9 = SudokuUtil.shuffledValues();
            for (int j = 0; j < 9; j++) {
                if(square.contains(random9[j])) {
                    board.setSquare(p, PossibleValues.only(random9[j]));
                    return;
                }
            }
        }
        throw new RuntimeException("cannot guess");
    }
}
