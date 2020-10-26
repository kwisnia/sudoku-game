import org.junit.jupiter.api.Test;

import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardTest {
    @Test
    void fillBoard() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoard.solveGame();
        assertTrue(sudokuBoard.validBoard());
    }

    @Test
    void randomBoardCheck() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoard.solveGame();
        String firstCall = sudokuBoard.toString();
        sudokuBoard.solveGame();
        assertNotEquals(firstCall, sudokuBoard.toString());
    }

    @Test
    void getException() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        try {
            sudokuBoard.get(10, 10);
        } catch (IndexOutOfBoundsException e) {
            assertEquals(e.getMessage(), "Index 10 out of bounds for length 9");
        }
    }

    @Test
    void setNumberOutOfRange() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        try {
            sudokuBoard.set(0, 0, 10);
        } catch (InputMismatchException e) {
            assertEquals(e.getMessage(), "Number must be in range from 1 to 9");
        }
        try {
            sudokuBoard.set(0,0, -1);
        } catch (InputMismatchException e) {
            assertEquals(e.getMessage(), "Number must be in range from 1 to 9");
        }
    }

    @Test
    void setIndexOutOfRange() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        try {
            sudokuBoard.set(10, 10, 2);
        } catch (IndexOutOfBoundsException e) {
            assertEquals(e.getMessage(), "Number must be in range from 1 to 9");
        }
    }
}