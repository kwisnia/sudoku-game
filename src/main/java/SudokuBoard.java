import java.util.*;

public class SudokuBoard {
    private final int[][] sudokuBoard = new int[9][9];

    public int[][] getSudokuBoard() {
        return sudokuBoard;
    }

    public void fillBoard() {
        int fill = 1;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                    sudokuBoard[i][j] = fill;
                    fill++;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        final String HORIZONTAL_BREAK = "-------------------------\n";
        stringBuilder.append(HORIZONTAL_BREAK);
        for (int i = 0; i < 9; i++) {
            if (i == 3 || i == 6) {
                stringBuilder.append(HORIZONTAL_BREAK);
            }
            stringBuilder.append("| ");
            for (int j = 0; j < 9; j++) {
                stringBuilder.append(sudokuBoard[i][j])
                        .append(" ");
                if (j == 2 || j == 5) {
                    stringBuilder.append("| ");
                }
            }
            stringBuilder.append("|\n");

        }
        stringBuilder.append(HORIZONTAL_BREAK);
        return stringBuilder.toString();
    }

    public int validRow(int row) {
        int[] tempTab = sudokuBoard[row];
        Set<Integer>set = new HashSet<Integer>();
        for (int value : tempTab) {
            if (value < 0 || value > 9) {
                return -1;
            } else if (!set.add(value)) {
                return 0;
            }
        }
        return 1;
        /*
            -1 gdy wartości są nie właściwe
            0 gdy wartości w kolumnie się powtarzają
            1 gdy wszystkie wartości są dobre :)
        */
    }

    public int validCol(int col) {
        Set<Integer>set = new HashSet<Integer>();
        for (int i = 0; i < 9; i++) {
            if(sudokuBoard[i][col] < 0 || sudokuBoard[i][col] > 9) {
                return -1;
            } else if(sudokuBoard[i][col] != 0) {
                if(!set.add(sudokuBoard[i][col])){
                    return 0;
                }
            }
        }
        return 1;
        /*
            -1 gdy wartości są nie właściwe
            0 gdy wartości w kolumnie się powtarzają
            1 gdy wszystkie wartości są dobre :)
        */
    }
    public int validSubSq() {
        for (int i = 0; i < 9; i = i + 3) {
            for (int j = 0; j < 9; j = j+ 3) {
                Set<Integer> set = new HashSet<>();
                for (int k = 0; k < i + 3; k++) {
                    for (int l = 0; l < j + 3; l++) {
                        if (sudokuBoard[k][l] < 0 || sudokuBoard[k][l] > 9) {
                            return -1;
                        }
                        else if (sudokuBoard[k][l] != 0) {
                            if(!set.add(sudokuBoard[k][l])) {
                                return 0;
                            }
                        }
                    }
                }
            }
        }
        /*
            -1 gdy wartości są nie właściwe
            0 gdy wartości w kolumnie się powtarzają
            1 gdy wszystkie wartości są dobre :)
        */
        return 1;
    }

    public int validBoard() {
        for(int i = 0; i < 9; i++) {
            int valid_1 = validRow(i);
            int valid_2 = validCol(i);
            if (valid_1 < 1 || valid_2 < 1) {
                return -1;
            }
            int valid_3 = validSubSq();
            if (valid_3 < 1) {
                return 1;
            } else {
                return 0;
            }
        }
        return 1;
    }
}
