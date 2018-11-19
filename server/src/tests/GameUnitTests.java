package tests;

import core.checkers.Game;
import core.checkers.players.IPlayer;
import core.checkers.primitives.Vector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GameUnitTests {

    @BeforeEach
    public void SetUp(){
        var whitePlayer= mock(IPlayer.class);
        var blackPlayer= mock(IPlayer.class);
        var game = new Game(whitePlayer,  blackPlayer, null, null);

        when(whitePlayer.haveNextTurn()).thenReturn(true);
        verify(whitePlayer, times(1)).haveNextTurn();
    }


    @Test
    void getState_shouldReturnRightValue(){

    }
}
