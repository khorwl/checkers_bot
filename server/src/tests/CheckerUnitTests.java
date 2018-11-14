package tests;

import core.checkers.primitives.Checker;
import core.checkers.primitives.Color;
import core.checkers.primitives.Rank;
import core.checkers.primitives.Vector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CheckerUnitTests {

    @Test
    void equals_compareWithNotChecker_shouldReturnFalse(){
        var notCh= 10;
        var ch = new Checker(new Vector(1, 1, 2), Color.WHITE, Rank.SOLDIER);

        assertFalse(ch.equals(notCh));
    }


    @Test
    void equals_compareWithSelf_shouldReturnTrue(){
        var ch = new Checker(new Vector(1, 1, 2), Color.WHITE, Rank.SOLDIER);

        assertTrue(ch.equals(ch));
    }

    @Test
    void equals_compareEqualsCheckers_shouldReturnTrue(){
        var ch = new Checker(new Vector(1, 1, 2), Color.WHITE, Rank.SOLDIER);
        var ch2 = new Checker(new Vector(1, 1, 2), Color.WHITE, Rank.SOLDIER);

        assertTrue(ch.equals(ch2));
    }

    @Test
    void equals_compareDiffCheckers_shouldReturnFalse(){
        var ch = new Checker(new Vector(1, 1, 2), Color.WHITE, Rank.SOLDIER);
        var ch2 = new Checker(new Vector(2, 1, 2), Color.WHITE, Rank.SOLDIER);

        assertFalse(ch.equals(ch2));
    }


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

        var actual = checker.move(delta).getPosition();

        assertEquals(expected, actual);
    }
}
