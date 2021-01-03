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
import java.util.ArrayList;
import java.util.List;

public class FileSudokuBoardDao implements Dao<SudokuBoard> {

    private final String fileName;

    public FileSudokuBoardDao(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public SudokuBoard read() throws IOException, ClassNotFoundException {
        try (InputStream fis = new FileInputStream(fileName);
             ObjectInput ois = new ObjectInputStream(fis)) {
            return (SudokuBoard) ois.readObject();
        }
    }

    public List<SudokuBoard> readAll() throws IOException, ClassNotFoundException {
        List<SudokuBoard> readBoards = new ArrayList<>();
        try (InputStream fis = new FileInputStream(fileName);
             ObjectInput ois = new ObjectInputStream(fis)) {
            while (fis.available() != 0) {
                readBoards.add((SudokuBoard) ois.readObject());
            }
        }
        return readBoards;
    }

    @Override
    public void write(SudokuBoard obj) throws IOException {
        try (OutputStream fos = new FileOutputStream(fileName, true);
             ObjectOutput oos = new ObjectOutputStream(fos)) {
            oos.writeObject(obj);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public void close() throws Exception {

    }
}
