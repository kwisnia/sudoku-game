package pl.comp.view;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.ListView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.comp.model.DaoException;

public class LoadController implements Initializable {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    public Button load;
    public ListView<String> listBase;
    ResourceBundle bundle;
    private ResourceBundle exceptionBundle = ResourceBundle.getBundle("Exceptions");
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DRIVER = "org.postgresql.Driver";
    private Statement JDBC_STATEMENT;
    private Connection connection;
    private List<String> nameOfSudoku = new ArrayList<>();
    private boolean loaded = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bundle = resourceBundle;
        try {
            getFileNames();
        } catch (DaoException | SQLException e) {
            logger.error(exceptionBundle.getString("connection.failure"));
        }
        nameOfSudoku.removeIf(s -> s.contains("initial"));
        listBase.getItems().addAll(nameOfSudoku);
    }

    private void getFileNames() throws DaoException, SQLException {
        try  {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, "postgres", "prokomp2020");
            logger.debug(exceptionBundle.getString("connection.success"));
        } catch (ClassNotFoundException | SQLException e) {
            logger.error(exceptionBundle.getString("connection.failure"), e);
            throw new DaoException(e.getLocalizedMessage(), e);
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT " +
                "BOARDS.BOARD_NAME from BOARDS"); ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                nameOfSudoku.add(resultSet.getString(1));
            }
            logger.debug(bundle.getString("loaded"));
        } catch (SQLException e) {
            logger.debug(e.getMessage());
            logger.error(exceptionBundle.getString("io.error"));
        }
        connection.close();
    }

    public void loadSudoku() {
        loaded = true;
        listBase.getScene().getWindow().hide();
    }

    public String getFileName() {
        if (!listBase.getSelectionModel().getSelectedIndices().isEmpty()) {
            return String.valueOf(listBase.getSelectionModel().getSelectedItems());
        }
        logger.error(bundle.getString("loadError"));
        return null;
    }
}