package pl.comp.view;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.comp.model.Difficulty;

public class PrimaryController {
    public Pane root;
    public Button primaryButton;
    public ChoiceBox<String> difficultyChoiceBox;
    public ComboBox<String> languageComboBox;
    public Label authorsLabel;
    private ResourceBundle bundle;
    private ResourceBundle authorsBundle;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

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
        languageComboBox.getItems().setAll(FXCollections.observableArrayList(
                bundle.getString("language_en"),
                bundle.getString("language_pl"),
                bundle.getString("language_fr")));
        switch (Locale.getDefault().toString()) {
            case "en_US":
                languageComboBox.setPromptText(bundle.getString("language_en"));
                break;
            case "fr_FR":
                languageComboBox.setPromptText(bundle.getString("language_fr"));
                break;
            default:
                languageComboBox.setPromptText(bundle.getString("language_pl"));
        }
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
            logger.debug(bundle.getString("fileNotFound"));
        }
    }

    public void loadLanguage() {
        switch (languageComboBox.getValue()) {
            case "English":
                changeLanguage(new Locale("en", "US"));
                languageComboBox.setPromptText(bundle.getString("language_en"));
                break;
            case "Francais":
                changeLanguage(new Locale("fr", "FR"));
                languageComboBox.setPromptText(bundle.getString("language_fr"));
                break;
            default:
                changeLanguage(new Locale("pl", "PL"));
                languageComboBox.setPromptText(bundle.getString("language_pl"));
        }
    }
}