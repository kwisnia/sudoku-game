package pl.comp.model;

import java.util.InputMismatchException;
import java.util.ResourceBundle;

public class WrongInputException extends InputMismatchException {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("Exceptions");

    public WrongInputException(String s) {
        super(bundle.getString(s));
    }
}
