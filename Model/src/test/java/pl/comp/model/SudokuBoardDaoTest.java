package pl.comp.model;

import org.junit.jupiter.api.Test;
import pl.comp.model.BacktrackingSudokuSolver;
import pl.comp.model.Dao;
import pl.comp.model.SudokuBoard;
import pl.comp.model.SudokuBoardDaoFactory;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoardDaoTest {
    @Test
    void writeTest() throws Exception {
        SudokuBoard testBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        testBoard.solveGame();
        try (Dao<SudokuBoard> fDao = SudokuBoardDaoFactory.getFileDao("test.txt")) {
            fDao.write(testBoard);
            assertNotEquals(new File("test.txt").length(), 0);
        }
    }
    @Test
    void readTest() throws Exception {
        SudokuBoard testBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        testBoard.solveGame();
        try (Dao<SudokuBoard> fBoardDao = SudokuBoardDaoFactory.getFileDao("test_1.txt")) {
            fBoardDao.write(testBoard);
            assertEquals(testBoard, fBoardDao.read());
        }
    }
}
