package pl.comp.model;

import java.io.IOException;

public interface Dao<T> extends AutoCloseable {

    @Override
    void close() throws Exception;

    T read() throws IOException, ClassNotFoundException;

    void write(T obj) throws DaoWriteException;
}
