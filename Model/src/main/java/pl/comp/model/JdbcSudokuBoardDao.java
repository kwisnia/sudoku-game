package pl.comp.model;

import java.io.IOException;
import java.sql.*;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.Result;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard> {
    private final String name;
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DRIVER = "org.postgresql.Driver";
    private final Connection connection;
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

    private void createTables() throws SQLException {
        try (Statement jdbcStatement = connection.createStatement()) {
            String boardTables = "CREATE TABLE BOARDS(BOARD_ID int PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                    + "BOARD_NAME varchar(30) NOT NULL); CREATE TABLE FIELDS(ROW int NOT NULL, COLUMN int NOT NULL, "
                    + "VALUE int NOT NULL, BOARD_ID int, FOREIGN KEY (BOARD_ID) REFERENCES BOARDS (BOARD_ID) ON"
                    + " UPDATE CASCADE ON DELETE CASCADE )";
            jdbcStatement.executeUpdate(boardTables);
            logger.debug("Utworzono tablice");
        } catch (SQLException e) {
            logger.error("Nie stworzono tablicy poprawnie");
        }
    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public SudokuBoard read() throws IOException, ClassNotFoundException, DaoException {
        SudokuBoard readBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        int id;
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT BOARDS.BOARD_ID FROM " +
                "BOARDS where BOARDS.BOARD_NAME=?"); ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            } else {
                logger.error("No nie");
                throw new DaoException("placeholder");
            }
            logger.debug("Wczytano tablice");
        } catch (SQLException e) {
            throw new DaoException("placeholder");
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT FIELDS.ROW, " +
                "FIELDS.COLUMN, FIELDS.VALUE FROM FIELDS WHERE FIELDS.BOARD_ID=?");
                ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                readBoard.set(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3));
            }
            logger.debug("Przypisano liczby");
        } catch (SQLException e) {
            logger.error("Test");
            throw new DaoException("Test");
        }
        return readBoard;
    }

    @Override
    public void write(SudokuBoard obj) throws DaoException {

    }
}
