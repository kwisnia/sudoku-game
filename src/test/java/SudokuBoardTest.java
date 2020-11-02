import org.junit.jupiter.api.Test;

import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardTest {
    @Test
    void fillBoard() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoard.solveGame();
        for (int i = 0; i < 9; i++) {
            assertTrue(sudokuBoard.getRow(i).verify());
            assertTrue(sudokuBoard.getColumn(i).verify());
        }
        for (int i = 0; i < 9; i = i + 3) {
            for (int j = 0; j < 9; j = j + 3) {
                assertTrue(sudokuBoard.getBox(i, j).verify());
            }
        }
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