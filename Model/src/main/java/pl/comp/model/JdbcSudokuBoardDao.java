package pl.comp.model;

import java.io.IOException;
import java.sql.*;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard> {
    private final String name;
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DRIVER = "org.postgresql.Driver";
    private Statement jdbcStatement;
    private Connection connection;
    private final ResourceBundle bundle = ResourceBundle.getBundle("Exceptions");
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public JdbcSudokuBoardDao(String name) throws DaoException {
        this.name = name;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, "postgres", "postgres");
            logger.debug(bundle.getString("connection.success"));
        } catch (ClassNotFoundException | SQLException e) {
            logger.error(bundle.getString("connection.failure"));
            throw new DaoException("connection.failure", e.getCause());
        }
    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public SudokuBoard read() throws IOException, ClassNotFoundException, DaoException {
        return null;
    }

    @Override
    public void write(SudokuBoard obj) throws DaoException {

    }
}
