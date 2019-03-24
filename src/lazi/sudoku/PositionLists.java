package lazi.sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PositionLists {
    
    private static List<Position> ALL;
    public static List<Position> all() {
        if (ALL == null) {
            List<Position> list = new ArrayList<>();
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    list.add(Position.of(row, col));
                }
            }
            ALL = Collections.unmodifiableList(list);
        }
        return ALL;
    }
    
    private static List<List<Position>> ROWS;
    public static List<Position> row(int rowNum) {
        if (ROWS == null) {
            ROWS = new ArrayList<>();
            for (int row = 0; row < 9; row++) {
                List<Position> list = new ArrayList<>();
                for (int col = 0; col < 9; col++) {
                    list.add(Position.of(row, col));
                }
                ROWS.add(Collections.unmodifiableList(list));
            }
        }
        return ROWS.get(rowNum);
    }
    
    private static List<List<Position>> COLS;
    public static List<Position> col(int colNum) {
        if (COLS == null) {
            COLS = new ArrayList<>();
            for (int col = 0; col < 9; col++) {
                List<Position> list = new ArrayList<>();
                for (int row = 0; row < 9; row++) {
                    list.add(Position.of(row, col));
                }
                COLS.add(Collections.unmodifiableList(list));
            }
        }
        return COLS.get(colNum);
    }
    
    private static List<List<Position>> BOXES;
    public static List<Position> box(int boxNum) {
        if (BOXES == null) {
            BOXES = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                List<Position> list = new ArrayList<>();
                for (int j = 0; j < 9; j++) {
                    list.add(Position.of((i / 3) * 3 + j / 3, (i % 3) * 3 + j % 3));
                }
                BOXES.add(Collections.unmodifiableList(list));
            }
        }
        return BOXES.get(boxNum);
    }
    
    private static List<List<Position>> GROUPS;
    public static List<List<Position>> groups() {
        if (GROUPS == null) {
            List<List<Position>> list = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                list.add(row(i));
            }
            for (int i = 0; i < 9; i++) {
                list.add(col(i));
            }
            for (int i = 0; i < 9; i++) {
                list.add(box(i));
            }
            GROUPS = Collections.unmodifiableList(list);
        }
        return GROUPS;
    }
    
    
}
