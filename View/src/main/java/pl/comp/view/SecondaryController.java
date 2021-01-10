package pl.comp.view;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
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
import pl.comp.model.*;

public class SecondaryController extends javafx.stage.Window {

    private static final String REGEX_VALID_INTEGER = "[1-9]?";
    public Button secondaryButton;
    public GridPane sudokuBoardGrid;
    private SudokuBoard currentBoard = new SudokuBoard(new BacktrackingSudokuSolver());
    private SudokuBoard solvedBoard;
    private SudokuBoard startBoard;
    private final StringConverter<Number> converter = new NumberStringConverter();
    final FileChooser fileChooser = new FileChooser();
    private ResourceBundle bundle;

    public void initialize() {
        bundle = ResourceBundle.getBundle("pl/comp/view/Sudoku");
    }

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
                }
                textField.setOnKeyPressed(e -> {
                    if (e.getText().matches("[1-9]")) {
                        textField.setText(e.getText());
                    }
                });
                textField.setTextFormatter(new TextFormatter<>(this::filter));
                Bindings.bindBidirectional(textField.textProperty(),
                        currentBoard.getProperty(i, j), converter);
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
            fileChooser.setTitle(bundle.getString("loadFile"));
            File loadedFile = fileChooser.showOpenDialog(this);
            try (FileMultipleBoardsDao fsdb =
                         new FileMultipleBoardsDao(loadedFile.getAbsolutePath())) {
                SudokuBoard[] readBoards = fsdb.read();
                currentBoard = readBoards[0];
                startBoard = readBoards[1];
                for (Node k : sudokuBoardGrid.getChildren().subList(0, 81)) {
                    TextField field = (TextField) k;
                    int row = GridPane.getRowIndex(k);
                    int column = GridPane.getColumnIndex(k);
                    field.setDisable(false);
                    Bindings.bindBidirectional(field.textProperty(),
                            currentBoard.getProperty(row, column), converter);
                    if (currentBoard.get(row, column) == startBoard.get(row, column) && startBoard.get(row, column) != 0) {
                        field.setDisable(true);
                        field.setStyle("-fx-background-color: #F0EBD7;"
                                + "-fx-opacity: 100%;"
                                + "-fx-alignment: center;"
                                + "-fx-border-style: solid");
                    }
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.NONE, bundle.getString("readingFile"), ButtonType.OK);
                alert.setResizable(false);
                alert.setTitle(bundle.getString("error"));
                alert.showAndWait();
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.NONE,
                    bundle.getString("noFileChosen"), ButtonType.OK);
            alert.setResizable(false);
            alert.setTitle(bundle.getString("error"));
            alert.showAndWait();
        }
    }

    public void save() {
        try {
            File saveFile = fileChooser.showSaveDialog(this);
            fileChooser.setTitle(bundle.getString("saveFile"));
            try (FileMultipleBoardsDao fsbd = new FileMultipleBoardsDao(saveFile.getAbsolutePath())) {
                fsbd.write(new SudokuBoard[]{currentBoard, startBoard});
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.NONE,
                        bundle.getString("saveError"), ButtonType.OK);
                alert.setResizable(false);
                alert.setTitle(bundle.getString("error"));
                alert.showAndWait();
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.NONE,
                    bundle.getString("noFileChosen"), ButtonType.OK);
            alert.setResizable(false);
            alert.setTitle(bundle.getString("error"));
            alert.showAndWait();
        }
    }
}