package pl.comp.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard> {
    private final String name;
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DRIVER = "org.postgresql.Driver";
    private final Connection connection;
    private final ResourceBundle bundle = ResourceBundle.getBundle("Exceptions");
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public String getName() {
        return name;
    }

    public JdbcSudokuBoardDao(String name) throws DaoException, SQLException, ClassNotFoundException {
        this.name = name;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, "postgres", "prokomp2020");
            createTables();
            logger.debug(bundle.getString("connection.success"));
        } catch (ClassNotFoundException | SQLException e) {
            logger.error(bundle.getString("connection.failure"));
            throw e;
        }
    }

    private void createTables() throws SQLException {
        try (Statement jdbcStatement = connection.createStatement()) {
            String boardTables = "CREATE TABLE BOARDS(BOARD_ID int PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                    + "BOARD_NAME varchar(30) NOT NULL UNIQUE); CREATE TABLE FIELDS(ROW int NOT NULL, Y int NOT NULL, "
                    + "VALUE int NOT NULL, BOARD_ID int, FOREIGN KEY (BOARD_ID) REFERENCES BOARDS (BOARD_ID) ON"
                    + " UPDATE CASCADE ON DELETE CASCADE )";
            jdbcStatement.executeUpdate(boardTables);
            logger.debug("Utworzono tablice");
        } catch (SQLException e) {
            logger.error(e.getLocalizedMessage());
            logger.error("Nie stworzono tablicy poprawnie");
        }
    }

    @Override
    public SudokuBoard read() throws IOException, ClassNotFoundException, DaoException {
        SudokuBoard readBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        int id;
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT BOARDS.BOARD_ID FROM " +
                "BOARDS where BOARDS.BOARD_NAME=?")) {
                preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                } else {
                    logger.error("No nie");
                    throw new DaoException("placeholder");
                }
                logger.debug("Wczytano tablice");
            }
        } catch (SQLException e) {
            throw new DaoException("placeholder");
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT FIELDS.ROW, " +
                "FIELDS.Y, FIELDS.VALUE FROM FIELDS WHERE FIELDS.BOARD_ID=?")) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    readBoard.set(resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getInt(3));
                }
                logger.debug("Przypisano liczby");
            }
        } catch (SQLException e) {
            logger.error(e.getLocalizedMessage());
            throw new DaoException("Test");
        }
        return readBoard;
    }

    @Override
    public void write(SudokuBoard obj) throws DaoException {
        int id;
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO BOARDS " +
                "(BOARD_NAME) VALUES (?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException("Lol");
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT " +
                "BOARDS.BOARD_ID FROM BOARDS WHERE BOARDS.BOARD_NAME=?")) {
            preparedStatement.setString(1, name);
            try (ResultSet set = preparedStatement.executeQuery()) {
                if (set.next()) {
                    id = set.getInt(1);
                } else {
                    logger.error(bundle.getString("io.error"));
                    throw new DaoException(bundle.getString("io.error"));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException("lol");
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO" +
                " FIELDS VALUES(?, ?, ?, ?)")) {
            preparedStatement.setInt(4, id);
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    preparedStatement.setInt(1, i);
                    preparedStatement.setInt(2, j);
                    preparedStatement.setInt(3, obj.get(i, j));
                    preparedStatement.executeUpdate();
                }
            }
            logger.debug("Wprowadzono dane");
        } catch (SQLException e) {
            throw new DaoException("lol");
        }
    }

    @Override
    public void close() throws DaoException {
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error(bundle.getString("connectionError"));
            throw new DaoException("connectionError");
        }
    }


}
