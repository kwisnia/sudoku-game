package pl.comp.view;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class PrimaryController {

    public Button primaryButton;
    public ChoiceBox difficultyChoiceBox;

    @FXML
    private void switchToSecondary() throws IOException {
        Window.setRoot("secondary");
    }
}
