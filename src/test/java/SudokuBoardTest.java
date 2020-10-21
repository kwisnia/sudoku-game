import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SudokuBoardTest {

    @Test
    void fillBoard() {
        SudokuBoard sudokuBoard = new SudokuBoard();
        sudokuBoard.fillBoard();
        assertTrue(sudokuBoard.validBoard());
    }
    @Test
    void randomBoardCheck() {
        SudokuBoard sudokuBoard = new SudokuBoard();
        sudokuBoard.fillBoard();
        String firstCall = sudokuBoard.toString();
        sudokuBoard.fillBoard();
        assertNotEquals(firstCall, sudokuBoard.toString());
    }
}