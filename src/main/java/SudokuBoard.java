import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


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

    private void initializeBoard() {
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
        for (int[] row:sudokuBoard) {
            Arrays.fill(row, 0);
        }
        initializeBoard();
        solveBoard();
    }

    public boolean solveBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                    if (sudokuBoard[i][j] == 0) {
                        for (int k = 1; k < 10; k++) {
                        sudokuBoard[i][j] = k;
                        if (validBoard()) {
                            if (solveBoard()) {
                                return true;
                            }
                        } else {
                            sudokuBoard[i][j] = 0;
                        }
                    }
                        return false;
                }
            }
        }
        return true;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        final String horizontalBreak = "-------------------------\n";
        stringBuilder.append(horizontalBreak);
        for (int i = 0; i < 9; i++) {
            if (i == 3 || i == 6) {
                stringBuilder.append(horizontalBreak);
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
        stringBuilder.append(horizontalBreak);
        return stringBuilder.toString();
    }

    private boolean validRow(int row) {
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
    }

    private boolean validCol(int col) {
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
    }

    private boolean validSubSq() {
        for (int i = 0; i < 9; i = i + 3) {
            for (int j = 0; j < 9; j = j + 3) {
                Set<Integer> set = new HashSet<>();
                for (int k = i; k < i + 3; k++) {
                    for (int l = j; l < j + 3; l++) {
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