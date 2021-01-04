package pl.comp.view;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import pl.comp.model.Difficulty;

public class PrimaryController {
    public Pane root;
    public Button primaryButton;
    public ChoiceBox<String> difficultyChoiceBox;
    public ToggleGroup languageGroup;
    public RadioButton engButton;
    public RadioButton plButton;
    public RadioButton frButton;

    public void initialize() {
        difficultyChoiceBox.setOnAction(this::turnOnButton);
        if (difficultyChoiceBox.getValue() == null) {
            primaryButton.setDisable(true);
        }
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

    public void loadEnglish() {
        changeLanguage(Locale.ENGLISH);
    }

    public void loadPL() {
        changeLanguage(new Locale("pl", "PL"));
    }

    public void loadFR() {
        changeLanguage(Locale.FRENCH);
    }

    private void changeLanguage(Locale locale) {
        try {
            Locale.setDefault(locale);
            Window.setRoot("primary");
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.NONE, "File wasn't found", ButtonType.OK);
            alert.setTitle("Error!");
            alert.setResizable(false);
            alert.showAndWait();
        }
    }
}
