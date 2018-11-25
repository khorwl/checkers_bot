package tests;

import core.checkers.game.Game;
import core.checkers.game.GameBoard;
import core.checkers.game.GameState;
import core.checkers.game.Generator;
import core.checkers.players.IPlayer;
import core.checkers.primitives.Checker;
import core.checkers.primitives.Color;
import core.checkers.primitives.Rank;
import core.checkers.primitives.Turn;
import core.checkers.primitives.TurnStatus;
import core.checkers.primitives.Vector;
import core.tools.CoreException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GameUnitTests {

  private int numberEven =  ((int) (Math.random() * 20)) * 2;

  private Game game;
  private Generator gen = new Generator();
  private IPlayer whitePlayer;
  private IPlayer blackPlayer;


  @BeforeEach
  public void SetUp() {
    var checkers = gen.getStartCheckers();

    whitePlayer = mock(IPlayer.class);
    blackPlayer = mock(IPlayer.class);
    game = new Game(whitePlayer, blackPlayer, new GameState(), new GameBoard(checkers));
  }

  @Test
  void getState_shouldReturnRightValue() {
    var expected = new GameState();

    var actual = game.getState();

    assertEquals(expected, actual);
  }

  @Test
  void performNextTurn_shouldReturnRightValue() throws CoreException {
    var expected = TurnStatus.SUCCESS;
    when(whitePlayer.getNextTurn()).thenReturn(new Turn(new Vector(2, 1, 0), new Vector(3, 1, 0)));
    when(whitePlayer.haveNextTurn()).thenReturn(true);

    var actual = game.performNextTurn();

    assertEquals(expected, actual);
  }

  @Test
  void getState_settingAfterFirstStep_shouldReturnRightValue() throws CoreException {
    var expected = new GameState();
    expected.changeTurnOrderToNext();
    when(whitePlayer.getNextTurn()).thenReturn(new Turn(new Vector(2, 1, 0), new Vector(3, 1, 0)));
    when(whitePlayer.haveNextTurn()).thenReturn(true);
    game.performNextTurn();

    var actual = game.getState();

    assertEquals(expected, actual);
  }

  @Test
  void getState_settingAfterSecondStep_shouldReturnRightValue() throws CoreException {
    var expected = new GameState();

    when(whitePlayer.getNextTurn()).thenReturn(new Turn(new Vector(2, 1, 0), new Vector(3, 1, 0)));
    when(whitePlayer.haveNextTurn()).thenReturn(true);
    game.performNextTurn();
    when(whitePlayer.haveNextTurn()).thenReturn(false);
    when(blackPlayer.haveNextTurn()).thenReturn(true);
    when(blackPlayer.getNextTurn()).thenReturn(new Turn(new Vector(2, 2, 7), new Vector(2, 3, 6)));
    game.performNextTurn();

    var actual = game.getState();

    assertEquals(expected, actual);
  }

  @Test
  void getCheckers_shouldReturnRightValue(){
    var expected = gen.getStartCheckers();

    var actual = game.getCheckers();

    assertEquals(expected, actual);
  }


  @Test
  void getCheckers_gettingAfterStep_shouldReturnRightValue() throws CoreException {
    when(whitePlayer.getNextTurn()).thenReturn(new Turn(new Vector(2, 1, 0), new Vector(3, 1, 0)));
    when(whitePlayer.haveNextTurn()).thenReturn(true);
    game.performNextTurn();

    var expected = gen.getStartCheckers();
    expected.remove(new Checker(new Vector(2,1,0), Color.WHITE, Rank.SOLDIER));
    expected.add(new Checker(new Vector(3,1,0), Color.WHITE, Rank.SOLDIER));

    var actual = game.getCheckers();

    assertEquals(expected, actual);
  }
}
