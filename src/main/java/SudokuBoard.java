import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


/**
 * The type Sudoku board.
 */
public class SudokuBoard {
    private final int[][] board = new int[9][9];


    /**
     * Gets number from position.
     *
     * @param i the row
     * @param j the column
     * @return the number from position
     */
    public int getNumberFromPosition(int i, int j) {
        try {
            return board[i][j];
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    private void initializeBoard() {
        Random random = new Random();
        for (int i = 1; i <= 9; i++) {
            int[] positions = {random.nextInt(8), random.nextInt(8)};
            if (board[positions[0]][positions[1]] == 0) {
                board[positions[0]][positions[1]] = i;
            } else {
                i--;
            }
        }
    }

    /**
     * Fill board.
     */

    public void fillBoard() {
        for (int[] row: board) {
            Arrays.fill(row, 0);
        }
        initializeBoard();
        solveBoard();
    }

    /**
     * Solve board boolean.
     *
     * @return the boolean
     */

    private boolean solveBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                    if (board[i][j] == 0) {
                        for (int k = 1; k < 10; k++) {
                        board[i][j] = k;
                        if (validBoard()) {
                            if (solveBoard()) {
                                return true;
                            }
                        } else {
                            board[i][j] = 0;
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
                stringBuilder.append(board[i][j])
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
        for (int value : board[row]) {
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
            if (board[i][col] < 0 || board[i][col] > 9) {
                return false;
            } else if (board[i][col] != 0) {
                if (!set.add(board[i][col])) {
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
                        if (board[k][l] < 0 || board[k][l] > 9) {
                            return false;
                        } else if (board[k][l] != 0) {
                            if (!set.add(board[k][l])) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Valid board boolean.
     *
     * @return the boolean
     */

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