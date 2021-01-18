package pl.comp.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
        System.out.println(testBoard.getRow(0).hashCode());
        assertTrue(testBoard.getRow(0).toString()
                .matches("SudokuRow\\{fields=\\[(SudokuField\\{value=\\d}(,\\s)*){9}]}"));
    }

    @Test
    void EqualHashForUnequalObjects() {
        List<SudokuField> testList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            testList.add(new SudokuField(0));
        }
        SudokuPiece testPiece1 = new SudokuRow(testList);
        SudokuPiece testPiece2 = new SudokuColumn(testList);
        assertEquals(new SudokuRow(testList).hashCode(), new SudokuColumn(testList).hashCode());
        assertNotEquals(testPiece1, testPiece2);
    }

    @Test
    void cloneTest() throws CloneNotSupportedException {

        SudokuPiece row1 = new SudokuRow(new ArrayList<>());
        SudokuPiece row2 = row1.clone();
        assertEquals(row1, row2);
    }
}
