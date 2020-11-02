public class BacktrackingSudokuSolver implements SudokuSolver {

    @Override
    public void solve(SudokuBoard sudokuBoard) {
        solveBoard(sudokuBoard);
    }

    private boolean solveBoard(SudokuBoard sudokuBoard) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudokuBoard.get(i, j) == 0) {
                    for (int k = 1; k < 10; k++) {
                        sudokuBoard.set(i, j, k);
                        if (sudokuBoard.getBox(i, j).verify()
                                && sudokuBoard.getColumn(j).verify()
                                && sudokuBoard.getRow(i).verify()) {
                            if (solveBoard(sudokuBoard)) {
                                return true;
                            }
                        } else {
                            sudokuBoard.set(i, j, 0);
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
}
