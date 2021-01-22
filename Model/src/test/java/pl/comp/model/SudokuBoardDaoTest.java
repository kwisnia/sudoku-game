package pl.comp.model;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SudokuBoardDaoTest {
    @Test
    void writeTest() throws Exception {
        SudokuBoard testBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        testBoard.solveGame();
        try (Dao<SudokuBoard> fDao = SudokuBoardDaoFactory.getFileDao("test.txt")) {
            fDao.write(testBoard);
            assertNotEquals(new File("test.txt").length(), 0);
        } catch (Exception e) {
            throw new DaoException("DaoReadException", e.getCause());
        }
        System.gc();
    }

    @Test
    void writeExceptionTest() throws Exception {
        SudokuBoard testBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        try (Dao<SudokuBoard> fDao = new FileSudokuBoardDao("")) {
            assertThrows(DaoException.class, () -> fDao.write(testBoard));
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
            throw new DaoException("DaoReadException", e.getCause());
        }
        System.gc();
    }

    @Test
    void readExceptionTest() throws Exception {
        try (Dao<SudokuBoard> fBoardDao = new FileSudokuBoardDao("cos")) {
            assertThrows(DaoException.class, fBoardDao::read);
            fBoardDao.read();
        } catch (DaoException e) {
            assertEquals(ResourceBundle.getBundle("Exceptions").getString("DaoReadException"), e.getLocalizedMessage());
        }
        System.gc();
    }
}