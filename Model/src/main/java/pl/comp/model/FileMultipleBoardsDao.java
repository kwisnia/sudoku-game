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
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileMultipleBoardsDao implements Dao<SudokuBoard[]> {
    private final String fileName;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

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
            logger.info(ResourceBundle.getBundle("Exceptions").getString("readFile"));
            return (SudokuBoard[]) ois.readObject();
        } catch (IOException e) {
            throw new DaoReadException(e.getCause());
        }
    }

    @Override
    public void write(SudokuBoard[] obj) throws DaoWriteException {
        try (OutputStream fos = new FileOutputStream(fileName);
             ObjectOutput oos = new ObjectOutputStream(fos)) {
            oos.writeObject(obj);
            logger.info(ResourceBundle.getBundle("Exceptions").getString("writeFile"));
        } catch (IOException e) {
            throw new DaoWriteException(e.getCause());
        }
    }
}