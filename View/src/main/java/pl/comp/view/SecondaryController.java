package pl.comp.view;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import pl.comp.model.BacktrackingSudokuSolver;
import pl.comp.model.Difficulty;
import pl.comp.model.SudokuBoard;

public class SecondaryController {

    public Button secondaryButton;
    public GridPane sudokuBoardGrid;
    private Difficulty difficulty;

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void initialize() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoard.setDifficulty(difficulty);
        sudokuBoard.clearFields();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudokuBoardGrid.add(new TextField(), i, j);
            }
        }
    }

    @FXML
    private void switchToPrimary() throws IOException {
        Window.setRoot("primary");
    }
}