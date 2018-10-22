package tests;

import org.junit.jupiter.api.Test;
import primitives.Cell;
import primitives.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FieldUnitTests {

    @Test
    void getCell_gettingByValidCords_shouldReturnRightCell(){
        var field = new Field(5, 5);
        var expected = Cell.BLACK;

        var actual = field.getCell(3, 4);

        assertEquals(expected,  actual);
    }

    @Test
    void getCell_gettingByNegativeCords_shouldThrowException(){
        var field = new Field(5, 5);

        assertThrows(IndexOutOfBoundsException.class, () -> field.getCell(-1, 3));
    }

    @Test
    void getCell_gettingByCordsOutRange_shouldThrowException(){
        var field = new Field(5, 5);

        assertThrows(IndexOutOfBoundsException.class, () -> field.getCell(5, 5));
    }

    @Test
    void getHeight_shouldReturnRightValue(){
        var field = new Field(5, 10);
        var expected = 5;

        var actual = field.getHeight();

        assertEquals(expected, actual);
    }

    @Test
    void getWidth_shouldReturnRightValue(){
        var field = new Field(5, 10);
        var expected = 10;

        var actual = field.getWidth();

        assertEquals(expected, actual);
    }
}
