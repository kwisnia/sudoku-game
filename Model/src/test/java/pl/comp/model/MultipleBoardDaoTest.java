package pl.comp.model;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MultipleBoardDaoTest {
    @Test
    void writeTest() throws Exception {
        SudokuBoard testBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard testBoard2 = new SudokuBoard(new BacktrackingSudokuSolver());
        testBoard.solveGame();
        testBoard2.solveGame();
        SudokuBoard[] testArray = {testBoard, testBoard2};
        try (Dao<SudokuBoard[]> fDao = new FileMultipleBoardsDao("testMultiple.txt")) {
            fDao.write(testArray);
            assertNotEquals(new File("testMultiple.txt").length(), 0);
        }
        System.gc();
    }
    @Test
    void readTest() throws Exception {
        SudokuBoard testBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard testBoard2 = new SudokuBoard(new BacktrackingSudokuSolver());
        testBoard.solveGame();
        testBoard2.solveGame();
        SudokuBoard[] testArray = {testBoard, testBoard2};

        testBoard.solveGame();
        try (Dao<SudokuBoard[]> fBoardDao = new FileMultipleBoardsDao("testMultiple_1.txt")) {
            fBoardDao.write(testArray);
            SudokuBoard[] readBoard = fBoardDao.read();
            assertEquals(testBoard, readBoard[0]);
            assertEquals(testBoard2, readBoard[1]);
        }
        System.gc();
    }
}
