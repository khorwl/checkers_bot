package tests;

import core.checkers.game.GameBoard;
import core.checkers.game.Generator;
import core.checkers.primitives.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void makeMove_makeValidStepWhiteSoldier_shouldReturnRightTurnStatus() {
        var expected = TurnStatus.SUCCESS;
        var actual = board.makeMove(new Vector(5, 4, 0), new Vector(5, 5, 1));
        var ch = new Checker(new Vector(5, 5, 1), Color.WHITE, Rank.SOLDIER);

        assertEquals(expected, actual);
        assertTrue(board.getCheckers().contains(ch));
    }

    @Test
    void makeMove_makeValidStepBlackSoldier_shouldReturnRightTurnStatus() {
        var expected = TurnStatus.SUCCESS;
        var actual = board.makeMove(new Vector(0, 0, 7), new Vector(1, 0, 6));
        var ch = new Checker(new Vector(1, 0, 6), Color.BLACK, Rank.SOLDIER);

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

    @Test
    void makeMove_whiteSoldierMakeValidCut_shouldReturnRightTurnStatus(){
        var expected = TurnStatus.SUCCESS;
        board.makeMove(new Vector(0,0,7),  new Vector(1, 0, 6));
        board.makeMove(new Vector(1,0,6),  new Vector(1, 1, 5));
        board.makeMove(new Vector(1, 1, 5),  new Vector(1, 2, 4));
        board.makeMove(new Vector(1,2,4),  new Vector(2,2, 3));
        board.makeMove(new Vector(2,2,3),  new Vector(3,2,2));
        board.makeMove(new Vector(3,2,2),  new Vector(3,3, 1));

        var actual = board.makeMove(new Vector(2,3,0), new Vector(4,3,2));

        assertEquals( 63, checkers.size());
        assertEquals(expected, actual);
    }

    @Test
    void makeMove_blackSoldierMakeValidCut_shouldReturnRightTurnStatus(){
        var expected = TurnStatus.SUCCESS;
        board.makeMove(new Vector(4,3,0),  new Vector(4,4, 1));
        board.makeMove(new Vector(4,4,1),  new Vector(4,3,2));
        board.makeMove(new Vector(4,3,2),  new Vector(4,4, 3));
        board.makeMove(new Vector(4,4, 3),  new Vector(4,3, 4));
        board.makeMove(new Vector(4,3, 4),  new Vector(4,4, 5));
        board.makeMove(new Vector(4,4, 5),  new Vector(4,3, 6));

        var actual = board.makeMove(new Vector(4,4,7), new Vector(4,2,5));

        assertEquals( 63, checkers.size());
        assertEquals(expected, actual);
    }

    @Test
    void makeMove_blackLadyMakeValidCut_shouldReturnRightTurnStatus(){
        var expected = TurnStatus.SUCCESS;
        board.makeMove(new Vector(3,2,0), new Vector(4,2,1));
        var lady = new Checker(new Vector(5,2,2), Color.BLACK, Rank.LADY);
        checkers.add(lady);

        var actual = board.makeMove(new Vector(5,2,2), new Vector(3,2,0));

        assertEquals( 64, checkers.size());
        assertEquals(expected, actual);
    }

    @Test
    void makeMove_whiteLadyMakeValidCut_shouldReturnRightTurnStatus(){
        var expected = TurnStatus.SUCCESS;
        board.makeMove(new Vector(3,3,7), new Vector(4,3,6));
        var lady = new Checker(new Vector(5,3,5), Color.WHITE, Rank.LADY);
        checkers.add(lady);

        var actual = board.makeMove(new Vector(5,3,5), new Vector(3,3,7));

        assertEquals( 64, checkers.size());
        assertEquals(expected, actual);
    }

    @Test
    void makeMove_whiteMakeCutSelfChecker_shouldReturnRightTurnStatus(){
        var expected = TurnStatus.FAIL;
        board.makeMove(new Vector(4,1,0),  new Vector(4, 2, 1));

        var actual = board.makeMove(new Vector(4,3,0), new Vector(4,1,2));

        assertEquals(expected, actual);
        assertEquals(64, checkers.size());
    }

    @Test
    void makeMove_BlackMakeCutSelfChecker_shouldReturnRightTurnStatus(){
        var expected = TurnStatus.FAIL;
        board.makeMove(new Vector(4,0,7),  new Vector(4, 1, 6));

        var actual = board.makeMove(new Vector(4,2,7), new Vector(4,0,5));

        assertEquals(expected, actual);
        assertEquals(64, checkers.size());
    }

    @Test
    void makeMove_SoldierMakeCutNotFreeCell_shouldReturnRightTurnStatus(){
        var expected = TurnStatus.FAIL;
        var ch = new Checker(new Vector(4,2,5), Color.WHITE, Rank.SOLDIER);
        checkers.add(ch);
        board.makeMove(new Vector(4,2,7), new Vector(4,3,6));

        var actual = board.makeMove(new Vector(4,2,5), new Vector(4,4,7));

        assertEquals(expected, actual);
        assertEquals(65, checkers.size());
    }

    @Test
    void makeMove_LadyMakeCutNotFreeCell_shouldReturnRightTurnStatus(){
        var expected = TurnStatus.FAIL;
        var ch = new Checker(new Vector(4,2,5), Color.WHITE, Rank.LADY);
        checkers.add(ch);
        board.makeMove(new Vector(4,2,7), new Vector(4,3,6));

        var actual = board.makeMove(new Vector(4,2,5), new Vector(4,4,7));

        assertEquals(expected, actual);
        assertEquals(65, checkers.size());
    }

}
