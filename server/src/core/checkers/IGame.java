package core.checkers;

import core.checkers.players.IPlayer;
import core.checkers.primitives.TurnStatus;

public interface IGame {
  GameState getState();

  TurnStatus performNextTurn();

  IPlayer getWhitePlayer();
  IPlayer getBlackPlayer();
}
