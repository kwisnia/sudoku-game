import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SudokuBoardTest {
    @Test
    void fillBoard() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoard.solveGame();
        System.out.println(sudokuBoard.toString());
        for (int i = 0; i < 9; i++) {
            assertTrue(sudokuBoard.getRow(i).verify());
            assertTrue(sudokuBoard.getColumn(i).verify());
        }
        for (int i = 0; i < 9; i = i + 3) {
            for (int j = 0; j < 9; j = j + 3) {
                assertTrue(sudokuBoard.getBox(i, j).verify());
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertNotEquals(0, sudokuBoard.get(i, j));
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

    @Test
    void listenerTest() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoard.setCheckFlag(true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream oldOutput = System.out;
        System.setOut(ps);
        sudokuBoard.set(0, 0, 1);
        assertEquals(0, baos.toString().length());
        sudokuBoard.set(0, 1, 1);
        assertEquals("Wrong input: 1", baos.toString().trim());
        sudokuBoard.set(0, 1, 0);
        sudokuBoard.set(1, 0, 1);
        sudokuBoard.set(1, 0, 0);
        sudokuBoard.set(2, 2, 1);
        System.setOut(oldOutput);
    }

    @Test
    void BoardEqualsTest() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard sudokuBoard1 = new SudokuBoard(new BacktrackingSudokuSolver());
        assertNotEquals(sudokuBoard, null);
        assertNotEquals(sudokuBoard, 1);
        assertEquals(sudokuBoard, sudokuBoard);
        assertEquals(sudokuBoard1, sudokuBoard);
        sudokuBoard1.set(0, 0, 3);
        assertNotEquals(sudokuBoard, sudokuBoard1);
    }

    @Test
    void BoardHashCodeTest() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard sudokuBoard1 = new SudokuBoard(new BacktrackingSudokuSolver());
        assertEquals(sudokuBoard.hashCode(), sudokuBoard1.hashCode());
    }

}