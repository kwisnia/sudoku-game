package pl.comp.model;

import java.util.ResourceBundle;

public class SudokuNullException extends NullPointerException {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("Exceptions");
    public SudokuNullException(String s) {
        super(bundle.getString("Null"));
    }
}
