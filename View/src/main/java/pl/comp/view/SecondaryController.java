package pl.comp.view;

import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import pl.comp.model.BacktrackingSudokuSolver;
import pl.comp.model.Difficulty;
import pl.comp.model.FileSudokuBoardDao;
import pl.comp.model.SudokuBoard;

public class SecondaryController extends javafx.stage.Window {

    private static final String REGEX_VALID_INTEGER = "[1-9]?";
    public Button secondaryButton;
    public GridPane sudokuBoardGrid;
    private final SudokuBoard currentBoard = new SudokuBoard(new BacktrackingSudokuSolver());
    private SudokuBoard solvedBoard;
    private SudokuBoard startBoard;
    final FileChooser fileChooser = new FileChooser();

    public void setDifficulty(Difficulty difficulty) {
        this.currentBoard.setDifficulty(difficulty);
    }

    @FXML
    private void switchToPrimary() throws IOException {
        Window.setRoot("primary");
    }

    public void startGame() {
        currentBoard.solveGame();
        solvedBoard = currentBoard.clone();
        currentBoard.clearFields();
        startBoard = currentBoard.clone();
        fillGrid();
    }

    public void fillGrid() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TextField textField = new TextField();
                textField.setMaxSize(100, 65);
                textField.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 24));
                textField.setStyle("-fx-background-color: #F0EBD7;"
                        + "-fx-alignment: center;"
                        + " -fx-border-style: solid");
                if (currentBoard.get(i, j) != 0) {
                    textField.setDisable(true);
                    textField.setStyle("-fx-background-color: #F0EBD7;"
                            + "-fx-opacity: 100%;"
                            + "-fx-alignment: center;"
                            + "-fx-border-style: solid");
                    textField.setText(String.valueOf(currentBoard.get(i, j)));
                }
                textField.setOnKeyPressed(e -> {
                    if (e.getText().matches("[1-9]")) {
                        textField.setText(e.getText());
                    }
                });
                textField.setTextFormatter(new TextFormatter<>(this::filter));
                textField.textProperty().addListener((observableValue, s, t1) -> {
                    try {
                        currentBoard.set(GridPane.getRowIndex(textField),
                                GridPane.getColumnIndex(textField),
                                Integer.parseInt(t1));
                    } catch (NumberFormatException e) {
                        currentBoard.set(GridPane.getRowIndex(textField),
                                GridPane.getColumnIndex(textField),
                                0);
                    }
                });
                sudokuBoardGrid.add(textField, j, i);
            }
        }
    }

    public void resetGame() {
        for (Node c : sudokuBoardGrid.getChildren().subList(0, 81)) {
            if (startBoard.get(GridPane.getRowIndex(c), GridPane.getColumnIndex(c)) == 0) {
                ((TextField) c).setText("");
            }
        }
    }

    private TextFormatter.Change filter(TextFormatter.Change change) {
        if (!change.getControlNewText().matches(REGEX_VALID_INTEGER)) {
            change.setText("");
        }
        return change;
    }

    public void load() {
        try {
            File loadedFile = fileChooser.showOpenDialog(this);
            fileChooser.setTitle("Wczytaj plik");
            FileSudokuBoardDao fsdb = new FileSudokuBoardDao(loadedFile.getAbsolutePath());
            SudokuBoard loadedBoard = fsdb.read();
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    for (Node k: sudokuBoardGrid.getChildren().subList(0, 81)) {
                        TextField field = (TextField) k;
                        field.setEditable(true);
                        if (currentBoard.get(GridPane.getRowIndex(k), GridPane.getColumnIndex(k)) != 0) {
                            field.setEditable(false);
                            field.setText(String.valueOf(loadedBoard.get(GridPane.getRowIndex(k), GridPane.getColumnIndex(k))));
                        } else {
                            field.setText("");
                        }
                    }
                }
            }
        } catch (IOException | ClassNotFoundException | NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.NONE, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void save() {
        try {
            File saveFile = fileChooser.showSaveDialog(this);
            fileChooser.setTitle("Zapisz do pliku");
            FileSudokuBoardDao fsbd = new FileSudokuBoardDao(saveFile.getAbsolutePath());
            fsbd.write(this.currentBoard);
        } catch (IOException | NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.NONE, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }
}