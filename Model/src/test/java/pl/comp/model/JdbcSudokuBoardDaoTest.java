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
                System.out.println(board.toString());
                boardtwo = jdbcDao.read();
                System.out.println(boardtwo.toString());
                assertEquals(board, boardtwo);
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

