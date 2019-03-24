package lazi.sudoku;

import java.util.List;
import java.util.Random;

public class SudokuUtil {
    
    private final static Random RANDOM = new Random();
    
    public static int validateValue(int value) {
        if (value < 1 || value > 9) {
            throw new IllegalArgumentException("Illegal Value :" + value);
        }
        return value;
    }
    
    public static int[] shuffledValues() {
        int[] values = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, };
        for (int i = 9; i > 1; i--) {
            int r = RANDOM.nextInt(i);
            if (r != i - 1) {
                int tmp = values[i - 1];
                values[i - 1] = values[r];
                values[r] = tmp;
            }
        }
        return values;
    }
    
    public static <E> E randomElement(List<E> list) {
        return list.get(RANDOM.nextInt(list.size()));
    }
    
}
