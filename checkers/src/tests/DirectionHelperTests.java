package tests;

import org.junit.jupiter.api.Test;
import primitives.Direction;
import primitives.Vector;
import tools.DirectionHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirectionHelperTests {

    @Test
    void getDeltaVector_gettingFromLeftUp_shouldReturnRightValue(){
        var helper = new DirectionHelper();
        var direction = Direction.LEFT_UP;
        var expected = new Vector(1, -1);

        var actual = helper.getDeltaVector(direction);

        assertEquals(expected,  actual);
    }


    @Test
    void getDeltaVector_gettingFromRightDown_shouldReturnRightValue(){
        var helper = new DirectionHelper();
        var direction = Direction.RIGHT_DOWN;
        var expected = new Vector(-1, 1);

        var actual = helper.getDeltaVector(direction);

        assertEquals(expected,  actual);
    }


    @Test
    void getDeltaVector_gettingFromRightUp_shouldReturnRightValue(){
        var helper = new DirectionHelper();
        var direction = Direction.RIGHT_UP;
        var expected = new Vector(1, 1);

        var actual = helper.getDeltaVector(direction);

        assertEquals(expected,  actual);
    }

    @Test
    void getDeltaVector_gettingFromLeftDown_shouldReturnRightValue(){
        var helper = new DirectionHelper();
        var direction = Direction.LEFT_DOWN;
        var expected = new Vector(-1, -1);

        var actual = helper.getDeltaVector(direction);

        assertEquals(expected,  actual);
    }
}
