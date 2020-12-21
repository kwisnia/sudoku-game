package pl.comp.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class FileSudokuBoardDao implements Dao<SudokuBoard> {

    private final String fileName;

    public FileSudokuBoardDao(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public SudokuBoard read() throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(fileName);
             ObjectInput ois = new ObjectInputStream(fis)) {
            return (SudokuBoard) ois.readObject();
        }
    }

    @Override
    public void write(SudokuBoard obj) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(fileName);
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
