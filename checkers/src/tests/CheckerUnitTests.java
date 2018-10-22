package tests;

import org.junit.jupiter.api.Test;
import primitives.Checker;
import primitives.Color;
import primitives.Rank;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CheckerUnitTests {

    @Test
    void getColor_shouldReturnRightValue() {
        var checker = new Checker(new Vector(1, 6), Color.YELLOW, Rank.SOLDIER);
        var expected = Color.YELLOW;

        var actual = checker.getColor();

        assertEquals(expected, actual);
    }


    @Test
    void getRank_shouldReturnRightValue() {
        var checker = new Checker(new Vector(1, 6), Color.PINK, Rank.LADY);
        var expected = Rank.LADY;

        var actual = checker.getRank();

        assertEquals(expected, actual);
    }


    @Test
    void getPosition_shouldReturnRightValue() {
        var checker = new Checker(new Vector(1, 6), Color.PINK, Rank.SOLDIER);
        var expected = new Vector(1, 6);

        var actual = checker.getPosition();

        assertEquals(expected, actual);
    }

    @Test
    void setPosition_settingByValidCords_shouldReturnRightValue(){
        var checker = new Checker(new Vector(1, 6), Color.PINK, Rank.SOLDIER);
        var expected = new Vector(5, 7);

        checker.setPosition(expected);
        var actual =  checker.getPosition();

        assertEquals(expected, actual);
    }

    @Test
    void setPosition_settingByNegativeCords_shouldReturnRightValue(){
        var checker = new Checker(new Vector(1, 6), Color.PINK, Rank.SOLDIER);
        var nextPosition = new Vector(-1, 7);

        assertThrows(IllegalArgumentException.class, () -> checker.setPosition(nextPosition));
    }

    @Test
    void move_movingByInvalidCords_shouldReturnRightValue(){
        var checker = new Checker(new Vector(1, 6), Color.PINK, Rank.SOLDIER);
        var delta = new Vector(-2, 7);

        assertThrows(IllegalArgumentException.class, () -> checker.move(delta));
    }

    @Test
    void move_movingByValidCords_shouldReturnRightValue(){
        var checker = new Checker(new Vector(1, 6), Color.PINK, Rank.SOLDIER);
        var delta = new Vector(-1, 7);
        var expected = new Vector(0, 13);

        checker.move(delta);
        var actual =  checker.getPosition();

        assertEquals(expected, actual);
    }

}
