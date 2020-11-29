import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;


/**
 * The type Sudoku board.
 */
public class SudokuBoard implements PropertyChangeListener {
    final List<SudokuField> board = Arrays.asList(new SudokuField[81]);
    private final SudokuSolver sudokuSolver;
    private boolean checkFlag = false;

    public SudokuBoard(SudokuSolver sudokuSolver) {
        this.sudokuSolver = sudokuSolver;
        for (int i = 0; i < 81; i++) {
            board.set(i, new SudokuField(0));
        }
        board.forEach(f -> f.addPropertyChangeListener(this));
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
            return board.get(i * 9 + j).getFieldValue();
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public SudokuRow getRow(int y) {
        return new SudokuRow(board.subList(y * 9, y * 9 + 9));
    }

    public SudokuColumn getColumn(int x) {
        SudokuField[] column = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            column[i] = board.get(i * 9 + x);
        }
        return new SudokuColumn(Arrays.asList(column));
    }

    public SudokuBox getBox(int x, int y) {
        SudokuField[] box = new SudokuField[9];
        int index = 0;
        int boxBeginningRow = x - x % 3;
        int boxBeginningColumn = y - y % 3;
        for (int i = boxBeginningRow; i < boxBeginningRow + 3; i++) {
            for (int j = boxBeginningColumn; j < boxBeginningColumn + 3; j++) {
                box[index++] = board.get(i * 9 + j);
            }
        }
        return new SudokuBox(Arrays.asList(box));
    }

    public void set(int i, int j, int number) {
        if (number < 0 || number > 9) {
            throw new InputMismatchException("Number must be in range from 1 to 9");
        } else {
            try {
                board.get(i * 9 + j).setFieldValue(number);
            } catch (IndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void setCheckFlag(boolean checkFlag) {
        this.checkFlag = checkFlag;
    }

    private void initializeBoard() {
        Random random = new Random();
        int insertedNumber = 1;
        while (insertedNumber <= 9) {
            int[] positions = {random.nextInt(8), random.nextInt(8)};
            if (board.get(positions[0] * 9 + positions[1]).getFieldValue() == 0) {
                board.get(positions[0] * 9 + positions[1]).setFieldValue(insertedNumber);
                insertedNumber++;
            }
        }
    }

    /**
     * Fill board.
     */

    public void solveGame() {
        board.forEach(f -> f.setFieldValue(0));
        initializeBoard();
        sudokuSolver.solve(this);
        checkBoard();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("board", board)
                .toString();
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

    public void propertyChange(PropertyChangeEvent evt) {
        if (checkFlag && !this.checkBoard()) {
            System.out.println("Wrong input: " + evt.getNewValue());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SudokuBoard that = (SudokuBoard) o;
        return Objects.equal(board, that.board);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(board);
    }
}