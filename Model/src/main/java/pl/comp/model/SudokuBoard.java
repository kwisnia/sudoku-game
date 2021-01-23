package pl.comp.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The type Sudoku board.
 */
public class SudokuBoard implements PropertyChangeListener, Serializable, Cloneable {
    private final List<SudokuField> board = Arrays.asList(new SudokuField[81]);
    private final SudokuSolver sudokuSolver;
    private boolean checkFlag = false;
    private Difficulty difficulty;
    protected static final Logger logger = LoggerFactory.getLogger(SudokuBoard.class);

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public SudokuBoard(SudokuSolver sudokuSolver) {
        this.sudokuSolver = sudokuSolver;
        for (int i = 0; i < 81; i++) {
            board.set(i, new SudokuField(0));
        }
        board.forEach(f -> f.addPropertyChangeListener(this));
        logger.debug(ResourceBundle.getBundle("Exceptions").getString("boardCreate"));
    }

    /**
     * Gets number from position.
     *
     * @param i the row
     * @param j the column
     * @return the number from position
     */
    public int get(int i, int j) {
        return board.get(i * 9 + j).getFieldValue();
    }

    public IntegerProperty getProperty(int i, int j) {
        return board.get(i * 9 + j).getValueProperty();
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
            throw new WrongInputException("InputMismatch");
        } else {
            board.get(i * 9 + j).setFieldValue(number);
        }
    }

    public void setCheckFlag(boolean checkFlag) {
        this.checkFlag = checkFlag;
    }

    /**
     * Fill board.
     */

    public void solveGame() {
        board.forEach(f -> f.setFieldValue(0));
        sudokuSolver.solve(this);
        checkBoard();
    }

    public void clearFields() {
        int counter = 0;
        int loops;
        Random random = new Random();
        switch (difficulty) {
            case EASY -> loops = 18;
            case HARD -> loops = 48;
            default -> loops = 36;
        }
        while (counter < loops) {
            int[] positions = {random.nextInt(9), random.nextInt(9)};
            if (board.get(positions[0] * 9 + positions[1]).getFieldValue() != 0) {
                board.get(positions[0] * 9 + positions[1]).setFieldValue(0);
                counter++;
            }
        }
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
            logger.error("Wrong input: " + evt.getNewValue());
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

    @Override
    public SudokuBoard clone() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(this);
                try (ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                ObjectInputStream ois = new ObjectInputStream(bais)) {
                    return (SudokuBoard) ois.readObject();
                }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        for (SudokuField field :
                board) {
            oos.writeInt(field.getFieldValue());
        }
    }

    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        for (int i = 0; i < 81; i++) {
            board.set(i, new SudokuField(ois.readInt()));
        }
    }
}