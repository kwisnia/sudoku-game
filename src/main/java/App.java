public class App {
    public static void main(String[] args) {
        SudokuBoard su = new SudokuBoard();
        su.fillBoard();
        System.out.println(su.toString());

    }
}
