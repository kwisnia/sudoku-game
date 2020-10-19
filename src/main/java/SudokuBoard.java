import java.util.*;

public class SudokuBoard {
    private final int[][] sudokuBoard = new int[9][9];

    public int[][] getSudokuBoard() {
        return sudokuBoard;
    }

    public void fillBoard() {
        int fill = 1;
        for (int i = 0; i <= 2; i++){
            for (int j = 0; j <= 2; j++){
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
        /*  -1 gdy wartości są nie właściwe
            0 gdy wartości w kolumnie się powtarzają
            1 gdy wszystkie wartości są dobre :)
         */
        return 1;
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
        /*  -1 gdy wartości są nie właściwe
            0 gdy wartości w kolumnie się powtarzają
            1 gdy wszystkie wartości są dobre :)
         */
    }
    public  int validSubSq() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Set<Integer> set = new HashSet<>();
                for (int k = 0; k < ; k++) {
                    for (int l = 0; l < ; l++) {
                        
                    }
                }
            }
        }
    }
}
