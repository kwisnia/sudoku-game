package pl.comp.model;

import org.junit.jupiter.api.Test;
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
        } catch (Exception e) {
            throw new DaoReadException(e.getCause());
        }
        System.gc();
    }

    @Test
    void writeExceptionTest() throws Exception {
        SudokuBoard testBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        try (Dao<SudokuBoard> fDao = new FileSudokuBoardDao("")) {
            assertThrows(DaoWriteException.class, () -> fDao.write(testBoard));
        }
    }


    @Test
    void readTest() throws Exception {
        SudokuBoard testBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        testBoard.solveGame();
        try (Dao<SudokuBoard> fBoardDao = SudokuBoardDaoFactory.getFileDao("test_1.txt")) {
            fBoardDao.write(testBoard);
            assertEquals(testBoard, fBoardDao.read());
        } catch (Exception e) {
            throw new DaoReadException(e.getCause());
        }
        System.gc();
    }

    @Test
    void readExceptionTest() throws Exception {
        try (Dao<SudokuBoard> fBoardDao = new FileSudokuBoardDao("cos")) {
            assertThrows(DaoReadException.class, fBoardDao::read);
        }
        System.gc();
    }
}