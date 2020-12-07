package pl.comp.view;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class PrimaryController {

    public Button primaryButton;
    public ChoiceBox<String> difficultyChoiceBox;

    public void initialize() {
        if (difficultyChoiceBox.getValue() == null) {
            primaryButton.setDisable(true);
        }
    }

    public void turnOnButton() {
        primaryButton.setDisable(false);
    }

    @FXML
    private void switchToSecondary() throws IOException {
        Window.setRoot("secondary");
//        switch (difficultyChoiceBox.getValue()) {
//
//
//        }
    }

}
