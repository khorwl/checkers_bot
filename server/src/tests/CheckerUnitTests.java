package tests;

import core.checkers.primitives.Checker;
import core.checkers.primitives.Color;
import core.checkers.primitives.Rank;
import core.checkers.primitives.Vector;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CheckerUnitTests {

    @Test
    void getColor_gettingColorByWhiteChecher_shouldReturnRightValue() {
        var checker = new Checker(new Vector(1, 6, 4), Color.WHITE, Rank.SOLDIER);
        var expected = Color.WHITE;

        var actual = checker.getColor();

        assertEquals(expected, actual);
    }

    @Test
    void getColor_gettingColorByBlackChecher_shouldReturnRightValue() {
        var checker = new Checker(new Vector(1, 6, 4), Color.BLACK, Rank.SOLDIER);
        var expected = Color.BLACK;

        var actual = checker.getColor();

        assertEquals(expected, actual);
    }

    @Test
    void getRank_gettingRankLadyChecker_shouldReturnRightValue() {
        var checker = new Checker(new Vector(1, 6, 4), Color.BLACK, Rank.LADY);
        var expected = Rank.LADY;

        var actual = checker.getRank();

        assertEquals(expected, actual);
    }

    @Test
    void getRank_gettingRankSoldierChecker_shouldReturnRightValue() {
        var checker = new Checker(new Vector(1, 6, 4), Color.BLACK, Rank.SOLDIER);
        var expected = Rank.SOLDIER;

        var actual = checker.getRank();

        assertEquals(expected, actual);
    }

    @Test
    void getPosition_shouldReturnRightValue() {
        var checker = new Checker(new Vector(1, 6, 4), Color.WHITE, Rank.SOLDIER);
        var expected = new Vector(1, 6, 4);

        var actual = checker.getPosition();

        assertEquals(expected, actual);
    }

    @Test
    void setPosition_settingByValidCords_shouldReturnRightValue(){
        var checker = new Checker(new Vector(1, 6, 4), Color.BLACK, Rank.SOLDIER);
        var expected = new Vector(5, 7, 3);

        checker.setPosition(expected);
        var actual =  checker.getPosition();

        assertEquals(expected, actual);
    }

    @Test
    void setPosition_settingByNegativeCords_shouldThrowException(){
        var checker = new Checker(new Vector(1, 6, 6), Color.WHITE, Rank.SOLDIER);
        var nextPosition = new Vector(-1, 7, 0);

        assertThrows(IllegalArgumentException.class, () -> checker.setPosition(nextPosition));
    }

    @Test
    void setPosition_settingCordsOutRange_shouldThrowException(){
        var checker = new Checker(new Vector(1, 6, 6), Color.WHITE, Rank.SOLDIER);
        var nextPosition = new Vector(8, 7, 0);

        assertThrows(IllegalArgumentException.class, () -> checker.setPosition(nextPosition));
    }

    @Test
    void move_movingNegativeCords_shouldThrowException(){
        var checker = new Checker(new Vector(1, 6, 4), Color.BLACK, Rank.SOLDIER);
        var delta = new Vector(-2, 7, 1);

        assertThrows(IllegalArgumentException.class, () -> checker.move(delta));
    }

    @Test
    void move_movingCordsOutRange_shouldThrowException(){
        var checker = new Checker(new Vector(1, 6, 4), Color.BLACK, Rank.SOLDIER);
        var delta = new Vector(2, 2, -1);

        assertThrows(IllegalArgumentException.class, () -> checker.move(delta));
    }

    @Test
    void move_movingByValidCords_shouldReturnRightValue(){
        var checker = new Checker(new Vector(1, 6, 7), Color.WHITE, Rank.SOLDIER);
        var delta = new Vector(-1, -3, 0);
        var expected = new Vector(0, 3, 7);

        checker.move(delta);
        var actual =  checker.getPosition();

        assertEquals(expected, actual);
    }
}
