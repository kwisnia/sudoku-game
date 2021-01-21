package pl.comp.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class FileMultipleBoardsDao implements Dao<SudokuBoard[]> {
    private final String fileName;

    public FileMultipleBoardsDao(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public SudokuBoard[] read() throws IOException, ClassNotFoundException {
        try (InputStream fis = new FileInputStream(fileName);
             ObjectInput ois = new ObjectInputStream(fis)) {
            return (SudokuBoard[]) ois.readObject();
        } catch (IOException e) {
            e.initCause(new DaoReadException("s", e.getCause()));
            throw e;
        }
    }

    @Override
    public void write(SudokuBoard[] obj) throws IOException {
        try (OutputStream fos = new FileOutputStream(fileName);
             ObjectOutput oos = new ObjectOutputStream(fos)) {
            oos.writeObject(obj);
        }
    }
}
