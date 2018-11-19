package tests;

import core.checkers.game.Generator;
import core.checkers.primitives.Checker;
import core.checkers.primitives.Color;
import core.checkers.primitives.Rank;
import core.checkers.primitives.Vector;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class GeneratorUnitTests {

    @Test
    void getStartCheckers_shouldReturnRightCheckrs() {
        var z = 0;
        var white = Color.WHITE;
        var soldier = Rank.SOLDIER;
        var ch1 = new Checker(new Vector(1, 0, z), white, soldier);
        var ch2 = new Checker(new Vector(3, 0, z), white, soldier);
        var ch3 = new Checker(new Vector(5, 0, z), white, soldier);
        var ch4 = new Checker(new Vector(7, 0, z), white, soldier);
        var ch5 = new Checker(new Vector(0, 1, z), white, soldier);
        var ch6 = new Checker(new Vector(2, 1, z), white, soldier);
        var ch7 = new Checker(new Vector(4, 1, z), white, soldier);
        var ch8 = new Checker(new Vector(6, 1, z), white, soldier);
        var ch9 = new Checker(new Vector(1, 2, z), white, soldier);
        var ch10 = new Checker(new Vector(3, 2, z), white, soldier);
        var ch11 = new Checker(new Vector(5, 2, z), white, soldier);
        var ch12 = new Checker(new Vector(7, 2, z), white, soldier);
        var ch13 = new Checker(new Vector(0, 3, z), white, soldier);
        var ch14 = new Checker(new Vector(2, 3, z), white, soldier);
        var ch15 = new Checker(new Vector(4, 3, z), white, soldier);
        var ch16 = new Checker(new Vector(6, 3, z), white, soldier);
        var ch17 = new Checker(new Vector(1, 4, z), white, soldier);
        var ch18 = new Checker(new Vector(3, 4, z), white, soldier);
        var ch19 = new Checker(new Vector(5, 4, z), white, soldier);
        var ch20 = new Checker(new Vector(7, 4, z), white, soldier);
        var ch21 = new Checker(new Vector(0, 5, z), white, soldier);
        var ch22 = new Checker(new Vector(2, 5, z), white, soldier);
        var ch23 = new Checker(new Vector(4, 5, z), white, soldier);
        var ch24 = new Checker(new Vector(6, 5, z), white, soldier);
        var ch25 = new Checker(new Vector(1, 6, z), white, soldier);
        var ch26 = new Checker(new Vector(3, 6, z), white, soldier);
        var ch27 = new Checker(new Vector(5, 6, z), white, soldier);
        var ch28 = new Checker(new Vector(7, 6, z), white, soldier);
        var ch29 = new Checker(new Vector(0, 7, z), white, soldier);
        var ch30 = new Checker(new Vector(2, 7, z), white, soldier);
        var ch31 = new Checker(new Vector(4, 7, z), white, soldier);
        var ch32 = new Checker(new Vector(6, 7, z), white, soldier);

        z = 7;
        var black = Color.BLACK;
        var ch33 = new Checker(new Vector(0, 0, z), black, soldier);
        var ch34 = new Checker(new Vector(2, 0, z), black, soldier);
        var ch35 = new Checker(new Vector(4, 0, z), black, soldier);
        var ch36 = new Checker(new Vector(6, 0, z), black, soldier);
        var ch37 = new Checker(new Vector(1, 1, z), black, soldier);
        var ch38 = new Checker(new Vector(3, 1, z), black, soldier);
        var ch39 = new Checker(new Vector(5, 1, z), black, soldier);
        var ch40 = new Checker(new Vector(7, 1, z), black, soldier);
        var ch41 = new Checker(new Vector(0, 2, z), black, soldier);
        var ch42 = new Checker(new Vector(2, 2, z), black, soldier);
        var ch43 = new Checker(new Vector(4, 2, z), black, soldier);
        var ch44 = new Checker(new Vector(6, 2, z), black, soldier);
        var ch45 = new Checker(new Vector(1, 3, z), black, soldier);
        var ch46 = new Checker(new Vector(3, 3, z), black, soldier);
        var ch47 = new Checker(new Vector(5, 3, z), black, soldier);
        var ch48 = new Checker(new Vector(7, 3, z), black, soldier);
        var ch49 = new Checker(new Vector(0, 4, z), black, soldier);
        var ch50 = new Checker(new Vector(2, 4, z), black, soldier);
        var ch51 = new Checker(new Vector(4, 4, z), black, soldier);
        var ch52 = new Checker(new Vector(6, 4, z), black, soldier);
        var ch53 = new Checker(new Vector(1, 5, z), black, soldier);
        var ch54 = new Checker(new Vector(3, 5, z), black, soldier);
        var ch55 = new Checker(new Vector(5, 5, z), black, soldier);
        var ch56 = new Checker(new Vector(7, 5, z), black, soldier);
        var ch57 = new Checker(new Vector(0, 6, z), black, soldier);
        var ch58 = new Checker(new Vector(2, 6, z), black, soldier);
        var ch59 = new Checker(new Vector(4, 6, z), black, soldier);
        var ch60 = new Checker(new Vector(6, 6, z), black, soldier);
        var ch61 = new Checker(new Vector(1, 7, z), black, soldier);
        var ch62 = new Checker(new Vector(3, 7, z), black, soldier);
        var ch63 = new Checker(new Vector(5, 7, z), black, soldier);
        var ch64 = new Checker(new Vector(7, 7, z), black, soldier);

        var expected = new HashSet<Checker>(List.of(ch1, ch10, ch11, ch12, ch13, ch14, ch15, ch16, ch17, ch18, ch19,
                ch2, ch20, ch21, ch22, ch23, ch24, ch25, ch26, ch27, ch28, ch29,
                ch3, ch30, ch31, ch32, ch33, ch34, ch35, ch36, ch37, ch38, ch39,
                ch4, ch40, ch41, ch42, ch43, ch44, ch45, ch46, ch47, ch48, ch49,
                ch5, ch50, ch51, ch52, ch53, ch54, ch55, ch56, ch57, ch58, ch59,
                ch6, ch7, ch8, ch9, ch60, ch61, ch62, ch63, ch64));
        var gen = new Generator();
        var actual = gen.getStartCheckers();

        assertEquals(expected.size(), actual.size());
        for (var ch : expected) {
            if (!actual.contains(ch)) {
                fail(String.format("No checker: %s", ch.toString()));
            }
        }
    }
}

