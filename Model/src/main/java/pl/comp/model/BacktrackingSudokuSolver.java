package pl.comp.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BacktrackingSudokuSolver implements SudokuSolver {

    @Override
    public void solve(SudokuBoard sudokuBoard) {
        List<Integer> numbersToFill = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collections.shuffle(numbersToFill);
        solveBoard(sudokuBoard, numbersToFill);
    }

    private boolean solveBoard(SudokuBoard sudokuBoard, List<Integer> shuffledNumbers) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudokuBoard.get(i, j) == 0) {
                    for (int k = 0; k < 9; k++) {
                        sudokuBoard.set(i, j, shuffledNumbers.get(k));
                        if (sudokuBoard.getBox(i, j).verify()
                                && sudokuBoard.getColumn(j).verify()
                                && sudokuBoard.getRow(i).verify()) {
                            if (solveBoard(sudokuBoard, shuffledNumbers)) {
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
