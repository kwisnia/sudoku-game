package pl.comp.model;

import java.util.ResourceBundle;

public class DaoWriteException extends Exception {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("Exceptions");

    public DaoWriteException(Throwable cause) {
        super(bundle.getString("DaoWriteException"), cause);
    }
}
