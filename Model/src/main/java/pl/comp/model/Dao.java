package pl.comp.model;

import java.io.IOException;
import java.sql.SQLException;

public interface Dao<T> extends AutoCloseable {

    @Override
    void close() throws Exception;

    T read() throws IOException, ClassNotFoundException, DaoException, SQLException;

    void write(T obj) throws DaoException, SQLException;
}
