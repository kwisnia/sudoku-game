package pl.comp.view;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import pl.comp.model.BacktrackingSudokuSolver;
import pl.comp.model.SudokuBoard;

public class SecondaryController {

    public Button secondaryButton;
    public GridPane sudokuBoardGrid;

    public void initialize() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
    }

    @FXML
    private void switchToPrimary() throws IOException {
        Window.setRoot("primary");
    }
}