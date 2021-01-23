package pl.comp.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class InputController implements Initializable {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private ResourceBundle bundle = ResourceBundle.getBundle("Sudoku");
    public TextField textField;
    private boolean input = false;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = resources;
    }

    @FXML
    public void submit() {
        input = true;
//        textField.getScene().getWindow().hide();
    }

    public String getInput() {
        if(!textField.getText().isEmpty()) {
            return textField.getText();
        }
        logger.error(bundle.getString("saveError"));
        return null;
    }
}