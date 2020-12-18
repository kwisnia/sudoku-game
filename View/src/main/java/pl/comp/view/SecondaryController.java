package pl.comp.view;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import pl.comp.model.BacktrackingSudokuSolver;
import pl.comp.model.Difficulty;
import pl.comp.model.SudokuBoard;

public class SecondaryController {

    public Button secondaryButton;
    public GridPane sudokuBoardGrid;
    private Difficulty difficulty;
    private final SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
    private SudokuBoard sudokuBoardClone;
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    @FXML
    private void switchToPrimary() throws IOException {
        Window.setRoot("primary");
    }

    public void startGame() {
        sudokuBoard.setDifficulty(difficulty);
        sudokuBoard.solveGame();
        sudokuBoardClone = sudokuBoard.clone();
        sudokuBoard.clearFields();
        fillGrid();
    }

    public void fillGrid() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TextField textField = new TextField();
                textField.setMaxSize(100, 65);
                textField.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 24));
                textField.setStyle("-fx-background-color: #F0EBD7;-fx-alignment: center; -fx-border-style: solid");
                if (sudokuBoard.get(i, j) != 0) {
                    textField.setDisable(true);
                    textField.setStyle("-fx-background-color: #F0EBD7;-fx-opacity: 100%;-fx-alignment: center;-fx-border-style: solid");
                    textField.setText(String.valueOf(sudokuBoard.get(i, j)));
                }
                sudokuBoardGrid.add(textField, i, j);
            }
        }
    }
}