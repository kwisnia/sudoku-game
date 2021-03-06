package pl.comp.model;

import org.junit.jupiter.api.Test;
import pl.comp.model.BacktrackingSudokuSolver;
import pl.comp.model.SudokuBoard;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.ResourceBundle;

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
    void setNumberOutOfRange() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        assertThrows(WrongInputException.class, () -> sudokuBoard.set(0, 0, 10));
        assertThrows(WrongInputException.class, () -> sudokuBoard.set(0, 0, -1));
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
        assertTrue(baos.toString().trim().matches(".*Wrong input: 1"));
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

    @Test
    void BoardCloneTest() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard sudokuBoardClone = sudokuBoard.clone();
        assertEquals(sudokuBoard, sudokuBoardClone);
        sudokuBoard.set(0, 0, 3);
        assertNotEquals(sudokuBoard, sudokuBoardClone);
    }

    @Test
    void BoardDifficultyTest() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoard.solveGame();
        sudokuBoard.setDifficulty(Difficulty.EASY);
        sudokuBoard.clearFields();
        assertEquals(18, checkEmptySpots(sudokuBoard));
        sudokuBoard.solveGame();
        sudokuBoard.setDifficulty(Difficulty.MEDIUM);
        sudokuBoard.clearFields();
        assertEquals(36, checkEmptySpots(sudokuBoard));
        sudokuBoard.solveGame();
        sudokuBoard.setDifficulty(Difficulty.HARD);
        sudokuBoard.clearFields();
        assertEquals(48, checkEmptySpots(sudokuBoard));
    }

    private int checkEmptySpots(SudokuBoard sudokuBoard) {
        int counter = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudokuBoard.get(i, j) == 0) {
                    counter++;
                }
            }
        }
        return counter;
    }

    @Test
    void getPropertyTest() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        assertNotNull(sudokuBoard.getProperty(0, 0));
    }

}