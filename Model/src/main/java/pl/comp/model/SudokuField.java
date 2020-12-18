package pl.comp.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class SudokuField implements Comparable<SudokuField>, Serializable, Cloneable {
    private int value;

    private final PropertyChangeSupport support;

    public int getFieldValue() {
        return value;
    }

    public void setFieldValue(int value) {
        int oldValue = this.value;
        this.value = value;
        support.firePropertyChange("value", oldValue, value);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public SudokuField(int value) {
        this.value = value;
        support = new PropertyChangeSupport(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SudokuField that = (SudokuField) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("value", value)
                .toString();
    }

    @Override
    public int compareTo(SudokuField o) {
        return Integer.compare(this.value, o.getFieldValue());
    }
}
