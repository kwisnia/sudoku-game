import java.util.InputMismatchException;
import java.util.Random;


/**
 * The type Sudoku board.
 */
public class SudokuBoard {
    private final SudokuField[][] board = new SudokuField[9][9];
    private final SudokuSolver sudokuSolver;

    public SudokuBoard(SudokuSolver sudokuSolver) {
        this.sudokuSolver = sudokuSolver;
    }

    /**
     * Gets number from position.
     *
     * @param i the row
     * @param j the column
     * @return the number from position
     */
    public int get(int i, int j) {
        try {
            return board[i][j].getFieldValue();
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public SudokuRow getRow(int y) {
        return new SudokuRow(board[y]);
    }

    public SudokuColumn getColumn(int x) {
        SudokuField[] column = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            column[i] = board[i][x];
        }
        return new SudokuColumn(column);
    }

    public SudokuBox getBox(int x, int y) {
        SudokuField[] box = new SudokuField[9];
        int index = 0;
        int boxBeginningRow = x - x % 3;
        int boxBeginningColumn = y - y % 3;
        for (int i = boxBeginningRow; i < boxBeginningRow + 3; i++) {
            for (int j = boxBeginningColumn; j < boxBeginningColumn + 3; j++) {
                box[index++] = board[i][j];
            }
        }
        return new SudokuBox(box);
    }

    public void set(int i, int j, int number) {
        if (number < 0 || number > 9) {
            throw new InputMismatchException("Number must be in range from 1 to 9");
        } else {
            try {
                board[i][j].setFieldValue(number);
            } catch (IndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void initializeBoard() {
        Random random = new Random();
        int insertedNumber = 1;
        while (insertedNumber <= 9) {
            int[] positions = {random.nextInt(8), random.nextInt(8)};
            if (board[positions[0]][positions[1]].getFieldValue() == 0) {
                board[positions[0]][positions[1]].setFieldValue(insertedNumber);
                insertedNumber++;
            }
        }
    }

    /**
     * Fill board.
     */

    public void solveGame() {
        for (SudokuField[] row : board) {
            for (int i = 0; i < 9; i++) {
                row[i] = new SudokuField(0);
            }
        }
        initializeBoard();
        sudokuSolver.solve(this);
        checkBoard();
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
                stringBuilder.append(board[i][j].getFieldValue())
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

    /**
     * Valid board boolean.
     *
     * @return the boolean
     */

    private boolean checkBoard() {
        for (int i = 0; i < 9; i++) {
            if (!getRow(i).verify()) {
                return false;
            }
            if (!getColumn(i).verify()) {
                return false;
            }
        }
        for (int i = 0; i < 9; i = i + 3) {
            for (int j = 0; j < 9; j = j + 3) {
                if (!getBox(i, j).verify()) {
                    return false;
                }
            }
        }
        return true;
    }
}