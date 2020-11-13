import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SudokuField {
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
}
