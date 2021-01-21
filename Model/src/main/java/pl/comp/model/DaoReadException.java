package pl.comp.model;

import java.io.IOException;
import java.util.ResourceBundle;

public class DaoReadException extends IOException {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("Exceptions");

    public DaoReadException(String message, Throwable cause) {
        super(bundle.getString("DaoReadException"), cause);
    }
}
