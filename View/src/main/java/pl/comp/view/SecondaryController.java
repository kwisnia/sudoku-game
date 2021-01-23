package pl.comp.view;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.comp.model.BacktrackingSudokuSolver;
import pl.comp.model.Dao;
import pl.comp.model.DaoException;
import pl.comp.model.Difficulty;
import pl.comp.model.FileMultipleBoardsDao;
import pl.comp.model.SudokuBoard;
import pl.comp.model.SudokuBoardDaoFactory;

public class SecondaryController extends javafx.stage.Window {

    private static final String REGEX_VALID_INTEGER = "[1-9]?";
    public Button secondaryButton;
    public GridPane sudokuBoardGrid;
    public Pane root;
    private SudokuBoard currentBoard = new SudokuBoard(new BacktrackingSudokuSolver());
    private SudokuBoard solvedBoard;
    private SudokuBoard startBoard;
    private final StringConverter<Number> converter = new NumberStringConverter();
    final FileChooser fileChooser = new FileChooser();
    private ResourceBundle bundle;
    protected static final Logger logger = LoggerFactory.getLogger(SecondaryController.class);

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
                logger.info(bundle.getString("loadFileLog") + loadedFile.getName());
                currentBoard = readBoards[0];
                startBoard = readBoards[1];
                updateFields();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.NONE,
                        bundle.getString("readingFile"),
                        ButtonType.OK);
                alert.setResizable(false);
                alert.setTitle(bundle.getString("error"));
                alert.showAndWait();
                logger.error(bundle.getString("readingFile"));
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.NONE,
                    bundle.getString("noFileChosen"), ButtonType.OK);
            alert.setResizable(false);
            alert.setTitle(bundle.getString("error"));
            alert.showAndWait();
            logger.info(bundle.getString("error"));
            throw new SudokuNullException();
        }
    }

    public void save() {
        try {
            File saveFile = fileChooser.showSaveDialog(this);
            fileChooser.setTitle(bundle.getString("saveFile"));
            try (FileMultipleBoardsDao fsbd =
                         new FileMultipleBoardsDao(saveFile.getAbsolutePath())) {
                fsbd.write(new SudokuBoard[]{currentBoard, startBoard});
                logger.info(bundle.getString("savedFileLog") + saveFile.getName());
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.NONE,
                        bundle.getString("saveError"), ButtonType.OK);
                alert.setResizable(false);
                alert.setTitle(bundle.getString("error"));
                alert.showAndWait();
                logger.error(bundle.getString("error"));
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.NONE,
                    bundle.getString("noFileChosen"), ButtonType.OK);
            alert.setResizable(false);
            alert.setTitle(bundle.getString("error"));
            alert.showAndWait();
            logger.debug(bundle.getString("noFileChosen"));
            throw new SudokuNullException();
        }
    }

    public void loadBaseButton() throws DaoException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("listView.fxml"), bundle);
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        Stage readStage = new Stage();
        readStage.setTitle(bundle.getString("loadButtonLabel"));
        readStage.initOwner(root.getScene().getWindow());
        readStage.setScene(scene);
        readStage.showAndWait();
        readStage.setResizable(false);
        String fileName = loader.<LoadController>getController().getFileName();
        if (fileName != null) {
            try {
                try (Dao<SudokuBoard> jdbcDao = SudokuBoardDaoFactory.getJdbcDao(fileName);
                Dao<SudokuBoard> jdbcDaoInitial = SudokuBoardDaoFactory
                        .getJdbcDao("initial" + fileName)) {
                    currentBoard = jdbcDao.read();
                    startBoard = jdbcDaoInitial.read();
                    updateFields();
                }
                } catch (Exception e) {
                    logger.error(e.getLocalizedMessage(), e);
                    throw new DaoException("io.error");
            }
        }
    }

    private void updateFields() {
        for (Node k : sudokuBoardGrid.getChildren().subList(0, 81)) {
            TextField field = (TextField) k;
            int row = GridPane.getRowIndex(k);
            int column = GridPane.getColumnIndex(k);
            field.setDisable(false);
            Bindings.bindBidirectional(field.textProperty(),
                    currentBoard.getProperty(row, column), converter);
            if (currentBoard.get(row, column) == startBoard.get(row, column)
                    && startBoard.get(row, column) != 0) {
                field.setDisable(true);
                field.setStyle("-fx-background-color: #F0EBD7;"
                        + "-fx-opacity: 100%;"
                        + "-fx-alignment: center;"
                        + "-fx-border-style: solid");
            }
        }
    }

    public void saveBaseButton() throws DaoException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("inputFile.fxml"), bundle);
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
        }
        Stage inputStage = new Stage();
        inputStage.setTitle(bundle.getString("saveButtonLabel"));
        inputStage.initOwner(root.getScene().getWindow());
        inputStage.setScene(scene);
        inputStage.setResizable(false);
        inputStage.showAndWait();
        String filename = loader.<InputController>getController().getInput();
        if (filename != null) {
            try {
                try (Dao<SudokuBoard> jdbcDao = SudokuBoardDaoFactory.getJdbcDao(filename);
                     Dao<SudokuBoard> jdbcDaoInitial = SudokuBoardDaoFactory
                             .getJdbcDao("initial" + filename)) {
                    jdbcDao.write(currentBoard);
                    jdbcDaoInitial.write(startBoard);
                }
            } catch (Exception e) {
                logger.error(e.getLocalizedMessage(), e);
                throw new DaoException(e.getLocalizedMessage());
            }
        }
    }
}