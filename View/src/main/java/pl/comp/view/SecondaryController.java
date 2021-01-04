package pl.comp.view;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import pl.comp.model.BacktrackingSudokuSolver;
import pl.comp.model.Difficulty;
import pl.comp.model.FileSudokuBoardDao;
import pl.comp.model.SudokuBoard;

public class SecondaryController extends javafx.stage.Window {

    private static final String REGEX_VALID_INTEGER = "[1-9]?";
    public Button secondaryButton;
    public GridPane sudokuBoardGrid;
    private SudokuBoard currentBoard = new SudokuBoard(new BacktrackingSudokuSolver());
    private SudokuBoard solvedBoard;
    private SudokuBoard startBoard;
    private final StringConverter<Number> converter = new NumberStringConverter();
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
                Bindings.bindBidirectional(textField.textProperty(), currentBoard.getProperty(i, j), converter);
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
            fileChooser.setTitle("Wczytaj plik");
            File loadedFile = fileChooser.showOpenDialog(this);
            try (FileSudokuBoardDao fsdb = new FileSudokuBoardDao(loadedFile.getAbsolutePath())) {
                currentBoard = fsdb.read();
                startBoard = fsdb.read();
                for (Node k : sudokuBoardGrid.getChildren().subList(0, 81)) {
                    TextField field = (TextField) k;
                    field.setEditable(true);
                    Bindings.bindBidirectional(field.textProperty(),
                            currentBoard.getProperty(GridPane.getRowIndex(k), GridPane.getColumnIndex(k)), converter);
                    if (currentBoard.get(GridPane.getRowIndex(k), GridPane.getColumnIndex(k)) ==
                            startBoard.get(GridPane.getRowIndex(k), GridPane.getColumnIndex(k))) {
                        field.setEditable(false);
                    }
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.NONE, "Error reading file!", ButtonType.OK);
                alert.setResizable(false);
                alert.setTitle("Error");
                alert.showAndWait();
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.NONE, "No file chosen!", ButtonType.OK);
            alert.setResizable(false);
            alert.setTitle("Error");
            alert.showAndWait();
        }
        System.out.println(currentBoard.printBoard());
        System.out.println(startBoard.printBoard());
    }

    public void save() {
        try {
            File saveFile = fileChooser.showSaveDialog(this);
            fileChooser.setTitle("Save file");
            FileSudokuBoardDao fsbd = new FileSudokuBoardDao(saveFile.getAbsolutePath());
            fsbd.write(this.currentBoard);
            fsbd.write(this.startBoard);
        } catch (IOException | NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.NONE, "No file chosen!", ButtonType.OK);
            alert.setResizable(false);
            alert.setTitle("Error");
            alert.showAndWait();
        }
    }
}