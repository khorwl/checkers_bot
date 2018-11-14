package tests;

import core.checkers.GameState;
import core.checkers.primitives.Color;
import core.checkers.primitives.GameProgress;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameStateUnitTests {

    @Test
    void getProgress_shouldReturnRightValue() {
        var state = new GameState();
        var expected = GameProgress.IN_PROGRESS;

        assertEquals(state.getProgress(), expected);
    }


    @Test
    void getProgress_gettingNotStartValue_shouldReturnRightValue() {
        var state = new GameState();
        var expected = GameProgress.FINISHED_BY_WINNING_BLACK;
        state.setProgress(GameProgress.FINISHED_BY_WINNING_BLACK);

        assertEquals(state.getProgress(), expected);
    }


    @Test
    void setProgress_shouldRightChangeValue() {
        var state = new GameState();
        var expected = GameProgress.FINISHED_BY_WINNING_BLACK;

        state.setProgress(GameProgress.FINISHED_BY_WINNING_BLACK);

        assertEquals(state.getProgress(), expected);
    }

    @Test
    void setProgress_settingOtherValue_shouldRightChangeValue() {
        var state = new GameState();
        var expected = GameProgress.FINISHED_BY_DRAW;

        state.setProgress(GameProgress.FINISHED_BY_DRAW);

        assertEquals(state.getProgress(), expected);
    }

    @Test
    void getNextTurnOrder_gettingNotStartValue_shouldReturnRightValue() {
        var state = new GameState();
        state.changeNextTurnOrder();
        var expected = Color.BLACK;

        var actual = state.getNextTurnOrder();

        assertEquals(actual, expected);
    }


    @Test
    void getNextTurnOrder_shouldReturnRightValue() {
        var state = new GameState();
        var expected = Color.WHITE;

        var actual = state.getNextTurnOrder();

        assertEquals(actual, expected);
    }


    @Test
    void changeNextTurnOrder_shouldReturnRightValue() {
        var state = new GameState();
        state.changeNextTurnOrder();
        var expected = Color.BLACK;

        var actual = state.getNextTurnOrder();

        assertEquals(actual, expected);
    }


    @Test
    void changeNextTurnOrder_changeOddTime_shouldReturnRightValue() {
        var state = new GameState();
        var a = 20;
        var n = ((int) (Math.random() * a)) * 2 - 1;
        for (var i = 0; i < n; i++)
            state.changeNextTurnOrder();

        var expected = Color.BLACK;

        var actual = state.getNextTurnOrder();

        assertEquals(actual, expected);
    }

    @Test
    void changeNextTurnOrder_changeEvenTime_shouldReturnRightValue() {
        var state = new GameState();
        var a = 20;
        var n = ((int) (Math.random() * a)) * 2;
        for (var i = 0; i < n; i++)
            state.changeNextTurnOrder();

        var expected = Color.WHITE;

        var actual = state.getNextTurnOrder();

        assertEquals(actual, expected);
    }


    @Test
    void getNextTurnOrder_gettingChangeOddTimeValue_shouldReturnRightValue() {
        var state = new GameState();
        var a = 20;
        var n = ((int) (Math.random() * a)) * 2 - 1;
        for (var i = 0; i < n; i++)
            state.changeNextTurnOrder();
        var expected = Color.BLACK;

        var actual = state.getNextTurnOrder();

        assertEquals(actual, expected);
    }

}
