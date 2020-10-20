public class App {
    public static void main(String[] args) {
        SudokuBoard su = new SudokuBoard();
        su.initializeBoard();
        System.out.println(su.toString());
        System.out.println(su.validBoard());
    }
}
