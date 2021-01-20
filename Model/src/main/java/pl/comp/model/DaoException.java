package pl.comp.model;

import java.io.IOException;

public class DaoException extends IOException {
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

}
