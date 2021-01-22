package pl.comp.view;

import java.util.ResourceBundle;

public class SudokuNullException extends NullPointerException {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("pl/comp/model/Exceptions");

    public SudokuNullException() {
        super(bundle.getString("Null"));
    }
}
