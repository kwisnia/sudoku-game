package pl.comp.model;

public class SudokuBoardDaoFactory {
    private SudokuBoardDaoFactory() { // Prywatny konstruktor, by nie moc
        // powolywac instancji klas pl.comp.model.SudokuBoardDaoFactory
    }

    public static Dao<SudokuBoard> getFileDao(String fileName) {
        return new FileSudokuBoardDao(fileName);
    }
}
