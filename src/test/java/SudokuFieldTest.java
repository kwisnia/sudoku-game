import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
        assertEquals(testField.toString(), "SudokuField{value=0}");
    }
}
