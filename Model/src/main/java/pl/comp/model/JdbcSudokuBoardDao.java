package pl.comp.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard> {
    private final String name;
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DRIVER = "org.postgresql.Driver";
    private Statement jdbcStatement;
    private Connection connection;
    private final ResourceBundle bundle = ResourceBundle.getBundle("Exceptions");
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public JdbcSudokuBoardDao(String name){
        this.name = name;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, "postgres", "postgres");
            logger.debug(bundle.getString("connected"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public SudokuBoard read() throws IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public void write(SudokuBoard obj) throws DaoWriteException {

    }
}
