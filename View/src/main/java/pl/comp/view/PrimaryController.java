package pl.comp.view;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import pl.comp.model.Difficulty;

public class PrimaryController {
    public Pane root;
    public Button primaryButton;
    public ChoiceBox<String> difficultyChoiceBox;
    public Button engButton;
    public Button plButton;
    public Button frButton;
    public Label authorsLabel;
    private ResourceBundle bundle;
    private ResourceBundle authorsBundle;

    public void initialize() {
        difficultyChoiceBox.setOnAction(this::turnOnButton);
        if (difficultyChoiceBox.getValue() == null) {
            primaryButton.setDisable(true);
        }
        bundle = ResourceBundle.getBundle("pl/comp/view/Sudoku");
        authorsBundle = ResourceBundle.getBundle("pl.comp.view.Authors");
        difficultyChoiceBox.getItems().setAll(FXCollections.observableArrayList(
                bundle.getString("easyLevelLabel"),
                bundle.getString("mediumLevelLabel"),
                bundle.getString("hardLevelLabel")));
        authorsLabel.setText(authorsBundle.getString("authors"));
    }

    private void turnOnButton(ActionEvent actionEvent) {
        primaryButton.setDisable(false);
    }

    @FXML
    private void switchToSecondary() throws IOException {
        FXMLLoader secondary = Window.getFxmlLoader("secondary");
        Parent p = secondary.load();
        SecondaryController boardView = secondary.getController();
        switch (difficultyChoiceBox.getValue()) {
            case "Easy":
                boardView.setDifficulty(Difficulty.EASY);
                break;
            case "Medium":
            default:
                boardView.setDifficulty(Difficulty.MEDIUM);
                break;
            case "Hard":
                boardView.setDifficulty(Difficulty.HARD);
                break;
        }
        boardView.startGame();
        Window.setRoot(p);
    }

    public void loadEng() {
        changeLanguage(new Locale("en", "US"));
    }

    public void loadPL() {
        changeLanguage(new Locale("pl", "PL"));
    }

    public void loadFR() {
        changeLanguage(new Locale("fr", "FR"));
    }

    private void changeLanguage(Locale locale) {
        try {
            Locale.setDefault(locale);
            Window.setRoot("primary");
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.NONE,
                    bundle.getString("fileNotFound"),
                    ButtonType.OK);
            alert.setTitle("Error!");
            alert.setResizable(false);
            alert.showAndWait();
        }
    }
}