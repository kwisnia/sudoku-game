package pl.comp.model;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcSudokuBoardDaoTest {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard boardtwo;

        @Test
        void writeAndReadTest() throws Exception {
            try (Dao<SudokuBoard> jdbcDao = new JdbcSudokuBoardDao("testFilename")) {
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
            try (Dao<SudokuBoard> jdbcDao = new JdbcSudokuBoardDao("testFilename")) {
                assertEquals(((JdbcSudokuBoardDao) jdbcDao).getName(), "testFilename");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}

