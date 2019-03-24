package lazi.sudoku;

public class SquarePossibilities {
    
    private final int possibilities;
    private final int size;
    private final int smallest;
    
    private SquarePossibilities(int possibilities) {
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
    
    public SquarePossibilities and(SquarePossibilities other) {
        return SquarePossibilities.of(possibilities & other.possibilities);
    }
    public SquarePossibilities or(SquarePossibilities other) {
        return SquarePossibilities.of(possibilities | other.possibilities);
    }
    public SquarePossibilities substract(SquarePossibilities other) {
        return SquarePossibilities.of(possibilities & ~other.possibilities);
    }
    public SquarePossibilities add(int value) {
        SudokuUtil.validateValue(value);
        return SquarePossibilities.of(possibilities | (1 << value));
    }
    public SquarePossibilities remove(int value) {
        SudokuUtil.validateValue(value);
        return SquarePossibilities.of(possibilities & ~(1 << value));
    }
    
    private static final SquarePossibilities[] ALL = new SquarePossibilities[1 << 9];
    static {
        for (int i = 0; i < 1 << 9; i++) {
            ALL[i] = new SquarePossibilities(i << 1);
        }
    }
    private static SquarePossibilities of(int possibilities) {
        return ALL[possibilities >> 1];
    }
    
    public static final SquarePossibilities EMPTY = SquarePossibilities.of(0);
    public static final SquarePossibilities FULL = SquarePossibilities.of((1 << 10) - 2);
    
    public static SquarePossibilities only(int value) {
        SudokuUtil.validateValue(value);
        return SquarePossibilities.of(1 << value);
    }
    
}
