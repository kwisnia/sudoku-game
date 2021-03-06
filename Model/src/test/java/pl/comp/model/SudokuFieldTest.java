package pl.comp.model;

import javafx.beans.property.IntegerProperty;
import org.junit.jupiter.api.Test;
import pl.comp.model.SudokuField;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuFieldTest {

    @Test
    void FieldEqualsTest() {
        SudokuField testField1 = new SudokuField(0);
        SudokuField testField2 = new SudokuField(0);
        assertEquals(testField1, testField2);
        assertEquals(testField1, testField1);
        assertNotEquals(testField1, 10);
        assertNotEquals(testField1, null);
        testField2.setFieldValue(7);
        assertNotEquals(testField1, testField2);
    }

    @Test
    void FieldHashCodeTest() {
        SudokuField testField1 = new SudokuField(0);
        SudokuField testField2 = new SudokuField(0);
        assertEquals(testField1.hashCode(), testField2.hashCode());
    }

    @Test
    void FieldToStringTest() {
        SudokuField testField = new SudokuField(0);
        assertEquals("SudokuField{value=0}", testField.toString());
    }


    @Test
    void fieldCompareTest() {
        SudokuField field1 = new SudokuField(9);
        SudokuField field2 = new SudokuField(9);
        assertEquals(0, field1.compareTo(field2));
        field1.setFieldValue(2);
        assertEquals(-1, field1.compareTo(field2));
        field2.setFieldValue(1);
        assertEquals(1, field1.compareTo(field2));
    }

    @Test
    void fieldCloneTest() throws CloneNotSupportedException {
        SudokuField field1 = new SudokuField(9);
        SudokuField field2 = field1.clone();
        assertEquals(field1, field2);
    }

    @Test
    void propertyGetTest() {
        SudokuField field1 = new SudokuField(9);
        IntegerProperty prop = field1.getValueProperty();
        assertNotNull(prop);
    }

    @Test
    void nullCompareTest() {
        SudokuField field1 = new SudokuField(9);
        assertEquals(-1, field1.compareTo(null));
    }
}
