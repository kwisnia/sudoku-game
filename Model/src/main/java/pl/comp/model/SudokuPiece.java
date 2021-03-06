package pl.comp.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class SudokuPiece implements Serializable, Cloneable {
    public SudokuPiece(List<SudokuField> fields) {
        this.fields = fields;
    }

    private final List<SudokuField> fields;

    public boolean verify() {
        Set<Integer> usedNumbers = new HashSet<>();
        for (SudokuField value : fields) {
            if (value.getFieldValue() != 0) {
                if (!usedNumbers.add(value.getFieldValue())) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SudokuPiece that = (SudokuPiece) o;
        return Objects.equal(fields, that.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(fields);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("fields", fields)
                .toString();
    }

    @Override
    public SudokuPiece clone() throws CloneNotSupportedException {
        return (SudokuPiece) super.clone();
    }
}
