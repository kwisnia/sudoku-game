package pl.comp.model;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SudokuBoardDaoTest {
    @Test
    void writeTest() throws Exception {
        SudokuBoard testBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        testBoard.solveGame();
        try (Dao<SudokuBoard> fDao = SudokuBoardDaoFactory.getFileDao("test.txt")) {
            fDao.write(testBoard);
            assertNotEquals(new File("test.txt").length(), 0);
        }
        System.gc();
    }
    @Test
    void readTest() throws Exception {
        SudokuBoard testBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        testBoard.solveGame();
        try (Dao<SudokuBoard> fBoardDao = SudokuBoardDaoFactory.getFileDao("test_1.txt")) {
            fBoardDao.write(testBoard);
            assertEquals(testBoard, fBoardDao.read());
        }
        System.gc();
    }
}
