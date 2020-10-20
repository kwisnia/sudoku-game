import java.util.*;

public class SudokuBoard {
    private final int[][] sudokuBoard = new int[9][9];

    public int getNumberFromPosition(int i, int j) {
        try {
            return sudokuBoard[i][j];
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public void initializeBoard() {
        Random random = new Random();
        for (int i = 1; i <= 9; i++) {
            int[] positions = {random.nextInt(8), random.nextInt(8)};
            if (sudokuBoard[positions[0]][positions[1]] == 0) {
                sudokuBoard[positions[0]][positions[1]] = i;
            } else {
                i--;
            }
        }
    }

    public void fillBoard() {

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

    public boolean validRow(int row) {
        Set<Integer> set = new HashSet<>();
        for (int value : sudokuBoard[row]) {
            if (value < 0 || value > 9) {
                return false;
            } else if (value != 0) {
                if (!set.add(value)) {
                    return false;
                }
            }
        }
        return true;
        /*
            -1 gdy wartości są nie właściwe
            0 gdy wartości w kolumnie się powtarzają
            1 gdy wszystkie wartości są dobre :)
        */
    }

    public boolean validCol(int col) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < 9; i++) {
            if (sudokuBoard[i][col] < 0 || sudokuBoard[i][col] > 9) {
                return false;
            } else if (sudokuBoard[i][col] != 0) {
                if (!set.add(sudokuBoard[i][col])) {
                    return false;
                }
            }
        }
        return true;
        /*
            -1 gdy wartości są nie właściwe
            0 gdy wartości w kolumnie się powtarzają
            1 gdy wszystkie wartości są dobre :)
        */
    }

    public boolean validSubSq() {
        for (int i = 0; i < 9; i = i + 3) {
            for (int j = 0; j < 9; j = j + 3) {
                Set<Integer> set = new HashSet<>();
                for (int k = 0; k < i + 3; k++) {
                    for (int l = 0; l < j + 3; l++) {
                        if (sudokuBoard[k][l] < 0 || sudokuBoard[k][l] > 9) {
                            return false;
                        } else if (sudokuBoard[k][l] != 0) {
                            if (!set.add(sudokuBoard[k][l])) {
                                return false;
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
        return true;
    }

    public boolean validBoard() {
        for (int i = 0; i < 9; i++) {
            if (!validRow(i)) {
                return false;
            }
            if (!validCol(i)) {
                return false;
            }
        }
        return validSubSq();
    }
}

