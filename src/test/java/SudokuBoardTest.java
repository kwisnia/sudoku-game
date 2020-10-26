import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SudokuBoardTest {
    @Test
    void fillBoard() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoard.solveGame();
        assertTrue(sudokuBoard.validBoard());
    }

    @Test
    void randomBoardCheck() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoard.solveGame();
        String firstCall = sudokuBoard.toString();
        sudokuBoard.solveGame();
        assertNotEquals(firstCall, sudokuBoard.toString());
    }
}