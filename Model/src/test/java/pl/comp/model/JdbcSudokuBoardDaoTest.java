package pl.comp.model;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcSudokuBoardDaoTest {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard boardtwo;

        @Test
        void writeAndReadTest() throws Exception {
            try (Dao<SudokuBoard> jdbcDao = SudokuBoardDaoFactory.getJdbcDao("testFilename22")) {
                board.solveGame();
                jdbcDao.write(board);
                boardtwo = jdbcDao.read();
                assertEquals(board, boardtwo);
                boardtwo.set(0, 0, 0);
                assertNotEquals(board, boardtwo);
            }
        }

        @Test
        public void filenameTest() {
            try (JdbcSudokuBoardDao jdbcDao = new JdbcSudokuBoardDao("testFilename2")) {
                assertEquals(jdbcDao.getName(), "testFilename2");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Test
        public void exceptionTest() {
            try (Dao<SudokuBoard> jdbcDao = SudokuBoardDaoFactory.getJdbcDao("");
            Dao<SudokuBoard> failDao = SudokuBoardDaoFactory.getJdbcDao("test33")) {
                assertThrows(DaoException.class, failDao::read);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}

