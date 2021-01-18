package pl.comp.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.checkerframework.checker.nullness.qual.NonNull;

public class SudokuField implements Comparable<SudokuField>, Serializable, Cloneable {
    private final transient IntegerProperty value = new SimpleIntegerProperty();
    private final PropertyChangeSupport support;

    public int getFieldValue() {
        return value.get();
    }

    public IntegerProperty getValueProperty() {
        return value;
    }

    public void setFieldValue(int value) {
        this.value.setValue(value);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public SudokuField(int value) {
        this.value.setValue(value);
        support = new PropertyChangeSupport(this);
        this.value.addListener((observableValue, number, t1) ->
                support.firePropertyChange("value", number, t1));
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
        return value.get() == that.value.get();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value.get());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("value", value.get())
                .toString();
    }

    @Override
    public int compareTo(@NonNull SudokuField o) {
        try {
            return Integer.compare(this.value.get(), o.getFieldValue());
        } catch (NullPointerException e) {
            return -1;
        }
    }

    @Override
    public SudokuField clone() throws CloneNotSupportedException {
        return (SudokuField) super.clone();
    }
}
