import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class SudokuPieceTest {
    @Test
    void PieceEqualsTest() {
        SudokuBoard testBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuRow testRow = new SudokuRow(new ArrayList<>());
        assertEquals(testBoard.getRow(0), testBoard.getRow(1));
        assertNotEquals(testBoard.getRow(0), testBoard.getBox(3, 3));
        assertNotEquals(testBoard.getRow(0), null);
        assertEquals(testBoard.getRow(0), testBoard.getRow(0));
        assertEquals(testRow, testRow);
    }

    @Test
    void PieceHashCodeTest() {
        SudokuBoard testBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        assertEquals(testBoard.getRow(0).hashCode(), testBoard.getRow(1).hashCode());
        testBoard.solveGame();
        assertNotEquals(testBoard.getRow(0).hashCode(), testBoard.getRow(1).hashCode());
    }

    @Test
    void PieceToStringTest() {
        SudokuBoard testBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        testBoard.solveGame();
        assertTrue(testBoard.getRow(0).toString()
                .matches("SudokuRow\\{fields=\\[(SudokuField\\{value=\\d}(,\\s)*){9}]}"));
    }
}
