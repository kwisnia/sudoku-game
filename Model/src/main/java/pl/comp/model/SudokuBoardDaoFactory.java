package pl.comp.model;

import java.sql.SQLException;

public class SudokuBoardDaoFactory {
    private SudokuBoardDaoFactory() { // Prywatny konstruktor, by nie moc
        // powolywac instancji klas pl.comp.model.SudokuBoardDaoFactory
    }

    public static Dao<SudokuBoard> getFileDao(String fileName) {
        return new FileSudokuBoardDao(fileName);
    }

    public static Dao<SudokuBoard> getJdbcDao(String name) throws DaoException {
        return new JdbcSudokuBoardDao(name);
    }
}
