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

    public JdbcSudokuBoardDao(String name) throws DaoException {
        this.name = name;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, "postgres", "prokomp2020");
            createTables();
            logger.debug(bundle.getString("connection.success"));
        } catch (ClassNotFoundException | SQLException e) {
            logger.error(bundle.getString("connection.failure"));
            throw new DaoException("connection.failure");
        }
    }

    private void createTables() {
        try (Statement jdbcStatement = connection.createStatement()) {
            String boardTables = "CREATE TABLE BOARDS(BOARD_ID int PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                    + "BOARD_NAME varchar(30) NOT NULL UNIQUE); CREATE TABLE FIELDS(X int NOT NULL, Y int NOT NULL, "
                    + "VALUE int NOT NULL, BOARD_ID int, FOREIGN KEY (BOARD_ID) REFERENCES BOARDS (BOARD_ID) ON"
                    + " UPDATE CASCADE ON DELETE CASCADE )";
            jdbcStatement.executeUpdate(boardTables);
            logger.debug(bundle.getString("tableCreated"));
        } catch (SQLException e) {
            logger.error(bundle.getString("noTableCreated"));
        }
    }

    @Override
    public SudokuBoard read() throws DaoException {
        SudokuBoard readBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        int id;
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT BOARDS.BOARD_ID FROM " +
                "BOARDS where BOARDS.BOARD_NAME=?")) {
                preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                } else {
                    logger.error(bundle.getString("io.error"));
                    throw new DaoException("io.error");
                }
                logger.debug(bundle.getString("loadTable"));
            }
        } catch (SQLException e) {
            throw new DaoException("io.error");
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT FIELDS.X, " +
                "FIELDS.Y, FIELDS.VALUE FROM FIELDS WHERE FIELDS.BOARD_ID=?")) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    readBoard.set(resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getInt(3));
                }
                logger.debug(bundle.getString("loadSudokuToTable"));
            }
        } catch (SQLException e) {
            logger.error(e.getLocalizedMessage());
            throw new DaoException("io.error");
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
            throw new DaoException("io.error");
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
            throw new DaoException("io.error");
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
            logger.debug(bundle.getString("saveSudoku"));
        } catch (SQLException e) {
            throw new DaoException("io.error");
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