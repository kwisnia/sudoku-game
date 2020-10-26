public class App {

    public static void main(String[] args) {
        SudokuBoard su = new SudokuBoard(new BacktrackingSudokuSolver());
        su.solveGame();
        System.out.println(su.toString());
        System.out.println(su.get(10, 10));
    }
}
