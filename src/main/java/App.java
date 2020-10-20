public class App {
    public static void main(String[] args) {
        SudokuBoard su = new SudokuBoard();
        System.out.println(su.toString());
        System.out.println(su.validBoard());
    }
}
