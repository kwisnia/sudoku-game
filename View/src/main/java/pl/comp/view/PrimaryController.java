package pl.comp.view;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import pl.comp.model.Difficulty;

public class PrimaryController {

    public Button primaryButton;
    public ChoiceBox<String> difficultyChoiceBox;

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

}
