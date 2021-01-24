package pl.comp.model;


public interface Dao<T> extends AutoCloseable {

    @Override
    void close() throws Exception;

    T read() throws Exception;

    void write(T obj) throws Exception;
}
