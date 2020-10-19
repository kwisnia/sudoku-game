import java.util.Random;

public class SudokuBoard {
    private final int[][] sudokuBoard = new int[9][9];

    public int[][] getSudokuBoard() {
        return sudokuBoard;
    }

    public void fillBoard() {
        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            for(int j = 0; j < 2; j++) {
                sudokuBoard[i][j] = random.nextInt();
            }

        }
    }
}
