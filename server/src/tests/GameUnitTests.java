package tests;

import core.checkers.game.Game;
import core.checkers.players.IPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GameUnitTests {

    @BeforeEach
    public void SetUp(){
        var whitePlayer= mock(IPlayer.class);
        var blackPlayer= mock(IPlayer.class);
        var game = new Game(whitePlayer,  blackPlayer, null, null);
    }


    @Test
    void getState_shouldReturnRightValue(){

    }
}
