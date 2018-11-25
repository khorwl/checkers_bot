package core.checkers.game;

import core.checkers.players.IPlayer;
import core.checkers.primitives.Checker;
import core.checkers.primitives.TurnStatus;
import java.util.Set;

public interface IGame {

  GameState getState();

  Set<Checker> getCheckers();

  TurnStatus performNextTurn();

  IPlayer getWhitePlayer();

  IPlayer getBlackPlayer();
}
