import java.util.HashSet;
import java.util.Set;

public abstract class SudokuVerify {
    public SudokuVerify(SudokuField[] fields) {
        this.fields = fields;
    }

    SudokuField[] fields;
    Set<Integer> usedNumbers = new HashSet<>();

    public boolean verify() {
        for (SudokuField value : fields) {
            if (value.getFieldValue() != 0) {
                if (!usedNumbers.add(value.getFieldValue())) {
                    return false;
                }
            }
        }
        return true;
    }
}
