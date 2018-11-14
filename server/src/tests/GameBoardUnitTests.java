package tests;

import core.checkers.GameBoard;
import core.checkers.Generator;
import core.checkers.primitives.*;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameBoardUnitTests {

    private final Generator gen = new Generator();
    private final HashSet<Checker> checkers = gen.getStartCheckers();
    private final GameBoard board = new GameBoard(checkers);

    @Test
    void makeMove_makeValidStep_shouldReturnRightTurnStatus() {
        var expected = TurnStatus.SUCCESS;
        var actual = board.makeMove(new Vector(5, 4, 0), new Vector(5, 5, 1));
        var ch = new Checker(new Vector(5, 5, 1), Color.WHITE, Rank.SOLDIER);

        assertEquals(expected, actual);
        assertTrue(board.getCheckers().contains(ch));
    }

    @Test
    void getCheckers_gettingAfterStep_shouldReturnRightChechers() {
        board.makeMove(new Vector(5, 4, 0), new Vector(5, 5, 1));
        var ch = new Checker(new Vector(5, 5, 1), Color.WHITE, Rank.SOLDIER);

        assertTrue(board.getCheckers().contains(ch));

        assertEquals(checkers, board.getCheckers());
    }



}
