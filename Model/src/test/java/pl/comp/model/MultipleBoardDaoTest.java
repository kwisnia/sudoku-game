package pl.comp.model;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

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
        } catch (Exception e) {
            throw new DaoException("DaoWriteException", e.getCause());
        }
        System.gc();
    }

    @Test
    void writeExceptionTest() throws Exception {
        SudokuBoard[] testArray = {new SudokuBoard(new BacktrackingSudokuSolver())};
        try (Dao<SudokuBoard[]> fmDao = new FileMultipleBoardsDao("")) {
            assertThrows(DaoException.class, () -> fmDao.write(testArray));
        }
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
        } catch (Exception e) {
            throw new DaoException("DaoReadException", e.getCause());
        }
        System.gc();
    }

    @Test
    void readExceptionTest() throws Exception {
        try (Dao<SudokuBoard[]> fBoardDao = new FileMultipleBoardsDao("cos")) {
            assertThrows(DaoException.class, fBoardDao::read);
            fBoardDao.read();
        } catch (DaoException e) {
            assertEquals(ResourceBundle.getBundle("Exceptions").getString("DaoReadException"), e.getLocalizedMessage());
        }
        System.gc();
    }

    @Test
    void daoExceptionWrongKeyTest() {
        try {
            throw new DaoException("Najman");
        } catch (DaoException e) {
            assertEquals(ResourceBundle.getBundle("Exceptions").getString("noKeyError") + e.getMessage(),
                    e.getLocalizedMessage());
        }
    }
}
