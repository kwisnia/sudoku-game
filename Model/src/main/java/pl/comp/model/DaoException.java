package pl.comp.model;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class DaoException extends Exception {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("Exceptions");

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getLocalizedMessage() {
        String message;
        try {
            message = bundle.getString(getMessage());
        } catch (MissingResourceException mre) {
            message = bundle.getString("noKeyError") + getMessage();
        }
        return message;
    }
}
