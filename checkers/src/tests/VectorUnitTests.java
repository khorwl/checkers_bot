package tests;

import org.junit.jupiter.api.Test;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VectorUnitTests {

    @Test
    void getX_shouldReturnRightX(){
        var v = new Vector(4, 2);
        var expected = 2;

        var actual = v.getX();

        assertEquals(expected, actual);
    }

    @Test
    void getY_shouldReturnRightY(){
       var v = new Vector(4, 2);
       var expected = 4;

       var actual = v.getY();

       assertEquals(expected, actual);
    }

    @Test
    void add_addWithNegativeCords_shouldRightToChangeTheVector(){
        var vector = new Vector(-4, 2);
        var delta = new Vector(42, -1);
        var expected = new Vector(38, 1);

        var actual = vector.add(delta);

        assertEquals(expected, actual);
    }


    @Test
    void add_addWithPositiveCords_shouldRightToChangeTheVector(){
        var vector = new Vector(4, 2);
        var delta = new Vector(42, 1);
        var expected = new Vector(46, 3);

        var actual = vector.add(delta);

        assertEquals(expected, actual);
    }
}
