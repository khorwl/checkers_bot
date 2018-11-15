package tests;

import core.checkers.GameBoard;
import core.checkers.Generator;
import core.checkers.primitives.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameBoardUnitTests {

    private Generator gen;
    private HashSet<Checker> checkers;
    private GameBoard board;

    @BeforeEach
    public void setUp() {
        gen = new Generator();
        checkers = gen.getStartCheckers();
        board = new GameBoard(checkers);
    }

    @Test
    void getCheckers_gettingAfterStep_shouldReturnRightChechers() {
        board.makeMove(new Vector(5, 4, 0), new Vector(5, 5, 1));
        var ch = new Checker(new Vector(5, 5, 1), Color.WHITE, Rank.SOLDIER);

        assertTrue(board.getCheckers().contains(ch));

        assertEquals(checkers, board.getCheckers());
    }

    @Test
    void makeMove_makeValidStepSoldier_shouldReturnRightTurnStatus() {
        var expected = TurnStatus.SUCCESS;
        var actual = board.makeMove(new Vector(5, 4, 0), new Vector(5, 5, 1));
        var ch = new Checker(new Vector(5, 5, 1), Color.WHITE, Rank.SOLDIER);

        assertEquals(expected, actual);
        assertTrue(board.getCheckers().contains(ch));
    }

    @Test
    void makeMove_makeStepSoldierNotFreeCell_shouldReturnRightTurnStatus() {
        var expected = TurnStatus.FAIL;
        board.makeMove(new Vector(5, 4, 0), new Vector(5, 5, 1));
        var actual = board.makeMove(new Vector(5, 5, 1), new Vector(5, 6, 0));

        assertEquals(expected, actual);
    }

    @Test
    void makeMove_makeStepSoldierNotBoundCell_shouldReturnRightTurnStatus() {
        var expected = TurnStatus.FAIL;
        assertEquals(expected, board.makeMove(new Vector(5, 4, 0), new Vector(5, 5, 2)));
    }

    @Test
    void makeMove_makeValidStepLady_shouldReturnRightTurnStatus() {
        var expected = TurnStatus.SUCCESS;
        checkers.add(new Checker(new Vector(5, 1,1), Color.WHITE, Rank.LADY));
        var actual = board.makeMove(new Vector(5, 1, 1), new Vector(5, 5, 5));
        var ch = new Checker(new Vector(5, 5, 5), Color.WHITE, Rank.LADY);

        assertEquals(expected, actual);
        assertTrue(board.getCheckers().contains(ch));
    }

    @Test
    void makeMove_makeStepLadyNotBoundCell_shouldReturnRightTurnStatus() {
        var expected = TurnStatus.FAIL;
        checkers.add(new Checker(new Vector(5, 1,1), Color.WHITE, Rank.LADY));
        var actual = board.makeMove(new Vector(5, 1, 1), new Vector(5, 8, 8));

        assertEquals(expected, actual);
    }

    @Test
    void makeMove_makeStepLadyNotFreeCell_shouldReturnRightTurnStatus() {
        var expected = TurnStatus.FAIL;
        checkers.add(new Checker(new Vector(5, 1,1), Color.WHITE, Rank.LADY));
        var actual = board.makeMove(new Vector(5, 1, 1), new Vector(5, 2, 0));

        assertEquals(expected, actual);
    }



}
