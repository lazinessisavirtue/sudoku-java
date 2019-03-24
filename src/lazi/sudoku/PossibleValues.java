package lazi.sudoku;

public class PossibleValues {
    
    private final int possibilities;
    private final int size;
    private final int smallest;
    
    private PossibleValues(int possibilities) {
        this.possibilities = possibilities;
        int size = 0;
        int smallest = 0;
        for (int i = 1; i <= 9; i++) {
            if ((possibilities & (1 << i)) != 0) {
                size++;
                if (smallest == 0) {
                    smallest = i;
                }
            }
        }
        this.size = size;
        this.smallest = smallest;
    }
    
    public int size() {
        return size;
    }
    public int smallest() {
        return smallest;
    }
    public boolean isEmpty() {
        return possibilities == 0;
    }
    public boolean containsExactlyOne() {
        return size == 1;
    }
    public boolean contains(int value) {
        SudokuUtil.validateValue(value);
        return (possibilities & (1 << value)) != 0;
    }
    
    public PossibleValues and(PossibleValues other) {
        return PossibleValues.of(possibilities & other.possibilities);
    }
    public PossibleValues or(PossibleValues other) {
        return PossibleValues.of(possibilities | other.possibilities);
    }
    public PossibleValues substract(PossibleValues other) {
        return PossibleValues.of(possibilities & ~other.possibilities);
    }
    public PossibleValues add(int value) {
        SudokuUtil.validateValue(value);
        return PossibleValues.of(possibilities | (1 << value));
    }
    public PossibleValues remove(int value) {
        SudokuUtil.validateValue(value);
        return PossibleValues.of(possibilities & ~(1 << value));
    }
    
    private static final PossibleValues[] ALL = new PossibleValues[1 << 9];
    static {
        for (int i = 0; i < 1 << 9; i++) {
            ALL[i] = new PossibleValues(i << 1);
        }
    }
    private static PossibleValues of(int possibilities) {
        return ALL[possibilities >> 1];
    }
    
    public static final PossibleValues EMPTY = PossibleValues.of(0);
    public static final PossibleValues FULL = PossibleValues.of((1 << 10) - 2);
    
    public static PossibleValues only(int value) {
        SudokuUtil.validateValue(value);
        return PossibleValues.of(1 << value);
    }
    
}
